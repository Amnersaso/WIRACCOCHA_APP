package w.kipu.kipu.ITEM;

public class ItemAlmacen {

    public ItemAlmacen(String id, String descrip, String direc, String telefo) {
        this.id = id;
        this.descrip = descrip;
        this.direc = direc;
        this.telefo = telefo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getDirec() {
        return direc;
    }

    public void setDirec(String direc) {
        this.direc = direc;
    }

    public String getTelefo() {
        return telefo;
    }

    public void setTelefo(String telefo) {
        this.telefo = telefo;
    }

    String id,descrip,direc,telefo;

}
