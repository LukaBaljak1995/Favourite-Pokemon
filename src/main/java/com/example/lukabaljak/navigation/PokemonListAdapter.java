package com.example.lukabaljak.navigation;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;

public class PokemonListAdapter implements ListAdapter {
    LinkedList<Pokemon> pokemonList;
    Context context;
    DBBroker dbBroker;
    boolean favourites;

    public PokemonListAdapter(Context context, LinkedList<Pokemon> pokemonList) {
        this.context = context;
        this.pokemonList = pokemonList;
        dbBroker = new DBBroker(context);
    }

    public void setFavourites(boolean favourites) {
        this.favourites = favourites;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return pokemonList.size();
    }

    @Override
    public Object getItem(int i) {
        return pokemonList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.single_list_item, null);

        ImageView pokemonImageView = view.findViewById(R.id.pokemonImage);
        TextView pokemonName = view.findViewById(R.id.pokemonName);
        TextView pokemonTypes = view.findViewById(R.id.pokemonTypes);

        if (pokemonList.get(i).getName().contains(" ex")) {
            Picasso.get().load("https://img.pokemondb.net/artwork/" + pokemonList.get(i).getName().toLowerCase().split(" ")[0] + ".jpg").into(pokemonImageView);
        } else if (pokemonList.get(i).getName().contains("-EX")) {
            Picasso.get().load("https://img.pokemondb.net/artwork/" + pokemonList.get(i).getName().toLowerCase().split("-")[0] + ".jpg").into(pokemonImageView);

        } else {
            Picasso.get().load("https://img.pokemondb.net/artwork/" + pokemonList.get(i).getName().toLowerCase() + ".jpg").into(pokemonImageView);
        }
        pokemonName.setText(pokemonList.get(i).getName());
        pokemonTypes.setText(pokemonList.get(i).getTypes());

        ImageView heartImageView = view.findViewById(R.id.heartImageView);
        if(dbBroker.doesExist(pokemonList.get(i))){
            heartImageView.setImageResource(R.drawable.ic_favorite_black_24dp);
        } else {
            heartImageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }


        heartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView heart = (ImageView) view;
                ViewGroup parent = (ViewGroup) heart.getParent();
                Log.d("BROJ DECII", parent.getChildCount() + "");

                TextView nameTextView = (TextView) parent.getChildAt(1);
                Pokemon pokemon = new Pokemon(String.valueOf(nameTextView.getText()));
                if(dbBroker.doesExist(pokemon)){
                    dbBroker.deletePokemon(pokemon);
                    heart.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    if(favourites){
                        parent.removeAllViews();
                    }
                } else {
                    pokemon.setTypes(String.valueOf(((TextView) parent.getChildAt(1)).getText()));
                    dbBroker.insertPokemon(pokemon);
                    heart.setImageResource(R.drawable.ic_favorite_black_24dp);
                }

            }
        });


        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


}
