package w.kipu.kipu.formtocompra;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ConnSQLite.AdapterSQLite;
import w.kipu.kipu.ITEM.ITEM_NCOMPRA;
import w.kipu.kipu.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ncompra#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ncompra extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object StringBuffer;

//YA GIL GRACIAS
    public Fragment_ncompra() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ncompra.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ncompra newInstance(String param1, String param2) {
        Fragment_ncompra fragment = new Fragment_ncompra();
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

    ADAPTADOR_NCOMPRA adaptador_ncompra;
    ADAPTADOR_NCOMPRA.ViewHolder viewHolder;
    CONEXION conexion = new CONEXION();
    public SwipeRefreshLayout swipeContainer;
    AdapterSQLite myDB;
    String idUsu;
    public RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ncompra, container, false);
        myDB = new AdapterSQLite(view.getContext());
        Intent prueba = getActivity().getIntent();
        Bundle b = prueba.getExtras();
        if(b!=null){
            idUsu =(String) b.get("id");
        }
        //Toast.makeText(getContext(),idUsu,Toast.LENGTH_SHORT).show();
       recyclerView = (RecyclerView)view.findViewById(R.id.RecyclerNcompra);
        swipeContainer = (SwipeRefreshLayout)view.findViewById(R.id.swipeContainerNCompra);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptador_ncompra = new ADAPTADOR_NCOMPRA(ObtenerDetalleNCompra(idUsu));

        recyclerView.setAdapter(adaptador_ncompra);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adaptador_ncompra = new ADAPTADOR_NCOMPRA(ObtenerDetalleNCompra(idUsu));
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
        String lot="";
        try {
            Cursor res2 = myDB.selectMostrarListaDetalleCompra(id);
            if(res2 != null && res2.getCount()>0) {
                while (res2.moveToNext()){
                    ListaProducto.add(new ITEM_NCOMPRA(res2.getString(4), res2.getString(7), res2.getString(5), res2.getString(6), res2.getString(2) +" - "+res2.getString(3),
                            res2.getString(0), res2.getString(1),res2.getString(11), res2.getString(8), res2.getString(9),res2.getString(10),"LOCAL",res2.getString(12)));
                }
            }else
                Toast.makeText(getContext(),"SIN NOTA DE COMPRA",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return ListaProducto;
    }
}