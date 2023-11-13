package w.kipu.kipu.ITEM;

public class ItemListaSerieNumPeso {
    public ItemListaSerieNumPeso(String serie, String num, String peso,String prod) {
        this.serie = serie;
        this.num = num;
        this.peso = peso;
        this.prod = prod;
    }

    String serie,num,peso,prod;

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
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

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }
}