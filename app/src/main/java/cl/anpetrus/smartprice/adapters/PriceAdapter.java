package cl.anpetrus.smartprice.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.data.StringProcessor;
import cl.anpetrus.smartprice.models.Price;

import static cl.anpetrus.smartprice.data.PriceProcessor.getPriceString;


/**
 * Created by Petrus on 12-08-2017.
 */

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {

    private static final Integer MAX_LENGTH_UBICATION = 45;
    private List<Price> prices = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_price, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final Price price = prices.get(position);
        viewHolder.price.setText(getPriceString(price.getPrice()));
        viewHolder.ubication.setText(StringProcessor.getResumeStr(price.getUbication().getName(), MAX_LENGTH_UBICATION));
    }

    public void update(List<Price> prices) {
        this.prices.clear();
        this.prices.addAll(prices);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView price, ubication;

        public ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.priceListTv);
            ubication = itemView.findViewById(R.id.ubicacionListTv);
        }
    }
}