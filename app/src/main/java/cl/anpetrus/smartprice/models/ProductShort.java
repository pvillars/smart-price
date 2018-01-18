package cl.anpetrus.smartprice.models;

/**
 * Created by Petrus on 16-11-2017.
 */

public class ProductShort {

    private String key;
    private String name;
    private Long create;
    private Long update;
    private String imageThumbnail;
    private Price lowerPrice;

    public ProductShort() {
    }

    public ProductShort(Product product, Price lowerPrice) {
        this.key = product.getKey();
        this.name = product.getName();
        this.create = product.getCreate();
        this.update = product.getUpdate();
        this.imageThumbnail = product.getImageThumbnail();
        this.lowerPrice = lowerPrice;
    }

    public Long getUpdate() {
        return update;
    }

    public void setUpdate(Long update) {
        this.update = update;
    }

    public Long getCreate() {
        return create;
    }

    public void setCreate(Long create) {
        this.create = create;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Price getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(Price lowerPrice) {
        this.lowerPrice = lowerPrice;
    }
}
