package w.kipu.kipu.login.Produccion.MenuControlFormato;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemTiProList;
import w.kipu.kipu.R;

public class AdapterTiProList extends ArrayAdapter<ItemTiProList> {

    private List<ItemTiProList> objects;
    private Context context;

    public AdapterTiProList(Context context, int resourceId,List<ItemTiProList> objects) {
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
        View row=inflater.inflate(R.layout.item_tipro_spinner, parent, false);
        TextView cod=row.findViewById(R.id.txt_codTiProSpinner);
        TextView prod=row.findViewById(R.id.txt_prodTiProSpinner);
        cod.setText(((ItemTiProList) this.objects.get(position)).getCod());
        prod.setText(((ItemTiProList) this.objects.get(position)).getProd());

        return row;
    }

}