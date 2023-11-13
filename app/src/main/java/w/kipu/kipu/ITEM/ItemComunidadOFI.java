package w.kipu.kipu.ITEM;

public class ItemComunidadOFI {

    public String getIdComu() {
        return idComu;
    }

    public void setIdComu(String idComu) {
        this.idComu = idComu;
    }

    public String getComuni() {
        return comuni;
    }

    public void setComuni(String comuni) {
        this.comuni = comuni;
    }

    public ItemComunidadOFI(String idComu, String comuni) {
        this.idComu = idComu;
        this.comuni = comuni;
    }

    String idComu,comuni;

}
