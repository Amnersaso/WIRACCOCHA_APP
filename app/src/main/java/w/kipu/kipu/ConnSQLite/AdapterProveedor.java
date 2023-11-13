package w.kipu.kipu.ConnSQLite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemProveedor;
import w.kipu.kipu.R;

public class AdapterProveedor extends ArrayAdapter<ItemProveedor> {

    private List<ItemProveedor> objects;
    private Context context;

    public AdapterProveedor(Context context, int resourceId, List<ItemProveedor> objects) {
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
        View row=inflater.inflate(R.layout.item_proveedor_sqlite, parent, false);
        TextView id=row.findViewById(R.id.txt_idProvSQLite);
        TextView dni=row.findViewById(R.id.txt_dniProvSQLite);
        TextView nombre = row.findViewById(R.id.txt_nombreProvSQLite);
        TextView telefo=row.findViewById(R.id.txt_telefoProvSQLite);
        TextView comunidad=row.findViewById(R.id.txt_comunidadProvSQLite);
        TextView distrito = row.findViewById(R.id.txt_distritoProvSQLite);
        TextView direcc=row.findViewById(R.id.txt_direcProvSQLite);
        TextView referenc=row.findViewById(R.id.txt_referenciProvSQLite);
        TextView tipo = row.findViewById(R.id.txt_tipoProvSQLite);
        TextView parcela = row.findViewById(R.id.txt_parcelaProvSQLite);
        id.setText(((ItemProveedor) this.objects.get(position)).getId());
        dni.setText(((ItemProveedor) this.objects.get(position)).getDni());
        nombre.setText(((ItemProveedor) this.objects.get(position)).getNombre());
        telefo.setText(((ItemProveedor) this.objects.get(position)).getTelefono());
        comunidad.setText(((ItemProveedor) this.objects.get(position)).getComunidad());
        distrito.setText(((ItemProveedor) this.objects.get(position)).getDistrito());
        direcc.setText(((ItemProveedor) this.objects.get(position)).getDirec());
        referenc.setText(((ItemProveedor) this.objects.get(position)).getReferencia());
        tipo.setText(((ItemProveedor) this.objects.get(position)).getTipo());
        parcela.setText(((ItemProveedor) this.objects.get(position)).getParcela());

        return row;
    }
}