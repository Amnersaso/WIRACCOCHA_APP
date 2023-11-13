package w.kipu.kipu.login.Produccion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemLiProduc;
import w.kipu.kipu.R;

public class AdapterLiProduc extends ArrayAdapter<ItemLiProduc> {

    private List<ItemLiProduc> objects;
    private Context context;
    AdapterSQLite myDB;

    public AdapterLiProduc(Context context, int resourceId,List<ItemLiProduc> objects) {
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
        TextView id =row.findViewById(R.id.txt_idItemLiProducSpinner);
        TextView prod=row.findViewById(R.id.txt_prodItemLiProducSpinner);
        cod.setText(((ItemLiProduc) this.objects.get(position)).getCod());
        prod.setText(((ItemLiProduc) this.objects.get(position)).getDesc());
        id.setText(((ItemLiProduc) this.objects.get(position)).getId());
        return row;
    }

}