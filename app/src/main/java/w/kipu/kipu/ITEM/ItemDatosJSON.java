package w.kipu.kipu.ITEM;

public class ItemDatosJSON {

    String FECHA_HORA;
    String NUM_ITEM;

    public String getLOTE_PROVEEDOR() {
        return LOTE_PROVEEDOR;
    }

    public void setLOTE_PROVEEDOR(String LOTE_PROVEEDOR) {
        this.LOTE_PROVEEDOR = LOTE_PROVEEDOR;
    }

    String LOTE_PROVEEDOR;

    public ItemDatosJSON(String FECHA_HORA, String NUM_ITEM, String PRODUCTO, String LOTE, String PESO_REAL, String PESO_MODIFICADO, String PRECIO_UNIT, String PRECIO_TOTAL, String OBSERVACION, String OBSERVACION_GENERAL,String LOTE_PROVEEDOR) {
        this.FECHA_HORA = FECHA_HORA;
        this.NUM_ITEM = NUM_ITEM;
        this.PRODUCTO = PRODUCTO;
        this.LOTE = LOTE;
        this.PESO_REAL = PESO_REAL;
        this.PESO_MODIFICADO = PESO_MODIFICADO;
        this.PRECIO_UNIT = PRECIO_UNIT;
        this.PRECIO_TOTAL = PRECIO_TOTAL;
        this.OBSERVACION = OBSERVACION;
        this.OBSERVACION_GENERAL = OBSERVACION_GENERAL;
        this.LOTE_PROVEEDOR=LOTE_PROVEEDOR;
    }

    String PRODUCTO;
    String LOTE;
    String PESO_REAL;
    String PESO_MODIFICADO;
    String PRECIO_UNIT;
    String PRECIO_TOTAL;
    String OBSERVACION;
    String OBSERVACION_GENERAL;

    public String getFECHA_HORA() {
        return FECHA_HORA;
    }

    public void setFECHA_HORA(String FECHA_HORA) {
        this.FECHA_HORA = FECHA_HORA;
    }

    public String getNUM_ITEM() {
        return NUM_ITEM;
    }

    public void setNUM_ITEM(String NUM_ITEM) {
        this.NUM_ITEM = NUM_ITEM;
    }

    public String getPRODUCTO() {
        return PRODUCTO;
    }

    public void setPRODUCTO(String PRODUCTO) {
        this.PRODUCTO = PRODUCTO;
    }

    public String getLOTE() {
        return LOTE;
    }

    public void setLOTE(String LOTE) {
        this.LOTE = LOTE;
    }

    public String getPESO_REAL() {
        return PESO_REAL;
    }

    public void setPESO_REAL(String PESO_REAL) {
        this.PESO_REAL = PESO_REAL;
    }

    public String getPESO_MODIFICADO() {
        return PESO_MODIFICADO;
    }

    public void setPESO_MODIFICADO(String PESO_MODIFICADO) {
        this.PESO_MODIFICADO = PESO_MODIFICADO;
    }

    public String getPRECIO_UNIT() {
        return PRECIO_UNIT;
    }

    public void setPRECIO_UNIT(String PRECIO_UNIT) {
        this.PRECIO_UNIT = PRECIO_UNIT;
    }

    public String getPRECIO_TOTAL() {
        return PRECIO_TOTAL;
    }

    public void setPRECIO_TOTAL(String PRECIO_TOTAL) {
        this.PRECIO_TOTAL = PRECIO_TOTAL;
    }

    public String getOBSERVACION() {
        return OBSERVACION;
    }

    public void setOBSERVACION(String OBSERVACION) {
        this.OBSERVACION = OBSERVACION;
    }

    public String getOBSERVACION_GENERAL() {
        return OBSERVACION_GENERAL;
    }

    public void setOBSERVACION_GENERAL(String OBSERVACION_GENERAL) {
        this.OBSERVACION_GENERAL = OBSERVACION_GENERAL;
    }


}
