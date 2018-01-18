package cl.anpetrus.smartprice.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import cl.anpetrus.smartprice.models.Price;
import cl.anpetrus.smartprice.models.Product;
import cl.anpetrus.smartprice.models.ProductShort;

import static cl.anpetrus.smartprice.data.ProductProcessor.getFormatKeyPrice;


/**
 * Created by Petrus on 31-08-2017.
 */

public class ProductReference {

    public static final String CHILD_CREATE = "create";
    public static final String CHILD_UPDATE = "update";
    private DatabaseReference reference;

    public ProductReference() {
        reference = new Nodes().products();
    }

    public void saveProduct(Product product, Price price) {
        saveOrUpdate(product, price, product.getKey());
    }

    public void saveNewPrice(final Price price, final String productKey) {
        final String priceKey = getFormatKeyPrice(price, reference);
        price.setKey(priceKey);
        price.setCreate(new Date().getTime());

        new Nodes().product(productKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                Map<String, Price> priceMap = product.getPrices() != null ? product.getPrices() : new HashMap<String, Price>();
                priceMap.put(priceKey, price);
                product.setPrices(priceMap);

                Map<String, String> timeMap = ServerValue.TIMESTAMP;
                new Nodes().product(productKey).setValue(product);
                new Nodes().productSetDateUpdate(productKey, timeMap);

                Integer priceAux = price.getPrice();
                String priceLowerKey = priceKey;

                for (Map.Entry<String, Price> entry : priceMap.entrySet()) {
                    Integer price = entry.getValue().getPrice();
                    if (price < priceAux) {
                        priceAux = price;
                        priceLowerKey = entry.getKey();
                    }
                }
                Price lowerPrice = priceMap.get(priceLowerKey);
                new Nodes().productListSetDateUpdate(productKey, timeMap);
                new Nodes().productListLowerPrice(productKey).setValue(lowerPrice);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveOrUpdate(final Product product, Price price, final String productKey) {

        product.setKey(productKey);
        product.setInformer(EmailProcessor.sanitizedEmail(new CurrentUser().email()));

        String priceKey = getFormatKeyPrice(price, reference);
        price.setKey(priceKey);

        Map<String, Price> prices = new TreeMap<>();
        prices.put(priceKey, price);
        product.setPrices(prices);

        createProductNodes(product, price, productKey, priceKey);

    }

    private void createProductNodes(Product product, Price price, String productkey, String priceKey) {
        Map<String, String> timeMap = ServerValue.TIMESTAMP;
        new Nodes().product(productkey).setValue(product);
        new Nodes().productSetDateCreate(productkey, timeMap);
        new Nodes().productSetDateUpdate(productkey, timeMap);
        new Nodes().productPriceListSetDateCreate(productkey, priceKey, timeMap);
        new Nodes().productList(productkey).setValue(new ProductShort(product, price));
        new Nodes().productListSetDateCreate(productkey, timeMap);
        new Nodes().productListSetDateUpdate(productkey, timeMap);
        new Nodes().productListLowerPriceSetDateCreate(productkey, timeMap);
    }

}