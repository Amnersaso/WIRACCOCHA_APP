package w.kipu.kipu.ORDAprobaciones;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.ITEM.ItemDetalleOrdenes;
import w.kipu.kipu.R;

public class AdapterDetalleOclOSL extends BaseAdapter {

    public class ViewHolder{
        TextView desc,cant,pUnit,pTotal;
    }
    public List<ItemDetalleOrdenes> listaDetalle;
    public Context context;
    public ArrayList<ItemDetalleOrdenes> listaArray;

    public AdapterDetalleOclOSL(List<ItemDetalleOrdenes> lista, Context context){
        this.listaDetalle=lista;
        this.context=context;
        listaArray= new ArrayList<ItemDetalleOrdenes>();
        listaArray.addAll(listaDetalle);
    }

    @Override
    public int getCount() {
        return listaDetalle.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rootview = view;
        ViewHolder holder = new ViewHolder();
        Context context;
        if(rootview == null){


            holder.desc = (TextView) rootview.findViewById(R.id.txt_descripOrdenes);
            holder.cant = (TextView) rootview.findViewById(R.id.txt_cantOrdenes);
            holder.pUnit = (TextView) rootview.findViewById(R.id.txt_punitOrdenes);
            holder.pTotal = (TextView) rootview.findViewById(R.id.txt_ptotalOrdenes);
            rootview.setTag(holder);
        }else{

        }
        holder.desc.setText(listaDetalle.get(i).getDesc()+"");
        return rootview;
    }
}
