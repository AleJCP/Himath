package com.example.prototipo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="himath";

    public AdminSQLiteOpenHelper(@Nullable Context context) {

        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //CREAR LA TABLA USUARIOS
        String CONSULTACREARBDD = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "monedas INTEGER," +
                "corazones INTEGER,"+
                "llaves INTEGER,"+
                "cofres INTEGER,"+
                "time_aux_powerUp TEXT,"+
                "power INTEGER)";

        //CREAR LA TABLA ESTADISTICAS
        String CONSULTACREARBDD2 = "CREATE TABLE estadisticas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipodejuego TEXT," +
                "dificultad INTEGER," +
                "corazones_rest INTEGER," +
                "resp_correct INTEGER," +
                "resp_incorrect INTEGER)";

        //CREAR TABLA Logros
        String CONSULTACREARBDD3 = "CREATE TABLE logros (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fiebreoro INTEGER," +
                "matematicoprincipiante INTEGER," +
                "matematicoexperto INTEGER)";

        //CREAR TABLA Niveles
        String CONSULTACREARBDD4 = "CREATE TABLE niveles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "desbloqueable1 INTEGER," +
                "desbloqueable2 INTEGER)";

      //TABLA USUARIOS
        sqLiteDatabase.execSQL(CONSULTACREARBDD);

        //TABLA ESTADISTICAS
        sqLiteDatabase.execSQL(CONSULTACREARBDD2);

        //TABLA Logros desbloqueables
        sqLiteDatabase.execSQL(CONSULTACREARBDD3);

        //TABLA Niveles desbloqueables
        sqLiteDatabase.execSQL(CONSULTACREARBDD4);

        //INSERTAR DATOS
        ContentValues values = new ContentValues();

     /*   // NUMERO 1
        values.put(BDDdificultad, "facil");
        values.put(BDDEcuacion, "6 + 9 = ?");
        values.put(BDDOpcion1, "1");
        values.put(BDDOpcion2, "15");
        values.put(BDDOpcion3, "18");
        values.put(BDDOpcion4, "19");
        values.put(BDDOpcion5, "4");
        values.put(BDDResultado, "");
        values.put(BDDTipodeOperacion, "suma");*/

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
