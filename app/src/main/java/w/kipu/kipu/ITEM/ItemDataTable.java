package w.kipu.kipu.ITEM;

public class ItemDataTable {

    public String getTIPPRO() {
        return TIPPRO;
    }

    public void setTIPPRO(String TIPPRO) {
        this.TIPPRO = TIPPRO;
    }

    public String getARTCOD() {
        return ARTCOD;
    }

    public void setARTCOD(String ARTCOD) {
        this.ARTCOD = ARTCOD;
    }

    public String getDESCRP() {
        return DESCRP;
    }

    public void setDESCRP(String DESCRP) {
        this.DESCRP = DESCRP;
    }

    public ItemDataTable(String TIPPRO, String ARTCOD, String DESCRP) {
        this.TIPPRO = TIPPRO;
        this.ARTCOD = ARTCOD;
        this.DESCRP = DESCRP;
    }

    String TIPPRO,ARTCOD,DESCRP;
}
