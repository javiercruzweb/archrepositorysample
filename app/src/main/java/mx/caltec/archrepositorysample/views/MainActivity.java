package mx.caltec.archrepositorysample.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
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
import android.widget.SearchView;
import android.widget.Toast;

import mx.caltec.archrepositorysample.R;
import mx.caltec.archrepositorysample.data.Status;
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
        viewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        setUI();
        subscribeViewModel();
    }

    private void setUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerViewMovies = findViewById(R.id.recycleViewMovies);
        mRecyclerViewMovies.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MovieAdapter(movie -> Log.d(TAG, movie.toString()));
        mRecyclerViewMovies.setAdapter(adapter);
    }

    private void subscribeViewModel() {
        viewModel.getMovies().observe(this, resource -> {
            String y = "resource: " + (resource.data!=null ? ""+resource.data.size() : "0");
            String x = "status: " + resource.status + y;
            Log.d(TAG, x);

            if (resource.status.equals(Status.ERROR)) {
                Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
                return;
            }

            if (resource.status.equals(Status.LOADING)) {
                Toast.makeText(mContext, "loading", Toast.LENGTH_SHORT).show();
            }

            if (resource.status.equals(Status.SUCCESS)) {
                Toast.makeText(mContext, "success", Toast.LENGTH_SHORT).show();
            }

            adapter.setmMovieList(resource.data);
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

    private void refresh() {
        viewModel.reloadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movies, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                viewModel.getMoviesLike(query);
                return true;
            }

        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addMovie:
                showAddMovie();
                return true;

            case R.id.refresh:
                refresh();
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }
}
