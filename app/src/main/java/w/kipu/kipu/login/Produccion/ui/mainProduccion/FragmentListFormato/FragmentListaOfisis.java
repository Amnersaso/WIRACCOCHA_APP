package w.kipu.kipu.login.Produccion.ui.mainProduccion.FragmentListFormato;

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

import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.ItemPaletizado;
import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.ui.mainProduccion.AdapterFragmentControl.AdapterPaletizado;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListaOfisis#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListaOfisis extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListaOfisis() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPaletizado_Estiba.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListaOfisis newInstance(String param1, String param2) {
        FragmentListaOfisis fragment = new FragmentListaOfisis();
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

    AdapterPaletizado adapterPaletizado;
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    RecyclerView rv_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_paletizado__estiba, container, false);
        rv_list = view.findViewById(R.id.rv_ItemPaletizaddo);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterPaletizado = new AdapterPaletizado(SEPALET());
        rv_list.setAdapter(adapterPaletizado);
        rv_list.getAdapter().notifyDataSetChanged();
        rv_list.setHasFixedSize(true);
        return view;
    }
    public List<ItemPaletizado> SEPALET(){
        List<ItemPaletizado>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call SPAPP_SEPALET}");
            ResultSet rs = pst1.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ItemPaletizado(rs.getString("LOTE_PRODUCCION"),rs.getString("DOCUMENTO"),rs.getString("FECHA"),rs.getString("COLUMNA1"),rs.getString("LINEA")+" - "+rs.getString("TURNO"),rs.getString("INGRESO_MATERIA_PRIMA"),rs.getString("TOTAL")));
            }
        }catch (Exception e){
            Toast.makeText(getActivity(),"desconectado",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
}