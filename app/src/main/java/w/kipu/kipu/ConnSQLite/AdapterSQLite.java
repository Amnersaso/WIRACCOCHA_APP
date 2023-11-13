package w.kipu.kipu.ConnSQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdapterSQLite extends SQLiteOpenHelper {

    public static final String DATABASENAME = "DB_KIPU_2023.db";

    //USUARIO
    public static final String TABLE_USUARIO="USUARIO";
    public static final String USUCOL_IDUSU="ID_USUARIO";
    public static final String USUCOL_DNI="DNI";
    public static final String USUCOL_NOMBR="NOMBRE_COMPLETO";
    public static final String USUCOL_TELEF="TELEFONO";
    public static final String USUCOL_DIREC="DIRECCION";
    public static final String USUCOL_LOGIN="LOGIN";
    public static final String USUCOL_PASS="PASSWORD";
    public static final String USUCOL_CARGO="CARGO";
    public static final String USUCOL_ESTAD="ESTADO";
    public static final String USUCOL_IDOFI="ID_OFI";
    public static final String USUCOL_FECMOD="FCHMOD";

    //PROVEEDOR MP
    public static final String TABLE_PROVEEDOR="PROVEEDOR_MP";
    public static final String PROVCOL_IDPROV="ID_PROVEEDOR";
    public static final String PROVCOL_DNI="DNI_RUC";
    public static final String PROVCOL_NOMBR="NOMBRE_COMPLETO";
    public static final String PROVCOL_TELEF="TELEFONO";
    public static final String PROVCOL_COMUNI="COMUNIDAD";
    public static final String PROVCOL_DISTRI="DISTRITO";
    public static final String PROVCOL_DIREC="DIRECCION";
    public static final String PROVCOL_REFERE="REFERENCIA";
    public static final String PROVCOL_TIPO="TIPO";
    public static final String PROVCOL_PARCE="PARCELA";

    //DETALLE COMPRA
    public static final String TABLE_DETALLE_COMPRA="DETALLE_COMPRA";
    public static final String DETCOMCOL_IDDETACOM="ID_DETALLECOMPRA";
    public static final String DETCOMCOL_IDCOMP="ID_COMPRA";
    public static final String DETCOMCOL_FECHA="FECHA_HORA";
    public static final String DETCOMCOL_NUMITEM="NUM_ITEM";
    public static final String DETCOMCOL_PRODUC="PRODUCTO";
    public static final String DETCOMCOL_LOTE_WIRACCOCHA="LOTE_WIRACCOCHA";
    public static final String DETCOMCOL_LOTE_PROVEEDOR="LOTE_PROVEEDOR";
    public static final String DETCOMCOL_ESTADOLOTE="ESTADOLOTE";
    public static final String DETCOMCOL_PESO_REAL="PESO_REAL";
    public static final String DETCOMCOL_PESO_MOD="PESO_MODIFICADO";
    public static final String DETCOMCOL_PREC_UNIT="PRECIO_UNIT";
    public static final String DETCOMCOL_PREC_TOTAL="PRECIO_TOTAL";
    public static final String DETCOMCOL_OBS="OBSERVACION";
    public static final String DETCOMCOL_OBS_GENERAL="OBSERVACION_GENERAL";
    public static final String DETCOMCOL_ESTADO="ESTADO";

    //ALMACEN
    public static final String TABLE_ALMACEN="ALMACEN";
    public static final String ALMACOL_IDALMACEN="ID_ALMACEN";
    public static final String ALMACOL_CODALMA="CODIGO";
    public static final String ALMACOL_DESC="DESCRIPCION";
    public static final String ALMACOL_DIREC="DIRECCION";
    public static final String ALMACOL_TELEF="TELEFONO";

    //ALMACEN UBICACION
    public static final String TABLE_ALMACEN_UBICACION="ALMACEN_UBICACION";
    public static final String ALMAUBICOL_IDALMA_UBI="ID_ALMACEN_UBICACION";
    public static final String ALMAUBICOL_IDALMA="CODALMA";
    public static final String ALMAUBICOL_UBICACION="UBICACION";
    public static final String ALMAUBICOL_DESC="DESCRIPCION";

    //LISTA PRODUCTO COMPRA
    public static final String TABLE_LISTA_PRODUCTOCOMPRA="LISTA_PRODUCTO_COMPRA";
    public static final String LPCOMCOL_IDPROD_COMPR="IDPRODUCTO_COMPRA";
    public static final String LPCOMCOL_CODPROD_COMP="CODIGOPRODUCTO_COMPRA";
    public static final String LPCOMCOL_NOMPROD_COMP="NOMBREPRODUCTO_COMPRA";

    //LISTA PRODUCTO VARIEDAD
    public static final String TABLE_LISTA_PRODUCTOVARIEDAD="LISTA_PRODUCTO_VARIEDAD";
    public static final String LPVARIECOL_IDPROD_VARIE="IDPRODUCTO_VARIEDAD";
    public static final String LPVARIECOL_CODPROD_COMP="CODIGOPRODUCTO_COMPRA";
    public static final String LPVARIECOL_NOMPROD_VARIE="NOMBREPRODUCTO_VARIEDAD";
    public static final String LPVARIECOL_TIPPRO="TIPPRO";
    public static final String LPVARIECOL_ARTCOD="ARTCOD";

    //LISTA COMUNIDAD
    public static final String TABLE_LISTA_COMUNIDAD="LISTA_COMUNIDAD";
    public static final String COMUCOL_IDCOMU="IDCOMUNIDAD";
    public static final String COMUCOL_DESCRIPCOMU="COMUNIDAD";

    //LISTA DISTRITO
    public static final String TABLE_LISTA_DISTRITO="LISTA_DISTRITO";
    public static final String DISCOL_IDDISTRI="GRTJUR_JURISD";
    public static final String DISCOL_DESCRIPDISTRI="GRTJUR_DESCRP";

    //COMPRA
    public static final String TABLE_COMPRA="COMPRA";
    public static final String COMCOL_IDCOMP="ID_COMPRA";
    public static final String COMCOL_IDUSU="ID_USUARIO";
    public static final String COMCOL_IDPROV="ID_PROVEEDOR";
    public static final String COMCOL_PER_COBRA="PERSONA_ACOBRAR";
    public static final String COMCOL_PER_ENTRE="PERSONA_QUIENENTREGA";
    public static final String COMCOL_TELEF="TELEFONO";
    public static final String COMCOL_TOTSACO="TOTAL_SACOS";
    public static final String COMCOL_TOTPESO="TOTAL_PESO";
    public static final String COMCOL_TOTPESO_MOD="TOTAL_PESO_MODIFICADO";
    public static final String COMCOL_TOTPESO_OBS="TOTAL_PESOOBSERVADO";
    public static final String COMCOL_MONTO_TOTAL="MONTO_TOTAL";
    public static final String COMCOL_SERIE="SERIE";
    public static final String COMCOL_NUMDOC="NUM_DOC";
    public static final String COMCOL_OBS_COMPRA="OBSERVACION_COMPRA";
    public static final String COMCOL_PLACA="PLACA";
    public static final String COMCOL_FECHA="FECHA";
    public static final String COMCOL_SOLI_ADELAN="SOLI_ADELANTO";
    public static final String COMCOL_ADELAN="ADELANTO";
    public static final String COMCOL_ESTADO="ESTADO";
    public static final String COMCOL_COMUNI="COMUNIDAD";
    public static final String COMCOL_DISTRI="DISTRITO";
    public static final String COMCOL_PARCEL="PARCELA";
    public static final String COMCOL_TIPOREG="TIPOREGISTRO";
    public static final String COMCOL_TIPOUSER="TIPOUSER";
    public static final String COMCOL_LOTEPROVEEDOR="LOTE_PROVEEDOR";
    public static final String COMCOL_GUIAREMISION="GUIA_REMISION";

    //OBSERVA
    public static final String TABLE_OBS="OBSERVACION";
    public static final String COMOBS_OBS="DESCRIPCION";

    //LISTA PRODUCTO PARA UPDATE DETALLE COMPRA
    public static final String TABLE_LISTAPRODUCT="LISTAPRODUCTO_UPDATE";
    public static final String COMCOL_LISTPRO_DESC="DESCRIPCION";

    //ALMACEN PRODUCTO
    public static final String TABLE_ALMACEN_PRODUCTO="ALMACEN_PRODUCTO";
    public static final String COMCOL_ALMAPROD_IDALMA="ID_ALMACENPRODUCTO";
    public static final String COMCOL_ALMAPROD_CODALMA="CODALMACEN";
    public static final String COMCOL_ALMAPROD_CODALMAUBIC="CODALMACENUBICACION";
    public static final String COMCOL_ALMAPROD_IDCOMPRA="ID_COMPRA";
    public static final String COMCOL_ALMAPROD_DESC="DESCRIPCION";
    public static final String COMCOL_ALMAPROD_LOTE="LOTE";
    public static final String COMCOL_ALMAPROD_STOCK="STOCK";
    public static final String COMCOL_ALMAPROD_FECHAVENC="FECHA_VENCIMIENTO";
    public static final String COMCOL_ALMAPROD_PRECOMPRA="PRECIO_COMPRA";
    public static final String COMCOL_ALMAPROD_PESOACUMU="TOTAL_PESOACUMULADO";
    public static final String COMCOL_ALMAPROD_ESTACALI="ESTADO_CALIDAD";

    //ESTIVA
    public static final String TABLE_ESTIVA="ESTIVA";
    public static final String COMCOL_ESTI_IDCOMPRA="ID_COMPRA";
    public static final String COMCOL_ESTI_NOMBRE="NOMBRE";
    public static final String COMCOL_ESTI_TIPO="TIPO";

    //SERIE USUARIOA
    public static final String TABLE_SERIEUSUARIO="SERIE_USUARIO";
    public static final String COMCOL_SERIEUSU_IDUSU="ID_USER";
    public static final String COMCOL_SERIEUSU_SERIE="SERIE";
    public static final String COMCOL_SERIEUSU_NUMERO="NUMERO";

    //SERIE RP
    public static final String TABLE_SERIENUM_RP="SERIENUM_RP";
    public static final String COMCOL_SERIENUM_IDUSUARIO="ID_USUARIO";
    public static final String COMCOL_SERIENUM_IDCOMPRA="ID_COMPRA";
    public static final String COMCOL_SERIENUM_SERIE="SERIE";
    public static final String COMCOL_SERIENUM_NUMERO="NUMERO";

    //ESTIVA
    public static final String TABLE_COD_VARPRODUCT="TB_CODIGO_VARIEDADPRODUCTO";
    public static final String COMCOL_COD_CODIGO="CODIGO";
    public static final String COMCOL_COD_DESCRIPC="DESCRIPCION";

    public AdapterSQLite(Context context){
        super(context,DATABASENAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  " + TABLE_USUARIO + " (IDUSUARIO INTEGER PRIMARY KEY AUTOINCREMENT ,ID_USUARIO TEXT,DNI TEXT,NOMBRE_COMPLETO TEXT,TELEFONO TEXT,DIRECCION TEXT,LOGIN TEXT,PASSWORD TEXT,CARGO TEXT,ESTADO TEXT,ID_OFI TEXT,FCHMOD TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_PROVEEDOR + " (IDPROVEEDOR INTEGER PRIMARY KEY AUTOINCREMENT ,ID_PROVEEDOR TEXT,DNI_RUC TEXT,NOMBRE_COMPLETO TEXT,TELEFONO TEXT,COMUNIDAD TEXT,DISTRITO TEXT,DIRECCION TEXT,REFERENCIA TEXT,TIPO TEXT,PARCELA TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_COMPRA + " (IDCOMPRA INTEGER PRIMARY KEY AUTOINCREMENT ,ID_COMPRA TEXT,ID_USUARIO TEXT,ID_PROVEEDOR TEXT,PERSONA_ACOBRAR TEXT,PERSONA_QUIENENTREGA TEXT,TELEFONO TEXT,TOTAL_SACOS TEXT," +
                "TOTAL_PESO TEXT,TOTAL_PESO_MODIFICADO TEXT,TOTAL_PESOOBSERVADO TEXT,MONTO_TOTAL TEXT,SERIE TEXT,NUM_DOC TEXT,OBSERVACION_COMPRA TEXT,PLACA TEXT,FECHA TEXT,SOLI_ADELANTO TEXT,ADELANTO TEXT,ESTADO TEXT,COMUNIDAD TEXT," +
                "DISTRITO TEXT,PARCELA TEXT,TIPOREGISTRO TEXT,TIPOUSER TEXT,LOTE_PROVEEDOR TEXT,GUIA_REMISION TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_DETALLE_COMPRA + " (IDDETALLE_COMPRA INTEGER PRIMARY KEY AUTOINCREMENT ,ID_DETALLECOMPRA TEXT,ID_COMPRA TEXT,FECHA_HORA TEXT,NUM_ITEM TEXT,PRODUCTO TEXT,LOTE_WIRACCOCHA TEXT,LOTE_PROVEEDOR TEXT,ESTADOLOTE TEXT," +
                "PESO_REAL TEXT,PESO_MODIFICADO TEXT,PRECIO_UNIT TEXT,PRECIO_TOTAL TEXT,OBSERVACION TEXT,OBSERVACION_GENERAL TEXT,ESTADO TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_ALMACEN + " (IDALMACEN INTEGER PRIMARY KEY AUTOINCREMENT ,ID_ALMACEN TEXT,CODIGO TEXT,DESCRIPCION TEXT,DIRECCION TEXT,TELEFONO TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_ALMACEN_UBICACION + " (IDALMACEN_UBICACION INTEGER PRIMARY KEY AUTOINCREMENT ,ID_ALMACEN_UBICACION TEXT,CODALMA TEXT,UBICACION TEXT,DESCRIPCION TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_LISTA_PRODUCTOCOMPRA + " (IDLISTA_PRODUCTOCOMPRA INTEGER PRIMARY KEY AUTOINCREMENT ,IDPRODUCTO_COMPRA TEXT,CODIGOPRODUCTO_COMPRA TEXT,NOMBREPRODUCTO_COMPRA TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_LISTA_PRODUCTOVARIEDAD + " (IDLISTA_PRODUCTOVARIEDAD INTEGER PRIMARY KEY AUTOINCREMENT ,IDPRODUCTO_VARIEDAD TEXT,CODIGOPRODUCTO_COMPRA TEXT,NOMBREPRODUCTO_VARIEDAD TEXT," +
                "TIPPRO TEXT,ARTCOD TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_LISTA_COMUNIDAD + " (IDLISTA_COMUNIDAD INTEGER PRIMARY KEY AUTOINCREMENT ,IDCOMUNIDAD TEXT,COMUNIDAD TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_LISTA_DISTRITO + " (IDLISTA_DISTRITO INTEGER PRIMARY KEY AUTOINCREMENT ,GRTJUR_JURISD TEXT,GRTJUR_DESCRP TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_OBS + " (IDOBS INTEGER PRIMARY KEY AUTOINCREMENT ,DESCRIPCION TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_LISTAPRODUCT + " (IDLISTAPRODUCT_UPDATE INTEGER PRIMARY KEY AUTOINCREMENT ,DESCRIPCION TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_ALMACEN_PRODUCTO + " (IDALMACEN_PRODUCTO INTEGER PRIMARY KEY AUTOINCREMENT ,ID_ALMACENPRODUCTO TEXT,CODALMACEN TEXT,CODALMACENUBICACION TEXT,ID_COMPRA TEXT,DESCRIPCION TEXT," +
                "LOTE TEXT,STOCK TEXT,FECHA_VENCIMIENTO TEXT, PRECIO_COMPRA TEXT,TOTAL_PESOACUMULADO TEXT,ESTADO_CALIDAD TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_ESTIVA + " (IDESTIVA INTEGER PRIMARY KEY AUTOINCREMENT ,ID_COMPRA TEXT,NOMBRE TEXT,TIPO TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_SERIEUSUARIO + " (IDUSUARIO INTEGER PRIMARY KEY AUTOINCREMENT ,ID_USER TEXT,SERIE TEXT,NUMERO TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_SERIENUM_RP + " (IDSERIENUM_RP INTEGER PRIMARY KEY AUTOINCREMENT ,ID_USUARIO TEXT,ID_COMPRA TEXT,SERIE TEXT,NUMERO TEXT)");
        db.execSQL("CREATE TABLE  " + TABLE_COD_VARPRODUCT + " (ID_CODVARPRODUC INTEGER PRIMARY KEY AUTOINCREMENT ,CODIGO TEXT,DESCRIPCION TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PROVEEDOR);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMPRA);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_DETALLE_COMPRA);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ALMACEN);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ALMACEN_UBICACION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LISTA_PRODUCTOCOMPRA);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LISTA_PRODUCTOVARIEDAD);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LISTA_COMUNIDAD);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LISTA_DISTRITO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_OBS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_LISTAPRODUCT);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ALMACEN_PRODUCTO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ESTIVA);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SERIEUSUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SERIENUM_RP);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_COD_VARPRODUCT);
    }
    //insert
    public boolean insertCod_VariedadProduc(String CODIGO,String DESCRIP){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_COD_CODIGO,CODIGO);
        contentValues.put(COMCOL_COD_DESCRIPC,DESCRIP);
        long result = db.insert(TABLE_COD_VARPRODUCT,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insertUsuario(String ID_USUARIO,String DNI,String NOMBRE_COMPLETO,String TELEFONO,String DIRECCION,String LOGIN,String PASSWORD,String CARGO,String ESTADO,String idOFI,String FCHMOD ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USUCOL_IDUSU,ID_USUARIO);
        contentValues.put(USUCOL_DNI,DNI);
        contentValues.put(USUCOL_NOMBR,NOMBRE_COMPLETO);
        contentValues.put(USUCOL_TELEF,TELEFONO);
        contentValues.put(USUCOL_DIREC,DIRECCION);
        contentValues.put(USUCOL_LOGIN,LOGIN);
        contentValues.put(USUCOL_PASS,PASSWORD);
        contentValues.put(USUCOL_CARGO,CARGO);
        contentValues.put(USUCOL_ESTAD,ESTADO);
        contentValues.put(USUCOL_IDOFI,idOFI);
        contentValues.put(USUCOL_FECMOD,FCHMOD);
        long result = db.insert(TABLE_USUARIO,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertProveedor(String ID_PROVEEDOR,String DNI_RUC,String NOMBRE_COMPLETO,String TELEFONO,String COMUNIDAD,String DISTRITO,String DIRECCION,String REFERENCIA,String TIPO,String PARCELA ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROVCOL_IDPROV,ID_PROVEEDOR);
        contentValues.put(PROVCOL_DNI,DNI_RUC);
        contentValues.put(PROVCOL_NOMBR,NOMBRE_COMPLETO);
        contentValues.put(PROVCOL_TELEF,TELEFONO);
        contentValues.put(PROVCOL_COMUNI,COMUNIDAD);
        contentValues.put(PROVCOL_DISTRI,DISTRITO);
        contentValues.put(PROVCOL_DIREC,DIRECCION);
        contentValues.put(PROVCOL_REFERE,REFERENCIA);
        contentValues.put(PROVCOL_TIPO,TIPO);
        contentValues.put(PROVCOL_PARCE,PARCELA);
        long result = db.insert(TABLE_PROVEEDOR,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertCompra(String ID_COMPRA,String ID_USUARIO,String ID_PROVEEDOR,String PERSONA_ACOBRAR,String PERSONA_QUIENENTREGA,String TELEFONO,String TOTAL_SACOS,String TOTAL_PESO,String TOTAL_PESO_MODIFICADO,
                                String TOTAL_PESOOBSERVADO,String MONTO_TOTAL,String SERIE,String NUM_DOC,String OBSERVACION_COMPRA,String PLACA,String FECHA,String SOLI_ADELANTO,String ADELANTO,String ESTADO,String COMUNIDAD,
                                String DISTRITO,String PARCELA,String TIPOREG,String TIPOUSER,String LOTE_PROVEEDOR,String GUIA_REMISION){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_IDCOMP,ID_COMPRA);
        contentValues.put(COMCOL_IDUSU,ID_USUARIO);
        contentValues.put(COMCOL_IDPROV,ID_PROVEEDOR);
        contentValues.put(COMCOL_PER_COBRA,PERSONA_ACOBRAR);
        contentValues.put(COMCOL_PER_ENTRE,PERSONA_QUIENENTREGA);
        contentValues.put(COMCOL_TELEF,TELEFONO);
        contentValues.put(COMCOL_TOTSACO,TOTAL_SACOS);
        contentValues.put(COMCOL_TOTPESO,TOTAL_PESO);
        contentValues.put(COMCOL_TOTPESO_MOD,TOTAL_PESO_MODIFICADO);
        contentValues.put(COMCOL_TOTPESO_OBS,TOTAL_PESOOBSERVADO);
        contentValues.put(COMCOL_MONTO_TOTAL,MONTO_TOTAL);
        contentValues.put(COMCOL_SERIE,SERIE);
        contentValues.put(COMCOL_NUMDOC,NUM_DOC);
        contentValues.put(COMCOL_OBS_COMPRA,OBSERVACION_COMPRA);
        contentValues.put(COMCOL_PLACA,PLACA);
        contentValues.put(COMCOL_FECHA,getDateTime());
        contentValues.put(COMCOL_SOLI_ADELAN,SOLI_ADELANTO);
        contentValues.put(COMCOL_ADELAN,ADELANTO);
        contentValues.put(COMCOL_ESTADO,ESTADO);
        contentValues.put(COMCOL_COMUNI,COMUNIDAD);
        contentValues.put(COMCOL_DISTRI,DISTRITO);
        contentValues.put(COMCOL_PARCEL,PARCELA);
        contentValues.put(COMCOL_TIPOREG,TIPOREG);
        contentValues.put(COMCOL_TIPOUSER,TIPOUSER);
        contentValues.put(COMCOL_LOTEPROVEEDOR,LOTE_PROVEEDOR);
        contentValues.put(COMCOL_GUIAREMISION,GUIA_REMISION);
        long result = db.insert(TABLE_COMPRA,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public boolean insertDetalleCompra(String ID_DETALLECOMPRA,String ID_COMPRA,String NUM_ITEM,String PRODUCTO,String LOTE_WIRACCOCHA,String ESTADOLOTE,String PESO_REAL,String PESO_MODIFICADO,String PRECIO_UNIT,
                                String PRECIO_TOTAL,String OBSERVACION,String OBSERVACION_GENERAL,String ESTADO,String LOTE_PROVEEDOR){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DETCOMCOL_IDDETACOM,ID_DETALLECOMPRA);
        contentValues.put(DETCOMCOL_IDCOMP,ID_COMPRA);
        contentValues.put(DETCOMCOL_FECHA,getDateTime());
        contentValues.put(DETCOMCOL_NUMITEM,NUM_ITEM);
        contentValues.put(DETCOMCOL_PRODUC,PRODUCTO);
        contentValues.put(DETCOMCOL_LOTE_WIRACCOCHA,LOTE_WIRACCOCHA);
        contentValues.put(DETCOMCOL_LOTE_PROVEEDOR,LOTE_PROVEEDOR);
        contentValues.put(DETCOMCOL_ESTADOLOTE,ESTADOLOTE);
        contentValues.put(DETCOMCOL_PESO_REAL,PESO_REAL);
        contentValues.put(DETCOMCOL_PESO_MOD,PESO_MODIFICADO);
        contentValues.put(DETCOMCOL_PREC_UNIT,PRECIO_UNIT);
        contentValues.put(DETCOMCOL_PREC_TOTAL,PRECIO_TOTAL);
        contentValues.put(DETCOMCOL_OBS,OBSERVACION);
        contentValues.put(DETCOMCOL_OBS_GENERAL,OBSERVACION_GENERAL);
        contentValues.put(DETCOMCOL_ESTADO,ESTADO);
        long result = db.insert(TABLE_DETALLE_COMPRA,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertAlmacen(String ID_ALMACEN,String CODIGO,String DESCRIPCION,String DIRECCION,String TELEFONO ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALMACOL_IDALMACEN,ID_ALMACEN);
        contentValues.put(ALMACOL_CODALMA,CODIGO);
        contentValues.put(ALMACOL_DESC,DESCRIPCION);
        contentValues.put(ALMACOL_DIREC,DIRECCION);
        contentValues.put(ALMACOL_TELEF,TELEFONO);
        long result = db.insert(TABLE_ALMACEN,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertAlmacenUbicacion(String ID_ALMACEN_UBICACION,String CODALMA,String UBICACION,String DESCRIPCION ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALMAUBICOL_IDALMA_UBI,ID_ALMACEN_UBICACION);
        contentValues.put(ALMAUBICOL_IDALMA,CODALMA);
        contentValues.put(ALMAUBICOL_UBICACION,UBICACION);
        contentValues.put(ALMAUBICOL_DESC,DESCRIPCION);
        long result = db.insert(TABLE_ALMACEN_UBICACION,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertListaProductCompra(String IDPRODUCTO_COMPRA,String CODIGOPRODUCTO_COMPRA,String NOMBREPRODUCTO_COMPRA ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LPCOMCOL_IDPROD_COMPR,IDPRODUCTO_COMPRA);
        contentValues.put(LPCOMCOL_CODPROD_COMP,CODIGOPRODUCTO_COMPRA);
        contentValues.put(LPCOMCOL_NOMPROD_COMP,NOMBREPRODUCTO_COMPRA);
        long result = db.insert(TABLE_LISTA_PRODUCTOCOMPRA,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertListaProductVariedad(String IDPRODUCTO_VARIEDAD,String CODIGOPRODUCTO_COMPRA,String NOMBREPRODUCTO_VARIEDAD,String TIPPRO,String ARTCOD){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LPVARIECOL_IDPROD_VARIE,IDPRODUCTO_VARIEDAD);
        contentValues.put(LPVARIECOL_CODPROD_COMP,CODIGOPRODUCTO_COMPRA);
        contentValues.put(LPVARIECOL_NOMPROD_VARIE,NOMBREPRODUCTO_VARIEDAD);
        contentValues.put(LPVARIECOL_TIPPRO,TIPPRO);
        contentValues.put(LPVARIECOL_ARTCOD,ARTCOD);
        long result = db.insert(TABLE_LISTA_PRODUCTOVARIEDAD,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertComunidad(String IDCOMUNIDAD,String COMUNIDAD ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMUCOL_IDCOMU,IDCOMUNIDAD);
        contentValues.put(COMUCOL_DESCRIPCOMU,COMUNIDAD);
        long result = db.insert(TABLE_LISTA_COMUNIDAD,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertDistrito(String GRTJUR_JURISD,String GRTJUR_DESCRP ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DISCOL_IDDISTRI,GRTJUR_JURISD);
        contentValues.put(DISCOL_DESCRIPDISTRI,GRTJUR_DESCRP);
        long result = db.insert(TABLE_LISTA_DISTRITO,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertObs(String DESCRIPCION ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMOBS_OBS,DESCRIPCION);
        long result = db.insert(TABLE_OBS,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertListaProducUpdate(String DESCRIPCION ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_LISTPRO_DESC,DESCRIPCION);
        long result = db.insert(TABLE_LISTAPRODUCT,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertAlmacenProducto(String ID_ALMACENPRODUCTO,String CODALMACEN,String CODALMACENUBICACION,String ID_COMPRA,String DESCRIPCION,String LOTE,String STOCK,String PCOMPRA,String PACUMULA,String ESTA_CALI){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_ALMAPROD_IDALMA,ID_ALMACENPRODUCTO);
        contentValues.put(COMCOL_ALMAPROD_CODALMA,CODALMACEN);
        contentValues.put(COMCOL_ALMAPROD_CODALMAUBIC,CODALMACENUBICACION);
        contentValues.put(COMCOL_ALMAPROD_IDCOMPRA,ID_COMPRA);
        contentValues.put(COMCOL_ALMAPROD_DESC,DESCRIPCION);
        contentValues.put(COMCOL_ALMAPROD_LOTE,LOTE);
        contentValues.put(COMCOL_ALMAPROD_STOCK,STOCK);
        contentValues.put(COMCOL_ALMAPROD_FECHAVENC,getDateTime());
        contentValues.put(COMCOL_ALMAPROD_PRECOMPRA,PCOMPRA);
        contentValues.put(COMCOL_ALMAPROD_PESOACUMU,PACUMULA);
        contentValues.put(COMCOL_ALMAPROD_ESTACALI,ESTA_CALI);
        long result = db.insert(TABLE_ALMACEN_PRODUCTO,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertEstiva(String ID_COMPRA,String NOMBRE,String TIPO ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_ESTI_IDCOMPRA,ID_COMPRA);
        contentValues.put(COMCOL_ESTI_NOMBRE,NOMBRE);
        contentValues.put(COMCOL_ESTI_TIPO,TIPO);
        long result = db.insert(TABLE_ESTIVA,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertSerieNum(String ID_USER,String SERIE,String NUMERO ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_SERIEUSU_IDUSU,ID_USER);
        contentValues.put(COMCOL_SERIEUSU_SERIE,SERIE);
        contentValues.put(COMCOL_SERIEUSU_NUMERO,NUMERO);
        long result = db.insert(TABLE_SERIEUSUARIO,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean insertSerieNum_RP(String ID_USUARIO,String ID_COMPRA,String SERIE,String NUMERO){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_SERIENUM_IDUSUARIO,ID_USUARIO);
        contentValues.put(COMCOL_SERIENUM_IDCOMPRA,ID_COMPRA);
        contentValues.put(COMCOL_SERIENUM_SERIE,SERIE);
        contentValues.put(COMCOL_SERIENUM_NUMERO,NUMERO);
        long result = db.insert(TABLE_SERIENUM_RP,null,contentValues);
        db.close();
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //select
    public Cursor selectSerieNum_RP(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_SERIENUM_RP,null);
        return res;
    }
    public Cursor selectProductCompra(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_LISTA_PRODUCTOCOMPRA,null);
        return res;
    }
    public Cursor selectProductVariedad(String codig){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_LISTA_PRODUCTOVARIEDAD +" where CODIGOPRODUCTO_COMPRA = ?",new String[]{codig});
        return res;
    }
    public Cursor selectUsuario(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_USUARIO,null);
        return res;
    }

    public Cursor selectCod_VariedadProduct(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select CODIGO, DESCRIPCION from " + TABLE_COD_VARPRODUCT,null);
        return res;
    }
    public Cursor selectUsuarioMax(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT MAX(FCHMOD) FROM USUARIO",null);
        return res;
    }
    public Cursor selectProveedor(String dni){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_PROVEEDOR+" WHERE DNI_RUC = ?",new String[]{dni});
        return res;
    }
    public Cursor selectCompra(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_COMPRA,null);
        return res;
    }
    public Cursor selectCompraPrueba(){
        String sql= "select C.ID_COMPRA,C.ID_PROVEEDOR,CONCAT( c.SERIE ,' - ',CONVERT(varchar,c.NUM_DOC)) as SERIE,"
                +"p.NOMBRE_COMPLETO,c.TOTAL_SACOS,c.MONTO_TOTAL,c.TOTAL_PESO_MODIFICADO "
                +"from COMPRA c INNER JOIN PROVEEDOR_MP p on c.ID_PROVEEDOR = p.ID_PROVEEDOR "
                +"INNER JOIN DETALLE_COMPRA D ON D.ID_COMPRA = C.ID_COMPRA "
                +"where c.SERIE like 'NC%' "
                +"group by p.NOMBRE_COMPLETO,c.TOTAL_SACOS,c.MONTO_TOTAL,c.TOTAL_PESO_MODIFICADO,C.ID_COMPRA,C.ID_PROVEEDOR,C.SERIE,C.NUM_DOC "
                +"order by c.ID_COMPRA desc";
        Cursor res = getReadableDatabase().rawQuery(sql,null);
        return res;
    }
    public Cursor selectDetalleCompra(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_DETALLE_COMPRA,null);
        return res;
    }
    public Cursor selectAlmacen(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_ALMACEN,null);
        return res;
    }
    public Cursor selectAlmacenUbicacion(String cod){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_ALMACEN_UBICACION +" WHERE CODALMA = ?",new String[]{cod});
        return res;
    }
    public Cursor selectComunidad(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_LISTA_COMUNIDAD,null);
        return res;
    }
    //
    public Cursor listaAutocompleteGac(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT NOMBRE_COMPLETO FROM USUARIO WHERE CARGO='GAC' AND LOGIN IS NOT NULL ",null);
        return res;
    }
    public Cursor selectDistrito(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_LISTA_DISTRITO,null);
        return res;
    }
    public Cursor selectObs(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_OBS,null);
        return res;
    }
    public Cursor selectListaProductUpdate(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_LISTAPRODUCT,null);
        return res;
    }
    public Cursor selectEstiva(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_ESTIVA,null);
        return res;
    }
    public Cursor selectSerieNum(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_SERIEUSUARIO,null);
        return res;
    }
    public Cursor selectProveedor(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_PROVEEDOR,null);
        return res;
    }
    public Cursor selectAlmacenUbicacion(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_ALMACEN_UBICACION,null);
        return res;
    }
    public Cursor selectProductVariedad(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_LISTA_PRODUCTOVARIEDAD,null);
        return res;
    }
    public Cursor selectValidarSerieNum_RP(String idUsu,String ser,String num){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT ID_USUARIO,SERIE,NUMERO FROM SERIENUM_RP " +
                "WHERE ID_USUARIO = ? AND SERIE = ? AND NUMERO=?";
        Cursor res = db.rawQuery(query,new String[]{idUsu,ser,num});
        return res;
    }

    //LISTA SPINNER SERIE RP
    public Cursor listaSpinnerSerie_RP(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT SERIE,NUMERO FROM SERIE_USUARIO " +
                "WHERE SERIE LIKE 'OCMP%' AND ID_USER <> 42 AND ID_USER <> 64 " +
                "ORDER BY SERIE ASC";
        Cursor res = db.rawQuery(query,null);
        return res;
    }
    public Cursor listaRecyclerSerieNum_RP(String idCom){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT IDSERIENUM_RP,ID_COMPRA,SERIE,NUMERO FROM SERIENUM_RP " +
                "WHERE ID_COMPRA="+idCom;
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    //MOSTRAR ALMACEN PARA ENVIAR AL SERVER
    public Cursor mostrarAlmacenProductoServer(String idComp){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT CODALMACEN,CODALMACENUBICACION from ALMACEN_PRODUCTO WHERE ID_COMPRA ="+idComp;
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    //conunt producto por variedad
    public Cursor selectCountNumItem(String idCompra,String produc){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select COUNT(NUM_ITEM) + 1 AS NUMERO_ITEM from " + TABLE_DETALLE_COMPRA + " WHERE ID_COMPRA = ? AND PRODUCTO = ?",new String[]{idCompra,produc});
        return res;
    }

    //MOSTRAR LISTA DE LOS ULTIMOS PESOS
    public Cursor selectMostrarUltimoPeso(String idCompra,String produc){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT NUM_ITEM,PESO_REAL FROM DETALLE_COMPRA WHERE ID_COMPRA ="+idCompra+" AND PRODUCTO='"+produc+"' " +
                     "ORDER BY IDDETALLE_COMPRA DESC " +
                     "LIMIT 5";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    //
    public Cursor selectMostrarEstivaPDF(String idCompra){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select NOMBRE from ESTIVA WHERE ID_COMPRA =" +idCompra;
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    //mostrar lista de pesos por variedad produc
    public Cursor selectMostrarListaProducto(String idCompra){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT C.IDCOMPRA,C.SERIE,COUNT(D.PRODUCTO) SACOS, D.PRODUCTO, SUM(D.PESO_REAL) PESO_REAL,SUM(D.PESO_MODIFICADO) PESO_MODIFICADO," +
                     "SUM(CASE WHEN D.OBSERVACION IS NOT null THEN D.PESO_REAL END) as OBSERVACION  from DETALLE_COMPRA D JOIN COMPRA C ON D.ID_COMPRA=C.IDCOMPRA " +
                     "where D.ID_COMPRA=? "+
                     "group by D.producto,C.SERIE,C.IDCOMPRA";
                Cursor res = db.rawQuery(query,new String[]{idCompra});
        return res;
    }
    public Cursor selectMostrarListaDetalleProducto(String idCompra,String prod){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="select IDDETALLE_COMPRA,ID_COMPRA,PRODUCTO,NUM_ITEM,PESO_REAL,PESO_MODIFICADO,OBSERVACION from DETALLE_COMPRA " +
                     "where ID_COMPRA=? and PRODUCTO = ?";
        Cursor res = db.rawQuery(query,new String[]{idCompra,prod});
        return res;
    }
    public boolean updateDetaListaPeso1(String idDeta,String idCompra,String prod,String p_Real,String p_Mod,String obs) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE DETALLE_COMPRA SET PESO_REAL="+p_Real+",PESO_MODIFICADO="+p_Mod+",OBSERVACION='"+obs+"',PRODUCTO='"+prod+"',NUM_ITEM=(SELECT COUNT(NUM_ITEM) FROM DETALLE_COMPRA WHERE ID_COMPRA="+idCompra+" AND PRODUCTO='"+prod+"') "+
                   "WHERE IDDETALLE_COMPRA="+idDeta);
        return true;
    }
    public boolean updateDetaListaPeso2(String idCompra,String prod) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE DETALLE_COMPRA SET NUM_ITEM = (SELECT COUNT(D1.FECHA_HORA)+1 FROM DETALLE_COMPRA D1 WHERE D1.ID_COMPRA=DETALLE_COMPRA.ID_COMPRA AND D1.PRODUCTO=DETALLE_COMPRA.PRODUCTO AND D1.FECHA_HORA<DETALLE_COMPRA.FECHA_HORA) " +
                    "WHERE DETALLE_COMPRA.ID_COMPRA="+idCompra+" AND DETALLE_COMPRA.PRODUCTO='"+prod+"'");
        return true;
    }

    //ACTUALIZAR ID_PROVEEDOR DE NUBE AL PROV ACTUAL
    public boolean updateIdProv_PROVEEDOR_MP(String dni,String idProv_VAL) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE PROVEEDOR_MP SET ID_PROVEEDOR="+idProv_VAL +
                " WHERE DNI_RUC='"+dni+"'");
        return true;
    }

    //ACTUALIZAR ID_PROVEEDOR COMPRA
    public boolean updateIdProv_PROVEEDOR_MP_COMPRA(String idCom,String idProv_VAL) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE COMPRA SET ID_PROVEEDOR = "+idProv_VAL+" WHERE IDCOMPRA="+idCom);
        return true;
    }

    //MOSTRAR CONTINUAR REGISTRO RP
    public Cursor selectContinuarRegistro_RP(String idCompra){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT COMPRA.IDCOMPRA, COMPRA.ID_USUARIO,COMPRA.ID_PROVEEDOR,COMPRA.PERSONA_QUIENENTREGA,COMPRA.TIPOREGISTRO,PROVEEDOR_MP.DNI_RUC,PROVEEDOR_MP.NOMBRE_COMPLETO,PROVEEDOR_MP.TIPO,COMPRA.PLACA,COMPRA.SERIE,COMPRA.NUM_DOC" +
                     " FROM COMPRA INNER JOIN PROVEEDOR_MP ON COMPRA.ID_PROVEEDOR = PROVEEDOR_MP.ID_PROVEEDOR " +
                     "WHERE COMPRA.IDCOMPRA = "+idCompra;
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    //MOSTRAR CODIGO VARIEDAD PRODUCTO PARA ASIGNAR SU LOTE
    public Cursor mostrarListaCodigoVariedadProducto(String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT CODIGO FROM TB_CODIGO_VARIEDADPRODUCTO  WHERE DESCRIPCION = '"+desc+"'";
        Cursor res = db.rawQuery(query,null);
        return res;
    }
    public Cursor verificarPrecioTotal_Compra(String id_com){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT ID_COMPRA,PRODUCTO,PRECIO_UNIT,PRECIO_TOTAL FROM DETALLE_COMPRA WHERE ID_COMPRA = "+id_com+" AND PRECIO_UNIT IS NULL";
        Cursor res = db.rawQuery(query,null);
        return res;
    }

    public boolean updateDetaListaPeso3(String idCompra,String prod) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE DETALLE_COMPRA SET NUM_ITEM = (SELECT COUNT(D1.FECHA_HORA)+1 FROM DETALLE_COMPRA D1 WHERE D1.ID_COMPRA=DETALLE_COMPRA.ID_COMPRA AND D1.PRODUCTO =DETALLE_COMPRA.PRODUCTO AND D1.FECHA_HORA<DETALLE_COMPRA.FECHA_HORA) " +
                "WHERE DETALLE_COMPRA.ID_COMPRA="+idCompra+" AND DETALLE_COMPRA.PRODUCTO='"+prod+"'");
        return true;
    }
    public Cursor selectMostrarProductAsigPrecio(String idCompra){
        SQLiteDatabase db = this.getWritableDatabase();
        String query="SELECT C.IDCOMPRA,C.SERIE,COUNT(D.PRODUCTO), D.PRODUCTO, SUM(D.PESO_REAL),SUM(D.PESO_MODIFICADO),SUM(CASE WHEN D.OBSERVACION IS NOT null THEN D.PESO_REAL END),D.LOTE_WIRACCOCHA,D.LOTE_PROVEEDOR,D.PRECIO_UNIT " +
                "from DETALLE_COMPRA D JOIN COMPRA C ON D.ID_COMPRA=C.IDCOMPRA" +
                " where D.ID_COMPRA=?" +
                " group by D.producto,C.SERIE,C.IDCOMPRA,D.LOTE_WIRACCOCHA,D.LOTE_PROVEEDOR";
        Cursor res = db.rawQuery(query,new String[]{idCompra});
        return res;
    }
    public boolean updateAsigPrecioDetaCompra(String idCompra,String prod,String punit,String estad,String loteWiraccocha,String obs,String LoteProveedor) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("update DETALLE_COMPRA set PRECIO_UNIT="+punit+", PRECIO_TOTAL = PESO_MODIFICADO * "+punit+", ESTADO ='"+estad+"',LOTE_WIRACCOCHA ='"+loteWiraccocha+"',OBSERVACION_GENERAL='"+obs+"',LOTE_PROVEEDOR='"+LoteProveedor+"' " +
                "where ID_COMPRA ="+idCompra+" and PRODUCTO = '"+prod+"'");
        return true;
    }
    public boolean updateCompraTotal(String idCompra) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("update COMPRA set TOTAL_SACOS = (select count(id_compra) Sacos from DETALLE_COMPRA where ID_COMPRA="+idCompra+")," +
                "TOTAL_PESO=(select sum(PESO_REAL) PesoReal from DETALLE_COMPRA where ID_COMPRA="+idCompra+" ), " +
                "TOTAL_PESO_MODIFICADO = (select sum(PESO_MODIFICADO) PesoModificado from DETALLE_COMPRA where ID_COMPRA="+idCompra+"), " +
                "TOTAL_PESOOBSERVADO=(select sum(PESO_REAL) from DETALLE_COMPRA where ID_COMPRA="+idCompra+" and OBSERVACION is not null),  " +
                "MONTO_TOTAL=(select sum(PRECIO_TOTAL) from DETALLE_COMPRA where ID_COMPRA="+idCompra+")  " +
                "where IDCOMPRA ="+idCompra);
        return true;
    }
    public boolean updateCompraAdelanto(String idCompra,String solAdelant,String adelanto, String obs) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE COMPRA SET SOLI_ADELANTO ="+solAdelant+", ADELANTO="+adelanto+", OBSERVACION_COMPRA = '"+obs+"',ESTADO='LOCAL' " +
                "WHERE IDCOMPRA ="+idCompra);
        return true;
    }
    public boolean updateCompraEstado(String idCompra) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE COMPRA SET ESTADO = 'LOCAL' WHERE IDCOMPRA = "+idCompra);
        return true;
    }
    public boolean updateSerieNum(String idUser,String serie,String num) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE SERIE_USUARIO SET NUMERO="+num+" WHERE ID_USER="+idUser+" AND SERIE='"+serie+"' ");
        return true;
    }
    public Cursor selectIdProveedorCompra(String idCompra) {
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("SELECT IDCOMPRA,ID_USUARIO,ID_PROVEEDOR,PERSONA_ACOBRAR,PERSONA_QUIENENTREGA FROM COMPRA WHERE IDCOMPRA ="+idCompra);
        Cursor res1 = db.rawQuery("SELECT * FROM "+TABLE_COMPRA +" WHERE IDCOMPRA=?",new String[]{idCompra});
        return res1;
    }
    public Cursor selectMostrarEncabezadoPDF(String idCompra,String idprov) {
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("SELECT IDCOMPRA,ID_USUARIO,ID_PROVEEDOR,PERSONA_ACOBRAR,PERSONA_QUIENENTREGA FROM COMPRA WHERE IDCOMPRA ="+idCompra);
        String sql= "select MAX(COMPRA.IDCOMPRA), COMPRA.SERIE, COMPRA.NUM_DOC ,PROVEEDOR_MP.DNI_RUC,PROVEEDOR_MP.NOMBRE_COMPLETO,COMPRA.PERSONA_QUIENENTREGA,COMPRA.PERSONA_ACOBRAR,PROVEEDOR_MP.TIPO,COMPRA.PLACA," +
                "PROVEEDOR_MP.COMUNIDAD,PROVEEDOR_MP.DISTRITO,PROVEEDOR_MP.PARCELA,COMPRA.FECHA,USUARIO.NOMBRE_COMPLETO,USUARIO.DNI,COMPRA.MONTO_TOTAL,COMPRA.ADELANTO,COMPRA.COMUNIDAD,COMPRA.DISTRITO ,COMPRA.PARCELA ,COMPRA.TIPOREGISTRO,COMPRA.TELEFONO,COMPRA.OBSERVACION_COMPRA,LOTE_PROVEEDOR,GUIA_REMISION  " +
                "from PROVEEDOR_MP INNER JOIN COMPRA ON PROVEEDOR_MP.ID_PROVEEDOR = COMPRA.ID_PROVEEDOR  inner join USUARIO ON USUARIO.ID_USUARIO = COMPRA.ID_USUARIO " +
                "WHERE COMPRA.ID_PROVEEDOR = ? AND COMPRA.IDCOMPRA = ? " +
                "order by COMPRA.NUM_DOC desc";
        Cursor res1 = db.rawQuery(sql,new String[]{idprov,idCompra});
        return res1;
    }
    public Cursor selectMostrarLote(String idComp){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res1 = db.rawQuery("select DISTINCT LOTE_WIRACCOCHA from DETALLE_COMPRA where ID_COMPRA=?",new String[]{idComp});
        return res1;
    }
    public Cursor selectMostrarListaDetalleCompra(String idUsu) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="select COMPRA.IDCOMPRA, COMPRA.ID_PROVEEDOR, COMPRA.SERIE, COMPRA.NUM_DOC, PROVEEDOR_MP.NOMBRE_COMPLETO, COMPRA.TOTAL_SACOS, COMPRA.MONTO_TOTAL, COMPRA.TOTAL_PESO_MODIFICADO, COMPRA.TIPOREGISTRO, COMPRA.ESTADO,COMPRA.ID_USUARIO,DETALLE_COMPRA.LOTE_WIRACCOCHA,COMPRA.TIPOUSER,DETALLE_COMPRA.LOTE_PROVEEDOR " +
                "from COMPRA INNER JOIN PROVEEDOR_MP on COMPRA.ID_PROVEEDOR = PROVEEDOR_MP.ID_PROVEEDOR " +
                "INNER JOIN DETALLE_COMPRA ON DETALLE_COMPRA.ID_COMPRA = COMPRA.IDCOMPRA " +
                "where COMPRA.SERIE like 'OCMP%' OR COMPRA.SERIE like 'SERV%' AND COMPRA.ID_USUARIO =" +idUsu+
                " group by PROVEEDOR_MP.NOMBRE_COMPLETO,COMPRA.TOTAL_SACOS,COMPRA.MONTO_TOTAL,COMPRA.TOTAL_PESO_MODIFICADO,COMPRA.IDCOMPRA,COMPRA.ID_PROVEEDOR,COMPRA.SERIE,COMPRA.NUM_DOC,COMPRA.TIPOREGISTRO,COMPRA.ESTADO " +
                "order by COMPRA.IDCOMPRA desc";
        Cursor res = db.rawQuery(sql,null);
        return res;
    }
    public Cursor selectMostrarListaRegistroPesoRecycler(String idUsu) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="select COMPRA.IDCOMPRA, COMPRA.ID_PROVEEDOR, COMPRA.SERIE, COMPRA.NUM_DOC, PROVEEDOR_MP.NOMBRE_COMPLETO, COMPRA.TOTAL_SACOS, COMPRA.MONTO_TOTAL, COMPRA.TOTAL_PESO_MODIFICADO, COMPRA.TIPOREGISTRO, COMPRA.ESTADO,COMPRA.ID_USUARIO,DETALLE_COMPRA.LOTE_WIRACCOCHA,COMPRA.TIPOUSER,DETALLE_COMPRA.LOTE_PROVEEDOR " +
                "from COMPRA INNER JOIN PROVEEDOR_MP on COMPRA.ID_PROVEEDOR = PROVEEDOR_MP.ID_PROVEEDOR " +
                "INNER JOIN DETALLE_COMPRA ON DETALLE_COMPRA.ID_COMPRA = COMPRA.IDCOMPRA " +
                "where COMPRA.SERIE like 'RP%' AND COMPRA.ID_USUARIO =" +idUsu+
                " group by PROVEEDOR_MP.NOMBRE_COMPLETO,COMPRA.TOTAL_SACOS,COMPRA.MONTO_TOTAL,COMPRA.TOTAL_PESO_MODIFICADO,COMPRA.IDCOMPRA,COMPRA.ID_PROVEEDOR,COMPRA.SERIE,COMPRA.NUM_DOC,COMPRA.TIPOREGISTRO,COMPRA.ESTADO " +
                "order by COMPRA.IDCOMPRA desc";
        Cursor res = db.rawQuery(sql,null);
        return res;
    }
    public Cursor selectMostrarProveedorRegistro(String idCom) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT COMPRA.ID_USUARIO,COMPRA.ID_PROVEEDOR,COMPRA.PERSONA_ACOBRAR,COMPRA.PERSONA_QUIENENTREGA,COMPRA.TELEFONO,COMPRA.SERIE,COMPRA.NUM_DOC,COMPRA.PLACA,COMPRA.ESTADO,COMPRA.COMUNIDAD,COMPRA.DISTRITO," +
                "COMPRA.PARCELA,COMPRA.TIPOREGISTRO,PROVEEDOR_MP.DNI_RUC,PROVEEDOR_MP.NOMBRE_COMPLETO,PROVEEDOR_MP.TIPO,PROVEEDOR_MP.DIRECCION,PROVEEDOR_MP.PARCELA,PROVEEDOR_MP.REFERENCIA,COMPRA.PLACA,PROVEEDOR_MP.TELEFONO, " +
                "COMPRA.LOTE_PROVEEDOR,COMPRA.GUIA_REMISION FROM COMPRA INNER JOIN PROVEEDOR_MP ON COMPRA.ID_PROVEEDOR =PROVEEDOR_MP.ID_PROVEEDOR " +
                "WHERE COMPRA.IDCOMPRA ="+idCom;
        Cursor res = db.rawQuery(sql,null);
        return res;
    }

    //
    public Cursor selectMostrarAlmacen(String idCom) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT ALMACEN.DESCRIPCION FROM ALMACEN INNER JOIN ALMACEN_PRODUCTO ON ALMACEN.CODIGO=ALMACEN_PRODUCTO.CODALMACEN " +
                "INNER JOIN COMPRA ON COMPRA.IDCOMPRA = ALMACEN_PRODUCTO.ID_COMPRA " +
                "WHERE COMPRA.IDCOMPRA = "+idCom;
        Cursor res = db.rawQuery(sql,null);
        return res;
    }

    //CONTAR EL ULTIMO IDPROV PARA AGREGAR NUEVO APROV
    public Cursor selectMostrarUltimoIDPROV() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT ID_PROVEEDOR FROM PROVEEDOR_MP ORDER BY IDPROVEEDOR DESC";
        Cursor res = db.rawQuery(sql,null);
        return res;
    }

    //LISTAR PESOS PREVISTA
    public Cursor ListaPrevistaPesos(String idCom,String prod) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="select PESO_REAL from DETALLE_COMPRA WHERE PRODUCTO = '"+prod+"' AND ID_COMPRA=" + idCom;
        Cursor res = db.rawQuery(sql,null);
        return res;
    }

    public Cursor selectListarPesoPDF(String idCom,String prod) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT PESO_MODIFICADO FROM DETALLE_COMPRA WHERE ID_COMPRA = "+idCom+" AND PRODUCTO = '"+prod+"'";
        Cursor res = db.rawQuery(sql,null);
        return res;
    }
    public Cursor selectListarEncabezadoPesoPDF(String idCom) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql="SELECT DETALLE_COMPRA.PRODUCTO,  COUNT(DETALLE_COMPRA.NUM_ITEM),  SUM(DETALLE_COMPRA.PESO_MODIFICADO),  DETALLE_COMPRA.PRECIO_UNIT,  SUM(DETALLE_COMPRA.PRECIO_TOTAL)," +
                "(select max( D.OBSERVACION_GENERAL) from DETALLE_COMPRA D where D.ID_COMPRA = DETALLE_COMPRA.ID_COMPRA AND D.PRODUCTO = DETALLE_COMPRA.PRODUCTO),DETALLE_COMPRA.LOTE_WIRACCOCHA,DETALLE_COMPRA.LOTE_PROVEEDOR,SUM(DETALLE_COMPRA.PESO_REAL) " +
                "FROM DETALLE_COMPRA " +
                "WHERE DETALLE_COMPRA.ID_COMPRA = "+idCom+
                " GROUP BY DETALLE_COMPRA.PRODUCTO,DETALLE_COMPRA.LOTE_WIRACCOCHA,DETALLE_COMPRA.PRECIO_UNIT,DETALLE_COMPRA.ID_COMPRA,DETALLE_COMPRA.LOTE_PROVEEDOR " +
                "ORDER BY DETALLE_COMPRA.PRODUCTO ASC";
        Cursor res = db.rawQuery(sql,null);
        return res;
    }
    public Cursor mostrarProvDNI(String idprov) {
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("SELECT IDCOMPRA,ID_USUARIO,ID_PROVEEDOR,PERSONA_ACOBRAR,PERSONA_QUIENENTREGA FROM COMPRA WHERE IDCOMPRA ="+idCompra);
        String sql= "select DNI_RUC,NOMBRE_COMPLETO,TELEFONO,COMUNIDAD,DISTRITO,DIRECCION,REFERENCIA,TIPO,PARCELA from PROVEEDOR_MP where ID_PROVEEDOR = "+idprov;
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }
    //mostrar lista estiva rp / recyclerview
    public Cursor listaEstivaRecyclerview(String idCom) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "select NOMBRE,ID_COMPRA,TIPO from ESTIVA where ID_COMPRA = "+idCom;
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }
    public Cursor mostrarListaDetalleCompraPesos(String idCom) {
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("SELECT IDCOMPRA,ID_USUARIO,ID_PROVEEDOR,PERSONA_ACOBRAR,PERSONA_QUIENENTREGA FROM COMPRA WHERE IDCOMPRA ="+idCompra);
        String sql= "select ID_COMPRA,FECHA_HORA,NUM_ITEM,PRODUCTO,PESO_REAL,PESO_MODIFICADO,PRECIO_UNIT,PRECIO_TOTAL,OBSERVACION,OBSERVACION_GENERAL,ESTADO,LOTE_WIRACCOCHA,LOTE_PROVEEDOR from DETALLE_COMPRA where ID_COMPRA ="+idCom;
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }
    public Cursor mostrarListaDetalleAlmacenProduc(String idCom) {
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("SELECT IDCOMPRA,ID_USUARIO,ID_PROVEEDOR,PERSONA_ACOBRAR,PERSONA_QUIENENTREGA FROM COMPRA WHERE IDCOMPRA ="+idCompra);
        String sql= "SELECT ID_COMPRA,PRODUCTO,COUNT(NUM_ITEM),SUM(PESO_REAL),SUM(PRECIO_TOTAL),LOTE_WIRACCOCHA,LOTE_PROVEEDOR FROM DETALLE_COMPRA" +
                " WHERE ID_COMPRA ="+idCom+
                " GROUP BY PRODUCTO";
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }
    public boolean cambiarEstadoCompra(String idCom) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE COMPRA SET ESTADO='ENVIADO' WHERE IDCOMPRA="+idCom);
        return true;
    }
    public Cursor mostrarListaSerieNumUser(String idUsu,String tipoDoc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "select ID_USER,SERIE,NUMERO from SERIE_USUARIO " +
                "WHERE ID_USER="+idUsu+" AND SERIE LIKE '"+tipoDoc+"%'";
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }

    //MOSTRAR LISTA DE PRODUCTOS REGISTRADOS EN RP
    public Cursor listaProductosRecyclerview_RP(String idCom) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT COMPRA.IDCOMPRA,COMPRA.SERIE,COUNT(DETALLE_COMPRA.PRODUCTO) SACOS, DETALLE_COMPRA.PRODUCTO, SUM(DETALLE_COMPRA.PESO_REAL) PESO_REAL,SUM(DETALLE_COMPRA.PESO_MODIFICADO) PESO_MODIFICADO," +
                "SUM(CASE WHEN DETALLE_COMPRA.OBSERVACION IS NOT null THEN DETALLE_COMPRA.PESO_REAL END) as OBSERVACION  from DETALLE_COMPRA JOIN COMPRA ON DETALLE_COMPRA.ID_COMPRA=COMPRA.IDCOMPRA " +
                "where DETALLE_COMPRA.ID_COMPRA="+idCom+" group by DETALLE_COMPRA.producto,COMPRA.SERIE,COMPRA.IDCOMPRA";
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }
    public Cursor listaSerieNumNC_by_RP(String idCom) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT COMPRA.SERIE,COMPRA.NUM_DOC,DETALLE_COMPRA.PRODUCTO,COUNT(DETALLE_COMPRA.ID_COMPRA) SACOS , SUM(DETALLE_COMPRA.PESO_MODIFICADO) AS TOTAL " +
                "FROM DETALLE_COMPRA INNER JOIN COMPRA ON DETALLE_COMPRA.ID_COMPRA = COMPRA.IDCOMPRA  " +
                "INNER JOIN SERIENUM_RP ON SERIENUM_RP.SERIE = COMPRA.SERIE AND SERIENUM_RP.NUMERO = COMPRA.NUM_DOC " +
                "WHERE SERIENUM_RP.ID_COMPRA = "+idCom+
                " GROUP BY COMPRA.SERIE,COMPRA.NUM_DOC,DETALLE_COMPRA.PRODUCTO " +
                "ORDER BY PRODUCTO";
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }
    public Cursor listaSerieNumEncabezado_RP(String idCom) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql= "SELECT ID_COMPRA,SERIE,NUMERO FROM SERIENUM_RP " +
                "WHERE ID_COMPRA ="+idCom;
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }

    public Cursor contarSacosCompra(String idComp,String prod) {
        SQLiteDatabase db = this.getReadableDatabase();
        //db.execSQL("SELECT IDCOMPRA,ID_USUARIO,ID_PROVEEDOR,PERSONA_ACOBRAR,PERSONA_QUIENENTREGA FROM COMPRA WHERE IDCOMPRA ="+idCompra);
        String sql= "SELECT COUNT(ID_COMPRA) SACOS FROM DETALLE_COMPRA " +
                "WHERE PRODUCTO="+prod+ " AND ID_COMPRA ="+idComp;
        Cursor res1 = db.rawQuery(sql,null);
        return res1;
    }

    //select proveedor y agregar. para compra
    public Cursor selectProveedorDNI(String dni){
        //String query = "Select * from " + TABLE_PROVEEDOR+" where DNI_RUC like '%"+dni+"%'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_PROVEEDOR+" where DNI_RUC like ?",new String[]{"'%"+dni+"%'"});
        //Cursor res = db.query(TABLE_PROVEEDOR,"DNI_RUC LIKE ?",new String[]{"%"+dni+"%"},null,null,null,null);
        return res;
    }
    public Cursor ValidarProveedor(String dni){//VALIDAR SI EXISTE PROVEEDOR
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_PROVEEDOR + " where DNI_RUC = ? ",new String[]{dni});
        return res;
    }
    public boolean UpdateProveedor(String idProv,String telefo,String comu,String distri,String refe,String tipo,String parce){//VALIDAR SI EXISTE PROVEEDOR
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROVCOL_TELEF,telefo);
        contentValues.put(PROVCOL_COMUNI,comu);
        contentValues.put(PROVCOL_DISTRI,distri);
        contentValues.put(PROVCOL_REFERE,refe);
        contentValues.put(PROVCOL_TIPO,tipo);
        contentValues.put(PROVCOL_PARCE,parce);
        int result = db.update(TABLE_PROVEEDOR,contentValues,"ID_PROVEEDOR = ?",new String[]{idProv});
        if (result>0)
            return true;
        else
            return false;
    }
    public boolean UpdateCompra(String idCompra,String idProv,String cobra,String entrega,String placa,String comu,String distri,String parce,String telefono,
                                String LOTE_PROVEEDOR,String GUIA_REMISION){//VALIDAR SI EXISTE PROVEEDOR
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_IDPROV,idProv);
        contentValues.put(COMCOL_PER_COBRA,cobra);
        contentValues.put(COMCOL_PER_ENTRE,entrega);
        contentValues.put(COMCOL_PLACA,placa);
        contentValues.put(COMCOL_COMUNI,comu);
        contentValues.put(COMCOL_DISTRI,distri);
        contentValues.put(COMCOL_PARCEL,parce);
        contentValues.put(COMCOL_TELEF,telefono);
        contentValues.put(COMCOL_LOTEPROVEEDOR,LOTE_PROVEEDOR);
        contentValues.put(COMCOL_GUIAREMISION,GUIA_REMISION);
        int result = db.update(TABLE_COMPRA,contentValues,"IDCOMPRA = ?",new String[]{idCompra});
        if (result>0)
            return true;
        else
            return false;
    }
    public boolean UpdateCompraGuiaLote(String idCompra,String LOTE_PROVEEDOR,String GUIA_REMISION){//ACTUALIZA COMPRA GUIA Y LOTE PROVEEDOR
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COMCOL_LOTEPROVEEDOR,LOTE_PROVEEDOR);
        contentValues.put(COMCOL_GUIAREMISION,GUIA_REMISION);
        int result = db.update(TABLE_COMPRA,contentValues,"IDCOMPRA = ?",new String[]{idCompra});
        if (result>0)
            return true;
        else
            return false;
    }
    public Cursor selectTopCompra(){
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor res = db.rawQuery("Select * from " + TABLE_COMPRA + "order by IDCOMPRA ",null);
        Cursor res1 = db.query(TABLE_COMPRA,null,null,null,null,null,"IDCOMPRA DESC",null);
        return res1;
    }


    //validar usuario
    public Cursor ValidarUsuario(String user,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_USUARIO + " where login = ? and password = ? and estado='HABILITADO'",new String[]{user,pass});
        return res;
    }

    //validar usuario RP pesos
    public Cursor ValidarUsuarioRP_InsertPeso(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select ID_USUARIO,DNI,NOMBRE_COMPLETO from " + TABLE_USUARIO + " where NOMBRE_COMPLETO = ? ",new String[]{user});
        return res;
    }
    //validar proveedor RP Gac
    public Cursor ValidarUsuarioRP_InsertPeso_GAC(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select ID_PROVEEDOR from PROVEEDOR_MP where NOMBRE_COMPLETO = ? ",new String[]{user});
        return res;
    }

    public Boolean ValidarUsuario_(String user,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * from " + TABLE_USUARIO + " where login = ? and password = ?",new String[]{user,pass});
        if (res.getCount()>0)
            return true;
        else
            return false;
    }

    //ELIMINAR SERIE NUM POR ID
    public void deleteSerieNum_RP(String idSerNum){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SERIENUM_RP WHERE IDSERIENUM_RP="+idSerNum);
    }
    //ELIMINAR SERIE NUM POR ID
    public void deleteDetalleCompra(String idCompra){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM DETALLE_COMPRA WHERE IDDETALLE_COMPRA="+idCompra);
    }

    //DELETE
    public void deleteProductCompra(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_LISTA_PRODUCTOCOMPRA);
    }
    public void deleteProductVariedad(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_LISTA_PRODUCTOVARIEDAD);
    }
    public void deleteUsusario(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_USUARIO);
    }
    public void deleteProveedor(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_PROVEEDOR);
    }
    public void deleteCodVarProduct(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_COD_VARPRODUCT);
    }
    public void deleteCompra(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_COMPRA);
    }
    public void deleteDetalleCompra(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_DETALLE_COMPRA);
    }
    public void deleteAlmacen(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_ALMACEN);
    }
    public void deleteAlmacenUbicacion(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_ALMACEN_UBICACION);
    }
    public void deleteComunidad(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_LISTA_COMUNIDAD);
    }
    public void deleteDistrito(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_LISTA_DISTRITO);
    }
    public void deleteObs(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_OBS);
    }
    public void deleteListaProductUpdate(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_LISTAPRODUCT);
    }
    public void deleteSerieNum(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_SERIEUSUARIO);
    }
}
