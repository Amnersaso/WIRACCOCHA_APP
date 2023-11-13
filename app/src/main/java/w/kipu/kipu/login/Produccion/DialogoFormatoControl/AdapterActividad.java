package w.kipu.kipu.login.Produccion.DialogoFormatoControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import w.kipu.kipu.ITEM.ItemActividad;
import w.kipu.kipu.R;

public class AdapterActividad extends RecyclerView.Adapter<AdapterActividad.ViewHolder> {

    public List<ItemActividad> ncomprasLista;
    public List<ItemActividad> ncomprasListaFull;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText txt_acti,txt_tipro,txt_prod,txt_saco,txt_peso,txt_subActi;
        Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_acti = itemView.findViewById(R.id.txt_actividadItemActi);
            this.txt_tipro = itemView.findViewById(R.id.txt_tiproItemActi);
            this.txt_prod = itemView.findViewById(R.id.txt_proItemActi);
            this.txt_saco = itemView.findViewById(R.id.txt_sacoItemActi);
            this.txt_peso = itemView.findViewById(R.id.txt_pesoItemActi);
            this.txt_subActi = itemView.findViewById(R.id.txt_subactiviItemActi);
            this.context = itemView.getContext();
        }
    }
    public AdapterActividad(List<ItemActividad> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad, parent, false);
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_acti.setText(this.ncomprasLista.get(position).getActividad());
        holder.txt_tipro.setText(this.ncomprasLista.get(position).getTipro());
        holder.txt_prod.setText(this.ncomprasLista.get(position).getProd());
        holder.txt_saco.setText(this.ncomprasLista.get(position).getSaco());
        holder.txt_peso.setText(this.ncomprasLista.get(position).getPeso());
        holder.txt_subActi.setText(this.ncomprasLista.get(position).getSubActivi());
    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}