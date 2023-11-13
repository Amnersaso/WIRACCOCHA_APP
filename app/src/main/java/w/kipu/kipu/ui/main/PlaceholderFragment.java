package w.kipu.kipu.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import w.kipu.kipu.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        return root;
    }

    /*private void runAnimation(RecyclerView recyclerView, int type) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller = null;
        if(type == 0)
            controller = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_animation_fall_down);

        adaptador_ncompra = new ADAPTADOR_NCOMPRA(contacts);
        adaptador_ncompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        recyclerView.setAdapter(adaptador_ncompra);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }*/
   /* public List<ITEM_NCOMPRA> obtenerlista(){
        List<ITEM_NCOMPRA>  producto = new ArrayList<>();
        producto.clear();
        try{
            CONEXION conexion = new CONEXION();
            Statement st= conexion.ConnectionDB().createStatement();
            ResultSet rs=st.executeQuery("select * from Producto where Estado = '1' and id_categoria='"+c+"' order by PRECIO asc");
            while (rs.next()){
                producto.add(new ITEM_NCOMPRA(rs.getString("Nombre_Producto"),rs.getString("Descripcion"),rs.getString("Precio"),
                        rs.getString("Estado"),rs.getString("Stock"),rs.getString("Idproducto"),a));
            }
        }catch (SQLException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return producto;
    }*/

}