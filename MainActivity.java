package com.maher.zero;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class MainActivity extends Activity {
    private WebView web;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(android.graphics.Color.rgb(8, 7, 13));
        getWindow().setNavigationBarColor(android.graphics.Color.rgb(8, 7, 13));
        web = new WebView(this);
        web.setBackgroundColor(android.graphics.Color.rgb(8, 7, 13));
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        web.addJavascriptInterface(new PhoneBridge(), "MaherPhone");
        web.setWebViewClient(new WebViewClient());
        setContentView(web);
        web.loadUrl("file:///android_asset/index.html");
    }
    public class PhoneBridge {
        @JavascriptInterface public String getApps() {
            JSONArray array = new JSONArray();
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            PackageManager pm = getPackageManager();
            List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);
            for (ResolveInfo info : apps) {
                try {
                    JSONObject item = new JSONObject();
                    item.put("name", info.loadLabel(pm).toString());
                    item.put("package", info.activityInfo.packageName);
                    item.put("icon", "");
                    array.put(item);
                } catch (Exception ignored) {}
            }
            return array.toString();
        }
        @JavascriptInterface public void launchApp(String packageName) {
            runOnUiThread(() -> {
                Intent launch = getPackageManager().getLaunchIntentForPackage(packageName);
                if (launch != null) startActivity(launch);
            });
        }
        @JavascriptInterface public void openSettings(String page) {
            runOnUiThread(() -> {
                Intent i;
                switch (page) {
                    case "wifi": i = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS); break;
                    case "bluetooth": i = new Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS); break;
                    case "display": i = new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS); break;
                    case "sound": i = new Intent(android.provider.Settings.ACTION_SOUND_SETTINGS); break;
                    default: i = new Intent(android.provider.Settings.ACTION_SETTINGS);
                }
                startActivity(i);
            });
        }
    }
    @Override public void onBackPressed() {
        if (web != null) web.evaluateJavascript("showHome()", null);
        else super.onBackPressed();
    }
}
