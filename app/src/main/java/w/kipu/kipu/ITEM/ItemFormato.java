package w.kipu.kipu.ITEM;

public class ItemFormato {
    String cod,des;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public ItemFormato(String cod, String des,String id) {
        this.cod = cod;
        this.des = des;
        this.id=id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
