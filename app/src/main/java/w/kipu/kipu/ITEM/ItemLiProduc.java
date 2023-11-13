package w.kipu.kipu.ITEM;

public class ItemLiProduc {
    String cod,desc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    public ItemLiProduc(String cod, String desc,String id) {
        this.cod = cod;
        this.desc = desc;
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
