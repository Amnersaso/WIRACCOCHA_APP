package w.kipu.kipu.formtocompra;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ITEM_NCOMPRA;
import w.kipu.kipu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_rp_nube#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_rp_nube extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_rp_nube() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_rp_nube.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_rp_nube newInstance(String param1, String param2) {
        Fragment_rp_nube fragment = new Fragment_rp_nube();
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

    ADAPTADOR_NCOMPRA_NUBE adaptador_ncompra;
    private SwipeRefreshLayout swipeContainer;
    AdapterSQLite myDB;
    CONEXION conexionLocal=new CONEXION();
    Connection conLocal,conPublica,conAll=null;
    String idUsu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_rp_nube, container, false);
        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.Recycler_RP_Nube);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeContainer = view.findViewById(R.id.swipeContainer_RP_nube);
        myDB = new AdapterSQLite(view.getContext());
        Intent prueba = getActivity().getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            idUsu =(String) b.get("idLogin_RP");
        }

        adaptador_ncompra = new ADAPTADOR_NCOMPRA_NUBE(ObtenerDetalleNCompra(idUsu));
        recyclerView.setAdapter(adaptador_ncompra);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adaptador_ncompra = new ADAPTADOR_NCOMPRA_NUBE(ObtenerDetalleNCompra(idUsu));
                recyclerView.setAdapter(adaptador_ncompra);
                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.setHasFixedSize(true);
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }
    public List<ITEM_NCOMPRA> ObtenerDetalleNCompra(String id){
        List<ITEM_NCOMPRA>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            conPublica = conexionLocal.ConnectionDB_Local();
            if(conPublica != null) {
                conAll = conPublica;
            }else
                conAll = conexionLocal.ConnectionDB_Local();

            PreparedStatement pst1 = conAll.prepareStatement("{call ListaPEstiva (?)}");
            pst1.setString(1,id);
            ResultSet rs = pst1.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ITEM_NCOMPRA(rs.getString("NOMBRE_COMPLETO"),rs.getString("TOTAL_PESO_MODIFICADO"),rs.getString("TOTAL_SACOS"),rs.getString("MONTO_TOTAL")
                        ,rs.getString("SERIE"),rs.getString("ID_COMPRA"),rs.getString("ID_PROVEEDOR"),rs.getString("LOTE")
                        ,rs.getString("TIPOREGISTRO"),rs.getString("ESTADO"),rs.getString("ID_USUARIO"),rs.getString("ID_USUARIO"),rs.getString("ID_USUARIO")));
            }
        }catch (Exception e){
            Toast.makeText(getActivity(),"ERROR RP NUBE! / " + e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
}