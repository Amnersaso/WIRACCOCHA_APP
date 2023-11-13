package w.kipu.kipu.ITEM;

public class Item_Aprobado {
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

    public String getFech_Emi() {
        return fech_Emi;
    }

    public void setFech_Emi(String fech_Emi) {
        this.fech_Emi = fech_Emi;
    }

    public String getProvee() {
        return provee;
    }

    public void setProvee(String provee) {
        this.provee = provee;
    }

    public String getObser() {
        return obser;
    }

    public void setObser(String obser) {
        this.obser = obser;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getUsua() {
        return usua;
    }

    public void setUsua(String usua) {
        this.usua = usua;
    }

    public String getFech_apro() {
        return fech_apro;
    }

    public void setFech_apro(String fech_apro) {
        this.fech_apro = fech_apro;
    }



    public Item_Aprobado(String serie, String num, String fech_Emi, String provee, String obser, String monto, String usua, String fech_apro) {
        this.serie = serie;
        this.num = num;
        this.fech_Emi = fech_Emi;
        this.provee = provee;
        this.obser = obser;
        this.monto = monto;
        this.usua = usua;
        this.fech_apro = fech_apro;
    }
    String serie;
    String num;
    String fech_Emi;
    String provee;
    String obser;
    String monto;
    String usua;
    String fech_apro;
}
