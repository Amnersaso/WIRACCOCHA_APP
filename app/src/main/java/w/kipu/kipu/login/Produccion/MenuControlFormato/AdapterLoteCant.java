package w.kipu.kipu.login.Produccion.MenuControlFormato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemLoteCant;
import w.kipu.kipu.R;

public class AdapterLoteCant extends ArrayAdapter<ItemLoteCant> {

    private List<ItemLoteCant> objects;
    private Context context;

    public AdapterLoteCant(Context context, int resourceId,List<ItemLoteCant> objects) {
        super(context, resourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row=inflater.inflate(R.layout.item_lote_cant_spinner, parent, false);
        TextView tipro=row.findViewById(R.id.txt_tiproItemLoteCantSpinner);
        TextView codprod = row.findViewById(R.id.txt_codproItemLoteCantSpinner);
        TextView lote=row.findViewById(R.id.txt_loteItemLoteCantSpinner);
        TextView fecha=row.findViewById(R.id.txt_fechaItemLoteCantSpinner);
        TextView cant=row.findViewById(R.id.txt_cantItemLoteCantSpinner);
        tipro.setText(((ItemLoteCant) this.objects.get(position)).getTipro());
        codprod.setText(((ItemLoteCant) this.objects.get(position)).getcodPro());
        lote.setText(((ItemLoteCant) this.objects.get(position)).getLote());
        fecha.setText(((ItemLoteCant) this.objects.get(position)).getFecha());
        cant.setText(((ItemLoteCant) this.objects.get(position)).getCant());
        return row;
    }

}