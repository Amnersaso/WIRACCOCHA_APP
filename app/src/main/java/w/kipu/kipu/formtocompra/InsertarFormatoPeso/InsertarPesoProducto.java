package w.kipu.kipu.formtocompra.InsertarFormatoPeso;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import dmax.dialog.SpotsDialog;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemFormato;
import w.kipu.kipu.ITEM.ItemLiProduc;
import w.kipu.kipu.ITEM.ItemOBS_MP;
import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.AdapterFormato;
import w.kipu.kipu.login.Produccion.AdapterLiProduc;

public class InsertarPesoProducto extends AppCompatDialogFragment implements View.OnKeyListener {
    Spinner sp_product,sp_varied,sp_observacionMP;
    EditText pesoNeto,pesoManual;
    CheckBox aSwitch;
    CheckBox ch_pesoManual,ch_pesoMenor;
    public Integer n=0;
    Button btn_guardar,btn_finalizar;
    LinearLayout linearLayoutOBS,linearInserPesoRP;
    android.app.AlertDialog progressBar;
    String id_compra,product,codProduct,obs;
    ListView listView;
    TextInputEditText txt_observa;
    public String obtenerProducto;
    public String obtenerVariedad;
    AdapterSQLite myDB;
    String idRegistroPeso,serieSelectRP,idCompraSelectRP,validarRegMenuForma,idUsuADAPTERNC,obtSerie_RP,obtNumdoc_RP,obtOBS_MP,idUsuALL,idCompraNCompra,numItem,prodVarie,idCompFormaCompraActivy,idCompraSQLite;
    ArrayList<String> dataPesoEnd = new ArrayList<>();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(getContext()).setCancelable(false).build();
        final View view = inflater.inflate(R.layout.activity_insertar_peso_producto,null);
        builder.setView(view);
        setCancelable(false);
        myDB = new AdapterSQLite(getContext());
        n=n+1;
        sp_product= view.findViewById(R.id.sp_producto);
        sp_varied= view.findViewById(R.id.sp_variedad);
        sp_observacionMP = view.findViewById(R.id.sp_observacion_mp);
        pesoNeto=view.findViewById(R.id.txt_pesoneto);
        listView=view.findViewById(R.id.listviewPesoUltimo);
        pesoManual= view.findViewById(R.id.txt_pesomanual);
        ch_pesoManual= view.findViewById(R.id.ch_pesomanual);
        btn_guardar= view.findViewById(R.id.btn_guardarPeso);
        btn_finalizar= view.findViewById(R.id.btn_finalizarPeso);
        txt_observa = view.findViewById(R.id.txt_observaOpcinalPeso);
        linearLayoutOBS = view.findViewById(R.id.linearLayout_OBSInsertPeso);
        linearInserPesoRP=view.findViewById(R.id.linearInsertPesoRP);
        aSwitch = view.findViewById(R.id.btn_toglePesoSacoBulk);
        ch_pesoMenor = view.findViewById(R.id.btn_toglePesoMenorSacos);

        //FALTA AGREGAR ESTA LINEA DE COMANDO PARA PESO REDONDEADO.

        Intent prueba = getActivity().getIntent();
        Bundle b = prueba.getExtras();
        Bundle bundle = getArguments();
        if(b!=null){
            serieSelectRP = (String) b.get("idusuarioMenuFotma");
            idUsuADAPTERNC = (String) b.get("idUsuarioADAPTER_NCOMPRA");
            idCompraNCompra =(String)b.get("idCompraNCompra");
            //idCompFormaCompraActivy = (String)b.get("idCompraFormaCompraActiv");
        }

        idCompraSQLite = bundle.getString("TEXT","");
        //Toast.makeText(getActivity(),serieSelectRP+" / "+ idCompraSQLite,Toast.LENGTH_LONG).show();
        pesoNeto.setOnKeyListener(this);
        if(idUsuADAPTERNC != null){
            idUsuALL = idUsuADAPTERNC;
        }else{
            idUsuALL=serieSelectRP;
        }
        if(idUsuALL.equals("42")){
            ViewGroup.LayoutParams params = linearInserPesoRP.getLayoutParams();
            params.height = 250;
            params.width = 1000;
            linearInserPesoRP.setLayoutParams(params);
        }else{
            ViewGroup.LayoutParams params = linearInserPesoRP.getLayoutParams();
            params.height = 0;
            params.width = 1000;
            linearInserPesoRP.setLayoutParams(params);
        }

