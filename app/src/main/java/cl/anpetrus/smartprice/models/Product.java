package cl.anpetrus.smartprice.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Petrus on 16-11-2017.
 */

public class Product {

    private String key;
    private String informer;
    private String name;
    private Long create;
    private Long update;
    private String image;
    private String imageThumbnail;
    private Map<String, Price> prices;

    public Product() {
    }

    public Long getUpdate() {
        return update;
    }

    public void setUpdate(Long update) {
        this.update = update;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInformer() {
        return informer;
    }

    public void setInformer(String informer) {
        this.informer = informer;
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

    public String getImage() {
        return image;
    }

    public void setImageFull(String image) {
        this.image = image;
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

    public Map<String, Price> getPrices() {
        return prices;
    }

    public Integer getLowerPrice() {
        List<Price> list = new ArrayList<>(getPrices().values());
        Integer lowerPrice = Integer.MAX_VALUE;
        for (Price price : list) {
            if (price.getPrice() < lowerPrice)
                lowerPrice = price.getPrice();
        }
        return lowerPrice;
    }

    public void setPrices(Map<String, Price> prices) {
        this.prices = prices;
    }
}
