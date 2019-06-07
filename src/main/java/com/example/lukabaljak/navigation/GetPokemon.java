package com.example.lukabaljak.navigation;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class GetPokemon extends AsyncTask<Void,Void ,JSONObject> {

    private LinkedList<Pokemon> pokemonList;
    private Context context;
    ListView pokemonListView;

    public GetPokemon(Context context, ListView pokemonListView) {
        this.context = context;
        this.pokemonListView = pokemonListView;
    }

    @Override
    protected JSONObject doInBackground(Void... voids) {



        JSONObject json;
        try {
            URL url = new URL("https://api.pokemontcg.io/v1/cards");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader
                    (new InputStreamReader(connection.getInputStream()));

            String response = "";

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                String line;
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                Log.d("JSONRESPONSE",response);

                json = new JSONObject(response);

                return  json;
            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            JSONArray pokemonJSONArray = jsonObject.getJSONArray("cards");

            pokemonList= new LinkedList<>();
            for (int i =0;i<pokemonJSONArray.length();i++){
                JSONObject pokemonJSONObject = pokemonJSONArray.getJSONObject(i);
                if(pokemonList.size()>20){
                    break;
                }
                if(pokemonJSONObject.has("types")){
                    Pokemon pokemon = new Pokemon();
                    pokemon.setName(pokemonJSONObject.getString("name"));
                    pokemon.setPictureURL("https://img.pokemondb.net/artwork/"
                            +pokemon.getName()+".jpg");

                    JSONArray pokemonTypes = pokemonJSONObject.getJSONArray("types");
                    String types="";
                    for(int j=0;j<pokemonTypes.length();j++){
                        if(j==0) {
                            types += pokemonTypes.getString(j);
                        } else {
                            types += ", "+pokemonTypes.getString(j);
                        }
                    }

                    pokemon.setTypes(types);
                    pokemonList.add(pokemon);
                }
            }

            PokemonListAdapter pokemonListAdapter = new PokemonListAdapter(context, pokemonList);
            pokemonListView.setAdapter(pokemonListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

