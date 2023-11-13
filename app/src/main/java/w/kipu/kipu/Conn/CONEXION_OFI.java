package w.kipu.kipu.Conn;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CONEXION_OFI {
 CONEXION conexion = new CONEXION();
 public String ip,db,us,pa,descripcion,estado;

 public Connection ConnectionDB(){
     Connection connection = null;
     try {
         //ObtCON();
         StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
         StrictMode.setThreadPolicy(policy);
         Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
         //connection= DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+";databaseName="+db+";user="+us+";password="+pa+"");
         connection = DriverManager.getConnection("jdbc:jtds:sqlserver://190.117.59.216;databaseName=WIRACCOCHA;user=sa;password=W1r@cc0ch@");
     } catch (Exception e) {
         Log.e("ERROR ", e.getMessage());
     }
     return connection;
 }

//    public void ObtCON() throws SQLException {
//        PreparedStatement pst1 = conexion.ConnectionDB_Publica().prepareStatement("{call CON_DB}");
//        ResultSet rs = pst1.executeQuery();
//        while(rs.next()){
//            ip = rs.getString("IP");
//            db = rs.getString("DB");
//            us = rs.getString("US");
//            pa = rs.getString("PA");
//            descripcion = rs.getString("DESCRIPCION");
//            estado = rs.getString("ESTADO");
//        }
//    }
}
