package w.kipu.kipu.ui.aprobados;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.Item_Aprobado;
import w.kipu.kipu.R;

public class Aprobados extends Fragment {

    AdapterAprobado adapterAprobado;
    Connection con;
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    Spinner spOrden,spSerie;
    String TipoOrden,numSerie;
    ImageButton btn_buscarTIpoDoc;
    RecyclerView recyclerView;
    private final static String[] ListaTipoOrden = { "OCL", "OSL","OCMP"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_aprobados, container, false);
        btn_buscarTIpoDoc = root.findViewById(R.id.btn_buscarTipoDocAprobado);
        recyclerView = root.findViewById(R.id.rv_aprobado);
        spOrden = root.findViewById(R.id.sp_OrdenPendientAprobado);
        spSerie = root.findViewById(R.id.sp_SeriePendientAprobado);
        ArrayAdapter prod = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, ListaTipoOrden);
        spOrden.setAdapter(prod);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        spOrden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                TipoOrden = item.toString();

                if(TipoOrden.equals("OSL")){
                    spSerie.setVisibility(View.INVISIBLE);
                }else if(TipoOrden.equals("OCL")){
                    spSerie.setVisibility(View.INVISIBLE);
                }else{
                    try {
                        spSerie.setVisibility(View.VISIBLE);
                        BuscarSerie(TipoOrden);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                numSerie = item.toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btn_buscarTIpoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),TipoOrden,Toast.LENGTH_SHORT).show();
                if(TipoOrden.equals("OCMP")){
                    String numSer = numSerie.toString().substring(2,4);
                    String seriNum = TipoOrden+numSer;
                    adapterAprobado = new AdapterAprobado(ListaAprobado(seriNum));
                    recyclerView.setAdapter(adapterAprobado);
                    recyclerView.setHasFixedSize(true);
                }else{
                    adapterAprobado = new AdapterAprobado(ListaAprobado(TipoOrden));
                    recyclerView.setAdapter(adapterAprobado);
                    recyclerView.setHasFixedSize(true);
                }
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.sv_buscar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapterAprobado.getFilter().filter(s);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void BuscarSerie(String tipoDoc) throws SQLException {
        PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call BUSCAR_SERIE  ("+tipoDoc+")}");
        ResultSet rs = pst1.executeQuery();
        ArrayList<String> data= new ArrayList<String>();
        while(rs.next()){
            //String orden = rs.getString("GRCCBH_CODCOM"); //orden
            String serie = rs.getString("USR_GRCCBH_NUSERI");
            data.add(serie);
        }
        ArrayAdapter prod = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, data);
        spSerie.setAdapter(prod);
    }

    public List<Item_Aprobado> ListaAprobado(String num){
        List<Item_Aprobado>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement st= conexionOfi.ConnectionDB().prepareStatement("{call LISTA_ORD_APROBADOS ("+num+")}");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ListaProducto.add(new Item_Aprobado(rs.getString("SERIE")+" - ",rs.getString("NUMERO_DOC"),rs.getString("FCHMOV"),rs.getString("PVMPRH_NOMBRE"),rs.getString("PRODUCTO"),rs.getString("PRECIO_TOTAL"),rs.getString("CORMVH_USERID"),rs.getString("FECHA_AUTORIZACION")) );
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Conexión inválida",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
}