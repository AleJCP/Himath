package com.example.prototipo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.renderscript.Type;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.TypeVariable;
import java.util.Random;

import static java.lang.Thread.sleep;


public class juego extends AppCompatActivity {
    //ACTIVIDAD DEL JUEGO, CORAZON DE LA APLICACION

    //Declarando variables ---

    //Variables de Estadistica
    int Veces_Respuestas_Incorrectas = 0;
    int Veces_Respuestas_Correctas = 0;
    //Variable Dificultadad, para identificar que nivel selecciono el usuario
    int dificultad;
    //Variable TipodeJuego, Para identificar que juego selecciono el usuario
    String TipodeJuego;

    // Vidas del jugador
    int Vidas=0;
    //Variable PROGRESSBAR, Para mostrar el progreso del juego
    ProgressBar progresoingame;
    //Varieable del progreso
    public int progreso = 0;
    //Variale de progreso Maximo, Se define de acuerdo a la dificultdad, mientras mas dificil, mas preguntas debe responder el usuario
    int ProgresMax;
    //Variable Boton de salida, Es para cerrar el juego activo, se encuentra al lado de la progresbar en el XML
    ImageButton exitbtn;
    //Variable Boton Comprobar, Es para comprobar la respuesta del usuario, es un toggle button porque tiene dos estados, el primero para comprobar
    //y el segundo para ir a la siguiente pregunta.
    ToggleButton comprobar;

    //GRUPO DE BTNS, Opciones del juego
    ToggleButton opcion1;
    ToggleButton opcion2;
    ToggleButton opcion3;
    ToggleButton opcion4;
    ToggleButton opcion5;


//METODO CREATE, es para inflar el Layout y definir la logica del juego
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //SoundPool, Se estalece para manejar los sonidos de la aplicacion
        final SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        //Cargar Sonidos dentro del objeto SoundPool
        //Sonido de empezar el juego
        final int sonido_newGame = soundPool.load(this, R.raw.new_game, 1);
        //Sonido Nivel Completado
        final int sonido_lvlcomplete = soundPool.load(this, R.raw.lvl_completado, 1);
        //Sonido Respuesta Correcta (DUOLINGO SOUND)
        final int sonido_correct_duo = soundPool.load(this, R.raw.duolingo_correct, 1);
        //Sonido Respuesta Correcta (Sin Usar por ahora)
        final int sonido_correct = soundPool.load(this, R.raw.correct_answer, 1);
        //Sonido Nivel Perdido
        final int sonido_lvllose = soundPool.load(this, R.raw.lvl_lose, 1);
        //Sonido Respuesta InCorrecta
        final int sonido_incorrect = soundPool.load(this, R.raw.incorrect_answer, 1);
        //Esperamos para reproducir el sonido y continuar con la ejecucion del programa
        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        soundPool.play(sonido_newGame, 1, 1, 0, 0, 1);

        //Datos enviados por el usuario de acuerdo a su eleccion para jugar
        //Estos datos son enviados para el BackEnd de la aplicacion, dentro de la clase BackGroundGame
        //La dificultad, 0 Facil, 1 Normal, 2 Dificil
        dificultad = getIntent().getExtras().getInt("Dificultad");
        //Tipo de Juego, Hasta ahora solo hay Suma,
        TipodeJuego = getIntent().getExtras().getString("Tipo");
        //Recogemos las vidas
        Vidas = getIntent().getExtras().getInt("Vidas");

        final int F_IsPowerActive = getIntent().getExtras().getInt("powerUp");

        //De acuerdo a los datos enviados, Seteamos el juego
        BackgroundGame.SetGame(TipodeJuego, dificultad);

        //CREAMOS EL ACTIVITY
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //SETEAMOS LAS VIDAS si no hay power, en ese caso cambiamos el icono, el color y el texto del Textview
        if(F_IsPowerActive == 1){
            //cambiamos el icono
            ImageView icon_heart = findViewById(R.id.game_icon_heart);
            TypedValue value = new TypedValue();
            getResources().getValue(R.drawable.ic_heart_blue, value, true);
            icon_heart.setImageDrawable(getResources().getDrawable(value.resourceId));

            //cambiamos el color
            TextView hearts = findViewById(R.id.count_corazones);
            getResources().getValue(R.color.colorPrimary, value, true);
            hearts.setTextColor(getResources().getColor(value.resourceId));
            //texto
            hearts.setText("∞");

        }else{
            TextView hearts = findViewById(R.id.count_corazones);
            hearts.setText(String.valueOf(Vidas));
        }



