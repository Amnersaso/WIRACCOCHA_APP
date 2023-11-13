package w.kipu.kipu.formtocompra.MostrarListaProductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemListaProductoDetaComp;
import w.kipu.kipu.R;

public class AdapterListaProductoDetalleCompra extends ArrayAdapter<ItemListaProductoDetaComp> {

    private List<ItemListaProductoDetaComp> objects;
    private Context context;
    AdapterSQLite myDB;

    public AdapterListaProductoDetalleCompra(Context context, int resourceId,List<ItemListaProductoDetaComp> objects) {
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
        View row=inflater.inflate(R.layout.item_listaproduc_deta_compra, parent, false);
        TextView cod=row.findViewById(R.id.txt_produListaDetalleCompra);
        cod.setText(((ItemListaProductoDetaComp) this.objects.get(position)).getProd());
        return row;
    }

}