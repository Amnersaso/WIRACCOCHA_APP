package w.kipu.kipu.ui.pendiente;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.ItemAprobMatePrima;
import w.kipu.kipu.R;

public class HomeFragment extends Fragment {

    AdapterMatePrima adapterMatePrima;
    ImageButton btn_buscarTIpoDoc;
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    public RecyclerView recyclerView;
    Spinner spOrden,spSerie;
    TextView txt_can;
    String cantOCMP,serie;
    String TipoOrden,numSerie;
    private final static String[] ListaTipoOrden = { "OCL", "OSL","OCMP"};

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_pendientes, container, false);
        spOrden = root.findViewById(R.id.sp_OrdenPendient);
        spSerie = root.findViewById(R.id.sp_SeriePendient);
        txt_can = root.findViewById(R.id.txt_cantOCMP);
        btn_buscarTIpoDoc = root.findViewById(R.id.btn_buscarTipoDoc);
        recyclerView = root.findViewById(R.id.rv_pendiente);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setHasOptionsMenu(true);


        ArrayAdapter prod = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, ListaTipoOrden);
        spOrden.setAdapter(prod);

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
                if (TipoOrden.equals("OSL")){
                    Toast.makeText(getContext(),TipoOrden,Toast.LENGTH_SHORT).show();
                    adapterMatePrima = new AdapterMatePrima(ListaOCL(TipoOrden));
                    adapterMatePrima.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    recyclerView.setAdapter(adapterMatePrima);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.setHasFixedSize(true);

                }else if(TipoOrden.equals("OCL")){
                    Toast.makeText(getContext(),TipoOrden,Toast.LENGTH_SHORT).show();
                    adapterMatePrima = new AdapterMatePrima(ListaOCL(TipoOrden));
                    adapterMatePrima.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
                    recyclerView.setAdapter(adapterMatePrima);
                    recyclerView.getAdapter().notifyDataSetChanged();
                    recyclerView.setHasFixedSize(true);

                }else{
                    String numSer = numSerie.toString().substring(2,4);
                    serie = TipoOrden+numSer;
                    Toast.makeText(getContext(),serie,Toast.LENGTH_SHORT).show();
                    try {
                        ListaCant(serie);
                        txt_can.setText(cantOCMP);
                        adapterMatePrima = new AdapterMatePrima(ListaOCMP(serie));
                        adapterMatePrima.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                        recyclerView.setAdapter(adapterMatePrima);
                        recyclerView.getAdapter().notifyDataSetChanged();
                        recyclerView.setHasFixedSize(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return root;
    }
    public void ListaCant(String tipoDoc) throws SQLException {
        PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call CONTAR_OCMP  ("+tipoDoc+")}");
        ResultSet rs = pst1.executeQuery();
        if(rs.next()){
            cantOCMP = rs.getString("cant");
        }
    }
    public void BuscarSerie(String tipoDoc) throws SQLException {
        PreparedStatement pst1 = conexionOfi.ConnectionDB().prepareStatement("{call BUSCAR_SERIE  ("+tipoDoc+")}");
        ResultSet rs = pst1.executeQuery();
        ArrayList<String> data= new ArrayList<>();
        while(rs.next()){
            //String orden = rs.getString("GRCCBH_CODCOM"); //orden
            String serie = rs.getString("USR_GRCCBH_NUSERI");
            data.add(serie);
        }
        ArrayAdapter prod = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1, data);
        spSerie.setAdapter(prod);
    }

    public List<ItemAprobMatePrima> ListaOCL(String num){
        List<ItemAprobMatePrima>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement st= conexionOfi.ConnectionDB().prepareStatement("{call LISTA_OCMP_PENDIENTES ("+num+")}");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ItemAprobMatePrima(rs.getString("SERIE"),rs.getString("NUMERO_DOC"),rs.getString("PVMPRH_NOMBRE"),rs.getString("PRODUCTO"),"","",rs.getString("PRECIO_TOTAL"),rs.getString("CORMVH_FCHMOV")) );
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Conexión inválida",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }

    public List<ItemAprobMatePrima> ListaOCMP(String num){
        List<ItemAprobMatePrima>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            PreparedStatement st= conexionOfi.ConnectionDB().prepareStatement("{call LISTA_OCMP_PENDIENTES ("+num+")}");
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ItemAprobMatePrima(rs.getString("SERIE"),rs.getString("NUMERO_DOC"),rs.getString("PVMPRH_NOMBRE"),rs.getString("PRODUCTO"),rs.getString("PRECIO_UNITARIO"),rs.getString("PESO"),rs.getString("PRECIO_TOTAL"),rs.getString("CORMVH_FCHMOV")) );
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Conexión inválida",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.sv_buscar);
        SearchView searchView = (SearchView) item.getActionView();
        search(searchView);

    }
    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    adapterMatePrima.getFilter().filter(s);
                    return true;
                }catch (Exception e){
                    Toast.makeText(getContext(),"nada que buscar",Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
        });
    }
}




























