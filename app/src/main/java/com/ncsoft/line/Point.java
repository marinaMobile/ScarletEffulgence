package com.ncsoft.line;

import static com.ncsoft.line.Glob.FUNC_ID;
import static com.ncsoft.line.MainActivity.qhxsgdfync;
import static com.ncsoft.line.NewAct.qazwsxedcrfv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.appsflyer.AppsFlyerLib;
import com.orhanobut.hawk.Hawk;

import java.io.File;
import java.io.IOException;

public class Point extends AppCompatActivity {

    private static final String TAG = Point.class.getSimpleName();
    private static final int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;


    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;
    WebView viewMainMain;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        viewMainMain = findViewById(R.id.realView);

        WebSettings webSettings = viewMainMain.getSettings();

        webSettings.setJavaScriptEnabled(true);

        webSettings.setUseWideViewPort(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString().replace("; wv", ""));

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(false);

        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setAppCacheEnabled(true);


        webSettings.setAllowContentAccess(true);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(viewMainMain,true);


        final Activity activity = this;
        viewMainMain.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return false;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


                if(viewMainMain.getProgress() == 100) {
                    viewMainMain.setVisibility(View.VISIBLE);
//                    animationView.setVisibility(View.INVISIBLE);
                    saveUrl(url);
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });


        viewMainMain.loadUrl(yhgfxcbnmd());

        String permission = Manifest.permission.CAMERA;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }

        viewMainMain.setWebChromeClient(new WebChromeClient() {


            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    WebChromeClient.FileChooserParams fileChooserParams) {
                if (mFilePathCallback != null) {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    // create the file where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e(TAG, "Unable to create Image File", ex);
                    }

                    // continue only if the file was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                    } else {
                        takePictureIntent = null;
                    }
                }

                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("image/*");

                Intent[] intentArray;
                if (takePictureIntent != null) {
                    intentArray = new Intent[]{takePictureIntent};
                } else {
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.image_chooser));
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);

                return true;
            }

            // creating image files (Lollipop only)
            private File createImageFile() throws IOException {

                File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DirectoryNameHere");

                if (!imageStorageDir.exists()) {
                    imageStorageDir.mkdirs();
                }

                // create an image file name
                imageStorageDir = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                return imageStorageDir;
            }

            // openFileChooser for Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;

                try {
                    File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "DirectoryNameHere");

                    if (!imageStorageDir.exists()) {
                        imageStorageDir.mkdirs();
                    }

                    File file = new File(imageStorageDir + File.separator + "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

                    mCapturedImageURI = Uri.fromFile(file); // save to the private variable

                    final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
                    //captureIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");

                    Intent chooserIntent = Intent.createChooser(i, getString(R.string.image_chooser));
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

                    startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Camera Exception:" + e, Toast.LENGTH_LONG).show();
                }

            }

        });

    }

    public void saveUrl(String url) {
        SharedPreferences sp = getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("SAVED_URL", url);
        editor.apply();
    }

    public String yhgfxcbnmd(){


        String  cstr1, main_id, dlnk1, paket;

        String one, two, three, four, five, six;

        SharedPreferences sp_p = getSharedPreferences("SP_WEBVIEW_PREFS", MODE_PRIVATE);


        cstr1 = Hawk.get(qazwsxedcrfv);
        dlnk1 = Hawk.get(qhxsgdfync);
        main_id = Hawk.get(FUNC_ID);
        paket = "com.ncsoft.line";
        String afId = AppsFlyerLib.getInstance().getAppsFlyerUID(this);
        AppsFlyerLib.getInstance().setCollectAndroidID(true);
        String androidVersion = Build.VERSION.RELEASE;


        one = "sub_id_1=";
        two = "deviceID=";
        three = "ad_id=";
        four = "sub_id_4=";
        five = "sub_id_5=";
        six = "sub_id_6=";


        String first1 = "http://";
        String second2 = "scarleteffulgence.xyz/go.php?to=2&";
        String whole = first1 + second2;

        String fff;

        if (!cstr1.equals("null")){
            fff = whole+one+cstr1+"&"+two+afId+"&"+three+main_id+"&"+four+paket+"&"+five+androidVersion+"&"+six+"naming";
        }

        else{
            fff = whole+one+dlnk1+"&"+two+afId+"&"+three+main_id+"&"+four+paket+"&"+five+androidVersion+"&"+six+"deeporg";
        }


        Log.d("TESTAG", "Test Result " + fff);


        return sp_p.getString("SAVED_URL", fff);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        if (requestCode != FILECHOOSER_RESULTCODE || mFilePathCallback == null) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        Uri[] results = null;

        // check that the response is a good one
        if (resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null) {
                // if there is not data, then we may have taken a photo
                if (mCameraPhotoPath != null) {
                    results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                }
            } else {
                String dataString = data.getDataString();
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }

        mFilePathCallback.onReceiveValue(results);
        mFilePathCallback = null;

    }

    @Override
    public void onBackPressed() {
        if (viewMainMain.canGoBack()) {
            viewMainMain.goBack();
        } else {
            super.onBackPressed();
        }
    }
}