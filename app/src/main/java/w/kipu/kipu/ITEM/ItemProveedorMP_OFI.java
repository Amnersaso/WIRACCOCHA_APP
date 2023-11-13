package w.kipu.kipu.ITEM;

public class ItemProveedorMP_OFI {
    public String getNROCTA() {
        return NROCTA;
    }

    public void setNROCTA(String NROCTA) {
        this.NROCTA = NROCTA;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDIRECC() {
        return DIRECC;
    }

    public void setDIRECC(String DIRECC) {
        this.DIRECC = DIRECC;
    }

    public ItemProveedorMP_OFI(String NROCTA, String NOMBRE, String DIRECC) {
        this.NROCTA = NROCTA;
        this.NOMBRE = NOMBRE;
        this.DIRECC = DIRECC;
    }

    String NROCTA,NOMBRE,DIRECC;
}
