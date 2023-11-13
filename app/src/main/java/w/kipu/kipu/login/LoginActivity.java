package w.kipu.kipu.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dmax.dialog.SpotsDialog;
import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ORDAprobaciones.Aprobaciones;
import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.formtocompra.MenuFormatoCompra;
import w.kipu.kipu.R;
import w.kipu.kipu.gac.ActivityMenuGac;
import w.kipu.kipu.login.Produccion.ProduccionFormat;

public class LoginActivity extends AppCompatActivity {

    EditText usuario,pass;
    Button ingresar;
    android.app.AlertDialog progressBar;
    CONEXION conexion = new CONEXION();
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    Connection con;
    TextView btn_crearCuenta,txtSelect,txtSelectVari;
    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;
    AdapterSQLite adapterSQLite;
    String path;
    String file_name = "WiraPDF.pdf";
    Display mDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        adapterSQLite = new AdapterSQLite(this);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mDisplay = wm.getDefaultDisplay();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(this).setCancelable(false).build();

        btn_crearCuenta = findViewById(R.id.btn_crearCuenta);
        usuario = findViewById(R.id.txt_usuarioLogin);
        pass = findViewById(R.id.txt_passLogin);
        ingresar = findViewById(R.id.btn_ingresarLogin);
        //txtSelect = findViewById(R.id.txtmostrarVariedadSQLite);
        txtSelectVari = findViewById(R.id.txtmostrarVariedadSQLite);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
                 /*
                String txt_user = usuario.getText().toString();
                mostrarProveedorMP(txt_user);

                  */
            }
        });

        btn_crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearCuenta diag = new CrearCuenta();
                diag.show(getSupportFragmentManager(),"example dialog");
            }
        });
    }

//(IDLISTA_PRODUCTOCOMPRA INTEGER PRIMARY KEY AUTOINCREMENT ,IDPRODUCTO_COMPRA TEXT,CODIGOPRODUCTO_COMPRA TEXT,NOMBREPRODUCTO_COMPRA TEXT)");
    public void selectProducCom(){
        Cursor res = adapterSQLite.selectProductCompra();
        StringBuffer stringBuffer = new StringBuffer();
        if(res!= null && res.getCount()>0){
            while (res.moveToNext()){
                stringBuffer.append("ID: "+res.getString(0)+"\n");
                stringBuffer.append("ID_PC: "+res.getString(1)+"\n");
                stringBuffer.append("CODPROD: "+res.getString(2)+"\n");
                stringBuffer.append("NOMPROD: "+res.getString(3)+"\n");
            }
            txtSelect.setText(stringBuffer.toString());
        }else {
            Toast.makeText(getApplication(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
        }
    }
    public void selectProducVarie(){
        Cursor res = adapterSQLite.selectProductCompra();
        StringBuffer stringBuffer = new StringBuffer();
        if(res!= null && res.getCount()>0){
            while (res.moveToNext()){
                stringBuffer.append("ID: "+res.getString(0)+"\n");
                stringBuffer.append("ID_PC: "+res.getString(1)+"\n");
                stringBuffer.append("CODPROD: "+res.getString(2)+"\n");
                stringBuffer.append("NOMPROD: "+res.getString(3)+"\n");
            }
            txtSelectVari.setText(stringBuffer.toString());
        }else {
            Toast.makeText(getApplication(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
        }
    }
    public void selectComunidad(){
        try {
            Cursor res = adapterSQLite.selectComunidad();
            StringBuffer stringBuffer = new StringBuffer();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    stringBuffer.append("ID: "+res.getString(1)+"\n");
                    stringBuffer.append("COMU: "+res.getString(2)+"\n");
                }
                txtSelectVari.setText(stringBuffer.toString());
            }else {
                Toast.makeText(getApplication(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

//    public void mostrarProveedorMP(String dniPro){
//        try {
//            String dniProve = null,nombreCompleto = null,direccionProvee = null;
//            PreparedStatement stmt = conexionOfi.ConnectionDB().prepareStatement("{ CALL SPAPP_SEALLPROVMP_OFI (?)}");
//            stmt.setString(1,dniPro);
//            ResultSet rs = stmt.executeQuery();
//            if(rs.next()){
//                dniProve = rs.getString("CORMVH_NROCTA");
//                nombreCompleto = rs.getString("PVMPRH_NOMBRE");
//                direccionProvee = rs.getString("PVMPRH_DIRECC");
//            }
//            txtSelectVari.setText(dniProve+" / "+nombreCompleto+" / "+direccionProvee);
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//        }
//        /*
//        try {
//            Cursor rs = adapterSQLite.selectProveedorDNI(dniProveedor);
//            if(rs!= null && rs.getCount()>0){
//                if(rs.moveToNext()){
//                    dniProvee = rs.getString(2);
//                    nombreCompleto = rs.getString(3);
//                    telefonoprovee = rs.getString(4);
//                    direccionProvee = rs.getString(7);
//                    referenciaProvee = rs.getString(8);
//                    tipoProvee = rs.getString(9);
//                    parcelaProvee = rs.getString(10);
//                }
//            }else {
//                //Toast.makeText(getApplication(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
//            }
//        }catch (Exception e){
//            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
//        }
//         */
//    }

    public String nomUser = "";
    public String id_usuario = "";
    public String login ="";
    public String tipoUser;
    public class CheckLogin extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;
        String usernam = usuario.getText().toString();
        String passwordd = pass.getText().toString();
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params){
            if(usernam.equals("")|| passwordd.equals(""))
                z = "Faltan datos";
            else{
                try{
                    Cursor res = adapterSQLite.ValidarUsuario(usernam,passwordd);
                    if(res!= null && res.getCount()>0){
                        while (res.moveToNext()){
                            z = res.getString(3);
                            tipoUser = res.getString(8);
                            id_usuario = res.getString(1);
                            login = res.getString(6);
                            nomUser = res.getString(3);
                        }
                        z = nomUser;
                        isSuccess =true;
                    }else {
                        //Toast.makeText(getApplication(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
                        isSuccess = false;
                        z = "Datos incorrectos";
                    }
                }
                catch (Exception ex){
                    isSuccess = false;
                    z = "error de usuario";
                }
            }
            return z;
        }
        @Override
        protected void onPostExecute(String r){
            progressBar.dismiss();
                if(isSuccess){
                    Toast.makeText(LoginActivity.this , "Bienvenido - " +r , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(actividad(LoginActivity.this));
                    if(getApplication()!=null){
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("user",login);
                        intent.putExtra("id",id_usuario);
                        intent.putExtra("idLogin_RP",id_usuario);
                        intent.putExtra("userid",usernam);
                        intent.putExtra("tipo_User",tipoUser);
                        startActivity(intent);
                    }
                }else{
                Toast.makeText(LoginActivity.this , r +" / "+tipoUser+" / "+login , Toast.LENGTH_LONG).show();
                    //Toast.makeText(LoginActivity.this , usernam , Toast.LENGTH_LONG).show();
            }
        }
    }

    public Intent actividad(Context context){
        Intent intent;
        if(tipoUser.equals("OPERACIONES")){
            intent = new Intent(context, Aprobaciones.class);
        }else if(tipoUser.equals("COMPRADOR") || tipoUser.equals("ADMIN") ){
            intent = new Intent(context,MenuFormatoCompra.class);
        }else if(tipoUser.equals("GAC")){
            intent = new Intent(context, ActivityMenuGac.class);
        }else{
            intent = new Intent(context, ProduccionFormat.class);
        }
        return intent;
    }
    @Override
    public void onBackPressed() {
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            finish();
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }
}