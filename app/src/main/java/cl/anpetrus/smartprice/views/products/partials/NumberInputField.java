package cl.anpetrus.smartprice.views.products.partials;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;

/**
 * Created by Petrus on 15-11-2017.
 */

public class NumberInputField extends TextInputField {

    public static final String MESSAGE_UNCOMPLETE = "Introduzca un precio en entre %s y %s";
    public static final String MESSAGE_STATUS = "Introduzca mas de %s caracteres";
    private int max = 1;

    public NumberInputField(Context context) {
        super(context);
        setInputType(EditorInfo.TYPE_CLASS_NUMBER);
    }

    public void setValidation(final int validator, final int max) {
        super.setValidation(validator);
        this.max = max;
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int num;
                try {
                    num = Integer.valueOf(String.valueOf(editable));
                } catch (Exception e) {
                    num = -1;
                }
                validate(num);

            }
        });
    }

    private void validate(int num) {
        if (num >= validator && num <= max) {
            callBack.completed();
        } else {
            callBack.uncompleted(String.format(MESSAGE_UNCOMPLETE, validator, max));
        }
    }

    @Override
    public String getStatus() {
        if (getText().toString().trim().length() >= validator) {
            return getText().toString();
        }
        return String.format(MESSAGE_STATUS, validator);
    }

    public Integer getResultNumber() {
        return Integer.parseInt(getResult());
    }

}
