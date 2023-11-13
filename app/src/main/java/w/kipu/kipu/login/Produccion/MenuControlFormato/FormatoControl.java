package w.kipu.kipu.login.Produccion.MenuControlFormato;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.ItemActividad;
import w.kipu.kipu.ITEM.ItemLoteCant;
import w.kipu.kipu.ITEM.ItemMPLista;
import w.kipu.kipu.ITEM.ItemTiProActividad;
import w.kipu.kipu.ITEM.Item_Operarios;
import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.DialogoFormatoControl.AdapterActividad;
import w.kipu.kipu.login.Produccion.DialogoFormatoControl.AdapterOperarios;
import w.kipu.kipu.login.Produccion.DialogoFormatoControl.AdapterTiProActividad;
import w.kipu.kipu.login.Produccion.DialogoFormatoControl.DialogAddOperarioFormatControl;
import w.kipu.kipu.login.Produccion.DialogoFormatoControl.DialogoAddActividadFormaControl;
import w.kipu.kipu.login.Produccion.ProduccionFormat;

public class FormatoControl extends AppCompatActivity implements View.OnKeyListener {

    Button btn_addOperario,btn_addDetalle,btn_guardar;
    Spinner sp_mpLista,sp_TiPro,sp_loteCant;
    RecyclerView rv_operario,rv_actividad;
    AdapterOperarios adapterOperarios;
    AdapterActividad adapterActividad;
    CONEXION conexion = new CONEXION();
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    Button btn_callOperario;
    SwipeRefreshLayout sw_refres;
    String pendiente,codLinea,serie,numSerie,codTiPro,desTiPro,codProd,desProd,vacio,titulo,producto,codprodLoteCant,loteLoteCant,fechaLoteCant,cantLoteCant;
    String codTiProUpdate,loteUpdate,codProdUpdate,prodUpdate,desTiProUpdate;
    String cantDispo;
    String tiproSE,prodSE,loteSE,canDisponibleSE,fechaCreacionSE,cantSE,humeSE,obsSE,cantIN;
    EditText txt_cantDisponible,txt_cant,txt_humedad,txt_obs;
    TextView txt_titulo,txt_fechaLoteCant;
    ArrayList<String> DATASETIPRODLOT_TIPRO = new ArrayList<>();
    ArrayList<String> DATASETIPRODLOT_PROD = new ArrayList<>();
    ArrayList<String> DATASETIPRODLOT_LOTE = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formato_control);
        sp_TiPro = findViewById(R.id.sp_mpFormatoControl);
        sp_mpLista = findViewById(R.id.sp_mpListaFormatoControl);
        btn_addOperario = findViewById(R.id.btn_addOperarioFormatoControl);
        btn_addDetalle = findViewById(R.id.btn_addDetalleActFormatoControl);
        rv_operario = findViewById(R.id.rv_operariosFormatoControl);
        rv_actividad = findViewById(R.id.rv_activiFormatoControl);
        txt_cantDisponible = findViewById(R.id.txt_cantDisponibleFormatControl);
        btn_callOperario = findViewById(R.id.btn_callOperario);
        btn_guardar = findViewById(R.id.btn_guardarFormatoControl);
        txt_humedad = findViewById(R.id.txt_humedadFormatoControl);
        txt_cant = findViewById(R.id.txt_cantFormatControl);
        txt_fechaLoteCant = findViewById(R.id.txt_fechaCreacionFormatoControl);
        txt_titulo = findViewById(R.id.txt_titleFormatControl);
        txt_obs = findViewById(R.id.txt_obsFormatControl);
        sp_loteCant = findViewById(R.id.sp_lotecantFormatoControl);
        rv_actividad.setLayoutManager(new LinearLayoutManager(getApplication()));
        rv_operario.setLayoutManager(new LinearLayoutManager(getApplication()));
        txt_cant.setOnKeyListener(this);
        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            serie =(String) b.get("serie");
            numSerie =(String) b.get("numSerie");
            codLinea =(String) b.get("LiProduc");
            titulo = (String)b.get("dserie");
            producto = (String)b.get("producto");
            pendiente =(String)b.get("pendi");

            codTiProUpdate =(String)b.get("codTiPro");
            loteUpdate =(String)b.get("lote");
            codProdUpdate =(String)b.get("codProd");
            prodUpdate =(String)b.get("Prod");
            desTiProUpdate =(String)b.get("desTiPro");
            obsSE=(String)b.get("obs");
        }
        //Toast.makeText(getBaseContext(),serie+"/"+numSerie+"/"+codLinea+"/"+pendiente,Toast.LENGTH_LONG).show();
        Block_Text();
        txt_titulo.setText(titulo);
        txt_obs.setText(obsSE);
        txt_obs.clearFocus();

        if(pendiente.equals("2")){
            //Toast.makeText(getApplication(),"si se dio",Toast.LENGTH_SHORT).show();
            SETIPRODLOT(codTiProUpdate,prodUpdate,loteUpdate);
            SENUMSERPROD(numSerie,serie,codLinea,codTiProUpdate,loteUpdate,codProdUpdate);
            btn_addOperario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogAddOperarioFormatControl dialogAddOperacionFormatControl = new DialogAddOperarioFormatControl();
                    dialogAddOperacionFormatControl.show(getSupportFragmentManager(),"");
                    txt_obs.clearFocus();
                }
            });
            btn_addDetalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cantidad = txt_cant.getText().toString();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    DialogFragment dialogo =  DialogoAddActividadFormaControl.newInstance(cantidad,prodUpdate,codTiProUpdate,codProdUpdate);
                    //Toast.makeText(getApplication(),cantidad+"/"+prodUpdate+"/"+codTiProUpdate,Toast.LENGTH_SHORT).show();
                    dialogo.show(fragmentManager, "tagAlerta");
                    txt_obs.clearFocus();
                }
            });
            sw_refres = findViewById(R.id.sw_refreshFormatoControl);
            sw_refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    btn_callOperario.callOnClick();
                    sw_refres.setRefreshing(false);
                }
            });
            btn_callOperario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterOperarios = new AdapterOperarios(ListaOCMP(serie,numSerie,codLinea));
                    rv_operario.setAdapter(adapterOperarios);
                    rv_operario.setHasFixedSize(true);
                    adapterActividad = new AdapterActividad(Lista_SETBACT(numSerie,serie,codLinea));
                    rv_actividad.setAdapter(adapterActividad);
                    rv_actividad.setHasFixedSize(true);
                }
            });
        }else{
            //Toast.makeText(getApplication(),"parece que no se dio",Toast.LENGTH_SHORT).show();
            sw_refres = findViewById(R.id.sw_refreshFormatoControl);
            sw_refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    btn_callOperario.callOnClick();
                    sw_refres.setRefreshing(false);
                }
            });
            String accion = "ENTRADA";
            sp_TiPro.setAdapter(new AdapterTiProActividad(getApplication(),R.layout.item_tipro_actividad_spinner,ListdataTiPro(accion,serie)));
            sp_TiPro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Object item = adapterView.getItemAtPosition(i);
                    codTiPro = ((ItemTiProActividad) item).getCod();
                    desTiPro = ((ItemTiProActividad) item).getDesc();
                    //Toast.makeText(getBaseContext(), codTiPro+"/"+desTiPro, Toast.LENGTH_SHORT).show();
                    sp_mpLista.setAdapter(new AdapterMPList(getApplication(),R.layout.item_mplista_spinner,SEMP(codTiPro,producto)));
                    sp_loteCant.setAdapter(new AdapterLoteCant(getApplication(),R.layout.item_lote_cant_spinner,SELOCAN(codTiPro)));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            sp_loteCant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Object item = adapterView.getItemAtPosition(i);
                    codprodLoteCant = ((ItemLoteCant) item).getcodPro();
                    loteLoteCant = ((ItemLoteCant) item).getLote();
                    fechaLoteCant = ((ItemLoteCant) item).getFecha();
                    cantLoteCant = ((ItemLoteCant) item).getCant();
                    txt_cantDisponible.setText(cantLoteCant);
                    txt_fechaLoteCant.setText(fechaLoteCant);
                    sp_mpLista.setAdapter(new AdapterMPList(getApplication(),R.layout.item_mplista_spinner,SEMP(codTiPro,loteLoteCant)));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            sp_mpLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Object item = adapterView.getItemAtPosition(i);
                    codProd = ((ItemMPLista) item).getCod();
                    desProd = ((ItemMPLista) item).getProd();
                    //Toast.makeText(getBaseContext(), codProd, Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            btn_callOperario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterOperarios = new AdapterOperarios(ListaOCMP(serie,numSerie,codLinea));
                    rv_operario.setAdapter(adapterOperarios);
                    rv_operario.setHasFixedSize(true);
                    adapterActividad = new AdapterActividad(Lista_SETBACT(numSerie,serie,codLinea));
                    rv_actividad.setAdapter(adapterActividad);
                    rv_actividad.setHasFixedSize(true);
                }
            });
            btn_addOperario.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogAddOperarioFormatControl dialogAddOperacionFormatControl = new DialogAddOperarioFormatControl();
                    dialogAddOperacionFormatControl.show(getSupportFragmentManager(),"");
                }
            });
            btn_addDetalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cant = txt_cant.getText().toString();
                    if(cant.equals("") ||cant.length()==0){
                        Toast.makeText(getBaseContext(), "Falta agregar cantidad", Toast.LENGTH_SHORT).show();
                        btn_addDetalle.setEnabled(false);
                    }else{
                        btn_addDetalle.setEnabled(true);
                        String cantidad = txt_cant.getText().toString();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        DialogFragment dialogo =  DialogoAddActividadFormaControl.newInstance(cantidad,desProd,codTiPro,codProd);
                        dialogo.show(fragmentManager, "tagAlerta");
                    }
                }
            });
        }

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(FormatoControl.this);
                builder.setMessage("SEGURO QUE QUIERE GUARDAR LOS CAMBIOS");
                builder.setCancelable(true);
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String cant = txt_cant.getText().toString();
                        String hume = txt_humedad.getText().toString();
                        String est,accion;
                        String obs = txt_obs.getText().toString();
                        SEVALIDCANT(numSerie,serie,codLinea,cant);
                        if(hume.equals("") || cant.equals("")) {
                            Toast.makeText(getBaseContext(), "Faltan algunos campos", Toast.LENGTH_LONG).show();
                        }else{
                            if(pendiente.equals("2")){
                                accion = "U";
                                est="1";
                                UPDFORCON(numSerie,serie,codLinea,codTiProUpdate,desTiProUpdate,codProdUpdate,prodUpdate,loteUpdate,cant,hume,est,obs,accion);
                                SEIDDFORCON(numSerie,serie,codLinea);
                                //Toast.makeText(getBaseContext(), "-2-"+numSerie+"/"+serie+"/"+codLinea+"/"+codTiProUpdate+"/"+desTiProUpdate+"/"+codProdUpdate+"/"+prodUpdate+"/"+loteUpdate+"/"+cant+"/"+hume+"/"+est, Toast.LENGTH_LONG).show();
                                if(vacio.equals("0")){
                                    Toast.makeText(getBaseContext(), "No se pudo realizar la operacion ", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getBaseContext(), "Modificado correctamente", Toast.LENGTH_LONG).show();
                                    limpiar();
                                    finish();
                                }
                            }else if(pendiente.equals("1")){
                                est="1";
                                accion = "I";
                                UPDFORCON(numSerie,serie,codLinea,codTiPro,desTiPro,codProd,desProd,loteLoteCant,cant,hume,est,obs,accion);
                                SEIDDFORCON(numSerie,serie,codLinea);
                                //Toast.makeText(getBaseContext(), "-1-"+numSerie+"/"+serie+"/"+codLinea+"/"+codTiPro+"/"+desTiPro+"/"+codProd+"/"+desProd+"/"+loteLoteCant+"/"+cant+"/"+hume+"/"+est, Toast.LENGTH_LONG).show();
                                if(vacio.equals("0")){
                                    Toast.makeText(getBaseContext(), "No se pudo realizar la operacion ", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getBaseContext(), "Registrado correctamente", Toast.LENGTH_LONG).show();
                                    limpiar();
                                    finish();
                                }
                            }
                        }

                        String prueba = "prueba de notificacion";
                        NotificationCompat.Builder noti = new NotificationCompat.Builder(getBaseContext())
                                .setSmallIcon(R.drawable.hoja)
                                .setContentTitle("Nuevo ingreso")
                                .setAutoCancel(true);
                        Intent intent = new Intent(FormatoControl.this, ProduccionFormat.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("mensa",prueba);

                        PendingIntent pendingIntent = PendingIntent.getActivity(FormatoControl.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        noti.setContentIntent(pendingIntent);

                        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0,noti.build());
                        Toast.makeText(getBaseContext(), "llego", Toast.LENGTH_SHORT).show();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String txt_ca = txt_cant.getText().toString();
                INCANTTBGEN(txt_ca,numSerie,serie,codLinea);
            }
        }, 7);

        return false;
    }

    @Override
    public void onBackPressed() {
        salir();
    }

    public void salir(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(FormatoControl.this);
        builder.setMessage("SEGURO QUE QUIERE CANCELAR EL REGISTRO");
        builder.setCancelable(true);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void limpiar(){
        txt_cant.setText("");
        txt_cantDisponible.setText("");
        txt_humedad.setText("");
        txt_obs.setText("");
    }

    public void Block_Text(){
        if(pendiente.equals("2")){
            txt_cant.setEnabled(false);
            txt_humedad.setEnabled(false);
            sp_TiPro.setEnabled(false);
            sp_loteCant.setEnabled(false);
            sp_mpLista.setEnabled(false);
            //Toast.makeText(getBaseContext(),"disabled",Toast.LENGTH_LONG).show();
        }else if(pendiente.equals("") || pendiente.length()==0){
            txt_cant.setEnabled(true);
            txt_humedad.setEnabled(true);
            sp_TiPro.setEnabled(true);
            sp_loteCant.setEnabled(true);
            sp_mpLista.setEnabled(true);
            //Toast.makeText(getBaseContext(),"enabled",Toast.LENGTH_LONG).show();
        }
    }
    public void INCANTTBGEN(String cnt, String nser, String ser,String clinea){
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_INCANTTBGEN (?,?,?,?)}");
            pst1.setString(1,cnt);
            pst1.setString(2,nser);
            pst1.setString(3,ser);
            pst1.setString(4,clinea);
            pst1.executeUpdate();
        }catch (Exception e){
        }
    }

    public void SENUMSERPROD(String nser, String ser,String clinea,String cTiPro,String lot,String cProd){
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SENUMSERPROD (?,?,?,?,?,?)}");
            pst1.setString(1,nser);
            pst1.setString(2,ser);
            pst1.setString(3,clinea);
            pst1.setString(4,cTiPro);
            pst1.setString(5,lot);
            pst1.setString(6,cProd);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()){
                cantSE = rs.getString("CANT");
                humeSE = rs.getString("HUMEDAD");
                txt_cant.setText(cantSE);
                txt_humedad.setText(humeSE);
            }
        }catch (Exception e){
        }
    }

    public void SETIPRODLOT(String ctipro, String prodc,String lot){
        try {
            PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call SPAPP_SETIPRODLOT (?,?,?)}");
            pst1.setString(1,ctipro);
            pst1.setString(2,prodc);
            pst1.setString(3,lot);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()){
                tiproSE = rs.getString("TIPRO");
                prodSE = rs.getString("PRODUCTO");
                loteSE = rs.getString("LOTE");
                canDisponibleSE = rs.getString("CANTIDAD");
                fechaCreacionSE = rs.getString("FECHA");
                DATASETIPRODLOT_TIPRO.add(tiproSE);
                DATASETIPRODLOT_PROD.add(prodSE);
                DATASETIPRODLOT_LOTE.add(loteSE);
                txt_cantDisponible.setText(canDisponibleSE);
                txt_fechaLoteCant.setText(fechaCreacionSE);
            }
            sp_TiPro.setAdapter(new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,DATASETIPRODLOT_TIPRO));
            sp_loteCant.setAdapter(new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,DATASETIPRODLOT_LOTE));
            sp_mpLista.setAdapter(new ArrayAdapter<>(getApplication(),android.R.layout.simple_spinner_item,DATASETIPRODLOT_PROD));
        }catch (Exception e){
        }
    }

    public void SEIDDFORCON(String numfor, String codfor,String codlinea){
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SEFORCON (?,?,?)}");
            pst1.setString(1,numfor);
            pst1.setString(2,codfor);
            pst1.setString(3,codlinea);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()){
               vacio = rs.getString("VACIO");
            }
        }catch (Exception e){
        }
    }

    public void SEVALIDCANT(String numS, String Ser,String codLi,String cantDis){
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SEVALIDCANT (?,?,?,?)}");
            pst1.setString(1,numS);
            pst1.setString(2,Ser);
            pst1.setString(3,codLi);
            pst1.setString(4,cantDis);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()){
                cantDispo = rs.getString("DISPONIBLE");
            }
        }catch (Exception e){
        }
    }

    public List<ItemLoteCant> SELOCAN(String tpro){
        List<ItemLoteCant>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement st= conexionOfi.ConnectionDB().prepareStatement("{call SPAPP_SELOCAN (?)}");
            st.setString(1,tpro);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ItemLoteCant(rs.getString("TIPPRO"),rs.getString("CODIGO_PRO"),rs.getString("LOTE"),rs.getString("FECHA"),rs.getString("CANTIDAD")));
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),"Conexión inválida actividad",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }

    public void UPDFORCON(String numfor, String codfor,String codlinea,String ctipro,String dtipro,String cprod,String dprod,String lot, String can,String hume,String estad,String obs,String ac){
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_UPDFORCON (?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            pst1.setString(1,numfor);
            pst1.setString(2,codfor);
            pst1.setString(3,codlinea);
            pst1.setString(4,ctipro);
            pst1.setString(5,dtipro);
            pst1.setString(6,cprod);
            pst1.setString(7,dprod);
            pst1.setString(8,lot);
            pst1.setString(9,can);
            pst1.setString(10,hume);
            pst1.setString(11,estad);
            pst1.setString(12,obs);
            pst1.setString(13,ac);
            pst1.executeUpdate();
        }catch (Exception e){
            Toast.makeText(getApplication(),"Error al ingresar datos",Toast.LENGTH_SHORT).show();
        }
    }

    public List<ItemActividad> Lista_SETBACT(String nu, String se, String codLi){
        List<ItemActividad>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement st= conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SETBACT (?,?,?)}");
            st.setString(1,nu);
            st.setString(2,se);
            st.setString(3,codLi);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ItemActividad(rs.getString("DESACTIVIDAD"),rs.getString("DESTIPRO"),rs.getString("DESPRODUCT"),rs.getString("SACOS"),rs.getString("PESO"),rs.getString("DESSUBLINEA"),rs.getString("OBSERVACION")));
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),"Conexión inválida actividad",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }

    public List<Item_Operarios> ListaOCMP(String ser,String num,String codLi){
        List<Item_Operarios>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement st= conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SEIDALLOPERARIO (?,?,?)}");
            st.setString(1,ser);
            st.setString(2,num);
            st.setString(3,codLi);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ListaProducto.add(new Item_Operarios(rs.getString("OPERARIO"),rs.getString("ACTIVIDAD")));
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),"Conexión inválida operario",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }

    public List<ItemTiProActividad> ListdataTiPro(String acc,String SerieAcc){
        ArrayList<ItemTiProActividad>  dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SETIPRO (?,?)}");
            pst1.setString(1,acc);
            pst1.setString(2,SerieAcc);
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                dataListaPuesto.add(new ItemTiProActividad(rs.getString("NOMPROD"),rs.getString("COD")));
            }
        }catch (Exception e){
        }
        return dataListaPuesto;
    }

    public List<ItemMPLista> SEMP(String c,String cpro){
        ArrayList<ItemMPLista>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try {
            PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{ call SPAPP_SEBYLOCAN (?,?)}");
            pst1.setString(1,c);
            pst1.setString(2,cpro);
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                ListaProducto.add(new ItemMPLista(rs.getString("CODIGO_PRO"),rs.getString("PRODUCTO")));
            }
        }catch (Exception e){
            Toast.makeText(getApplication(),"Conexión inválida tipro",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }

}