package mx.caltec.archrepositorysample.data.api;

import android.content.Context;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.caltec.archrepositorysample.util.AppExecutors;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class FakeApi {
    private static FakeApi sInstance;
    private Context context;
    private FakeWebservice fakeWebservice;

    public static FakeApi getInstance(Context context, AppExecutors appExecutors) {
        if (sInstance == null) {
            sInstance = new FakeApi(context, appExecutors);
        }

        return sInstance;
    }

    private FakeApi(Context context, AppExecutors appExecutors) {
        this.context = context;

        //http client
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(10, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS);

        //retrofit build
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(appExecutors.networkIO())
                .build();

        fakeWebservice = retrofit.create(FakeWebservice.class);
    }

    public Observable<List<MovieApi>> getMovies() {
        return fakeWebservice.getMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
