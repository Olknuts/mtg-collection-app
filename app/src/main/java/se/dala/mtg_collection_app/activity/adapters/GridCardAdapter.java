package se.dala.mtg_collection_app.activity.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.List;

import se.dala.mtg_collection_app.R;
import se.dala.mtg_collection_app.activity.CollectionActivity;

public class GridCardAdapter extends BaseAdapter {

    private final LayoutInflater layoutInflater;
    Context context;
    Activity currentActivity;
    List<String> cardUrls;

    public GridCardAdapter(CollectionActivity context, List<String> cardUrls) {
        this.context = context;
        this.currentActivity = context;
        this.layoutInflater = (LayoutInflater) currentActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cardUrls = cardUrls;
    }

    @Override

    public int getCount() {
        return cardUrls.size();
    }

    @Override
    public String getItem(int position) {
        return cardUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.custom_image_webview, viewGroup,false);
            WebView webView = view.findViewById(R.id.webView);
            //Todo make stuff.
        }

        String url = cardUrls.get(position);
        //updateImageView();
        WebView webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView1, String url) {
                return false;
            }
        });
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadUrl(url);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        return view;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }
}
