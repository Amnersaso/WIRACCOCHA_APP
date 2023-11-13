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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import dmax.dialog.SpotsDialog;
import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.R;
import w.kipu.kipu.formtocompra.InsertEstiva.InsertEstiva;
import w.kipu.kipu.formtocompra.InsertarFormatoPeso.InsertarPesoProducto;
import w.kipu.kipu.formtocompra.MostrarListaProductos.AdapterMostrarListaProducto;
import w.kipu.kipu.ITEM.ItemMostrarListaProducto;
import w.kipu.kipu.formtocompra.MostrarListaProductos.MostrarListaProductoRegistrados;
//import w.wira.wiraccocha.login.Produccion.DialogoFormatoControl.DialogoAddActividadFormaControl;

public class FormatoCompra_Activity extends AppCompatActivity {

    public String serienum="0";
    public String obtenerTipo="0";
    public String numDoc="0";

    String dniProvee = null,nombreCompleto=null,direccionProvee=null;
    public String id_usuario,id_compra;
    TextView titulo;
    EditText nombre,dni,codigo,cel,placa_vehicu,direccion,referencia,txt_codigoProveedor,txt_guiaRemision;
    TextInputEditText numSerie;
    TextInputLayout textInputLayoutNumdoc;
    Button insertarProducto,btn_mostrarProduct,btn_asigarPrecio,btn_agregarEstiva;
    Button btn_buscarNumDoc,btn_buscardniRUC;
    Spinner sp_tipo;
    android.app.AlertDialog progressBar;
    String[] tipoProductor = { "Productor", "Acopiador"};
    RecyclerView recyclerViewListaProd;
    AdapterMostrarListaProducto adapterMostrarListaProducto;
    LinearLayout linearLayout,LiPersona;
    EditText quienEntrega,nombreCobra;
    CheckBox ch_Add;
    public ArrayList<String> dataProductor= new ArrayList<>();
    AdapterSQLite adapterSQLite;
    AutoCompleteTextView comunidadAuto,distritoAuto;
    String z = "";
    Boolean isSuccess = false;
    String idAllCompra,idCompraEstado,idProvSQLite,idCompraSQLite,idProvSQLiteNCompra,idAllProvSQLite,valTipoReg_GAC_Compra,tipoUSer,tipoNota;

    public void find(){
        titulo=(TextView) findViewById(R.id.txt_tituloFormatoCompra);
        numSerie=(TextInputEditText) findViewById(R.id.txt_numFormato);
        textInputLayoutNumdoc = (TextInputLayout) findViewById(R.id.textinputlauoyt_NumDoc);
        dni=(EditText) findViewById(R.id.txt_dniFormato);
        nombre=(EditText) findViewById(R.id.txt_nombreProductor);
        comunidadAuto=(AutoCompleteTextView) findViewById(R.id.txt_comunidad);
        distritoAuto=(AutoCompleteTextView) findViewById(R.id.txt_distrito);
        btn_mostrarProduct = (Button) findViewById(R.id.btn_mostrarProducto);
        btn_buscardniRUC = (Button) findViewById(R.id.btn_buscardni);
        codigo=(EditText) findViewById(R.id.txt_parcelaCodigo);
        txt_codigoProveedor=(EditText) findViewById(R.id.txt_nc_codProveedor);
        txt_guiaRemision=(EditText) findViewById(R.id.txt_nc_GuiaRemision);
        placa_vehicu=(EditText) findViewById(R.id.txt_placa);
        cel=(EditText) findViewById(R.id.txt_celular);
        insertarProducto=(Button) findViewById(R.id.btn_insertarProducto);
        btn_buscarNumDoc =(Button) findViewById(R.id.btn_buscarFormatoNumDoc);
        btn_asigarPrecio =(Button) findViewById(R.id.btn_AsignarPrecio);
        direccion = (EditText) findViewById(R.id.txt_direccionProductor);
        referencia =(EditText) findViewById(R.id.txt_referenciaProductor);
        sp_tipo=(Spinner) findViewById(R.id.sp_tipoProductor);
        quienEntrega =(EditText) findViewById(R.id.txt_quienEntrega);
        nombreCobra=(EditText)findViewById(R.id.txt_nombreCobra);
        recyclerViewListaProd =(RecyclerView) findViewById(R.id.RecyclerProductosCompra);
        linearLayout = findViewById(R.id.linearlayout_GAC);
        LiPersona = findViewById(R.id.linearPersona);
        ch_Add = findViewById(R.id.ch_agregarPersona);
        btn_agregarEstiva = findViewById(R.id.btn_insertarEstivaFormtaControl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formatocompra);

        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(this).setCancelable(false).build();
        find();
        recyclerViewListaProd.setHasFixedSize(true);
        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            id_usuario=(String) b.get("idusuarioMenuFotma");
            serienum=(String) b.get("serieMenuFotma");
            numDoc = (String) b.get("numDocMenuFotma");
            valTipoReg_GAC_Compra = (String) b.get("validarTipoRegistr_GAC_Comprador");
            tipoUSer = (String) b.get("tipo_User");
            tipoNota = (String) b.get("tipo_Nota");

            idCompraEstado = (String) b.get("idCompraNCompra");
            idCompraSQLite = (String) b.get("idCompraSQLiteMenuFormato");
            idProvSQLiteNCompra = (String) b.get("idProvNCompra");
        }
        //Toast.makeText(getApplication(),tipoNota,Toast.LENGTH_LONG).show();
        ViewGroup.LayoutParams params1 = linearLayout.getLayoutParams();
        params1.height = 0;
        params1.width = 1000;
        linearLayout.setLayoutParams(params1);

