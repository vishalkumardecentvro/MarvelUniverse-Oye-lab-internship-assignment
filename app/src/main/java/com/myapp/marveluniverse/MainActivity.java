package com.myapp.marveluniverse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ArchitecturalFunctions {

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

  }
}