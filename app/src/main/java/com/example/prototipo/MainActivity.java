package com.example.prototipo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.prototipo.ServicioTimer;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_SHORT;
import static java.lang.Thread.sleep;


/*
NOTAS
*CONFIGURAR TIENDA
*HACER LOS CORAZONES RECARGABLES
*HACER BASE DE DATOS DE ESTADISTICAS
*GUARDAS ESTADISTICAS CON CADA JUEGO

*/
public class MainActivity extends AppCompatActivity {
    //Servicio de Cronometro
    //OOLEAN DE LOS FRAGMENT ACTIVOS
    public static Fragment fragment = null;
    public static Boolean isFragmentGames;
    public static Boolean isFragmentPerfil;
    public static Boolean isFragmentTienda;
    public static Intent cronoService;
    //Menu Inferior Bottom Nav Menu
    private BottomNavigationView menu_inf;
    //Boton Superior Izquierda

    public static LinearLayout Btn_logo;
    public static LinearLayout Btn_Money;
    public static LinearLayout Btn_Lives;
    public static LinearLayout Btn_Objetos;
    //Variables auxiliares de los corazones
    public static ImageView corazones_icon;
    public static TextView corazones_anim;
    public static TextView corazones_crono;
    //Variable de los Corazones, Del TextView del Toolbar, (Se utiliza para conectar con el Fragment)
    public static TextView corazones_tv;
    public static String corazones;
    //Demas Objetos
    public static TextView cofres_tv;
    public static TextView llaves_tv;
    public static String cofres;
    public static String llaves;
    //Variable para saber si esta activo el main
    public static Boolean isthisactive;
    //Variable de las Monedas, Del TextView del Toolbar, (Se utiliza para conectar con el Fragment)
    public static TextView monedas_tv;
    public static String monedas;
    //Condicional del powerUp
    public static int isPowerActive;
    //Auxiliar del powerUP
    public static String Time_Aux_powerUp;
    //Variable del nomre de usuario
    public static String nombre;
    static SoundPool sound = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        //SoundPool, Se estalece para manejar los sonidos de la aplicacion

        //Cargamos el sonido

        //notificamos que la ventana esta abierta
        isthisactive = true;
        //ESTABLECEMOS LA ORIENTACION DEL PROGRAMA A VERTICAL
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //CARGAMOS LA BASE DE DATOS
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();
        SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();

        //Primero comproamos si ya hay algun usuario, de no ser asi, creamos uno
        if (dbread != null) {
            String sql = "SELECT * FROM usuarios";
            Cursor c = dbread.rawQuery(sql, null);
            //Si encontro, recogemos los datos
            if (c.moveToNext()) {
                nombre = c.getString(c.getColumnIndex("nombre"));
                monedas = c.getString(c.getColumnIndex("monedas"));
                corazones = c.getString(c.getColumnIndex("corazones"));
                llaves = c.getString(c.getColumnIndex("llaves"));
                cofres = c.getString(c.getColumnIndex("cofres"));
                isPowerActive = c.getInt(c.getColumnIndex("power"));
                Time_Aux_powerUp = c.getString(c.getColumnIndex("time_aux_powerUp"));


            }
            //Si no, Creamos un usuarion Nuevo
            else {
                //METODO PARA CREAR UN NUEVO USUARIO
                //Creamos el Registro de usuario y los logros
                ContentValues values = new ContentValues();
                values.put("nombre", "Usuario");
                values.put("monedas", "20000");
                values.put("corazones", "3");
                values.put("llaves", "0");
                values.put("cofres", "0");
                values.put("power", "0");
                dbwrite.insert("usuarios", null, values);
                //Logros
                ContentValues values2 = new ContentValues();
                values2.put("fiebreoro", 0);
                values2.put("matematicoprincipiante", 0);
                values2.put("matematicoexperto", 0);
                dbwrite.insert("logros", null, values2);

                //Logros
                ContentValues values3 = new ContentValues();
                values3.put("desbloqueable1", 0);
                values3.put("desbloqueable2", 0);
                dbwrite.insert("niveles", null, values3);

                //Ahora recogemos los datos
                String sql2 = "SELECT * FROM usuarios";
                Cursor c2 = dbread.rawQuery(sql2, null);
                if (c2.moveToNext()) {
                    nombre = c2.getString(c.getColumnIndex("nombre"));
                    monedas = c2.getString(c.getColumnIndex("monedas"));
                    corazones = c2.getString(c.getColumnIndex("corazones"));
                    llaves = c2.getString(c.getColumnIndex("llaves"));
                    cofres = c2.getString(c.getColumnIndex("cofres"));
                    isPowerActive = c2.getInt(c.getColumnIndex("power"));
                }

            }
            //Cerramos la Conexion
            adminSQLiteOpenHelper.close();
        }


