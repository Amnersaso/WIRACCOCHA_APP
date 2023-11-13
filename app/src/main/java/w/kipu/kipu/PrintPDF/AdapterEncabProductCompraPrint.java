package w.kipu.kipu.PrintPDF;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import w.kipu.kipu.ITEM.Item_EncabProductCompraPrint;
import w.kipu.kipu.R;

public class AdapterEncabProductCompraPrint extends RecyclerView.Adapter<AdapterEncabProductCompraPrint.ViewHolder> {

    public AdapterEncabProductCompraPrint(Context context, List<Item_EncabProductCompraPrint> ncomprasLista) {
        this.context = context;
        this.ncomprasLista = ncomprasLista;
    }
    public Context context;
    public List<Item_EncabProductCompraPrint> ncomprasLista;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView numSer,dni,productor,comunidad,distrito,/*parcela,*/tipo,placa,nomProdFirma,dniProdFirma,nomUserFirma,dniUserFirma;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.numSer = itemView.findViewById(R.id.txt_numSerieProductCompraPrint);
            this.dni = itemView.findViewById(R.id.TXT_DNI);
            this.productor = itemView.findViewById(R.id.txt_nombreProductCompraPrint);
            this.comunidad = itemView.findViewById(R.id.txt_comunidadProductCompraPrint);
            this.distrito = itemView.findViewById(R.id.txt_distritoProductCompraPrint);
            //this.parcela = itemView.findViewById(R.id.txt_parcelaProductCompraPrint);
            this.tipo = itemView.findViewById(R.id.txt_tipProductorProductCompraPrint);
            this.placa = itemView.findViewById(R.id.txt_placaProductCompraPrint);
            this.nomProdFirma = itemView.findViewById(R.id.txt_nombreProdFirma);
            this.dniProdFirma = itemView.findViewById(R.id.txt_dniProdFirma);
            this.nomUserFirma = itemView.findViewById(R.id.txt_nombreUserFirma);
            this.dniUserFirma = itemView.findViewById(R.id.txt_dniUserFirma);
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
        holder.numSer.setText(((Item_EncabProductCompraPrint) this.ncomprasLista.get(position)).getNumSer());
        holder.dni.setText(((Item_EncabProductCompraPrint) this.ncomprasLista.get(position)).getDni());
        holder.productor.setText(((Item_EncabProductCompraPrint) this.ncomprasLista.get(position)).getProductor());
        holder.comunidad.setText(((Item_EncabProductCompraPrint)this.ncomprasLista.get(position)).getComunidad());
        holder.distrito.setText(((Item_EncabProductCompraPrint) this.ncomprasLista.get(position)).getDistrito());
        //holder.parcela.setText(((Item_EncabProductCompraPrint) this.ncomprasLista.get(position)).getParcela());
        holder.tipo.setText(((Item_EncabProductCompraPrint) this.ncomprasLista.get(position)).getTipo());
        holder.placa.setText(((Item_EncabProductCompraPrint)this.ncomprasLista.get(position)).getPlaca());
        holder.nomProdFirma.setText(((Item_EncabProductCompraPrint)this.ncomprasLista.get(position)).getNomProdFirma());
        holder.dniProdFirma.setText(((Item_EncabProductCompraPrint)this.ncomprasLista.get(position)).getDniProdFirma());
        holder.nomUserFirma.setText(((Item_EncabProductCompraPrint)this.ncomprasLista.get(position)).getNomUserFirma());
        holder.dniUserFirma.setText(((Item_EncabProductCompraPrint)this.ncomprasLista.get(position)).getDniUserFirma());
    }
    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}