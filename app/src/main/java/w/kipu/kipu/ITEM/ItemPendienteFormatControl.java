package w.kipu.kipu.ITEM;

public class ItemPendienteFormatControl {
    String numSerie,serie,codLinea,linea,lote,codTipro,desTipro,codProd,desProd,cant,fecReg;
    String obs;
    String cantSalida;
    String humedad;

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getCantSalida() {
        return cantSalida;
    }

    public void setCantSalida(String cantSalida) {
        this.cantSalida = cantSalida;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public ItemPendienteFormatControl(String numSerie, String serie, String codLinea, String linea, String lote, String codTipro, String desTipro, String codProd, String desProd, String cant, String fecReg,String obse,String cantSalida,String Humedad) {
        this.numSerie = numSerie;
        this.serie = serie;
        this.codLinea = codLinea;
        this.linea = linea;
        this.lote = lote;
        this.codTipro = codTipro;
        this.desTipro = desTipro;
        this.codProd = codProd;
        this.desProd = desProd;
        this.cant = cant;
        this.fecReg = fecReg;
        this.obs = obse;
        this.cantSalida = cantSalida;
        this.humedad = Humedad;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCodLinea() {
        return codLinea;
    }

    public void setCodLinea(String codLinea) {
        this.codLinea = codLinea;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getCodTipro() {
        return codTipro;
    }

    public void setCodTipro(String codTipro) {
        this.codTipro = codTipro;
    }

    public String getDesTipro() {
        return desTipro;
    }

    public void setDesTipro(String desTipro) {
        this.desTipro = desTipro;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public String getDesProd() {
        return desProd;
    }

    public void setDesProd(String desProd) {
        this.desProd = desProd;
    }

    public String getCant() {
        return cant;
    }

    public void setCant(String cant) {
        this.cant = cant;
    }

    public String getFecReg() {
        return fecReg;
    }

    public void setFecReg(String fecReg) {
        this.fecReg = fecReg;
    }
}