        //Layout del Mensaje Emergente, De Respuesta Correcta o Incorrecta, Segun sea el caso
        final LinearLayout msg = (LinearLayout) findViewById(R.id.textlinear);
        msg.setVisibility(View.GONE); //El mensaje possee la Propiedad OCULTO por ahora, Se mostrara cuando sea conveniente
        //Texto del Mensaje Emergente, De Respuesta Correcta o Incorrecta, Segun sea el caso
        final TextView msgtext = (TextView) findViewById(R.id.textmessage);
        //El mensaje possee la Propiedad OCULTO por ahora, Se mostrara cuando sea conveniente
        msg.setVisibility(View.GONE);

        //EL mensaje dinamico de las vidas, CUANDO DISMINUYEN o AUMENTAN
        TextView dinamicmsg = findViewById(R.id.dinamicmsg_hearts);
        dinamicmsg.setVisibility(View.GONE); //oculto, para mostrarse cuando sea conveniente

        //BARRA DE PROGRESO
        progresoingame = (ProgressBar) findViewById(R.id.progressBargame);
        //De acuerdo a la dificultad, elegida, establecemos el maximo del juego
        //FACIL

        if (dificultad == 0) {
            ProgresMax = 8 * 100;
            progresoingame.setMax(ProgresMax);
        }
        //NORMAL
        if (dificultad == 1) {
            ProgresMax = 14 * 100;
            progresoingame.setMax(ProgresMax);
        }

        //DIFICIL
        if (dificultad == 2) {
            ProgresMax = 20 * 100;
            progresoingame.setMax(ProgresMax);
        }


        //Boton Comprobar
        comprobar = (ToggleButton) findViewById(R.id.buttonComprobar);
        comprobar.setChecked(false);
        //Al hacer Click...
        comprobar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
//COMPROARMOS EL ESTADO DEL BOTTON, UN ESTADO PARA COMPROBAR, Y EL OTRO PARA PASAR A LA SIGUIENTE PREGUNTA
                //AL PRESIONARSE LA PRIMERA VEZ SIEMPRE VA A SER TRUE ENTONCES,

                //EL BOTON TOGGLE TIENE DOS ESTADOS; TRUE Y FALSE, TAMBIEN ON u OFF
                //UN ESTADO (TRUE) SE UTILIZA PARA COMPROBAR SI LA RESPUESTA FUE CORRECTA,
                //Y EL OTRO (FALSE) PARA PASAR A LA Siguiente Actividad

                //Para mantener esos estados, para cada funcion, se hace uso de SetCheck para modificar el boton, y ejecutar uno o el otro

