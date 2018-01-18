package cl.anpetrus.smartprice.views.products.price;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.data.ProductReference;
import cl.anpetrus.smartprice.models.Price;
import cl.anpetrus.smartprice.models.Ubication;
import cl.anpetrus.smartprice.views.products.add.FieldCallBack;
import cl.anpetrus.smartprice.views.products.partials.NumberInputField;
import cl.anpetrus.smartprice.views.products.partials.TextInputField;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPriceFragment extends DialogFragment implements VerticalStepperForm, FieldCallBack {

    public final static String KEY_PRICE = "cl.anpetrus.smartprice.views.products.price.NewPriceFragment.KEY_PRICE";
    public static final String PRECIO_AGREGADO = "Precio agregado";
    public static final String TAG_ERROR = "ERROR";
    public static final String TEXT_UNCOMPLETED = "";
    private VerticalStepperFormLayout verticalStepperForm;
    private NumberInputField priceField;
    private TextInputField ubicationField;
    private Price price;
    private String productKey;

    public NewPriceFragment() {
        // Required empty public constructor
    }

    public static NewPriceFragment newInstance(String key) {
        NewPriceFragment fragment = new NewPriceFragment();
        Bundle args = new Bundle();
        args.putString(KEY_PRICE, key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productKey = getArguments().getString(KEY_PRICE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_price, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        String[] steps = getResources().getStringArray(R.array.form_fiels_price);
        verticalStepperForm = view.findViewById(R.id.vertical_stepper_form_price);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, steps, this, getActivity())
                //     .primaryColor(colorPrimary)
                //     .primaryDarkColor(colorPrimaryDark)
                .showVerticalLineWhenStepsAreCollapsed(true)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
                .init();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View createStepContentView(int stepNumber) {
        switch (stepNumber) {
            case 0:
                priceField = new NumberInputField(getContext());
                priceField.setFieldCallBack(this);
                priceField.setValidation(0, 999999999);
                return priceField;
            case 1:
                ubicationField = new TextInputField(getContext());
                ubicationField.setFieldCallBack(this);
                ubicationField.setValidation(2);
                return ubicationField;
            default:
                return null;
        }
    }

    @Override
    public void onStepOpening(int stepNumber) {
        String validation;
        switch (stepNumber) {
            case 0:
                validation = priceField.getStatus();
                if (validation == null) {
                    completed();
                } else {
                    uncompleted(TEXT_UNCOMPLETED);
                }
                break;
            case 1:
                validation = ubicationField.getStatus();
                if (validation == null) {
                    completed();
                } else {
                    uncompleted(TEXT_UNCOMPLETED);
                }
                break;
            case 2:
                hideKeyBoard(ubicationField);
                break;
            default:
                break;
        }
    }

    @Override
    public void sendData() {

        price = new Price();
        Ubication ubication = new Ubication();
        ubication.setName(ubicationField.getResult());

        price.setUbication(ubication);
        price.setPrice(priceField.getResultNumber());

        new ProductReference().saveNewPrice(price,getProductKey());
        Toast.makeText(getContext(), PRECIO_AGREGADO, Toast.LENGTH_SHORT).show();

        try {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(priceField.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e(TAG_ERROR, e.getMessage());
        }
        dismiss();
    }

    @Override
    public void completed() {
        verticalStepperForm.setActiveStepAsCompleted();
    }

    @Override
    public void uncompleted(String error) {
        verticalStepperForm.setActiveStepAsUncompleted(error);
    }

    private String getProductKey() {
        return this.productKey;
    }

    private void hideKeyBoard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            Log.e(TAG_ERROR, e.getMessage());
        }
    }
}
