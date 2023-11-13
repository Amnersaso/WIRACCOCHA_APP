package w.kipu.kipu.formtocompra.MostrarListaProductos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemMostrarListaProducto;
import w.kipu.kipu.R;

public class AdapterUpdateListaProducto_GAC extends RecyclerView.Adapter<AdapterUpdateListaProducto_GAC.ViewHolder> {

    private View.OnClickListener listener;
    public List<ItemMostrarListaProducto> ncomprasLista;
    public List<ItemMostrarListaProducto>ncomprasListaFull;
    AdapterSQLite myDB;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView producto,txt_IDComDetalleCompra,txt_SerieDetalleCompra;
        TextView pesosNeto,pesoRedondo;
        TextView sacos,txt_totalPrecio;
        Context context;
        EditText txt_precioXKg,txt_observa;//,txt_loteWiraccocha,txt_loteProveedor;
        Switch toggleButton;
        AdapterSQLite myDB;
        String obtnerValor;

        public ViewHolder(View itemView) {
            super(itemView);
            this.producto =  itemView.findViewById(R.id.txt_tituloListaProduct_gac);
            this.pesosNeto =  itemView.findViewById(R.id.txt_pesolistaNeto_gac);
            this.pesoRedondo = itemView.findViewById(R.id.txt_pesolistaRedondo_gac);
            this.sacos =  itemView.findViewById(R.id.txt_sacoListaProducto_gac);
            this.txt_precioXKg =  itemView.findViewById(R.id.txt_precioKG_gac);
            this.txt_totalPrecio= itemView.findViewById(R.id.txt_precioTotalSacos_gac);
//            this.txt_loteWiraccocha =  itemView.findViewById(R.id.txt_asp_loteWiraccocha);
//            this.txt_loteProveedor =  itemView.findViewById(R.id.txt_asp_loteProveedor);
            this.txt_observa =  itemView.findViewById(R.id.txt_observacionPrecioProd_gac);
            this.toggleButton = itemView.findViewById(R.id.btn_togleListaProducto_gac);
            this.txt_IDComDetalleCompra = itemView.findViewById(R.id.txt_IDComDetalleCompra_gac);
            this.txt_SerieDetalleCompra = itemView.findViewById(R.id.txt_SerieDetalleCompra_gac);
            this.context = itemView.getContext();
            this.myDB = new AdapterSQLite(itemView.getContext());
        }

        void listenersclock() {
            this.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                    //formatoCompraActivity.mostrarID_COMPRA();
                    final String idcom = txt_IDComDetalleCompra.getText().toString();
                    final String serie = txt_SerieDetalleCompra.getText().toString();
                    final String prod = producto.getText().toString();
                    final String pUnit = txt_precioXKg.getText().toString();
                    final String observa = txt_observa.getText().toString();
                    final String estado = "compra";
                    final String stock = sacos.getText().toString();
                    final String precioCompra = txt_totalPrecio.getText().toString();
                    final String totalPeso = pesosNeto.getText().toString();
                    final String estado_Calidad = "";
                    if(b){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ViewHolder.this.itemView.getContext());
                        builder1.setMessage("SEGURO QUE DESEA GUARDAR LOS CAMBIOS !!");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "SI",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            if(pUnit.equals("")){
                                                Toast.makeText(ViewHolder.this.itemView.getContext(),"FALTA INGRESAR PRECIO",Toast.LENGTH_SHORT).show();
                                                toggleButton.setEnabled(true);
                                            }else{
                                                InsertarAlmacen_Ubicacion("SINALMA","SINALMA",idcom,prod,null,stock,precioCompra,totalPeso,estado_Calidad);
                                                Boolean result = myDB.updateAsigPrecioDetaCompra(idcom,prod,pUnit,estado,null,observa,null);
                                                if(result == true){
                                                    Boolean rs = myDB.updateCompraTotal(idcom);
                                                    if(rs == true){
                                                        Toast.makeText(ViewHolder.this.itemView.getContext(),"REGISTRADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                                                        toggleButton.setEnabled(false);
                                                    }
                                                }else
                                                    Toast.makeText(ViewHolder.this.itemView.getContext(),"ERROR AL REGISTRAR",Toast.LENGTH_SHORT).show();
                                                InsertarPeso(idcom,prod);
                                            }
                                        }catch (Exception e){
                                            toggleButton.setChecked(false);
                                            e.printStackTrace();
                                            Toast.makeText(ViewHolder.this.itemView.getContext(),"FALTA INGRESAR PRECIO",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        toggleButton.setChecked(false);
                                        dialog.cancel();
                                    }
                                });

                        builder1.create().show();
                    }
                }
            });
            this.txt_precioXKg.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(txt_precioXKg.getText().length()==0){
                    }else{
                        String pUnit = ViewHolder.this .txt_precioXKg.getText().toString();
                        String pesoRed = ViewHolder.this.pesoRedondo.getText().toString();
                        Double res,pU;
                        double pCan = Double.parseDouble(pesoRed);
                        pU = Double.parseDouble(pUnit);
                        res = pU * pCan;
                        txt_totalPrecio.setText(String.valueOf(res));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(txt_precioXKg.getText().length()==0){
                        txt_totalPrecio.setText("0");
                    }else if(txt_precioXKg.getText().equals("")){
                        Toast.makeText(ViewHolder.this.itemView.getContext(),"vacio ",Toast.LENGTH_LONG).show();
                    }else{
                        // ejecutar accion despues de editar edittext
                    }
                }
            });
        }

        public void InsertarAlmacen_Ubicacion(String idAlmacen,String idAlmacenUbica,String idCom,String descrip,String lote,String stock,String precioCompra,String TotalPeso,String esta_cali) throws SQLException        {
            try {
                Boolean result = myDB.insertAlmacenProducto(null,idAlmacen,idAlmacenUbica,idCom,descrip,lote,stock,precioCompra,TotalPeso,esta_cali);
                if(result == true){
                    //Toast.makeText(getApplication(),"Registrado",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getApplication(),"falla",Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                //Toast.makeText(getApplication(),"Error Almacén"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }

        @SuppressLint("Range")
        public void InsertarPeso(String idCompra, String prod){
            SQLiteDatabase db = myDB.getReadableDatabase();
            Cursor result = db.rawQuery("select MAX(PRECIO_UNIT) as PRECIO  from DETALLE_COMPRA WHERE ID_COMPRA = ? AND PRODUCTO = ?",new String[]{idCompra,prod} );
            if(result.moveToFirst()){
                obtnerValor = result.getString(result.getColumnIndex("PRECIO"));
                //Toast.makeText(itemView.getContext(),"EL VALOR ES: "+obtnerValor,Toast.LENGTH_SHORT).show();
            }else
                obtnerValor = "NO HAY VALOR";
            result.close();
        }
    }
    public Context context;

    public AdapterUpdateListaProducto_GAC(List<ItemMostrarListaProducto> comidaLista, Context context) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistaproducto_gac, parent, false);
        myDB = new AdapterSQLite(view.getContext());
        return new ViewHolder(view) {
        };
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.producto.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getProducto());
        holder.pesosNeto.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getPesoNeto());
        holder.sacos.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getSaco());
        holder.pesoRedondo.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getPesoRedondo());
        holder.txt_IDComDetalleCompra.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getIdComp());
        holder.txt_SerieDetalleCompra.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getSerieComp());
        holder.listenersclock();
    }
    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}