package w.kipu.kipu.formtocompra.MostrarListaProductos;

import static java.lang.Double.parseDouble;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemMostrarListaProducto;
import w.kipu.kipu.ITEM.ItemOneSpinner;
import w.kipu.kipu.R;
//import w.wira.wiraccocha.login.Produccion.DialogoFormatoControl.AdapterPuesto;

public class AdapterUpdateListaProducto extends RecyclerView.Adapter<AdapterUpdateListaProducto.ViewHolder> {

    private View.OnClickListener listener;
    public List<ItemMostrarListaProducto> ncomprasLista;
    public List<ItemMostrarListaProducto>ncomprasListaFull;
    String codLote="";
    AdapterSQLite myDB;


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView producto,txt_IDComDetalleCompra,txt_SerieDetalleCompra;
        TextView pesosNeto,pesoRedondo;
        TextView sacos,txt_totalPrecio;
        Context context;
        RecyclerView recyclerView;
        EditText txt_precioXKg,txt_observa,txt_loteWiraccocha,txt_loteProveedor,txt_ubica;//,txt_totalPesoObserva,txtcodLote,txtanioLote,txt_asp_vistaPreviaLote;
        Spinner spAlmacen,spUbicacion;
        Switch toggleButton;
        CONEXION conexion = new CONEXION();
        Connection con;
        AdapterSQLite myDB;
        String AlmacenNom,UbicacionAlmacen,ObtenerId,idAlmacen,idUbicacion,codAlmacen,codAlmaUbicacion;
        String idRP_selet,pesoLote,estadoLote,obtnerValor;
        ImageButton btn_consultar_lote;
        Connection conAll=null;
        CONEXION conexionLocal = new CONEXION();

        public ViewHolder(View itemView) {
            super(itemView);
            this.producto =  itemView.findViewById(R.id.txt_tituloListaProduct);
            this.pesosNeto =  itemView.findViewById(R.id.txt_pesolistaNeto);
            this.pesoRedondo = itemView.findViewById(R.id.txt_pesolistaRedondo);
            this.sacos =  itemView.findViewById(R.id.txt_sacoListaProducto);
            this.recyclerView =itemView.findViewById(R.id.RecyclerProductosCompra);
            this.txt_precioXKg =  itemView.findViewById(R.id.txt_precioKG);
            this.txt_totalPrecio= itemView.findViewById(R.id.txt_precioTotalSacos);
            this.txt_observa =  itemView.findViewById(R.id.txt_observacionPrecioProd);
            this.txt_loteWiraccocha =  itemView.findViewById(R.id.txt_asp_loteWiraccocha);
            this.txt_loteProveedor =  itemView.findViewById(R.id.txt_asp_loteProveedor);
            this.txt_ubica =  itemView.findViewById(R.id.txt_ubicaci);
            this.spAlmacen =  itemView.findViewById(R.id.sp_almacenLista);
            this.spUbicacion= itemView.findViewById(R.id.sp_almacenUbicacion);
//            this.txt_totalPesoObserva= itemView.findViewById(R.id.txt_totalPesoObserva);
//            this.txt_asp_vistaPreviaLote= itemView.findViewById(R.id.txt_asp_vistaPreviaLote);
            this.toggleButton = itemView.findViewById(R.id.btn_togleListaProducto);
            this.txt_IDComDetalleCompra = itemView.findViewById(R.id.txt_IDComDetalleCompra);
            this.txt_SerieDetalleCompra = itemView.findViewById(R.id.txt_SerieDetalleCompra);
            this.btn_consultar_lote = itemView.findViewById(R.id.btn_revisar_lote);
//            this.txtanioLote = itemView.findViewById(R.id.txt_anioLoteProd);
            this.context = itemView.getContext();
            this.myDB = new AdapterSQLite(itemView.getContext());
        }

