/*
 *
 *  Copyright (C) 2018 Eduardo Zarate Lasurtegui
 *   Copyright (C) 2018, University of the Basque Country (UPV/EHU)
 *
 *  Contact for licensing options: <licensing-mcpttclient(at)mcopenplatform(dot)com>
 *
 *  This file is part of MCOP MCPTT Client
 *
 *  This is free software: you can redistribute it and/or modify it under the terms of
 *  the GNU General Public License as published by the Free Software Foundation, either version 3
 *  of the License, or (at your option) any later version.
 *
 *  This is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */


package org.test.client.mcopclient;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;



import java.net.URI;
import java.net.URISyntaxException;

public class ScreenAuthenticationWebView extends AppCompatActivity {

    private final static String TAG = Utils.getTAG(ScreenAuthenticationWebView.class.getCanonicalName());
    private Context mContext;
    public final static int RETURN_ON_AUTHENTICATION_LISTENER_OK= 6547;
    public final static int RETURN_ON_AUTHENTICATION_LISTENER_FAILURE= 6548;
    public final static String RETURN_ON_AUTHENTICATION_CAMPS= "RETURN__ON_AUTHENTICATION_CAMPS."+TAG;
    public final static String RETURN_ON_AUTHENTICATION_ERROR= "RETURN__ON_AUTHENTICATION_ERROR."+TAG;
    public final static String RETURN_ON_AUTHENTICATION_RESPONSE= "RETURN__ON_AUTHENTICATION_RESPONSE."+TAG;

    public final static String DATA_URI_INTENT= "DATA_URI_INTENT."+TAG;
    public final static String DATA_REDIRECTION_URI= "DATA_REDIRECTION_URI."+TAG;
    public final static String DATA_USER= "DATA_USER."+TAG;
    public final static String DATA_PASS= "DATA_PASS."+TAG;
    private final static String QUERY_PATH="code";

    private final static int TIME_DELAY_MSEG=3000;

    private WebView screen_authentication_WebView_info;
    private String userAuthentication;
    private String passAuthentication;
    private String uriString;
    private Runnable mRunnableCheckUrl;
    private Handler handler;
    private URI redirectionDataUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_authentication_web);

        mContext=this;
        Log.d(TAG,"Init proccess for token");
        //Init Delete all Cache for webView
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //End Delete all Cache for webView
        screen_authentication_WebView_info=(WebView)findViewById(R.id.screen_authentication_WebView_info);
        // Force links and redirects to open in the WebView instead of in a browser
        screen_authentication_WebView_info.setWebViewClient(new WebViewClient());
        // Enable Javascript
        WebSettings webSettings = screen_authentication_WebView_info.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        Intent intent=getIntent();
        if (intent != null) {
            uriString=intent.getStringExtra(DATA_URI_INTENT);
            String redirectionDataString=intent.getStringExtra(DATA_REDIRECTION_URI);
            if(redirectionDataString==null || redirectionDataString.trim().isEmpty()){
                Log.e(TAG,"Data for redirectionURI isn´t valid");
                sendError("Data for redirectionURI isn´t valid");
            }else{
                try {
                    redirectionDataUri=new URI(redirectionDataString);
                } catch (URISyntaxException e) {
                    Log.e(TAG,"Error parse redirection URI. "+e.getMessage() );
                    sendError("Error parse redirection URI. "+e.getMessage());
                }
            }
            userAuthentication=intent.getStringExtra(DATA_USER);
            passAuthentication=intent.getStringExtra(DATA_PASS);
            if(uriString!=null){
                screen_authentication_WebView_info.loadUrl(uriString);
            }

        }
        screen_authentication_WebView_info.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(final WebView view, int errorCode, String description,
                                        final String failingUrl) {

                Uri uri=Uri.parse(screen_authentication_WebView_info.getUrl());
                if(((uri.getScheme().compareToIgnoreCase(redirectionDataUri.getScheme())!=0 ||
                        uri.getHost().compareToIgnoreCase(redirectionDataUri.getHost())!=0 ||
                        uri.getPort()!=redirectionDataUri.getPort() )&&
                        description.trim().compareTo("net::ERR_CONNECTION_REFUSED") ==0
                )){
                    Log.e(TAG,"Error in web code: "+errorCode+" des: "+description);
                    finishError(getString(R.string.Error_in_authetication)+" code: "+errorCode+" des: "+description);
                }

                super.onReceivedError(view, errorCode, description, failingUrl);

            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                if(userAuthentication!=null && passAuthentication!=null){
                    screen_authentication_WebView_info.loadUrl("javascript:(function() { document.getElementById('j_username').value = '" + userAuthentication + "'; ;})()");
                    screen_authentication_WebView_info.loadUrl("javascript:(function() { document.getElementById('j_password').value = '" + passAuthentication + "'; ;})()");
                }
                checkUrl();
            }
        });
    }

    private void checkUrl(){
        handler = new Handler();
        mRunnableCheckUrl=new Runnable() {
            @Override
            public void run() {
                try {
                    Uri uri=Uri.parse(screen_authentication_WebView_info.getUrl());
                    String scheme=uri.getScheme();
                    String host=uri.getHost();
                    String path=uri.getPath();
                    if(scheme.compareToIgnoreCase(redirectionDataUri.getScheme())==0 &&
                            host.compareToIgnoreCase(redirectionDataUri.getHost())==0 &&
                            uri.getPort()==redirectionDataUri.getPort() &&
                            path.contains(redirectionDataUri.getPath())
                            ){
                        Intent intent = new Intent();
                        intent.putExtra(RETURN_ON_AUTHENTICATION_RESPONSE, uri.toString());
                        setResult(RETURN_ON_AUTHENTICATION_LISTENER_OK,intent);
                        finish();
                    }
                    else{
                        if(userAuthentication!=null &&
                                !userAuthentication.isEmpty() &&
                                passAuthentication!=null &&
                                !passAuthentication.isEmpty()) {
                            //Configure user and pass And click in submit
                            screen_authentication_WebView_info.loadUrl("javascript:(function() { document.getElementsByName('submit')[0].click(); })()");
                            //Click in Authorize
                            screen_authentication_WebView_info.loadUrl("javascript:(function() { document.getElementsByName('authorize')[0].click(); })()");

                        }else{
                            //In this situation the
                            checkUrl();
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG,"Error checking Url authentication: "+e.getMessage());
                    e.printStackTrace();
                }

            }
        };
        handler.postDelayed(mRunnableCheckUrl,TIME_DELAY_MSEG);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onNewIntent(Intent intent) {

    }

    @Override
    public void finish(){
        if(handler!=null){
            handler.removeCallbacks(mRunnableCheckUrl);
        }
        super.finish();
    }

    public void finishError(String error){
        sendError(error);
        finish();
    }

    private void sendError(String error){
        Intent intent = new Intent();
        intent.putExtra(RETURN_ON_AUTHENTICATION_ERROR,error);
        setResult(RETURN_ON_AUTHENTICATION_LISTENER_FAILURE,intent);
    }

}
