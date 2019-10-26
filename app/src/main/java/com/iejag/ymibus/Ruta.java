package com.iejag.ymibus;

public class Ruta {
    private String uid;
    private String latitud;
    private String longitud;

    public Ruta() {
    }

    public Ruta(String uid, String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.uid = uid;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
