package w.kipu.kipu.ITEM;

public class ItemValidarProveedor {

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ItemValidarProveedor(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    String dni,nombre;

}
