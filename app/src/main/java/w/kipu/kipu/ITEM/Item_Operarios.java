package w.kipu.kipu.ITEM;

public class Item_Operarios {
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Item_Operarios(String nombre, String actividad) {
        this.nombre = nombre;
        this.dni = actividad;
    }

    String nombre,dni;
}
