package cl.anpetrus.smartprice.views.main;

import android.graphics.Bitmap;

/**
 * Created by USUARIO on 06-09-2017.
 */

public interface ProductListener {
    void clicked(String keyProduct, String lowerPrice, Bitmap bitmap);
    void dataChange();
}