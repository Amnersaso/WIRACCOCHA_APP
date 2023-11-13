package w.kipu.kipu.ui.pendiente;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.ncorti.slidetoact.SlideToActView;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ORDAprobaciones.DetalleOcl_Osl;
import w.kipu.kipu.ITEM.ItemAprobMatePrima;
import w.kipu.kipu.R;

public class AdapterOSL_OCL extends RecyclerView.Adapter<AdapterOSL_OCL.ViewHolder> implements View.OnClickListener, Filterable {

    public List<ItemAprobMatePrima> ncomprasLista;
    public List<ItemAprobMatePrima>ncomprasListaFull;
    public CONEXION_OFI conexionOfi = new CONEXION_OFI();
    private View.OnClickListener listener;

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemAprobMatePrima> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(ncomprasListaFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ItemAprobMatePrima item : ncomprasListaFull) {
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
        TextView serie,num,fecha;
        TextInputEditText proveedor,producto,monto;
        SlideToActView btn_aprobar,btn_desapro;
        ImageButton btn_verDetalle;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.serie = itemView.findViewById(R.id.txt_serieOCL_OSL);
            this.num = itemView.findViewById(R.id.txt_numOCL_OSL);
            this.fecha = itemView.findViewById(R.id.txt_fechaOSL_OCL);
            this.proveedor = itemView.findViewById(R.id.txt_proveedorOCL_OSL);
            this.producto = itemView.findViewById(R.id.txt_productoOCL_OSL);
            this.monto = itemView.findViewById(R.id.txt_montoOCL_OSL);
            this.btn_aprobar= itemView.findViewById(R.id.sa_aprobarOCL_OSL);
            this.btn_desapro=itemView.findViewById(R.id.sa_desaproOCL_OSL);
            this.btn_verDetalle = itemView.findViewById(R.id.btn_verDetalleOCL_OSL);
            this.btn_verDetalle.setOnClickListener(this);
            this.context = itemView.getContext();
        }
        void listenersclock() {
        }
        @Override
        public void onClick(View view) {
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
            }
        }
    }

    public AdapterOSL_OCL(List<ItemAprobMatePrima> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_ocl_osl, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.serie.setText(((ItemAprobMatePrima) this.ncomprasLista.get(position)).getSerie());
        holder.num.setText(((ItemAprobMatePrima) this.ncomprasLista.get(position)).getNum());
        holder.proveedor.setText(((ItemAprobMatePrima) this.ncomprasLista.get(position)).getProveedor());
        holder.producto.setText(((ItemAprobMatePrima) this.ncomprasLista.get(position)).getProducto());
        holder.monto.setText(((ItemAprobMatePrima) this.ncomprasLista.get(position)).getpTotal());
        holder.fecha.setText(((ItemAprobMatePrima) this.ncomprasLista.get(position)).getFecha());
        holder.listenersclock();
        final String user ="NBENDEZU";
        final String serie = holder.serie.getText().toString();
        final String num = holder.num.getText().toString();

        holder.btn_aprobar.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {
                try {
                    String ser = serie.toString().substring(0,3);
                    UpdateAprob(user,ser,num);
                    ncomprasLista.remove(position);
                    notifyItemRangeChanged(position,ncomprasLista.size());
                    //Toast.makeText(holder.itemView.getContext(),"Documento Aprobado " +user+" / "+ser+" / "+num,Toast.LENGTH_SHORT).show();
                    Toast.makeText(holder.itemView.getContext(),"Documento Aprobado",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(holder.itemView.getContext(),"Intente nuevamente!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.btn_aprobar.resetSlider();
        holder.btn_desapro.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {

                try {
                    String ser = serie.toString().substring(0,3);
                    Desaprobar(user,ser,num);
                    ncomprasLista.remove(position);
                    notifyItemRangeChanged(position,ncomprasLista.size());
                    Toast.makeText(holder.itemView.getContext(),"el documento ha sido desaprobado",Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    Toast.makeText(holder.itemView.getContext(),"Intente nuevamente!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.btn_desapro.resetSlider();
    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }

    public void UpdateAprob(String user,String serie,String numero) throws SQLException {
        PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call APROB_ORD (?,?,?)}");
        pst1.setString(1, user);
        pst1.setString(2, serie);
        pst1.setString(3, numero);
        pst1.executeUpdate();
    }
    public void Desaprobar(String user,String serie,String numero) throws SQLException {
        PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call DESAPROB_ORD (?,?,?)}");
        pst1.setString(1, user);
        pst1.setString(2, serie);
        pst1.setString(3, numero);
        pst1.executeUpdate();
    }
}



