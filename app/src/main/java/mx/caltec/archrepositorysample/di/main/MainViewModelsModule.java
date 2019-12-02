package mx.caltec.archrepositorysample.di.main;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import mx.caltec.archrepositorysample.di.ViewModelKey;
import mx.caltec.archrepositorysample.presentation.viewmodels.MovieListViewModel;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    public abstract ViewModel bindAuthViewModel(MovieListViewModel viewModel);


}
