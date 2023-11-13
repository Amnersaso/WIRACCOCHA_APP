package w.kipu.kipu.ConnSQLite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemAlmacen;
import w.kipu.kipu.R;

public class AdapterAlmacen extends ArrayAdapter<ItemAlmacen> {

    private List<ItemAlmacen> objects;
    private Context context;

    public AdapterAlmacen(Context context, int resourceId, List<ItemAlmacen> objects) {
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
        View row=inflater.inflate(R.layout.item_almacen_sqlite, parent, false);
        TextView id=row.findViewById(R.id.txt_idAlmaSQLite);
        TextView descrip=row.findViewById(R.id.txt_descripAlmaSQLite);
        TextView direc = row.findViewById(R.id.txt_direcAlmaSQLite);
        TextView telefo=row.findViewById(R.id.txt_telefonoAlmaSQLite);
        id.setText(((ItemAlmacen) this.objects.get(position)).getId());
        descrip.setText(((ItemAlmacen) this.objects.get(position)).getDescrip());
        direc.setText(((ItemAlmacen) this.objects.get(position)).getDirec());
        telefo.setText(((ItemAlmacen) this.objects.get(position)).getTelefo());

        return row;
    }
}