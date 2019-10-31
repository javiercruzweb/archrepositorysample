package mx.caltec.archrepositorysample;

import android.app.Application;

import mx.caltec.archrepositorysample.data.AppDatabase;
import mx.caltec.archrepositorysample.data.DataRepository;
import mx.caltec.archrepositorysample.util.AppExecutors;

public class MovieApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), mAppExecutors);
    }
}