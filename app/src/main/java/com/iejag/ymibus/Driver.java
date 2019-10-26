package com.iejag.ymibus;

public class Driver {
    String uid;
    String nombre;
    String apellido;
    String usuario;
    String correo;
    String celular;

    public Driver(){

    }

    public Driver(String uid, String nombre, String apellido, String usuario, String correo, String celular) {
        this.uid = uid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.correo = correo;
        this.celular = celular;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}

/* pendiente

1. Poner log off en DriverActivity
2. Poner Spinner con rutas en DriverActivity
3. Colocar la imagen en la parte superior del login
4. Quitar el constraint layout del SplashActivity
5. Quitar el constraint layout UserDriverActity
6. Eliminar el marker cuando se inactiva el driver
7. Crear metodo para actualizar la posicion del driver
8. Revisar calculo de tiempo y metros para destino
9. Poner mas rutas en RutasActivity
10. Cambiar ic_launcher de la app



 */