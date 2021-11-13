package com.myapp.marveluniverse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.myapp.marveluniverse.adapter.HeroesAdapter;
import com.myapp.marveluniverse.databinding.ActivityMainBinding;
import com.myapp.marveluniverse.modalclass.Res;
import com.myapp.marveluniverse.modalclass.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArchitecturalFunctions  {
  private String apiKey = "c71d57d7a4da14320d8c00bdddff6b4a&hash=3b2e2759c4a455bc7f57a3520aa56e16";
  private HeroesAdapter heroesAdapter;
  private ActivityMainBinding binding;
  private List<ResultsItem> resultsItemList = new ArrayList<>();

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
    heroesAdapter = new HeroesAdapter(this);

  }

  @Override
  public void initialize() {

  }

  @Override
  public void listen() {
    heroesAdapter.setOnHeroClickInterface(new HeroesAdapter.OnHeroClickInterface() {
      @Override
      public void onHeroClick(int position) {
        Intent intent = new Intent(MainActivity.this,HeroesDescriptionActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("heroName",resultsItemList.get(position).getName());
        bundle.putString("heroImage",resultsItemList.get(position).getThumbnail().getPath() + "." + resultsItemList.get(position).getThumbnail().getExtension());
        bundle.putString("heroDescription",resultsItemList.get(position).getDescription());

        intent.putExtra("heroBundle",bundle);
        startActivity(intent);
      }
    });

  }

  @Override
  public void load() {

    Call<Res> heroesList = Connection.getMarvelHeroes().heroes(0,20);
    heroesList.enqueue(new Callback<Res>() {
      @Override
      public void onResponse(Call<Res> call, Response<Res> response) {

        if (response.isSuccessful()) {
          resultsItemList = response.body().getData().getResults();
          heroesAdapter.setHeroesList(resultsItemList);
          binding.rvMarvelHeroes.setAdapter(heroesAdapter);

          int j = 0;
          for (ResultsItem i : response.body().getData().getResults()) {

            Log.i("--name--", i.getName());
            Log.i("--description--", i.getDescription());
            Log.i("--thumbnailPath--", i.getThumbnail().getPath());
            Log.i("--thumbnailExtension--", i.getThumbnail().getExtension());

          }
        }
      }

      @Override
      public void onFailure(Call<Res> call, Throwable t) {
        Log.i("--error--",t.toString());
      }
    });

  }
}