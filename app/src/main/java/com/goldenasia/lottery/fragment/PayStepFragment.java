package com.goldenasia.lottery.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;

import butterknife.BindView;

/**
 * Created By Sakura
 */
public class PayStepFragment extends BaseFragment {

    private String url;

    @BindView(R.id.ga_web)
    WebView webView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)

    {
        // Inflate the layout for this fragment
        /*View view = inflater.inflate(R.layout.fragment_pay_step, container, false);
        ButterKnife.bind(this, view);
        return view;*/
        return inflateView(inflater, container, "支付步骤", R.layout.fragment_pay_step);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        Bundle bundle;
        bundle = getActivity().getIntent().getExtras();
        if (bundle == null)
            return;
        url = bundle.get("url").toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        webView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingLayout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        webView.destroy();
        super.onDestroyView();
    }
}
