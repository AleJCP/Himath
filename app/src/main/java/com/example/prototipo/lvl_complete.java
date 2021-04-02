package com.example.prototipo;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.prototipo.MainActivity.monedas;

public class lvl_complete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lvl_complete);
        //INICIALIZAMOS LAS VARIABLES
        int monedas = 0;
        int monedas_ganadas = 0;

        //PROCESAMIENTO DE ESTADOISTICAS DESDE EL JUEGO
        //FECHA
        //HORA
        int isPower = getIntent().getExtras().getInt("isPowerUp");
        int Vidas_restantes = getIntent().getExtras().getInt("VidasRestantes");
        int Veces_Resp_Correct = getIntent().getExtras().getInt("Veces_Resp_Correct");
        int Veces_Resp_Incorrect = getIntent().getExtras().getInt("Veces_Resp_Incorrect");
        String TipodeJuego = getIntent().getExtras().getString("TipodeJuego");
        int dificultad = getIntent().getExtras().getInt("Dificultad");

        //LLAMAMOS A LA BASE DE DATOS
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
        SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

        //SUBIR ESTADISTICAS A LA BASE DE DATOS
        ContentValues values = new ContentValues();
        values.put("tipodejuego", TipodeJuego);
        values.put("dificultad", dificultad);
        values.put("corazones_rest", Vidas_restantes);
        values.put("resp_correct", Veces_Resp_Correct);
        values.put("resp_incorrect", Veces_Resp_Incorrect);
        dbwrite.insert("estadisticas", null, values);

        //SUMAMOS LAS MONEDAS; PRIMERO AGARRAMOS LAS MONEDAS DESDE LA BDD Y LUEGO LE SUMAMOS LO ESTIPULADO Y LO ACTUALIZAMOS DE NUEVO EN LA BDD
        String sql = "SELECT * FROM usuarios";
        Cursor c = dbread.rawQuery(sql, null);
        //Si encontro, recogemos los datos
        if (c.moveToNext()) {
            monedas = Integer.parseInt(c.getString(c.getColumnIndex("monedas")));
        }

        //Monedas ganadas segun la dificultad
        if (dificultad == 0) {
            monedas_ganadas = 10;
            monedas = monedas + 10;
        } else if (dificultad == 1) {
            monedas_ganadas = 20;
            monedas = monedas + 20;
        } else if (dificultad == 2) {
            monedas_ganadas = 40;
            monedas = monedas + 40;
        }

        ContentValues values2 = new ContentValues();
        values2.put("monedas", monedas);
        values2.put("corazones", Vidas_restantes);
        dbwrite.update("usuarios", values2, "id = 1", null);
        //Cerramos la conexion
        adminSQLiteOpenHelper.close();
        //Mostramos los datos en el activity de "Enhorabuena"
        //MONEDAS GANADAS
        TextView monedas_tv = findViewById(R.id.earned_money);
        monedas_tv.setText(String.valueOf("+" + monedas_ganadas));
        //VIDAS RESTANTES
            //Si hay Power Up Colocamos Infinito
        TextView Vidas_tv = findViewById(R.id.vidas_rest_tv);
        if(isPower == 1){
            Vidas_tv.setText(String.valueOf("∞"));
        }else{
            Vidas_tv.setText(String.valueOf(Vidas_restantes));
        }
        //RESP CORRECTAS
        TextView resp_c_tv = findViewById(R.id.resp_correctas_tv);
        resp_c_tv.setText(String.valueOf(Veces_Resp_Correct));
        //RESP INCORRECTAS
        TextView resp_i_tv = findViewById(R.id.resp_incorrectas_tv);
        resp_i_tv.setText(String.valueOf(Veces_Resp_Incorrect));
        //Iniciamos el boton de siguiente
        Button sig = findViewById(R.id.buttonsiguiente);
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Comprobamos si obtuvo un logro
                if(comprobarLogro("fiebreoro", lvl_complete.this)){
        //NOthing
                }else if(comprobarLogro("matematicoprincipiante", lvl_complete.this)) {

                }else if(comprobarLogro("matematicoexperto", lvl_complete.this)){

                }else{
                    finish();
                }



            }
        });
    }

    public Boolean comprobarLogro(String logro, Context context){
        //If para cada logro
        if(logro == "fiebreoro") {
            //Comprobamos en la base de datos
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getApplicationContext());
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

            String sql = "SELECT * FROM usuarios";
            Cursor c = dbread.rawQuery(sql, null);
            //Si encontro, recogemos los datos
            String monedasFromDB = "0";
            if (c.moveToNext()) {
                monedasFromDB = c.getString(c.getColumnIndex("monedas"));
            }

            //primero comprobamos que NO tenga ya el logro
            String sql2 = "SELECT * FROM logros";
            Cursor c2 = dbread.rawQuery(sql2, null);
            //Si encontro, recogemos los datos
            int isFiebreOro = 0;
            if (c2.moveToNext()) {
                isFiebreOro = c2.getInt(c2.getColumnIndex("fiebreoro"));
            }

            if (Integer.parseInt(monedasFromDB) > 499 && isFiebreOro == 0) {
                //Mostramos un dialogo que obtuvo el logro y lo guardamos en la base de datos,
                //Si no esta activo modificamos el registro
                    ContentValues values = new ContentValues();
                    //Añadimos el logro
                    isFiebreOro = 1;
                    values.put("fiebreoro", isFiebreOro);
                    dbwrite.update("logros", values, "id = 1", null);
                    //Añadimos las monedas
                    ContentValues values2 = new ContentValues();
                    monedas = String.valueOf(Integer.parseInt(monedasFromDB) + 100);
                    values2.put("monedas", monedas);
                    dbwrite.update("usuarios", values2, "id = 1", null);
                    //Mostramos el Dialogo para decirle que completo un logro y se gano 100 monedas
                    //Dialogo de detalles para mostrar la cantidad de las monedas que posee el usuario
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_inform_obtained_logro, null);
                    alert.setView(v);
                    //Modificamos el textview y la imagen
                    TextView tv1 = v.findViewById(R.id.tv1_dialog_logro);
                    tv1.setText("Fiebre de Oro");

                    ImageView imageView = v.findViewById(R.id.imageoflogro);
                    TypedValue value = new TypedValue();
                    getResources().getValue(R.drawable.ic_money_bag, value, true);
                    imageView.setImageDrawable(getResources().getDrawable(value.resourceId));
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    //Sonido
                    MediaPlayer mp = MediaPlayer.create(context, R.raw.new_lvl);
                    mp.start();
                    //Boton de ok
                    Button btn_ok = v.findViewById(R.id.btnOk);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });
                    adminSQLiteOpenHelper.close();
                    return true;
            }
            adminSQLiteOpenHelper.close();
            return false;

        }
        if(logro == "matematicoexperto"){
            //Comprobamos en la base de datos
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getApplicationContext());
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

            String sql = "SELECT COUNT(*) FROM estadisticas WHERE dificultad = 2";
            Cursor c = dbread.rawQuery(sql, null);
            //Si encontro, recogemos los datos
            int juegosTotalesDificil = 0;
            if (c.moveToFirst()) {
                juegosTotalesDificil = c.getInt(0);
            }
            System.out.println(juegosTotalesDificil);

            //primero comprobamos que NO tenga ya el logro
            String sql2 = "SELECT * FROM logros";
            Cursor c2 = dbread.rawQuery(sql2, null);
            //Si encontro, recogemos los datos
            int isMatematicoExp = 0;
            if (c2.moveToNext()) {
                isMatematicoExp = c2.getInt(c2.getColumnIndex("matematicoexperto"));
            }

            if (juegosTotalesDificil > 19 && isMatematicoExp == 0) {
                //Mostramos un dialogo que obtuvo el logro y lo guardamos en la base de datos,
                //Si no esta activo modificamos el registro
                ContentValues values = new ContentValues();
                //Añadimos el logro
                isMatematicoExp = 1;
                values.put("matematicoexperto", isMatematicoExp);
                dbwrite.update("logros", values, "id = 1", null);
                //Añadimos las monedas
                ContentValues values2 = new ContentValues();
                monedas = String.valueOf(Integer.parseInt(monedas) + 100);
                values2.put("monedas", monedas);
                dbwrite.update("usuarios", values2, "id = 1", null);
                //Mostramos el Dialogo para decirle que completo un logro y se gano 100 monedas
                //Dialogo de detalles para mostrar la cantidad de las monedas que posee el usuario
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_inform_obtained_logro, null);
                alert.setView(v);
                //Modificamos el textview y la imagen
                TextView tv1 = v.findViewById(R.id.tv1_dialog_logro);
                tv1.setText("Matematico Experto");

                ImageView imageView = v.findViewById(R.id.imageoflogro);
                TypedValue value = new TypedValue();
                getResources().getValue(R.drawable.ic_maths, value, true);
                imageView.setImageDrawable(getResources().getDrawable(value.resourceId));
                //CREAMOS EL DIALOGO
                alert.create();
                final AlertDialog mDialog = alert.show();
                //Sonido
                MediaPlayer mp = MediaPlayer.create(context, R.raw.new_lvl);
                mp.start();
                //Boton de ok
                Button btn_ok = v.findViewById(R.id.btnOk);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                adminSQLiteOpenHelper.close();
                return true;
            }
            adminSQLiteOpenHelper.close();
            return false;
        }
        if(logro == "matematicoprincipiante"){
            //Comprobamos en la base de datos
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getApplicationContext());
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

            String sql = "SELECT COUNT(*) FROM estadisticas";
            Cursor c = dbread.rawQuery(sql, null);
            //Si encontro, recogemos los datos
            int juegosTotales = 0;
            if (c.moveToFirst()) {
                juegosTotales = c.getInt(0);
            }

            //primero comprobamos que NO tenga ya el logro
            String sql2 = "SELECT * FROM logros";
            Cursor c2 = dbread.rawQuery(sql2, null);
            //Si encontro, recogemos los datos
            int isMatematicoPrinc = 0;
            if (c2.moveToNext()) {
                isMatematicoPrinc = c2.getInt(c2.getColumnIndex("matematicoprincipiante"));
            }

            if (juegosTotales > 19 && isMatematicoPrinc == 0) {
                //Mostramos un dialogo que obtuvo el logro y lo guardamos en la base de datos,
                //Si no esta activo modificamos el registro
                ContentValues values = new ContentValues();
                //Añadimos el logro
                isMatematicoPrinc = 1;
                values.put("matematicoprincipiante", isMatematicoPrinc);
                dbwrite.update("logros", values, "id = 1", null);
                //Añadimos las monedas
                ContentValues values2 = new ContentValues();
                monedas = String.valueOf(Integer.parseInt(monedas) + 100);
                values2.put("monedas", monedas);
                dbwrite.update("usuarios", values2, "id = 1", null);
                //Mostramos el Dialogo para decirle que completo un logro y se gano 100 monedas
                //Dialogo de detalles para mostrar la cantidad de las monedas que posee el usuario
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_inform_obtained_logro, null);
                alert.setView(v);
                //Modificamos el textview y la imagen
                TextView tv1 = v.findViewById(R.id.tv1_dialog_logro);
                tv1.setText("Matematico Principiante");

                ImageView imageView = v.findViewById(R.id.imageoflogro);
                TypedValue value = new TypedValue();
                getResources().getValue(R.drawable.ic_star, value, true);
                imageView.setImageDrawable(getResources().getDrawable(value.resourceId));
                //CREAMOS EL DIALOGO
                alert.create();
                final AlertDialog mDialog = alert.show();
                //Sonido
                MediaPlayer mp = MediaPlayer.create(context, R.raw.new_lvl);
                mp.start();
                //Boton de ok
                Button btn_ok = v.findViewById(R.id.btnOk);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                adminSQLiteOpenHelper.close();
                return true;
            }
            adminSQLiteOpenHelper.close();
            return false;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        if(comprobarLogro("fiebreoro", lvl_complete.this) || comprobarLogro("matematicoexperto", lvl_complete.this) || comprobarLogro("matematicoprincipiante", lvl_complete.this)){
            //NOthing
        }else{
            this.finish();
        }
    }
}