package w.kipu.kipu.formtocompra;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemSerieNum;
import w.kipu.kipu.R;

public class AdapterSerieNumero extends RecyclerView.Adapter<AdapterSerieNumero.ViewHolder> {
    public List<ItemSerieNum> ncomprasLista;
    public List<ItemSerieNum>ncomprasListaFull;
    public CONEXION conexion = new CONEXION();
    AdapterSQLite myDB;
    public Connection con;

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txt_id,txt_serieNum,txt_idCompra,txt_numero;
        ImageButton btnImage;
        Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_id = itemView.findViewById(R.id.txt_idSerieNumero);
            this.txt_serieNum = itemView.findViewById(R.id.txt_serieNumero);
            this.txt_idCompra = itemView.findViewById(R.id.txt_idCompraSerieNumero);
            this.txt_numero = itemView.findViewById(R.id.txt_numserieNumero);
            this.btnImage = itemView.findViewById(R.id.btn_eliminarSerieNumero);
            this.context = itemView.getContext();
        }
    }
    public AdapterSerieNumero(List<ItemSerieNum> comidaLista) {
        this.ncomprasLista = comidaLista;
        ncomprasListaFull = new ArrayList<>(comidaLista);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serie_numero, parent, false);
        myDB = new AdapterSQLite(view.getContext());
        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.txt_id.setText(((ItemSerieNum) this.ncomprasLista.get(position)).getId());
        holder.txt_serieNum.setText(((ItemSerieNum) this.ncomprasLista.get(position)).getSerie());
        holder.txt_idCompra.setText(((ItemSerieNum) this.ncomprasLista.get(position)).getIdCompra());
        holder.txt_numero.setText(((ItemSerieNum) this.ncomprasLista.get(position)).getNum());

        final String idSerNum = holder.txt_id.getText().toString();
        final String ser = holder.txt_serieNum.getText().toString();
        final String idAllCompra = holder.txt_idCompra.getText().toString();
        final String numero = holder.txt_numero.getText().toString();

        holder.btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(holder.itemView.getContext(),id+" /"+ser+" / "+idAllCompra,Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(holder.itemView.getContext());
                builder1.setMessage("¿ SEGURO QUE DESEA ELIMINAR LA NOTA DE COMPRA / "+ser+" - "+numero+" ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "SI",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    myDB.deleteSerieNum_RP(idSerNum);
                                    Toast.makeText(holder.itemView.getContext(),"ELIMINADO CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                                }catch (Exception e){
                                    Toast.makeText(holder.itemView.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.create().show();
                try {

                }catch (Exception e){
                    Toast.makeText(holder.itemView.getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public int getItemCount() {
        return this.ncomprasLista.size();
    }
}