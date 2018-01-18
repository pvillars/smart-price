package cl.anpetrus.smartprice.data;

/**
 * Created by Petrus on 05-12-2017.
 */

public class PriceProcessor {
    public static String getPriceString(Integer model) {
        String price = String.valueOf(model);
        if (price == null)
            price = "";

        int x = price.length();
        if (x > 6) {
            price = price.substring(0, x - 6) + '.' + price.substring(x - 6, x - 3) + '.' + price.substring(x - 3, x);
        } else if (x > 3) {
            price = price.substring(0, x - 3) + '.' + price.substring(x - 3, x);
        }
        return '$' + price;
    }
}