        void listenersclock() {
            this.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                    //formatoCompraActivity.mostrarID_COMPRA();
                    final String idcom = txt_IDComDetalleCompra.getText().toString();
                    final String serie = txt_SerieDetalleCompra.getText().toString();
                    final String prod = producto.getText().toString();
                    final String pUnit = txt_precioXKg.getText().toString();
                    final String observa = txt_observa.getText().toString();
                    final String estado = "compra";
                    final String descp = "";
                    final String loteWiraccocha = txt_loteWiraccocha.getText().toString();
                    final String loteProveedor = txt_loteProveedor.getText().toString();
                    final String stock = sacos.getText().toString();
                    //final String fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                    final String precioCompra = txt_totalPrecio.getText().toString();
                    final String totalPeso = pesosNeto.getText().toString();
                    final String estado_Calidad = "";
                    if(b){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewHolder.this.itemView.getContext());
                        builder1.setMessage("SEGURO QUE DESEA GUARDAR LOS CAMBIOS!!");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "SI",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            if(loteWiraccocha.equals("") || loteProveedor.equals("") ){
                                                Toast.makeText(ViewHolder.this.itemView.getContext(),"FALTA INGRESAR LOTE",Toast.LENGTH_SHORT).show();
                                                toggleButton.setEnabled(true);
                                            }else{
                                                InsertarAlmacen_Ubicacion(codAlmacen,codAlmaUbicacion,idcom,prod,loteWiraccocha,stock,precioCompra,totalPeso,estado_Calidad);
                                                Boolean result = myDB.updateAsigPrecioDetaCompra(idcom,prod,pUnit,estado,loteWiraccocha,observa,loteProveedor);
                                                if(result == true){
                                                    Boolean rs = myDB.updateCompraTotal(idcom);
                                                    if(rs == true){
                                                        Toast.makeText(ViewHolder.this.itemView.getContext(),"REGISTRADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                                                        toggleButton.setEnabled(false);
                                                    }
                                                }else
                                                    Toast.makeText(ViewHolder.this.itemView.getContext(),"ERROR AL REGISTRAR",Toast.LENGTH_SHORT).show();
                                                InsertarPeso(idcom,prod);
                                            }
                                        }catch (Exception e){
                                            toggleButton.setChecked(false);
                                            e.printStackTrace();
                                            Toast.makeText(ViewHolder.this.itemView.getContext(),"FALTA INGRESAR PRECIO",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        toggleButton.setChecked(false);
                                        //toggleButton.isEnabled();
                                        dialog.cancel();
                                    }
                                });

