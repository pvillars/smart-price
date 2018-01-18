package cl.anpetrus.smartprice.views.products.add;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.data.ProductReference;
import cl.anpetrus.smartprice.models.Price;
import cl.anpetrus.smartprice.models.Product;
import cl.anpetrus.smartprice.models.Ubication;
import cl.anpetrus.smartprice.views.loading.LoadingFragment;
import cl.anpetrus.smartprice.views.products.partials.NumberInputField;
import cl.anpetrus.smartprice.views.products.partials.PhotoField;
import cl.anpetrus.smartprice.views.products.partials.TextInputField;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

public class NewProductActivity extends AppCompatActivity implements VerticalStepperForm, FieldCallBack, UploadCallBack {

    public static final String TEXT_UNCOMPLETE = "";
    public static final String LOAD_PRODUCT = "Producto cargado";
    public static final String TAG_LOADING = "loading";
    public static final int MIN_LENGTH_NAME = 2;
    public static final int MIN_PRICE = 1;
    public static final int MAX_PRICE = 999999999;
    public static final int MIN_LENGTH_UBICATION = 2;
    private VerticalStepperFormLayout verticalStepperForm;
    private PhotoField photoField;
    private TextInputField nameField;
    private NumberInputField priceField;
    private TextInputField ubicationField;
    private LoadingFragment loadingFragment;
    private Price price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        String[] steps = getResources().getStringArray(R.array.form_fiels);
        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);
        setTitle(R.string.title_activity_new_product);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, steps, this, this)
                //     .primaryColor(colorPrimary)
                //     .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
                .init();
    }

    @Override
    public View createStepContentView(int stepNumber) {
        switch (stepNumber) {
            case 0:
                photoField = new PhotoField(verticalStepperForm, this);
                photoField.setCallBack(this);
                return photoField.getView();

            case 1:
                nameField = new TextInputField(this);
                nameField.setFieldCallBack(this);
                nameField.setValidation(MIN_LENGTH_NAME);
                return nameField;
            case 2:

                priceField = new NumberInputField(this);
                priceField.setFieldCallBack(this);
                priceField.setValidation(MIN_PRICE, MAX_PRICE);
                return priceField;
            case 3:
                ubicationField = new TextInputField(this);
                ubicationField.setFieldCallBack(this);
                ubicationField.setValidation(MIN_LENGTH_UBICATION);
                return ubicationField;
            default:
                return new EditText(this);
        }
    }

    @Override
    public void onStepOpening(int stepNumber) {
        String validation;
        switch (stepNumber) {
            case 0:
                validation = photoField.getStatus();
                if (validation == null) {
                    completed();
                } else {
                    uncompleted(TEXT_UNCOMPLETE);
                }
                break;
            case 1:
                validation = nameField.getStatus();
                if (validation == null) {
                    completed();
                } else {
                    uncompleted(TEXT_UNCOMPLETE);
                }
                break;
            case 2:
                validation = priceField.getStatus();
                if (validation == null) {
                    completed();
                } else {
                    uncompleted(TEXT_UNCOMPLETE);
                }
                break;
            case 3:
                validation = ubicationField.getStatus();
                if (validation == null) {
                    completed();
                } else {
                    uncompleted(TEXT_UNCOMPLETE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void sendData() {
        loadingShow();
        Product product = new Product();
        price = new Price();
        Ubication ubication = new Ubication();
        ubication.setName(ubicationField.getResult());
        price.setPrice(priceField.getResultNumber());
        price.setUbication(ubication);
        product.setName(nameField.getResult());
        Bitmap photo = photoField.getImageFull();
        new UploadImagesPresenter(this).uploadSave(photo,product);
    }


    @Override
    public void completed() {
        verticalStepperForm.setActiveStepAsCompleted();
    }

    @Override
    public void uncompleted(String error) {
        verticalStepperForm.setActiveStepAsUncompleted(error);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        photoField.getPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        photoField.getPhotoResult(requestCode, resultCode, data);
    }

    @Override
    public void uploadFinished(Product product) {
        new ProductReference().saveProduct(product,price);
        loadingDismiss();
        Toast.makeText(this, LOAD_PRODUCT, Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void loadingShow() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(TAG_LOADING);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        loadingFragment = LoadingFragment.newInstance();
        loadingFragment.show(ft, TAG_LOADING);
    }

    private void loadingDismiss() {
        loadingFragment.dismiss();
    }
}
