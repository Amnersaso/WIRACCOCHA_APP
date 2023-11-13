package w.kipu.kipu.login.Produccion.DialogoFormatoControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemTiProActividad;
import w.kipu.kipu.R;

public class AdapterPresentacion extends ArrayAdapter<ItemTiProActividad> {

    private List<ItemTiProActividad> objects;
    private Context context;

    public AdapterPresentacion(Context context, int resourceId,List<ItemTiProActividad> objects) {
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
        View row=inflater.inflate(R.layout.item_presentacion_spinner, parent, false);
        TextView presenta=row.findViewById(R.id.txt_presentItemPresentSpinner);
        TextView kg=row.findViewById(R.id.txt_kgItemPresentSpinner);
        presenta.setText(((ItemTiProActividad) this.objects.get(position)).getCod());
        kg.setText(((ItemTiProActividad) this.objects.get(position)).getDesc());
        return row;
    }
}