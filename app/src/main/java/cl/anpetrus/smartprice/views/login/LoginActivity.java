package cl.anpetrus.smartprice.views.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;

import java.util.Arrays;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.data.ConfigPreference;
import cl.anpetrus.smartprice.data.UserReference;
import cl.anpetrus.smartprice.views.explainer.ExplainerActivity;
import cl.anpetrus.smartprice.views.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginCallBack {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        View view = findViewById(R.id.root);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        new LoginValidator(this).init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RC_SIGN_IN == requestCode) {
            if (ResultCodes.OK == resultCode) {
                new UserReference().saveCurrentUser();
                success();
            } else {
                finish();
            }
        }
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
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(
                                Arrays.asList(
                                       // new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()/*,
                                        new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()/*,
                                        new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()*/))
                        .setTheme(R.style.LoginTheme)
                        //setLogo(R.mipmap.logo)
                        .build(),
                RC_SIGN_IN);
    }
}
