package cl.anpetrus.smartprice.views.login;

import cl.anpetrus.smartprice.data.CurrentUser;

/**
 * Created by Petrus on 26-08-2017.
 */

public class LoginValidator {

    private LoginCallBack loginCallBack;

    public LoginValidator(LoginCallBack loginCallBack) {
        this.loginCallBack = loginCallBack;
    }

    public void init() {
        if (new CurrentUser().getCurrentUser() != null) {
            loginCallBack.success();
        } else {
            loginCallBack.singIn();
        }
    }
}