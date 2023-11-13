package w.kipu.kipu.gac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemDocumentAsigSerieNum;
import w.kipu.kipu.R;
import w.kipu.kipu.formtocompra.FormatoCompra_Activity;
import w.kipu.kipu.formtocompra.MostrarListaProductos.AdapterDocumentAsigSerNum;
import w.kipu.kipu.gac.ui.main.SectionsPagerAdapter_GAC;

public class ActivityMenuGac extends AppCompatActivity {

    FabSpeedDial fab_menu;
    TextView titulo,num;
    Spinner spinner;
    EditText txtserieSQLite;
    Button generar;
    String texto,valRP,tipoReg,TipoDocu,id_usuario,obtSerieSQLite,obtenerSer,obtNumdoc_RP,tipoUser;
    int resul = 0;
    android.app.AlertDialog progressBar;
    AdapterSQLite myDB;
    AdapterDocumentAsigSerNum prod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_gac);
        SectionsPagerAdapter_GAC sectionsPagerAdapter = new SectionsPagerAdapter_GAC(this, getSupportFragmentManager());
        progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(this).setCancelable(false).build();

        myDB = new AdapterSQLite(getApplicationContext());
        ViewPager viewPager = findViewById(R.id.view_pager_gac);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_gac);
        tabs.setupWithViewPager(viewPager);

        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            id_usuario =(String) b.get("id");
            tipoUser =(String) b.get("tipo_User");
        }

        fab_menu = findViewById(R.id.fab_menu_gac);
        fab_menu.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.floating_Ncompra_GAC){
                    texto="Nota de Compra";
                    valRP = "0";
                    tipoReg = "NC";
                    TipoDocu="OCMP";
                    cuadro_dialogo();
                }
                return true;
            }
            @Override
            public void onMenuClosed() {
            }
        });
    }

    public int sumar(String numero){
        try {
            if (numero != null){
                resul = Integer.parseInt(numero);
                resul = resul + 1;
            }else
                resul = 0;
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return resul;
    }
    public List<ItemDocumentAsigSerieNum> listSerieUsu(String idUs, String tipDoc){
        ArrayList<ItemDocumentAsigSerieNum> dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        Cursor res = myDB.mostrarListaSerieNumUser(idUs,tipDoc);
        if(res!= null && res.getCount()>0){
            while (res.moveToNext())
                dataListaPuesto.add(new ItemDocumentAsigSerieNum(res.getString(0),res.getString(1),res.getString(2)));
        }
        return dataListaPuesto;
    }
    public void cuadro_dialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMenuGac.this);
        final View view = getLayoutInflater().inflate(R.layout.activity_dialog__n_compra,null);
        titulo= view.findViewById(R.id.txt_FormatoCompraTitulo);
        spinner =view.findViewById(R.id.sp_SerieUsuario);
        num = view.findViewById(R.id.txt_numNCompra);
        txtserieSQLite = view.findViewById(R.id.txt_serieSQLite);

        prod = new AdapterDocumentAsigSerNum(getApplication(),R.layout.item_document_asig_serie_num, listSerieUsu(id_usuario,TipoDocu) );
        spinner.setAdapter(prod);
        titulo.setText(texto);
        obtSerieSQLite = txtserieSQLite.getText().toString();
        generar = view.findViewById(R.id.btn_generarNCompra);
        builder.setView(view);
        final AlertDialog dialogo = builder.create();
        dialogo.show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                obtenerSer = ((ItemDocumentAsigSerieNum) item).getSerie();
                obtNumdoc_RP = ((ItemDocumentAsigSerieNum) item).getNum();
                sumar(obtNumdoc_RP);
                String s= String.valueOf(resul);
                num.setText(s);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });
    }
    public void ShowDialog(){
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("CREAR ORDEN DE COMPRA");
        obtSerieSQLite = txtserieSQLite.getText().toString();
        builder.setMessage("¿ Seguro que desea crear esta Orden de Compra?"+"\n"+"\n"+"> "+obtenerSer+"-"+num.getText().toString());
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CheckInsertarCompra checkInsertarCompra = new CheckInsertarCompra();
                checkInsertarCompra.execute("");
                builder.setCancelable(true);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        builder.create().show();
    }
    public class CheckInsertarCompra extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;
        String numer = num.getText().toString();
        String idCompraSQLite;
        @Override
        protected void onPreExecute(){
            progressBar.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try{
                Boolean result = myDB.insertCompra(null,id_usuario,null,null,null,null,null,null,
                        null,null,null,obtenerSer,numer,null,null,null,null,
                        null,"PROCESO",null,null,null,tipoReg,tipoUser,null ,null);
                if(result == true){
                    Cursor res = myDB.selectTopCompra();
                    if(res!= null && res.getCount()>0){
                        if (res.moveToNext()){
                            idCompraSQLite = res.getString(0);
                            isSuccess = true;
                            updateTBSerieNum(id_usuario,obtenerSer,numer);
                            z="ORDEN DE COMPRA CREADA";
                        }
                    }
                }else{
                    isSuccess = false;
                    z="NO SE CREÓ LA ORDEN DE COMPRA";
                }
            }
            catch (Exception ex){
                isSuccess = false;
                z="ERROR "+ex.getMessage();
            }return z;
        }
        @Override
        protected void onPostExecute(String r){
            progressBar.dismiss();
            if(isSuccess){
                Toast.makeText(getApplicationContext(),r,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ActivityMenuGac.this, FormatoCompra_Activity.class);
                    intent.putExtra("idusuarioMenuFotma",id_usuario);
                    intent.putExtra("serieMenuFotma",obtenerSer);
                    intent.putExtra("numDocMenuFotma",numer);
                    intent.putExtra("validarRPMenuFotma",valRP);
                    intent.putExtra("idCompraSQLiteMenuFormato",idCompraSQLite);
                    intent.putExtra("idCompraSQLiteMenuFormatoEstiva",idCompraSQLite);
                    intent.putExtra("tipo_User",tipoUser);
                    startActivity(intent);

            }else{
                Toast.makeText(getApplicationContext(),r,Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void updateTBSerieNum(String id_usu,String serie,String num_doc) {
        try {
            Boolean result = myDB.updateSerieNum(id_usu,serie,num_doc);
            if(result == true){
            }else{
            }
        } catch (Exception e) {
        }
    }
}