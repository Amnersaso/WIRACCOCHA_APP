package w.kipu.kipu.login.Produccion.ui.mainProduccion.AdapterFragmentControl;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ITEM.ItemPaletizado;
import w.kipu.kipu.R;

public class AdapterPaletizado extends RecyclerView.Adapter<AdapterPaletizado.ViewHolder> {

    public List<ItemPaletizado> detalleLista;
    public List<ItemPaletizado> detalleListaFull;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        EditText txt_lote,txt_numdoc,txt_produc,txt_lineaturno,txt_pIngreso,txt_pSalida;
        TextView txt_fecha;
        ImageButton btn_detalle;
        Context context;
        CardView cardView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_lote = itemView.findViewById(R.id.txt_loteItemPaletizado);
            this.txt_numdoc = itemView.findViewById(R.id.txt_numdocItemPaletizado);
            this.txt_fecha = itemView.findViewById(R.id.txt_fechaItemPaletizado);
            this.txt_produc = itemView.findViewById(R.id.txt_producItemPaletizado);
            this.txt_lineaturno = itemView.findViewById(R.id.txt_lineTurnoItemPaletizado);
            this.txt_pIngreso = itemView.findViewById(R.id.txt_pIngresoItemPaletizado);
            this.txt_pSalida = itemView.findViewById(R.id.txt_pSalidaItemPaletizado);
            this.btn_detalle = itemView.findViewById(R.id.btn_detalleItemPaletizado);
            this.cardView = itemView.findViewById(R.id.cd_paletizado);
            this.linearLayout = itemView.findViewById(R.id.ll_paletizado);
            this.context = itemView.getContext();
        }

        void listenersclock() {
           this.btn_detalle.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(view.getContext(),"Si",Toast.LENGTH_SHORT).show();
               }
           });
        }

        @Override
        public void onClick(final View view) {
        }
    }

    public AdapterPaletizado(List<ItemPaletizado> comidaLista) {
        this.detalleLista = comidaLista;
        this.detalleListaFull = new ArrayList<>(comidaLista);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_paletizado, parent, false);

        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt_lote.setText(((ItemPaletizado) this.detalleLista.get(position)).getLote());
        holder.txt_numdoc.setText(((ItemPaletizado) this.detalleLista.get(position)).getNumdoc());
        holder.txt_fecha.setText(((ItemPaletizado) this.detalleLista.get(position)).getFecha());
        holder.txt_produc.setText(((ItemPaletizado) this.detalleLista.get(position)).getProduc());
        holder.txt_lineaturno.setText(((ItemPaletizado) this.detalleLista.get(position)).getLinea());
        holder.txt_pIngreso.setText(((ItemPaletizado) this.detalleLista.get(position)).getPingreso());
        holder.txt_pSalida.setText(((ItemPaletizado) this.detalleLista.get(position)).getPsalida());
        holder.listenersclock();
        String in = holder.txt_pIngreso.getText().toString();
        String sal = holder.txt_pSalida.getText().toString();
        double ingreso = Double.parseDouble(in);
        double salida = Double.parseDouble(sal);
        String verde = "#c8e6c9";
        String amarillo = "#ffecb3";
        String rojo = "#ffccbc";
        //Toast.makeText(holder.itemView.getContext(), ingreso +"/",Toast.LENGTH_SHORT).show();

        if(ingreso == salida){
            holder.linearLayout.setBackgroundColor(Color.parseColor(verde));
        }else if(ingreso > salida){
            holder.linearLayout.setBackgroundColor(Color.parseColor(amarillo));
        }else if(ingreso < salida){
            holder.linearLayout.setBackgroundColor(Color.parseColor(rojo));
        }
    }

    public int getItemCount() {
        return this.detalleLista.size();
    }

}