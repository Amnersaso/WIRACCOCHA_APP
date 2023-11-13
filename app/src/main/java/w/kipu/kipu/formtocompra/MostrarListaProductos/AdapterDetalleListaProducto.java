package w.kipu.kipu.formtocompra.MostrarListaProductos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemDetalleListaProducto;
import w.kipu.kipu.ITEM.ItemListaProductoDetaComp;
import w.kipu.kipu.R;
import w.kipu.kipu.formtocompra.FormatoCompra_Activity;
import w.kipu.kipu.formtocompra.RegistroPeso;

public class AdapterDetalleListaProducto extends RecyclerView.Adapter<AdapterDetalleListaProducto.ViewHolder> {

    public List<ItemDetalleListaProducto> detalleLista;
    public List<ItemDetalleListaProducto> detalleListaFull;
    public ArrayList<ItemListaProductoDetaComp> dataLista = new ArrayList<>();
    private View.OnClickListener listener;
    CONEXION conexion = new CONEXION();
    AdapterSQLite myDB;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView producto,txt_idCompradetalleProd,txt_serieCompradetalleProd,txt_idDetalleCompra;
        TextInputEditText txt_numDetalle,txt_pesoReal,txt_pesoRedondo;
        EditText txt_observacion;
        //Switch toggleButton;
        ImageButton btn_guardar,btn_eliminar;
        Spinner spinner;
        CONEXION conexion = new CONEXION();
        FormatoCompra_Activity formatoCompraActivity = new FormatoCompra_Activity();
        RegistroPeso registroPeso = new RegistroPeso();
        String producList;
        AdapterSQLite myDB;

