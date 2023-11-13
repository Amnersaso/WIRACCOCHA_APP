package w.kipu.kipu.login.Produccion.ui.mainProduccion.AdapterFragmentControl;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.ItemPendienteFormatControl;
import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.MenuControlFormato.FormatoControl;

public class AdapterPendForControl extends RecyclerView.Adapter<AdapterPendForControl.ViewHolder> {

    public List<ItemPendienteFormatControl> detalleLista;
    public List<ItemPendienteFormatControl> detalleListaFull;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        EditText txt_serie,txt_num,txt_lote,txt_produc,txt_linea,txt_cant,txt_obs,txt_cantSalida;
        TextView txt_fecha,txt_codLinea,txt_codTiPro,txt_codProd,txt_desTiPro,txt_humedad;
        ImageButton btn_detalle;
        Button btn_aprobar;
        Context context;
        CONEXION_OFI conexionOfi = new CONEXION_OFI();

        public ViewHolder(View itemView) {
            super(itemView);
            this.txt_serie = itemView.findViewById(R.id.txt_seriePendForControl);
            this.txt_num = itemView.findViewById(R.id.txt_numPendForControl);
            this.txt_lote = itemView.findViewById(R.id.txt_lotePendForControl);
            this.txt_produc = itemView.findViewById(R.id.txt_producPendForControl);
            this.txt_linea = itemView.findViewById(R.id.txt_lineaPendForControl);
            this.txt_cant = itemView.findViewById(R.id.txt_cantPendForControl);
            this.txt_fecha = itemView.findViewById(R.id.txt_fechaPendForControl);
            this.btn_detalle = itemView.findViewById(R.id.btn_detallePendForControl);
            this.txt_codLinea =itemView.findViewById(R.id.txt_codLineaPenForCont);
            this.txt_codTiPro =itemView.findViewById(R.id.txt_codTiProPenForCont);
            this.txt_codProd = itemView.findViewById(R.id.txt_codProdPenForCont);
            this.txt_obs = itemView.findViewById(R.id.txt_obsPenForCont);
            this.txt_cantSalida = itemView.findViewById(R.id.txt_cantSalidForControl);
            this.txt_desTiPro = itemView.findViewById(R.id.txt_desTiProPenForCont);
            this.btn_aprobar = itemView.findViewById(R.id.btn_aprobarPendFormatControl);
            this.txt_humedad = itemView.findViewById(R.id.txt_humePenForCont);
            this.context = itemView.getContext();
        }

