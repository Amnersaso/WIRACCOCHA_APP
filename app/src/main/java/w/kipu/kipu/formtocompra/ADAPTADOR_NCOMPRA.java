package w.kipu.kipu.formtocompra;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ITEM_NCOMPRA;
import w.kipu.kipu.ITEM.ItemDatosJSON;
import w.kipu.kipu.ITEM.ItemEstivaJSON;
import w.kipu.kipu.ITEM.ItemOneSpinner;
import w.kipu.kipu.PrintPDF.PrintDocumentPDF;
import w.kipu.kipu.R;
import w.kipu.kipu.formtocompra.ConvertFile_Byte.EnviarJsonAPI;
import w.kipu.kipu.formtocompra.ConvertFile_Byte.File_Byte;

public class ADAPTADOR_NCOMPRA extends RecyclerView.Adapter<ADAPTADOR_NCOMPRA.ViewHolder> {
    public List<ITEM_NCOMPRA>ncomprasLista;
    public List<ITEM_NCOMPRA>ncomprasListaFull;
    public ArrayList<ItemDatosJSON> itemNomb = new ArrayList<>();
    CONEXION conexion = new CONEXION();
    Connection conPublica;
    AdapterSQLite myDB;
    String estado;
    public String dniProv,nombreCompleto,telefono,comunidad,distrito,direccion,referencia,tipo,parcela,personaCobraCompra,personaEntregaCompra,telefonoCompra,tSacosCompra,tPesoCompra,tPesoModCompra,tPesoOBSCompra;
    String loteProveedor,guiaRemision,montoTotalCompra,serieCompra,numDocCompra,obsCompra,placaCompra,fechaCompra,soliAdelantoCompra,adelantCompra,estadoCompra,comuniCompra,distriCompra,parcelaCompra,tipoRegCompra;
    public String codAlmacen,codAlmacenUbi;
    LocalTime time = LocalTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH-mm-ss");
    EnviarJsonAPI enviarJsonAPI = new EnviarJsonAPI();

