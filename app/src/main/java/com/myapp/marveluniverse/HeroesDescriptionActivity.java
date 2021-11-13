package com.myapp.marveluniverse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.myapp.marveluniverse.databinding.ActivityHeroesDescriptionBinding;

public class HeroesDescriptionActivity extends AppCompatActivity implements ArchitecturalFunctions {
  private ActivityHeroesDescriptionBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityHeroesDescriptionBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    instantiate();
    initialize();
    listen();
    load();
  }

  @Override
  public void instantiate() {

    Intent intent = getIntent();
    Bundle bundle = intent.getBundleExtra("heroBundle");

    if (bundle != null) {
      binding.tvDescription.setText(bundle.getString("heroDescription"));
      if(bundle.getString("heroDescription").isEmpty()){
        binding.tvDescription.setText("Sorry!! No description.");
      }
      binding.tvName.setText(bundle.getString("heroName"));

      Glide.with(this)
              .asBitmap()
              .load(bundle.getString("heroImage"))
              .into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                  binding.ivHeroImage.setImageBitmap(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }
              });
    }

  }

  @Override
  public void initialize() {

  }

  @Override
  public void listen() {

  }

  @Override
  public void load() {

  }
}