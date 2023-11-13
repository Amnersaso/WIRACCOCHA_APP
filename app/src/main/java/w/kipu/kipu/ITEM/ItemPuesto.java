package w.kipu.kipu.ITEM;

public class ItemPuesto {

    String cod,puest;

    public ItemPuesto(String puest, String cod) {
        this.cod = cod;
        this.puest = puest;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getPuest() {
        return puest;
    }

    public void setPuest(String puest) {
        this.puest = puest;
    }
}
