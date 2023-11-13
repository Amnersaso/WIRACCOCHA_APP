package w.kipu.kipu.ITEM;

public class ItemXEnviar {

    private String productor,peso,saco,total,numcompra;
    String idCompra;

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    String lote;

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public String getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(String idProveedor) {
        this.idProveedor = idProveedor;
    }

    String idProveedor;

    public ItemXEnviar(String productor, String peso, String saco, String total, String numcompra,String idComp,String idProv,String lote) {
        this.productor = productor;
        this.peso = peso;
        this.saco = saco;
        this.total = total;
        this.numcompra = numcompra;
        this.idCompra = idComp;
        this.idProveedor = idProv;
        this.lote = lote;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getSaco() {
        return saco;
    }

    public void setSaco(String saco) {
        this.saco = saco;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNumcompra() {
        return numcompra;
    }

    public void setNumcompra(String numcompra) {
        this.numcompra = numcompra;
    }


}