        //CREAMOS EL ACTIVITY
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Corazones ImageView
        corazones_icon = findViewById(R.id.vida_icon);
        //Enlazamos las variables auxiliares
        corazones_anim = findViewById(R.id.vidas_anim);
        corazones_anim.setVisibility(View.GONE);

        corazones_crono = findViewById(R.id.vidas_crono);
        corazones_crono.setVisibility(View.GONE);
        //Enlazamos el TV con la variable
        corazones_tv = findViewById(R.id.vidas_text);
        corazones_tv.setText(String.valueOf(corazones));
        //Enlazamos el TV con la variable
        monedas_tv = findViewById(R.id.monedas_text);
        monedas_tv.setText(String.valueOf(monedas));

        cofres_tv = findViewById(R.id.cofres_text);
        cofres_tv.setText(cofres);

        llaves_tv  = findViewById(R.id.llaves_text);
        llaves_tv.setText(llaves);

        //SI HAY LLAVES O COFRES LO MOSTRAMOS DE LO CONTRARIO LO OCULTAMOS
        Btn_Objetos = findViewById(R.id.CyLBoton);

        if(Integer.parseInt(cofres) > 0 || Integer.parseInt(llaves) > 0){
            Btn_Objetos.setVisibility(View.VISIBLE);
        }else{
            Btn_Objetos.setVisibility(View.GONE);
        }


        //Comprobamos si hay powerUp para establecer el cronometro de acuerdo a la hora recolectada, cuando se establecio, si no establecemos
        ///la app sin el power up
        if(isPowerActive == 1) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a", Locale.ENGLISH);
            String currentTime = simpleDateFormat.format(new Date());
            //Declaramos las fechas
            Date dateold = null;
            Date dateNew = null;
            try {
                dateold = simpleDateFormat.parse(Time_Aux_powerUp);
                dateNew = simpleDateFormat.parse(currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Calculamos la diferencia en milisegundos
            long Millis = dateNew.getTime() - dateold.getTime();
            long Seconds = Millis / 1000;
            int diff = Integer.parseInt(String.valueOf(300 - Seconds));

            if(diff > 0){
                //Si no ponemos lo que falta
                if(isCronoRunning(ServicioTimer.class, MainActivity.this)){

                }else{
                    cronoService = new Intent(MainActivity.this, ServicioTimer.class);
                    cronoService.putExtra("CronoTime", diff);
                    startService(cronoService);
                }

            }else{
               powerUpInfiniteLives(false,this);
            }
        }

        //Comprobramos si hay menos corazones en ese caso llamamos el servicio
        if (Integer.parseInt(corazones) < 3 && isPowerActive != 1){
            //Comprobamos que el servicion no se este ejecutando
            if(isCronoRunning(ServicioTimer.class, MainActivity.this)){
                //We does nothing
            }
            else{
                //Iniciamos el crono
                cronoService = new Intent(MainActivity.this, ServicioTimer.class);
                cronoService.putExtra("CronoTime", 120);
                startService(cronoService);
            }
        }

        //Boton Menu
        //Enlazamos el boton del menu
        Btn_logo = findViewById(R.id.menuBtn);
        Btn_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MOSTRAR UN DIALOGO PARA CONFIRMAR LA COMPRA
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_menu, null);
                alert.setView(v);
                //CREAMOS EL DIALOGO
                alert.create();
                final AlertDialog mDialog = alert.show();

