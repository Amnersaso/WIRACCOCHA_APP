package w.kipu.kipu.ITEM;

public class ItemDetalleListaProducto {

    public String getTituloProduct() {
        return tituloProduct;
    }

    public void setTituloProduct(String tituloProduct) {
        this.tituloProduct = tituloProduct;
    }

    public String getNumeroProd() {
        return numeroProd;
    }

    public void setNumeroProd(String numeroProd) {
        this.numeroProd = numeroProd;
    }

    public String getPesoReal() {
        return pesoReal;
    }

    public void setPesoReal(String pesoReal) {
        this.pesoReal = pesoReal;
    }

    public String getPesoRedondo() {
        return pesoRedondo;
    }

    public void setPesoRedondo(String pesoRedondo) {
        this.pesoRedondo = pesoRedondo;
    }

    public ItemDetalleListaProducto(String tituloProduct, String numeroProd, String pesoReal, String pesoRedondo,String observacion,String idComp,String idDeta) {
        this.tituloProduct = tituloProduct;
        this.numeroProd = numeroProd;
        this.pesoReal = pesoReal;
        this.pesoRedondo = pesoRedondo;
        this.observacion = observacion;
        this.idComp = idComp;
        this.idDeta = idDeta;
    }

    String tituloProduct,numeroProd,pesoReal,pesoRedondo;

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    String observacion;
    public String getIdComp() {
        return idComp;
    }

    public void setIdComp(String idComp) {
        this.idComp = idComp;
    }

    public String getIdDetalle() {
        return idDeta;
    }

    public void setSerieComp(String serieComp) {
        this.idDeta = serieComp;
    }

    String idComp,idDeta;
}
