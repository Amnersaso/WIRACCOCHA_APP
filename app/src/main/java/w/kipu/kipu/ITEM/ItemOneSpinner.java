package w.kipu.kipu.ITEM;

public class ItemOneSpinner {
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ItemOneSpinner(String texto,String cod) {
        this.texto = texto;
        this.cod = cod;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    String texto,cod;
}
