package w.kipu.kipu.ITEM;

public class ItemAprobMatePrima {
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getpUnit() {
        return pUnit;
    }

    public void setpUnit(String pUnit) {
        this.pUnit = pUnit;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getpTotal() {
        return pTotal;
    }

    public void setpTotal(String pTotal) {
        this.pTotal = pTotal;
    }

    public ItemAprobMatePrima(String serie, String num, String proveedor, String producto, String pUnit, String peso, String pTotal,String fecha) {
        this.serie = serie;
        this.num = num;
        this.proveedor = proveedor;
        this.producto = producto;
        this.pUnit = pUnit;
        this.peso = peso;
        this.pTotal = pTotal;
        this.fecha = fecha;
    }

    String serie,num,proveedor,producto,pUnit,peso,pTotal;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    String fecha;

}
