package phone1000.com.firemilitary.Activity.ViedoInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.liang.OkHttpLibrary.OkHttpUtils;

import phone1000.com.firemilitary.R;

/**
 * 使用WebView播放视频
 * <!--PlayVideoActivity设置横屏，在清单文件中设置-->
 *  <activity
 *       android:name=".activity.PlayVideoActivity"
 *       android:screenOrientation="landscape">
 *  </activity>
 */
public class PlayVideoActivity extends AppCompatActivity {

    private boolean netConnect;
    private WebView mWebView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        //判断当前网络状态
        netConnect = OkHttpUtils.isNetworkConnected(this);
        if (!netConnect) {
            Toast.makeText(this, "当前网络未连接,请查看网络状态", Toast.LENGTH_SHORT).show();
        }
        //接收VideoContentActivity传来的视频地址参数
        Intent intent = getIntent();
        String video_play = intent.getStringExtra("video_play");


        //初始化视图
        mWebView = (WebView) findViewById(R.id.video_play_wb);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.show();

        progressDialog.setMessage("视频页面加载中...");
        // 加载url
        mWebView.loadUrl(video_play);
        // 初始化比例
        //mWebView.setInitialScale(50);
        WebSettings settings = mWebView.getSettings();
        // 支持使用接口,可任意比例缩放
        settings.setUseWideViewPort(true);
        // setUseWideViewPort方法设置WebView推荐使用的窗口。setLoadWithOverviewMode方法是设置WebView加载的页面的模式。
        settings.setLoadWithOverviewMode(true);
        // 支持脚本JavaScript
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new VideoWebViewClient());

    }

    public class VideoWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);// WebView里面的链接还在同一个页面中加载
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
        mWebView.pauseTimers();
    }

    /**
     * 主要是把WebView所持用的资源销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.loadUrl("about:blank");
        mWebView.stopLoading();
        mWebView.setWebChromeClient(null);
        mWebView.setWebViewClient(null);
        mWebView.destroy();
        mWebView = null;
    }


}
