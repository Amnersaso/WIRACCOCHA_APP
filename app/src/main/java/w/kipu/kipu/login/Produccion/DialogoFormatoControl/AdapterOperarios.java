package w.kipu.kipu.login.Produccion.DialogoFormatoControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ITEM.Item_Operarios;
import w.kipu.kipu.R;

public class AdapterOperarios extends RecyclerView.Adapter<AdapterOperarios.ViewHolder> {

    public List<Item_Operarios> ncomprasLista;
    public List<Item_Operarios> ncomprasListaFull;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText txt_nombre,txt_actividad;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_nombre = itemView.findViewById(R.id.txt_nombreOperario);
            this.txt_actividad = itemView.findViewById(R.id.txt_actividadOperario);
            this.context = itemView.getContext();
        }

    }
    public AdapterOperarios(List<Item_Operarios> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_operarios, parent, false);
              return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_nombre.setText(this.ncomprasLista.get(position).getNombre());
        holder.txt_actividad.setText(this.ncomprasLista.get(position).getDni());

    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}



