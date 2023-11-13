package w.kipu.kipu.login.Produccion.DialogoFormatoControl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemDetaActivi;
import w.kipu.kipu.R;

public class AdapterDetaActivi extends ArrayAdapter<ItemDetaActivi> {

private List<ItemDetaActivi> objects;
private Context context;

public AdapterDetaActivi(Context context, int resourceId, List<ItemDetaActivi> objects) {
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
        View row=inflater.inflate(R.layout.item_detaactivi_spinner, parent, false);
        TextView cod=row.findViewById(R.id.txt_codItemDetaActivi);
        TextView prod=row.findViewById(R.id.txt_prodItemDetaActivi);
        cod.setText(((ItemDetaActivi) this.objects.get(position)).getCod());
        prod.setText(((ItemDetaActivi) this.objects.get(position)).getDes());

        return row;
    }
}