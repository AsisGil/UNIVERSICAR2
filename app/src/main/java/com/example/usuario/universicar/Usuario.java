package com.example.usuario.universicar;

public class Usuario {

    private String usuario_string, email_string, password_string, rep_password_string, telefono_string, provincia, coche_string;
    private boolean coche;

    public Usuario(String usuario_string, String email_string, String password_string, String rep_password_string, String telefono_string, boolean coche) {
        this.usuario_string = usuario_string;
        this.email_string = email_string;
        this.password_string = password_string;
        this.rep_password_string = rep_password_string;
        this.telefono_string = telefono_string;
        this.coche = coche;
    }

    public Usuario() {
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCoche_string() {
        return coche_string;
    }

    public void setCoche_string(String coche_string) {
        this.coche_string = coche_string;
    }


    public String getUsuario_string() {
        return usuario_string;
    }

    public void setUsuario_string(String usuario_string) {
        this.usuario_string = usuario_string;
    }

    public String getEmail_string() {
        return email_string;
    }

    public void setEmail_string(String email_string) {
        this.email_string = email_string;
    }

    public String getRep_password_string() {
        return rep_password_string;
    }

    public void setRep_password_string(String rep_password_string) {
        this.rep_password_string = rep_password_string;
    }

    public String getPassword_string() {
        return password_string;
    }

    public void setPassword_string(String password_string) {
        this.password_string = password_string;
    }

    public String getTelefono_string() {
        return telefono_string;
    }

    public void setTelefono_string(String telefono_string) {
        this.telefono_string = telefono_string;
    }

    public boolean getCoche() {
        return coche;
    }

    public void setCoche(boolean coche) {
        this.coche = coche;
    }
}