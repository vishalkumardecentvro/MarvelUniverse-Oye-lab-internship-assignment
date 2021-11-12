package com.myapp.marveluniverse.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.myapp.marveluniverse.databinding.RvMarvelHeroesBinding;
import com.myapp.marveluniverse.modalclass.ResultsItem;

import java.util.ArrayList;
import java.util.List;

public class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.ViewHolder> {

  private List<ResultsItem> heroesList = new ArrayList<>();
  private Context context;

  public HeroesAdapter(Context context) {
    this.context = context;
  }

  @NonNull
  @Override
  public HeroesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    return new ViewHolder(RvMarvelHeroesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull HeroesAdapter.ViewHolder holder, int position) {
    holder.populate(position);

  }

  @Override
  public int getItemCount() {
    return heroesList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private RvMarvelHeroesBinding binding;

    public ViewHolder(@NonNull RvMarvelHeroesBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    private void populate(int position){
      binding.tvName.setText(heroesList.get(position).getName());

      Glide.with(context)
              .asBitmap()
              .load(heroesList.get(position).getThumbnail().getPath()+"."+heroesList.get(position).getThumbnail().getExtension())
              .into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                  binding.ivCrewImage.setImageBitmap(resource);
                }

                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) {
                }
              });
    }
  }

  public void setHeroesList(List<ResultsItem> heroesList) {
    this.heroesList = heroesList;
    notifyDataSetChanged();
  }
}