        void listenersclock() {
            this.btn_detalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context con = view.getContext();
                    String numSerie, serie, codLinea,codTiPro,lote,codProd,prod,desTiPro,obs;
                    String pendiente = "2";
                    numSerie = txt_num.getText().toString();
                    serie = txt_serie.getText().toString();
                    codLinea = txt_codLinea.getText().toString();
                    codTiPro = txt_codTiPro.getText().toString();
                    lote = txt_lote.getText().toString();
                    codProd = txt_codProd.getText().toString();
                    prod = txt_produc.getText().toString();
                    desTiPro = txt_desTiPro.getText().toString();
                    obs = txt_obs.getText().toString();

                    Intent enviar = new Intent(con, FormatoControl.class);
                    enviar.putExtra("numSerie", numSerie);
                    enviar.putExtra("serie", serie);
                    enviar.putExtra("LiProduc", codLinea);
                    enviar.putExtra("pendi", pendiente);
                    enviar.putExtra("codTiPro", codTiPro);
                    enviar.putExtra("lote", lote);
                    enviar.putExtra("codProd", codProd);
                    enviar.putExtra("Prod", prod);
                    enviar.putExtra("desTiPro", desTiPro);
                    enviar.putExtra("obs",obs);
                    con.startActivity(enviar);
                    //Toast.makeText(view.getContext(),numSerie+"/"+serie+"/"+codLinea,Toast.LENGTH_SHORT).show();
                }
            });
            this.btn_aprobar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setMessage("ESTÁ SEGURO DE APROBAR EL DOCUMENTO");
                    builder.setCancelable(true);
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String modFor = "PD";
                            String codFor = txt_serie.getText().toString();
                            String nroFor = txt_num.getText().toString();
                            String suCurs = "0001";
                            String observ = txt_obs.getText().toString();
                            String codTiP = txt_codTiPro.getText().toString();
                            String codPro = txt_codProd.getText().toString();
                            String lotemp = txt_lote.getText().toString();
                            String mapcan = txt_cant.getText().toString();
                            String porhum = txt_humedad.getText().toString();
                            DateFormat dateFormat = new SimpleDateFormat("HHmm");
                            Date date = new Date();
                            String horain = dateFormat.format(date);
                            String hortur;
                            int fe = Integer.parseInt(horain);
                            if (fe>=4 & fe<=18){
                                hortur = "D";
                            }else{
                                hortur = "N";
                            }
                            String linmel = txt_codLinea.getText().toString();
                            String ultopr = "A";
                            String debaja = "N";
                            String userid = "PRODUCCION";


                            try {
                                FILTAB(modFor,codFor,nroFor,suCurs,"","",observ,codTiP,codPro,lotemp,mapcan,porhum,"",hortur,linmel,"","",ultopr,debaja,userid);
                                Toast.makeText(itemView.getContext(),"Se aprobó correctamente" ,Toast.LENGTH_SHORT).show();
                            }catch (Exception e){
                                Toast.makeText(itemView.getContext(),"Error" ,Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });
        }
        public void FILTAB(String modFor, String codFor, String nroFor, String sucurs, String horain, String horafi, String obse, String tipmap, String artmap, String lotemp, String mapcan, String porhum,
                                  String fechai, String hortur, String lineamel, String fecalt, String fecmod, String ultopr, String debaja, String userid){
            try {
                PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call SPAPP_FILTABNEW (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                pst1.setString(1,modFor);
                pst1.setString(2,codFor);
                pst1.setString(3,nroFor);
                pst1.setString(4,sucurs);
                pst1.setString(5,horain);
                pst1.setString(6,horafi);
                pst1.setString(7,obse);
                pst1.setString(8,tipmap);
                pst1.setString(9,artmap);
                pst1.setString(10,lotemp);
                pst1.setString(11,mapcan);
                pst1.setString(12,porhum);
                pst1.setString(13,fechai);
                pst1.setString(14,hortur);
                pst1.setString(15,lineamel);
                pst1.setString(16,fecalt);
                pst1.setString(17,fecmod);
                pst1.setString(18,ultopr);
                pst1.setString(19,debaja);
                pst1.setString(20,userid);
                pst1.executeUpdate();
            }catch (Exception e){
            }
        }
        @Override
        public void onClick(final View view) {
        }
    }
    public AdapterPendForControl(List<ItemPendienteFormatControl> comidaLista) {
        this.detalleLista = comidaLista;
        this.detalleListaFull = new ArrayList<>(comidaLista);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pend_format_control, parent, false);

        return new ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_serie.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getSerie());
        holder.txt_num.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getNumSerie());
        holder.txt_lote.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getLote());
        holder.txt_produc.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getDesProd());
        holder.txt_linea.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getLinea());
        holder.txt_cant.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getCant());
        holder.txt_fecha.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getFecReg());
        holder.txt_codLinea.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getCodLinea());
        holder.txt_codTiPro.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getCodTipro());
        holder.txt_codProd.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getCodProd());
        holder.txt_desTiPro.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getDesTipro());
        holder.txt_obs.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getObs());
        holder.txt_cantSalida.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getCantSalida());
        holder.txt_humedad.setText(((ItemPendienteFormatControl) this.detalleLista.get(position)).getHumedad());
        holder.listenersclock();
    }

    public int getItemCount() {
        return this.detalleLista.size();
    }

}