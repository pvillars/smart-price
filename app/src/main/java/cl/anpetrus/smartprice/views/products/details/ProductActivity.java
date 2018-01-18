package cl.anpetrus.smartprice.views.products.details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.views.products.price.NewPriceFragment;

public class ProductActivity extends AppCompatActivity {

    public final static String KEY_PRODUCT = "cl.anpetrus.smartprice.views.products.details.ProductActivity.KEY_PRODUCT";
    public final static String KEY_LOWER_PRICE = "cl.anpetrus.smartprice.views.products.details.ProductActivity.KEY_LOWER_PRICE";
    private static final String FRAGMENT_PRICE_TAG = "NEW_PRICE";

    private String productKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        productKey = getIntent().getStringExtra(KEY_PRODUCT);

        FloatingActionButton addNewPriceFab = (FloatingActionButton) findViewById(R.id.addNewPriceFab);
        addNewPriceFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPriceShow();
            }
        });
    }

    private void addNewPriceShow() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(FRAGMENT_PRICE_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment fragment = NewPriceFragment.newInstance(productKey);
        fragment.show(ft, FRAGMENT_PRICE_TAG);
    }

}
