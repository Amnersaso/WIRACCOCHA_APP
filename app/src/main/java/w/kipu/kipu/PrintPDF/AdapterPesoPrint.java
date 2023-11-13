package w.kipu.kipu.PrintPDF;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemPesoPrintPDF_One;
import w.kipu.kipu.R;

public class AdapterPesoPrint extends RecyclerView.Adapter<AdapterPesoPrint.ViewHolder> {

    public AdapterPesoPrint(Context context, List<ItemPesoPrintPDF_One> ncomprasLista) {
        this.context = context;
        this.ncomprasLista = ncomprasLista;
    }
    public Context context;
    public List<ItemPesoPrintPDF_One> ncomprasLista;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView product;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.product = itemView.findViewById(R.id.txt_prodItemMPListaSpinner);
            this.context = itemView.getContext();
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mplista_spinner, parent, false);
        return new ViewHolder(view) {
        };
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.product.setText(((ItemPesoPrintPDF_One) this.ncomprasLista.get(position)).getPeso());
    }
    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}