    public ADAPTADOR_NCOMPRA(List<ITEM_NCOMPRA> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
    }
    android.app.AlertDialog progressBar;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notacompra, parent, false);
        myDB = new AdapterSQLite(view.getContext());
        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(view.getContext()).setCancelable(false).build();
        return new ViewHolder(view).LinkAdapter(this);
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.productor.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getProductor());
        holder.peso.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getPeso());
        holder.sacos.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getSaco());
        holder.total.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getTotal());
        holder.numCompra.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getNumcompra());
        holder.txt_idCompra.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getIdCompra());
        holder.txt_idProv.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getIdProveedor());
        holder.txt_lote.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getLote());
        holder.txt_tipoRegis.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getTipoRegistro());
        holder.txt_estado.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getEstado());
        holder.txt_idUsuario.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getIdUsu());
        holder.tipoUser.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getTipoUser());
        final String tipoCon = ((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getTipoCon();

        final String idCompra = holder.txt_idCompra.getText().toString();
        final String idProv = holder.txt_idProv.getText().toString();
        final String tipoRegis = holder.txt_tipoRegis.getText().toString();
        final String idUsu = holder.txt_idUsuario.getText().toString();
        final String tipoUs = holder.tipoUser.getText().toString();
        estado = holder.txt_estado.getText().toString();

        if(estado.equals("ENVIADO") || tipoCon.equals("NUBE"))
            holder.btn_sync.setVisibility(View.INVISIBLE);
        else
            holder.btn_sync.setVisibility(View.VISIBLE);
//        holder.btn_sync.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                class ValidarInsert extends AsyncTask<String,String,String> {
//                    String z,contarArray,countList,countArray,countListAntes;
//                    byte[] archivFile;
//                    boolean deleted=false;
//                    File file;
//                    boolean isSuccess = false;
//
//                    @Override
//                    protected void onPreExecute(){
//                        super.onPreExecute();
//                        progressBar.show();
//                    }
//                    @Override
//                    protected String doInBackground(String... params){
//                        try {
//                            conPublica = conexion.ConnectionDB_Publica();
//                            if(conPublica!= null){
//                                Cursor res = holder.myDB.mostrarProvDNI(idProv);
//                                if(res!= null && res.getCount()>0){
//                                    if (res.moveToNext()){
//                                        dniProv = res.getString(0);
//                                        nombreCompleto = res.getString(1);
//                                        telefono = res.getString(2);
//                                        comunidad = res.getString(3);
//                                        distrito = res.getString(4);
//                                        direccion = res.getString(5);
//                                        referencia = res.getString(6);
//                                        tipo = res.getString(7);
//                                        parcela = res.getString(8);
//                                    }
//                                }
//                                Cursor res1 = holder.myDB.selectIdProveedorCompra(idCompra);
//                                if(res1!= null && res1.getCount()>0){
//                                    if (res1.moveToNext()){
//                                        personaCobraCompra = res1.getString(4);
//                                        personaEntregaCompra = res1.getString(5);
//                                        telefonoCompra = res1.getString(6);
//                                        tSacosCompra = res1.getString(7);
//                                        tPesoCompra = res1.getString(8);
//                                        tPesoModCompra = res1.getString(9);
//                                        tPesoOBSCompra = res1.getString(10);
//                                        montoTotalCompra = res1.getString(11);
//                                        serieCompra = res1.getString(12);
//                                        numDocCompra = res1.getString(13);
//                                        obsCompra = res1.getString(14);
//                                        placaCompra = res1.getString(15);
//                                        fechaCompra = res1.getString(16);
//                                        soliAdelantoCompra = res1.getString(17);
//                                        adelantCompra = res1.getString(18);
//                                        estadoCompra = res1.getString(19);
//                                        comuniCompra = res1.getString(20);
//                                        distriCompra = res1.getString(21);
//                                        parcelaCompra = res1.getString(22);
//                                        tipoRegCompra = res1.getString(23);
//                                    }
//                                }
//                                Cursor alma = holder.myDB.mostrarAlmacenProductoServer(idCompra);
//                                if(alma!= null && alma.getCount()>0) {
//                                    if (alma.moveToNext()) {
//                                        codAlmacen = alma.getString(0);
//                                        codAlmacenUbi = alma.getString(1);
//                                    }
//                                }
//
//                                JSONObject parent = new JSONObject();
//                                JSONObject jsonObject = new JSONObject();
//
//
//                                jsonObject.put("TIPO_COMPRA", tipoRegis);
//                                jsonObject.put("DNI", dniProv);
//                                jsonObject.put("ID_USUARIO", idUsu);
//                                jsonObject.put("ID_PRODUCTOR", idProv);
//                                jsonObject.put("PRODUCTOR", nombreCompleto);
//                                jsonObject.put("COMUNIDAD", comuniCompra);
//                                jsonObject.put("DISTRITO", distriCompra);
//                                jsonObject.put("TIPO", tipo);
//                                jsonObject.put("PARCELA", parcelaCompra);
//                                jsonObject.put("DIRECCION", direccion);
//                                jsonObject.put("REFERENCIA", referencia);
//                                jsonObject.put("COD_ALMACEN", codAlmacen);
//                                jsonObject.put("ALMACEN", codAlmacenUbi);
//                                jsonObject.put("CELULAR", telefonoCompra);
//                                jsonObject.put("COBRADO_POR", personaCobraCompra);
//                                jsonObject.put("ENTREGADO_POR", personaEntregaCompra);
//                                jsonObject.put("TOTAL_SACOS", tSacosCompra);
//                                jsonObject.put("TOTAL_PESOS", tPesoCompra);
//                                jsonObject.put("TOTAL_PESOS_MOD", tPesoModCompra);
//                                jsonObject.put("MONTO_TOTAL", montoTotalCompra);
//                                jsonObject.put("SERIE", serieCompra);
//                                jsonObject.put("NUM_DOC", numDocCompra);
//                                jsonObject.put("OBS", obsCompra);
//                                jsonObject.put("PLACA", placaCompra);
//                                jsonObject.put("FECHA", fechaCompra);
//                                jsonObject.put("SOLI_ADELANTO", soliAdelantoCompra);
//                                jsonObject.put("ADELANTO", adelantCompra);
//
//                                String val = null;
//                                Cursor resj = holder.myDB.mostrarListaDetalleCompraPesos(idCompra);
//                                if(resj!= null && resj.getCount()>0){
//                                    for(int a=0;a<100;a++){
//                                        while (resj.moveToNext()){
//                                            if(val==null){
//                                                val=resj.getString(3);}
//                                            itemNomb.add(new ItemDatosJSON(resj.getString(1),resj.getString(2),resj.getString(3),resj.getString(11),resj.getString(4),resj.getString(5),resj.getString(6),resj.getString(7),resj.getString(8),resj.getString(9)));
//                                        }}}
//                                int a =1,b=1;
//                                List<String> list_fecha = new ArrayList<>();
//                                List<String> list_numero = new ArrayList<>();
//                                List<String> list_produc = new ArrayList<>();
//                                List<String> list_lote = new ArrayList<>();
//                                List<String> list_p_real = new ArrayList<>();
//                                List<String> list_p_modi = new ArrayList<>();
//                                List<String> list_p_unit = new ArrayList<>();
//                                List<String> list_p_total = new ArrayList<>();
//                                List<String> list_obs = new ArrayList<>();
//                                List<String> list_obsG = new ArrayList<>();
//                                List<String> list_estiva_nombre = new ArrayList<>();
//                                List<String> list_estiva_tipo = new ArrayList<>();
//                                List<String> list_docRP_serie = new ArrayList<>();
//                                List<String> list_docRP_numer = new ArrayList<>();
//                                List<String> list_alma_codAlma = new ArrayList<>();
//                                List<String> list_alma_codAlmaUbica = new ArrayList<>();
//                                List<String> list_alma_descrip = new ArrayList<>();
//                                List<String> list_alma_lote = new ArrayList<>();
//                                List<String> list_alma_stock = new ArrayList<>();
//                                List<String> list_alma_preCompra = new ArrayList<>();
//                                List<String> list_alma_pesoAcumula = new ArrayList<>();
//
//                                countListAntes= String.valueOf(list_fecha.size());
//                                list_fecha.clear();
//                                list_numero.clear();
//                                list_produc.clear();
//                                list_lote.clear();
//                                list_p_real.clear();
//                                list_p_modi.clear();
//                                list_p_unit.clear();
//                                list_p_total.clear();
//                                list_obs.clear();
//                                list_obsG.clear();
//
//                                countList= String.valueOf(itemNomb.size());
////                                SimpleDateFormat formatter5=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//                                //DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                                Date date1;
//                                for (ItemDatosJSON item: itemNomb){
////                                    jsonObjectDetaPesoItem.put("FECHA" ,item.getFECHA_HORA());
////                                    jsonObjectDetaPesoItem.put("NUMER" ,item.getNUM_ITEM());
////                                    jsonObjectDetaPesoItem.put("PRODU" ,item.getPRODUCTO());
////                                    jsonObjectDetaPesoItem.put("LOTE_" ,item.getLOTE());
////                                    jsonObjectDetaPesoItem.put("P_REA" ,item.getPESO_REAL());
////                                    jsonObjectDetaPesoItem.put("P_MOD" ,item.getPESO_MODIFICADO());
////                                    jsonObjectDetaPesoItem.put("P_UNI" ,item.getPRECIO_UNIT());
////                                    jsonObjectDetaPesoItem.put("P_TOT" ,item.getPRECIO_TOTAL());
////                                    jsonObjectDetaPesoItem.put("OBS__" ,item.getOBSERVACION());
////                                    jsonObjectDetaPesoItem.put("OBS_G" ,item.getOBSERVACION_GENERAL());
////                                    item_deta_fecha= item_deta_fecha +"|"+item.getFECHA_HORA();
////                                    item_deta_num= item_deta_num+"|"+item.getNUM_ITEM();
////                                    item_deta_produc= item_deta_produc+"|"+item.getPRODUCTO();
////                                    item_deta_lote= item_deta_lote+"|"+item.getLOTE();
////                                    item_deta_p_real= item_deta_p_real+"|"+item.getPESO_REAL();
////                                    item_deta_p_modi= item_deta_p_modi+"|"+item.getPESO_MODIFICADO();
////                                    item_deta_p_unit= item_deta_p_unit+"|"+item.getPRECIO_UNIT();
////                                    item_deta_p_total= item_deta_p_total+"|"+item.getPRECIO_TOTAL();
////                                    item_deta_obs= item_deta_obs+"|"+item.getOBSERVACION();
////                                    item_deta_obs_g= item_deta_obs_g+"|"+item.getOBSERVACION_GENERAL();
//                                    //date1=dtf.format(item.getFECHA_HORA());
//                                    list_fecha.add(item.getFECHA_HORA());
//                                    list_numero.add(item.getNUM_ITEM());
//                                    list_produc.add(item.getPRODUCTO());
//                                    list_lote.add(item.getLOTE());
//                                    list_p_real.add(item.getPESO_REAL());
//                                    list_p_modi.add(item.getPESO_MODIFICADO());
//                                    list_p_unit.add(item.getPRECIO_UNIT());
//                                    list_p_total.add(item.getPRECIO_TOTAL());
//                                    list_obs.add(item.getOBSERVACION());
//                                    list_obsG.add(item.getOBSERVACION_GENERAL());
//                                } //CARGO LOS DATOS DE LOS ITEM EN ARRAY JSON
//
//                                Cursor rsEstiva = myDB.listaEstivaRecyclerview(idCompra);
//                                if(rsEstiva!= null && rsEstiva.getCount()>0){
//                                    list_estiva_nombre.clear();
//                                    list_estiva_tipo.clear();
//                                    while (rsEstiva.moveToNext()){
//                                        list_estiva_nombre.add(rsEstiva.getString(0));
//                                        list_estiva_tipo.add(rsEstiva.getString(2));
//                                    }
//                                }
//                                Cursor rs = myDB.listaSerieNumEncabezado_RP(idCompra);
//                                if(rs!= null && rs.getCount()>0){
//                                    list_docRP_serie.clear();
//                                    list_docRP_numer.clear();
//                                    while (rs.moveToNext()){
//                                        list_docRP_serie.add(rs.getString(1));
//                                        list_docRP_numer.add(rs.getString(2));
//                                    }
//                                }
//                                Cursor res3 = holder.myDB.mostrarListaDetalleAlmacenProduc(idCompra);
//                                if(res3!= null && res3.getCount()>0){
//                                    list_alma_codAlma.clear();
//                                    list_alma_codAlmaUbica.clear();
//                                    list_alma_descrip.clear();
//                                    list_alma_lote.clear();
//                                    list_alma_stock.clear();
//                                    list_alma_preCompra.clear();
//                                    list_alma_pesoAcumula.clear();
//                                    while (res3.moveToNext()){
//                                        list_alma_codAlma.add(codAlmacen);
//                                        list_alma_codAlmaUbica.add(codAlmacenUbi);
//                                        list_alma_descrip.add(res3.getString(1));
//                                        list_alma_lote  .add(res3.getString(5));
//                                        list_alma_stock.add(res3.getString(2));
//                                        list_alma_preCompra.add(res3.getString(4));
//                                        list_alma_pesoAcumula.add(res3.getString(3));
//                                    }
//
//                                }
//                                String li = new Gson().toJson(list_alma_descrip.size());
//                                z=li + " | " + list_alma_descrip.size();
//
////                                JSONArray array_fecha = new JSONArray();
////                                JSONArray array_numero = new JSONArray();
////                                JSONArray array_produc = new JSONArray();
////                                JSONArray array_lote = new JSONArray();
////                                JSONArray array_p_real = new JSONArray();
////                                JSONArray array_p_modi = new JSONArray();
////                                JSONArray array_p_unit = new JSONArray();
////                                JSONArray array_p_total = new JSONArray();
////                                JSONArray array_obs = new JSONArray();
////                                JSONArray array_obsG = new JSONArray();
////                                JSONArray array_estiva_nom = new JSONArray();
////                                JSONArray array_estiva_tipo = new JSONArray();
////                                JSONArray array_docRP_serie = new JSONArray();
////                                JSONArray array_docRP_numer = new JSONArray();
////                                JSONArray array_alma_codAlma = new JSONArray();
////                                JSONArray array_alma_codAlmaUbi = new JSONArray();
////                                JSONArray array_alma_descrip = new JSONArray();
////                                JSONArray array_alma_lote = new JSONArray();
////                                JSONArray array_alma_stock = new JSONArray();
////                                JSONArray array_alma_preCompra = new JSONArray();
////                                JSONArray array_alma_pesoAcumu = new JSONArray();
//                                JSONArray array_fecha;
//                                JSONArray array_numero;
//                                JSONArray array_produc;
//                                JSONArray array_lote;
//                                JSONArray array_p_real;
//                                JSONArray array_p_modi;
//                                JSONArray array_p_unit;
//                                JSONArray array_p_total;
//                                JSONArray array_obs;
//                                JSONArray array_obsG;
//                                JSONArray array_estiva_nom;
//                                JSONArray array_estiva_tipo;
//                                JSONArray array_docRP_serie;
//                                JSONArray array_docRP_numer;
//                                JSONArray array_alma_codAlma;
//                                JSONArray array_alma_codAlmaUbi;
//                                JSONArray array_alma_descrip;
//                                JSONArray array_alma_lote;
//                                JSONArray array_alma_stock;
//                                JSONArray array_alma_preCompra;
//                                JSONArray array_alma_pesoAcumu;
//
//                                array_fecha = new JSONArray(new ArrayList<String>());
//                                array_numero = new JSONArray(new ArrayList<String>());
//                                array_produc = new JSONArray(new ArrayList<String>());
//                                array_lote = new JSONArray(new ArrayList<String>());
//                                array_p_real = new JSONArray(new ArrayList<String>());
//                                array_p_modi = new JSONArray(new ArrayList<String>());
//                                array_p_unit = new JSONArray(new ArrayList<String>());
//                                array_p_total = new JSONArray(new ArrayList<String>());
//                                array_obs = new JSONArray(new ArrayList<String>());
//                                array_obsG = new JSONArray(new ArrayList<String>());
//                                array_estiva_nom = new JSONArray(new ArrayList<String>());
//                                array_estiva_tipo = new JSONArray(new ArrayList<String>());
//                                array_docRP_serie = new JSONArray(new ArrayList<String>());
//                                array_docRP_numer = new JSONArray(new ArrayList<String>());
//                                array_alma_codAlma = new JSONArray(new ArrayList<String>());
//                                array_alma_codAlmaUbi = new JSONArray(new ArrayList<String>());
//                                array_alma_descrip = new JSONArray(new ArrayList<String>());
//                                array_alma_lote = new JSONArray(new ArrayList<String>());
//                                array_alma_stock = new JSONArray(new ArrayList<String>());
//                                array_alma_preCompra = new JSONArray(new ArrayList<String>());
//                                array_alma_pesoAcumu = new JSONArray(new ArrayList<String>());
//
//                                for(int i = 0; i < list_fecha.size(); i++)
//                                    array_fecha.put(list_fecha.get(i));
//                                for(int i = 0; i < list_numero.size(); i++)
//                                    array_numero.put(list_numero.get(i));
//                                for(int i = 0; i < list_produc.size(); i++)
//                                    array_produc.put(list_produc.get(i));
//                                for(int i = 0; i < list_lote.size(); i++)
//                                    array_lote.put(list_lote.get(i));
//                                for(int i = 0; i < list_p_real.size(); i++)
//                                    array_p_real.put(list_p_real.get(i));
//                                for(int i = 0; i < list_p_modi.size(); i++)
//                                    array_p_modi.put(list_p_modi.get(i));
//                                for(int i = 0; i < list_p_unit.size(); i++)
//                                    array_p_unit.put(list_p_unit.get(i));
//                                for(int i = 0; i < list_p_total.size(); i++)
//                                    array_p_total.put(list_p_total.get(i));
//                                for(int i = 0; i < list_obs.size(); i++)
//                                    array_obs.put(list_obs.get(i));
//                                for(int i = 0; i < list_obsG.size(); i++)
//                                    array_obsG.put(list_obsG.get(i));
//                                for(int i = 0; i < list_estiva_nombre.size(); i++)
//                                    array_estiva_nom.put(list_estiva_nombre.get(i));
//                                for(int i = 0; i < list_estiva_tipo.size(); i++)
//                                    array_estiva_tipo.put(list_estiva_tipo.get(i));
//                                for(int i = 0; i < list_docRP_serie.size(); i++)
//                                    array_docRP_serie.put(list_docRP_serie.get(i));
//                                for(int i = 0; i < list_docRP_numer.size(); i++)
//                                    array_docRP_numer.put(list_docRP_numer.get(i));
//                                for(int i = 0; i < list_alma_codAlma.size(); i++)
//                                    array_alma_codAlma.put(list_alma_codAlma.get(i));
//                                for(int i = 0; i < list_alma_codAlmaUbica.size(); i++)
//                                    array_alma_codAlmaUbi.put(list_alma_codAlmaUbica.get(i));
//                                for(int i = 0; i < list_alma_descrip.size(); i++)
//                                    array_alma_descrip.put(list_alma_descrip.get(i));
//                                for(int i = 0; i < list_alma_lote.size(); i++)
//                                    array_alma_lote.put(list_alma_lote.get(i));
//                                for(int i = 0; i < list_alma_stock.size(); i++)
//                                    array_alma_stock.put(list_alma_stock.get(i));
//                                for(int i = 0; i < list_alma_preCompra.size(); i++)
//                                    array_alma_preCompra.put(list_alma_preCompra.get(i));
//                                for(int i = 0; i < list_alma_pesoAcumula.size(); i++)
//                                    array_alma_pesoAcumu.put(list_alma_pesoAcumula.get(i));
//
//                                jsonObject.remove("PRODUCTO_FECHA");
//                                jsonObject.remove("PRODUCTO_NUMERO");
//                                jsonObject.remove("PRODUCTO_PRODUCTO");
//                                jsonObject.remove("PRODUCTO_LOTE");
//                                jsonObject.remove("PRODUCTO_P_REAL");
//                                jsonObject.remove("PRODUCTO_P_MODI");
//                                jsonObject.remove("PRODUCTO_P_UNIT");
//                                jsonObject.remove("PRODUCTO_P_TOTAL");
//                                jsonObject.remove("PRODUCTO_OBS");
//                                jsonObject.remove("PRODUCTO_OBSG");
//                                jsonObject.remove("ESTIVA_NOMBRE");
//                                jsonObject.remove("ESTIVA_TIPO");
//                                jsonObject.remove("DOCRP_SERIE");
//                                jsonObject.remove("DOCRP_NUMER");
//                                jsonObject.remove("ALMACEN_CODALMA");
//                                jsonObject.remove("ALMACEN_CODALMAUBI");
//                                jsonObject.remove("ALMACEN_DESCRIP");
//                                jsonObject.remove("ALMACEN_LOTE");
//                                jsonObject.remove("ALMACEN_STOCK");
//                                jsonObject.remove("ALMACEN_PRECOMPRA");
//                                jsonObject.remove("ALMACEN_PESOACUMU");
//
//                                jsonObject.put("PRODUCTO_FECHA", array_fecha);
//                                jsonObject.put("PRODUCTO_NUMERO", array_numero);
//                                jsonObject.put("PRODUCTO_PRODUCTO", array_produc);
//                                jsonObject.put("PRODUCTO_LOTE", array_lote);
//                                jsonObject.put("PRODUCTO_P_REAL", array_p_real);
//                                jsonObject.put("PRODUCTO_P_MODI", array_p_modi);
//                                jsonObject.put("PRODUCTO_P_UNIT", array_p_unit);
//                                jsonObject.put("PRODUCTO_P_TOTAL", array_p_total);
//                                jsonObject.put("PRODUCTO_OBS", array_obs);
//                                jsonObject.put("PRODUCTO_OBSG", array_obsG);
//                                jsonObject.put("ESTIVA_NOMBRE", array_estiva_nom);
//                                jsonObject.put("ESTIVA_TIPO", array_estiva_tipo);
//                                jsonObject.put("DOCRP_SERIE", array_docRP_serie);
//                                jsonObject.put("DOCRP_NUMER", array_docRP_numer);
//                                jsonObject.put("ALMACEN_CODALMA", array_alma_codAlma);
//                                jsonObject.put("ALMACEN_CODALMAUBI", array_alma_codAlmaUbi);
//                                jsonObject.put("ALMACEN_DESCRIP", array_alma_descrip);
//                                jsonObject.put("ALMACEN_LOTE", array_alma_lote);
//                                jsonObject.put("ALMACEN_STOCK", array_alma_stock);
//                                jsonObject.put("ALMACEN_PRECOMPRA", array_alma_preCompra);
//                                jsonObject.put("ALMACEN_PESOACUMU", array_alma_pesoAcumu);
//
//                                contarArray= array_fecha.length() +" | " + array_numero.length() + " | " + array_produc.length() +" | " + array_lote.length() +" | " + array_p_real.length()+
//                                        " | " + array_p_modi.length() +" | " + array_p_unit.length() +" | " + array_p_total.length() +" | " + array_obs.length() +" | " + array_obsG.length() +
//                                        " | " + array_estiva_nom.length() +" | " + array_estiva_tipo.length() +" | " + array_docRP_serie.length() +" | " + array_docRP_numer.length() +
//                                        " | " + array_alma_codAlma.length() + " | " + array_alma_codAlmaUbi.length() +" | " + array_alma_descrip.length() +" | " + array_alma_lote.length() +
//                                        " | " + array_alma_stock.length() +" | " + array_alma_preCompra.length() +" | " + array_alma_pesoAcumu.length();
//
//                                file = new File(holder.context.getFilesDir(),serieCompra + "-" + numDocCompra +"-"+ time.format(myFormatObj) +".json");
//                                FileWriter fileWriter = new FileWriter(file);
//                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//                                bufferedWriter.write(jsonObject.toString(2));
//                                bufferedWriter.close();
//                                fileWriter.close();
//                                archivFile = File_Byte.getBytesFromFile(file);
//                                z = enviarJsonAPI.enviarJSON(archivFile,serieCompra + "-" + numDocCompra +"-"+ time.format(myFormatObj));
//                                isSuccess = true;
//                            }else
//                                z="ERROR DE CONEXION AL SERVIDOR";
//                        }catch (Exception e){
//                            z= "error de tread " +e.getMessage();
//                        }
//                        return z;
//                    }
//                    @Override
//                    protected void onPostExecute(String r){
//                        progressBar.dismiss();
//                        if(isSuccess){
//                            Toast.makeText(holder.itemView.getContext(),r + " | " + contarArray,Toast.LENGTH_LONG).show();
//                            holder.btn_sync.setVisibility(View.INVISIBLE);
//                            System.out.println(r);
//                            holder.adaptador_ncompra.notifyDataSetChanged();
//                        }else{
//                            Toast.makeText(holder.itemView.getContext(),r,Toast.LENGTH_LONG).show();
//                            holder.btn_sync.setVisibility(View.VISIBLE);
//                        }
//                    }
//                }
//                ValidarInsert validarInsert = new ValidarInsert();
//                validarInsert.execute("");
//            }
//        });
        holder.btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.itemView.getContext(),idCompra+" / "+idProv,Toast.LENGTH_SHORT).show();
                Intent enviar = new Intent(holder.itemView.getContext(), PrintDocumentPDF.class);
                enviar.putExtra("idCompraNCompra", idCompra);
                enviar.putExtra("idProvNCompra", idProv);
                enviar.putExtra("tipoRegistroNCompra", tipoRegis);
                enviar.putExtra("estadoCompraRegistroNCompra", estado);
                enviar.putExtra("tipo_Nota","SERVICIO");
                holder.itemView.getContext().startActivity(enviar);
            }
        });
        if(estado.equals("ENVIADO")) {
            holder.btn_continuarReg.setVisibility(View.INVISIBLE);
        }else
            holder.btn_continuarReg.setVisibility(View.VISIBLE);
        holder.btn_continuarReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tipoRegis.equals("NC")){
                    Intent intent = new Intent(holder.itemView.getContext(),FormatoCompra_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("estadoCompraRegistroNCompra", estado);
                    intent.putExtra("idProvNCompra", idProv);
                    intent.putExtra("idCompraNCompra", idCompra);
                    intent.putExtra("idCompraSQLiteMenuFormatoEstiva",idCompra);
                    intent.putExtra("idUsuarioADAPTER_NCOMPRA",idUsu);
                    intent.putExtra("tipo_User",tipoUs);
                    intent.putExtra("tipo_Nota",tipoRegis);
                    holder.itemView.getContext().startActivity(intent);
                    //Toast.makeText(holder.itemView.getContext(),tipoRegis,Toast.LENGTH_SHORT).show();
                }else if (tipoRegis.equals("NS")){
                    Intent intent = new Intent(holder.itemView.getContext(),FormatoCompra_Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("estadoCompraRegistroNCompra", estado);
                    intent.putExtra("idProvNCompra", idProv);
                    intent.putExtra("idCompraNCompra", idCompra);
                    intent.putExtra("idCompraSQLiteMenuFormatoEstiva",idCompra);
                    intent.putExtra("idUsuarioADAPTER_NCOMPRA",idUsu);
                    intent.putExtra("tipo_User",tipoUs);
                    intent.putExtra("tipo_Nota",tipoRegis);
                    holder.itemView.getContext().startActivity(intent);
                    //Toast.makeText(holder.itemView.getContext(),tipoRegis,Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(holder.itemView.getContext(),RegistroPeso.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("estadoCompraRegistroNCompra", estado);
                    intent.putExtra("idProvNCompra", idProv);
                    intent.putExtra("idCompraNCompra", idCompra);
                    intent.putExtra("idCompraSQLiteMenuFormatoEstiva",idCompra);
                    intent.putExtra("idUsuarioADAPTER_NCOMPRA",idUsu);
                    intent.putExtra("tipo_Nota",tipoRegis);
                    holder.itemView.getContext().startActivity(intent);
                    //Toast.makeText(holder.itemView.getContext(),tipoRegis,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String enviarJSON(byte[] archivo,String NombreArchivo){
        String SOAP_ACTION="http://tempuri.org/DataJson";
        String METODO_NAME="DataJson";
        String NAMESPACE="http://tempuri.org/";
        String URL="http://190.117.59.216:1234/Inicio_app.asmx";
        SoapPrimitive returnPrimitive;
        
        try{
            SoapObject request = new SoapObject(NAMESPACE,METODO_NAME);

            request.addProperty("nombreArchivo",NombreArchivo);
            //String encoded = Base64.encode(archivo);
            PropertyInfo info = new PropertyInfo();
            info.setName("archivo");
            info.setValue(archivo);
            info.setType(archivo.getClass());
            request.addProperty(info);
            MarshalBase64 marshalBase64 = new MarshalBase64();            
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);
            envelope.dotNet=true;
            envelope.implicitTypes=true;
            envelope.setAddAdornments(false);
            envelope.setOutputSoapObject(request);
            marshalBase64.register(envelope);

            HttpTransportSE transportSE = new HttpTransportSE(URL);
            transportSE.call(SOAP_ACTION,envelope);
            returnPrimitive =(SoapPrimitive) envelope.getResponse();
            String a = (String) returnPrimitive.getValue();
            return a;
        }catch (Exception e){
            return "falla de envio: "+ e.getMessage();
        }
    }
    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }
    public int getItemCount() {
        return this.ncomprasLista.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder  {
        TextView productor,txt_idCompra,txt_idProv,txt_lote,txt_tipoRegis,txt_estado;
        TextView peso,txt_idUsuario;
        TextView numCompra,tipoUser;
        TextView sacos;
        TextView total;
        Button btn_detalle;
        ImageButton btn_continuarReg,btn_sync;
        Context context;
        AdapterSQLite myDB;
        ADAPTADOR_NCOMPRA adaptador_ncompra;

        JSONArray array_fecha;
        JSONArray array_numero;
        JSONArray array_produc;
        JSONArray array_lote;
        JSONArray array_loteProveedor;
        JSONArray array_p_real;
        JSONArray array_p_modi;
        JSONArray array_p_unit;
        JSONArray array_p_total;
        JSONArray array_obs;
        JSONArray array_obsG;
        JSONArray array_estiva_nom;
        JSONArray array_estiva_tipo;
        JSONArray array_docRP_serie;
        JSONArray array_docRP_numer;
        JSONArray array_alma_codAlma;
        JSONArray array_alma_codAlmaUbi;
        JSONArray array_alma_descrip;
        JSONArray array_alma_lote;
        JSONArray array_alma_stock;
        JSONArray array_alma_preCompra;
        JSONArray array_alma_pesoAcumu;

        List<String> list_fecha;
        List<String> list_numero;
        List<String> list_produc;
        List<String> list_lote;
        List<String> list_loteProveedor;
        List<String> list_p_real;
        List<String> list_p_modi;
        List<String> list_p_unit;
        List<String> list_p_total;
        List<String> list_obs;
        List<String> list_obsG;
        List<String> list_estiva_nombre;
        List<String> list_estiva_tipo;
        List<String> list_docRP_serie;
        List<String> list_docRP_numer;
        List<String> list_alma_codAlma;
        List<String> list_alma_codAlmaUbica;
        List<String> list_alma_descrip;
        List<String> list_alma_lote;
        List<String> list_alma_stock;
        List<String> list_alma_preCompra ;
        List<String> list_alma_pesoAcumula;

        public ViewHolder(View itemView) {
            super(itemView);
            this.productor = itemView.findViewById(R.id.txt_ncompra_nombreproductor);
            this.peso = itemView.findViewById(R.id.txt_ncompra_peso);
            this.numCompra = itemView.findViewById(R.id.ncompra_numcompra);
            this.sacos = itemView.findViewById(R.id.ncompra_saco);
            this.total = itemView.findViewById(R.id.ncompra_total);
            this.btn_detalle = itemView.findViewById(R.id.btn_ncompraVerDetalle);
            this.txt_idCompra = itemView.findViewById(R.id.txt_idCompraNCompra);
            this.txt_idProv = itemView.findViewById(R.id.txt_idProvNCompra);
            this.txt_lote = itemView.findViewById(R.id.txt_ncompraLote);
            this.txt_tipoRegis = itemView.findViewById(R.id.txt_tipoRegistroNCompra);
            this.txt_estado = itemView.findViewById(R.id.txt_estadRegistroNCompra);
            this.btn_continuarReg = itemView.findViewById(R.id.btn_continuarRegNCompra);
            this.btn_sync = itemView.findViewById(R.id.btn_syncNCompra);
            this.txt_idUsuario = itemView.findViewById(R.id.txt_idusuarioNCompra);
            this.tipoUser = itemView.findViewById(R.id.txt_tipoUser_NCompra);

            this.context = itemView.getContext();
            this.myDB = new AdapterSQLite(itemView.getContext());

            btn_sync.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    class ValidarInsert extends AsyncTask<String,String,String> {
                        String z;
                        byte[] archivFile;
                        boolean deleted=false;
                        File file;
                        boolean isSuccess = false;
                        @Override
                        protected void onPreExecute(){
                            super.onPreExecute();
                            progressBar.show();
                        }
                        @Override
                        protected String doInBackground(String... params){
                            try {
                                array_fecha = new JSONArray(new ArrayList<String>());
                                array_numero = new JSONArray(new ArrayList<String>());
                                array_produc = new JSONArray(new ArrayList<String>());
                                array_lote = new JSONArray(new ArrayList<String>());
                                array_loteProveedor = new JSONArray(new ArrayList<String>());
                                array_p_real = new JSONArray(new ArrayList<String>());
                                array_p_modi = new JSONArray(new ArrayList<String>());
                                array_p_unit = new JSONArray(new ArrayList<String>());
                                array_p_total = new JSONArray(new ArrayList<String>());
                                array_obs = new JSONArray(new ArrayList<String>());
                                array_obsG = new JSONArray(new ArrayList<String>());
                                array_estiva_nom = new JSONArray(new ArrayList<String>());
                                array_estiva_tipo = new JSONArray(new ArrayList<String>());
                                array_docRP_serie = new JSONArray(new ArrayList<String>());
                                array_docRP_numer = new JSONArray(new ArrayList<String>());
                                array_alma_codAlma = new JSONArray(new ArrayList<String>());
                                array_alma_codAlmaUbi = new JSONArray(new ArrayList<String>());
                                array_alma_descrip = new JSONArray(new ArrayList<String>());
                                array_alma_lote = new JSONArray(new ArrayList<String>());
                                array_alma_stock = new JSONArray(new ArrayList<String>());
                                array_alma_preCompra = new JSONArray(new ArrayList<String>());
                                array_alma_pesoAcumu = new JSONArray(new ArrayList<String>());

                                list_fecha = new ArrayList<>();
                                list_numero = new ArrayList<>();
                                list_produc = new ArrayList<>();
                                list_lote = new ArrayList<>();
                                list_loteProveedor = new ArrayList<>();
                                list_p_real = new ArrayList<>();
                                list_p_modi = new ArrayList<>();
                                list_p_unit = new ArrayList<>();
                                list_p_total = new ArrayList<>();
                                list_obs = new ArrayList<>();
                                list_obsG = new ArrayList<>();
                                list_estiva_nombre = new ArrayList<>();
                                list_estiva_tipo = new ArrayList<>();
                                list_docRP_serie = new ArrayList<>();
                                list_docRP_numer = new ArrayList<>();
                                list_alma_codAlma = new ArrayList<>();
                                list_alma_codAlmaUbica = new ArrayList<>();
                                list_alma_descrip = new ArrayList<>();
                                list_alma_lote = new ArrayList<>();
                                list_alma_stock = new ArrayList<>();
                                list_alma_preCompra = new ArrayList<>();
                                list_alma_pesoAcumula = new ArrayList<>();

                                conPublica = conexion.ConnectionDB_Local();
                                if(conPublica!= null){
                                    Cursor res = myDB.mostrarProvDNI(txt_idProv.getText().toString());
                                    if(res!= null && res.getCount()>0){
                                        if (res.moveToNext()){
                                            dniProv = res.getString(0)+" ";
                                            nombreCompleto = res.getString(1)+" ";
                                            telefono = res.getString(2)+" ";
                                            comunidad = res.getString(3)+" ";
                                            distrito = res.getString(4)+" ";
                                            direccion = res.getString(5)+" ";
                                            referencia = res.getString(6)+" ";
                                            tipo = res.getString(7)+" ";
                                            parcela = res.getString(8)+" ";
                                        }
                                    }
                                    Cursor res1 = myDB.selectIdProveedorCompra(txt_idCompra.getText().toString());
                                    if(res1!= null && res1.getCount()>0){
                                        if (res1.moveToNext()){
                                            personaCobraCompra = res1.getString(4);
                                            personaEntregaCompra = res1.getString(5);
                                            telefonoCompra = res1.getString(6);
                                            tSacosCompra = res1.getString(7);
                                            tPesoCompra = res1.getString(8);
                                            tPesoModCompra = res1.getString(9);
                                            tPesoOBSCompra = res1.getString(10);
                                            montoTotalCompra = res1.getString(11);
                                            serieCompra = res1.getString(12);
                                            numDocCompra = res1.getString(13);
                                            obsCompra = res1.getString(14)+" ";
                                            placaCompra = res1.getString(15)+" ";
                                            fechaCompra = res1.getString(16);
                                            soliAdelantoCompra = res1.getString(17)+" ";
                                            adelantCompra = res1.getString(18);
                                            estadoCompra = res1.getString(19)+" ";
                                            comuniCompra = res1.getString(20)+" ";
                                            distriCompra = res1.getString(21)+" ";
                                            parcelaCompra = res1.getString(22)+" ";
                                            tipoRegCompra = res1.getString(23)+" ";
                                            loteProveedor = res1.getString(25)+" ";
                                            guiaRemision = res1.getString(26)+" ";
                                        }
                                    }
                                    Cursor alma = myDB.mostrarAlmacenProductoServer(txt_idCompra.getText().toString());
                                    if(alma!= null && alma.getCount()>0) {
                                        if (alma.moveToNext()) {
                                            codAlmacen = alma.getString(0)+" ";
                                            codAlmacenUbi = alma.getString(1)+" ";
                                        }
                                    }

                                    JSONObject jsonObject = new JSONObject();

                                    jsonObject.put("TIPO_COMPRA", txt_tipoRegis.getText().toString());
                                    jsonObject.put("DNI", dniProv);
                                    jsonObject.put("ID_USUARIO", txt_idUsuario.getText().toString());
                                    jsonObject.put("ID_PRODUCTOR", txt_idProv.getText().toString());
                                    jsonObject.put("PRODUCTOR", nombreCompleto);
                                    jsonObject.put("COMUNIDAD", comuniCompra);
                                    jsonObject.put("DISTRITO", distriCompra);
                                    jsonObject.put("TIPO", tipo);
                                    jsonObject.put("PARCELA", parcelaCompra);
                                    jsonObject.put("DIRECCION", direccion);
                                    jsonObject.put("REFERENCIA", referencia);
                                    jsonObject.put("COD_ALMACEN", codAlmacen);
                                    jsonObject.put("ALMACEN", codAlmacenUbi);
                                    jsonObject.put("CELULAR", telefonoCompra);
                                    jsonObject.put("COBRADO_POR", personaCobraCompra);
                                    jsonObject.put("ENTREGADO_POR", personaEntregaCompra);
                                    jsonObject.put("TOTAL_SACOS", tSacosCompra);
                                    jsonObject.put("TOTAL_PESOS", tPesoCompra);
                                    jsonObject.put("TOTAL_PESOS_MOD", tPesoModCompra);
                                    jsonObject.put("MONTO_TOTAL", montoTotalCompra);
                                    jsonObject.put("SERIE", serieCompra);
                                    jsonObject.put("NUM_DOC", numDocCompra);
                                    jsonObject.put("OBS", obsCompra);
                                    jsonObject.put("PLACA", placaCompra);
                                    jsonObject.put("FECHA", fechaCompra);
                                    jsonObject.put("SOLI_ADELANTO", soliAdelantoCompra);
                                    jsonObject.put("ADELANTO", adelantCompra);
                                    jsonObject.put("LOTE_PROVEEDOR", loteProveedor);
                                    jsonObject.put("GUIA_REMISION", guiaRemision);

                                    String val = null;
                                    Cursor resj = myDB.mostrarListaDetalleCompraPesos(txt_idCompra.getText().toString());
                                    if(resj!= null && resj.getCount()>0){
                                        itemNomb.clear();
                                        for(int a=0;a<100;a++){
                                            while (resj.moveToNext()){
                                                if(val==null){
                                                    val=resj.getString(3);}
                                                itemNomb.add(new ItemDatosJSON(resj.getString(1),resj.getString(2),resj.getString(3),resj.getString(11),resj.getString(4),resj.getString(5),resj.getString(6),resj.getString(7),resj.getString(8),resj.getString(9),resj.getString(12)));
                                            }}}
                                    for (ItemDatosJSON item: itemNomb){
                                        list_fecha.add(item.getFECHA_HORA());
                                        list_numero.add(item.getNUM_ITEM());
                                        list_produc.add(item.getPRODUCTO());
                                        list_lote.add(item.getLOTE());
                                        list_loteProveedor.add(item.getLOTE_PROVEEDOR());
                                        list_p_real.add(item.getPESO_REAL());
                                        list_p_modi.add(item.getPESO_MODIFICADO());
                                        list_p_unit.add(item.getPRECIO_UNIT());
                                        list_p_total.add(item.getPRECIO_TOTAL());
                                        list_obs.add(item.getOBSERVACION());
                                        list_obsG.add(item.getOBSERVACION_GENERAL());
                                    } //CARGO LOS DATOS DE LOS ITEM EN ARRAY JSON
                                    Cursor rsEstiva = myDB.listaEstivaRecyclerview(txt_idCompra.getText().toString());
                                    if(rsEstiva!= null && rsEstiva.getCount()>0){
                                        list_estiva_nombre.clear();
                                        list_estiva_tipo.clear();
                                        while (rsEstiva.moveToNext()){
                                            list_estiva_nombre.add(rsEstiva.getString(0));
                                            list_estiva_tipo.add(rsEstiva.getString(2));
                                        }
                                    }
                                    Cursor rs = myDB.listaSerieNumEncabezado_RP(txt_idCompra.getText().toString());
                                    if(rs!= null && rs.getCount()>0){
                                        list_docRP_serie.clear();
                                        list_docRP_numer.clear();
                                        while (rs.moveToNext()){
                                            list_docRP_serie.add(rs.getString(1));
                                            list_docRP_numer.add(rs.getString(2));
                                        }
                                    }
                                    Cursor res3 = myDB.mostrarListaDetalleAlmacenProduc(txt_idCompra.getText().toString());
                                    if(res3!= null && res3.getCount()>0){
                                        list_alma_codAlma.clear();
                                        list_alma_codAlmaUbica.clear();
                                        list_alma_descrip.clear();
                                        list_alma_lote.clear();
                                        list_alma_stock.clear();
                                        list_alma_preCompra.clear();
                                        list_alma_pesoAcumula.clear();
                                        while (res3.moveToNext()){
                                            list_alma_codAlma.add(codAlmacen);
                                            list_alma_codAlmaUbica.add(codAlmacenUbi);
                                            list_alma_descrip.add(res3.getString(1));
                                            list_alma_lote  .add(res3.getString(5));
                                            list_alma_stock.add(res3.getString(2));
                                            list_alma_preCompra.add(res3.getString(4));
                                            list_alma_pesoAcumula.add(res3.getString(3));
                                        }
                                    }

                                for(int i = 0; i < list_fecha.size(); i++)
                                    array_fecha.put(list_fecha.get(i));
                                for(int i = 0; i < list_numero.size(); i++)
                                    array_numero.put(list_numero.get(i));
                                for(int i = 0; i < list_produc.size(); i++)
                                    array_produc.put(list_produc.get(i));
                                for(int i = 0; i < list_lote.size(); i++)
                                    array_lote.put(list_lote.get(i));
                                for(int i = 0; i < list_loteProveedor.size(); i++)
                                    array_loteProveedor.put(list_loteProveedor.get(i));
                                for(int i = 0; i < list_p_real.size(); i++)
                                    array_p_real.put(list_p_real.get(i));
                                for(int i = 0; i < list_p_modi.size(); i++)
                                    array_p_modi.put(list_p_modi.get(i));
                                for(int i = 0; i < list_p_unit.size(); i++)
                                    array_p_unit.put(list_p_unit.get(i));
                                for(int i = 0; i < list_p_total.size(); i++)
                                    array_p_total.put(list_p_total.get(i));
                                for(int i = 0; i < list_obs.size(); i++)
                                    array_obs.put(list_obs.get(i));
                                for(int i = 0; i < list_obsG.size(); i++)
                                    array_obsG.put(list_obsG.get(i));
                                for(int i = 0; i < list_estiva_nombre.size(); i++)
                                    array_estiva_nom.put(list_estiva_nombre.get(i));
                                for(int i = 0; i < list_estiva_tipo.size(); i++)
                                    array_estiva_tipo.put(list_estiva_tipo.get(i));
                                for(int i = 0; i < list_docRP_serie.size(); i++)
                                    array_docRP_serie.put(list_docRP_serie.get(i));
                                for(int i = 0; i < list_docRP_numer.size(); i++)
                                    array_docRP_numer.put(list_docRP_numer.get(i));
                                for(int i = 0; i < list_alma_codAlma.size(); i++)
                                    array_alma_codAlma.put(list_alma_codAlma.get(i));
                                for(int i = 0; i < list_alma_codAlmaUbica.size(); i++)
                                    array_alma_codAlmaUbi.put(list_alma_codAlmaUbica.get(i));
                                for(int i = 0; i < list_alma_descrip.size(); i++)
                                    array_alma_descrip.put(list_alma_descrip.get(i));
                                for(int i = 0; i < list_alma_lote.size(); i++)
                                    array_alma_lote.put(list_alma_lote.get(i));
                                for(int i = 0; i < list_alma_stock.size(); i++)
                                    array_alma_stock.put(list_alma_stock.get(i));
                                for(int i = 0; i < list_alma_preCompra.size(); i++)
                                    array_alma_preCompra.put(list_alma_preCompra.get(i));
                                for(int i = 0; i < list_alma_pesoAcumula.size(); i++) {
                                    array_alma_pesoAcumu.put(list_alma_pesoAcumula.get(i));
                                }
                                    jsonObject.remove("PRODUCTO_FECHA");
                                    jsonObject.remove("PRODUCTO_NUMERO");
                                    jsonObject.remove("PRODUCTO_PRODUCTO");
                                    jsonObject.remove("PRODUCTO_LOTE");
                                    jsonObject.remove("PRODUCTO_P_REAL");
                                    jsonObject.remove("PRODUCTO_P_MODI");
                                    jsonObject.remove("PRODUCTO_P_UNIT");
                                    jsonObject.remove("PRODUCTO_P_TOTAL");
                                    jsonObject.remove("PRODUCTO_OBS");
                                    jsonObject.remove("PRODUCTO_OBSG");
                                    jsonObject.remove("ESTIVA_NOMBRE");
                                    jsonObject.remove("ESTIVA_TIPO");
                                    jsonObject.remove("DOCRP_SERIE");
                                    jsonObject.remove("DOCRP_NUMER");
                                    jsonObject.remove("ALMACEN_CODALMA");
                                    jsonObject.remove("ALMACEN_CODALMAUBI");
                                    jsonObject.remove("ALMACEN_DESCRIP");
                                    jsonObject.remove("ALMACEN_LOTE");
                                    jsonObject.remove("ALMACEN_STOCK");
                                    jsonObject.remove("ALMACEN_PRECOMPRA");
                                    jsonObject.remove("ALMACEN_PESOACUMU");

                                    jsonObject.put("PRODUCTO_FECHA", array_fecha);
                                    jsonObject.put("PRODUCTO_NUMERO", array_numero);
                                    jsonObject.put("PRODUCTO_PRODUCTO", array_produc);
                                    jsonObject.put("PRODUCTO_LOTE", array_lote);
                                    jsonObject.put("PRODUCTO_LOTE_PROVEEDOR", array_loteProveedor);
                                    jsonObject.put("PRODUCTO_P_REAL", array_p_real);
                                    jsonObject.put("PRODUCTO_P_MODI", array_p_modi);
                                    jsonObject.put("PRODUCTO_P_UNIT", array_p_unit);
                                    jsonObject.put("PRODUCTO_P_TOTAL", array_p_total);
                                    jsonObject.put("PRODUCTO_OBS", array_obs);
                                    jsonObject.put("PRODUCTO_OBSG", array_obsG);
                                    jsonObject.put("ESTIVA_NOMBRE", array_estiva_nom);
                                    jsonObject.put("ESTIVA_TIPO", array_estiva_tipo);
                                    jsonObject.put("DOCRP_SERIE", array_docRP_serie);
                                    jsonObject.put("DOCRP_NUMER", array_docRP_numer);
                                    jsonObject.put("ALMACEN_CODALMA", array_alma_codAlma);
                                    jsonObject.put("ALMACEN_CODALMAUBI", array_alma_codAlmaUbi);
                                    jsonObject.put("ALMACEN_DESCRIP", array_alma_descrip);
                                    jsonObject.put("ALMACEN_LOTE", array_alma_lote);
                                    jsonObject.put("ALMACEN_STOCK", array_alma_stock);
                                    jsonObject.put("ALMACEN_PRECOMPRA", array_alma_preCompra);
                                    jsonObject.put("ALMACEN_PESOACUMU", array_alma_pesoAcumu);

                                file = new File(context.getFilesDir(),serieCompra + "-" + numDocCompra +"-"+ time.format(myFormatObj) +".json");
                                FileWriter fileWriter = new FileWriter(file);
                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                bufferedWriter.write(jsonObject.toString(2));
                                bufferedWriter.close();

                                archivFile = File_Byte.getBytesFromFile(file);
                                z = enviarJsonAPI.enviarJSON(archivFile,serieCompra + "-" + numDocCompra +"-"+ time.format(myFormatObj));
                                isSuccess = true;
                                }else
                                    z="ERROR DE CONEXION AL SERVIDOR";
                            }catch (Exception e){
                                z= "error de tread " +e.getMessage();
                            }
                            return z;
                        }
                        @Override
                        protected void onPostExecute(String r){
                            progressBar.dismiss();
                            if(isSuccess){
//                                Toast.makeText(itemView.getContext(),dniProv+" / "+nombreCompleto+" / "+telefonoCompra+" / "+comuniCompra+" / "+distriCompra+" / "+
//                                        direccion+" / "+referencia+" / "+tipo+" / "+parcelaCompra,Toast.LENGTH_LONG).show();
                                Toast.makeText(itemView.getContext(),r ,Toast.LENGTH_LONG).show();
                                btn_sync.setVisibility(View.INVISIBLE);
                            }else{
                                Toast.makeText(itemView.getContext(),r,Toast.LENGTH_LONG).show();
                                btn_sync.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    ValidarInsert validarInsert = new ValidarInsert();
                    validarInsert.execute("");
                }
            });
        }
        public ViewHolder LinkAdapter(ADAPTADOR_NCOMPRA adaptador_ncompra) {
            this.adaptador_ncompra=adaptador_ncompra;
            return this;
        }
    }

}



