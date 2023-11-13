package w.kipu.kipu.login.Produccion;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.ItemFormato;
import w.kipu.kipu.ITEM.ItemLiProduc;
import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.MenuControlFormato.FormatoControl;
import w.kipu.kipu.login.Produccion.ui.mainProduccion.TitleAdapter;

public class ProduccionFormat extends AppCompatActivity {

    FloatingActionButton fab_menu;
    String id_usuario;
    String Titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produccion_format);
        TitleAdapter titleAdapter = new TitleAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pagerProduccion);
        viewPager.setAdapter(titleAdapter);
        TabLayout tabs = findViewById(R.id.tabsProduccion);
        tabs.setupWithViewPager(viewPager);

        String pruebaNotify = getIntent().getStringExtra("mensa");
        Toast.makeText(getApplication(),pruebaNotify,Toast.LENGTH_SHORT).show();

        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            id_usuario =(String) b.get("id");
        }

        fab_menu = findViewById(R.id.fab_menuProduccion);
        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Titulo = "Formato Control";
                cuadro_dialogo();
            }
        });
    }
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    CONEXION conexion = new CONEXION();
    Spinner sp_lisFormato,sp_lisSubFormat,sp_linea;
    EditText txt_serieDialog;
    EditText txt_numserieDialog;
    Button btn_regSerie;
    String nser,serie,desFor,codLiProd,desLinea,idVal,desSubFormat,producto;
    ArrayList<ItemLiProduc>  ListaProducto = new ArrayList<>();

    public void cuadro_dialogo(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProduccionFormat.this);
        final View view = getLayoutInflater().inflate(R.layout.activity_app_dialog_serie,null);

        txt_numserieDialog =view.findViewById(R.id.txt_numSerieAPPDialogSer);
        btn_regSerie = view.findViewById(R.id.btn_registrarSerieFormatoControl);
        sp_lisFormato = view.findViewById(R.id.sp_listaFormatoAppDialogSer);
        txt_serieDialog = view.findViewById(R.id.txt_serieAppDialogSer);
        sp_linea = view.findViewById(R.id.sp_lineaAppDialogSer);
        sp_lisSubFormat = view.findViewById(R.id.sp_listaSubFormatoAppDialogSer);
        sp_linea.setAdapter(new AdapterLiProduc(getApplication(),R.layout.item_li_produc_spinner, ListLiProduc()));
        sp_lisFormato.setAdapter(new AdapterFormato(getApplication(),R.layout.item_formato_spinner,SETBFORMAT()));
        builder.setView(view);
        final AlertDialog dialogo = builder.create();
        dialogo.show();
        sp_lisFormato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int getid = adapterView.getSelectedItemPosition();
                Object item = adapterView.getItemAtPosition(position);
                desFor = ((ItemFormato) item).getCod();
                //Toast.makeText(getApplicationContext(),desFor,Toast.LENGTH_LONG).show();
                sp_lisSubFormat.setAdapter(new AdapterFormato(getApplication(),R.layout.item_formato_spinner,SETBVAL(desFor)));
                if(getid == 0){
                    producto = "QUINUA";
                }else if(getid == 1){
                    producto = "CHIA";
                }else if(getid == 2){
                    txt_serieDialog.setText("");
                    txt_numserieDialog.setText("");
                }else if(getid == 3){
                    txt_serieDialog.setText("");
                    txt_numserieDialog.setText("");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sp_lisSubFormat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                serie = ((ItemFormato) item).getCod();
                desSubFormat = ((ItemFormato) item).getDes();
                txt_serieDialog.setText(serie);
                Lista_SEENDN(serie);
                txt_numserieDialog.setText(nser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        sp_linea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                codLiProd = ((ItemLiProduc) item).getCod();
                desLinea = ((ItemLiProduc) item).getDesc();
                //Toast.makeText(getApplicationContext(),codLiProd+"/"+desLinea,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btn_regSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cod = txt_serieDialog.getText().toString();
                String nu = txt_numserieDialog.getText().toString();
                String pendiente = "1";
                if(cod.equals("") || nu.equals("")){
                    Toast.makeText(getBaseContext(), "campos vacios",Toast.LENGTH_SHORT).show();
                }else{
                        try {
                        //Toast.makeText(getBaseContext(), num,Toast.LENGTH_SHORT).show();
                        INFORCON(nu,serie,codLiProd);
                        SEIDTBFORCON(nu,cod,codLiProd);
                        //Toast.makeText(getBaseContext(), idVal+"/"+codLiProd,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), FormatoControl.class);
                        intent.putExtra("serie",cod);
                        intent.putExtra("numSerie",nu);
                        intent.putExtra("LiProduc",codLiProd);
                        intent.putExtra("dlinea",desLinea);
                        intent.putExtra("dserie",desSubFormat);
                        intent.putExtra("producto",producto);
                        intent.putExtra("pendi",pendiente);
                        intent.putExtra("escariBatch",serie);
                        startActivity(intent);
                        dialogo.dismiss();
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Intente nuevamente !!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void SEIDTBFORCON(String numfor, String codfor,String codlinea){
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SEIDTBFORCON (?,?,?)}");
            pst1.setString(1,numfor);
            pst1.setString(2,codfor);
            pst1.setString(3,codlinea);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()){
                idVal = rs.getString("ID");
            }
        }catch (Exception e){
        }
    }

    public void Lista_SEENDN(String se){
        try {
            PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call SPAPP_SEN (?)}");
            pst1.setString(1,se);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()){
                nser = rs.getString("numero");
            }
        }catch (Exception e){
        }
    }
    public void INFORCON(String numser, String ser,String codlinea) throws SQLException {
        PreparedStatement pst = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_INFORCON (?,?,?)}");
        pst.setString(1, numser);
        pst.setString(2, ser);
        pst.setString(3, codlinea);
        pst.executeUpdate();
        Toast.makeText(getBaseContext(), "Creado Correctamente",Toast.LENGTH_SHORT).show();
    }

    public List<ItemFormato> SETBFORMAT(){
        ArrayList<ItemFormato>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SETBFORMAT }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                dataListaPuesto.add(new ItemFormato(rs.getString("CODFORMATO"),rs.getString("DESFORMATO"),rs.getString("DESFORMATO")));
            }
        }catch (Exception e){
        }
        return dataListaPuesto;
    }
    public List<ItemFormato> SETBVAL(String CODFORMATO){
        ArrayList<ItemFormato>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SETBVAL (?)}");
            pst1.setString(1, CODFORMATO);
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                dataListaPuesto.add(new ItemFormato(rs.getString("CODSUBFORMATO"),rs.getString("DESSUBFORMATO"),rs.getString("DESFORMATO")));
            }
        }catch (Exception e){
        }
        return dataListaPuesto;
    }
    public List<ItemLiProduc> ListLiProduc(){
        ListaProducto.clear();
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{ call SPAPP_SELIPROD }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                ListaProducto.add(new ItemLiProduc(rs.getString("CODPROD"),rs.getString("DESPROD"),rs.getString("DESCRIPCION")));
            }
        }catch (Exception e){
            Toast.makeText(getBaseContext(),"Conexión inválida",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
}