package w.kipu.kipu.ITEM;

public class ItemPaletizado {
    String lote,numdoc,fecha,produc,linea,pingreso,psalida;

    public ItemPaletizado(String lote, String numdoc, String fecha, String produc, String linea, String pingreso, String psalida) {
        this.lote = lote;
        this.numdoc = numdoc;
        this.fecha = fecha;
        this.produc = produc;
        this.linea = linea;
        this.pingreso = pingreso;
        this.psalida = psalida;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getNumdoc() {
        return numdoc;
    }

    public void setNumdoc(String numdoc) {
        this.numdoc = numdoc;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProduc() {
        return produc;
    }

    public void setProduc(String produc) {
        this.produc = produc;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getPingreso() {
        return pingreso;
    }

    public void setPingreso(String pingreso) {
        this.pingreso = pingreso;
    }

    public String getPsalida() {
        return psalida;
    }

    public void setPsalida(String psalida) {
        this.psalida = psalida;
    }
}
