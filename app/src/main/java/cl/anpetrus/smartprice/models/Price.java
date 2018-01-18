package cl.anpetrus.smartprice.models;

/**
 * Created by Petrus on 17-11-2017.
 */

public class Price {

    private String key;
    private Integer price;
    private Ubication ubication;
    private String informerKey;
    private Long create;

    public Price() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Ubication getUbication() {
        return ubication;
    }

    public void setUbication(Ubication ubication) {
        this.ubication = ubication;
    }

    public String getInformerKey() {
        return informerKey;
    }

    public void setInformerKey(String informerKey) {
        this.informerKey = informerKey;
    }

    public Long getCreate() {
        return create;
    }

    public void setCreate(Long create) {
        this.create = create;
    }
}
