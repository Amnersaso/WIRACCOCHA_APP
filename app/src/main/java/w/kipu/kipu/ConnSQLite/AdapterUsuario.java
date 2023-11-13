package w.kipu.kipu.ConnSQLite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import w.kipu.kipu.ITEM.ItemUsuario;
import w.kipu.kipu.R;

public class AdapterUsuario extends ArrayAdapter<ItemUsuario> {

    private List<ItemUsuario> objects;
    private Context context;

    public AdapterUsuario(Context context, int resourceId, List<ItemUsuario> objects) {
        super(context, resourceId, objects);
        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row=inflater.inflate(R.layout.item_usuario_sqlite, parent, false);
        TextView id=row.findViewById(R.id.txt_idUsuarioSQLite);
        TextView dni=row.findViewById(R.id.txt_dniUsuarioSQLite);
        TextView nombre = row.findViewById(R.id.txt_nombreUsuarioSQLite);
        TextView telefo=row.findViewById(R.id.txt_telefoUsuarioSQLite);
        TextView direc=row.findViewById(R.id.txt_direccionUsuarioSQLite);
        TextView login = row.findViewById(R.id.txt_loginUsuarioSQLite);
        TextView pass=row.findViewById(R.id.txt_passUsuarioSQLite);
        TextView cargo=row.findViewById(R.id.txt_cargoUsuarioSQLite);
        TextView estado = row.findViewById(R.id.txt_estadoUsuarioSQLite);
        id.setText(((ItemUsuario) this.objects.get(position)).getId());
        dni.setText(((ItemUsuario) this.objects.get(position)).getDni());
        nombre.setText(((ItemUsuario) this.objects.get(position)).getNombre());
        telefo.setText(((ItemUsuario) this.objects.get(position)).getTelefono());
        direc.setText(((ItemUsuario) this.objects.get(position)).getDireccion());
        login.setText(((ItemUsuario) this.objects.get(position)).getLogin());
        pass.setText(((ItemUsuario) this.objects.get(position)).getPass());
        cargo.setText(((ItemUsuario) this.objects.get(position)).getCargo());
        estado.setText(((ItemUsuario) this.objects.get(position)).getEstado());
        return row;
    }
}