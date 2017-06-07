package com.sciento.wumu.zhengjing.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sciento.wumu.zhengjing.R;


public class WebFragment extends Fragment {


    WebView webview;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean enable = false;

    SwipeRefreshLayout.OnRefreshListener  listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (false == enable) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webview.loadUrl("https://shop187903877.taobao.com");
                        //swipeRefreshLayout.setRefreshing(false);
                        enable = true;
                    }
                }, 120);

            }
            else
            {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webview.reload();
                        //swipeRefreshLayout.setRefreshing(false);
                    }

                }, 120);

            }



        }
    };


    // TODO: Rename and change types and number of parameters
    public static WebFragment newInstance() {
        WebFragment fragment = new WebFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        webview = (WebView)view.findViewById(R.id.shopwebview);
        WebSettings websettings = webview.getSettings();
        websettings.setJavaScriptEnabled(true);
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);
        websettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
        websettings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
        websettings.setAppCacheEnabled(true);//是否使用缓存
        websettings.setSupportZoom(true);//是否可以缩放，默认true
        websettings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
        websettings.setDomStorageEnabled(true);
        //webview.loadUrl("https://shop187903877.taobao.com");
        webview.setWebViewClient(new WebViewClient(){


                                     @Override
                                     public void onPageFinished(WebView view, String url) {
                                         swipeRefreshLayout.setRefreshing(false);
                                         //super.onPageFinished(view, url);
                                     }
                                 }
        );
        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
                        //表示按返回键时的操作
                        webview.goBack();   //后退

                        //webview.goForward();//前进
                        return true;    //已处理
                    }
                }
                return false;
            }
        });




        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.gray);
        swipeRefreshLayout.setOnRefreshListener(listener);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        listener.onRefresh();
        return  view;
    }


}
