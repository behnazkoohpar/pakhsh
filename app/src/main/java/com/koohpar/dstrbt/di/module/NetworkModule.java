package com.koohpar.dstrbt.di.module;

import android.app.Application;
import android.util.Log;

import com.koohpar.dstrbt.api.ICallApi;
import com.koohpar.dstrbt.data.pref.AppPreferencesHelper;
import com.koohpar.dstrbt.utils.AppConstants;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shb on 10/31/2017.
 */
@Module
public class NetworkModule implements AppConstants {

    private static final String NAME_BASE_URL = "NAME_BASE_URL";
    private static final String NAME_HEADER_SECURITY_KEY = HEADER_SECURITY_KEY;
//    private static final String NAME_HEADER_DRIVER_ID = HEADER_DRIVER_ID;
    private static final String NAME_HEADER_TYPE = HEADER_TYPE;

    @Provides
    @Named(NAME_BASE_URL)
    String provideBaseUrlString() {
        return BASE_URL;
    }

    @Provides
    @Named(NAME_HEADER_SECURITY_KEY)
    String provideHeaderSecurityKey() {
        return HEADER_SECURITY_KEY;
    }

//    @Provides
//    @Named(NAME_HEADER_DRIVER_ID)
//    String provideHeaderDriverID() {
//        return HEADER_DRIVER_ID;
//    }

    @Provides
    @Named(NAME_HEADER_TYPE)
    String provideHeaderType() {
        return HEADER_TYPE;
    }

    @Provides
    Cache provideOkHttpClientCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    TrustManager[] provideTrusManagerCertificates() {
        final TrustManager[] certs = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }
        };

        return certs;
    }

    @Provides
    SSLContext provideSSLContext(TrustManager[] cert) {
        SSLContext ctx = null;

        try {
            ctx = SSLContext.getInstance("SSL");
            ctx.init(null, cert, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
        }
        return ctx;

    }

    @Provides
    HostnameVerifier provideHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
    }

    @Provides
    HttpLoggingInterceptor provideClientInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @Provides
    @Singleton
    Interceptor provideInterceptor(final AppPreferencesHelper appPreferencesHelper,
//                                   @Named(NAME_HEADER_DRIVER_ID) final String driverId,
                                   @Named(NAME_HEADER_SECURITY_KEY) final String securityKey,
                                   @Named(NAME_HEADER_TYPE) final String type) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                if (appPreferencesHelper != null) {
                    Request request = original.newBuilder()
//                            .addHeader(driverId, appPreferencesHelper.getDriverId())
                            .addHeader(securityKey, appPreferencesHelper.getSecurityKey())
//                            .addHeader(type, "driver")
                            .build();
                    if (LOGTRUE)
                        Log.d("Header", request.headers().toString());
                    return chain.proceed(request);
                } else return null;
            }
        };
        return interceptor;
    }

    @Provides
    OkHttpClient provideOkHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor, Interceptor interceptor, SSLContext sslContext, TrustManager[] trustManagers, HostnameVerifier hostnameVerifier) {
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(interceptor)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40,TimeUnit.SECONDS)
                .connectTimeout(40, TimeUnit.SECONDS)
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagers[0])
                .hostnameVerifier(hostnameVerifier)
                .build();
        return client;
    }

    @Provides
    GsonConverterFactory provideGsonConverter() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory converter, OkHttpClient client, @Named(NAME_BASE_URL) String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    ICallApi provideCallApi(Retrofit retrofit) {
        return retrofit.create(ICallApi.class);
    }

}
