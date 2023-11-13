package w.kipu.kipu.ORDAprobaciones;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.ItemDetalleOrdenes;
import w.kipu.kipu.R;

public class DetalleOcl_Osl extends AppCompatActivity {

    ListView listViewOrdenes;
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    String ser,num,total,prove,obser,fec;
    MyAppAdapter myAppAdapter;
    TextView txt_serie,txt_num,txt_prove,txt_obser,txt_fecha,txt_total,txt_moneda;
    String moneda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_ocl__osl);

        listViewOrdenes = findViewById(R.id.ls_detalleOCL_OSL);
        txt_serie =findViewById(R.id.txt_serieDetalleOrdenes);
        txt_num= findViewById(R.id.txt_numDetalleOrdenes);
        txt_prove = findViewById(R.id.txt_proveedorDetalleOrdenes);
        txt_obser = findViewById(R.id.txt_obserDetalleOrdenes);
        txt_fecha = findViewById(R.id.txt_fechaDetalleOrdenes);
        txt_total = findViewById(R.id.txt_totalDetalleOrdenes);
        txt_moneda = findViewById(R.id.txt_monedaDetalleOrdenes);

        Intent prueba = getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            ser=(String) b.get("serie");
            num=(String) b.get("numser");
            total=(String) b.get("total");
            prove=(String) b.get("provee");
            obser=(String) b.get("observa");
            fec=(String) b.get("fecha");
        }
       // Toast.makeText(getApplicationContext(),ser+" / "+num,Toast.LENGTH_SHORT).show();
        try {
            BuscarMoneda(ser,num);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txt_serie.setText(ser+" - ");
        txt_num.setText(num);
        txt_prove.setText(prove);
        txt_obser.setText(obser);
        txt_fecha.setText(fec);
        txt_total.setText(total);
        txt_moneda.setText(moneda);

        myAppAdapter = new MyAppAdapter(ListaDetalleOrdenes(ser,num),DetalleOcl_Osl.this);
        listViewOrdenes.setAdapter(myAppAdapter);
    }

    public void BuscarMoneda(String serie,String numer) throws SQLException {
        PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call DETALLE_ORDEN  ("+serie+","+numer+")}");
        ResultSet rs = pst1.executeQuery();
        if (rs.next()){
            moneda = rs.getString("GRTCOF_DESCRP");
        }
    }

    public ArrayList<ItemDetalleOrdenes> ListaDetalleOrdenes(String serie,String numSerie){
        ArrayList<ItemDetalleOrdenes>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement st= conexionOfi.ConnectionDB().prepareStatement("{call DETALLE_ORDEN ("+serie+","+numSerie+")}");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ItemDetalleOrdenes(rs.getString("STMPDH_DESCRP"),rs.getString("CORMVI_CANTID")+"  ",rs.getString("PRECIOCONIMPUESTO"),rs.getString("SUBTOTAL")));
                //String.valueOf(Double.valueOf(Double.parseDouble(rs.getString(41))).doubleValue() * Double.valueOf(Double.parseDouble(rs.getString(9))).doubleValue())
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Conexión inválida",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }

    public class MyAppAdapter extends BaseAdapter         //has a class viewholder which holds
    {
        public class ViewHolder
        {
            TextView desc,cant,punit,ptotal;
            ImageView imageView;
        }

        public List<ItemDetalleOrdenes> parkingList;

        public Context context;
        ArrayList<ItemDetalleOrdenes> arraylist;

        private MyAppAdapter(List<ItemDetalleOrdenes> apps, Context context)
        {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<ItemDetalleOrdenes>();
            arraylist.addAll(parkingList);
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) // inflating the layout and initializing widgets
        {
            View rowView = convertView;
            ViewHolder viewHolder;
            if (rowView == null){
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_detalle_ordenes, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.desc = (TextView) rowView.findViewById(R.id.txt_descripOrdenes);
                viewHolder.cant = (TextView) rowView.findViewById(R.id.txt_cantOrdenes);
                viewHolder.punit = (TextView) rowView.findViewById(R.id.txt_punitOrdenes);
                viewHolder.ptotal = (TextView) rowView.findViewById(R.id.txt_ptotalOrdenes);
                rowView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.desc.setText(parkingList.get(position).getDesc());
            viewHolder.cant.setText(parkingList.get(position).getCant());
            viewHolder.punit.setText(parkingList.get(position).getPunit());
            viewHolder.ptotal.setText(parkingList.get(position).getPtotal());
            return rowView;
        }
    }


}