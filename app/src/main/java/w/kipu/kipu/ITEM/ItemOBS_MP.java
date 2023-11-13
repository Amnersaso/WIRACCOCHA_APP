package w.kipu.kipu.ITEM;

public class ItemOBS_MP {

    public String getIdOBS() {
        return idOBS;
    }

    public void setIdOBS(String idOBS) {
        this.idOBS = idOBS;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public ItemOBS_MP(String idOBS, String descrip) {
        this.idOBS = idOBS;
        this.descrip = descrip;
    }

    String idOBS,descrip;

}
