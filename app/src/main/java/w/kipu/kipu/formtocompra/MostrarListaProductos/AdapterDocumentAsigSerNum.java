package w.kipu.kipu.formtocompra.MostrarListaProductos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemDocumentAsigSerieNum;
import w.kipu.kipu.R;

public class AdapterDocumentAsigSerNum extends ArrayAdapter<ItemDocumentAsigSerieNum> {

    private List<ItemDocumentAsigSerieNum> objects;
    private Context context;

    public AdapterDocumentAsigSerNum(Context context, int resourceId,List<ItemDocumentAsigSerieNum> objects) {
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
        View row=inflater.inflate(R.layout.item_document_asig_serie_num, parent, false);
        TextView id=row.findViewById(R.id.txt_iduserDocumentAsigSerNum);
        TextView ser=row.findViewById(R.id.txt_serieDocumentAsigSerNum);
        TextView num=row.findViewById(R.id.txt_numDocumentAsigSerNum);
        id.setText(((ItemDocumentAsigSerieNum) this.objects.get(position)).getIdUsu());
        ser.setText(((ItemDocumentAsigSerieNum) this.objects.get(position)).getSerie());
        num.setText(((ItemDocumentAsigSerieNum) this.objects.get(position)).getNum());

        return row;
    }
}