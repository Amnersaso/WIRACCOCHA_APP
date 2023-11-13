package w.kipu.kipu.ITEM;

public class ItemAlmacenUbicacion {

    public ItemAlmacenUbicacion(String idAlmaUbi, String idAlma, String ubic, String desc) {
        this.idAlmaUbi = idAlmaUbi;
        this.idAlma = idAlma;
        this.ubic = ubic;
        this.desc = desc;
    }

    public String getIdAlmaUbi() {
        return idAlmaUbi;
    }

    public void setIdAlmaUbi(String idAlmaUbi) {
        this.idAlmaUbi = idAlmaUbi;
    }

    public String getIdAlma() {
        return idAlma;
    }

    public void setIdAlma(String idAlma) {
        this.idAlma = idAlma;
    }

    public String getUbic() {
        return ubic;
    }

    public void setUbic(String ubic) {
        this.ubic = ubic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    String idAlmaUbi,idAlma,ubic,desc;

}
