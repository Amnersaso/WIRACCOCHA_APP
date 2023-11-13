package w.kipu.kipu.ITEM;

public class ITEM_NCOMPRA {

    private String productor,peso,saco,total,numcompra;
    String idCompra;

    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    String tipoUser;

    public String getTipoCon() {
        return tipoCon;
    }

    public void setTipoCon(String tipoCon) {
        this.tipoCon = tipoCon;
    }

    String tipoCon;

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

    String idUsu;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    String estado;

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    String tipoRegistro;

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
    public ITEM_NCOMPRA(String productor, String peso, String saco, String total, String numcompra,String idComp,String idProv,String lote,String tipoRegistro,String estado,String idUsu,String tipoCon,String tipoUser) {
        this.productor = productor;
        this.peso = peso;
        this.saco = saco;
        this.total = total;
        this.numcompra = numcompra;
        this.idCompra = idComp;
        this.idProveedor = idProv;
        this.lote = lote;
        this.tipoRegistro=tipoRegistro;
        this.estado = estado;
        this.idUsu = idUsu;
        this.tipoCon = tipoCon;
        this.tipoUser = tipoUser;
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
