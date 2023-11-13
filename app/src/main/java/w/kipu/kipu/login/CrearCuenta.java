package w.kipu.kipu.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.AsyncTask;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dmax.dialog.SpotsDialog;
import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.R;

public class CrearCuenta extends AppCompatDialogFragment {

    EditText txt_dni,txt_nombre,txt_telefono,txt_direc,txt_usu,txt_pas,txt_cor;
    Button btn_regis;
    CONEXION conexion = new CONEXION();
    Connection con;
    android.app.AlertDialog progressBar;
    Spinner sp_carg;
    String cargoNue;
    final static String[] tipoProductor = { "comprador", "gac","producción"};

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_crear_cuenta,null);
        builder.setView(view);

        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(getContext()).setCancelable(false).build();
        txt_dni = view.findViewById(R.id.txt_dniCuentNueva);
        txt_nombre = view.findViewById(R.id.txt_nombreCuentNueva);
        txt_telefono = view.findViewById(R.id.txt_teleCuentNueva);
        txt_direc = view.findViewById(R.id.txt_direcCuentNueva);
        txt_usu = view.findViewById(R.id.txt_usuCuentNueva);
        txt_pas = view.findViewById(R.id.txt_contraCuentNueva);
        txt_cor = view.findViewById(R.id.txt_correoCuentNueva);
        sp_carg = view.findViewById(R.id.sp_cargoCuentNueva);
        btn_regis = view.findViewById(R.id.btn_regCuentaNueva);

        ArrayAdapter prod = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, tipoProductor);
        sp_carg.setAdapter(prod);

        sp_carg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                cargoNue = item.toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            CheckRegUS checkRegUS = new CheckRegUS();
            checkRegUS.execute("");
            }
        });

        return builder.create();
    }

    public class CheckRegUS extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;
        String dni = txt_dni.getText().toString();
        String nombre = txt_nombre.getText().toString();
        String telefono = txt_telefono.getText().toString();
        String direccion = txt_direc.getText().toString();
        String usuario = txt_usu.getText().toString();
        String contrase = txt_pas.getText().toString();
        String correo = txt_cor.getText().toString();
        String estado = "deshabilitado";

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressBar.show();
        }
        @Override
        protected String doInBackground(String... params){
            if(dni.trim().equals("") || nombre.trim().equals("") || usuario.trim().equals("") || contrase.trim().equals(""))
                z = "Faltan datos";
            else{
                try{
                    con = conexion.ConnectionDB_Local();
                    if (con == null){
                        z = "Check Your Internet Access!";
                    }else{
                        try {
                            RegUS(dni,nombre,telefono,direccion,usuario,contrase,cargoNue,correo,estado);
                            z="Registrado Correctamente";
                            isSuccess= true;
                        }catch (Exception e){
                        }

                    }
                }
                catch (Exception ex){
                    isSuccess = false;
                    z = "error de servidor";
                }
            }
            return z;
        }
        @Override
        protected void onPostExecute(String r){
            progressBar.dismiss();
            if(isSuccess){
                Toast.makeText(getActivity(), r , Toast.LENGTH_LONG).show();
                dismiss();
            }else{
                Toast.makeText(getActivity(), r , Toast.LENGTH_LONG).show();
            }
        }
    }

    public void RegUS(String dn, String nomb ,String tele,String dire,String us,String pas,String car,String cor,String est)throws SQLException {
            PreparedStatement pst = conexion.ConnectionDB_Local().prepareStatement("{call Registrar_Usuario (?,?,?,?,?,?,?,?,?)}");
            pst.setString(1, dn);
            pst.setString(2, nomb);
            pst.setString(3, tele);
            pst.setString(4, dire);
            pst.setString(5, us);
            pst.setString(6, pas);
            pst.setString(7, car);
            pst.setString(8, cor);
            pst.setString(9, est);
            pst.executeUpdate();
    }


}