        public ViewHolder(View itemView) {
            super(itemView);
            this.producto = itemView.findViewById(R.id.txt_titulodetalleProd);
            this.txt_idDetalleCompra = itemView.findViewById(R.id.txt_idDetalleCompra);
            this.txt_idCompradetalleProd = itemView.findViewById(R.id.txt_idCompradetalleProd);
            this.txt_serieCompradetalleProd = itemView.findViewById(R.id.txt_serieCompradetalleProd);
            this.txt_numDetalle = itemView.findViewById(R.id.txt_numeroSaco);
            this.txt_pesoReal = itemView.findViewById(R.id.txt_pesoReal);
            this.txt_pesoRedondo = itemView.findViewById(R.id.txt_pesoRedondeado);
            //this.toggleButton = itemView.findViewById(R.id.btn_togdetalleProduc);
            this.txt_observacion=itemView.findViewById(R.id.txt_observaDetalleProduct);
            this.btn_guardar=itemView.findViewById(R.id.btn_guardar_DetaCompra);
            this.btn_eliminar=itemView.findViewById(R.id.btn_eliminar_DetaCompra);
            this.spinner=itemView.findViewById(R.id.sp_listaProductoDetalle);
            this.myDB = new AdapterSQLite(itemView.getContext());
        }
        void listenersclock() {
            this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Object item = parent.getItemAtPosition(position);
                    producList = ((ItemListaProductoDetaComp)item).getProd();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        public void actualizarRegistroPeso(String idDeta,String idCompra,String prod,String p_Real,String p_Mod,String obs,String pro_antrigu) {
            try {
                Boolean res = myDB.updateDetaListaPeso1(idDeta,idCompra,prod,p_Real,p_Mod,obs);
                if(res == true){
                    Boolean res2 = myDB.updateDetaListaPeso2(idCompra,prod);
                    if (res2 == true){
                        Boolean res3 = myDB.updateDetaListaPeso3(idCompra,pro_antrigu);
                        if (res3 == true)
                            Toast.makeText(itemView.getContext(),"ACTUALIZADO",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(itemView.getContext(),"FALLA 3",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(itemView.getContext(),"FALLA 2",Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(itemView.getContext(),"FALLA 1",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(itemView.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onClick(final View view) {
        }
    }
    public Context context;
    public AdapterDetalleListaProducto(List<ItemDetalleListaProducto> comidaLista, Context context) {
        this.detalleLista = comidaLista;
        this.detalleListaFull = new ArrayList<>(comidaLista);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemdetalle_listaproducto, parent, false);
        myDB = new AdapterSQLite(view.getContext());
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.producto.setText(((ItemDetalleListaProducto) this.detalleLista.get(position)).getTituloProduct());
        holder.txt_numDetalle.setText(((ItemDetalleListaProducto) this.detalleLista.get(position)).getNumeroProd());
        holder.txt_pesoReal.setText(((ItemDetalleListaProducto) this.detalleLista.get(position)).getPesoReal());
        holder.txt_pesoRedondo.setText(((ItemDetalleListaProducto) this.detalleLista.get(position)).getPesoRedondo());
        holder.txt_observacion.setText(((ItemDetalleListaProducto) this.detalleLista.get(position)).getObservacion());
        holder.txt_idCompradetalleProd.setText(((ItemDetalleListaProducto) this.detalleLista.get(position)).getIdComp());
        holder.txt_idDetalleCompra.setText(((ItemDetalleListaProducto) this.detalleLista.get(position)).getIdDetalle());
        holder.listenersclock();

        String prod = holder.producto.getText().toString();
        mostrarListaProduc();
        holder.spinner.setAdapter(new AdapterListaProductoDetalleCompra(holder.itemView.getContext(),R.layout.item_listaproduc_deta_compra, dataLista));

        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idDetaCompra = holder.txt_idDetalleCompra.getText().toString();
                final String numItem = holder.txt_numDetalle.getText().toString();

                try {
                    //Toast.makeText(holder.itemView.getContext(),idDetaCompra +" / "+ produ +" / "+position,Toast.LENGTH_LONG).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                    builder.setTitle("ELIMINAR");
                    builder.setMessage("¿Seguro que desea eliminar el saco N: "+numItem+" ?");
                    builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myDB.deleteDetalleCompra(idDetaCompra);
                            detalleLista.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, detalleLista.size());
                            holder.itemView.setVisibility(View.GONE);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.setCancelable(true);
                        }
                    });
                    builder.create().show();

                }catch (Exception e){
                    Toast.makeText(holder.itemView.getContext(),"no registrado!! ",Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String numItem = holder.txt_numDetalle.getText().toString();
                final String prod = holder.producto.getText().toString();
                final String pesoReal =  holder.txt_pesoReal.getText().toString();
                final String pesoRedondo = holder.txt_pesoRedondo.getText().toString();
                final String idDetaCompra = holder.txt_idDetalleCompra.getText().toString();
                final String observa = holder.txt_observacion.getText().toString();
                final String idcom = holder.txt_idCompradetalleProd.getText().toString();
                final AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("ACTUALIZAR");
                builder.setMessage("¿Seguro que desea actualizar el saco N: "+numItem+" ?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Boolean res = myDB.updateDetaListaPeso1(idDetaCompra,idcom,prod,pesoReal,pesoRedondo,observa);
                            if(res == true){
                                Boolean res2 = myDB.updateDetaListaPeso2(idcom,prod);
                                if (res2 == true){
                                    Boolean res3 = myDB.updateDetaListaPeso3(idcom,prod);
                                    if (res3 == true)
                                        Toast.makeText(holder.itemView.getContext(),"ACTUALIZADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(holder.itemView.getContext(),"FALLA 3",Toast.LENGTH_SHORT).show();
                                }else
                                    Toast.makeText(holder.itemView.getContext(),"FALLA 2",Toast.LENGTH_SHORT).show();
                            }else
                                Toast.makeText(holder.itemView.getContext(),"FALLA 1",Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(holder.itemView.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.setCancelable(true);
                    }
                });
                builder.create().show();
            }
        });

        if(prod.equals("AGUAYMANTO FRESCO")){
            holder.spinner.setSelection(0);
        }else if(prod.equals("AMARANTO BLANCA")){
            holder.spinner.setSelection(1);
        }else if(prod.equals("AMARANTO BLANCA OBSERVADA")){
            holder.spinner.setSelection(2);
        }else if(prod.equals("CACAO GENERICO")){
            holder.spinner.setSelection(3);
        }else if(prod.equals("CACAO OBSERVADA")){
            holder.spinner.setSelection(4);
        }else if(prod.equals("CAÑIHUA GENERICO")){
            holder.spinner.setSelection(5);
        }else if(prod.equals("CAÑIHUA OBSERVADA")){
            holder.spinner.setSelection(6);
        }else if(prod.equals("CHÍA BLANCA")){
            holder.spinner.setSelection(7);
        }else if(prod.equals("CHÍA BLANCA OBSERVADA")){
            holder.spinner.setSelection(8);
        }else if(prod.equals("CHÍA NEGRA")){
            holder.spinner.setSelection(9);
        }else if(prod.equals("CHÍA NEGRA OBSERVADA")){
            holder.spinner.setSelection(10);
        }else if(prod.equals("JENGIBRE DESHIDRATADO")){
            holder.spinner.setSelection(11);
        }else if(prod.equals("JENGIBRE FRESCO")){
            holder.spinner.setSelection(12);
        }else if(prod.equals("LINAZA DORADA")){
            holder.spinner.setSelection(13);
        }else if(prod.equals("LÚCUMA GENERICO")){
            holder.spinner.setSelection(14);
        }else if(prod.equals("LÚCUMA OBSERVADA")){
            holder.spinner.setSelection(15);
        }else if(prod.equals("MACA AMARILLA")){
            holder.spinner.setSelection(16);
        }else if(prod.equals("MACA AMARILLA OBSERVADA")){
            holder.spinner.setSelection(17);
        }else if(prod.equals("MACA BLANCA")){
            holder.spinner.setSelection(18);
        }else if(prod.equals("MACA BLANCA OBSERVADA")){
            holder.spinner.setSelection(19);
        }else if(prod.equals("MACA NEGRA")){
            holder.spinner.setSelection(20);
        }else if(prod.equals("MACA NEGRA OBSERVADA")){
            holder.spinner.setSelection(21);
        }else if(prod.equals("NUECES DE AMAZONAS GENERICO")){
            holder.spinner.setSelection(22);
        }else if(prod.equals("NUECES DE AMAZONAS OBSERVADA")){
            holder.spinner.setSelection(23);
        }else if(prod.equals("QUINUA AMARILLA")){
            holder.spinner.setSelection(24);
        }else if(prod.equals("QUINUA AMARILLA OBSERVADA")){
            holder.spinner.setSelection(25);
        }else if(prod.equals("QUINUA BLANCA")){
            holder.spinner.setSelection(26);
        }else if(prod.equals("QUINUA BLANCA OBSERVADA")){
            holder.spinner.setSelection(27);
        }else if(prod.equals("QUINUA NEGRA")){
            holder.spinner.setSelection(28);
        }else if(prod.equals("QUINUA NEGRA OBSERVADA")){
            holder.spinner.setSelection(29);
        }else if(prod.equals("QUINUA ROJA")){
            holder.spinner.setSelection(30);
        }else if(prod.equals("QUINUA ROJA OBSERVADA")){
            holder.spinner.setSelection(31);
        }
    }

    public int getItemCount() {
        return this.detalleLista.size();
    }
    public void mostrarListaProduc() {
        dataLista.clear();
        try {
            Cursor res = myDB.selectListaProductUpdate();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    dataLista.add(new ItemListaProductoDetaComp(res.getString(1)));
                }
            }else {
               // Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            //Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
