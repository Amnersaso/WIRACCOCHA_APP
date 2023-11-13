package w.kipu.kipu.ITEM;

public class Item_EncabProductCompraPrint {

    String numSer;

    public String getNomProdFirma() {
        return nomProdFirma;
    }

    public void setNomProdFirma(String nomProdFirma) {
        this.nomProdFirma = nomProdFirma;
    }

    public String getDniProdFirma() {
        return dniProdFirma;
    }

    public void setDniProdFirma(String dniProdFirma) {
        this.dniProdFirma = dniProdFirma;
    }

    public String getNomUserFirma() {
        return nomUserFirma;
    }

    public void setNomUserFirma(String nomUserFirma) {
        this.nomUserFirma = nomUserFirma;
    }

    public String getDniUserFirma() {
        return dniUserFirma;
    }

    public void setDniUserFirma(String dniUserFirma) {
        this.dniUserFirma = dniUserFirma;
    }

    String nomProdFirma,dniProdFirma,nomUserFirma,dniUserFirma;

    public String getNumSer() {
        return numSer;
    }

    public void setNumSer(String numSer) {
        this.numSer = numSer;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    String dni;
    String productor;
    String comunidad;
    String distrito;
    String parcela;
    String tipo;
    String placa;
    public Item_EncabProductCompraPrint(String numSer, String dni, String productor, String comunidad, String distrito, String parcela, String tipo, String placa) {
        this.numSer = numSer;
        this.dni = dni;
        this.productor = productor;
        this.comunidad = comunidad;
        this.distrito = distrito;
        this.parcela = parcela;
        this.tipo = tipo;
        this.placa = placa;
    }



}
