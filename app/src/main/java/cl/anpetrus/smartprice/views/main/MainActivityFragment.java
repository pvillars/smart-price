package cl.anpetrus.smartprice.views.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.Query;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.adapters.ProductAdapter;
import cl.anpetrus.smartprice.data.Nodes;
import cl.anpetrus.smartprice.views.products.details.ProductActivity;
import cl.anpetrus.smartprice.views.products.details.ProductDetailsFragment;

import static cl.anpetrus.smartprice.data.ImageProcessor.getResizedBitmap;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ProductListener {

    public static final String ORDER_CHILD = "update";
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private GridLayoutManager mGridLayoutManager;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.reloadSrl);
        swipeRefreshLayout.setRefreshing(true);
        recyclerView = view.findViewById(R.id.productsRv);
        recyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }


        });
    }

    private void getData() {
        Query dbR = new Nodes()
                .productsList()
                .orderByChild(ORDER_CHILD);
        adapter = new ProductAdapter(this, getContext(), dbR);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void clicked(String keyProduct, String lowerPrice, Bitmap bitmap) {
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra(ProductActivity.KEY_PRODUCT , keyProduct);
        intent.putExtra(ProductActivity.KEY_LOWER_PRICE , lowerPrice);

        Bundle extras = new Bundle();
        extras.putParcelable(ProductDetailsFragment.KEY_THUMBS_IMAGE, getResizedBitmap(bitmap, 50));
        intent.putExtras(extras);
        startActivity(intent);
    }

    @Override
    public void dataChange() {
        recyclerView.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

}
