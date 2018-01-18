package cl.anpetrus.smartprice.views.products.details;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.anpetrus.smartprice.data.Nodes;
import cl.anpetrus.smartprice.models.Product;

/**
 * Created by Petrus on 06-01-2018.
 */

public class ProductPresenter {

    ProductCallBack callBack;

    public ProductPresenter(ProductCallBack callBack) {
        this.callBack = callBack;
    }

    public void loadProduct(final String productKey){

        new Nodes().product(productKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                callBack.load(product);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
