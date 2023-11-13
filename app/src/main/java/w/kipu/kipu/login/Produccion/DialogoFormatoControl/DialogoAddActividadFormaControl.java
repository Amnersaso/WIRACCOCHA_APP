package w.kipu.kipu.login.Produccion.DialogoFormatoControl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.ItemDetaActivi;
import w.kipu.kipu.ITEM.ItemLiProduc;
import w.kipu.kipu.ITEM.ItemMPLista;
import w.kipu.kipu.ITEM.ItemTiProActividad;
import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.AdapterLiProduc;
import w.kipu.kipu.login.Produccion.MenuControlFormato.AdapterMPList;

public class DialogoAddActividadFormaControl extends DialogFragment implements View.OnKeyListener {

    Spinner sp_tipro,sp_pro,sp_actividad,sp_detAct,sp_presentacion;
    CONEXION conexion = new CONEXION();
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    String NSerie,serie,coddetAct,desDetAct,codActivi,desActivi,coTiPro,desTiPro,codProd,desProd,codLinea,dser,dlinea,producto,cantLoteCant,codPresentacion,desPresentacion,desLoteCant,cotiProFomControl;
    String pesoDisponible,escariBatch,codproduc,valPresentacion,userID;
    AlertDialog progressBar;
    ArrayList<ItemLiProduc>  ListaProductodat = new ArrayList<>();
    Button btn_addActi;
    double result = 0;
    EditText txt_saco,txt_peso,txt_obs,txt_present;
    TextView txt_pesoDisponible;
    TextInputLayout textInputLayout;
    int nro = 1;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_dialog_add_actividad_forma_control,null);
        builder.setView(view);

        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(getContext()).setCancelable(false).build();
        sp_detAct = view.findViewById(R.id.sp_primaActiviAppDialogSer);
        sp_tipro = view.findViewById(R.id.sp_tiproAppDialogSer);
        sp_pro = view.findViewById(R.id.sp_prodAppDialogSer);
        sp_actividad = view.findViewById(R.id.sp_actiAppDialogSer);
        sp_presentacion = view.findViewById(R.id.sp_presentacionAppDialogActividad);
        btn_addActi = view.findViewById(R.id.btn_agregarActivi);
        txt_saco = view.findViewById(R.id.txt_sacoDialogActivi);
        txt_peso = view.findViewById(R.id.txt_pesoDialogActivi);
        textInputLayout = view.findViewById(R.id.txt_obserDialogActiviInput);
        txt_obs = view.findViewById(R.id.txt_obserDialogActivi);
        txt_pesoDisponible = view.findViewById(R.id.txt_cantDispoRefeFormatControl);
        txt_present = view.findViewById(R.id.txt_presentDialogActivi);
        txt_saco.setOnKeyListener(this);

        Intent prueba = getActivity().getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            serie =(String) b.get("serie");
            NSerie =(String) b.get("numSerie");
            codLinea =(String) b.get("LiProduc");
            dser =(String) b.get("dserie");
            dlinea =(String) b.get("dlinea");
            producto =(String) b.get("producto");
            cantLoteCant = getArguments().getString("cantLoteCant");
            desLoteCant = getArguments().getString("produc");
            codproduc = getArguments().getString("codproduc");
            cotiProFomControl = getArguments().getString("codTiPro");
            escariBatch = (String) b.get("escariBatch");
            userID = (String) b.get("userid");
        }
        //Toast.makeText(getContext(), codproduc, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), cotiProFomControl+"/"+codproduc, Toast.LENGTH_LONG).show();
        //Toast.makeText(getContext(), cotiProFomControl+"/"+desLoteCant+"/"+cantLoteCant, Toast.LENGTH_SHORT).show();

        final String us = getActivity().getIntent().getStringExtra("userid");
        Toast.makeText(getContext(), us+"/"+userID, Toast.LENGTH_SHORT).show();

        SEPETBACT(NSerie,serie,codLinea);

        calCant();
        txt_pesoDisponible.setText("Peso disponible: "+ result+" Kg");

        txt_peso.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction()==KeyEvent.ACTION_UP)){
                    String txt_pes = txt_peso.getText().toString();
                    try {
                        if(txt_pes.isEmpty()){
                            //Toast.makeText(getContext(),"0",Toast.LENGTH_SHORT).show();
                        }else{
                            double pesoDispon = Double.parseDouble(String.valueOf(result)); //cantidad en sacos
                            double pesoTotal = Double.parseDouble(txt_pes);
                            if(pesoTotal > pesoDispon){
                                Toast.makeText(getContext(),"Peso exedido. Disponible: "+String.valueOf(result),Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch(Exception e) {
                        System.out.println("ERROR: el valor de tipo String contiene caracteres no numéricos");
                    }
                    return true;
                }
                return false;
            }
        });
        //Toast.makeText(getContext(), pesoDisponible, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        }, 0);

        sp_actividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                codActivi = ((ItemLiProduc) item).getCod();
                desActivi = ((ItemLiProduc) item).getDesc();
                //Toast.makeText(getContext(), codActivi, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sp_detAct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                coddetAct = ((ItemDetaActivi) item).getCod();
                desDetAct = ((ItemDetaActivi) item).getDes();
                //Toast.makeText(getContext(), coddetAct+"/"+desDetAct, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sp_presentacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                codPresentacion = ((ItemTiProActividad) item).getCod();
                desPresentacion = ((ItemTiProActividad) item).getDesc();
                //Toast.makeText(getContext(),codPresentacion +"/"+desPresentacion,Toast.LENGTH_SHORT).show();
                sp_pro.setAdapter(new AdapterMPList(getContext(),R.layout.item_mplista_spinner,LIKPROD(codproduc,codPresentacion,cotiProFomControl,coTiPro)));
                Toast.makeText(getContext(), codproduc+"/"+codPresentacion+"/"+cotiProFomControl+"/"+coTiPro, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), desLoteCant+"/"+codPresentacion+"/"+coTiPro+"/"+cotiProFomControl, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        //Toast.makeText(getContext(),serie+"/"+NSerie,Toast.LENGTH_LONG).show();
        sp_tipro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                coTiPro = ((ItemTiProActividad) item).getCod();
                desTiPro = ((ItemTiProActividad) item).getDesc();
                //Toast.makeText(getContext(), coTiPro, Toast.LENGTH_SHORT).show();

                if(coTiPro.equals("PT")){
                    //Toast.makeText(getContext(), coTiPro, Toast.LENGTH_SHORT).show();
                    sp_presentacion.setAdapter(new AdapterPresentacion(getActivity(),R.layout.item_presentacion_spinner,SEPRE(cotiProFomControl,coTiPro,codproduc)));
                    sp_pro.setAdapter(new AdapterMPList(getContext(),R.layout.item_mplista_spinner,LIKPROD(codproduc,codPresentacion,cotiProFomControl,coTiPro)));
                    textInputLayout.setVisibility(View.INVISIBLE);
                    txt_peso.setText("");
                    txt_peso.setEnabled(false);
                }else{
                    //Toast.makeText(getContext(), coTiPro, Toast.LENGTH_SHORT).show();
                    sp_pro.setAdapter(new AdapterMPList(getContext(),R.layout.item_mplista_spinner,LIKPROD(codproduc,codPresentacion,cotiProFomControl,coTiPro)));
                    sp_presentacion.setAdapter(null);
                    textInputLayout.setVisibility(View.VISIBLE);
                    txt_peso.setEnabled(true);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sp_pro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                codProd = ((ItemMPLista) item).getCod();
                desProd = ((ItemMPLista) item).getProd();
                //String nombre = ((ItemMPLista) item).getProd();
                //Toast.makeText(getContext(), codProd+"/"+desProd, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btn_addActi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
                builder.setMessage("SEGURO QUE QUIERE AGREGAR EL DETALLE");
                builder.setCancelable(true);
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String saco = txt_saco.getText().toString();
                        String peso = txt_peso.getText().toString();
                        String obs = txt_obs.getText().toString();

                        if(codPresentacion != null){
                            valPresentacion = codPresentacion;
                            codPresentacion = null;
                        }else{
                            valPresentacion = txt_present.getText().toString();
                        }
                        //Toast.makeText(getActivity(),valPresentacion,Toast.LENGTH_SHORT).show();

                        if(saco.equals("") || peso.length() == 0){
                            Toast.makeText(getActivity(),"Faltan datos",Toast.LENGTH_SHORT).show();
                        }else{
                            //INACTI(NSerie,serie,dser,codLinea,dlinea,coddetAct,desDetAct,coTiPro,desTiPro,codProd,desProd,codActivi,desActivi,saco,peso,obs,valPresentacion,String.valueOf(nro),userID);
                            Toast.makeText(getActivity(),NSerie+"/"+serie+"/"+dser+"/"+codLinea+"/"+dlinea+"/"+codActivi+"/"+desActivi+"/"+coTiPro+"/"+desTiPro+"/"+codProd+"/"+desProd+"/"+coddetAct+"/"+desDetAct+"/"+saco+"/"+peso+"/"+nro+"/"+us,Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"Agregado Correctamente",Toast.LENGTH_SHORT).show();
                            limpiar();
                            SEPETBACT(NSerie,serie,codLinea);
                            calCant();
                            nro = nro + 1;
                        }
                    }
                });
                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return builder.create();
    }
    public void calCant(){
        double peDispo = Double.parseDouble(pesoDisponible);
        double peCant = Double.parseDouble(cantLoteCant);
        result = peCant - peDispo;
        if(result == peCant){
            result = Double.parseDouble(cantLoteCant);
        }
    }

    public static DialogoAddActividadFormaControl newInstance(String cant,String pd,String tpr,String cod) {
        DialogoAddActividadFormaControl frag = new DialogoAddActividadFormaControl();
        Bundle args = new Bundle();
        args.putString("cantLoteCant", cant);
        args.putString("codTiPro",tpr);
        args.putString("produc", pd);
        args.putString("codproduc", cod);
        frag.setArguments(args);
        return frag;
    }
    public void limpiar(){
        txt_peso.setText("");
        txt_saco.setText("");
        txt_obs.setText("");
        txt_saco.setSelected(true);
    } @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if((keyEvent.getAction()==KeyEvent.ACTION_UP)){
        String txt_sac = txt_saco.getText().toString();
        String txt_pes = txt_peso.getText().toString();
        calCant();
        try {
            double sac ;
            if(txt_sac.isEmpty()){
                sac = 0;
                Toast.makeText(getContext(),"0",Toast.LENGTH_SHORT).show();
            }else {
                sac = Double.parseDouble(txt_sac);
            }
                DecimalFormat df = new DecimalFormat("#.##");
                 //cantidad disponible actual
                double pesoTotal;
                if(txt_pes.isEmpty()){
                    pesoTotal = 0;
                }else{
                    pesoTotal = Double.parseDouble(txt_pes);
                }
                double txt_presen;
                if(codPresentacion == null){
                    txt_presen = 0;
                }else {
                    txt_presen = Double.parseDouble(codPresentacion);
                }
                double cal = 0;
                if(codPresentacion != null){
                    cal = txt_presen * sac;
                    if(result < cal){
                        txt_saco.setText(txt_sac.substring(0,(txt_sac.length()-1)));
                        txt_saco.setSelection(txt_saco.getText().length());
                        Toast.makeText(getContext(),"Peso Exedido",Toast.LENGTH_SHORT).show();
                    }else{
                        txt_peso.setText(String.valueOf(cal));
                    }

                }else{
                    cal = pesoTotal / sac;
                    txt_present.setText(String.valueOf(df.format(cal)));
                }

        } catch(Exception e) {
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
            return true;
        }
        return false;
    }

    public class CheckLogin extends AsyncTask<String,String,String> {
        String z = "";
        AdapterDetaActivi det;
        AdapterLiProduc acti;
        AdapterTiProActividad tiProActividad;
        String accion="SALIDA";
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.show();
        }
        @Override
        protected String doInBackground(String... params){
            det = new AdapterDetaActivi(getActivity(),R.layout.item_detaactivi_spinner,listDetaActivi(serie));
            tiProActividad = new AdapterTiProActividad(getContext(),R.layout.item_tipro_actividad_spinner,ListdatampLista(accion,serie));
            acti = new AdapterLiProduc(getActivity(),R.layout.item_li_produc_spinner,listActivi());
            return z;
        }
        @Override
        protected void onPostExecute(String r){
            progressBar.dismiss();
            //Toast.makeText(getContext(),serie+"/",Toast.LENGTH_SHORT).show();
            sp_detAct.setAdapter(det);
            sp_actividad.setAdapter(acti);
            sp_tipro.setAdapter(tiProActividad);
            //Toast.makeText(getContext(), escariBatch, Toast.LENGTH_SHORT).show();
            if(escariBatch.equals("COSELE")){
                sp_actividad.setSelection(2);
            }else if(escariBatch.equals("COLASE")){
                sp_actividad.setSelection(0);
            }else if(escariBatch.equals("COSELB")){
                sp_actividad.setSelection(13);
            }else if(escariBatch.equals("COSELI")){
                sp_actividad.setSelection(13);
            }else if(escariBatch.equals("COEDPE")){
                sp_actividad.setSelection(8);
            }else if(escariBatch.equals("COSELC")){
                sp_actividad.setSelection(13);
            }
        }
    }

    public List<ItemTiProActividad> ListdatampLista(String acc,String SerieAcc){
        ArrayList<ItemTiProActividad>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SETIPRO (?,?)}");
            pst1.setString(1,acc);
            pst1.setString(2,SerieAcc);
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                dataListaPuesto.add(new ItemTiProActividad(rs.getString("NOMPROD"),rs.getString("COD")));
            }
        }catch (Exception e){
        }
        return dataListaPuesto;
    }

    public List<ItemTiProActividad> SEPRE(String tpro,String tiproout,String cod){
        ArrayList<ItemTiProActividad> data = new ArrayList<>();
        data.clear();
        try {
            PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call SPAPP_SEPRE (?,?,?)}");
            pst1.setString(1,tpro);
            pst1.setString(2,tiproout);
            pst1.setString(3,cod);
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                data.add(new ItemTiProActividad(rs.getString("UM"),rs.getString("PRESENTACIONES")));
            }
        }catch (Exception e){
        }
        return data;
    }

    public List<ItemMPLista> LIKPROD(String pro,String presen,String tpr,String tp){
        ArrayList<ItemMPLista>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call SPAPP_LIKPROD (?,?,?,?)}");
            pst1.setString(1,pro);
            pst1.setString(2,presen);
            pst1.setString(3,tpr);
            pst1.setString(4,tp);
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                dataListaPuesto.add(new ItemMPLista(rs.getString("STMPDH_ARTCOD"),rs.getString("STMPDH_DESCRP")));
            }
        }catch (Exception e){
        }
        return dataListaPuesto;
    }
    public void SEPETBACT(String numS, String Ser,String codLi){
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SEPETBACT (?,?,?)}");
            pst1.setString(1,numS);
            pst1.setString(2,Ser);
            pst1.setString(3,codLi);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()){
                pesoDisponible = rs.getString("PESO");
            }
            calCant();
        }catch (Exception e){
        }
    }

    public List<ItemLiProduc> listActivi(){
        ListaProductodat.clear();
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SELI }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                ListaProductodat.add(new ItemLiProduc(rs.getString("CODLINEA"),rs.getString("DESCRIPCION"),rs.getString("DESCRIPCION")));
            }
        }catch (Exception e){
        }
        return ListaProductodat;
    }
    public List<ItemDetaActivi> listDetaActivi(String cdt){
        ArrayList<ItemDetaActivi> data = new ArrayList<>();
        data.clear();
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SEDETACT (?)}");
            pst1.setString(1,cdt);
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                data.add(new ItemDetaActivi(rs.getString("CODDETACTV"),rs.getString("DESDETALLEACTI")));
            }
        }catch (Exception e){
        }
        return data;
    }
    public void INACTI(String nser, String ser,String dser,String clinea,String dlinea,String cacti,String dacti, String ctipro,String dtipro,String cprod,String dprod,String csubli,String dsubli,String sac,String pes,String obs,String present,String nro,String usid){
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_INACTI (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            pst1.setString(1,nser);
            pst1.setString(2,ser);
            pst1.setString(3,dser);
            pst1.setString(4,clinea);
            pst1.setString(5,dlinea);
            pst1.setString(6,cacti);
            pst1.setString(7,dacti);
            pst1.setString(8,ctipro);
            pst1.setString(9,dtipro);
            pst1.setString(10,cprod);
            pst1.setString(11,dprod);
            pst1.setString(12,csubli);
            pst1.setString(13,dsubli);
            pst1.setString(14,sac);
            pst1.setString(15,pes);
            pst1.setString(16,obs);
            pst1.setString(17,present);
            pst1.setString(18,nro);
            pst1.setString(19,usid);
            pst1.executeUpdate();
        }catch (Exception e){
            Toast.makeText(getContext(),"Conexión inválida",Toast.LENGTH_SHORT).show();
        }
    }
}