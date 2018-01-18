package cl.anpetrus.smartprice.data;

import android.support.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cl.anpetrus.smartprice.models.Price;

/**
 * Created by Petrus on 02-12-2017.
 */

public class ProductProcessor {

    public static String getFormatKeyPrice(Price price, DatabaseReference reference) {
        String key = String.valueOf(price.getPrice());
        while (key.length() < 9) {
            key = "0" + key;
        }
        return key + "_" + reference.push().getKey();
    }

    public static Price getLowerPrice(List<Price> list) {
        Price priceOut = new Price();
        Integer lowerPrice = Integer.MAX_VALUE;
        for (Price price : list) {
            if (price.getPrice() < lowerPrice){
                lowerPrice = price.getPrice();
                priceOut = price;
            }
        }
        return priceOut;
    }

    public static Price getOlderPrice(List<Price> list) {
        Price priceOut = new Price();
        Long olderPrice = 0L;
        for (Price price : list) {
            if (price.getCreate() > olderPrice){
                olderPrice = price.getCreate();
                priceOut = price;
            }
        }
        return priceOut;
    }

    @NonNull
    public static List<Price> sortByPrice(List<Price> prices) {
        Collections.sort(prices, new Comparator<Price>() {
            @Override
            public int compare(Price price1, Price price2) {
                return price1.getPrice() - price2.getPrice();
            }
        });
        return prices;
    }

    @NonNull
    public static List<Price> filterByUbicationName(List<Price> precios) {
        List<Price> listOut = new ArrayList<>();
        for (int x = 0; x < precios.size(); x++) {
            List<Price> listAux = new ArrayList<>();
            listAux.add(precios.get(x));
            for (int y = x+1; y < precios.size(); y++) {
                if (precios.get(x).getUbication().getName().equals(precios.get(y).getUbication().getName())) {
                    listAux.add(precios.get(y));
                    precios.remove(y);
                    y--;
                }
            }
            listOut.add(getOlderPrice(listAux));
        }
        return listOut;
    }

}
