package w.kipu.kipu.ITEM;

public class ItemLoteCant {
    String tipro;

    public String getTipro() {
        return tipro;
    }

    public void setTipro(String tipro) {
        this.tipro = tipro;
    }

    String codpro;
    String lote;
    String fecha;
    String cant;

    public ItemLoteCant(String tipro,String codpro, String lote, String fecha, String cant) {
        this.tipro = tipro;
        this.codpro = codpro;
        this.lote = lote;
        this.fecha = fecha;
        this.cant = cant;
    }

    public String getcodPro() {
        return codpro;
    }

    public void setcodPro(String tipro) {
        this.codpro = tipro;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }
}
