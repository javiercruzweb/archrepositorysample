package mx.caltec.archrepositorysample.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mx.caltec.archrepositorysample.di.main.MainViewModelsModule;
import mx.caltec.archrepositorysample.presentation.views.MainActivity;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {MainViewModelsModule.class}
    )
    abstract MainActivity contributeMainActivity();
}
