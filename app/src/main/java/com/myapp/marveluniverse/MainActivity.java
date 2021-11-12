package com.myapp.marveluniverse;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.myapp.marveluniverse.modalclass.Res;
import com.myapp.marveluniverse.modalclass.Res;
import com.myapp.marveluniverse.modalclass.ResultsItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ArchitecturalFunctions {
  private String apiKey = "c71d57d7a4da14320d8c00bdddff6b4a&hash=3b2e2759c4a455bc7f57a3520aa56e16";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    instantiate();
    initialize();
    listen();
    load();
  }

  @Override
  public void instantiate() {

  }

  @Override
  public void initialize() {

  }

  @Override
  public void listen() {

  }

  @Override
  public void load() {
    Log.i("--chk--", "inside load");
    Call<Res> heroesList = Connection.getMarvelHeroes().heroes(20,40);
    heroesList.enqueue(new Callback<Res>() {
      @Override
      public void onResponse(Call<Res> call, Response<Res> response) {
        Log.i("--chk--", "inside onRes");
        Log.i("--code--", String.valueOf(response.code()));
          Log.i("--size--", String.valueOf(response.body().getData().getResults().size()));
        if (response.isSuccessful()) {
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