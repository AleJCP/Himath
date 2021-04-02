package com.example.prototipo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class lvl_incomplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl_incomplete);

        //PROCESAMIENTO DE ESTADOISTICAS DESDE EL JUEGO
        //FECHA
        //HORA
        int Vidas_restantes = getIntent().getExtras().getInt("VidasRestantes");
        int Veces_Resp_Correct = getIntent().getExtras().getInt("Veces_Resp_Correct");
        int Veces_Resp_Incorrect = getIntent().getExtras().getInt("Veces_Resp_Incorrect");
        String TipodeJuego = getIntent().getExtras().getString("TipodeJuego");
        int dificultad = getIntent().getExtras().getInt("Dificultad");

        //LLAMAMOS A LA BASE DE DATOS
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
        SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("corazones", Vidas_restantes);
        dbwrite.update("usuarios", values, "id = 1", null);

        Button sig = findViewById(R.id.buttonsiguiente);
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}