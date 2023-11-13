package w.kipu.kipu.login.Produccion.ui.mainProduccion.FragmentListFormato;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ITEM.ItemPendienteFormatControl;
import w.kipu.kipu.R;
import w.kipu.kipu.login.Produccion.ui.mainProduccion.AdapterFragmentControl.AdapterPendForControl;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPendientes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPendientes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentPendientes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLavadoSecado.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPendientes newInstance(String param1, String param2) {
        FragmentPendientes fragment = new FragmentPendientes();
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
    CONEXION conexion=new CONEXION();
    RecyclerView rv_pendiente;
    AdapterPendForControl adapterPendForControl;
    SwipeRefreshLayout sw_refres;
    Button btn_callRefresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pend_format_control, container, false);
        rv_pendiente=view.findViewById(R.id.rv_pendienteFormatoControl);
        btn_callRefresh =view.findViewById(R.id.btn_refreshPendienteFormatoControl);
        sw_refres = view.findViewById(R.id.sw_refreshPendienteFormatoControl);

        rv_pendiente.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterPendForControl = new AdapterPendForControl(SETBGENTBACT());
        rv_pendiente.setAdapter(adapterPendForControl);
        rv_pendiente.getAdapter().notifyDataSetChanged();
        rv_pendiente.setHasFixedSize(true);

        sw_refres.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                btn_callRefresh.callOnClick();
                sw_refres.setRefreshing(false);
            }
        });
        btn_callRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterPendForControl = new AdapterPendForControl(SETBGENTBACT());
                rv_pendiente.setAdapter(adapterPendForControl);
                rv_pendiente.getAdapter().notifyDataSetChanged();
                rv_pendiente.setHasFixedSize(true);
            }
        });
        return view;
    }
    public List<ItemPendienteFormatControl> SETBGENTBACT(){
        ArrayList<ItemPendienteFormatControl> dataListaPuesto= new ArrayList<>();
        dataListaPuesto.clear();
        try {
            PreparedStatement pst1 = conexion.ConnectionDB_Local().prepareStatement("{call SPAPP_SETBGENTBACT }");
            ResultSet rs = pst1.executeQuery();
            while(rs.next()){
                dataListaPuesto.add(new ItemPendienteFormatControl(rs.getString("NUMSERIE"),rs.getString("SERIE"),rs.getString("CODLINEA"),rs.getString("LINEA"),rs.getString("LOTE"),rs.getString("CODTIPRO"),rs.getString("DESTIPRO"),rs.getString("CODPROD"),rs.getString("DESPROD"),rs.getString("CANT"),rs.getString("FECREG"),rs.getString("OBS"),rs.getString("CANTSALID"),rs.getString("HUMEDAD")));
            }
        }catch (Exception e){
        }
        return dataListaPuesto;
    }

}