                //Botones
                Button btnsi = v.findViewById(R.id.btnAcercade);
                btnsi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        //Abrimos el otro dialogo
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.dialog_creditos, null);
                        alert.setView(v);
                        //CREAMOS EL DIALOGO
                        alert.create();
                        final AlertDialog mDialog2 = alert.show();
                        //Boton de salida del dialogo de creditos
                        ImageButton btn_exit = v.findViewById(R.id.Exitbutton);
                        btn_exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialog2.dismiss();
                            }
                        });
                    }
                });

                Button btnno = v.findViewById(R.id.btnTutorial);
                btnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mDialog.dismiss();
                        //Abrimos el otro dialogo
                        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                        //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.dialog_howtoplay, null);
                        alert.setView(v);
                        //CREAMOS EL DIALOGO
                        alert.create();
                        final AlertDialog mDialog2 = alert.show();
                        //Boton de salida del dialogo de creditos
                        ImageButton btn_exit = v.findViewById(R.id.Exitbutton);
                        btn_exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialog2.dismiss();
                            }
                        });
                    }
                });
            }
        });

        //Boton Cofres
        //Enlazamos el boton del menu
        Btn_Objetos = findViewById(R.id.CyLBoton);
        Btn_Objetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MOSTRAR UN DIALOGO PARA CONFIRMAR LA COMPRA
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_objetos_menu, null);
                alert.setView(v);
                //CREAMOS EL DIALOGO
                alert.create();
                final AlertDialog mDialog = alert.show();
                //Seteamos las cantidades de los obetos
                TextView q_llaves = v.findViewById(R.id.quantityofproduct_LLAVES);
                q_llaves.setText(llaves);

                TextView q_cofres = v.findViewById(R.id.quantityofproduct_COFRE);
                q_cofres.setText(cofres);


                //Botones
                Button btnAbrirCofre = v.findViewById(R.id.btnAbrirCofre);
                btnAbrirCofre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        //Solo se ejecuta cuando hay mas de una llave y mas de un cofre
                        if(Integer.parseInt(llaves) > 0 && Integer.parseInt(cofres) > 0){
                            abrirCofre(MainActivity.this, MainActivity.this);
                        }else{
                            //Mostramos un dialogo que no tiene cofres y llaves suficientes
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                            LayoutInflater inflater = getLayoutInflater();
                            View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                            alert.setView(v);
                            //CREAMOS EL DIALOGO
                            alert.create();
                            final AlertDialog mDialog = alert.show();
                            TextView tv1 = v.findViewById(R.id.tv1_dialog_ok);
                            tv1.setText("Debes tener al menos 1 de cada uno para completar la operación");

                            //Boton Ok
                            Button btnOk = v.findViewById(R.id.btnOk);
                            btnOk.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDialog.dismiss();
                                }
                            });
                        }

                    }
                });

                Button btnRegresar = v.findViewById(R.id.btnRegresar);
                btnRegresar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mDialog.dismiss();
                    }
                });
            }
        });

        //MoneyBtn
        Btn_Money = findViewById(R.id.MoneyBtn);
        Btn_Money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialogo de detalles para mostrar la cantidad de las monedas que posee el usuario
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_details_item, null);
                alert.setView(v);
                //CREAMOS EL DIALOGO
                alert.create();
                final AlertDialog mDialog = alert.show();
                //Modificamos la cantidad de acuerdo a la cantidad de monedas que posee el usuario actualmente
                String monedasAct = String.valueOf(monedas_tv.getText());
                //String 1, texto :"Tienes"
                TextView s1 = v.findViewById(R.id.s1);
                //Color amarillo
                TypedValue value = new TypedValue();
                getResources().getValue(R.color.money, value, true);
                s1.setTextColor(getResources().getColor(value.resourceId));
                //Cantidad del producto
                TextView Monedas_dialog = v.findViewById(R.id.quantityofproduct);
                Monedas_dialog.setText(monedasAct);
                //Color amarillo
                getResources().getValue(R.color.money, value, true);
                Monedas_dialog.setTextColor(getResources().getColor(value.resourceId));
                //Establecemos la imagen de la moneda en el dialogo
                ImageView image_Product_dialog = v.findViewById(R.id.imageofproduct);

                getResources().getValue(R.drawable.ic_money, value, true);
                image_Product_dialog.setImageDrawable(getResources().getDrawable(value.resourceId));

                //Boton de ok
                Button btn_ok = v.findViewById(R.id.btnOk);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
            }
        });

        //Lives Btn
        //MoneyBtn
        Btn_Lives = findViewById(R.id.VidasBtn);
        Btn_Lives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialogo de detalles para mostrar la cantidad de las monedas que posee el usuario
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_details_item, null);
                alert.setView(v);
                //CREAMOS EL DIALOGO
                alert.create();
                final AlertDialog mDialog = alert.show();
                //Dialogo con Power UP
                if(isPowerActive == 1){
                    //String 1, texto :"Tienes"
                    TextView s1 = v.findViewById(R.id.s1);
                    //Color azul del power UP
                    TypedValue value = new TypedValue();
                    getResources().getValue(R.color.colorPrimary, value, true);
                    s1.setTextColor(getResources().getColor(value.resourceId));
                    //Cantidad del producto - Infinitas Vidas
                    TextView Corazones_dialog = v.findViewById(R.id.quantityofproduct);
                    Corazones_dialog.setText("∞");
                    //Color amarillo
                    getResources().getValue(R.color.colorPrimary, value, true);
                    Corazones_dialog.setTextColor(getResources().getColor(value.resourceId));
                    //Establecemos la imagen de la moneda en el dialogo
                    ImageView image_Product_dialog = v.findViewById(R.id.imageofproduct);

                    getResources().getValue(R.drawable.ic_heart_blue, value, true);
                    image_Product_dialog.setImageDrawable(getResources().getDrawable(value.resourceId));
                }else {
                //Modificamos la cantidad de acuerdo a la cantidad de corazones que posee el usuario actualmente
                String corazonesAct = String.valueOf(corazones_tv.getText());
                //String 1, texto :"Tienes"
                TextView s1 = v.findViewById(R.id.s1);
                //Color rojo
                TypedValue value = new TypedValue();
                getResources().getValue(R.color.lives, value, true);
                s1.setTextColor(getResources().getColor(value.resourceId));
                //Cantidad del producto
                TextView Corazones_dialog = v.findViewById(R.id.quantityofproduct);
                Corazones_dialog.setText(corazonesAct);
                //Color rojo a la cantidad
                getResources().getValue(R.color.lives, value, true);
                Corazones_dialog.setTextColor(getResources().getColor(value.resourceId));
                //Establecemos la imagen de la moneda en el dialogo
                ImageView image_Product_dialog = v.findViewById(R.id.imageofproduct);

                getResources().getValue(R.drawable.ic_heart, value, true);
                image_Product_dialog.setImageDrawable(getResources().getDrawable(value.resourceId));
                }
                //Boton de ok
                Button btn_ok = v.findViewById(R.id.btnOk);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });

            }
        });

        //Bottom Nav Menu, Enlazamos con la variable
        menu_inf = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.containermain, new Games_Fragment()).commit();
        //Listener para escuchar, al presionar uno de los elementos, cambiar el fragment
        menu_inf.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.games:
                        fragment = new Games_Fragment();
                        isFragmentGames = true;
                        isFragmentPerfil = false;
                        isFragmentTienda = false;
                        break;

                    case R.id.perfil:
                        fragment = new Perfil_Fragment();
                        isFragmentGames = false;
                        isFragmentPerfil = true;
                        isFragmentTienda = false;
                        break;

                    case R.id.tienda:
                        fragment = new Shop_Fragment();
                        isFragmentGames = false;
                        isFragmentPerfil = false;
                        isFragmentTienda = true;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.containermain, fragment).commit();


                return true;
            }
        });


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //Mostramos el Cronometro
                corazones_crono.setVisibility(View.VISIBLE);
                Integer tiempo = intent.getIntExtra("TimeR", 0);

                //FORMATEAMOS EL CRONOMETRO
                String timeFormatted = String.format("%02d:%02d",
                        TimeUnit.SECONDS.toMinutes(tiempo) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(tiempo)),
                        TimeUnit.SECONDS.toSeconds(tiempo) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(tiempo)));
                corazones_crono.setText(String.valueOf(timeFormatted));


                if (intent.hasExtra("Fin")) {
                    //Buscamos en la base de datos si esta activo el powerUP en la base de datos
                    //CARGAMOS LA BASE DE DATOS
                    AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getApplicationContext());
                    SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();
                    SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();

                    if (dbread != null) {
                        String sql = "SELECT * FROM usuarios";
                        Cursor c = dbread.rawQuery(sql, null);
                        //Si encontro, recogemos los datos
                        if (c.moveToNext()) {
                            isPowerActive = c.getInt(c.getColumnIndex("power"));
                        }
                    }
                    if (isPowerActive == 1) {
                        powerUpInfiniteLives(false, MainActivity.this);
                    } else {

                        //SUMAMOS LA VIDA Y LLAMAMOS LA ANIMACION
                        corazones = String.valueOf(Integer.parseInt(corazones) + 1);
                        //CARGAMOS LA BASE DE DATOS

                        ContentValues values = new ContentValues();
                        values.put("corazones", corazones);
                        dbwrite.update("usuarios", values, "id = 1", null);
                        //Cerramos la Conexion
                        adminSQLiteOpenHelper.close();
                        //MOSTRAMOS LA ANIMACION Y EL SETEO DE LAS VIDAS SI LA ACTIVIDAD PRINCIPAL ESTA ACTIVA; EN CASO CONTRARIO SOLO SE TOMA DESDE LA BASE DE DATOS
                        if (isthisactive) {
                            //Animamos la vida añadida
                            AnimacionSumarVidas(1, MainActivity.this);
                        }
                        //COMPROBAMOS QUE LAS VIDAS ESTEN COMPLETAS
                        if (Integer.parseInt(corazones) >= 3) {
                            corazones_crono.setVisibility(View.GONE);
                            stopService(new Intent(MainActivity.this, ServicioTimer.class));
                        } else {
                            if (isthisactive) {
                                //Iniciamos el crono
                                cronoService = new Intent(MainActivity.this, ServicioTimer.class);
                                cronoService.putExtra("CronoTime", 120);
                                startService(cronoService);
                            } else {
                                stopService(new Intent(MainActivity.this, ServicioTimer.class));
                            }

                        }

                    }
                }
            }

            };
                registerReceiver(br,intentFilter);
        }






    @Override
    protected void onResume() {
        //notificamos que la ventana esta abierta
        isthisactive = true;
    //Llamamos a la base de datos y seteamos la info, Dentro del metodo onResume, para refrescar los datos desde la BDD

        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getApplicationContext());
        SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

        if (dbread != null) {
            String sql = "SELECT * FROM usuarios";
            Cursor c = dbread.rawQuery(sql, null);
            //Si encontro, recogemos los datos
            if (c.moveToNext()) {
                String nombre = c.getString(c.getColumnIndex("nombre"));
                monedas = c.getString(c.getColumnIndex("monedas"));
                corazones = c.getString(c.getColumnIndex("corazones"));
                isPowerActive = c.getInt(c.getColumnIndex("power"));
            }

            MainActivity.monedas_tv.setText(monedas);
            if(isPowerActive == 1) {
                corazones_tv.setText("∞");
                //Solo activamos la animacion
                TypedValue value = new TypedValue();
                getResources().getValue(R.color.colorPrimary, value, true);
                corazones_crono.setTextColor(getResources().getColor(value.resourceId));
                //Color azul del power UP //Cantidad
                getResources().getValue(R.color.colorPrimary, value, true);
                corazones_tv.setTextColor(getResources().getColor(value.resourceId));
                //Establecemos la imagen del corazon en el main
                getResources().getValue(R.drawable.ic_heart_blue, value, true);
                corazones_icon.setImageDrawable(getResources().getDrawable(value.resourceId));


            }else{
                MainActivity.corazones_tv.setText(corazones);
            }
            //Comprobramos si hay menos corazones en ese caso llamamos el servicio
            if (Integer.parseInt(corazones) < 3 && isPowerActive != 1){
                //Comprobamos que el servicio no se este ejecutando
                if(isCronoRunning(ServicioTimer.class, MainActivity.this)){
                    //We does nothing
                }
                else{
                    //Iniciamos el crono
                    cronoService = new Intent(MainActivity.this, ServicioTimer.class);
                    cronoService.putExtra("CronoTime", 120);
                    startService(cronoService);
                }
            }

            //Cerramos la Conexion
            adminSQLiteOpenHelper.close();
            super.onResume();

        }
    }

    @Override
    protected void onPause() {
        isthisactive = false;
        super.onPause();
    }

    public static void AnimacionSumarVidas(int cantidad, Context c){

        int sonido_vida_oneUp = sound.load(c, R.raw.vida_one_up, 1);
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sound.play(sonido_vida_oneUp, 1, 1, 0, 0, 1);

        //Seteamos en la caja de texto
        corazones_tv.setText(corazones);

        //Caja de texto de la animacion

        corazones_anim.setText("+"+cantidad);

        Animation animation = AnimationUtils.loadAnimation(c, R.anim.plus_or_minus_value);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                corazones_anim.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        corazones_anim.setVisibility(View.VISIBLE);
        corazones_anim.startAnimation(animation);
        //VISIBLE EL TEXTO, Y GONE CUANDO TERMINE LA ANIMACION
    }

    public static void powerUpInfiniteLives(Boolean activar, Context c){
        if(activar){
            //Activar PowerUp
            //Play al sonido
            int sonido_vida_oneUp = sound.load(c, R.raw.power_up, 1);
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sound.play(sonido_vida_oneUp, 1, 1, 0, 0, 1);


            //Iniciarmos el servicio
            Intent i = new Intent(c, ServicioTimer.class);
            i.putExtra("CronoTime", 300);
            c.startService(i);

        //Color azul del power UP //Cronometro
        TypedValue value = new TypedValue();
        c.getResources().getValue(R.color.colorPrimary, value, true);
        corazones_crono.setTextColor(c.getResources().getColor(value.resourceId));
        //Color azul del power UP //Cantidad
         c.getResources().getValue(R.color.colorPrimary, value, true);
         corazones_tv.setTextColor(c.getResources().getColor(value.resourceId));
         corazones_tv.setText("∞");
        //Establecemos la imagen del corazon en el main
        c.getResources().getValue(R.drawable.ic_heart_blue, value, true);
        corazones_icon.setImageDrawable(c.getResources().getDrawable(value.resourceId));
        //CARGAMOS LA BASE DE DATOS
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(c);
        SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        isPowerActive = 1;
        //Recojemos la fecha para usarla despues
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss a", Locale.ENGLISH);
            Time_Aux_powerUp = simpleDateFormat.format(new Date());


        values.put("power", "1");
        values.put("time_aux_powerUp", Time_Aux_powerUp);
        dbwrite.update("usuarios", values, "id = 1", null);
        //Cerramos la Conexion
        adminSQLiteOpenHelper.close();
        }else{
            //Desactivar PowerUp
            // Desactivamos el reloj
            c.stopService(new Intent(c, ServicioTimer.class));
            //Ocultamos el cronometro
            corazones_crono.setVisibility(View.GONE);
            //Color rojo Default
            //Cronometro
            TypedValue value = new TypedValue();
            c.getResources().getValue(R.color.lives, value, true);
            corazones_crono.setTextColor(c.getResources().getColor(value.resourceId));
            //Cantidad
            c.getResources().getValue(R.color.lives, value, true);
            corazones_tv.setTextColor(c.getResources().getColor(value.resourceId));
            //Establecemos la imagen del corazon default
            c.getResources().getValue(R.drawable.ic_heart, value, true);
            corazones_icon.setImageDrawable(c.getResources().getDrawable(value.resourceId));
            //CARGAMOS LA BASE DE DATOS
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(c);
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            //Establecemos los corazones y el power en false
            corazones_tv.setText(String.valueOf(corazones));
            isPowerActive = 0;
            //Quitamos el power de la base de datos
            values.put("power", "0");
            values.put("corazones", corazones);
            dbwrite.update("usuarios", values, "id = 1", null);
            //Cerramos la Conexion
            adminSQLiteOpenHelper.close();
        }
    }


    public static boolean isCronoRunning(Class <?> ServicioTimer, Context c){
        ActivityManager manager = (ActivityManager) c.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if (ServicioTimer.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

    public static void abrirCofre(final Context c, Activity a){
        //Dialogo para mostrar luego del efecto con lo que le haya tocado al usuario
        final AlertDialog.Builder Obtainedalert = new AlertDialog.Builder(c);
        final AlertDialog[] ObtainedDialog = new AlertDialog[1];
        //Variable para saber cual toco
        int OpcionObtenida = 0; // 1 vidas, 2 monedas, 3 nuevo nivel
        //Se muestre un Sonido abriendo el cofre o una animacion
        //Luego de eso, me aparezca un dialogo con lo obtenido
        //Añadiendo el premio y restando el cofre y la llave
        //RAMDOM
        Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
        int numero = nroAleatorio.nextInt(100)+1;
        System.out.println(numero);

        if(numero > 0 && numero <= 50){
            //+10 Vidas
            OpcionObtenida = 1;
            //CARGAMOS LA BASE DE DATOS
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(c);
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            //Añadimos las vidas a la base de datos y restamos el cofre y la llave
            corazones = String.valueOf(Integer.parseInt(corazones) + 10);
            cofres = String.valueOf(Integer.parseInt(cofres) - 1);
            llaves = String.valueOf(Integer.parseInt(llaves) - 1);
            values.put("corazones", corazones);
            values.put("cofres", cofres);
            values.put("llaves", llaves);
            dbwrite.update("usuarios", values, "id = 1", null);
            //Cerramos la Conexion
            adminSQLiteOpenHelper.close();
            //CREAMOS EL DIALOGO PERO NO LO MOSTRAMOS
            //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
            LayoutInflater inflater = a.getLayoutInflater();
            View v = inflater.inflate(R.layout.dialog_inform_purchased, null);
            Obtainedalert.setView(v);
            Obtainedalert.create();
            //Texto
            TextView tv1 = v.findViewById(R.id.tv1_dialog_ok);
            tv1.setText("Has Obtenido 10");
            //Miniatura
            ImageView imageView = v.findViewById(R.id.imageofproduct);
            TypedValue value = new TypedValue();
            c.getResources().getValue(R.drawable.ic_heart, value, true);
            imageView.setImageDrawable(c.getResources().getDrawable(value.resourceId));
            //Boton de ok para cerrar el dialogo
            Button btnok = v.findViewById(R.id.btnOk);
            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ObtainedDialog[0].dismiss();
                }
            });
        }else if(numero > 50 && numero <= 85){
            //+200 Monedas
            OpcionObtenida = 2;
            //CARGAMOS LA BASE DE DATOS
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(c);
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            //Añadimos las monedas a la base de datos y restamos el cofre y la llave
            monedas = String.valueOf(Integer.parseInt(monedas) + 200);
            cofres = String.valueOf(Integer.parseInt(cofres) - 1);
            llaves = String.valueOf(Integer.parseInt(llaves) - 1);
            values.put("monedas", monedas);
            values.put("cofres", cofres);
            values.put("llaves", llaves);
            dbwrite.update("usuarios", values, "id = 1", null);
            //Cerramos la Conexion
            adminSQLiteOpenHelper.close();
            //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
            LayoutInflater inflater = a.getLayoutInflater();
            View v = inflater.inflate(R.layout.dialog_inform_purchased, null);
            Obtainedalert.setView(v);
            Obtainedalert.create();
            //Texto
            TextView tv1 = v.findViewById(R.id.tv1_dialog_ok);
            tv1.setText("Has Obtenido 200");
            //Miniatura
            ImageView imageView = v.findViewById(R.id.imageofproduct);
            TypedValue value = new TypedValue();
            c.getResources().getValue(R.drawable.ic_money, value, true);
            imageView.setImageDrawable(c.getResources().getDrawable(value.resourceId));

            //Boton de ok para cerrar el dialogo
            Button btnok = v.findViewById(R.id.btnOk);
            btnok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ObtainedDialog[0].dismiss();
                }
            });

        }else if(numero > 85){
            //OpcionObtenida = 3;
            //CARGAMOS LA BASE DE DATOS
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(c);
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

            String sql = "SELECT * FROM niveles";
            Cursor c3 = dbread.rawQuery(sql, null);
            int isDesbloqueable1 = 0;
            int isDesbloqueable2 = 0;
            if (c3.moveToNext()) {
                isDesbloqueable1 = c3.getInt(c3.getColumnIndex("desbloqueable1"));
                isDesbloqueable2 = c3.getInt(c3.getColumnIndex("desbloqueable2"));
            }

            if (isDesbloqueable1 == 1 && isDesbloqueable2 == 1){
                //GANA 1000 MONEDAS
                //CARGAMOS LA BASE DE DATOS
                ContentValues values = new ContentValues();
                //Añadimos las monedas a la base de datos y restamos el cofre y la llave
                monedas = String.valueOf(Integer.parseInt(monedas) + 1000);
                cofres = String.valueOf(Integer.parseInt(cofres) - 1);
                llaves = String.valueOf(Integer.parseInt(llaves) - 1);
                values.put("monedas", monedas);
                values.put("cofres", cofres);
                values.put("llaves", llaves);
                dbwrite.update("usuarios", values, "id = 1", null);
                //Cerramos la Conexion
                adminSQLiteOpenHelper.close();
                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = a.getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_inform_purchased, null);
                Obtainedalert.setView(v);
                Obtainedalert.create();
                //Texto
                TextView tv1 = v.findViewById(R.id.tv1_dialog_ok);
                tv1.setText("Has Obtenido 1000");
                //Miniatura
                ImageView imageView = v.findViewById(R.id.imageofproduct);
                TypedValue value = new TypedValue();
                c.getResources().getValue(R.drawable.ic_money, value, true);
                imageView.setImageDrawable(c.getResources().getDrawable(value.resourceId));

                //Boton de ok para cerrar el dialogo
                Button btnok = v.findViewById(R.id.btnOk);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ObtainedDialog[0].dismiss();
                    }
                });

            }else{
                //ENTONCES FALTA 1 POR DESBLOQUEAR
                ContentValues values = new ContentValues();
                //Ponemos que se ha desbloqueado en la BDD

                if (isDesbloqueable1 == 0){
                    values.put("desbloqueable1", 1);
                    if(isFragmentGames){
                        View v_fragment = fragment.getView();
                        TypedValue valueR = new TypedValue();
                            ImageButton Btn_E_1 = v_fragment.findViewById(R.id.btn_extra1);
                            v_fragment.getResources().getValue(R.color.desbloqueable1, valueR, true);
                            Btn_E_1.setBackgroundColor(v_fragment.getResources().getColor(valueR.resourceId));
                            Btn_E_1.setImageResource(R.drawable.ic_maths_desbloqueable1);


                    }
                }else if(isDesbloqueable2 == 0){
                    values.put("desbloqueable2", 1);

                    //COMProamos que este en la pantalla del fragnmt

                    if(isFragmentGames){
                        View v_fragment = fragment.getView();
                        TypedValue valueR = new TypedValue();
                        ImageButton Btn_E_2 = v_fragment.findViewById(R.id.btn_extra2);
                        v_fragment.getResources().getValue(R.color.colorAccent, valueR, true);
                        Btn_E_2.setBackgroundColor(v_fragment.getResources().getColor(valueR.resourceId));
                        Btn_E_2.setImageResource(R.drawable.ic_maths_desbloqueable1);
                    }

                }

                dbwrite.update("niveles", values, "id = 1", null);

                ContentValues values2 = new ContentValues();
                //Añadimos las monedas a la base de datos y restamos el cofre y la llave
                cofres = String.valueOf(Integer.parseInt(cofres) - 1);
                llaves = String.valueOf(Integer.parseInt(llaves) - 1);
                values2.put("cofres", cofres);
                values2.put("llaves", llaves);
                dbwrite.update("usuarios", values2, "id = 1", null);

                //Cerramos la Conexion
                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = a.getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_inform_purchased, null);
                Obtainedalert.setView(v);
                Obtainedalert.create();
                //Texto
                TextView tv1 = v.findViewById(R.id.tv1_dialog_ok);
                tv1.setText("¡Has Desbloqueado un Nivel!");
                //Miniatura
                ImageView imageView = v.findViewById(R.id.imageofproduct);
                TypedValue value = new TypedValue();
                c.getResources().getValue(R.drawable.ic_maths, value, true);
                imageView.setImageDrawable(c.getResources().getDrawable(value.resourceId));
                //VERIFICAMOS CUAL FUE EL QUE SE ACTIVO

                //Boton de ok para cerrar el dialogo
                Button btnok = v.findViewById(R.id.btnOk);
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ObtainedDialog[0].dismiss();
                    }
                });

            }

        }
        //Mostramos el dialogo
        //EL proceso para abrir el cofre
        AlertDialog.Builder alert = new AlertDialog.Builder(c);
        //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
        LayoutInflater inflater = a.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_open_chest_process, null);
        alert.setView(v);
        alert.create();
        final AlertDialog mDialog = alert.show();
        mDialog.setCancelable(false);

        MediaPlayer mp = MediaPlayer.create(c, R.raw.open_chest);
        mp.start();

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if(isthisactive){
                    mDialog.dismiss();
                    //Activamos el otro dialogo
                    ObtainedDialog[0] = Obtainedalert.show();
                    MediaPlayer mp = MediaPlayer.create(c, R.raw.new_lvl);
                    mp.start();
                    //Seteamos la inf
                    cofres_tv.setText(cofres);
                    llaves_tv.setText(llaves);
                    monedas_tv.setText(monedas);
                    if(isPowerActive == 0) {
                        corazones_tv.setText(corazones);
                    }

                }
            }
        });

    }


}
