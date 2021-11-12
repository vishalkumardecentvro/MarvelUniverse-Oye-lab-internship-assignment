package com.myapp.marveluniverse;

import com.myapp.marveluniverse.modalclass.Res;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Connection {

  public static String baseUrl = "https://gateway.marvel.com/v1/";
  public static MarvelHeroConnection marvelHeroConnection = null;

  public static MarvelHeroConnection getMarvelHeroes(){
    if(marvelHeroConnection == null){
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl(baseUrl)
              .addConverterFactory(GsonConverterFactory.create())
              .build();
      marvelHeroConnection = retrofit.create(MarvelHeroConnection.class);
    }
    return marvelHeroConnection;
  }

}

interface MarvelHeroConnection{
  //@GET("public/characters?ts=1&offset={minNumber}&limit={maxNumber}"+"&apikey=c71d57d7a4da14320d8c00bdddff6b4a&hash=3b2e2759c4a455bc7f57a3520aa56e16")
  @GET("public/characters?ts=1&apikey=c71d57d7a4da14320d8c00bdddff6b4a&hash=3b2e2759c4a455bc7f57a3520aa56e16")
  Call<Res> heroes(@Query("offset") int offset, @Query("limit") int limit);

  //@GET("public/characters?ts=1&offset={minNumber}&limit={maxNumber}"+"&apikey=c71d57d7a4da14320d8c00bdddff6b4a&hash=3b2e2759c4a455bc7f57a3520aa56e16")
//  @GET("public/characters")
//  Call<List<Data>> heroes(@Query("ts") Integer ts,
//                          @Query("offset") Integer minNumber,
//                          @Query("limit") Integer maxNumber,
//                          @Query("apiKey") String key);

}
