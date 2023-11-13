package w.kipu.kipu.gac;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dmax.dialog.SpotsDialog;
import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.R;
import w.kipu.kipu.formtocompra.FormatoCompra_Activity;
import w.kipu.kipu.formtocompra.Fragment_gac;

public class gac_Compra extends FragmentActivity {

    FloatingActionButton fab_menu;
    TextView serie,num,titulo;
    Button generar;
    String texto;
    Connection con;
    String obtenerId_usu,obtenerId_comp,obtenerSerie,obtenerNum_doc;
    CONEXION conexion = new CONEXION();
    String gc = "GC01";
    String id_usuario;
    android.app.AlertDialog progressBar;
    String numDocCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gac);

        fab_menu = (FloatingActionButton) findViewById(R.id.fab_menuGAC);

        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(this)
                .setCancelable(false).build();

        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            id_usuario =(String) b.get("id");
        }

        Fragment_gac fragment = new Fragment_gac();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_gac, fragment);
        transaction.commit();

        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numDocCompra = obtenerNum_doc;
                texto="GAC";
                sumar(obtenerNum_doc);
                cuadro_dialogo();
            }

        });
    }
    int resul = 0;
    int resulNumDoc = 0;
    public int sumar(String numero){

        resul = Integer.parseInt(numero);
        resul = resul + 1;
        return resul;
    }


    public void cuadro_dialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(gac_Compra.this);
        final View view = getLayoutInflater().inflate(R.layout.activity_dialog__n_compra,null);

        titulo=(TextView) view.findViewById(R.id.txt_FormatoCompraTitulo);
        //serie=(EditText)view.findViewById(R.id.txt_serieNCompra);
        num=(EditText) view.findViewById(R.id.txt_numNCompra);

        num.setText(String.valueOf(resul));
        serie.setText(obtenerSerie);
        titulo.setText(texto);

        generar=(Button)view.findViewById(R.id.btn_generarNCompra);
        builder.setView(view);
        final AlertDialog dialogo = builder.create();
        dialogo.show();
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckInsertarCompra checkInsertarCompra = new CheckInsertarCompra();
                checkInsertarCompra.execute("");
                dialogo.dismiss();
            }
        });
    }

    public class CheckInsertarCompra extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String ser = serie.getText().toString();
        String numer = num.getText().toString();
        Integer id_usu = Integer.parseInt(id_usuario);

        @Override
        protected void onPreExecute()
        {
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params)
        {
            if(ser.trim().equals("") || numer.trim().equals("")){
                z = "Faltan ingresar algunos datos";
            }else{
                try
                {
                    con = conexion.ConnectionDB_Local();
                    if (con == null){
                        z = "Verificar el acceso a Internet!";
                    }
                    else{
                        insertarCompra(id_usu,ser,numer);
                        isSuccess = true;
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                }
            }
            return z;
        }
        @Override
        protected void onPostExecute(String r)
        {
            progressBar.dismiss();
            Toast.makeText(getApplicationContext(),r,Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Intent intent = new Intent(gac_Compra.this, FormatoCompra_Activity.class);
                intent.putExtra("id",id_usuario);
                intent.putExtra("serie",obtenerSerie);
                intent.putExtra("numDoc",String.valueOf(resul));
                startActivity(intent);
            }
        }
    }

    public void insertarCompra(Integer id_usu,String serie,String num_doc) {
        try {
            PreparedStatement pst = conexion.ConnectionDB_Local().prepareStatement("insert into COMPRA (ID_USUARIO,SERIE,NUM_DOC) values(?,?,?)");
            pst.setInt(1, id_usu);
            pst.setString(2, serie);
            pst.setString(3, num_doc);
            pst.executeUpdate();
        } catch (SQLException e) {
        }
    }

}