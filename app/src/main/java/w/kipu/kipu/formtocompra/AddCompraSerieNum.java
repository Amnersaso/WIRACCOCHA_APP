package w.kipu.kipu.formtocompra;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemSerieNum;
import w.kipu.kipu.R;

public class AddCompraSerieNum extends AppCompatDialogFragment {

    EditText txtnumero;
    Spinner spinner;
    Button btnAdd,btnMostrar;
    CONEXION conexion = new CONEXION();
    AdapterSQLite myDB;
    String serieSelectRP,obtID_RP,obtSerie;
    String valID,valSer,valNum,idUsu,idAllCompra,num;
    RecyclerView rvSerieNumero;
    AdapterSerieNumero adapterSerieNumero;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_add_compra_serie_num,null);
        builder.setView(view);

        txtnumero = view.findViewById(R.id.txt_numeroAddSerieNumero);
        btnAdd = view.findViewById(R.id.btn_agregarAddSerieNumero);
        spinner = view.findViewById(R.id.sp_addserieOC);
        btnMostrar=view.findViewById(R.id.btn_mostrarAddSerieNumero);
        rvSerieNumero = view.findViewById(R.id.rvSerieNumero);

        myDB =new AdapterSQLite(getContext());
        Intent prueba = getActivity().getIntent();
        Bundle b = prueba.getExtras();
        Bundle bundle = getArguments();
        if(b!=null){
            serieSelectRP = (String) b.get("serieMenuFotma");
        }
        idAllCompra = bundle.getString("idCompraRegistroPeso","");
        idUsu = bundle.getString("idUsuarioRegistroPeso","");
        Toast.makeText(getContext(),idAllCompra,Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> prod = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, listSerieOC());
        spinner.setAdapter(prod);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                obtSerie = item.toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvSerieNumero.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
                adapterSerieNumero = new AdapterSerieNumero(listaSerieNumero(idAllCompra));
                rvSerieNumero.setAdapter(adapterSerieNumero);
                rvSerieNumero.getAdapter().notifyDataSetChanged();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = txtnumero.getText().toString();
                if(num.isEmpty())
                    Toast.makeText(getContext(),"INGRESE EL NÚMERO DE NOTA",Toast.LENGTH_SHORT).show();
                else {
                    //Toast.makeText(getContext(),idUsu+ " / "+obtSerie+" / "+num,Toast.LENGTH_SHORT).show();
                    valInsertSerNum(idUsu, obtSerie, num);
                    rvSerieNumero.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
                    adapterSerieNumero = new AdapterSerieNumero(listaSerieNumero(idAllCompra));
                    rvSerieNumero.setAdapter(adapterSerieNumero);
                    rvSerieNumero.getAdapter().notifyDataSetChanged();
                }
            }
        });
        return builder.create();
    }
    public ArrayList<String> listSerieOC(){
        ArrayList<String>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            Cursor res = myDB.listaSpinnerSerie_RP();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    dataListaPuesto.add(res.getString(0));
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return dataListaPuesto;
    }
    public ArrayList<ItemSerieNum> listaSerieNumero(String idC){
        ArrayList<ItemSerieNum>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            Cursor res = myDB.listaRecyclerSerieNum_RP(idC);
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    dataListaPuesto.add(new ItemSerieNum(res.getString(0),res.getString(1),res.getString(2),res.getString(3)));
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return dataListaPuesto;
    }
    public void valInsertSerNum(String idusuar,String ser,String nu){
        try {
            Cursor res = myDB.selectValidarSerieNum_RP(idusuar,ser,nu);
            if(res!= null && res.getCount()>0){
                if (res.moveToNext()){
                    valSer = res.getString(1);
                    valNum = res.getString(2);
                    Toast.makeText(getActivity(), "YA EXISTE EL NÚMERO CON ESTA SERIE",Toast.LENGTH_SHORT).show();
                }
            }else{
                insertSerNum(idUsu,idAllCompra,obtSerie,num);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "VALIDAR / "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void insertSerNum(String idus,String idCom,String ser,String num){
        try {
            Boolean result = myDB.insertSerieNum_RP(idus,idCom,ser,num);
            if(result == true){
                Toast.makeText(getContext(),"REGISTRADO",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(),"NO REGISTRADO",Toast.LENGTH_SHORT).show();
            }
            limpiar();
        } catch (Exception e) {
            Toast.makeText(getActivity(), "INSERT / "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void limpiar(){
        txtnumero.setText("");
    }

}