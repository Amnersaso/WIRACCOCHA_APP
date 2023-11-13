package w.kipu.kipu.formtocompra.MostrarListaProductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemValidarProveedor;
import w.kipu.kipu.R;

public class AdapterValidarProveedor extends ArrayAdapter<ItemValidarProveedor> {

    private List<ItemValidarProveedor> objects;
    private Context context;
    AdapterSQLite myDB;

    public AdapterValidarProveedor(Context context, int resourceId,List<ItemValidarProveedor> objects) {
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
        View row=inflater.inflate(R.layout.item_li_produc_spinner, parent, false);
        TextView cod=row.findViewById(R.id.txt_codItemLiProducSpinner);
        TextView prod=row.findViewById(R.id.txt_prodItemLiProducSpinner);
        cod.setText(((ItemValidarProveedor) this.objects.get(position)).getDni());
        prod.setText(((ItemValidarProveedor) this.objects.get(position)).getNombre());
        return row;
    }

}