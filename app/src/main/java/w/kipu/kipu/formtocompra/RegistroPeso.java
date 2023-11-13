package w.kipu.kipu.formtocompra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemMostrarListaProducto;
import w.kipu.kipu.ITEM.ItemSerieNum;
import w.kipu.kipu.R;
import w.kipu.kipu.formtocompra.InsertEstiva.InsertEstiva;
import w.kipu.kipu.formtocompra.InsertarFormatoPeso.InsertarPesoProducto;
import w.kipu.kipu.formtocompra.MostrarListaProductos.AdapterMostrarListaProducto;
import w.kipu.kipu.formtocompra.MostrarListaProductos.MostrarListaProductoRegistrados;

public class RegistroPeso extends AppCompatActivity {

    public String serienum,id_usuario,numDoc,obtSerie_RP,obtNumdoc_RP,idProveedor,nombreCompleto;
    EditText txt_quienEntregaRP,txt_placaRP,txt_loteProveedor,txt_guiaRemision;
    AutoCompleteTextView txt_nombreProductorRP;
    TextView txt_titulo;
    ImageButton btn_serieNumRP,btn_verOC;
    CheckBox ch_agregarPersonaRP;
    Button btn_insertarProductoRP,btn_mostrarProductoRP,btn_AsignarPrecioRP,btn_estiva;
    RecyclerView RecyclerProductosCompraRP;
    LinearLayout linearLayout,linearSerieNum;
    AdapterSQLite myDB;
    AdapterMostrarListaProducto adapterMostrarListaProducto;
    ArrayList<String>  dataProv = new ArrayList<>();
    String nombreProductor,idP,idAllCompra,idCompraNCompra,consultarNUM_NC=null,idCompraContiREg;
    android.app.AlertDialog progressBar;
    AdapterSerieNumero adapterSerieNumero;
    String quienEntrega,tipoNota;
    String tipoUser="COMPRADDOR";
    RecyclerView rvSerieNumero;

    public void find(){
        txt_titulo = findViewById(R.id.txt_tituloFormatoCompra);
        txt_nombreProductorRP = findViewById(R.id.txt_nombreProductorRP);
        txt_quienEntregaRP = findViewById(R.id.txt_quienEntregaRP);
        txt_placaRP = findViewById(R.id.txt_placaRP);
        linearSerieNum = findViewById(R.id.linearSerieNumRP);
        rvSerieNumero = findViewById(R.id.rvSerieNumeroRP);
        btn_serieNumRP = findViewById(R.id.btn_detallePendForControlRP);
        ch_agregarPersonaRP = findViewById(R.id.ch_agregarPersonaRP);
        btn_insertarProductoRP=findViewById(R.id.btn_insertarProductoRP);
        btn_mostrarProductoRP =findViewById(R.id.btn_mostrarProductoRP);
        btn_AsignarPrecioRP=findViewById(R.id.btn_AsignarPrecioRP);
        RecyclerProductosCompraRP = findViewById(R.id.RecyclerProductosCompraRP);
        linearLayout = findViewById(R.id.linearPersonaRP);
        btn_verOC = findViewById(R.id.btn_verseriePendForControlRP);
        btn_estiva = findViewById(R.id.btn_insertarEstivaRegistroPeso);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_peso);

        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(this).setCancelable(false).build();
        find();
        RecyclerProductosCompraRP.setHasFixedSize(true);
        myDB=new AdapterSQLite(getApplication());

        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            id_usuario=(String) b.get("idusuarioMenuFotma");
            serienum=(String) b.get("serieMenuFotma");
            numDoc = (String) b.get("numDocMenuFotma");
            tipoNota = (String) b.get("tipo_Nota");

