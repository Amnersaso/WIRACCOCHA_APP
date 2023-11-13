package w.kipu.kipu.ITEM;

public class ItemDocumentAsigSerieNum {

    public String getIdUsu() {
        return idUsu;
    }

    public void setIdUsu(String idUsu) {
        this.idUsu = idUsu;
    }

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

    public ItemDocumentAsigSerieNum(String idUsu, String serie, String num) {
        this.idUsu = idUsu;
        this.serie = serie;
        this.num = num;
    }

    String idUsu,serie,num;
}
