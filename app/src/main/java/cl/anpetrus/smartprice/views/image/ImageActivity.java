package cl.anpetrus.smartprice.views.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import cl.anpetrus.smartprice.R;

public class ImageActivity extends AppCompatActivity {

    public static final String KEY_URL = "cl.anpetrus.smartprice.views.image.ImageActivity.KEY_URL";
    public static final String KEY_THUMBS_IMAGE = "cl.anpetrus.smartprice.views.image.ImageActivity.KEY_THUMBS_IMAGE";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        imageView = (ImageView) findViewById(R.id.imageBtn);

        Bundle extras = getIntent().getExtras();
        Bitmap bitmap = extras.getParcelable(KEY_THUMBS_IMAGE);

        String url = getIntent().getStringExtra(KEY_URL);

        Picasso.with(this).invalidate(url);
        Picasso.with(this).load(url).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
        Picasso.with(this)
                .load(url)
                .placeholder(new BitmapDrawable(getResources(), bitmap))
                .into(imageView);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
