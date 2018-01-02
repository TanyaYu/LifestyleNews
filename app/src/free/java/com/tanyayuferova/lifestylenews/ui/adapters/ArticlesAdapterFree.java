package com.tanyayuferova.lifestylenews.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tanyayuferova.lifestylenews.R;
import com.tanyayuferova.lifestylenews.entity.Article;
import com.tanyayuferova.lifestylenews.ui.adapter.ArticlesAdapter;
import com.google.android.gms.ads.NativeExpressAdView;

/**
 * Created by Tanya Yuferova on 12/26/2017.
 */

public class ArticlesAdapterFree extends ArticlesAdapter {

    private static final int ARTICLE_ITEM_VIEW_TYPE = 0;
    private static final int NATIVE_EXPRESS_AD_VIEW_TYPE = 1;

    public ArticlesAdapterFree() {
        super();
    }

    public ArticlesAdapterFree(OnClickArticleHandler clickHandler) {
        super(clickHandler);
    }

    /**
     * The {@link NativeExpressAdViewHolder} class.
     */
    public class NativeExpressAdViewHolder extends RecyclerView.ViewHolder {

        NativeExpressAdViewHolder(View view) {
            super(view);
        }
    }

    /**
     * Determines the view type for the given position.
     */
    @Override
    public int getItemViewType(int position) {
        return data.get(position) instanceof Article ? ARTICLE_ITEM_VIEW_TYPE : NATIVE_EXPRESS_AD_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                View nativeExpressLayoutView = LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.native_express_ad_item,
                        parent, false);
                return new NativeExpressAdViewHolder(nativeExpressLayoutView);
            case ARTICLE_ITEM_VIEW_TYPE:
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case NATIVE_EXPRESS_AD_VIEW_TYPE:
                NativeExpressAdViewHolder nativeExpressHolder = (NativeExpressAdViewHolder) holder;
                NativeExpressAdView adView = (NativeExpressAdView) data.get(position);
                ViewGroup adCardView = (ViewGroup) nativeExpressHolder.itemView;

                if (adCardView.getChildCount() > 0) {
                    adCardView.removeAllViews();
                }
                if (adView.getParent() != null) {
                    ((ViewGroup) adView.getParent()).removeView(adView);
                }

                adCardView.addView(adView);
                break;
            case ARTICLE_ITEM_VIEW_TYPE:
            default:
                super.onBindViewHolder(holder, position);
                break;

        }
    }


}