                        builder1.create().show();
                    }
                }
            });
            this.txt_precioXKg.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(txt_precioXKg.getText().length()==0){
                    }else{
                        String pUnit = ViewHolder.this .txt_precioXKg.getText().toString();
                        String pesoRed = ViewHolder.this.pesoRedondo.getText().toString();
                        Double res,pU;
                        double pCan = parseDouble(pesoRed);
                        pU = parseDouble(pUnit);
                        res = pU * pCan;
                        txt_totalPrecio.setText(String.valueOf(res));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(txt_precioXKg.getText().length()==0){
                        txt_totalPrecio.setText("0");
                    }else if(txt_precioXKg.getText().equals("")){
                        Toast.makeText(ViewHolder.this.itemView.getContext(),"vacio ",Toast.LENGTH_LONG).show();
                    }else{
                        // ejecutar accion despues de editar edittext
                    }
                }
            });
            spAlmacen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Object item = adapterView.getItemAtPosition(i);
                    AlmacenNom = ((ItemOneSpinner) item).getTexto();
                    codAlmacen = ((ItemOneSpinner) item).getCod();
                    ListaAlmacenUbicacion(codAlmacen);
                    //Toast.makeText(ViewHolder.this.itemView.getContext(),idAlmacen,Toast.LENGTH_LONG).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spUbicacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Object item = adapterView.getItemAtPosition(i);
                    UbicacionAlmacen = ((ItemOneSpinner) item).getTexto();
                    codAlmaUbicacion = ((ItemOneSpinner) item).getCod();
                    //Toast.makeText(ViewHolder.this.itemView.getContext(),idUbicacion,Toast.LENGTH_LONG).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            this.btn_consultar_lote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String wira=txt_loteWiraccocha.getText().toString();
                        conAll = conexionLocal.ConnectionDB_Local();
                        PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_COMPRA_CONSULTAR_STOCK_LOTE (?)}");
                        pst1.setString(1,wira);
                        ResultSet rs = pst1.executeQuery();
                        if (rs.next()){
                            String cant = rs.getString("PESO_REAL");
                            double doubleValue = Double.parseDouble(cant);
                            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                            String formattedValue = decimalFormat.format(doubleValue);
                            Toast.makeText(context, formattedValue,Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(context,"NO HAY RESULTADO",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(context,"SIN CONEXION A INTERNET",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        public void InsertarAlmacen_Ubicacion(String idAlmacen,String idAlmacenUbica,String idCom,String descrip,String lote,String stock,String precioCompra,String TotalPeso,String esta_cali) throws SQLException
        {
            try {
                Boolean result = myDB.insertAlmacenProducto(null,idAlmacen,idAlmacenUbica,idCom,descrip,lote,stock,precioCompra,TotalPeso,esta_cali);
                if(result == true){
                    //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                //Toast.makeText(getApplication(),"Error Almacén"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        public void ListaAlmacen(){
            try {
                List<ItemOneSpinner> data= new ArrayList<>();
                data.clear();
                Cursor res = myDB.selectAlmacen();
                if(res!= null && res.getCount()>0){
                    while (res.moveToNext()){
                        data.add(new ItemOneSpinner( res.getString(3),res.getString(2)));
                    }
                    spAlmacen.setAdapter(new AdapterOneSpinner(ViewHolder.this.itemView.getContext(),R.layout.item_spinner_one,data));
                }else {
                    // Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        public void ListaAlmacenUbicacion(String cod){
            try {
                List<ItemOneSpinner> data= new ArrayList<>();
                data.clear();
                Cursor res = myDB.selectAlmacenUbicacion(cod);
                if(res!= null && res.getCount()>0){
                    while (res.moveToNext()){
                        data.add(new ItemOneSpinner( res.getString(4),res.getString(2)));
                    }
                    spUbicacion.setAdapter(new AdapterOneSpinner(ViewHolder.this.itemView.getContext(),R.layout.item_spinner_one,data));
                }else {
                    // Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        public void VerPesoLote(String lot,String idCo,String prod){
            try {
                con = conexion.ConnectionDB_Local();
                PreparedStatement pst = con.prepareStatement("{CALL SPAPP_SUMPES_DETCOM (?,?,?)}");
                pst.setString(1,lot);
                pst.setString(2,idCo);
                pst.setString(3,prod);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    pesoLote = rs.getString("PESO");
                    estadoLote = rs.getString("ESTADOLOTE");
                }else
                    Toast.makeText(ViewHolder.this.itemView.getContext(),"LOTE SIN PESO",Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(ViewHolder.this.itemView.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        @SuppressLint("Range")
        public void InsertarPeso(String idCompra, String prod){
            SQLiteDatabase db = myDB.getReadableDatabase();
            Cursor result = db.rawQuery("select MAX(PRECIO_UNIT) as PRECIO  from DETALLE_COMPRA WHERE ID_COMPRA = ? AND PRODUCTO = ?",new String[]{idCompra,prod} );
            if(result.moveToFirst()){
                obtnerValor = result.getString(result.getColumnIndex("PRECIO"));
                //Toast.makeText(itemView.getContext(),"EL VALOR ES: "+obtnerValor,Toast.LENGTH_SHORT).show();
            }else{
                obtnerValor = "NO HAY VALOR";
            }
            result.close();
        }
    }
    public Context context;
    public AdapterUpdateListaProducto(List<ItemMostrarListaProducto> comidaLista, Context context) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistaproducto, parent, false);

            ViewHolder hola= new ViewHolder(view);
            hola.ListaAlmacen();
            hola.ListaAlmacenUbicacion("1");
            myDB = new AdapterSQLite(view.getContext());
        return new ViewHolder(view) {
        };
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
    SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MM", Locale.getDefault());
    Date date = new Date();

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.producto.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getProducto());
        holder.pesosNeto.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getPesoNeto());
        holder.sacos.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getSaco());
        holder.pesoRedondo.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getPesoRedondo());
        holder.txt_precioXKg.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getPrecio());
        holder.txt_IDComDetalleCompra.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getIdComp());
        holder.txt_SerieDetalleCompra.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getSerieComp());
        holder.txt_loteWiraccocha.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getLoteWiraccocha());
        holder.txt_loteProveedor.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getLoteProveedor());

        String nombProduct = holder.producto.getText().toString();
        SEALL_CODVARPROD(nombProduct);
        String year = dateFormat.format(date);
        String month = dateFormatMonth.format(date);
        String loteWira = holder.txt_loteWiraccocha.getText().toString();
        //holder.txtanioLote.setText(month+year.substring(2,4));
        if (loteWira.equals(""))
            holder.txt_loteWiraccocha.setText(codLote +"-");
        holder.listenersclock();
    }
    public void SEALL_CODVARPROD(String descProduc){
        try {
            Cursor resCodVarProduc = myDB.mostrarListaCodigoVariedadProducto(descProduc);
            if(resCodVarProduc!= null && resCodVarProduc.getCount()>0){
                if (resCodVarProduc.moveToNext())
                    codLote = resCodVarProduc.getString(0);
            }else
                codLote="WDP";
        }catch (Exception e){
        }
    }
    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}