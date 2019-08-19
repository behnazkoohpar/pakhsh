package com.koohpar.dstrbt.ui.splash;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import com.koohpar.dstrbt.BR;
import com.koohpar.dstrbt.BuildConfig;
import com.koohpar.dstrbt.R;
import com.koohpar.dstrbt.data.DataManager;
import com.koohpar.dstrbt.data.model.api.BannerResponse;
import com.koohpar.dstrbt.databinding.ActivitySplashBinding;
import com.koohpar.dstrbt.ui.base.BaseActivity;
import com.koohpar.dstrbt.ui.login.LoginActivity;
import com.koohpar.dstrbt.ui.main.AdvertiseRecycleViewAdapter;
import com.koohpar.dstrbt.ui.main.MainActivity;
import com.koohpar.dstrbt.utils.AppConstants;
import com.koohpar.dstrbt.utils.CommonUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements SplashNavigator, AppConstants {

    private static final long INTERVAL_LOGIN = 10000;
    private Handler mHandler;
    final Handler handler = new Handler();
    ActivitySplashBinding mActivitySplashBinding;

    @Inject
    SplashViewModel mSplashViewModel;
    private ProgressDialog dialogBar;
    public static List<BannerResponse> banerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySplashBinding = getViewDataBinding();
        mSplashViewModel.setNavigator(this);
        mSplashViewModel.setActivity(SplashActivity.this);
        mHandler = new Handler();
        Intent intent = getIntent();

        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                !hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                !hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermissionsSafely(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1000);
        }
        startCheck();
    }

    private void startCheck() {
        callMethods.run();
    }

    private void stopCheck() {
        mHandler.removeCallbacks(callMethods);
    }

    Runnable callMethods = new Runnable() {
        @Override
        public void run() {
            try {
                if (checkConnectivity(SplashActivity.this)) {
                    stopCheck();
                    callGetLastVersion();
//                    decideNextActivity();
                } else {
                    CommonUtils.showSingleButtonAlert(SplashActivity.this, getString(R.string.text_attention), getString(R.string.lost_internet), null, null);
                }
            } finally {
                mHandler.postDelayed(callMethods, INTERVAL_LOGIN);
            }
        }
    };

    private void callGetLastVersion() {
        stopCheck();
        try {
            HashMap<String, String> map = new HashMap<>();
            mSplashViewModel.callGetlastVersion(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }
    @Override
    public void getAllBanerList() {
        stopCheck();
        try {
            HashMap<String, String> map = new HashMap<>();
            if (LOGTRUE)
                Log.d("mPARAMS BanerList :::::::: ", map.toString());
            mSplashViewModel.getBanerList(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }
    }
    public boolean checkConnectivity(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public SplashViewModel getViewModel() {
        return mSplashViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void openLoginActivity() {
        stopCheck();
        Intent intent = LoginActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openMainActivity() {
        stopCheck();
        Intent intent = MainActivity.getStartIntent(SplashActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void decideNextActivity() {
        stopCheck();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSplashViewModel.getDataManager().getCurrentUserLoggedInMode()
                        == DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()) {
                    mSplashViewModel.getNavigator().openLoginActivity();
                } else {
                    callLogin();
                }
            }
        }, 5000);
    }

    private void callLogin() {
        try {

//            passwordE = TextEncrypter.MD5String(password);
            HashMap<String, String> map = new HashMap<>();
            map.put(REQUEST_KEY_MOBILE_NUMBER, mSplashViewModel.getDataManager().getUsername());
            map.put(REQUEST_KEY_PASSWORD, mSplashViewModel.getDataManager().getPassword());
            map.put(REQUEST_KEY_TOKEN, mSplashViewModel.getDataManager().getToken());
            if (LOGTRUE)
                Log.d("mPARAMS Login :::::::: ", map.toString());
            mSplashViewModel.login(iCallApi, this, map);
        } catch (Exception e) {
            CommonUtils.showSingleButtonAlert(this, getString(R.string.text_attention), getString(R.string.data_incorrect), null, null);
            e.printStackTrace();
        }

    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        try {
////            if (requestCode == PERMISSION_CODE_LOCATION) {
//            for (int i = 0; i < permissions.length; i++) {
//                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
////                    CommonUtils.showSingleButtonAlert(this, StringLanguage.no_permission_title, StringLanguage.get_permission_failed + "\n\n" + permissions[i], StringLanguage.btn_alert, new CommonUtils.IL() {
////                        @Override
////                        public void onSuccess() {
////                            AppLogger.d("PERMISSION DENIED");
////                            finish();
////                        }
////
////                        @Override
////                        public void onCancel() {
////                        }
////                    });
//
//                } else {
//                    invokeVersion();
////                    decideNextActivity();
//                }
//            }
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void invokeVersion(String url) {
        stopCheck();
        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                !hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ||
                !hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermissionsSafely(
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1000);
            getAllBanerList();
        } else {
            new UpdateApp().execute(url);
        }

    }

    @Override
    public void setBanner(List<BannerResponse> bannerResponses) {
        banerList = bannerResponses;
        decideNextActivity();
    }
    private class UpdateApp extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(0);
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                String PATH = "/mnt/sdcard/download/";
                File file = new File(PATH);
                file.mkdir();

                String AppName = "Distribute.apk";
                File outputFile = new File(file, AppName);
                if (outputFile.exists())
                    outputFile.delete();
                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();
                int lenght = c.getContentLength();
                byte[] buffer = new byte[1024];
                int len = 0;
                int total = 0;
                try {
                    while ((len = is.read(buffer)) != -1) {
                        total += len;
                        fos.write(buffer, 0, len);
                        publishProgress("" + (int) ((total * 100) / lenght));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fos.flush();
                fos.close();
                is.close();
                Uri contentUri;
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    contentUri = FileProvider.getUriForFile(SplashActivity.this,getApplicationContext().getPackageName() + ".provider",file);
//                    Intent i = new Intent(Intent.ACTION_VIEW, contentUri);
//                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    startActivity(i);
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.fromFile
//                            (new File(Environment.getExternalStorageDirectory() + "/download/" + AppName)), "application/vnd.android.package-archive");
//                    startActivity(intent);
//                }
                File toInstall = new File(PATH, "Distribute" + ".apk");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri apkUri = FileProvider.getUriForFile(SplashActivity.this, BuildConfig.APPLICATION_ID + ".provider", toInstall);
                    Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    intent.setData(apkUri);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                } else {
                    Uri apkUri = Uri.fromFile(toInstall);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(String... values) {
            dialogBar.setProgress(Integer.parseInt(values[0]));
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dismissDialog(0);
        }

    }

    protected Dialog onCreateDialog(int id) {
        dialogBar = new ProgressDialog(SplashActivity.this);
        dialogBar.setMessage(getString(R.string.software_updating));
        dialogBar.setMax(100);
        dialogBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialogBar.show();
        return dialogBar;
    }

}
