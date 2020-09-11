package com.example.evolvefm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.evolvefm.R;

public class FragmentNews extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WebView webView = view.findViewById(R.id.newsWebView);
                        webView.loadUrl("https://vk.com/evolvefm");
                        webView.setWebViewClient(new WebViewClient());
                    }
                });
            }
        }).start();

        return view;
    }
}