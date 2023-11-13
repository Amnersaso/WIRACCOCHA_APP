package w.kipu.kipu.ITEM;

public class ItemActividadProduccion {

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getH_inicio() {
        return h_inicio;
    }

    public void setH_inicio(String h_inicio) {
        this.h_inicio = h_inicio;
    }

    public String getSaco() {
        return saco;
    }

    public void setSaco(String saco) {
        this.saco = saco;
    }

    public String getKg() {
        return kg;
    }

    public void setKg(String kg) {
        this.kg = kg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ItemActividadProduccion(String actividad, String h_inicio, String saco, String kg, String total) {
        this.actividad = actividad;
        this.h_inicio = h_inicio;
        this.saco = saco;
        this.kg = kg;
        this.total = total;
    }

    String actividad,h_inicio,saco,kg,total;

}
