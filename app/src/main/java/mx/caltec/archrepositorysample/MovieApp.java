package mx.caltec.archrepositorysample;

import android.app.Application;

import mx.caltec.archrepositorysample.data.api.FakeApi;
import mx.caltec.archrepositorysample.data.database.AppDatabase;
import mx.caltec.archrepositorysample.data.DataRepository;
import mx.caltec.archrepositorysample.util.AppExecutors;

public class MovieApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    private FakeApi getFakeApi() {
        return FakeApi.getInstance(this, mAppExecutors);
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), getFakeApi(), mAppExecutors);
    }
}
