package w.kipu.kipu.ITEM;

public class ItemUsuario {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ItemUsuario(String id, String dni, String nombre, String telefono, String direccion, String login, String pass, String cargo, String estado,String idOFI) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.login = login;
        this.pass = pass;
        this.cargo = cargo;
        this.estado = estado;
        this.idOFI = idOFI;
    }

    public String getIdOFI() {
        return idOFI;
    }

    public void setIdOFI(String idOFI) {
        this.idOFI = idOFI;
    }

    String idOFI;
    String id,dni,nombre,telefono,direccion,login,pass,cargo,estado;

}