        if(idCompraSQLite != null) {
            idAllCompra = idCompraSQLite;
            titulo.setText(serienum + " - " + numDoc);
        }else {
            idAllCompra = idCompraEstado;
            mostrarCompraProceso(idAllCompra);
        }
        adapterSQLite = new AdapterSQLite(this);
        //Toast.makeText(getApplicationContext(),id_compra+" / "+serienum+" / "+numDoc,Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplicationContext(),"PROCESO "+idCompraEstado+" / ",Toast.LENGTH_LONG).show();
        if(idCompraEstado != null){
            mostrarCompraProceso(idAllCompra);
        }else{
            titulo.setText(serienum + " - " + numDoc);
            mostrarCompraProceso(idAllCompra);
        }
        ViewGroup.LayoutParams params = LiPersona.getLayoutParams();
        params.height = 0;
        params.width = 1000;
        LiPersona.setLayoutParams(params);
        ch_Add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ViewGroup.LayoutParams params = LiPersona.getLayoutParams();
                    params.height = 350;
                    params.width = 1000;
                    LiPersona.setLayoutParams(params);
                }else{
                    ViewGroup.LayoutParams params = LiPersona.getLayoutParams();
                    params.height = 0;
                    params.width = 1000;
                    LiPersona.setLayoutParams(params);
                }
            }
        });
        listaProductor();
        ArrayAdapter comunidadAdapter = new ArrayAdapter(getApplication(),android.R.layout.simple_list_item_1, dataProductor );
        comunidadAuto.setAdapter(comunidadAdapter);
        comunidadAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getApplication(),item,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter DistritoAdapter = new ArrayAdapter(getApplication(),android.R.layout.simple_list_item_1, dataListaDistrito() );
        distritoAuto.setAdapter(DistritoAdapter);
        distritoAuto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                //Toast.makeText(getApplication(),item,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<String> prod = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_dropdown_item, tipoProductor);
        sp_tipo.setAdapter(prod);
        sp_tipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                obtenerTipo = item.toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btn_buscardniRUC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comprobarDNI = dni.getText().toString();
                /*
                mostrarProveedorMP(comprobarDNI);
                 */
                try {
                    Cursor res = adapterSQLite.ValidarProveedor(comprobarDNI);
                    if(res!= null && res.getCount()>0){
                        if (res.moveToNext()){
                            idProvSQLite = res.getString(1);
                            nombre.setText(res.getString(3));
                            comunidadAuto.setText(res.getString(5));
                            distritoAuto.setText(res.getString(6));
                            direccion.setText(res.getString(7));
                            codigo.setText(res.getString(10));
                            cel.setText(res.getString(4));
                            referencia.setText(res.getString(8));
                        }
                    }else {
                        Toast.makeText(getApplication(),"NO EXISTE",Toast.LENGTH_SHORT).show();
                        limpiar_datos();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
        insertarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarInsert validarInsert = new ValidarInsert();
                validarInsert.execute("");
            }
        });
        btn_agregarEstiva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertEstiva diag = new InsertEstiva();
                diag.show(getSupportFragmentManager(),"example dialog");
            }
        });

        btn_mostrarProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),id_compra+" / "+serienum+" / "+numDoc,Toast.LENGTH_LONG).show();
                recyclerViewListaProd.setLayoutManager(new LinearLayoutManager(getApplication().getBaseContext()));
                adapterMostrarListaProducto = new AdapterMostrarListaProducto(ObtenerListaProducto(idAllCompra),getApplicationContext());
                adapterMostrarListaProducto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
                recyclerViewListaProd.setAdapter(adapterMostrarListaProducto);
                recyclerViewListaProd.getAdapter().notifyDataSetChanged();
            }
        });
        btn_asigarPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateCompra updateCompra = new UpdateCompra();
                updateCompra.execute();
            }
        });
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
        builder.setTitle("Cancelar");
        builder.setMessage("¿ Seguro que desea cancelar el registro ?");
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
    public void listaProductor(){
        try {
            Cursor res = adapterSQLite.selectComunidad();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    String desc = res.getString(2);
                    dataProductor.add(desc);
                }
            }else
                Toast.makeText(getApplication(),"No se pudo mostrar al Productor",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public List<String> dataListaDistrito(){
        ArrayList<String>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            Cursor res = adapterSQLite.selectDistrito();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    String ID = res.getString(1);
                    String desc = res.getString(2);
                    dataListaPuesto.add(desc+" | "+ID);
                }
            }else {
                Toast.makeText(getApplication(),"No se pudo mostrar el Distrito",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return dataListaPuesto;
    }
    /*
    public void mostrarNumDocSerie_RP(){
        try {
            PreparedStatement pst = conexion.ConnectionDB_Local().prepareStatement("{CALL SPAPP_SEID_SERNUM_TBRP_MODIFI (?)}");
            pst.setString(1,id_usuario);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                id_compra = rs.getString("ID_COMPRA");
            }
        }catch (Exception e){
        }
    }

     */
    public void mostrarCompraProceso( String idComp){
        try {
            Cursor rs = adapterSQLite.selectMostrarProveedorRegistro(idComp);
            if(rs!= null && rs.getCount()>0){
                if (rs.moveToNext()){
                    idProvSQLite = rs.getString(1);
                    titulo.setText(rs.getString(5)+" - "+rs.getString(6));
                    dni.setText(rs.getString(13));
                    nombre.setText(rs.getString(14));
                    //sp_tipo.setText(rs.getString("TIPO"));
                    comunidadAuto.setText(rs.getString(9));
                    distritoAuto.setText(rs.getString(10));
                    direccion.setText(rs.getString(16));
                    referencia.setText(rs.getString(18));
                    codigo.setText(rs.getString(17));
                    placa_vehicu.setText(rs.getString(19));
                    cel.setText(rs.getString(20));
                    nombreCobra.setText(rs.getString(2));
                    quienEntrega.setText(rs.getString(3));
                    txt_codigoProveedor.setText(rs.getString(21));
                    txt_guiaRemision.setText(rs.getString(22));
                }
            }else
                Toast.makeText(getApplication(),"NOSE PUDO MOSTRAR PROVEEDOR",Toast.LENGTH_SHORT).show();

        }catch (Exception e){
        }
    }
    public List<ItemMostrarListaProducto> ObtenerListaProducto(String id){
        List<ItemMostrarListaProducto>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try {
            Cursor rs = adapterSQLite.selectMostrarListaProducto(id);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    ListaProducto.add(new ItemMostrarListaProducto(rs.getString(3),rs.getString(2),null,rs.getString(5)
                            ,null,rs.getString(0),rs.getString(1),null,null,null));
                }
            }else {
                Toast.makeText(getApplication(),"NO HAY PESOS",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
    //FALTA DETERMINAR ESTE CODIGO BUSCAR DNI EN RENIEC
    public void validarDNI(){
        String comprobarDNI = dni.getText().toString();
        //mostrarProveedorMP(comprobarDNI);
        if (comprobarDNI.equals(dniProvee)){
            nombre.setText(nombreCompleto);
            direccion.setText(direccionProvee);
        }else{
            try {
                String datos = null;
                String enlace = "http://api.reniec.cloud/dni/"+comprobarDNI;//https://api.reniec.cloud/dni/
                URL url = new URL(enlace);
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.connect();
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse(new InputStreamReader((InputStream)urlConnection.getContent()));
                if(root.isJsonObject()){
                    JsonObject rootobj = root.getAsJsonObject();
                    String dni = rootobj.get("dni").getAsString();
                    String apellidoPaterno = rootobj.get("apellido_paterno").getAsString();
                    String apellidoMaterno = rootobj.get("apellido_materno").getAsString();
                    String nombres = rootobj.get("nombres").getAsString();
                    datos = nombres+" "+apellidoPaterno+" "+apellidoMaterno;
                }
                nombre.setText(datos);
            }catch( Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }
/*
    public void actualizarCompra(String id_provee, String persoCobra,String dniCobra,String placa,String idCompra,String comu,String distri,String parce) throws SQLException {
        PreparedStatement pst1 = conexion.ConnectionDB().prepareStatement("{ call SPAPP_UPCOMP_COBRA_ENT (?,?,?,?,?,?,?,?)}");
        pst1.setString(1, id_provee);
        pst1.setString(2, persoCobra);
        pst1.setString(3, dniCobra);
        pst1.setString(4, placa);
        pst1.setString(5, idCompra);
        pst1.setString(6, comu);
        pst1.setString(7, distri);
        pst1.setString(8, parce);
        pst1.executeUpdate();
    }

 */
    public void mostrarProveedorMP(String dniPro){
        try {
            Cursor rst = adapterSQLite.selectProveedor(dniPro);
            if(rst!= null && rst.getCount()>0){
                while (rst.moveToNext()){
                    idAllProvSQLite = rst.getString(1);
                }
            }
        }catch (Exception e){
            //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public class ValidarInsert extends AsyncTask<String,String,String> {
        String comprobarDNI = dni.getText().toString();
        String nomb = nombre.getText().toString();
        String comunid = comunidadAuto.getText().toString();
        String distri = distritoAuto.getText().toString();
        String parce = codigo.getText().toString();
        String placa = placa_vehicu.getText().toString();
        String celu = cel.getText().toString();
        String direc = direccion.getText().toString();
        String refe = referencia.getText().toString();
        String nomCobra = nombreCobra.getText().toString();
        String QuienEntrega = quienEntrega.getText().toString();
        String loteProveedor = txt_codigoProveedor.getText().toString();
        String guiaRemision = txt_guiaRemision.getText().toString();
        String maxIDPROV;
        int a;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params){
            try {
                if(comprobarDNI.trim().equals("")|| nomb.trim().equals("") || comunid.trim().equals("") || distri.trim().equals("") || placa.trim().equals("")){
                    z = "Faltan ingresar algunos datos";
                }else{
                    Cursor res = adapterSQLite.selectMostrarUltimoIDPROV();
                    if(res!= null && res.getCount()>0) {
                        if (res.moveToNext()) {
                            maxIDPROV=res.getString(0);
                        }
                    }
                    a=Integer.parseInt(maxIDPROV);
                    a=a+1;
                    mostrarProveedorMP(comprobarDNI);
                    if(idAllProvSQLite != null){
                        Boolean result1 = adapterSQLite.UpdateProveedor(idAllProvSQLite,celu,comunid,distri,refe,obtenerTipo,parce);
                        if(result1 == true) {
                            isSuccess = true;
                            z="EXISTE";
                        }
                    }else{
                        Boolean result = adapterSQLite.insertProveedor(String.valueOf(a),comprobarDNI,nomb,celu,comunid,distri,direc,refe,obtenerTipo,parce);
                        if(result == true) {
                            isSuccess = true;
                            z="GUARDADO";
                        }else{
                            isSuccess =false;}
                    }
                    mostrarProveedorMP(comprobarDNI);
                    if(ch_Add.isChecked()==true){
                        Boolean rsr = adapterSQLite.UpdateCompra(idAllCompra,idAllProvSQLite,nomCobra,QuienEntrega,placa,comunid,distri,parce,celu,loteProveedor,guiaRemision);
                        if(rsr == true) {
                            isSuccess = true;
                            //z="ACTUALIZADO CH";
                        }else{
                            isSuccess =false;}
                    }else{
                        Boolean rs = adapterSQLite.UpdateCompra(idAllCompra,idAllProvSQLite,nomb,nomb,placa,comunid,distri,parce,celu,loteProveedor,guiaRemision);
                        if(rs == true) {
                            isSuccess = true;
                            //z="ACTUALIZADO";
                        }else{
                            isSuccess =false;}
                    }

                }
            }catch (Exception e){
                z="ERROR " +e.getMessage()+" / "+maxIDPROV;
                isSuccess = false;
            }
            return z;
        }
        @Override
        protected void onPostExecute(String r){
            progressBar.dismiss();
            try {
                if(isSuccess){
                    Toast.makeText(getApplicationContext(),z,Toast.LENGTH_SHORT).show();

                    InsertarPesoProducto dialogo = new InsertarPesoProducto();
                    Bundle bundle = new Bundle();
                    bundle.putString("TEXT",idAllCompra);
                    dialogo.setArguments(bundle);
                    dialogo.show((FormatoCompra_Activity.this).getSupportFragmentManager(),"Image Dialog");

                    //Toast.makeText(getApplicationContext(),idAllCompra,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),z,Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"ERROR "+e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }
    }

    public class UpdateCompra extends AsyncTask<String,String,String>{
        String z = "";
        Boolean isSuccess = false;
        String loteProveedor = txt_codigoProveedor.getText().toString();
        String guiaRemision = txt_guiaRemision.getText().toString();
        @Override
        protected void onPreExecute(){
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params){
            try{
                Boolean rs = adapterSQLite.UpdateCompraGuiaLote(idAllCompra,loteProveedor,guiaRemision);
                if(rs == true) {
                    isSuccess = true;
                }else{
                    isSuccess =false;}
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
                intent.putExtra("tipo_User",tipoUSer);
                intent.putExtra("tipo_Nota",tipoNota);
                //Toast.makeText(getApplicationContext(),tipoNota,Toast.LENGTH_LONG).show();
                startActivity(intent);
                finish();
            }else{
                Toast.makeText(getApplicationContext(),"Error " + r,Toast.LENGTH_LONG).show();
            }
        }
    }
    /*
    public void agregar_proveedorMP(String dni,String nombre,String comunidad,String distrito,String direccion,String parcela,String tipo,String celular,String referencia) {
        try {
            PreparedStatement pst = conexion.ConnectionDB().prepareStatement("{ CALL SPAPP_INALL_PROV (?,?,?,?,?,?,?,?,?)}");
            pst.setString(1, dni);
            pst.setString(2, nombre);
            pst.setString(3, celular);
            pst.setString(4, comunidad);
            pst.setString(5, distrito);
            pst.setString(6, direccion);
            pst.setString(7, referencia);
            pst.setString(8, tipo);
            pst.setString(9, parcela);
            pst.executeQuery();
            //Toast.makeText(getApplicationContext(), "Registrado Correctamente", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), "Proveedor "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void update_proveedorMP(String telefo,String comuni,String distrit,String refere,String tip,String parce,String idPro) {
        try {
            PreparedStatement pst = conexion.ConnectionDB().prepareStatement("{ CALL SPAPP_UPDATO_PROV (?,?,?,?,?,?,?)}");
            pst.setString(1, telefo);
            pst.setString(2, comuni);
            pst.setString(3, distrit);
            pst.setString(4, refere);
            pst.setString(5, tip);
            pst.setString(6, parce);
            pst.setString(7, idPro);
            pst.executeQuery();
            //Toast.makeText(getApplicationContext(), "Actualizado Correctamente", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), " Update Proveedor "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void verificarRegistroProv(String veri){
        try {
            PreparedStatement stmt = conexion.ConnectionDB().prepareStatement("{ CALL SPAPP_SEENDVALDNI_PROVMP (?)}");
            stmt.setString(1,veri);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                idProveedor = rs.getString("ID_PROVEEDOR");
            }
        }catch (Exception e){
            //Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
*/
    public void limpiar_datos(){
        idProvSQLite = null;
        nombre.setText("");
        comunidadAuto.setText("");
        distritoAuto.setText("");
        direccion.setText("");
        codigo.setText("");
        placa_vehicu.setText("");
        cel.setText("");
        //dni_cobra.setText("");
        nombreCobra.setText("");
        referencia.setText("");
    }
}