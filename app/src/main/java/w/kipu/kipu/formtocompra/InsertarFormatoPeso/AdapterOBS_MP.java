package w.kipu.kipu.formtocompra.InsertarFormatoPeso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemOBS_MP;
import w.kipu.kipu.R;

public class AdapterOBS_MP extends ArrayAdapter<ItemOBS_MP> {

    private List<ItemOBS_MP> objects;
    private Context context;

    public AdapterOBS_MP(Context context, int resourceId, List<ItemOBS_MP> objects) {
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

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.item_formato_spinner, parent, false);
        TextView cod = row.findViewById(R.id.txt_codItemFormato);
        TextView prod = row.findViewById(R.id.txt_desItemFormato);
        TextView id = row.findViewById(R.id.txt_idItemFormato);
        cod.setText(((ItemOBS_MP) this.objects.get(position)).getIdOBS());
        prod.setText(((ItemOBS_MP) this.objects.get(position)).getDescrip());

        return row;
    }
}