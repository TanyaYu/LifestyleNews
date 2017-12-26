package com.example.tanyayuferova.lifestylenews.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tanyayuferova.lifestylenews.R;
import com.example.tanyayuferova.lifestylenews.entity.Article;
import com.example.tanyayuferova.lifestylenews.ui.adapters.ArticlesAdapterFree;
import com.example.tanyayuferova.lifestylenews.ui.fragment.ArticlesListFragment;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanya Yuferova on 12/26/2017.
 */

public class ArticlesListFragmentFree extends ArticlesListFragment {

    public static int ITEMS_PER_AD = 0;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1072772517";

    public ArticlesListFragmentFree() {
        super();
    }

    public static ArticlesListFragmentFree newInstance(Uri uriData) {
        return newInstance(uriData, 0);
    }

    public static ArticlesListFragmentFree newInstance(Uri uriData, int count) {
        ArticlesListFragmentFree fragment = new ArticlesListFragmentFree();
        fragment.setArguments(new Bundle());
        fragment.getArguments().putParcelable(ARGUMENT_URI_DATA, uriData);
        fragment.getArguments().putInt(ARGUMENT_ARTICLES_COUNT, count);
        fragment.getArguments().putInt(ARGUMENT_LOADER_ID, ++countLoaders);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ITEMS_PER_AD = getContext().getResources().getInteger(R.integer.items_pre_ad);
        View result = super.onCreateView(inflater, container, savedInstanceState);
        binding.recyclerView.setAdapter(adapter = new ArticlesAdapterFree(this));
        return result;
    }


    /**
     * Adds Native Express ads to the items list.
     */
    private void addNativeExpressAds() {
        for (int i = 0; i <= adapter.getData().size(); i += ITEMS_PER_AD) {
            final NativeExpressAdView adView = new NativeExpressAdView(getContext());
            adapter.getData().add(i, adView);
        }
    }

    /**
     * Sets up and loads the Native Express ads.
     */
    private void setUpAndLoadNativeExpressAds() {
        binding.recyclerView.post(new Runnable() {
            @Override
            public void run() {
                final float scale =getContext().getResources().getDisplayMetrics().density;
                for (int i = 0; i <= adapter.getData().size(); i += ITEMS_PER_AD) {
                    final NativeExpressAdView adView =
                            (NativeExpressAdView) adapter.getData().get(i);
                    final CardView cardView = getActivity().findViewById(R.id.ad_card_view);
                    final int adWidth = cardView.getWidth() - cardView.getPaddingLeft()
                            - cardView.getPaddingRight();
                    AdSize adSize = new AdSize((int) (adWidth / scale), (int) getResources().getDimension(R.dimen.article_item_ad_height));
                    adView.setAdSize(adSize);
                    adView.setAdUnitId(AD_UNIT_ID);
                }

                loadNativeExpressAd(0);
            }
        });
    }

    /**
     * Loads the Native Express ads in the items list.
     */
    private void loadNativeExpressAd(final int index) {
        if (index >= adapter.getData().size()) {
            return;
        }

        Object item = adapter.getData().get(index);
        if (!(item instanceof NativeExpressAdView)) {
            throw new ClassCastException("Expected item at index " + index + " to be a Native"
                    + " Express ad.");
        }

        final NativeExpressAdView adView = (NativeExpressAdView) item;

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                loadNativeExpressAd(index + ITEMS_PER_AD);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                loadNativeExpressAd(index + ITEMS_PER_AD);
            }
        });

        adView.loadAd(new AdRequest.Builder().build());
    }

    @Override
    protected void setAdapterData(List<Article> data) {
        super.setAdapterData(data);
        addNativeExpressAds();
        setUpAndLoadNativeExpressAds();
    }
}
