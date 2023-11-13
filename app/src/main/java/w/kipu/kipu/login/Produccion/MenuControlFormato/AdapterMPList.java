package w.kipu.kipu.login.Produccion.MenuControlFormato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemMPLista;
import w.kipu.kipu.R;

public class AdapterMPList extends ArrayAdapter<ItemMPLista>{

    private List<ItemMPLista> objects;
    private Context context;

    public AdapterMPList(Context context, int resourceId,List<ItemMPLista> objects) {
        super(context, resourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row=inflater.inflate(R.layout.item_mplista_spinner, parent, false);
        TextView cod=row.findViewById(R.id.txt_codItemMPListaSpinner);
        TextView prod=row.findViewById(R.id.txt_prodItemMPListaSpinner);
        cod.setText(((ItemMPLista) this.objects.get(position)).getCod());
        prod.setText(((ItemMPLista) this.objects.get(position)).getProd());
        return row;
    }

}