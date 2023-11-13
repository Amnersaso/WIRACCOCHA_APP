package w.kipu.kipu.formtocompra.MostrarListaProductos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.ncorti.slidetoact.SlideToActView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemDetalleListaProducto;
import w.kipu.kipu.ITEM.ItemMostrarListaProducto;
import w.kipu.kipu.PrintPDF.PrintDocumentPDF;
import w.kipu.kipu.R;

public class MostrarListaProductoRegistrados extends AppCompatActivity{

    CONEXION conexion = new CONEXION();
    RecyclerView recyclerViewListaProd;
    AdapterDetalleListaProducto adapterDetalleListaProducto;
    AdapterUpdateListaProducto adapterUpdateListaProducto;
    AdapterUpdateListaProducto_GAC adapterUpdateListaProducto_gac;
    SlideToActView slideToActView;
    TextView titulo;
    CheckBox addAdalanto;
    LinearLayout linearAdelanto;
    String check="0";
    TextInputEditText txtadelanto,txt_obsCompra;
    String idRP_selet,idCompRP,serieSelectRP,registroPesoValidar,gacRegisPesoValidar,id_usuario,tituloSerieNumer,idAllCompra,idCompraNCompra,tipoUser,tipoNota,numPres,tituloProducto="";
    AdapterSQLite myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto_registrados);
        titulo = findViewById(R.id.txt_tituloMostrarListaProduct);
        slideToActView = (SlideToActView) findViewById(R.id.slide_action);
        recyclerViewListaProd =(RecyclerView) findViewById(R.id.recycler_MostrarLisProduc);
        recyclerViewListaProd.setLayoutManager(new LinearLayoutManager(getApplication().getBaseContext()));
        addAdalanto = findViewById(R.id.ch_adelanto);
        linearAdelanto = findViewById(R.id.linearAdelanto);
        txtadelanto = findViewById(R.id.txt_adelanto);
        txt_obsCompra = findViewById(R.id.txt_obsCompraUpdatePrecio);
        myDB = new AdapterSQLite(this);
        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            numPres=(String) b.get("numAdapMostrarListProd");
            tituloProducto=(String) b.get("tituloAdapMostrarListProd");
            idCompRP = (String) b.get("idCompradapMostrarListProd");
            serieSelectRP = (String) b.get("serieCompraAdapMostrarListProd");
            tipoUser = (String) b.get("tipo_User");
            tipoNota = (String) b.get("tipo_Nota");

            idRP_selet = (String) b.get("idcompraRegistroPeso");
            idCompraNCompra = (String) b.get("idcompraRegistroPesoNCompra");
            registroPesoValidar = (String) b.get("regisPesoRegistroPeso");
            gacRegisPesoValidar = (String) b.get("gacRegistroPeso");
            id_usuario = (String) b.get("idusuarioRegistroPeso");
            tituloSerieNumer = (String) b.get("serieTituloRegistroPeso");
        }

        if(idCompRP != null){
            idAllCompra = idCompRP;
            //Toast.makeText(getApplication(),"If: "+idAllCompra,Toast.LENGTH_LONG).show();
        }else{
            idAllCompra = idRP_selet;
            //Toast.makeText(getApplication(),"If: "+idAllCompra,Toast.LENGTH_LONG).show();
        }
        if(numPres != null ){
            titulo.setText("DETALLE DE PRODUCTO");
            adapterDetalleListaProducto = new AdapterDetalleListaProducto(ObtenerDetalleListaProdRP(idAllCompra,tituloProducto),getApplicationContext());
            recyclerViewListaProd.setAdapter(adapterDetalleListaProducto);
            recyclerViewListaProd.getAdapter().notifyDataSetChanged();
            slideToActView.setVisibility(View.INVISIBLE);
            addAdalanto.setVisibility(View.INVISIBLE);
            txt_obsCompra.setVisibility(View.INVISIBLE);
        }else{
            if(tipoUser.equals("GAC") ){
                //Toast.makeText(getApplication(),"GAC",Toast.LENGTH_LONG).show();
                titulo.setText("ASIGNAR PRECIO");
                adapterUpdateListaProducto_gac = new AdapterUpdateListaProducto_GAC(ObtenerListaProductoRP(idAllCompra),getApplicationContext());
                recyclerViewListaProd.setAdapter(adapterUpdateListaProducto_gac);
                recyclerViewListaProd.getAdapter().notifyDataSetChanged();
                slideToActView.setVisibility(View.VISIBLE);
                addAdalanto.setVisibility(View.VISIBLE);
                txt_obsCompra.setVisibility(View.VISIBLE);
            }else{
                //Toast.makeText(getApplication(),tipoNota,Toast.LENGTH_LONG).show();
                if (tipoNota.equals("NS")){
                    titulo.setText("NOTA DE SERVICIO");
                    adapterUpdateListaProducto = new AdapterUpdateListaProducto(ObtenerListaProductoRP(idAllCompra),getApplicationContext());
                    recyclerViewListaProd.setAdapter(adapterUpdateListaProducto);
                    recyclerViewListaProd.getAdapter().notifyDataSetChanged();
                    slideToActView.setVisibility(View.VISIBLE);
                    addAdalanto.setVisibility(View.INVISIBLE);
                    txt_obsCompra.setVisibility(View.VISIBLE);
                }else{
                    titulo.setText("ASIGNAR PRECIO");
                    adapterUpdateListaProducto = new AdapterUpdateListaProducto(ObtenerListaProductoRP(idAllCompra),getApplicationContext());
                    recyclerViewListaProd.setAdapter(adapterUpdateListaProducto);
                    recyclerViewListaProd.getAdapter().notifyDataSetChanged();
                    slideToActView.setVisibility(View.VISIBLE);
                    addAdalanto.setVisibility(View.VISIBLE);
                    txt_obsCompra.setVisibility(View.VISIBLE);
                }
            }
        }

        ViewGroup.LayoutParams params = linearAdelanto.getLayoutParams();
        params.height = 0;
        params.width = 1000;
        linearAdelanto.setLayoutParams(params);

        addAdalanto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    check = "1";
                    ViewGroup.LayoutParams params = linearAdelanto.getLayoutParams();
                    params.height = 170;
                    params.width = 1000;
                    linearAdelanto.setLayoutParams(params);
                }else{
                    check = "0";
                    ViewGroup.LayoutParams params = linearAdelanto.getLayoutParams();
                    params.height = 0;
                    params.width = 1000;
                    linearAdelanto.setLayoutParams(params);
                }
            }
        });
        slideToActView.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                    try{
                        String adelanto = txtadelanto.getText().toString();
                        String obserCompr = txt_obsCompra.getText().toString();
                        String adelantoAll;
                        if(addAdalanto.isChecked())
                            adelantoAll=adelanto;
                        else
                            adelantoAll=null;
                        Cursor rs = myDB.verificarPrecioTotal_Compra(idAllCompra);
                        if(rs!= null && rs.getCount()>0){
                            if(rs.moveToNext()){
                                Toast.makeText(getApplicationContext(),"FALTA AGREGAR PRECIO AL PRODUCTO: "+rs.getString(1),Toast.LENGTH_LONG).show();
                                slideToActView.resetSlider();
                            }
                        }else {
                            UPCOMP_ADELA(idAllCompra,check,adelantoAll,obserCompr);
                            updateCompraEstado(idAllCompra);
                            Intent intent = new Intent(MostrarListaProductoRegistrados.this, PrintDocumentPDF.class);
                            intent.putExtra("idCompraMostrarListProdRegis_Update",idAllCompra);
                            intent.putExtra("registroPesoMostListProducRegis",registroPesoValidar);
                            intent.putExtra("gacUsusarioMostListProducRegis",gacRegisPesoValidar);
                            intent.putExtra("idusuarioMostListProducRegis",id_usuario);
                            intent.putExtra("tituloSerieNumeroMostListProducRegis",tituloSerieNumer);
                            intent.putExtra("tipo_User",tipoUser);
                            intent.putExtra("tipo_Nota",tipoNota);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"ACTUALIZADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Error!! "+e.getMessage(),Toast.LENGTH_LONG).show();
                        slideToActView.resetSlider();
                    }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (numPres != null){
            super.onBackPressed();
        }else{
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
    public void updateCompraEstado(String idc) throws SQLException{
        Boolean rs = myDB.updateCompraEstado(idc);
        if(rs == true)
            Toast.makeText(getApplication(),"REGISTRADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
    }
    public void UPCOMP_ADELA(String idCom,String soliAde,String adelan,String obs) throws SQLException {
        Boolean rs = myDB.updateCompraAdelanto(idCom,soliAde,adelan,obs);
        if(rs == true)
            Toast.makeText(getApplication(),"REGISTRADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
    }
    public List<ItemMostrarListaProducto> ObtenerListaProductoRP(String idCom){
        List<ItemMostrarListaProducto>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try {
            Cursor rs = myDB.selectMostrarProductAsigPrecio(idCom);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    ListaProducto.add(new ItemMostrarListaProducto(rs.getString(3),rs.getString(2),rs.getString(4),
                            rs.getString(5),rs.getString(6), rs.getString(0),rs.getString(1),rs.getString(7),rs.getString(8),rs.getString(9)));
                }
            }else {
                // Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return ListaProducto;
    }
    public List<ItemDetalleListaProducto> ObtenerDetalleListaProdRP(String id, String prod){
        List<ItemDetalleListaProducto>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            Cursor rs = myDB.selectMostrarListaDetalleProducto(id,prod);
            if(rs!= null && rs.getCount()>0){
                while (rs.moveToNext()){
                    ListaProducto.add(new ItemDetalleListaProducto(rs.getString(2),rs.getString(3),rs.getString(4),
                            rs.getString(5),rs.getString(6),rs.getString(1),rs.getString(0)));
                }
            }else {
                Toast.makeText(getApplication(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"desconectado",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
}