package cl.anpetrus.smartprice.views.products.price;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.adapters.PriceAdapter;
import cl.anpetrus.smartprice.models.Product;
import cl.anpetrus.smartprice.views.products.details.ProductActivity;
import cl.anpetrus.smartprice.views.products.details.ProductCallBack;
import cl.anpetrus.smartprice.views.products.details.ProductPresenter;

import static cl.anpetrus.smartprice.data.ProductProcessor.filterByUbicationName;
import static cl.anpetrus.smartprice.data.ProductProcessor.sortByPrice;

public class ProductPricesFragment extends Fragment implements ProductCallBack {

    private String productKey;
    private PriceAdapter adapter;

    public ProductPricesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productKey = getActivity().getIntent().getStringExtra(ProductActivity.KEY_PRODUCT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_prices, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PriceAdapter();
        recyclerView.setAdapter(adapter);
        new ProductPresenter(this).loadProduct(productKey);
    }

    @Override
    public void load(Product product) {
        adapter.update(filterByUbicationName(sortByPrice(new ArrayList<>(product.getPrices().values()))));
    }
}
