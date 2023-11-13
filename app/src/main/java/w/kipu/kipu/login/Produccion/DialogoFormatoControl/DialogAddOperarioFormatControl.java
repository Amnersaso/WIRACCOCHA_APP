package w.kipu.kipu.login.Produccion.DialogoFormatoControl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.Conn.DataTableList;
import w.kipu.kipu.ITEM.ItemLiProduc;
import w.kipu.kipu.ITEM.ItemPuesto;
import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.AdapterLiProduc;

public class DialogAddOperarioFormatControl extends DialogFragment {

    Spinner sp_actividad,sp_puesto;
    AutoCompleteTextView autoComplete;
    DataTableList dataTableList = new DataTableList();
    CONEXION conexion = new CONEXION();
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    Button btn_agregar,btn_actividad,btn_guardar;
    String selActividad;
    RecyclerView rv_operario;
    ArrayList<ItemLiProduc>  ListaProductodat = new ArrayList<>();
    ArrayList<String> datalistLinea = new ArrayList<>();
    String serie_,numSerie_,codLinea,escariBatch;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_dialog_add_operario_format_control,null);
        builder.setView(view);

        sp_actividad = view.findViewById(R.id.sp_actividadAppDialogSer);
        autoComplete = view.findViewById(R.id.txt_operarioAdd);
        btn_agregar = view.findViewById(R.id.btn_agregarOperario);
        rv_operario = view.findViewById(R.id.rv_operariosFormatoControl);
        sp_puesto = view.findViewById(R.id.sp_puestoAppDialogSer);
        btn_actividad = view.findViewById(R.id.btn_addDetalleActFormatoControl);
        btn_guardar = view.findViewById(R.id.btn_guardarFormatoControl);

        Intent prueba = getActivity().getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            serie_ =(String) b.get("serie");
            numSerie_ =(String) b.get("numSerie");
            codLinea =(String) b.get("LiProduc");
            escariBatch = (String) b.get("escariBatch");
        }
       // sp_puesto.setAdapter(new AdapterPuesto(getContext(),R.layout.item_puesto_spinner,ListdatampLista()));

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dataTableList.ListaOperario();
//                ArrayAdapter prod = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1, dataTableList.data );
//                autoComplete.setAdapter(prod);
//            }
//        }, 0);

        autoComplete.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getContext(),item,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sp_actividad.setAdapter( new AdapterLiProduc(getActivity(),R.layout.item_li_produc_spinner,listActivi()));
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
        sp_actividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                String val =  item.toString();
                //codActivi = ((ItemLiProduc) item).getCod();
                selActividad = ((ItemLiProduc) item).getDesc();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(autoComplete.equals("") || autoComplete.length() == 0){
                    Toast.makeText(getActivity(),"Seleccione un operario",Toast.LENGTH_SHORT).show();
                }else{
                    String nomb = autoComplete.getText().toString();
                    String[] dniOpte = nomb.split("-");
                    String dni = dniOpte[1];
                    String nombre =dniOpte[0];
                    try {
                        //Toast.makeText(getContext(),idSer+"/"+serie_+"/"+numSerie_,Toast.LENGTH_LONG).show();
                        InsertarOperario(serie_,numSerie_,dni,nombre,selActividad,codLinea);
                        autoComplete.setText("");
                    } catch (SQLException e) {
                        Toast.makeText(getActivity(),"error al agregar",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return builder.create();
    }

//    public List<ItemPuesto> ListdatampLista(){
////        ArrayList<ItemPuesto>  dataListaPuesto= new ArrayList<>();
////        dataListaPuesto.clear();
////        try {
////            PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call SPAPP_SEPSTO }");
////            ResultSet rs = pst1.executeQuery();
////            while(rs.next()){
////                dataListaPuesto.add(new ItemPuesto(rs.getString("GRTPUE_DESCRP"),rs.getString("GRTPUE_PUESTO")));
////            }
////        }catch (Exception e){
////        }
////        return dataListaPuesto;
//    }

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
    public void InsertarOperario(String se, String nu,String dni,String ope,String acti,String cLinea) throws SQLException{
            PreparedStatement pst = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_ADDOPERARIO (?,?,?,?,?,?)}");
            pst.setString(1, se);
            pst.setString(2, nu);
            pst.setString(3, dni);
            pst.setString(4, ope);
            pst.setString(5, acti);
            pst.setString(6, cLinea);
            pst.executeUpdate();
            Toast.makeText(getActivity(), "Agregado Correctamente",Toast.LENGTH_SHORT).show();
    }
}