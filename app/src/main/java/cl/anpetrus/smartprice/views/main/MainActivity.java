package cl.anpetrus.smartprice.views.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.views.explainer.ExplainerActivity;
import cl.anpetrus.smartprice.views.login.LoginActivity;
import cl.anpetrus.smartprice.views.products.add.NewProductActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton addNewProductFab = (FloatingActionButton) findViewById(R.id.addNewProductFab);
        addNewProductFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewProduct();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOutTb:
                singOut();
                return true;
            case R.id.helpTb:
                showHelp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewProduct() {
        startActivity(new Intent(MainActivity.this, NewProductActivity.class));
    }

    private void singOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void showHelp() {
        startActivity(new Intent(this, ExplainerActivity.class));
    }

}
