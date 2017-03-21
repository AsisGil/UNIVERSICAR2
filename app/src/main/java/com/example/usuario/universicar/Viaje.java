package com.example.usuario.universicar;


public class Viaje {

    String origen, destino, sentido, uni, fechadeviaje, fechadepublicacion, usuario, distancia, imagen, clave;

    public Viaje(String origen, String destino, String sentido, String uni, String fechadeviaje, String fechadepublicacion, String usuario, String distancia, String imagen, String clave) {
        this.origen = origen;
        this.destino = destino;
        this.sentido = sentido;
        this.uni = uni;
        this.fechadeviaje = fechadeviaje;
        this.fechadepublicacion = fechadepublicacion;
        this.usuario = usuario;
        this.distancia = distancia;
        this.imagen = imagen;
        this.clave = clave;
    }

    public Viaje() {
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getFechadeviaje() {
        return fechadeviaje;
    }

    public void setFechadeviaje(String fechadeviaje) {
        this.fechadeviaje = fechadeviaje;
    }

    public String getFechadepublicacion() {
        return fechadepublicacion;
    }

    public void setFechadepublicacion(String fechadepublicacion) {
        this.fechadepublicacion = fechadepublicacion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getIdImage() {
        return imagen;
    }
}