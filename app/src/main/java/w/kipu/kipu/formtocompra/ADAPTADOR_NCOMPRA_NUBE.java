package w.kipu.kipu.formtocompra;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ITEM_NCOMPRA;
import w.kipu.kipu.PrintPDF.PrintDocumentPDF;
import w.kipu.kipu.R;

public class ADAPTADOR_NCOMPRA_NUBE extends RecyclerView.Adapter<ADAPTADOR_NCOMPRA_NUBE.ViewHolder> {
    public List<ITEM_NCOMPRA> ncomprasLista;
    public List<ITEM_NCOMPRA>ncomprasListaFull;
    AdapterSQLite myDB;
    String estado;
    public String comunidad,distrito,tipo,parcela;


    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView productor,txt_idCompra,txt_idProv,txt_lote,txt_tipoRegis,txt_estado;
        TextView peso,txt_idUsuario;
        TextView numCompra;
        TextView sacos;
        TextView total;
        Button btn_detalle;
        ImageButton btn_continuarReg,btn_sync;
        Context context;
        AdapterSQLite myDB;

        public ViewHolder(View itemView) {
            super(itemView);
            this.productor = itemView.findViewById(R.id.txt_ncompra_nombreproductor);
            this.peso = itemView.findViewById(R.id.txt_ncompra_peso);
            this.numCompra = itemView.findViewById(R.id.ncompra_numcompra);
            this.sacos = itemView.findViewById(R.id.ncompra_saco);
            this.total = itemView.findViewById(R.id.ncompra_total);
            this.btn_detalle = itemView.findViewById(R.id.btn_ncompraVerDetalle);
            this.txt_idCompra = itemView.findViewById(R.id.txt_idCompraNCompra);
            this.txt_idProv = itemView.findViewById(R.id.txt_idProvNCompra);
            this.txt_lote = itemView.findViewById(R.id.txt_ncompraLote);
            this.txt_tipoRegis = itemView.findViewById(R.id.txt_tipoRegistroNCompra);
            this.txt_estado = itemView.findViewById(R.id.txt_estadRegistroNCompra);
            this.btn_continuarReg = itemView.findViewById(R.id.btn_continuarRegNCompra);
            this.btn_sync = itemView.findViewById(R.id.btn_syncNCompra);
            this.txt_idUsuario = itemView.findViewById(R.id.txt_idusuarioNCompra);
            this.context = itemView.getContext();
            this.myDB = new AdapterSQLite(itemView.getContext());
        }
    }
    public ADAPTADOR_NCOMPRA_NUBE(List<ITEM_NCOMPRA> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
    }
    android.app.AlertDialog progressBar;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notacompra, parent, false);
        myDB = new AdapterSQLite(view.getContext());
        //progressBar = new SpotsDialog.Builder().setTheme(R.style.waitingDialog).setContext(view.getContext()).setCancelable(false).build();

        return new ViewHolder(view) {
        };
    }

    @Override
    public int getItemViewType(int position) {
        return this.ncomprasLista.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.productor.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getProductor());
        holder.peso.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getPeso());
        holder.sacos.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getSaco());
        holder.total.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getTotal());
        holder.numCompra.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getNumcompra());
        holder.txt_idCompra.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getIdCompra());
        holder.txt_idProv.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getIdProveedor());
        holder.txt_lote.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getLote());
        holder.txt_tipoRegis.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getTipoRegistro());
        holder.txt_estado.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getEstado());
        holder.txt_idUsuario.setText(((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getIdUsu());
        final String tipoCon = ((ITEM_NCOMPRA) this.ncomprasLista.get(position)).getTipoCon();

        final String idCompra = holder.txt_idCompra.getText().toString();
        final String idProv = holder.txt_idProv.getText().toString();
        final String tipoRegis = holder.txt_tipoRegis.getText().toString();
        final String idUsu = holder.txt_idUsuario.getText().toString();
        estado = holder.txt_estado.getText().toString();
        holder.btn_continuarReg.setVisibility(View.INVISIBLE);
        holder.btn_sync.setVisibility(View.INVISIBLE);
        final String nube="nube";

        holder.btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.itemView.getContext(),idCompra+" / "+idProv,Toast.LENGTH_SHORT).show();
                Intent enviar = new Intent(holder.itemView.getContext(), PrintDocumentPDF.class);
                enviar.putExtra("idCompraNCompra", idCompra);
                enviar.putExtra("idProvNCompra", idProv);
                enviar.putExtra("tipoRegistroNCompra", tipoRegis);
                enviar.putExtra("estadoCompraRegistroNCompra", estado);
                enviar.putExtra("nubeNCompraNube", nube);
                enviar.putExtra("tipo_Nota","SERVICIO");

                holder.itemView.getContext().startActivity(enviar);
                //Toast.makeText(holder.itemView.getContext(),idCompra+" / "+idProv+" / "+tipoRegis+" / "+estado,Toast.LENGTH_LONG).show();
            }
        });
    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }


}



