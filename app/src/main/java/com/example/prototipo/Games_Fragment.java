package com.example.prototipo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.prototipo.MainActivity.corazones;
import static com.example.prototipo.MainActivity.isPowerActive;

/*FRAGMENT GAMES----------------------------------
* Aca podras escoger los juegos creados, escoger su dificultad*/
public class Games_Fragment extends Fragment {
    //Vidas, Variable utilizada para almacenar las vidas disponibles del usuario, (Obtenidas desde la toolbar de la activity padre)
    int Vidas;
    //Btns del dialogo para escoger la dificultad
    Button btnfacil;
    Button btnnormal;
    Button btndificil;

    //BOTON JUGAR SUMA
    ImageButton Btn1;
    //BOTON JUGAR RESTA
    ImageButton Btn2;
    //BOTON JUGAR MULTI
    ImageButton Btn3;
    //BOTON JUGAR DIVI
    ImageButton Btn4;
    //BOTONES EXTRA (POR AHORA)
    ImageButton Btn_E_1;
    ImageButton Btn_E_2;
    ImageButton Btn_E_3;
    ImageButton Btn_E_4;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_games_, container, false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //Almacenamos las vidas del usuario disponibles desde la toolbar, donde se evidenciaran todos los cambios (comprar poderes etc)


        //CONECTORES BTOTON DE SUMA
        Btn1 = (ImageButton) v.findViewById(R.id.btn_jugar);
        //Igualamos el parametro Height del boton a su valor Widht, para que siempre sean cuadrados
        Btn1.getLayoutParams().height = Btn1.getMeasuredWidth();
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recogemos las vidas en cada click
                //Verificamos si el usuario tiene el Power Activo
                AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
                SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

                if (dbread != null) {
                    String sql = "SELECT * FROM usuarios";
                    Cursor c = dbread.rawQuery(sql, null);
                    //Si encontro, recogemos los datos
                    if (c.moveToNext()) {
                        isPowerActive = c.getInt(c.getColumnIndex("power"));
                    }
                }

                //Cerramos la Conexion
                adminSQLiteOpenHelper.close();

