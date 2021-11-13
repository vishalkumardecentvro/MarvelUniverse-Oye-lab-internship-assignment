package com.myapp.marveluniverse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.marveluniverse.adapter.HeroesAdapter;
import com.myapp.marveluniverse.databinding.ActivityMainBinding;
import com.myapp.marveluniverse.modalclass.Res;
import com.myapp.marveluniverse.modalclass.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArchitecturalFunctions {

  private HeroesAdapter heroesAdapter;
  private ActivityMainBinding binding;
  private List<ResultsItem> resultsItemList = new ArrayList<>();
  private GridLayoutManager gridLayoutManager;
  private boolean isScrolling = false;
  private int currentItems, totalItems, scrolledOutItems;
  private int offset = 0, limit = 20;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    instantiate();
    initialize();
    listen();
    load();
  }

  @Override
  public void instantiate() {
    gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
    heroesAdapter = new HeroesAdapter(this);


  }

  @Override
  public void initialize() {
    binding.rvMarvelHeroes.setLayoutManager(gridLayoutManager);

  }

  @Override
  public void listen() {
    heroesAdapter.setOnHeroClickInterface(new HeroesAdapter.OnHeroClickInterface() {
      @Override
      public void onHeroClick(int position) {
        Intent intent = new Intent(MainActivity.this, HeroesDescriptionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("heroName", resultsItemList.get(position).getName());
        bundle.putString("heroImage", resultsItemList.get(position).getThumbnail().getPath() + "." + resultsItemList.get(position).getThumbnail().getExtension());
        bundle.putString("heroDescription", resultsItemList.get(position).getDescription());

        intent.putExtra("heroBundle", bundle);
        startActivity(intent);
      }
    });

    binding.rvMarvelHeroes.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override
      public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
          isScrolling = true;
        }

      }

      @Override
      public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        currentItems = recyclerView.getLayoutManager().getChildCount();
        totalItems = recyclerView.getLayoutManager().getItemCount();
        scrolledOutItems = gridLayoutManager.findFirstVisibleItemPosition();

        if (isScrolling && currentItems + scrolledOutItems == totalItems) {
          isScrolling = false;

          fetchHeroes(offset + 20, limit + 20);

          offset += 20;
          limit += 20;
          heroesAdapter.notifyItemRangeChanged(offset, limit);
          Log.i("--sizeList--", String.valueOf(resultsItemList.size()));
        }
      }
    });

  }

  @Override
  public void load() {

    fetchHeroes(offset, limit);


  }

  private void fetchHeroes(int offset, int limit) {
    binding.progressBar.setVisibility(View.VISIBLE);
    binding.progressBar.setIndicatorColor(getResources().getColor(R.color.brand_color_dark));

    Call<Res> heroesList = Connection.getMarvelHeroes().heroes(offset, limit);
    heroesList.enqueue(new Callback<Res>() {
      @Override
      public void onResponse(Call<Res> call, Response<Res> response) {

        if (response.isSuccessful()) {
          if (offset > 0) {
            binding.rvMarvelHeroes.scrollToPosition(offset);
          }
          resultsItemList = response.body().getData().getResults();
          heroesAdapter.setHeroesList(resultsItemList);

          binding.rvMarvelHeroes.setAdapter(heroesAdapter);

//          for (ResultsItem i : response.body().getData().getResults()) {
//
//            Log.i("--name--", i.getName());
//            Log.i("--description--", i.getDescription());
//            Log.i("--thumbnailPath--", i.getThumbnail().getPath());
//            Log.i("--thumbnailExtension--", i.getThumbnail().getExtension());
//
//          }
        }
        binding.progressBar.setVisibility(View.GONE);

      }

      @Override
      public void onFailure(Call<Res> call, Throwable t) {
        Toast.makeText(MainActivity.this, "Please check your internet!", Toast.LENGTH_SHORT).show();
      }
    });

  }
}