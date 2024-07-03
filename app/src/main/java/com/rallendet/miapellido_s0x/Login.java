package com.rallendet.miapellido_s0x;

public class Login {

    String idLogin, correo, password;

    public Login(String idLogin, String correo, String password) {
        this.idLogin = idLogin;
        this.correo = correo;
        this.password = password;
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

}
