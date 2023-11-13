package w.kipu.kipu.formtocompra.MostrarListaProductos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ITEM.ItemMostrarListaProducto;
import w.kipu.kipu.R;

public class AdapterMostrarListaProducto  extends RecyclerView.Adapter<AdapterMostrarListaProducto.ViewHolder>{

    private View.OnClickListener listener;
    public List<ItemMostrarListaProducto> ncomprasLista;
    public List<ItemMostrarListaProducto> ncomprasListaFull;


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_descripcion,txt_cantidad,txt_peso,txt_IDComDetalleCompra,txt_SerieDetalleCompra;
        Context context;
        RecyclerView recyclerView;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.txt_descripcion = itemView.findViewById(R.id.txt_fc_descripItem);
            this.txt_cantidad = itemView.findViewById(R.id.txt_fc_cantidadItem);
            this.txt_peso = itemView.findViewById(R.id.txt_fc_pesoItem);
            this.txt_IDComDetalleCompra = itemView.findViewById(R.id.txt_fc_IDComDetalleCompra);
            this.txt_SerieDetalleCompra = itemView.findViewById(R.id.txt_fc_SerieDetalleCompra);
            this.context = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context con =  view.getContext();
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        ItemMostrarListaProducto click = ncomprasLista.get(pos);
                        String produ = click.getProducto();
                        String numer = "1";
                        String idcom = txt_IDComDetalleCompra.getText().toString();
                        String serie = txt_SerieDetalleCompra.getText().toString();
                        Intent intent = new Intent(con,MostrarListaProductoRegistrados.class);
                        intent.putExtra("numAdapMostrarListProd" ,numer);
                        intent.putExtra("tituloAdapMostrarListProd",produ);
                        intent.putExtra("idCompradapMostrarListProd" ,idcom);
                        intent.putExtra("serieCompraAdapMostrarListProd" ,serie);
                        con.startActivity(intent);
                    }
                }
            });
        }
        void listenersclock() {
        }
    }
    public Context context;

    public AdapterMostrarListaProducto(List<ItemMostrarListaProducto> comidaLista, Context context) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
        this.context = context;
    }
    LinearLayout layout;
    public int n = 0;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_pesos_formato_compra, parent, false);
//            LinearLayout layout = view.findViewById(R.id.lilayout_Precio);
//            ViewGroup.LayoutParams params = layout.getLayoutParams();
//            params.height = 0;
//            params.width = 1000;
//            layout.setLayoutParams(params);
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt_descripcion.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getProducto());
        holder.txt_cantidad.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getSaco());
        holder.txt_peso.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getPesoRedondo());
        holder.txt_IDComDetalleCompra.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getIdComp());
        holder.txt_SerieDetalleCompra.setText(((ItemMostrarListaProducto) this.ncomprasLista.get(position)).getSerieComp());
        holder.listenersclock();
    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}