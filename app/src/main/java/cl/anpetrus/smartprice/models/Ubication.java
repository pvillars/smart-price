package cl.anpetrus.smartprice.models;

/**
 * Created by Petrus on 17-11-2017.
 */

public class Ubication {
    private String name;
    private Long latitud;
    private Long longitud;

    public Ubication() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getLatitud() {
        return latitud;
    }

    public void setLatitud(Long latitud) {
        this.latitud = latitud;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }
}
