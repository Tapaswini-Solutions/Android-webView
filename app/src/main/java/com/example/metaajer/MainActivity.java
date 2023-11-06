package com.example.metaajer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import android.Manifest;
//import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
//import android.os.Bundle;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
      WebView webView;

    private static final int CALL_LOG_PERMISSION_REQUEST = 1;
    private static final int MICROPHONE_PERMISSION_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("https://dev-mo2athr.metaajer.com/index.php/login");
        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(Web
//            View view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
        });

        // Assuming webView is your WebView object.
        String callLogData = "";// Retrieve and process call log data.
        String jsCode = "displayCallLog(" + callLogData + ");";
        webView.evaluateJavascript(jsCode, null);




        // Check and request permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkCallLogPermission() && checkMicrophonePermission()) {
                // Permissions are already granted, load the web page
                loadWebViewContent();
            } else {
                requestCallLogPermission();
                requestMicrophonePermission();
            }
        } else {
            // Permissions are granted (for devices running below Android 6.0)
            loadWebViewContent();
        }

    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }

// this is permission....
@RequiresApi(api = Build.VERSION_CODES.M)
private boolean checkCallLogPermission() {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED;
}

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkMicrophonePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCallLogPermission() {
        if (checkCallLogPermission()) {
            return; // Permission already granted
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, CALL_LOG_PERMISSION_REQUEST);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestMicrophonePermission() {
        if (checkMicrophonePermission()) {
            return; // Permission already granted
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_REQUEST);
    }

    private void loadWebViewContent() {
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://yourwebsite.com");
    }

    // Handle permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_LOG_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Call log permission granted, load your content
                loadWebViewContent();
            }
        } else if (requestCode == MICROPHONE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Microphone permission granted, load your content
                loadWebViewContent();
            }
        }
    }


}