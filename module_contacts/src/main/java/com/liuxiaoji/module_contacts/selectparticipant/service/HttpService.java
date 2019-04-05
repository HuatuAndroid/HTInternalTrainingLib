package com.liuxiaoji.module_contacts.selectparticipant.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.liuxiaoji.module_contacts.selectparticipant.common.NetError;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class HttpService {
    public static final String TAG = "HttpService";
    private Context mContext;
    private Retrofit mRetrofit;

    public <T> T HttpService(Context context, String url, Class<T> tClass, String token) {
        this.mContext = context;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(new HttpClient().getClient(mContext, token))
                .build();
        return mRetrofit.create(tClass);
    }


    private HttpsCall enqueueRetrofitCall(final Call call, final NetCallback netCallback) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (netCallback == null) return;
                if (response.isSuccessful()) {
                    netCallback.onSuccess(response.body(), call);
                } else {

                    NetError error = new NetError();
                    error.setmErrorCode(response.code());
                    String errorBodyStr = "";
                    try {
                        errorBodyStr = response.errorBody().string();
                        ErrorResponse errorResponse = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                .create().fromJson(errorBodyStr, ErrorResponse.class);
                        if (errorResponse != null) {
                            error.setmServerMessage(errorResponse.error);
                            error.setmErrorCode(errorResponse.code);
                        } else {
                            error.setmServerMessage(errorBodyStr);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JsonSyntaxException e) {
                        try {
                            error.setmServerMessage("网络异常");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } finally {
                        try {
                            if (error.getmErrorCode() == 404) {
                                error.setmServerMessage("网络异常");
                            }
                            netCallback.onFailure(error, call);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                if (netCallback == null) return;
                NetError error = new NetError();
                String errorMsg = "";
                int errorCode = NetError.NET_ERROR_CODE;
                errorMsg = "网络未连接，请连接后重试";
                if (t instanceof JsonSyntaxException) {
                    errorCode = NetError.SERVER_ERROR_CODE;
                    errorMsg = "网络异常";
                }
                error.setmErrorCode(errorCode);
                error.setmServerMessage(errorMsg);
                netCallback.onFailure(error, call);
                Log.e("http error", t.toString());
            }
        });
        return new HttpsCall(call);
    }

    /**
     * 处理用户的网络请求
     *
     * @return
     */
    public HttpsCall netRequest(Call call, NetCallback netCallback) {
        return enqueueRetrofitCall(call, netCallback);
    }
}