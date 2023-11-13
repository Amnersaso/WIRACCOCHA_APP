package w.kipu.kipu.login.ListaXEnviar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ItemXEnviar;
import w.kipu.kipu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPorEnviar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPorEnviar extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPorEnviar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPorEnviar.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPorEnviar newInstance(String param1, String param2) {
        FragmentPorEnviar fragment = new FragmentPorEnviar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    AdapterXEnviar adapterXEnviar;
    AdapterSQLite adapterSQLite;
    CONEXION conexion =  new CONEXION();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_por_enviar, container, false);
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rv_listaXEnviar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterXEnviar = new AdapterXEnviar(ObtenerDetalleNCompra());
        recyclerView.setAdapter(adapterXEnviar);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        return view;
    }
    public List<ItemXEnviar> ObtenerDetalleNCompra(){
        List<ItemXEnviar>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SELIST_NCOMPRA }");
            ResultSet rs = pst1.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ItemXEnviar(rs.getString("NOMBRE_COMPLETO"),rs.getString("TOTAL_PESO_MODIFICADO"),rs.getString("TOTAL_SACOS"),
                        rs.getString("MONTO_TOTAL"),rs.getString("SERIE"),rs.getString("ID_COMPRA"),rs.getString("ID_PROVEEDOR"),rs.getString("LOTE")));
            }

            //Muestra Lista del SQLite
            /*
            Cursor res = adapterSQLite.selectCompraPrueba();
            StringBuffer stringBuffer = new StringBuffer();
            if(res!= null && res.getCount()>0){
                while (res.moveToNext()){
                    ListaProducto.add(new ItemXEnviar(res.getString(4),res.getString(7),res.getString(5),
                            res.getString(6),res.getString(3),res.getString(1),res.getString(2),res.getString(1)));
                }
            }else {
                Toast.makeText(getContext(),"No se pudo mostrar",Toast.LENGTH_SHORT).show();
            }

             */

        }catch (Exception e){
            Toast.makeText(getActivity(),"desconectado por enviar!",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
}