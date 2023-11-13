package w.kipu.kipu.ITEM;

public class ItemSerieNum {
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemSerieNum( String id,String idCompra,String serie,String num) {
        this.serie = serie;
        this.id = id;
        this.idCompra =idCompra;
        this.num=num;
    }

    String serie;
    String id;

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    String idCompra;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    String num;
}
