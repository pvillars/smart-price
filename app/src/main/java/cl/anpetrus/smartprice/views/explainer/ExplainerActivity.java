package cl.anpetrus.smartprice.views.explainer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.data.ConfigPreference;
import cl.anpetrus.smartprice.views.main.MainActivity;

public class ExplainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explainer);
        final CheckBox notMoreCb = (CheckBox) findViewById(R.id.notMoreCb);
        findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ConfigPreference(ExplainerActivity.this).showExplainSave(notMoreCb.isChecked());
                startActivity(new Intent(ExplainerActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
