package com.rallendet.miapellido_s0x;

public class Estudiante {
    String id;
    String nombre, apellido;
    String ciclo;
    String descripcion, urlimagen;

    public Estudiante(String id, String nombre, String apellido, String ciclo, String descripcion, String urlimagen) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciclo = ciclo;
        this.descripcion = descripcion;
        this.urlimagen = urlimagen;
    }

    public Estudiante(String nombre, String apellido, String ciclo, String urlimagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciclo = ciclo;
        this.urlimagen = urlimagen;
    }

    public Estudiante(String nombre, String apellido, String ciclo, String descripcion, String urlimagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciclo = ciclo;
        this.descripcion = descripcion;
        this.urlimagen = urlimagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }
}
