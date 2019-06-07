package com.example.lukabaljak.navigation;

import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    DBBroker dbBroker;
    ListView pokemonListView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    loadList();
                    return true;
                case R.id.navigation_dashboard:
                    loadFavouritesList();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pokemonListView = findViewById(R.id.pokemonList);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        dbBroker = new DBBroker(this);
        dbBroker.deleteAllEntries();
        loadList();
    }


    public void loadList() {

        GetPokemon getPokemon = new GetPokemon(this, pokemonListView);
        getPokemon.execute();

    }

    private void loadFavouritesList() {
        PokemonListAdapter pokemonListAdapter = new PokemonListAdapter(this, dbBroker.selectAll());
        pokemonListAdapter.setFavourites(true);
        pokemonListView.setAdapter(pokemonListAdapter);

    }

}
