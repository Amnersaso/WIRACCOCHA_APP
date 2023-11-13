package w.kipu.kipu.formtocompra;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import w.kipu.kipu.Conn.CONEXION;
import w.kipu.kipu.ITEM.ITEM_NCOMPRA;

public class Fragment_RegistroPesoTest {

    Connection conAll;
    CONEXION conexion;
    @Test
    public void newInstance() {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onCreateView() {
    }

    @Test
    public void obtenerDetalleNCompra() {

        List<ITEM_NCOMPRA> ListaProducto= new ArrayList<>();
        ListaProducto.clear();
        try{
            conAll = conexion.ConnectionDB_Publica();
            PreparedStatement pst1 = conAll.prepareStatement("{call SPAPP_SELIST_NCOMPRA (?)}");
            //pst1.setString(1,"");
            ResultSet rs = pst1.executeQuery();
            while (rs.next()){
                ListaProducto.add(new ITEM_NCOMPRA(rs.getString("NOMBRE_COMPLETO"),rs.getString("TOTAL_PESO_MODIFICADO"),rs.getString("TOTAL_SACOS"),rs.getString("MONTO_TOTAL")
                        ,rs.getString("SERIE"),rs.getString("ID_COMPRA"),rs.getString("ID_PROVEEDOR"),rs.getString("LOTE")
                        ,rs.getString("TIPOREGISTRO"),rs.getString("ESTADO"),rs.getString("TIPOREGISTRO"),"NUBE","NUBE"));
            }
        }catch (Exception e){

        }

    }
}