package w.kipu.kipu.login.ListaXEnviar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ITEM.ItemXEnviar;
import w.kipu.kipu.PrintPDF.PrintDocumentPDF;
import w.kipu.kipu.R;

public class AdapterXEnviar extends RecyclerView.Adapter<AdapterXEnviar.ViewHolder> {

    public List<ItemXEnviar> ncomprasLista;
    public List<ItemXEnviar>ncomprasListaFull;

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView productor,txt_idCompra,txt_idProv,txt_lote,peso,numCompra,sacos,total;
        Button btn_detalle,btn_estado;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.productor = itemView.findViewById(R.id.txt_ncompra_nombreproductorXEnviar);
            this.peso = itemView.findViewById(R.id.txt_ncompra_pesoXEnviar);
            this.numCompra = itemView.findViewById(R.id.ncompra_numcompraXEnviar);
            this.sacos = itemView.findViewById(R.id.ncompra_sacoXEnviar);
            this.total = itemView.findViewById(R.id.ncompra_totalXEnviar);
            this.btn_detalle = itemView.findViewById(R.id.btn_ncompraVerDetalleXEnviar);
            this.btn_estado = itemView.findViewById(R.id.btn_estadoXEnviar);
            this.txt_idCompra = itemView.findViewById(R.id.txt_idCompraNCompraXEnviar);
            this.txt_idProv = itemView.findViewById(R.id.txt_idProvNCompraXEnviar);
            this.txt_lote = itemView.findViewById(R.id.txt_ncompraLoteXEnviar);
            this.context = itemView.getContext();
        }
    }
    public AdapterXEnviar(List<ItemXEnviar> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_x_enviar, parent, false);

        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.productor.setText(((ItemXEnviar) this.ncomprasLista.get(position)).getProductor());
        holder.peso.setText(((ItemXEnviar) this.ncomprasLista.get(position)).getPeso());
        holder.sacos.setText(((ItemXEnviar) this.ncomprasLista.get(position)).getSaco());
        holder.total.setText(((ItemXEnviar) this.ncomprasLista.get(position)).getTotal());
        holder.numCompra.setText(((ItemXEnviar) this.ncomprasLista.get(position)).getNumcompra());
        holder.txt_idCompra.setText(((ItemXEnviar) this.ncomprasLista.get(position)).getIdCompra());
        holder.txt_idProv.setText(((ItemXEnviar) this.ncomprasLista.get(position)).getIdProveedor());
        holder.txt_lote.setText(((ItemXEnviar) this.ncomprasLista.get(position)).getLote());

        final String idCompra = holder.txt_idCompra.getText().toString();
        final String idProv = holder.txt_idProv.getText().toString();

        holder.btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.itemView.getContext(),idCompra+" / "+idProv,Toast.LENGTH_SHORT).show();
                Intent enviar = new Intent(holder.itemView.getContext(), PrintDocumentPDF.class);
                enviar.putExtra("idCompraNCompra", idCompra);
                enviar.putExtra("idProvNCompra", idProv);
                holder.itemView.getContext().startActivity(enviar);
            }
        });
    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}



