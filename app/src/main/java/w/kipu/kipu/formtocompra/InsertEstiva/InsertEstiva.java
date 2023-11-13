package w.kipu.kipu.formtocompra.InsertEstiva;

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

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemEstiva;
import w.kipu.kipu.R;

public class InsertEstiva extends AppCompatDialogFragment {

    Spinner sp_tipo;
    EditText txtAbrev,txtNombre;
    Button btnAgregar,btnMostrar;
    RecyclerView rvEstiva;
    String[] tipoData={"ESTIVA","DESESTIVA"};
    CONEXION conexion = new CONEXION();
    Connection con;
    String tipo,idUsuarioselect,obtID_RP,idCompraSQLite;
    AdapterSQLite myDB;
    AdapterListaEstiva adapterListaEstiva;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_insert_estiva,null);
        builder.setView(view);

        sp_tipo=view.findViewById(R.id.sp_tipoEstiva);
        txtAbrev=view.findViewById(R.id.txt_abrevEstiva);
        txtNombre =view.findViewById(R.id.txt_nombreEstiva);
        btnAgregar=view.findViewById(R.id.btn_agregarEstiva);
        btnMostrar=view.findViewById(R.id.btn_mostrarEstiva);
        rvEstiva=view.findViewById(R.id.rvEstiva);
        myDB = new AdapterSQLite(view.getContext());

        Intent prueba = getActivity().getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            idUsuarioselect = (String) b.get("idusuarioMenuFotma");
            idCompraSQLite = (String) b.get("idCompraSQLiteMenuFormatoEstiva");
        }
        //mostrarNumDocSerie_RP();
        Toast.makeText(getContext(),idUsuarioselect+" / "+idCompraSQLite,Toast.LENGTH_SHORT).show();

        rvEstiva.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        adapterListaEstiva = new AdapterListaEstiva(ObtenerListaProducto(idCompraSQLite));
        rvEstiva.setAdapter(adapterListaEstiva);
        rvEstiva.getAdapter().notifyDataSetChanged();

        sp_tipo.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,tipoData));
        sp_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                tipo = item.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String abrev=txtAbrev.getText().toString();
                String nombre=txtNombre.getText().toString();

                if (nombre.equals("")){
                    Toast.makeText(getContext(),"Ingrese en uno de los campos",Toast.LENGTH_SHORT).show();
                }else{
                    agregar_proveedorMP(nombre,tipo,idCompraSQLite);
                    rvEstiva.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
                    adapterListaEstiva = new AdapterListaEstiva(ObtenerListaProducto(idCompraSQLite));
                    rvEstiva.setAdapter(adapterListaEstiva);
                    rvEstiva.getAdapter().notifyDataSetChanged();
                    limpiar();
                }
            }
        });
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvEstiva.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
                adapterListaEstiva = new AdapterListaEstiva(ObtenerListaProducto(idCompraSQLite));
                rvEstiva.setAdapter(adapterListaEstiva);
                rvEstiva.getAdapter().notifyDataSetChanged();
            }
        });

        return builder.create();
    }
    public void agregar_proveedorMP(String nombre,String tipo,String idComp) {
        try {
            Boolean result = myDB.insertEstiva(idComp,nombre,tipo);
            if(result == true)
                Toast.makeText(getContext(), "ESTIVA AGREGADO", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Toast.makeText(getContext(), "Estiva / "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public List<ItemEstiva> ObtenerListaProducto(String id){
        List<ItemEstiva>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            Cursor res = myDB.listaEstivaRecyclerview(id);
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    ListaProducto.add(new ItemEstiva(res.getString(0)));
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"ERROR "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
    public void limpiar(){
        txtNombre.setText("");
        txtAbrev.setText("");
    }
}