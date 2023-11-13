package w.kipu.kipu.ITEM;

public class ItemEstivaJSON {
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ItemEstivaJSON(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    String nombre,tipo;
}
