package com.example.prototipo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.example.prototipo.MainActivity.Btn_Objetos;
import static com.example.prototipo.MainActivity.cofres;
import static com.example.prototipo.MainActivity.cofres_tv;
import static com.example.prototipo.MainActivity.corazones;
import static com.example.prototipo.MainActivity.corazones_tv;
import static com.example.prototipo.MainActivity.isCronoRunning;
import static com.example.prototipo.MainActivity.isPowerActive;
import static com.example.prototipo.MainActivity.llaves;
import static com.example.prototipo.MainActivity.llaves_tv;
import static com.example.prototipo.MainActivity.monedas;
import static com.example.prototipo.MainActivity.monedas_tv;
import static com.example.prototipo.MainActivity.powerUpInfiniteLives;
import static com.example.prototipo.MainActivity.sound;
import static java.lang.Thread.sleep;

/*FRAGMENT SHOP----------------------------------
 * Aca puedes Comprar cosas para mejorar tu jjuego*/
public class Shop_Fragment extends Fragment {
    //Vistas del diaogo
    TextView cost;
    TextView quantityOfproduct;
    ImageView imgProducto;

    int costodelP;
    int cantidadProductos;
//INICIALIZAMOS CADA PRODUCTO DE LA TIENDA
    //Vidas Extra
    LinearLayout product_1;

    //Vidas Infinitas durante un tiempo
    LinearLayout product_2;

    //Cofre para desbloquear Monedas o Niveles
    LinearLayout product_3;

