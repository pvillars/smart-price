package cl.anpetrus.smartprice.views.products.add;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import cl.anpetrus.smartprice.data.CurrentUser;
import cl.anpetrus.smartprice.data.EmailProcessor;
import cl.anpetrus.smartprice.data.Nodes;
import cl.anpetrus.smartprice.models.Product;

/**
 * Created by Petrus on 30-08-2017.
 */

public class UploadImagesPresenter {

    public static final String BASE_URL = "gs://smartprice-a7111.appspot.com";
    public static final String SEPARATOR = "/";
    public static final String DOT = ".";
    public static final String EXTENSION_JPG = "jpg";
    public static final String PRODUCTS = "products";
    public static final String PRODUCTS_THUMBS = "products_thumbs";
    public static final int FULL_QUALITY = 100;
    public static final int MEDIUM_QUALITY = 50;
    private UploadCallBack callback;
    private StorageReference storageReference;
    private String url;

    public UploadImagesPresenter(Context context) {
        this.callback = (UploadCallBack) context;
    }

    public void uploadSave(final Bitmap photo, final Product product) {

        final CurrentUser currentUser = new CurrentUser();
        final String userUidEmail = EmailProcessor.sanitizedEmail(currentUser.email());
        final String key = new Nodes().products().push().getKey();

        product.setKey(key);
        String folder = userUidEmail + SEPARATOR;
        String photoName = key + DOT + EXTENSION_JPG;
        String baseUrlFull = BASE_URL + SEPARATOR + PRODUCTS + SEPARATOR;
        String baseUrlThumbs = BASE_URL + SEPARATOR + PRODUCTS_THUMBS + SEPARATOR;
        final String refUrl = baseUrlFull + folder + photoName;
        final String refUrlThumbs = baseUrlThumbs + folder + photoName;

        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, FULL_QUALITY, baos);
        byte[] data = baos.toByteArray();

        storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests")
                String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");
                url = fullUrl[0];
                product.setImageFull(url);
                storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrlThumbs);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, MEDIUM_QUALITY, baos);
                byte[] data = baos.toByteArray();

                storageReference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests")
                        String[] fullUrl = taskSnapshot.getDownloadUrl().toString().split("&token");
                        url = fullUrl[0];
                        product.setImageThumbnail(url);
                        photo.recycle();
                        callback.uploadFinished(product);
                    }
                });
            }
        });
    }

}