                //agaramos las vidas
                Vidas = Integer.parseInt(corazones);
                //COMPROBAMOS SI EL USUARIO TIENE VIDAS SUFICIENTES
                if (Vidas > 0) {

                    //Instanciamos el Dialogo de escoger la dificultad
                    /*PROXIMAMENTE---------
                     * Es posible que haga falta quitar el dialogo y escoger la dificultad desde el menu */
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.layout_dificult_dialog, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();

                    //BOTONES DEL DIALOGOi
                    btnfacil = (Button) v.findViewById(R.id.btnfacil);
                    btnfacil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 0);
                            i.putExtra("Tipo", "suma");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                    btnnormal = (Button) v.findViewById(R.id.btnnormal);
                    btnnormal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 1);
                            i.putExtra("Tipo", "suma");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                    btndificil = (Button) v.findViewById(R.id.btndificil);
                    btndificil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 2);
                            i.putExtra("Tipo", "suma");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                } else {
                    /* Dialogo para mostrar que no tiene suficientes vidas */
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    TextView tv = v.findViewById(R.id.tv1_dialog_ok);
                    tv.setText("No tienes suficientes corazones para jugar ahora");
                    tv.setTextColor(Color.RED);
                    Button btnok = v.findViewById(R.id.btnOk);
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });

                }


            }
        });

        //CONECTORES BTOTON DE RESTA
        Btn2 = (ImageButton) v.findViewById(R.id.btn_jugar_resta);
        Btn2.getLayoutParams().height = Btn2.getMeasuredWidth();
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recogemos las vidas en cada click
                //Verificamos si el usuario tiene el Power Activo
                AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
                SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

                if (dbread != null) {
                    String sql = "SELECT * FROM usuarios";
                    Cursor c = dbread.rawQuery(sql, null);
                    //Si encontro, recogemos los datos
                    if (c.moveToNext()) {
                        isPowerActive = c.getInt(c.getColumnIndex("power"));
                    }
                }

                //Cerramos la Conexion
                adminSQLiteOpenHelper.close();
                //Agarramos las vidas
                Vidas = Integer.parseInt(corazones);

                //COMPROBAMOS SI TIENE SUFICIENTES VIDAS PARA JUGAR
                if (Vidas > 0) {
                    //DIALOGO

                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.layout_dificult_dialog, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();

                    //BOTONES DEL DIALOGO
                    btnfacil = (Button) v.findViewById(R.id.btnfacil);
                    btnfacil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 0);
                            i.putExtra("Tipo", "resta");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                    btnnormal = (Button) v.findViewById(R.id.btnnormal);
                    btnnormal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 1);
                            i.putExtra("Tipo", "resta");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                    btndificil = (Button) v.findViewById(R.id.btndificil);
                    btndificil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 2);
                            i.putExtra("Tipo", "resta");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                } else {
                    /* Dialogo para mostrar que no tiene suficientes vidas */
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    TextView tv = v.findViewById(R.id.tv1_dialog_ok);
                    tv.setText("No tienes suficientes corazones para jugar ahora");
                    tv.setTextColor(Color.RED);
                    Button btnok = v.findViewById(R.id.btnOk);
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });

                }


            }
        });


        //CONECTORES BTOTON DE MULTIPLICACION
        Btn3 = (ImageButton) v.findViewById(R.id.btn_jugar_multi);
        Btn3.getLayoutParams().height = Btn3.getMeasuredWidth();
        //Btn3.setBackground(AppCompatResources.getDrawable(getActivity(),R.drawable.btn_multi));
        Btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recogemos las vidas en cada click
                //Recogemos las vidas en cada click
                //Verificamos si el usuario tiene el Power Activo
                AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
                SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

                if (dbread != null) {
                    String sql = "SELECT * FROM usuarios";
                    Cursor c = dbread.rawQuery(sql, null);
                    //Si encontro, recogemos los datos
                    if (c.moveToNext()) {
                        isPowerActive = c.getInt(c.getColumnIndex("power"));
                    }
                }

                //Cerramos la Conexion
                adminSQLiteOpenHelper.close();

                //Agarramos las vidas
                Vidas = Integer.parseInt(corazones);

                //COMPROBAMOS SI TIENE SUFICIENTES VIDAS PARA JUGAR
                if (Vidas > 0) {
                    //DIALOGO

                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.layout_dificult_dialog, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();

                    //BOTONES DEL DIALOGO
                    btnfacil = (Button) v.findViewById(R.id.btnfacil);
                    btnfacil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 0);
                            i.putExtra("Tipo", "multi");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                    btnnormal = (Button) v.findViewById(R.id.btnnormal);
                    btnnormal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 1);
                            i.putExtra("Tipo", "multi");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                    btndificil = (Button) v.findViewById(R.id.btndificil);
                    btndificil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 2);
                            i.putExtra("Tipo", "multi");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                } else {
                    /* Dialogo para mostrar que no tiene suficientes vidas */
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    TextView tv = v.findViewById(R.id.tv1_dialog_ok);
                    tv.setText("No tienes suficientes corazones para jugar ahora");
                    tv.setTextColor(Color.RED);
                    Button btnok = v.findViewById(R.id.btnOk);
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });

                }


            }
        });

        //CONECTORES BTOTON DE DIVISION
        Btn4 = (ImageButton) v.findViewById(R.id.btn_jugar_divi);
        Btn4.getLayoutParams().height = Btn4.getMeasuredWidth();
        //Btn4.setBackground(AppCompatResources.getDrawable(getActivity(),R.drawable.btn_divi));
        Btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recogemos las vidas en cada click
                //Verificamos si el usuario tiene el Power Activo
                AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
                SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

                if (dbread != null) {
                    String sql = "SELECT * FROM usuarios";
                    Cursor c = dbread.rawQuery(sql, null);
                    //Si encontro, recogemos los datos
                    if (c.moveToNext()) {
                        isPowerActive = c.getInt(c.getColumnIndex("power"));
                    }
                }

                //Cerramos la Conexion
                adminSQLiteOpenHelper.close();


                //Agarramos las vidas
                Vidas = Integer.parseInt(corazones);


                //COMPROBAMOS SI TIENE SUFICIENTES VIDAS PARA JUGAR
                if (Vidas > 0) {
                    //DIALOGO

                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.layout_dificult_dialog, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();

                    //BOTONES DEL DIALOGO
                    btnfacil = (Button) v.findViewById(R.id.btnfacil);
                    btnfacil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 0);
                            i.putExtra("Tipo", "divi");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                    btnnormal = (Button) v.findViewById(R.id.btnnormal);
                    btnnormal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 1);
                            i.putExtra("Tipo", "divi");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                    btndificil = (Button) v.findViewById(R.id.btndificil);
                    btndificil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                            Intent i = new Intent(getActivity(), juego.class);
                            i.putExtra("Dificultad", 2);
                            i.putExtra("Tipo", "divi");
                            i.putExtra("Vidas", Vidas);
                            i.putExtra("powerUp", isPowerActive);
                            startActivity(i);
                            mDialog.dismiss();
                        }
                    });
                } else {
                    /* Dialogo para mostrar que no tiene suficientes vidas */
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    TextView tv = v.findViewById(R.id.tv1_dialog_ok);
                    tv.setText("No tienes suficientes corazones para jugar ahora");
                    tv.setTextColor(Color.RED);
                    Button btnok = v.findViewById(R.id.btnOk);
                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });

                }


            }
        });

        //VERIFICAMOS QUE LOS NIVELES EXTRA ESTAN EN QUE ESTADO

        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
        SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

        String sql = "SELECT * FROM niveles";
        Cursor c = dbread.rawQuery(sql, null);
        int isDesbloqueable1 = 0;
        int isDesbloqueable2 = 0;
        if (c.moveToNext()) {
            isDesbloqueable1 = c.getInt(c.getColumnIndex("desbloqueable1"));
            isDesbloqueable2 = c.getInt(c.getColumnIndex("desbloqueable2"));
        }
        adminSQLiteOpenHelper.close();


        Btn_E_1 = v.findViewById(R.id.btn_extra1);
        Btn_E_1.getLayoutParams().height = Btn_E_1.getMeasuredWidth();
        //SI esta desbloqueado lo mostramos
        if (isDesbloqueable1 == 1) {
            //BOTOM 1 ex
            //Color amarillo
            TypedValue value = new TypedValue();
            getResources().getValue(R.color.desbloqueable1, value, true);
            Btn_E_1.setBackgroundColor(getResources().getColor(value.resourceId));
            Btn_E_1.setImageResource(R.drawable.ic_maths_desbloqueable1);


            Btn_E_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Recogemos las vidas en cada click
                    //Verificamos si el usuario tiene el Power Activo
                    AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
                    SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

                    if (dbread != null) {
                        String sql = "SELECT * FROM usuarios";
                        Cursor c = dbread.rawQuery(sql, null);
                        //Si encontro, recogemos los datos
                        if (c.moveToNext()) {
                            isPowerActive = c.getInt(c.getColumnIndex("power"));
                        }
                    }

                    //Cerramos la Conexion
                    adminSQLiteOpenHelper.close();


                    //Agarramos las vidas
                    Vidas = Integer.parseInt(corazones);


                    //COMPROBAMOS SI TIENE SUFICIENTES VIDAS PARA JUGAR
                    if (Vidas > 0) {
                        //DIALOGO

                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.layout_dificult_dialog, null);
                        alert.setView(v);
                        //CREAMOS EL DIALOGO
                        alert.create();
                        final AlertDialog mDialog = alert.show();

                        //BOTONES DEL DIALOGO
                        //NO HAY FACIL
                        btnfacil = (Button) v.findViewById(R.id.btnfacil);
                        btnfacil.setVisibility(View.GONE);

                        btnnormal = (Button) v.findViewById(R.id.btnnormal);
                        btnnormal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                                Intent i = new Intent(getActivity(), juego.class);
                                i.putExtra("Dificultad", 1);
                                i.putExtra("Tipo", "combinado");
                                i.putExtra("Vidas", Vidas);
                                i.putExtra("powerUp", isPowerActive);
                                startActivity(i);
                                mDialog.dismiss();
                            }
                        });
                        btndificil = (Button) v.findViewById(R.id.btndificil);
                        btndificil.setVisibility(View.GONE);
                    } else {
                        /* Dialogo para mostrar que no tiene suficientes vidas */
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                        alert.setView(v);
                        //CREAMOS EL DIALOGO
                        alert.create();
                        final AlertDialog mDialog = alert.show();
                        TextView tv = v.findViewById(R.id.tv1_dialog_ok);
                        tv.setText("No tienes suficientes corazones para jugar ahora");
                        tv.setTextColor(Color.RED);
                        Button btnok = v.findViewById(R.id.btnOk);
                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialog.dismiss();
                            }
                        });

                    }


                }
            });


        }

        //Btn_E_1.setBackground(AppCompatResources.getDrawable(getActivity(),R.drawable.bg_round_button));

        //BOTOM 2 ex
        Btn_E_2 = v.findViewById(R.id.btn_extra2);
        Btn_E_2.getLayoutParams().height = Btn_E_2.getMeasuredWidth();

        //SI esta desbloqueado lo mostramos
        if (isDesbloqueable2 == 1) {
            //BOTOM 1 ex
            //Color amarillo
            TypedValue value = new TypedValue();
            getResources().getValue(R.color.colorAccent, value, true);
            Btn_E_2.setBackgroundColor(getResources().getColor(value.resourceId));
            Btn_E_2.setImageResource(R.drawable.ic_maths_desbloqueable1);


            Btn_E_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Recogemos las vidas en cada click
                    //Verificamos si el usuario tiene el Power Activo
                    AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
                    SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

                    if (dbread != null) {
                        String sql = "SELECT * FROM usuarios";
                        Cursor c = dbread.rawQuery(sql, null);
                        //Si encontro, recogemos los datos
                        if (c.moveToNext()) {
                            isPowerActive = c.getInt(c.getColumnIndex("power"));
                        }
                    }

                    //Cerramos la Conexion
                    adminSQLiteOpenHelper.close();


                    //Agarramos las vidas
                    Vidas = Integer.parseInt(corazones);


                    //COMPROBAMOS SI TIENE SUFICIENTES VIDAS PARA JUGAR
                    if (Vidas > 0) {
                        //DIALOGO

                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.layout_dificult_dialog, null);
                        alert.setView(v);
                        //CREAMOS EL DIALOGO
                        alert.create();
                        final AlertDialog mDialog = alert.show();

                        //BOTONES DEL DIALOGO
                        //NO HAY FACIL
                        btnfacil = (Button) v.findViewById(R.id.btnfacil);
                        btnfacil.setVisibility(View.GONE);

                        btnnormal = (Button) v.findViewById(R.id.btnnormal);
                        btnnormal.setVisibility(View.GONE);

                        btndificil = (Button) v.findViewById(R.id.btndificil);
                        btndificil.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //INICIAMOS EL ACTIVITY CON LA DIFICULTAD PROPORCIONADA
                                Intent i = new Intent(getActivity(), juego.class);
                                i.putExtra("Dificultad", 2);
                                i.putExtra("Tipo", "combinado");
                                i.putExtra("Vidas", Vidas);
                                i.putExtra("powerUp", isPowerActive);
                                startActivity(i);
                                mDialog.dismiss();
                            }
                        });
                    } else {
                        /* Dialogo para mostrar que no tiene suficientes vidas */
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                        LayoutInflater inflater = getLayoutInflater();
                        View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                        alert.setView(v);
                        //CREAMOS EL DIALOGO
                        alert.create();
                        final AlertDialog mDialog = alert.show();
                        TextView tv = v.findViewById(R.id.tv1_dialog_ok);
                        tv.setText("No tienes suficientes corazones para jugar ahora");
                        tv.setTextColor(Color.RED);
                        Button btnok = v.findViewById(R.id.btnOk);
                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mDialog.dismiss();
                            }
                        });

                    }


                }
            });


            //BOTOM 3 ex
          //  Btn_E_3 = v.findViewById(R.id.btn_extra3);
           // Btn_E_3.getLayoutParams().height = Btn_E_3.getMeasuredWidth();
            //Btn_E_3.setBackground(AppCompatResources.getDrawable(getActivity(),R.drawable.bg_round_button));

            //BOTOM 4 ex
          //  Btn_E_4 = v.findViewById(R.id.btn_extra4);
          //  Btn_E_4.getLayoutParams().height = Btn_E_4.getMeasuredWidth();
            //Btn_E_4.setBackground(AppCompatResources.getDrawable(getActivity(),R.drawable.bg_round_button));


        }
        return v;
    }
}
