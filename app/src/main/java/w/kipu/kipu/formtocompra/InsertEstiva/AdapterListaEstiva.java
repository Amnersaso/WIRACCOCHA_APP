package w.kipu.kipu.formtocompra.InsertEstiva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ITEM.ItemEstiva;
import w.kipu.kipu.R;

public class AdapterListaEstiva extends RecyclerView.Adapter<AdapterListaEstiva.ViewHolder> {
    public List<ItemEstiva> ncomprasLista;
    public List<ItemEstiva>ncomprasListaFull;

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView productor;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.productor = itemView.findViewById(R.id.txt_estivaItem);
            this.context = itemView.getContext();
        }
    }
    public AdapterListaEstiva(List<ItemEstiva> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estiva, parent, false);

        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.productor.setText(((ItemEstiva) this.ncomprasLista.get(position)).getEstiva());
    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}



