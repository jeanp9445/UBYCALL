package com.rallendet.miapellido_s0x;

public class Login {

    String idLogin, correo, password, idUsuario;

    public Login(String idLogin, String correo, String password, String idUsuario) {
        this.idLogin = idLogin;
        this.correo = correo;
        this.password = password;
        this.idUsuario = idUsuario;
    }

    public String getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(String idLogin) {
        this.idLogin = idLogin;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