            idCompraNCompra = (String) b.get("idCompraSQLiteMenuFormato");
            idCompraContiREg = (String) b.get("idCompraSQLiteMenuFormatoEstiva");
        }
        //Toast.makeText(getApplication(),tipoNota,Toast.LENGTH_LONG).show();
        ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
        params.height = 0;
        params.width = 1000;
        linearLayout.setLayoutParams(params);

        if(idCompraNCompra != null){
            idAllCompra = idCompraNCompra;
            //Toast.makeText(getApplication(),"If: "+idAllCompra,Toast.LENGTH_LONG).show();
        }else{
            idAllCompra = idCompraContiREg;
            //Toast.makeText(getApplication(),"else: "+ idAllCompra,Toast.LENGTH_LONG).show();
        }
        if(idCompraContiREg != null) {
            mostrarCompraProceso();
        }else
            txt_titulo.setText(serienum + " - " + numDoc);

        btn_verOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    consultarNUM_NC = "NUM_NC";
                    linearSerieNum.setVisibility(View.VISIBLE);
                    rvSerieNumero.setLayoutManager(new LinearLayoutManager(getApplication().getApplicationContext()));
                    adapterSerieNumero = new AdapterSerieNumero(listaSerieNumero(idAllCompra));
                    rvSerieNumero.setAdapter(adapterSerieNumero);
                    rvSerieNumero.getAdapter().notifyDataSetChanged();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
        listComprador();

        ArrayAdapter prod = new ArrayAdapter(getApplication(),android.R.layout.simple_list_item_1, dataProv );
        txt_nombreProductorRP.setAdapter(prod);
        txt_nombreProductorRP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ch_agregarPersonaRP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
                    params.height = 200;
                    params.width = 1000;
                    linearLayout.setLayoutParams(params);
                }else{
                    ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
                    params.height = 0;
                    params.width = 1000;
                    linearLayout.setLayoutParams(params);
                }
            }
        });
        btn_insertarProductoRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(consultarNUM_NC != null){
                    ValidarInsert validarInsert = new ValidarInsert();
                    validarInsert.execute("");
                }else{
                    Toast.makeText(getApplicationContext(),"TIENES QUE AGREGAR UNA NOTA. NO SEAS GIL",Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_estiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertEstiva diag = new InsertEstiva();
                diag.show(getSupportFragmentManager(),"example dialog");
            }
        });
        btn_mostrarProductoRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerProductosCompraRP.setLayoutManager(new LinearLayoutManager(getApplication().getBaseContext()));
                adapterMostrarListaProducto = new AdapterMostrarListaProducto(ObtenerListaProducto( idAllCompra),getApplicationContext());
                adapterMostrarListaProducto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                RecyclerProductosCompraRP.setAdapter(adapterMostrarListaProducto);
                RecyclerProductosCompraRP.getAdapter().notifyDataSetChanged();
            }
        });
        btn_AsignarPrecioRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateCompra updateCompra = new UpdateCompra();
                updateCompra.execute();
            }
        });
        btn_serieNumRP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddCompraSerieNum dialogo = new AddCompraSerieNum();
                Bundle bundle = new Bundle();
                bundle.putString("idCompraRegistroPeso",idAllCompra);
                bundle.putString("idUsuarioRegistroPeso",id_usuario);
                dialogo.setArguments(bundle);
                dialogo.show((RegistroPeso.this).getSupportFragmentManager(),"Image Dialog");
            }
        });
    }

    public void listComprador(){
        try {
            Cursor res = myDB.listaAutocompleteGac();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    String desc = res.getString(0);
                    dataProv.add(desc);
                }
            }else
                Toast.makeText(getApplication(),"NOSE PUDO MOSTRAR USUARIOS",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplication(),"ERORR "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
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
            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return dataListaPuesto;
    }

    Boolean back= true;
    @Override
    public void onBackPressed() {
        if (back){
            ShowDialog();
        }
    }
    public void ShowDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SALIR");
        builder.setMessage("¿ SEGURO QUE DESEA SALIR SIN IMPRIMIR ?");
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        builder.create().show();
    }

    public class ValidarInsert extends AsyncTask<String,String,String> {
        boolean isSuccess = false;
        String z;
        String idPro,idUsua,dniUsua,nombUsua;
        String comprobarNombre = txt_nombreProductorRP.getText().toString();
        String placa = txt_placaRP.getText().toString();
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params){
            if(comprobarNombre.trim().equals("") || placa.trim().equals("")){
                z = "Faltan ingresar algunos datos";
            }else{
                try{
                    if (ch_agregarPersonaRP.isChecked())
                        quienEntrega = txt_quienEntregaRP.getText().toString();
                    else
                        quienEntrega = null;

                    Cursor resPro = myDB.ValidarUsuarioRP_InsertPeso_GAC(comprobarNombre);
                    if(resPro!= null && resPro.getCount()>0) {
                        if (resPro.moveToFirst()) {
                            idPro=resPro.getString(0);
                            Boolean resCompra = myDB.UpdateCompra(idAllCompra,idPro,null,quienEntrega,placa,null,null,null,null,null,null);
                            if(resCompra == true) {
                                z="COMPRA ACTUALIZADA";
                                isSuccess = true;
                            }else{
                                z="NO SE ACTUALIZÓ LA COMPRA";
                                isSuccess =false;
                            }
                        }
                    }else {
                        z="GAC NO EXISTE";
                        isSuccess =false;
                    }
                }
                catch (Exception ex)     {
                    z="ERROR "+ex.getMessage();
                    isSuccess = false;   }
            }
            return z;
        }
        @Override
        protected void onPostExecute(String r){
            progressBar.dismiss();
            if(isSuccess){
                Toast.makeText(getApplicationContext(),z,Toast.LENGTH_SHORT).show();
                InsertarPesoProducto dialogo = new InsertarPesoProducto();
                Bundle bundle = new Bundle();
                bundle.putString("TEXT",idAllCompra);
                dialogo.setArguments(bundle);
                dialogo.show((RegistroPeso.this).getSupportFragmentManager(),"Image Dialog");
                //Toast.makeText(getApplicationContext(),idAllCompra,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),z,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class UpdateCompra extends AsyncTask<String,String,String>{
        String z = "";
        Boolean isSuccess = false;
        @Override
        protected void onPreExecute(){
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params){
            try{
                isSuccess = true;
            }catch (Exception ex){
                isSuccess = false;
                z= ex.getMessage();
            }
            return z;
        }
        @Override
        protected void onPostExecute(String r){
            progressBar.dismiss();
            if(isSuccess){
                Intent intent = new Intent(getApplicationContext(),MostrarListaProductoRegistrados.class);
                intent.putExtra("idcompraRegistroPeso",idAllCompra);
                intent.putExtra("tipo_User",tipoUser);
                intent.putExtra("regisPesoRegistroPeso",tipoUser);
                intent.putExtra("tipo_Nota",tipoNota);
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Error " + r,Toast.LENGTH_LONG).show();
            }
        }
    }

    public void mostrarCompraProceso(){
        try {
            Cursor rs = myDB.selectContinuarRegistro_RP(idAllCompra);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    txt_titulo.setText(rs.getString(9)+" - "+rs.getString(10));
                    txt_nombreProductorRP.setText(rs.getString(6));
                    txt_placaRP.setText(rs.getString(8));
                    txt_quienEntregaRP.setText(rs.getString(3));
                }
            }

        }catch (Exception e){
        }
    }

    public List<ItemMostrarListaProducto> ObtenerListaProducto(String d){
        List<ItemMostrarListaProducto>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            Cursor rs = myDB.listaProductosRecyclerview_RP(d);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    ListaProducto.add(new ItemMostrarListaProducto(rs.getString(3),rs.getString(2),rs.getString(4),rs.getString(5),
                            rs.getString(6),rs.getString(0),rs.getString(1),null,null,null));
                }
            }else
                Toast.makeText(getApplication(),"NOSE PUDO MOSTRAR USUARIOS",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"desconectado",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
}