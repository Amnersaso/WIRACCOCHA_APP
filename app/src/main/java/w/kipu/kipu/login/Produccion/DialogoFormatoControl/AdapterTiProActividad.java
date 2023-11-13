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

public class AdapterTiProActividad extends ArrayAdapter<ItemTiProActividad> {

    private List<ItemTiProActividad> objects;
    private Context context;

    public AdapterTiProActividad(Context context, int resourceId,List<ItemTiProActividad> objects) {
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
        View row=inflater.inflate(R.layout.item_tipro_actividad_spinner, parent, false);
        TextView cod=row.findViewById(R.id.txt_codItemTiProActividad);
        TextView prod=row.findViewById(R.id.txt_descItemTiProActividad);
        cod.setText(((ItemTiProActividad) this.objects.get(position)).getCod());
        prod.setText(((ItemTiProActividad) this.objects.get(position)).getDesc());
        return row;
    }
}