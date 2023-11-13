package w.kipu.kipu.ui.desaprobado;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION_OFI;
import w.kipu.kipu.ITEM.Item_Aprobado;
import w.kipu.kipu.R;
import w.kipu.kipu.ui.aprobados.AdapterAprobado;

public class NotificationsFragment extends Fragment {

    Connection con;
    AdapterAprobado adapterMatePrima;
    CONEXION_OFI conexionOfi = new CONEXION_OFI();
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_desaprobados, container, false);
        recyclerView = root.findViewById(R.id.rv_desaprobado);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterMatePrima = new AdapterAprobado(ObtenerMatePrima());
        recyclerView.setAdapter(adapterMatePrima);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.setHasFixedSize(true);
        return root;
    }
    public List<Item_Aprobado> ObtenerMatePrima(){
        List<Item_Aprobado>  ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            con = conexionOfi.ConnectionDB();
            if (con == null){
                Toast.makeText(getContext(),"desconectado",Toast.LENGTH_SHORT).show();
            }else{
                PreparedStatement st= conexionOfi.ConnectionDB().prepareStatement("{call LISTA_ORD_DESAPROBADOS}");
                ResultSet rs = st.executeQuery();
                while (rs.next()){
                    ListaProducto.add(new Item_Aprobado(rs.getString("SERIE")+" - ",rs.getString("NUMERO_DOC"),rs.getString("CORMVH_FECMOD"),rs.getString("PVMPRH_NOMBRE"),rs.getString("PRODUCTO"),rs.getString("PRECIO_TOTAL"),rs.getString("CORMVH_USRAUT"),rs.getString("FECHA_AUTORIZACION")) );
                }
                Toast.makeText(getContext(),"conectado",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Conexión inválida",Toast.LENGTH_SHORT).show();
        }
        return ListaProducto;
    }
}