package w.kipu.kipu.ITEM;

public class ItemMostrarListaProducto {

    String producto,saco,pesoNeto,pesoRedondo,observa,idComp,serieComp;
    String loteWiraccocha;

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    String precio;

    public String getLoteWiraccocha() {
        return loteWiraccocha;
    }

    public void setLoteWiraccocha(String loteWiraccocha) {
        this.loteWiraccocha = loteWiraccocha;
    }

    public String getLoteProveedor() {
        return loteProveedor;
    }

    public void setLoteProveedor(String loteProveedor) {
        this.loteProveedor = loteProveedor;
    }

    String loteProveedor;
    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getSaco() {
        return saco;
    }

    public void setSaco(String saco) {
        this.saco = saco;
    }

    public String getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(String pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public String getPesoRedondo() {
        return pesoRedondo;
    }

    public void setPesoRedondo(String pesoRedondo) {
        this.pesoRedondo = pesoRedondo;
    }

    public ItemMostrarListaProducto(String producto, String saco, String pesoNeto, String pesoRedondo, String observa,String idComp,String serieComp,String loteWiraccocha,String loteProveedor,String precio) {
        this.producto = producto;
        this.saco = saco;
        this.pesoNeto = pesoNeto;
        this.pesoRedondo = pesoRedondo;
        this.observa = observa;
        this.idComp = idComp;
        this.serieComp = serieComp;
        this.loteWiraccocha = loteWiraccocha;
        this.loteProveedor= loteProveedor;
        this.precio=precio;
    }
    public String getObserva() {
        return observa;
    }
    public void setObserva(String observa) {
        this.observa = observa;
    }
    public String getIdComp() {
        return idComp;
    }
    public void setIdComp(String idComp) {
        this.idComp = idComp;
    }
    public String getSerieComp() {
        return serieComp;
    }
    public void setSerieComp(String serieComp) {
        this.serieComp = serieComp;
    }

}
