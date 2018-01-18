package cl.anpetrus.smartprice.views.products.partials;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.views.products.add.FieldCallBack;


/**
 * Created by Petrus on 14-11-2017.
 */

public class TextInputField extends android.support.v7.widget.AppCompatEditText {

    public static final String MESSAGE_UNCOMPLETE = "Introduzca mas de %s caracteres";
    public static final String MESSAGE_STATUS = "Introduzca mas de %s caracteres";
    protected FieldCallBack callBack;
    protected int validator;

    public void setFieldCallBack(FieldCallBack callBack) {
        this.callBack = callBack;
    }

    public TextInputField(Context context) {
        super(context);
        setAppearance(context);
        setInputType(EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
    }

    private void setAppearance(Context context) {
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        int top = (int) context.getResources().getDimension(R.dimen.input_field_padding_top);
        int sides = (int) context.getResources().getDimension(R.dimen.input_field_padding_sides);
        setPadding(sides, top, sides, top);
    }

    public void setValidation(final int validator) {
        this.validator = validator;
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString();
                if (text.trim().length() >= validator) {
                    callBack.completed();
                } else {
                    callBack.uncompleted(String.format(MESSAGE_UNCOMPLETE, validator));
                }
            }
        });
    }

    public String getStatus() {
        if (getText().toString().trim().length() >= validator) {
            return null;
        }
        return String.format(MESSAGE_STATUS, validator);
    }

    public String getResult() {
        return getText().toString();
    }
}
