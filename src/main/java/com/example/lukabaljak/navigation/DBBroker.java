package com.example.lukabaljak.navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

public class DBBroker extends SQLiteOpenHelper {


    public DBBroker(Context context) {
        super(context, "pokemonDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //sqLiteDatabase = getWritableDatabase();
        String sql = "CREATE TABLE pokemon ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Name TEXT, "+
                "PictureURL TEXT, "+
                "Types TEXT )";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public void insertPokemon(Pokemon pokemon){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //onCreate(sqLiteDatabase);
        sqLiteDatabase.getAttachedDbs().get(0);
        ContentValues contentValues = new ContentValues();

        contentValues.put("Name", pokemon.getName());
        contentValues.put("PictureURL", pokemon.getName());
        contentValues.put("Types", pokemon.getTypes());


        sqLiteDatabase.insert("pokemon",null,contentValues);
        sqLiteDatabase.close();
        Log.d("pokemon ubacen","daa");
    }


    public LinkedList<Pokemon> selectAll(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * FROM pokemon", null);
        LinkedList<Pokemon> pokemonList = new LinkedList<>();


        while(cursor.moveToNext()){
            Log.d("USAO","USAO");
           Pokemon pokemon = new Pokemon();

            pokemon.setId(cursor.getInt(0));
            pokemon.setName(cursor.getString(1));
            pokemon.setPictureURL(cursor.getString(2));
            pokemon.setTypes(cursor.getString(3));

            pokemonList.add(pokemon);
        }

        sqLiteDatabase.close();
        return pokemonList;
    }

    public void deletePokemon(Pokemon pokemon) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.d("POKEMON IZBACEN","DAA");
        db.delete("pokemon",
                "Name = ?",
                new String[] { String.valueOf(pokemon.getName()) });

       db.close();
    }

    public boolean doesExist(Pokemon pokemon){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor =sqLiteDatabase.rawQuery("SELECT * FROM pokemon WHERE Name = '"+pokemon.getName()+"'", null);

        if(cursor.moveToNext()){
            sqLiteDatabase.close();
            return true;
        } else {
            sqLiteDatabase.close();
            return false;
        }
    }

    public void deleteAllEntries(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM pokemon");

        db.close();
    }


}
