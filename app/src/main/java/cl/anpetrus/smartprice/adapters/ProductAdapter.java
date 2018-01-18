package cl.anpetrus.smartprice.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.data.StringProcessor;
import cl.anpetrus.smartprice.models.ProductShort;
import cl.anpetrus.smartprice.views.main.ProductListener;

import static cl.anpetrus.smartprice.data.PriceProcessor.getPriceString;

/**
 * Created by USUARIO on 06-09-2017.
 */

public class ProductAdapter extends FirebaseRecyclerAdapter<ProductShort, ProductAdapter.ProductHolder> {

    public static final int MAX_LENGTH_NAME = 21;
    private ProductListener listener;
    private Context context;

    public ProductAdapter(ProductListener listener, Context context, Query reference) {
        super(ProductShort.class, R.layout.list_item_product, ProductHolder.class, reference);
        this.listener = listener;
        this.context = context;
    }


    @Override
    protected void populateViewHolder(final ProductHolder viewHolder, final ProductShort model, int position) {

        final String lowerPrice = getPriceString(model.getLowerPrice().getPrice());
        viewHolder.name.setText(StringProcessor.getResumeStr(model.getName(), MAX_LENGTH_NAME));
        viewHolder.price.setText(lowerPrice);
        Picasso.with(context)
                .load(model.getImageThumbnail())
                .placeholder(R.mipmap.logo)
                .into(viewHolder.image);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.image.buildDrawingCache();
                Bitmap imageBitmap = viewHolder.image.getDrawingCache();
                listener.clicked(model.getKey(), lowerPrice, imageBitmap);
            }
        });
    }

    @Override
    protected void onDataChanged() {
        super.onDataChanged();
        listener.dataChange();
    }

    /*Para obtener orden inverso*/
    @Override
    public ProductShort getItem(int position) {
        return super.getItem(getItemCount() - 1 - position);
    }

    static public class ProductHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;

        public ProductHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageListIv);
            name = itemView.findViewById(R.id.nameListTv);
            price = itemView.findViewById(R.id.priceListTv);
        }
    }

}