        sp_observacionMP.setAdapter(new AdapterOBS_MP(getContext(),R.layout.item_formato_spinner,SEALL_TBOBS_SQLITE()));
        sp_product.setAdapter(new AdapterLiProduc(getContext(),R.layout.item_li_produc_spinner,listActivi()));
        sp_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                codProduct = ((ItemLiProduc) item).getCod();
                obtenerProducto = ((ItemLiProduc) item).getDesc();
                //Toast.makeText(getApplicationContext(),desFor,Toast.LENGTH_LONG).show();
                sp_varied.setAdapter(new AdapterFormato(getContext(),R.layout.item_formato_spinner,SECODPROD_COM_VARI(codProduct)));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sp_varied.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int getid = adapterView.getCount();
                Object item = adapterView.getItemAtPosition(i);
                //Toast.makeText(getContext(), String.valueOf(getid),Toast.LENGTH_SHORT).show();
                //if(getid>1){
                    sp_varied.setVisibility(View.VISIBLE);
                    obtenerVariedad = ((ItemFormato) item).getDes();
                prodVarie = obtenerProducto+" "+ obtenerVariedad;
                numeroItemDetComp(idCompraSQLite,prodVarie);
                mostrarUltimosPesos(idCompraSQLite,prodVarie);
                pesoNeto.setHint("Agregue Peso " + numItem);
                pesoManual.setHint("Agregue Peso " + numItem);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sp_observacionMP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                obtOBS_MP = ((ItemOBS_MP) item).getDescrip();
                //Toast.makeText(getContext(),obtOBS_MP,Toast.LENGTH_LONG).show();

