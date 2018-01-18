package cl.anpetrus.smartprice.views.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.data.ConfigPreference;
import cl.anpetrus.smartprice.views.explainer.ExplainerActivity;
import cl.anpetrus.smartprice.views.login.LoginActivity;
import cl.anpetrus.smartprice.views.login.LoginCallBack;
import cl.anpetrus.smartprice.views.login.LoginValidator;
import cl.anpetrus.smartprice.views.main.MainActivity;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity implements LoginCallBack, BetaCallBack {

    public static final int DELAY_TIME = 1000;
    private TextView betaTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);
        View view = findViewById(R.id.root);

        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                //| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        betaTv = (TextView) findViewById(R.id.betaTv);
        new BetaValidator(this).init();
    }

    @Override
    public void success() {
        if (new ConfigPreference(this).getShowExplain()) {
            startActivity(new Intent(this, ExplainerActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    public void singIn() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void versionBetaOK() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new LoginValidator(SplashActivity.this).init();
            }
        }, DELAY_TIME);
    }

    @Override
    public void versionBetaNOK() {
        betaTv.setVisibility(View.VISIBLE);
    }
}
