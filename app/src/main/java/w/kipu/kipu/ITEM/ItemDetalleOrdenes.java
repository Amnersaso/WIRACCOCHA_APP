package w.kipu.kipu.ITEM;

public class ItemDetalleOrdenes {

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getPunit() {
        return punit;
    }

    public void setPunit(String punit) {
        this.punit = punit;
    }

    public String getPtotal() {
        return ptotal;
    }

    public void setPtotal(String ptotal) {
        this.ptotal = ptotal;
    }

    public ItemDetalleOrdenes(String desc, String cant, String punit, String ptotal) {
        this.desc = desc;
        this.cant = cant;
        this.punit = punit;
        this.ptotal = ptotal;
    }

    String desc,cant,punit,ptotal;
}