                if (obtOBS_MP.equals("Otro")){
                    ViewGroup.LayoutParams params = linearLayoutOBS.getLayoutParams();
                    params.height = 230;
                    params.width = 1000;
                    linearLayoutOBS.setLayoutParams(params);
                }else{
                    ViewGroup.LayoutParams params = linearLayoutOBS.getLayoutParams();
                    params.height = 90;
                    params.width = 1000;
                    linearLayoutOBS.setLayoutParams(params);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ch_pesoManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    pesoManual.setEnabled(true);
                }else{
                    pesoManual.setEnabled(false);
                }
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String observac = txt_observa.getText().toString();
                String netoPeso = pesoNeto.getText().toString();
                String modifiPeso = pesoManual.getText().toString();
                String peNet,peMod;
                double pN,pM;
                try {
                    if(netoPeso.trim().isEmpty()){
                        Toast.makeText(getActivity(), "INGRESE PESO",Toast.LENGTH_SHORT).show();
                    }else{
                        if (txt_observa.getText().length()==0){
                            observac = obtOBS_MP;
                        }
                        if(idUsuALL.equals("42")){
                            peNet = netoPeso;
                            peMod = modifiPeso;
                        }else {
                            peNet = netoPeso;
                            peMod = netoPeso;
                        }
                        pN=Double.parseDouble(netoPeso);
                        pM=Double.parseDouble(modifiPeso);
                        if(idUsuALL.equals("42")){
                            if(ch_pesoMenor.isChecked()){
                                if(pM<40 || pN<40){
                                    Toast toast =  Toast.makeText(getActivity(), "PESO MÍNIMO ES DE: 40",Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP,0,0);
                                    toast.show();
                                }else{
                                    if(pM>pN){
                                        Toast toast =  Toast.makeText(getActivity(), "NOSE PUDO REGISTRAR, PESO REDONDEADO ES MAYOR AL PESO NETO ",Toast.LENGTH_LONG);
//                                        View viewToas = toast.getView();
//                                        viewToas.setPadding(20, 30, 20, 30);
//                                        viewToas.setBackgroundResource(R.color.colorError);
//                                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
//                                        toastMessage.setTextColor(Color.WHITE);
//                                        toastMessage.setTextSize(20);
                                        toast.setGravity(Gravity.TOP,0,0);
                                        toast.show();
                                    }else{
                                        Boolean result = myDB.insertDetalleCompra(null,idCompraSQLite,numItem,prodVarie,null,null,peNet,peMod,null,
                                                null,observac,null,null,null);
                                        if(result == true){
                                            Toast toast = Toast.makeText(getActivity(),"PESO REGISTRADO",Toast.LENGTH_SHORT);
//                                            View viewToas = toast.getView();
//                                            viewToas.setPadding(20, 30, 20, 30);
//                                            viewToas.setBackgroundResource(R.color.colorSucces);
//                                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
//                                            toastMessage.setTextColor(Color.rgb(45,87,44));
//                                            toastMessage.setTextSize(18);
                                            toast.setGravity(Gravity.CENTER,0,0);
                                            toast.show();
                                            mostrarUltimosPesos(idCompraSQLite,prodVarie);
                                        }
                                        //insertarDETALLE_COMPRA(idAllCompra,numItem,prodVarie,netoPeso, modifiPeso,observac);
                                        numeroItemDetComp(idCompraSQLite,prodVarie);
                                        pesoNeto.setText("");
                                        pesoNeto.setHint("Agregue Peso " + numItem);
                                        pesoManual.setText("");
                                        pesoManual.setHint("Agregue Peso " + numItem);
                                        txt_observa.setText("");
                                        txt_observa.setHint("Observación opcional");
                                    }
                                }
                            }else {

                                if(pM>pN){
                                    Toast.makeText(getActivity(), "NOSE PUDO REGISTRAR, PESO REDONDEADO ES MAYOR AL PESO NETO ",Toast.LENGTH_LONG).show();
                                }else{
                                    Boolean result = myDB.insertDetalleCompra(null,idCompraSQLite,numItem,prodVarie,null,null,peNet,peMod,null,
                                            null,observac,null,null,null);
                                    if(result == true){
                                        Toast toast = Toast.makeText(getActivity(),"PESO REGISTRADO",Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP,0,0);
                                        toast.show();
                                        mostrarUltimosPesos(idCompraSQLite,prodVarie);
                                    }
                                    //insertarDETALLE_COMPRA(idAllCompra,numItem,prodVarie,netoPeso, modifiPeso,observac);
                                    numeroItemDetComp(idCompraSQLite,prodVarie);
                                    pesoNeto.setText("");
                                    pesoNeto.setHint("Agregue Peso " + numItem);
                                    pesoManual.setText("");
                                    pesoManual.setHint("Agregue Peso " + numItem);
                                    txt_observa.setText("");
                                    txt_observa.setHint("Observación opcional");
                                }
                            }
                        }else {
                            Boolean result = myDB.insertDetalleCompra(null,idCompraSQLite,numItem,prodVarie,null,null,peNet,peMod,null,
                                    null,observac,null,null,null);
                            if(result == true){
                                Toast toast = Toast.makeText(getActivity(),"PESO REGISTRADO",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP,0,0);
                                toast.show();
                                mostrarUltimosPesos(idCompraSQLite,prodVarie);
                            }
                            numeroItemDetComp(idCompraSQLite,prodVarie);
                            pesoNeto.setText("");
                            pesoNeto.setHint("Agregue Peso " + numItem);
                            pesoManual.setText("");
                            pesoManual.setHint("Agregue Peso " + numItem);
                            txt_observa.setText("");
                            txt_observa.setHint("Observación opcional");
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "ERROR "+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Finalizado",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
        return builder.create();
    }

    public void numeroItemDetComp(String id,String pv){
        try {
            Cursor res = myDB.selectCountNumItem(id,pv);
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    numItem = res.getString(0);
                }
            }else {
                Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void mostrarUltimosPesos(String id,String pv){
        try {
            dataPesoEnd.clear();
            Cursor res = myDB.selectMostrarUltimoPeso(id,pv);
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    dataPesoEnd.add(res.getString(0)+" - "+res.getString(1));
                }
                listView.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, dataPesoEnd));
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"ERROR ULTIMO PESO "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if((keyEvent.getAction()==KeyEvent.ACTION_UP)) {
            try {
                if(ch_pesoManual.isChecked()){
                }else{
                    pesoManual.setEnabled(false);}
                int number = Integer.parseInt(pesoNeto.getText().toString());
                if(aSwitch.isChecked()){
                    if(number > 180){
                        Toast.makeText(getContext(),"PESO EXEDIDO. LÍMITE ES DE: 180",Toast.LENGTH_LONG).show();
                        btn_guardar.setEnabled(false);
                    }else{
                        Long x = (long) number ;
                        pesoManual.setText(x.toString());
                        btn_guardar.setEnabled(true);
                    }
                }else{
                    Long x = (long) number ;
                    pesoManual.setText(x.toString());
                    btn_guardar.setEnabled(true);
                }
            } catch(Exception e) {
                //Toast.makeText(getContext(),"ERROR: el valor de tipo String contiene caracteres no numéricos",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
    public List<ItemLiProduc> listActivi(){
        ArrayList<ItemLiProduc>  ListaProductodat = new ArrayList<>();
        ListaProductodat.clear();
        try {
            Cursor res = myDB.selectProductCompra();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    ListaProductodat.add(new ItemLiProduc(res.getString(2),res.getString(3),res.getString(1)));
                }
            }else {
                Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return ListaProductodat;
    }
    public List<ItemFormato> SECODPROD_COM_VARI(String codProduct){
        ArrayList<ItemFormato>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            Cursor res = myDB.selectProductVariedad(codProduct);
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    dataListaPuesto.add(new ItemFormato(res.getString(2),res.getString(3),res.getString(1)));
                }
            }else {
                Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return dataListaPuesto;
    }
    public List<ItemOBS_MP> SEALL_TBOBS_SQLITE(){
        ArrayList<ItemOBS_MP>  dataListaObs= new ArrayList<>();
        dataListaObs.clear();
        try {
            Cursor res = myDB.selectObs();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    dataListaObs.add(new ItemOBS_MP(res.getString(0),res.getString(1)));
                }
            }else {
                Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return dataListaObs;
    }


}
