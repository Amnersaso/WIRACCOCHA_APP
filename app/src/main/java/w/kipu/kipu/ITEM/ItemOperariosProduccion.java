package w.kipu.kipu.ITEM;

public class ItemOperariosProduccion {

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public ItemOperariosProduccion(String nombre, String actividad) {
        this.nombre = nombre;
        this.actividad = actividad;
    }

    String nombre,actividad;

}
