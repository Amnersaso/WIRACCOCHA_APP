package w.kipu.kipu.formtocompra.MostrarListaProductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemOneSpinner;
import w.kipu.kipu.R;

public class AdapterOneSpinner extends ArrayAdapter<ItemOneSpinner> {

    private List<ItemOneSpinner> objects;
    private Context context;

    public AdapterOneSpinner(Context context, int resourceId,List<ItemOneSpinner> objects) {
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
        View row=inflater.inflate(R.layout.item_spinner_one, parent, false);
        TextView cod=row.findViewById(R.id.txt_codSpinnerOne);
        TextView desc=row.findViewById(R.id.txt_textSpinnerOne);
        cod.setText(((ItemOneSpinner) this.objects.get(position)).getCod());
        desc.setText(((ItemOneSpinner) this.objects.get(position)).getTexto());

        return row;
    }
}