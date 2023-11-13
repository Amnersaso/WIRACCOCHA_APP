package w.kipu.kipu.login.Produccion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import w.kipu.kipu.ITEM.ItemFormato;
import w.kipu.kipu.R;

public class AdapterFormato extends ArrayAdapter<ItemFormato> {

    private List<ItemFormato> objects;
    private Context context;

    public AdapterFormato(Context context, int resourceId, List<ItemFormato> objects) {
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
        View row=inflater.inflate(R.layout.item_formato_spinner, parent, false);
        TextView cod=row.findViewById(R.id.txt_codItemFormato);
        TextView prod=row.findViewById(R.id.txt_desItemFormato);
        TextView id = row.findViewById(R.id.txt_idItemFormato);
        cod.setText(((ItemFormato) this.objects.get(position)).getCod());
        prod.setText(((ItemFormato) this.objects.get(position)).getDes());
        id.setText(((ItemFormato) this.objects.get(position)).getId());

        return row;
    }
}