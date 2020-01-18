package com.renewal_studio.zaradie;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class MainActivity extends AppCompatActivity {

    String url = "https://listim.com/api/yaga/events/";
    public static final String username = "zaryadyemob";
    public static final String password = "Sa0jQd5qY3Nk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    fetch(url, username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("test",e.toString());
                }
            }
        });
        thread.start();
    }

    public static void fetch(String url, String username, String password) throws Exception {
        OkHttpClient httpClient = createAuthenticatedClient(username, password);
        doRequest(httpClient, url);
    }

    private static OkHttpClient createAuthenticatedClient(final String username,
                                                          final String password) {
        OkHttpClient httpClient = new OkHttpClient.Builder().authenticator(new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(username, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();
        return httpClient;
    }

    private static void doRequest(OkHttpClient httpClient, String anyURL) throws Exception {
        RequestBody formBody = new FormBody.Builder()
                .add("parner_id", "101")
                .build();
        Request request = new Request.Builder()
                .url(anyURL)
                .post(formBody).build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                Log.d("test",response.body().string());
            }

            public void onFailure(Call call, IOException e) {
            }
        });
    }

}