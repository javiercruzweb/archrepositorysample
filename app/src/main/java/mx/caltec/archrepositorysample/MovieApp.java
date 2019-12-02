package mx.caltec.archrepositorysample;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import mx.caltec.archrepositorysample.di.DaggerAppComponent;

public class MovieApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}
