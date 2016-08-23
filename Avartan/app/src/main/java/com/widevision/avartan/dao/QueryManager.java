package com.widevision.avartan.dao;


import android.util.Log;

import com.widevision.avartan.util.AsyncCallback;
import com.widevision.avartan.util.NetworkUtils;
import com.squareup.okhttp.Request;


/**
 * Created by Usama
 */
public abstract class QueryManager<T> {
    /**
     * Generate a request that encapsulates the API endpoint and any necessary parameters/values the API needs.
     *
     * @return
     */
    protected abstract Request.Builder buildRequest();

    /**
     * Asynchronously queries the web api using the parameters provided in the overridden buildRequest method. Data is handed back in the
     * parseResponse method.
     *
     * @param onFinishedCallback
     */
    public void query(final AsyncCallback<T> onFinishedCallback) {
        NetworkUtils.asyncQuery(buildRequest(), new AsyncCallback<String>() {
            @Override
            public void onOperationCompleted(String response, Exception e) {
                if (e == null) {
                    if (response != null) {
                        String serializedResponse = response.toString();
                        Log.e("chat receive response", "------------>" + serializedResponse);
                        T output = parseResponse(serializedResponse);

                        onFinishedCallback.onOperationCompleted(output, null);
                    } else {
                        onFinishedCallback.onOperationCompleted(null, new Exception("Null response from server"));
                    }

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    protected abstract T parseResponse(String jsonResponse);
}
