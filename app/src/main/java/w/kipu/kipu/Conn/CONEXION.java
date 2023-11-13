package w.kipu.kipu.Conn;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class CONEXION {
    public Connection ConnectionDB_Local(){
        Connection conLocal=null;
        try {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //conLocal = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.20;databaseName=DB_WIRACCOCHA;user=sa;password=W1r@cc0ch@");
            conLocal = DriverManager.getConnection("jdbc:jtds:sqlserver://68.64.164.102:10043;databaseName=DB_KIPU;user=inka;password=Inka2022");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conLocal;
    }
//    public Connection ConnectionDB_Publica(){
//        Connection conPublica = null;
//        try {
//            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
//            conPublica = DriverManager.getConnection("jdbc:jtds:sqlserver://190.117.59.216;databaseName=DB_WIRACCOCHA;user=sa;password=W1r@cc0ch@");
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return conPublica;
//    }
}
