package mx.caltec.archrepositorysample.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import mx.caltec.archrepositorysample.MovieApp;

/**
 * Created by mertsimsek on 20/05/2017.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        ActivityBuildersModule.class,
        ViewModelFactoryModule.class
        })
public interface AppComponent extends AndroidInjector<MovieApp> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
