package w.kipu.kipu.ui.aprobados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ITEM.Item_Aprobado;
import w.kipu.kipu.R;

public class AdapterAprobado extends RecyclerView.Adapter<AdapterAprobado.ViewHolder> implements View.OnClickListener, Filterable {

    public List<Item_Aprobado> ncomprasLista;
    public List<Item_Aprobado>ncomprasListaFull;
    private View.OnClickListener listener;

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Item_Aprobado> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(ncomprasListaFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Item_Aprobado item : ncomprasListaFull) {
                    if (item.getNum().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ncomprasLista.clear();
            ncomprasLista.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView serie,num,fechaEmi,fechaApro,txtUser;
        TextInputEditText proveedor,producto,monto;
        ImageButton btn_verDetalle;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.serie = itemView.findViewById(R.id.txt_serieOCL_OSLAprobado);
            this.num = itemView.findViewById(R.id.txt_numOCL_OSLAprobado);
            this.fechaEmi = itemView.findViewById(R.id.txt_fechaOSL_OCLAprobado);
            this.fechaApro=itemView.findViewById(R.id.txt_fechaAprobado);
            this.txtUser = itemView.findViewById(R.id.txt_nombreAprobado);
            this.proveedor = itemView.findViewById(R.id.txt_proveedorOCL_OSLAprobado);
            this.producto = itemView.findViewById(R.id.txt_productoOCL_OSLAprobado);
            this.monto = itemView.findViewById(R.id.txt_montoOCL_OSLAprobado);

            //this.btn_verDetalle = itemView.findViewById(R.id.btn_verDetalleOCL_OSL);
            //this.btn_verDetalle.setOnClickListener(this);
            this.context = itemView.getContext();
        }
        void listenersclock() {
        }
        @Override
        public void onClick(View view) {
            /*
            if(view.getId()==R.id.btn_verDetalleOCL_OSL){
                int position=getAdapterPosition();
                Context con =  view.getContext();
                String numSerie,s,total,prove,obser,fec;
                s = serie.getText().toString();
                String ser = s.toString().substring(0,3);
                numSerie = num.getText().toString();
                total = monto.getText().toString();
                prove = proveedor.getText().toString();
                obser = producto.getText().toString();
                fec = fecha.getText().toString();

                Intent enviar = new Intent(con, DetalleOcl_Osl.class);
                enviar.putExtra("serie",ser);
                enviar.putExtra("numser",numSerie);
                enviar.putExtra("total",total);
                enviar.putExtra("provee",prove);
                enviar.putExtra("observa",obser);
                enviar.putExtra("fecha",fec);
                con.startActivity(enviar);
                //Toast.makeText(con,ser+" / "+numSerie,Toast.LENGTH_SHORT).show();

            }*/
        }
    }

    public AdapterAprobado(List<Item_Aprobado> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aprobado, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.serie.setText(((Item_Aprobado) this.ncomprasLista.get(position)).getSerie());
        holder.num.setText(((Item_Aprobado) this.ncomprasLista.get(position)).getNum());
        holder.proveedor.setText(((Item_Aprobado) this.ncomprasLista.get(position)).getProvee());
        holder.producto.setText(((Item_Aprobado) this.ncomprasLista.get(position)).getObser());
        holder.monto.setText(((Item_Aprobado) this.ncomprasLista.get(position)).getMonto());
        holder.fechaEmi.setText(((Item_Aprobado) this.ncomprasLista.get(position)).getFech_Emi());
        holder.fechaApro.setText(((Item_Aprobado) this.ncomprasLista.get(position)).getFech_apro());
        holder.txtUser.setText(((Item_Aprobado) this.ncomprasLista.get(position)).getUsua());
        holder.listenersclock();
    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}