                //LA LOGICA ES QUE SI EL USUARIO APRIETA EL BOTON, Y NO SELECCIONA ALGUNA RESPUESTA, SETEAMOS EL BOTON DE NUEVO EN FALSO,
                //PARA APROVECHAR EL ESTADO DEL TOGGLEUTTON, AL MISMO TIEMPO LE MOSTRAMOS UN TOAST PARA QUE SELECCIONE UNA
                //En caso de que el usuario seleccione alguna respuesta correcta se comprueba si lo es o no, se pinta el boton
                //de acuerdo a la respuesta, y se establece su estado en "siguiente", al mismo tiempo estableciendo True el boton
                //para ejecutar el otro metodo (Setear Siguiente Juego), al hacer de nuevo click
                //METODO COMPROBAR
                //AL HACER CLICK
                if (comprobar.isChecked()){
                    //COMPROARMOS QUE HAYA ALGUNA OPCION SELECCIONADA, SI NO, Seteamos el BOton en False para no cambiar su estado
                    if (comprobar_seleccionado()){
                        //COMPROBAR LA RESPUESTA
                        //CORRECTA
                        if (comprobar(juego.this)) {

                            //SONIDO DE CORRECTO
                            soundPool.play(sonido_correct, 1, 1, 0, 0, 1);
                            //MENSAJE DINAMICO CORRECTO
                            Message(true);
                            //Ademas pintamos el Boton en verde
                            TypedValue value = new TypedValue();
                            getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                            comprobar.setBackground(getResources().getDrawable(value.resourceId));

                            //AGARRAMOS LAS VIDAS
                            if(F_IsPowerActive == 1){

                            }else {
                                TextView hearts = findViewById(R.id.count_corazones); //ENLACE AL TV
                                Vidas = Integer.parseInt(hearts.getText().toString());
                            }
                            //AUMENTAMOS EN UNO, LA ESTADISTICA DE REPUSTA CORRECtA
                            Veces_Respuestas_Correctas++;
                        }
                        //INCORRECTA
                        else {

                            //Sonido de Incorrecto
                            soundPool.play(sonido_incorrect, 1, 1, 0, 0, 1);
                            //MENSAJE DINAMICO INCORRECTO
                            Message(false);
                            //Fondo del boton comprobar en rojo
                            TypedValue value = new TypedValue();
                            getResources().getValue(R.drawable.bg_comprobar_incorrect, value, true);
                            comprobar.setBackground(getResources().getDrawable(value.resourceId));
                            //Restamos la Vida (Animacion)
                            //En caso de haer power NO lo hacemos
                            if(F_IsPowerActive == 1) {

                            }else{
                                DinamicVidas("restar", 1);
                                //AGARRAMOS LAS VIDAS
                                TextView hearts = findViewById(R.id.count_corazones); //ENLACE AL TextVIew
                                Vidas = Integer.parseInt(hearts.getText().toString());
                            }

                            //AUMENTAMOS EN UNO, LA ESTADISTICA DE REPUSTA INCORRECtA
                            Veces_Respuestas_Incorrectas++;
                        }
                        //Cambiamos el texto del boton comprobar a "Siguiente"
                        comprobar.setText("Siguiente");
                        comprobar.setTextOn("Siguiente");
                        comprobar.setTextOff("Siguiente");

                        //Desactivar Opciones, para luego de el usuario comprobar, no modificar mas nada
                        opcion1.setEnabled(false);
                        opcion2.setEnabled(false);
                        opcion3.setEnabled(false);
                        opcion4.setEnabled(false);
                        opcion5.setEnabled(false);

                        //AHORA EL BOTON ESTA EN TRUE, AL hacer click nuevamente, Se ejecutara el metodo de Sigueine juego

                    }else{
                        //Al no seleccionar alguna respuesta
                        //PARA NO CAMBIAR EL ESTADO DEL BOTON, es decir como si fuese su primer click
                        comprobar.setChecked(false);
                        Toast.makeText(juego.this,"Selecciona una respuesta porfavor", Toast.LENGTH_LONG).show();
                    }
                    //METODO DE SIGUIENTE JUEGO
                }else {
                    if (comprobar_seleccionado()) {
                        //VOLVER EL BOTON AL COLOR Y TEXTO DEFECTO
                        TypedValue value = new TypedValue();
                        getResources().getValue(R.drawable.bg_check_togglebtn, value, true);
                        comprobar.setBackground(getResources().getDrawable(value.resourceId));
                        //PINTAMOS EL PROBLEMA DE NUEVO EN AZUL
                        TextView problema = (TextView) findViewById(R.id.tvProblem);
                        getResources().getValue(R.color.colorPrimary, value, true);
                        problema.setTextColor(getResources().getColor(value.resourceId));

                        //para quitar el mensaje (CONSIDERAR HACER UNA ANIMACION)
                        msg.setVisibility(View.GONE);

                        //Establecemos el texto del boton a comprobar nuevamente
                        comprobar.setText("Comprobar");
                        comprobar.setTextOn("Comprobar");
                        comprobar.setTextOff("Comprobar");

                        //AUMENTAMOS EL PROGRESO Y LO SETEAMOS
                        //VARIALES PARA LA ANIM
                        int progresofrom = progreso * 100;
                        int progresoto = ++progreso * 100;
                        //Animacion de la progressbar
                        ProgressBarAnimation anim = new ProgressBarAnimation(progresoingame, progresofrom,progresoto);
                        anim.setDuration(1000);
                        progresoingame.startAnimation(anim);

                        //Activar Opciones de respuesta
                        opcion1.setEnabled(true);
                        opcion2.setEnabled(true);
                        opcion3.setEnabled(true);
                        opcion4.setEnabled(true);
                        opcion5.setEnabled(true);
                        //ANTES comprobamos si el usuario tiene vidas para continuar Si no
                        if(Vidas == 0){
                            //Sonido de nivel Fracasado
                            soundPool.play(sonido_lvllose, 1, 1, 0, 0, 1);
                            //estalecemos el progreso del juego en 0 nuevamente
                            ProgressBarAnimation anim1 = new ProgressBarAnimation(progresoingame, progresofrom,0);
                            anim.setDuration(1000);
                            progresoingame.startAnimation(anim1);
                            //Cerramos el juego
                            finish();
                            //Iniciamos el mensaje
                            Intent i = new Intent(juego.this, lvl_incomplete.class);
                            //ENVIAR ESTADISTICAS PARA SU PROCESAMIENTO
                            i.putExtra("Veces_Resp_Correct", Veces_Respuestas_Correctas);
                            i.putExtra("Veces_Resp_Incorrect", Veces_Respuestas_Incorrectas);
                            i.putExtra("TipodeJuego", TipodeJuego);
                            i.putExtra("VidasRestantes", Vidas);
                            i.putExtra("dificultad", dificultad);
                            startActivity(i);

                        }
                        //Comprobarmos si ya el usuario ha llegado al maximo, si es asi, cerramos el juego y mostramos un mensaje de FILICITACIONES
                        else if (progresoto == ProgresMax && Vidas >= 1) {
                            //Sonido de nivel completado
                            soundPool.play(sonido_lvlcomplete, 1, 1, 0, 0, 1);
                            //estalecemos el progreso del juego en 0 nuevamente
                            ProgressBarAnimation anim1 = new ProgressBarAnimation(progresoingame, progresofrom,0);
                            anim.setDuration(1000);
                            progresoingame.startAnimation(anim1);
                            //Cerramos el juego
                            finish();
                            //Iniciamos el mensaje
                            Intent i = new Intent(juego.this, lvl_complete.class);
                            //ENVIAR ESTADISTICAS PARA SU PROCESAMIENTO
                            i.putExtra("Veces_Resp_Correct", Veces_Respuestas_Correctas);
                            i.putExtra("Veces_Resp_Incorrect", Veces_Respuestas_Incorrectas);
                            i.putExtra("TipodeJuego", TipodeJuego);
                            i.putExtra("VidasRestantes", Vidas);
                            i.putExtra("isPowerUp", F_IsPowerActive);
                            i.putExtra("Dificultad", dificultad);
                            startActivity(i);
                        } else {
                            //SI NO LLamamos a la funcion para Settear Nuevamente Otro juego
                            nextGame();
                        }
                    }
                }
            }
        }); //ESTE QUE PASO ES EL BOTON COMPROAR



