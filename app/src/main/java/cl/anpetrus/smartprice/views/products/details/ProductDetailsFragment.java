package cl.anpetrus.smartprice.views.products.details;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.models.Product;
import cl.anpetrus.smartprice.views.image.ImageActivity;
import cl.anpetrus.smartprice.views.products.add.UploadCallBack;

import static cl.anpetrus.smartprice.data.ImageProcessor.getResizedBitmap;
import static cl.anpetrus.smartprice.data.PriceProcessor.getPriceString;

public class ProductDetailsFragment extends Fragment implements UploadCallBack, ProductCallBack {

    public static final String KEY_THUMBS_IMAGE = "cl.anpetrus.smartprice.views.main.views.ProductDetailsFragment.KEY_THUMBS_IMAGE";
    public static final String MESSAGE_UPLOAD_FINISHED = "Nuevo precio cargado";
    public static final String PATTERN_DATE = "dd-MM-yyyy";
    public static final String PATTERN_HOUR = "HH:mm";
    public static final String END_HOUR = " Hrs.";
    public static final int WIDTH_50 = 50;
    private String productKey;
    private String imageUrlThums;
    private ImageView image;
    private String imageUrl;
    private TextView price, name, dateUpload, hourUpload;
    private Bitmap bitmapThumbs;
    private FloatingActionButton fabZoomFake;
    private boolean firstLoad;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fabZoomFake = getActivity().findViewById(R.id.fabZoomFake);
        fabZoomFake.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorBlack)));
        price = getActivity().findViewById(R.id.priceTv);
        image = getActivity().findViewById(R.id.productImageIv);
        image.setImageBitmap(bitmapThumbs);
        firstLoad = true;
        new ProductPresenter(this).loadProduct(productKey);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productKey = getActivity().getIntent().getStringExtra(ProductActivity.KEY_PRODUCT);
        Bundle extras = getActivity().getIntent().getExtras();
        bitmapThumbs = extras.getParcelable(KEY_THUMBS_IMAGE);
        //lowerPrice = getActivity().getIntent().getStringExtra(KEY_LOWER_PRICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.nameProduct);
        dateUpload = view.findViewById(R.id.lastUpdateDate);
        hourUpload = view.findViewById(R.id.lastUpdateHour);
    }

    @Override
    public void uploadFinished(Product product) {
        Toast.makeText(getContext(), MESSAGE_UPLOAD_FINISHED, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void load(Product product) {
        if(firstLoad) {
            imageUrlThums = product.getImageThumbnail();
            Picasso.with(getContext()).invalidate(imageUrlThums);
            Picasso.with(getContext()).load(imageUrlThums).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE);
            Picasso.with(getContext())
                    .load(imageUrlThums)
                    .placeholder(new BitmapDrawable(getResources(), bitmapThumbs))
                    .into(image);
            imageUrl = product.getImageThumbnail();
            name.setText(product.getName());
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewCompleteImage();
                }
            });
            firstLoad = false;
        }
        price.setText(getPriceString(product.getLowerPrice()));
        Date date = new Date(product.getUpdate());
        String dateString = new SimpleDateFormat(PATTERN_DATE).format(date);
        String timeString = new SimpleDateFormat(PATTERN_HOUR).format(date) + END_HOUR;
        dateUpload.setText(dateString);
        hourUpload.setText(timeString);
    }

    private void viewCompleteImage() {
        Intent intent = new Intent(getActivity(), ImageActivity.class);
        intent.putExtra(ImageActivity.KEY_URL, imageUrl);
        image.buildDrawingCache();
        Bitmap imageBitmap = image.getDrawingCache();
        Bundle extras = new Bundle();
        extras.putParcelable(ImageActivity.KEY_THUMBS_IMAGE, getResizedBitmap(imageBitmap, WIDTH_50));
        intent.putExtras(extras);
        startActivity(intent);
    }


}
