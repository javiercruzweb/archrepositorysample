package mx.caltec.archrepositorysample.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mx.caltec.archrepositorysample.R;
import mx.caltec.archrepositorysample.data.model.Movie;
import mx.caltec.archrepositorysample.viewmodel.MovieListViewModel;
import mx.caltec.archrepositorysample.views.adapter.MovieAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Context mContext;

    private MovieAdapter adapter;

    private MovieListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        MovieListViewModel.Factory factory = new MovieListViewModel.Factory(getApplication());
        viewModel = new ViewModelProvider(this, factory).get(MovieListViewModel.class);

        setUI();

        subscribeViewModel();
    }

    private void setUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerViewMovies = findViewById(R.id.recycleViewMovies);
        mRecyclerViewMovies.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MovieAdapter(movie -> {
            Log.d(TAG, movie.toString());
        });
        mRecyclerViewMovies.setAdapter(adapter);
    }

    private void subscribeViewModel() {
        viewModel.getMovies().observe(this, movies -> {
            for (Movie m : movies) {
                Log.d(TAG, m.toString());
            }

            adapter.setmMovieList(movies);


        });
    }

    private void showAddMovie() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.view_add_movie,
                viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);

        AlertDialog alertDialog = builder.create();

        Button cancel = dialogView.findViewById(R.id.buttonCancel);
        cancel.setOnClickListener(view -> alertDialog.dismiss());

        Button save = dialogView.findViewById(R.id.buttonSaveMovie);
        save.setOnClickListener(view -> {
            EditText inputTitle = dialogView.findViewById(R.id.inputTitle);
            String title = inputTitle.getText().toString();

            EditText inputSynosis = dialogView.findViewById(R.id.inputSynopsis);
            String synopsis = inputSynosis.getText().toString();

            alertDialog.dismiss();

            viewModel.addMovie(title, synopsis);
        });

        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMovie:
                showAddMovie();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
