package cl.anpetrus.smartprice.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Map;

/**
 * Created by Petrus on 30-08-2017.
 */

public class Nodes {

    public static final String CHILD_BETA = "beta";
    public static final String CHILD_USERS = "users";
    public static final String CHILD_PRODUCTS = "products";
    public static final String CHILD_PRODUCTS_LIST = "products_list";
    public static final String CHILD_LOWER_PRICE = "lowerPrice";
    public static final String CHILD_UPDATE = "update";
    public static final String CHILD_CREATE = "create";

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference();


    public DatabaseReference beta() {
        return root.child(CHILD_BETA);
    }

    public DatabaseReference users() {
        return root.child(CHILD_USERS);
    }

    public DatabaseReference products() {
        return root.child(CHILD_PRODUCTS);
    }

    public DatabaseReference productsList() {
        return root.child(CHILD_PRODUCTS_LIST);
    }

    public DatabaseReference user(String key) {
        return users().child(key);
    }

    public DatabaseReference product(String key) {
        return products().child(key);
    }

    public DatabaseReference productList(String key) {
        return productsList().child(key);
    }

    public void productListSetDateUpdate(String key, Map<String,String> serverTime) {
        productList(key).child(CHILD_UPDATE).setValue(serverTime);
    }

    public void productListSetDateCreate(String key, Map<String,String> serverTime) {
        productList(key).child(CHILD_CREATE).setValue(serverTime);
    }

    public void productSetDateUpdate(String key, Map<String,String> serverTime) {
        product(key).child(CHILD_UPDATE).setValue(serverTime);
    }

    public void productSetDateCreate(String key, Map<String,String> serverTime) {
        product(key).child(CHILD_CREATE).setValue(serverTime);
    }

    public DatabaseReference productListLowerPrice(String key) {
        return productList(key).child(CHILD_LOWER_PRICE);
    }

    public void productListLowerPriceSetDateCreate(String key, Map<String,String> serverTime) {
        productList(key).child(CHILD_LOWER_PRICE).child(CHILD_CREATE).setValue(serverTime);
    }

    public void productPriceListSetDateCreate(String keyProduct,String keyPrice, Map<String,String> serverTime) {
        product(keyProduct).child("prices").child(keyPrice).child(CHILD_CREATE).setValue(serverTime);
    }

    public Query productAuxPrices(String key) {
        return root.child(key).orderByChild("price").startAt(0);
    }
}