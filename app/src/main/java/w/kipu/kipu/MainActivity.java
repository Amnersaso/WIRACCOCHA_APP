package w.kipu.kipu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dmax.dialog.SpotsDialog;
import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    Animation topanim,botonanim;
    ImageView imageHoja,imageLetra;
    android.app.AlertDialog progressBar;
    CONEXION conexionLocal = new CONEXION();
    CONEXION_OFI conexion_ofi = new CONEXION_OFI();
    Connection conAll=null;
    AdapterSQLite myDB;
    String idValidarUpdateSync,estadoValidarUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fade_inicio);

        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(this).setCancelable(false).build();
        getSupportActionBar().hide();
        myDB = new AdapterSQLite(this);

        topanim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        botonanim = AnimationUtils.loadAnimation(this,R.anim.botton_animation);
        imageHoja=(ImageView) findViewById(R.id.imageHoja);
        imageLetra = (ImageView) findViewById(R.id.imageLetra);

        imageHoja.setAnimation(topanim);
        imageLetra.setAnimation(botonanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActualizarDatos actualizarDatos = new ActualizarDatos();
                actualizarDatos.execute("");
            }
        }, 4000);
    }

//    public void verificarPermiso(){
//        if (ContextCompat.checkSelfPermission(this,Manifest.permission.MANAGE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
//            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.MANAGE_EXTERNAL_STORAGE},1);
//        }else{
//            Toast.makeText(this , "ya está permitido ", Toast.LENGTH_LONG).show();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode==1){
//            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this , "permitido", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(this , "denegado", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

    public class ActualizarDatos extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.show();}
        @Override
        protected String doInBackground(String... params){
            try{
                Cursor resUsu = myDB.selectUsuario();
                if(resUsu!= null && resUsu.getCount()>0){
                   z = "USUARIOS ACTUALIZADOS";
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                    myDB.deleteUsusario();
                    insertUsusario();
                    z = "USUARIOS DESCARGADOS";
                }

                Cursor resProduc = myDB.selectProductCompra();
                if(resProduc!= null && resProduc.getCount()>0){
                    z = "PRODUCTOS ACTUALIZADOS";
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                        myDB.deleteProductCompra();
                        insertProductCompra();
                        z = "PRODUCTOS DESCARGADOS";
                }

                Cursor resPVari = myDB.selectProductVariedad();
                if(resPVari!= null && resPVari.getCount()>0){
                    z = "PRODUCTO VARIEDAD ACTUALIZADOS";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                        myDB.deleteProductVariedad();
                        insertProductVariedad();
                        z = "PRODUCTO VARIEDAD DESCARGADOS";
                }

                Cursor resAlma = myDB.selectAlmacen();
                if(resAlma!= null && resAlma.getCount()>0){
                    z = "ALMACÉN ACTUALIZADO";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                    myDB.deleteAlmacen();
                        insertAlamacen();
                        z = "ALMACÉN DESCARGADO";
                }

                Cursor resAUbica = myDB.selectAlmacenUbicacion();
                if(resAUbica!= null && resAUbica.getCount()>0){
                    z = "ALMACÉN UBICACIÓN ACTUALIZADO";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                        myDB.deleteAlmacenUbicacion();
                        insertAlamacenUbicacion();
                        z = "ALMACÉN UBICACIÓN DESCARGADO";
                }

                Cursor resProve = myDB.selectProveedor();
                if(resProve!= null && resProve.getCount()>0){
                    z = "PROVEEDOR ACTUALIZADO";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                    myDB.deleteProveedor();
                        insertProveedor();
                        z = "PROVEEDOR DESCARGADO";
                }

                Cursor resComu = myDB.selectComunidad();
                if(resComu!= null && resComu.getCount()>0){
                    z = "COMUNIDADES ACTUALIZADOS";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                        myDB.deleteComunidad();
                        insertComunidad();
                        z = "COMUNIDADES DESCARGADOS";
                }

                Cursor resDistri = myDB.selectDistrito();
                if(resDistri!= null && resDistri.getCount()>0){
                    z = "DISTRITOS ACTUALIZADOS";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                        myDB.deleteDistrito();
                        insertDistrito();
                        z = "DISTRITOS DESCARGADOS";
                }

                Cursor resObs = myDB.selectObs();
                if(resObs!= null && resObs.getCount()>0){
                    z = "OBSERVACIONES ACTUALIZADAS";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                        myDB.deleteObs();
                        insertObs();
                        z = "OBSERVACIONES DESCARGADAS";
                }

                Cursor resPUpdate = myDB.selectListaProductUpdate();
                if(resPUpdate!= null && resPUpdate.getCount()>0){
                    z = "PRODUCTO UPDATE ACTUALIZADOS";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                        myDB.deleteListaProductUpdate();
                        insertListaProducUpdate();
                        z = "PRODUCTO UPDATE DESCARGADOS";
                }

                Cursor resSerNum = myDB.selectSerieNum();
                if(resSerNum!= null && resSerNum.getCount()>0){
                    z = "SERIE NUMERO ACTUALIZADOS";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                        myDB.deleteSerieNum();
                        insertSerieNum();
                        z = "SERIE NUMERO DESCARGADOS";
                }

                Cursor resCodVarProduc = myDB.selectCod_VariedadProduct();
                if(resCodVarProduc!= null && resCodVarProduc.getCount()>0){
                    z = "YA ESTÁN ACTUALIZADOS";
                    isSuccess = true;
                }else{
                    conAll = conexionLocal.ConnectionDB_Local();
                    myDB.deleteCodVarProduct();
                    insertCod_VariedadProduct();
                    z = "INFORMACION DESCARGADO CORRECTAMENTE";
                    isSuccess = true;
                }
            }
            catch (Exception ex){
                isSuccess = false;
                z = "ERROR / " + ex.getMessage();
            }
            return z;
        }
        @Override
        protected void onPostExecute(String r){
            progressBar.dismiss();
            if(isSuccess){
                StyleableToast.makeText(getApplication(),z,R.style.exampleToast).show();
                //Toast.makeText(MainActivity.this , z, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0]= new Pair<View,String>(imageHoja,"logo_image_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                startActivity(intent,options.toBundle());
                finish();
            }else{
                Toast.makeText(MainActivity.this , r , Toast.LENGTH_LONG).show();
            }
        }
    }
    public void validarUpdate(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALL_TBSYNC}");
            ResultSet rs = pst1.executeQuery();
            if(rs.next()){
                idValidarUpdateSync = rs.getString("IDUPDATE");
                estadoValidarUpdate = rs.getString("ESTADO");
            }
        }catch (Exception e){
        }
    }
    public void insert(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALLPROC_COM_SQLITE }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String idprod = rs.getString("CODIGOPRODUCTO_COMPRA");
                String codprod = rs.getString("NOMBREPRODUCTO_COMPRA");
                String prodP = rs.getString("IDPRODUCTO_COMPRA");
                try {
                    Boolean result = myDB.insertListaProductCompra(idprod,codprod,prodP);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplication(),"Error Producto Compra "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }

    }
    public void insertSerieNum(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALLIN_TBSERNUM }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String idprod = rs.getString("ID_USER");
                String codprod = rs.getString("SERIE");
                String prodP = rs.getString("NUM");
                try {
                    Boolean result = myDB.insertSerieNum(idprod,codprod,prodP);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    //Toast.makeText(getApplication(),"Error Producto Compra "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }

    }
    public void insertProductCompra(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALLPROC_COM_SQLITE }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String cod = rs.getString("CODIGOPRODUCTO_COMPRA");
                String nomb = rs.getString("NOMBREPRODUCTO_COMPRA");
                String idco = rs.getString("IDPRODUCTO_COMPRA");
                try {
                    Boolean result = myDB.insertListaProductCompra(idco,cod,nomb);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    //Toast.makeText(getApplication(),"Error Producto Compra "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }
    }
    public void insertProductVariedad(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SECODPROD_COM_VARI_SQLITE }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String idprod = rs.getString("IDPRODUCTO_VARIEDAD");
                String codprod = rs.getString("CODIGOPRODUCTO_COMPRA");
                String prodP = rs.getString("NOMBREPRODUCTO_VARIEDAD");
                String tippro = rs.getString("TIPPRO");
                String artcod = rs.getString("ARTCOD");
                try {
                    Boolean result = myDB.insertListaProductVariedad(idprod,codprod,prodP,tippro,artcod);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    //Toast.makeText(getApplication(),"Error Producto Variedad "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }
    }
    public void insertUsusario(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALL_USU_SQLITE }");
            ResultSet rs = pst1.executeQuery();
            String id;
            String dni;
            String nombre;
            String telef;
            String direc;
            String login;
            String pass;
            String cargo;
            String estado;
            String idOFI;
            String FCHMOD;
            while(rs.next()){
                id = rs.getString("ID_USUARIO");
                dni = rs.getString("DNI");
                nombre = rs.getString("NOMBRE_COMPLETO");
                telef = rs.getString("TELEFONO");
                direc = rs.getString("DIRECCION");
                login = rs.getString("LOGIN");
                pass = rs.getString("PASSWORD");
                cargo = rs.getString("CARGO");
                estado = rs.getString("ESTADO");
                idOFI = rs.getString("IDCOMPRA_OFI");
                FCHMOD = rs.getString("FCHMOD");

                Boolean result = myDB.insertUsuario(id,dni,nombre,telef,direc,login,pass,cargo,estado,idOFI,FCHMOD);
                if(result == true){
                    //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }
    }
    public void insertProveedor(){
        try {
            PreparedStatement stm = conAll.prepareStatement("{ CALL SPAPP_SEALLPROV_PROVMP }");
            ResultSet rst = stm.executeQuery();
            while (rst.next()){
                String idProveedor = rst.getString("ID_PROVEEDOR");
                String dniProvee = rst.getString("DNI_RUC");
                String nombreCompleto = rst.getString("NOMBRE_COMPLETO");
                String telefono = rst.getString("TELEFONO");
                String comunidad = rst.getString("COMUNIDAD");
                String distrito = rst.getString("DISTRITO");
                String direccion = rst.getString("DIRECCION");
                String referencia = rst.getString("REFERENCIA");
                String tipo = rst.getString("TIPO");
                String parcela = rst.getString("PARCELA");
                try {
                    Boolean result = myDB.insertProveedor(idProveedor,dniProvee,nombreCompleto,telefono,comunidad,distrito,direccion,referencia,tipo,parcela);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"No se pudo ingresar ProveedorMPOFI ",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                   // Toast.makeText(getApplication(),"Error Proveedor "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }
    }
    public void insertComunidad(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_INALL_TBCOMU_SQLITE }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String id = rs.getString("IDCOMUNIDAD");
                String dni = rs.getString("COMUNIDAD");

                    Boolean result = myDB.insertComunidad(id,dni);
                    if(result == true){
                        //Toast.makeText(getApplication(),"REGISTRO COMUNIDAD",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"No se pudo insertar Comunidad",Toast.LENGTH_SHORT).show();
                    }
            }
            //Toast.makeText(getApplication(),"SE INSERTO COMUNIDAD",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
        }
    }
    public void insertDistrito(){
        try {
            PreparedStatement pst1 = conexion_ofi.ConnectionDB().prepareStatement("{call SPAPP_SEDISTRI_OFI}");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String id = rs.getString("GRTJUR_JURISD");
                String dni = rs.getString("GRTJUR_DESCRP");

                Boolean result = myDB.insertDistrito(id,dni);
                if(result == true){
                    //Toast.makeText(getApplication(),"REGISTRO COMUNIDAD",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            //Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    public void insertAlamacen(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALL_ALMACE_SQLITE }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String id = rs.getString("ID_ALMACEN");
                String codig = rs.getString("CODIGO");
                String descripcion = rs.getString("DESCRIPCION");
                String direccion = rs.getString("DIRECCION");
                String telef = rs.getString("TELEFONO");
                try {
                    Boolean result = myDB.insertAlmacen(id,codig,descripcion,direccion,telef);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    //Toast.makeText(getApplication(),"Error Almacén"+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }
    }
    public void insertAlamacenUbicacion(){

        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALL_ALMACE_UBI_SQLITE }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                //usamos el item del almacen
                String idAlmaUbi = rs.getString("ID_ALMACEN_UBICACION");
                String idAlma = rs.getString("CODALMA");
                String ubicacion = rs.getString("UBICACION");
                String descripcion = rs.getString("DESCRIPCION");
                try {
                    Boolean result = myDB.insertAlmacenUbicacion(idAlmaUbi,idAlma,ubicacion,descripcion);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    //Toast.makeText(getApplication(),"Error Almacén ubicación "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }
    }
    public void insertObs(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALL_OBS }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                //usamos el item del almacen
                String obs = rs.getString("DESCRIPCION");
                try {
                    Boolean result = myDB.insertObs(obs);
                    if(result == true){
                       // Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    //Toast.makeText(getApplication(),"Error Almacén ubicación "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }
    }
    public void insertListaProducUpdate(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SELIST_PRODUC }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String obs = rs.getString("PRODUCTO");
                try {
                    Boolean result = myDB.insertListaProducUpdate(obs);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                   // Toast.makeText(getApplication(),"Error Almacén ubicación "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }
    }
    public void insertCod_VariedadProduct(){
        try {
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SEALL_CODVARIEDADPRODUCTO }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                String cod = rs.getString("CODIGO");
                String desc = rs.getString("DESCRIPCION");
                try {
                    Boolean result = myDB.insertCod_VariedadProduc(cod,desc);
                    if(result == true){
                        //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                    }else{
                        //Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    //Toast.makeText(getApplication(),"Error Producto Compra "+e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
        }

    }

}