    //Llaves Para abrir el cofre
    LinearLayout product_4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop_, container, false);
        // COMPROBAR SI TIENE LAS MONEDAS CORRECTAS PARA COMPRAR DETERMINADO OBJETO
        //ALMACENAR POR AHORA EL NUMERO DE VIDAS Y MONEDAS EN UN ARCHIVO.

        //MONEDAS


        //Productos-------------------------------------------------------------------------------

        //Mas vidas
        product_1 = v.findViewById(R.id.dos_vidas_mas);
        product_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lo que cuesta el producto1
                costodelP = 20;
                //Se usa condicional para comprobar si el usuario tiene las monedas requeridas
                if(Integer.parseInt(monedas) >= costodelP){
                    cantidadProductos = 2;
                    //MOSTRAR UN DIALOGO PARA CONFIRMAR LA COMPRA
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_confirm_purchase, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    //Seteamos la info de la compra en el dialogo
                    cost = v.findViewById(R.id.cost);
                    cost.setText(String.valueOf(costodelP));

                    quantityOfproduct = v.findViewById(R.id.quantityofproduct);
                    quantityOfproduct.setText(String.valueOf(cantidadProductos));

                    imgProducto = v.findViewById(R.id.imageofproduct);
                    TypedValue value = new TypedValue();
                    getResources().getValue(R.drawable.ic_heart, value, true);
                    imgProducto.setImageDrawable(getResources().getDrawable(value.resourceId));

                    //Botones
                    Button btnsi = v.findViewById(R.id.btnSi);
                    btnsi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                            //Verificamos que no tenga vidas infinitas
                            if(isPowerActive == 1){
                                //MOSTRAR UN DIALOGO PARA CONFIRMAR LA COMPRA
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                                LayoutInflater inflater = getLayoutInflater();
                                View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                                alert.setView(v);
                                //CREAMOS EL DIALOGO
                                alert.create();
                                final AlertDialog mDialog = alert.show();
                                TextView tv1ok = v.findViewById(R.id.tv1_dialog_ok);
                                tv1ok.setText("Ya tienes vidas infinitas, ¡Aprovecha!");

                                Button btnok = v.findViewById(R.id.btnOk);
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mDialog.dismiss();
                                    }
                                });
                            }else {//COMPRA DE 2 CORAZONES
                                //Cerramos el aviso anterior
                                mDialog.dismiss();
                                //Mostramos un aviso que ha comprado
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                                LayoutInflater inflater = getLayoutInflater();
                                View v = inflater.inflate(R.layout.dialog_inform_purchased, null);
                                alert.setView(v);
                                //CREAMOS EL DIALOGO
                                alert.create();
                                final AlertDialog mDialog = alert.show();
                                TextView tv1ok = v.findViewById(R.id.tv1_dialog_ok);
                                tv1ok.setText("Has adquirido 2");

                                imgProducto = v.findViewById(R.id.imageofproduct);
                                TypedValue value = new TypedValue();
                                getResources().getValue(R.drawable.ic_heart, value, true);
                                imgProducto.setImageDrawable(getResources().getDrawable(value.resourceId));

                                Button btnok = v.findViewById(R.id.btnOk);
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mDialog.dismiss();
                                    }
                                });

                                //CARGAMOS LA BASE DE DATOS
                                AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity().getApplicationContext());
                                SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();

                                ContentValues values = new ContentValues();
                                //Sumamos lo corazones
                                corazones = String.valueOf(Integer.parseInt(corazones) + 2);
                                values.put("corazones", corazones);
                                //Restamos las monedas
                                monedas = String.valueOf(Integer.parseInt(monedas) - costodelP);
                                values.put("monedas", monedas);
                                dbwrite.update("usuarios", values, "id = 1", null);

                                //Cerramos la Conexion
                                adminSQLiteOpenHelper.close();
                                //Seteamos en el tv
                                corazones_tv.setText(corazones);
                                monedas_tv.setText(monedas);
                                MainActivity.AnimacionSumarVidas(2, getActivity());
                            }
                        }
                    });

                    Button btnno = v.findViewById(R.id.btnNo);
                    btnno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });


                }else{
                    //Mostramos un dialogo que no tiene suficiente dinero para comprar
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();

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

        //Vidas infinitas durante un periodo corto de tiempo
        product_2 = v.findViewById(R.id.vidas_inf);
        product_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lo que cuesta el producto1
                costodelP = 150;
                //Se usa condicional para comprobar si el usuario tiene las monedas requeridas
                if(Integer.parseInt(monedas) >= costodelP){
                    cantidadProductos = 1;
                    //MOSTRAR UN DIALOGO PARA CONFIRMAR LA COMPRA
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_confirm_purchase, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    //Seteamos la info de la compra en el dialogo
                    cost = v.findViewById(R.id.cost);
                    cost.setText(String.valueOf(costodelP));

                    quantityOfproduct = v.findViewById(R.id.quantityofproduct);
                    quantityOfproduct.setText(String.valueOf(cantidadProductos));

                    imgProducto = v.findViewById(R.id.imageofproduct);
                    TypedValue value = new TypedValue();
                    getResources().getValue(R.drawable.ic_heart_blue, value, true);
                    imgProducto.setImageDrawable(getResources().getDrawable(value.resourceId));

                    //Botones
                    Button btnsi = v.findViewById(R.id.btnSi);
                    btnsi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                            //Llamamos el meotodo
                            if(isPowerActive == 1){
                                //MOSTRAR UN DIALOGO PARA MOSTRAR QUE yA EL SUUSARIO TIENE EL PRODUCTO
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                                LayoutInflater inflater = getLayoutInflater();
                                View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                                alert.setView(v);
                                //CREAMOS EL DIALOGO
                                alert.create();
                                final AlertDialog mDialog = alert.show();
                                TextView tv1ok = v.findViewById(R.id.tv1_dialog_ok);
                                tv1ok.setText("Ya tienes este producto activo");

                                Button btnok = v.findViewById(R.id.btnOk);
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mDialog.dismiss();
                                    }
                                });
                            }
                            else if(isCronoRunning(ServicioTimer.class, getActivity().getApplicationContext())){
                                //MOSTRAR UN DIALOGO PARA MOSTRAR QUE yA EL SUUSARIO TIENE EL PRODUCTO
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                                LayoutInflater inflater = getLayoutInflater();
                                View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                                alert.setView(v);
                                //CREAMOS EL DIALOGO
                                alert.create();
                                final AlertDialog mDialog = alert.show();
                                TextView tv1ok = v.findViewById(R.id.tv1_dialog_ok);
                                tv1ok.setText("Debes esperar a tener vidas completas para adquirir este producto");

                                Button btnok = v.findViewById(R.id.btnOk);
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mDialog.dismiss();
                                    }
                                });
                            }else if(isPowerActive != 1){
                                //Cerramos el aviso anterior
                                mDialog.dismiss();
                                //Mostramos un aviso que ha comprado
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                                LayoutInflater inflater = getLayoutInflater();
                                View v = inflater.inflate(R.layout.dialog_inform_purchased, null);
                                alert.setView(v);
                                //CREAMOS EL DIALOGO
                                alert.create();
                                final AlertDialog mDialog = alert.show();
                                TextView tv1ok = v.findViewById(R.id.tv1_dialog_ok);
                                tv1ok.setText("Has adquirido 1");

                                imgProducto = v.findViewById(R.id.imageofproduct);
                                TypedValue value = new TypedValue();
                                getResources().getValue(R.drawable.ic_heart_blue, value, true);
                                imgProducto.setImageDrawable(getResources().getDrawable(value.resourceId));

                                Button btnok = v.findViewById(R.id.btnOk);
                                btnok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mDialog.dismiss();
                                    }
                                });
                                //Restamos las monedas
                                //CARGAMOS LA BASE DE DATOS
                                AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity().getApplicationContext());
                                SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                //Restamos las monedas
                                monedas = String.valueOf(Integer.parseInt(monedas) - costodelP);
                                values.put("monedas", monedas);
                                dbwrite.update("usuarios", values, "id = 1", null);
                                //Cerramos la Conexion
                                adminSQLiteOpenHelper.close();
                                //Restamos en el view
                                monedas_tv.setText(monedas);

                                //Metodo para activar el poder
                                powerUpInfiniteLives(true,getActivity());
                            }else{
                                Toast.makeText(getActivity().getApplicationContext(), "Ha Ocurrido un Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    Button btnno = v.findViewById(R.id.btnNo);
                    btnno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });


                }else{
                    //Mostramos un dialogo que no tiene suficiente dinero para comprar
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();

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

        //Cofre
        product_3 = v.findViewById(R.id.elemt_3);
        product_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lo que cuesta el producto1
                costodelP = 300;
                //Se usa condicional para comprobar si el usuario tiene las monedas requeridas
                if(Integer.parseInt(monedas) >= costodelP){
                    cantidadProductos = 1;
                    //MOSTRAR UN DIALOGO PARA CONFIRMAR LA COMPRA
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_confirm_purchase, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    //Seteamos la info de la compra en el dialogo
                    cost = v.findViewById(R.id.cost);
                    cost.setText(String.valueOf(costodelP));

                    quantityOfproduct = v.findViewById(R.id.quantityofproduct);
                    quantityOfproduct.setText(String.valueOf(cantidadProductos));

                    imgProducto = v.findViewById(R.id.imageofproduct);
                    TypedValue value = new TypedValue();
                    getResources().getValue(R.drawable.ic_tesoro_closed, value, true);
                    imgProducto.setImageDrawable(getResources().getDrawable(value.resourceId));

                    //Botones
                    Button btnsi = v.findViewById(R.id.btnSi);
                    btnsi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Cerramos el aviso anterior
                            mDialog.dismiss();
                            //Mostramos un aviso que ha comprado
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                            LayoutInflater inflater = getLayoutInflater();
                            View v = inflater.inflate(R.layout.dialog_inform_purchased, null);
                            alert.setView(v);
                            //CREAMOS EL DIALOGO
                            alert.create();
                            final AlertDialog mDialog = alert.show();
                            TextView tv1ok = v.findViewById(R.id.tv1_dialog_ok);
                            tv1ok.setText("Has adquirido 1");

                            imgProducto = v.findViewById(R.id.imageofproduct);
                            TypedValue value = new TypedValue();
                            getResources().getValue(R.drawable.ic_tesoro_closed, value, true);
                            imgProducto.setImageDrawable(getResources().getDrawable(value.resourceId));

                            Button btnok = v.findViewById(R.id.btnOk);
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDialog.dismiss();
                                }
                            });

                            //COMPRA 1 COFRE
                            //Añadimos el cofre en la BDD
                            //Quitamos las monedas de la BDD
                            //Conexion
                            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity().getApplicationContext());
                            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();

                            //Actualizar en la BDD
                            ContentValues values = new ContentValues();
                            cofres = String.valueOf(Integer.parseInt(cofres) + 1);
                            monedas = String.valueOf(Integer.parseInt(monedas) - costodelP);
                            values.put("cofres", cofres);
                            values.put("monedas", monedas);
                            dbwrite.update("usuarios", values, "id = 1", null);
                            //Cerramos la Conexion
                            adminSQLiteOpenHelper.close();

                            //Actualizar el Layout
                            //Volvemos visible
                            Btn_Objetos.setVisibility(View.VISIBLE);
                            //Steamos la nueva cantidad en los TV
                            cofres_tv.setText(cofres);
                            monedas_tv.setText(monedas);

                            //Play al sonido
                            int sonido = sound.load(getActivity(), R.raw.buy_object, 1);
                            try {
                                sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            sound.play(sonido, 1, 1, 0, 0, 1);


                        }
                    });

                    Button btnno = v.findViewById(R.id.btnNo);
                    btnno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });


                }else{
                    //Mostramos un dialogo que no tiene suficiente dinero para comprar
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();

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


        //Cofre
        product_4 = v.findViewById(R.id.elemt_4);
        product_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lo que cuesta el producto1
                costodelP = 10;
                //Se usa condicional para comprobar si el usuario tiene las monedas requeridas
                if(Integer.parseInt(monedas) >= costodelP){
                    cantidadProductos = 1;
                    //MOSTRAR UN DIALOGO PARA CONFIRMAR LA COMPRA
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_confirm_purchase, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();
                    //Seteamos la info de la compra en el dialogo
                    cost = v.findViewById(R.id.cost);
                    cost.setText(String.valueOf(costodelP));

                    quantityOfproduct = v.findViewById(R.id.quantityofproduct);
                    quantityOfproduct.setText(String.valueOf(cantidadProductos));

                    imgProducto = v.findViewById(R.id.imageofproduct);
                    TypedValue value = new TypedValue();
                    getResources().getValue(R.drawable.ic_key, value, true);
                    imgProducto.setImageDrawable(getResources().getDrawable(value.resourceId));

                    //Botones
                    Button btnsi = v.findViewById(R.id.btnSi);
                    btnsi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                            //Mostramos un aviso que ha comprado
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                            //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                            LayoutInflater inflater = getLayoutInflater();
                            View v = inflater.inflate(R.layout.dialog_inform_purchased, null);
                            alert.setView(v);
                            //CREAMOS EL DIALOGO
                            alert.create();
                            final AlertDialog mDialog = alert.show();
                            TextView tv1ok = v.findViewById(R.id.tv1_dialog_ok);
                            tv1ok.setText("Has adquirido 1");

                            imgProducto = v.findViewById(R.id.imageofproduct);
                            TypedValue value = new TypedValue();
                            getResources().getValue(R.drawable.ic_key, value, true);
                            imgProducto.setImageDrawable(getResources().getDrawable(value.resourceId));

                            Button btnok = v.findViewById(R.id.btnOk);
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDialog.dismiss();
                                }
                            });
                            //COMPRA 1 LLAVE
                                //Añadimos la llave en la BDD
                                //Quitamos las monedas de la BDD
                            //Conexion
                            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity().getApplicationContext());
                            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();

                            //Actualizar en la BDD
                            ContentValues values = new ContentValues();
                            llaves = String.valueOf(Integer.parseInt(llaves) + 1);
                            monedas = String.valueOf(Integer.parseInt(monedas) - costodelP);
                            values.put("llaves", llaves);
                            values.put("monedas", monedas);
                            dbwrite.update("usuarios", values, "id = 1", null);
                            //Cerramos la Conexion
                            adminSQLiteOpenHelper.close();

                            //Actualizar el Layout
                                //Volvemos visible
                            Btn_Objetos.setVisibility(View.VISIBLE);
                                //Steamos la nueva cantidad en los TV
                            llaves_tv.setText(llaves);
                            monedas_tv.setText(monedas);
                            //Play al sonido
                            int sonido = sound.load(getActivity(), R.raw.buy_object, 1);
                            try {
                                sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            sound.play(sonido, 1, 1, 0, 0, 1);
                        }
                    });

                    Button btnno = v.findViewById(R.id.btnNo);
                    btnno.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mDialog.dismiss();
                        }
                    });


                }else{
                    //Mostramos un dialogo que no tiene suficiente dinero para comprar
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                    LayoutInflater inflater = getLayoutInflater();
                    View v = inflater.inflate(R.layout.dialog_standart_ok, null);
                    alert.setView(v);
                    //CREAMOS EL DIALOGO
                    alert.create();
                    final AlertDialog mDialog = alert.show();

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
        //PROXIMAMENTE AÑADIR MAS


        return v;
    }
}