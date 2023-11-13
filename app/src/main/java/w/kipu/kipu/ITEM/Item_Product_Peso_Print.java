package w.kipu.kipu.ITEM;

public class Item_Product_Peso_Print {

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    String lote;

    public String getLoteProveedor() {
        return loteProveedor;
    }

    public void setLoteProveedor(String loteProveedor) {
        this.loteProveedor = loteProveedor;
    }

    String loteProveedor;

    public String getpUnit() {
        return pUnit;
    }

    public void setpUnit(String pUnit) {
        this.pUnit = pUnit;
    }

    String pUnit;

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    String observacion,subtotal;

        public Item_Product_Peso_Print(String producto, String peso, String total,String pesos,String lote,String observacion, String subtotal,String pUnit,String loteProveedor,String pesoReal) {
            this.producto = producto;
            this.peso = peso;
            this.total = total;
            this.pesos = pesos;
            this.lote = lote;
            this.observacion = observacion;
            this.subtotal = subtotal;
            this.pUnit = pUnit;
            this.loteProveedor=loteProveedor;
            this.pesoReal=pesoReal;
        }

        public String getProducto() {
            return producto;
        }

        public void setProducto(String producto) {
            this.producto = producto;
        }

        public String getPeso() {
            return peso;
        }

        public void setPeso(String peso) {
            this.peso = peso;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        String producto,peso,total;

        public String getPesos() {
            return pesos;
        }

        public void setPesos(String pesos) {
            this.pesos = pesos;
        }

        String pesos;

    public String getPesoReal() {
        return pesoReal;
    }

    public void setPesoReal(String pesoReal) {
        this.pesoReal = pesoReal;
    }

    String pesoReal;

}
