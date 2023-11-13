package w.kipu.kipu.ITEM;

public class ItemActividad {
    String actividad,tipro,prod,saco,peso,subActivi;
    String obs;

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public ItemActividad(String actividad, String tipro, String prod, String saco, String peso, String subActivi,String obs) {
        this.actividad = actividad;
        this.tipro = tipro;
        this.prod = prod;
        this.saco = saco;
        this.peso = peso;
        this.subActivi = subActivi;
        this.obs = obs;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTipro() {
        return tipro;
    }

    public void setTipro(String tipro) {
        this.tipro = tipro;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getSaco() {
        return saco;
    }

    public void setSaco(String saco) {
        this.saco = saco;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getSubActivi() {
        return subActivi;
    }

    public void setSubActivi(String subActivi) {
        this.subActivi = subActivi;
    }
}