//BTN EXIT
        exitbtn = (ImageButton) findViewById(R.id.Exitbutton);
        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //DIALOGO
                AlertDialog.Builder alert = new AlertDialog.Builder(juego.this);

                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.stopgame_dialog, null);
                alert.setView(v);
                alert.create();

                //LISTENER BTNS
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                        progresoingame.setProgress(0);

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                //CREAMOS EL DIALOGO
                final AlertDialog mDialog = alert.show();
            }
        });

        //OPCIONES DE RESPUESTA
        opcion1 = (ToggleButton) findViewById(R.id.optButton1);
        opcion2 = (ToggleButton) findViewById(R.id.optButton2);
        opcion3 = (ToggleButton) findViewById(R.id.optButton3);
        opcion4 = (ToggleButton) findViewById(R.id.optButton4);
        opcion5 = (ToggleButton) findViewById(R.id.optButton5);

//LOGICA GROUPBUTTON
        //BTN1
        opcion1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                TypedValue value = new TypedValue();


                if (opcion1.isChecked()) {
                    //VISTA
                    getResources().getValue(R.drawable.bg_check_togglebtn, value, true);
                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.WHITE);
                    opcion2.setChecked(true);
                    //DESMARCAR LOS DEMAS
                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.DKGRAY);
                    opcion2.setChecked(false);

                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.DKGRAY);
                    opcion3.setChecked(false);

                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.DKGRAY);
                    opcion4.setChecked(false);

                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.DKGRAY);
                    opcion5.setChecked(false);

                } else if (!opcion1.isChecked()) {
                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.DKGRAY);
                    opcion1.setChecked(false);
                }
            }
        });
        //BTN2
        opcion2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                TypedValue value = new TypedValue();

                if (opcion2.isChecked()) {
                    //VISTA
                    getResources().getValue(R.drawable.bg_check_togglebtn, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.WHITE);
                    opcion2.setChecked(true);
                    //DESMARCAR LOS DEMAS
                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.DKGRAY);
                    opcion1.setChecked(false);

                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.DKGRAY);
                    opcion3.setChecked(false);

                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.DKGRAY);
                    opcion4.setChecked(false);

                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.DKGRAY);
                    opcion5.setChecked(false);
                } else if (!opcion2.isChecked()) {
                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.DKGRAY);
                    opcion2.setChecked(false);
                }
            }
        });
        //BTN3
        opcion3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                TypedValue value = new TypedValue();


                if (opcion3.isChecked()) {
                    //VISTA
                    getResources().getValue(R.drawable.bg_check_togglebtn, value, true);
                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.WHITE);
                    opcion3.setChecked(true);
                    //DESMARCAR LOS DEMAS
                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.DKGRAY);
                    opcion2.setChecked(false);

                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.DKGRAY);
                    opcion1.setChecked(false);

                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.DKGRAY);
                    opcion4.setChecked(false);

                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.DKGRAY);
                    opcion5.setChecked(false);
                } else if (!opcion3.isChecked()) {

                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.DKGRAY);
                    opcion3.setChecked(false);
                }
            }
        });
        //BTN4
        opcion4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                TypedValue value = new TypedValue();
                if (opcion4.isChecked()) {
                    //VISTA
                    getResources().getValue(R.drawable.bg_check_togglebtn, value, true);
                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.WHITE);
                    opcion4.setChecked(true);
                    //DESMARCAR LOS DEMAS
                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.DKGRAY);
                    opcion2.setChecked(false);

                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.DKGRAY);
                    opcion3.setChecked(false);

                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.DKGRAY);
                    opcion1.setChecked(false);

                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.DKGRAY);
                    opcion5.setChecked(false);
                } else if (!opcion4.isChecked()) {
                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.DKGRAY);
                    opcion4.setChecked(false);
                }
            }
        });
        //BTN5
        opcion5.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {

                TypedValue value = new TypedValue();


                if (opcion5.isChecked()) {
                    //VISTA
                    getResources().getValue(R.drawable.bg_check_togglebtn, value, true);
                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.WHITE);
                    opcion5.setChecked(true);
                    //DESMARCAR LOS DEMAS

                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.DKGRAY);
                    opcion2.setChecked(false);

                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.DKGRAY);
                    opcion3.setChecked(false);

                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.DKGRAY);
                    opcion4.setChecked(false);

                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.DKGRAY);
                    opcion1.setChecked(false);
                } else if (!opcion5.isChecked()) {
                    getResources().getValue(R.drawable.bg_togglebtn, value, true);
                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.DKGRAY);
                    opcion5.setChecked(false);
                }
            }
        });

        //SETTER
        //RECOGEMOS LOS DATOS DE LA CLASE JUGAR
        TextView problema = (TextView) findViewById(R.id.tvProblem);
        problema.setText(BackgroundGame.getBDDEcuacion());
        Random opcion = new Random(System.currentTimeMillis());
        int opcionn = opcion.nextInt(4) + 1;

        if (opcionn == 1) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));

        } else if (opcionn == 2) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));

        } else if (opcionn == 3) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));

        } else if (opcionn == 4) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));
        } else if (opcionn == 5) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));

        }

        //HABILITAR LOS BOTONES
        opcion1.setChecked(true);
        opcion1.setChecked(false);
        opcion2.setChecked(true);
        opcion2.setChecked(false);
        opcion3.setChecked(true);
        opcion3.setChecked(false);
        opcion4.setChecked(true);
        opcion4.setChecked(false);
        opcion5.setChecked(true);
        opcion5.setChecked(false);


    }


    @Override
    public void onBackPressed() {
        //DIALOGO PARA VOLVER
        AlertDialog.Builder alert = new AlertDialog.Builder(juego.this);

        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.stopgame_dialog, null);
        alert.setView(v);
        alert.create();

        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                progresoingame.setProgress(0);

            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        //CREAMOS EL DIALOGO
        final AlertDialog mDialog = alert.show();

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public boolean comprobar(Context context) {
        //TYPOE VALUE PARA PINTAR LOS BOTONES Y TEXTS
        TypedValue value = new TypedValue();
        //RESPUESTA DEL USUARIO
        int respuestafromuser;
        ///LISTENER DE CADA BOTON PARA COMPROBAR CUAL ESTA PRESIONADO, GETTEAR LA OPCION Y COMPROBAR SI EES CORRECTA LA RESPUESTA

        if (opcion1.isChecked()) {
            //GETTEAMOS lA REPSUESTA DEL USUARIO
            respuestafromuser = Integer.parseInt(String.valueOf(opcion1.getTextOn()));
            //COMPROBAMOS SI LA RESPUESTA DEL USUARIO ES CORRECTA
            if (respuestafromuser == BackgroundGame.getBDDResultado()) {
                //SI ES CORRECTA
                //PINTAMOS EL BOTON DE VERDE
                getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                opcion1.setBackground(getResources().getDrawable(value.resourceId));
                //PINTAMOS EL PROBLEMA DE VERDE
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.correct, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());
                return true;
            }
            //SINO
            else {
                //Pintamos el resultado de rojo porque no es correcto
                getResources().getValue(R.drawable.bg_comprobar_incorrect, value, true);
                opcion1.setBackground(getResources().getDrawable(value.resourceId));

                //PINTAMOS EL PROBLEMA DE AZUL
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.colorPrimary, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                //BUSCAMOS CUAL ES EL RESULTADO Y LO PINTAMOS DE VERDE
                //OPCION 2
                if(Integer.parseInt(String.valueOf(opcion2.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.WHITE);
                }
                //OPCION 3
                else if(Integer.parseInt(String.valueOf(opcion3.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.WHITE);
                }
                //OPCION 4
                else if(Integer.parseInt(String.valueOf(opcion4.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.WHITE);
                }
                //OPCION 5
                else if(Integer.parseInt(String.valueOf(opcion5.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.WHITE);
                }
                return false;

            }
        } else if (opcion2.isChecked()) {
            respuestafromuser = Integer.parseInt(String.valueOf(opcion2.getTextOn()));
            if (respuestafromuser == BackgroundGame.getBDDResultado()) {
                //SI ES CORRECTA
                //PINTAMOS EL BOTON DE VERDE
                getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                opcion2.setBackground(getResources().getDrawable(value.resourceId));
                //PINTAMOS EL PROBLEMA DE VERDE
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.correct, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                return true;
            }
            //SINO
            else {
                //Pintamos el resultado de rojo porque no es correcto
                getResources().getValue(R.drawable.bg_comprobar_incorrect, value, true);
                opcion2.setBackground(getResources().getDrawable(value.resourceId));

                //PINTAMOS EL PROBLEMA DE AZUL
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.colorPrimary, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                //BUSCAMOS CUAL ES EL RESULTADO Y LO PINTAMOS DE VERDE
                //OPCION 1
                if(Integer.parseInt(String.valueOf(opcion1.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.WHITE);
                }
                //OPCION 3
                else if(Integer.parseInt(String.valueOf(opcion3.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.WHITE);
                }
                //OPCION 4
                else if(Integer.parseInt(String.valueOf(opcion4.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.WHITE);
                }
                //OPCION 5
                else if(Integer.parseInt(String.valueOf(opcion5.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.WHITE);
                }
                return false;
            }
        } else if (opcion3.isChecked()) {
            respuestafromuser = Integer.parseInt(String.valueOf(opcion3.getTextOn()));
            if (respuestafromuser == BackgroundGame.getBDDResultado()) {
                //SI ES CORRECTA
                //PINTAMOS EL BOTON DE VERDE
                getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                opcion3.setBackground(getResources().getDrawable(value.resourceId));
                //PINTAMOS EL PROBLEMA DE VERDE
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.correct, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                return true;
            }
            //SINO
            else {
                //Pintamos el resultado de rojo porque no es correcto
                getResources().getValue(R.drawable.bg_comprobar_incorrect, value, true);
                opcion3.setBackground(getResources().getDrawable(value.resourceId));

                //PINTAMOS EL PROBLEMA DE AZUL
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.colorPrimary, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                //BUSCAMOS CUAL ES EL RESULTADO Y LO PINTAMOS DE VERDE
                //OPCION 2
                if(Integer.parseInt(String.valueOf(opcion2.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.WHITE);
                }
                //OPCION 1
                else if(Integer.parseInt(String.valueOf(opcion1.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.WHITE);
                }
                //OPCION 4
                else if(Integer.parseInt(String.valueOf(opcion4.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.WHITE);
                }
                //OPCION 5
                else if(Integer.parseInt(String.valueOf(opcion5.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.WHITE);
                }

                return false;
            }
        } else if (opcion4.isChecked()) {
            respuestafromuser = Integer.parseInt(String.valueOf(opcion4.getTextOn()));
            if (respuestafromuser == BackgroundGame.getBDDResultado()) {
                //SI ES CORRECTA
                //PINTAMOS EL BOTON DE VERDE
                getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                opcion4.setBackground(getResources().getDrawable(value.resourceId));
                //PINTAMOS EL PROBLEMA DE VERDE
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.correct, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                return true;
            }
            //SINO
            else {
                //Pintamos el resultado de rojo porque no es correcto
                getResources().getValue(R.drawable.bg_comprobar_incorrect, value, true);
                opcion4.setBackground(getResources().getDrawable(value.resourceId));

                //PINTAMOS EL PROBLEMA DE AZUL
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.colorPrimary, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                //BUSCAMOS CUAL ES EL RESULTADO Y LO PINTAMOS DE VERDE
                //OPCION 2
                if(Integer.parseInt(String.valueOf(opcion2.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.WHITE);
                }
                //OPCION 3
                else if(Integer.parseInt(String.valueOf(opcion3.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.WHITE);
                }
                //OPCION 1
                else if(Integer.parseInt(String.valueOf(opcion1.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.WHITE);
                }
                //OPCION 5
                else if(Integer.parseInt(String.valueOf(opcion5.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion5.setBackground(getResources().getDrawable(value.resourceId));
                    opcion5.setTextColor(Color.WHITE);
                }

                return false;
            }
        } else if (opcion5.isChecked()) {
            respuestafromuser = Integer.parseInt(String.valueOf(opcion5.getTextOn()));
            if (respuestafromuser == BackgroundGame.getBDDResultado()) {
                //SI ES CORRECTA
                //PINTAMOS EL BOTON DE VERDE
                getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                opcion5.setBackground(getResources().getDrawable(value.resourceId));
                //PINTAMOS EL PROBLEMA DE VERDE
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.correct, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                return true;
            }
            //SINO
            else {
                //Pintamos el resultado de rojo porque no es correcto
                getResources().getValue(R.drawable.bg_comprobar_incorrect, value, true);
                opcion5.setBackground(getResources().getDrawable(value.resourceId));

                //PINTAMOS EL PROBLEMA DE AZUL
                TextView problema = (TextView) findViewById(R.id.tvProblem);
                getResources().getValue(R.color.colorPrimary, value, true);
                problema.setTextColor(getResources().getColor(value.resourceId));
                problema.setText(BackgroundGame.getBDDEcuacionCompleta());

                //BUSCAMOS CUAL ES EL RESULTADO Y LO PINTAMOS DE VERDE
                //OPCION 2
                if(Integer.parseInt(String.valueOf(opcion2.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion2.setBackground(getResources().getDrawable(value.resourceId));
                    opcion2.setTextColor(Color.WHITE);
                }
                //OPCION 3
                else if(Integer.parseInt(String.valueOf(opcion3.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion3.setBackground(getResources().getDrawable(value.resourceId));
                    opcion3.setTextColor(Color.WHITE);
                }
                //OPCION 4
                else if(Integer.parseInt(String.valueOf(opcion4.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion4.setBackground(getResources().getDrawable(value.resourceId));
                    opcion4.setTextColor(Color.WHITE);
                }
                //OPCION 1
                else if(Integer.parseInt(String.valueOf(opcion1.getTextOff())) == BackgroundGame.getBDDResultado()){
                    getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
                    opcion1.setBackground(getResources().getDrawable(value.resourceId));
                    opcion1.setTextColor(Color.WHITE);
                }
                return false;
            }
        } else {
            Toast.makeText(context, "Selecciona Una Respuesta, no hay respuesta", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void nextGame() {

        dificultad = getIntent().getExtras().getInt("Dificultad");
        TipodeJuego = getIntent().getExtras().getString("Tipo");

        BackgroundGame.SetGame(TipodeJuego, dificultad);

        TextView problema = (TextView) findViewById(R.id.tvProblem);
        problema.setText(BackgroundGame.getBDDEcuacion());
        Random opcion = new Random(System.currentTimeMillis());
        int opcionn = opcion.nextInt(5) + 1;

        if (opcionn == 1) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));

        } else if (opcionn == 2) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));

        } else if (opcionn == 3) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));

        } else if (opcionn == 4) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));
        } else if (opcionn == 5) {
            opcion1.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion4()));
            opcion2.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOn(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOn(String.valueOf(BackgroundGame.getBDDResultado()));

            opcion1.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion4()));
            opcion2.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion1()));
            opcion3.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion2()));
            opcion4.setTextOff(String.valueOf(BackgroundGame.getBDDOpcion3()));
            opcion5.setTextOff(String.valueOf(BackgroundGame.getBDDResultado()));

        }

        opcion1.setChecked(true);
        opcion1.setChecked(false);
        opcion2.setChecked(true);
        opcion2.setChecked(false);
        opcion3.setChecked(true);
        opcion3.setChecked(false);
        opcion4.setChecked(true);
        opcion4.setChecked(false);
        opcion5.setChecked(true);
        opcion5.setChecked(false);

        //RESETAMOS LOS BOTONES AL COLOR DESACTIVADO
        TypedValue value = new TypedValue();

        getResources().getValue(R.drawable.bg_togglebtn, value, true);
        opcion1.setBackground(getResources().getDrawable(value.resourceId));
        opcion1.setTextColor(Color.DKGRAY);
        opcion1.setChecked(false);


        getResources().getValue(R.drawable.bg_togglebtn, value, true);
        opcion2.setBackground(getResources().getDrawable(value.resourceId));
        opcion2.setTextColor(Color.DKGRAY);
        opcion2.setChecked(false);

        getResources().getValue(R.drawable.bg_togglebtn, value, true);
        opcion3.setBackground(getResources().getDrawable(value.resourceId));
        opcion3.setTextColor(Color.DKGRAY);
        opcion3.setChecked(false);

        getResources().getValue(R.drawable.bg_togglebtn, value, true);
        opcion4.setBackground(getResources().getDrawable(value.resourceId));
        opcion4.setTextColor(Color.DKGRAY);
        opcion4.setChecked(false);

        getResources().getValue(R.drawable.bg_togglebtn, value, true);
        opcion5.setBackground(getResources().getDrawable(value.resourceId));
        opcion5.setTextColor(Color.DKGRAY);
        opcion5.setChecked(false);

    }

    public boolean comprobar_seleccionado() {

        if (opcion1.isChecked()) {
            return true;
        } else if (opcion2.isChecked()) {
            return true;
        } else if (opcion3.isChecked()) {
            return true;
        } else if (opcion4.isChecked()) {
            return true;
        } else if (opcion5.isChecked()) {
            return true;
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void Message(Boolean respuesta){

//CORRECTO
        if (respuesta == true){
            //Layout del Mensaje Emergente
            final LinearLayout msg = (LinearLayout) findViewById(R.id.textlinear);

            //Texto del Mensaje Emergente
            final TextView msgtext = (TextView) findViewById(R.id.textmessage);
            //ANIMACION
            msg.setVisibility(View.VISIBLE);
            msg.startAnimation(AnimationUtils.loadAnimation(juego.this, R.anim.aparation_mesg));
            //COLOR DEL MENSAJE
            TypedValue value = new TypedValue();
            getResources().getValue(R.color.correctText, value, true);
            msgtext.setTextColor(getResources().getColor(value.resourceId));
            msgtext.setText("Respuesta Correcta");
            //FONDo del mensaje
            getResources().getValue(R.drawable.bg_comprobar_correct, value, true);
            msg.setBackground(getResources().getDrawable(value.resourceId));


        }else{
            //Layout del Mensaje Emergente
            final LinearLayout msg = (LinearLayout) findViewById(R.id.textlinear);
            //Texto del Mensaje Emergente
            final TextView msgtext = (TextView) findViewById(R.id.textmessage);
            //ANIMACION Del mensaje
            msg.setVisibility(View.VISIBLE);
            msg.startAnimation(AnimationUtils.loadAnimation(juego.this, R.anim.aparation_mesg));
            //COLOR del texto del mensaje
            TypedValue value = new TypedValue();
            getResources().getValue(R.color.incorrectText, value, true);
            msgtext.setTextColor(getResources().getColor(value.resourceId));
            msgtext.setText("Respuesta Incorrecta");
            //FONDo del mensaje
            getResources().getValue(R.drawable.bg_comprobar_incorrect, value, true);
            msg.setBackground(getResources().getDrawable(value.resourceId));


        }


    }

    public void DinamicVidas(String sor,int nro){

        //CONTADOR
        final TextView dinamicmsg = findViewById(R.id.dinamicmsg_hearts);

        final TextView hearts = findViewById(R.id.count_corazones);

        if(sor.equals("restar")){
            // SETEAR INFO DE CUANTAS VIDAS SON
            dinamicmsg.setText("-"+nro);
            //COLOR
            TypedValue value = new TypedValue();
            getResources().getValue(R.color.lives, value, true);
            dinamicmsg.setTextColor(getResources().getColor(value.resourceId));
            //RESTAMOS LAS VIDAS EN EL LAYOUT
            int vidas_act = Integer.parseInt(hearts.getText().toString());
            int vidas_restult = vidas_act - nro;
            hearts.setText(String.valueOf(vidas_restult));
            //ANIMACION
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.plus_or_minus_value);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                dinamicmsg.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            dinamicmsg.setVisibility(View.VISIBLE);
            dinamicmsg.startAnimation(animation);
            //VISIBLE EL TEXTO, Y GONE CUANDO TERMINE LA ANIMACION
        }
        else if (sor.equals("sumar")){
            //ANIMACION
            // SETEAR INFO DE CUANTAS VIDAS SON
            //VISIBLE EL TEXTO, Y GONE CUANDO TERMINE LA ANIMACION
        }
    }

}


