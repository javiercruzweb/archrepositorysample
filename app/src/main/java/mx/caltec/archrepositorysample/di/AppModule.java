package mx.caltec.archrepositorysample.di;


import android.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mx.caltec.archrepositorysample.data.DataRepository;
import mx.caltec.archrepositorysample.data.api.FakeApi;
import mx.caltec.archrepositorysample.data.database.AppDatabase;
import mx.caltec.archrepositorysample.util.AppExecutors;
import mx.caltec.archrepositorysample.util.MainThreadExecutor;

/**
 * Created by mertsimsek on 20/05/2017.
 */
@Module()
public class AppModule {

    @Provides
    @Singleton
    DataRepository provideDataRepository(AppDatabase appDatabase, FakeApi fakeApi,
                                         AppExecutors appExecutors) {
        return DataRepository.getInstance(appDatabase, fakeApi, appExecutors);
    }

    @Provides
    @Singleton
    AppDatabase provideAppData(Application application, AppExecutors appExecutors) {
        return AppDatabase.getInstance(application, appExecutors);
    }

    @Provides
    @Singleton
    FakeApi provideFakeApi(Application application, AppExecutors appExecutors) {
        return FakeApi.getInstance(application, appExecutors);
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors(@Named("diskIO") Executor diskIO,
                                     @Named("networkIO") Executor networkIO,
                                     @Named("mainThread") Executor mainThread){
        return new AppExecutors(diskIO, networkIO, mainThread);
    }

    @Provides
    @Singleton
    @Named("diskIO")
    Executor provideDiskIOExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    @Provides
    @Singleton
    @Named("networkIO")
    Executor provideNetworkIOExecutor() {
        return Executors.newFixedThreadPool(3);
    }

    @Provides
    @Singleton
    @Named("mainThread")
    Executor provideMainThread() {
        return new MainThreadExecutor();
    }

}
