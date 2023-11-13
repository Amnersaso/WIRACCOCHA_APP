package w.kipu.kipu.ConnSQLite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemAlmacenUbicacion;
import w.kipu.kipu.R;

public class AdapterAlmacenUbicacion extends ArrayAdapter<ItemAlmacenUbicacion> {

    private List<ItemAlmacenUbicacion> objects;
    private Context context;

    public AdapterAlmacenUbicacion(Context context, int resourceId, List<ItemAlmacenUbicacion> objects) {
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
        View row=inflater.inflate(R.layout.item_almacen_ubica_sqlite, parent, false);
        TextView id=row.findViewById(R.id.txt_idAlmaUbiSQLite);
        TextView idAl=row.findViewById(R.id.txt_idAlUbiSQLite);
        TextView ubi = row.findViewById(R.id.txt_ubiAlmaUbiSQLite);
        TextView desc=row.findViewById(R.id.txt_descriAlmaUbiSQLite);
        id.setText(((ItemAlmacenUbicacion) this.objects.get(position)).getIdAlmaUbi());
        idAl.setText(((ItemAlmacenUbicacion) this.objects.get(position)).getIdAlma());
        ubi.setText(((ItemAlmacenUbicacion) this.objects.get(position)).getUbic());
        desc.setText(((ItemAlmacenUbicacion) this.objects.get(position)).getDesc());

        return row;
    }
}