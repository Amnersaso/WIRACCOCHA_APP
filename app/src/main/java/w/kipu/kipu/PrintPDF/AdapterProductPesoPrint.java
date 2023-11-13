package w.kipu.kipu.PrintPDF;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import w.kipu.kipu.ITEM.Item_Product_Peso_Print;
import w.kipu.kipu.R;

public class AdapterProductPesoPrint extends RecyclerView.Adapter<AdapterProductPesoPrint.ViewHolder> {

        public AdapterProductPesoPrint(Context context, List<Item_Product_Peso_Print> ncomprasLista) {
            this.context = context;
            this.ncomprasLista = ncomprasLista;
        }
        public Context context;
        public List<Item_Product_Peso_Print> ncomprasLista;


        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView product,peso,total,pesos,lote,observacion,subTotal,pUnit;
            Context context;

            public ViewHolder(View itemView) {
                super(itemView);
                this.product = itemView.findViewById(R.id.txt_product);
                this.peso = itemView.findViewById(R.id.txt_peso);
                this.total = itemView.findViewById(R.id.txt_total);
                this.pesos = itemView.findViewById(R.id.txt_pesos);
                this.lote = itemView.findViewById(R.id.txt_lote);
                this.observacion = itemView.findViewById(R.id.txt_observacionPrint);
                this.subTotal = itemView.findViewById(R.id.txt_subTotal);
                this.pUnit = itemView.findViewById(R.id.txt_pUnitPesoprint);
                this.context = itemView.getContext();
            }
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_peso_print, parent, false);
            return new ViewHolder(view) {
            };
        }
        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.product.setText(((Item_Product_Peso_Print) this.ncomprasLista.get(position)).getProducto());
            holder.peso.setText(((Item_Product_Peso_Print) this.ncomprasLista.get(position)).getPeso());
            holder.total.setText(((Item_Product_Peso_Print) this.ncomprasLista.get(position)).getTotal());
            holder.pesos.setText(((Item_Product_Peso_Print)this.ncomprasLista.get(position)).getPesos());
            holder.lote.setText(((Item_Product_Peso_Print)this.ncomprasLista.get(position)).getLote());
            holder.observacion.setText(((Item_Product_Peso_Print)this.ncomprasLista.get(position)).getObservacion());
            holder.subTotal.setText(((Item_Product_Peso_Print)this.ncomprasLista.get(position)).getSubtotal());
            holder.pUnit.setText(((Item_Product_Peso_Print)this.ncomprasLista.get(position)).getpUnit());
        }
        public int getItemCount() {
            return this.ncomprasLista.size();
        }
}
