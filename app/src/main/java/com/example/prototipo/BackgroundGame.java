package com.example.prototipo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class BackgroundGame {
//
    private static String BDDEcuacion;
    private static String BDDEcuacionCompleta;
    private static int BDDOpcion1;
    private static int BDDOpcion2;
    private static int BDDOpcion3;
    private static int BDDOpcion4;
    private static int BDDResultado;

    public static String getBDDEcuacionCompleta() {
        return BDDEcuacionCompleta;
    }

    public static String getBDDEcuacion() {
        return BDDEcuacion;
    }

    public static int getBDDOpcion1() { return BDDOpcion1; }

    public static int getBDDOpcion2() {
        return BDDOpcion2;
    }

    public static int getBDDOpcion3() {
        return BDDOpcion3;
    }

    public static int getBDDOpcion4() {
        return BDDOpcion4;
    }

    public static int getBDDResultado() {
        return BDDResultado;
    }


    public static void SetGame(String tipodejuego, int dificultad) {
//PROBLEMAS DE MANERA AUTOMATICA CON NUMEROS ALEATORIOS
//DONDE SE ESTABLEZCAN LOS RANGOS DE ACUERDO AL NIVEL
//CALCULAR LOS RESULTADOS CON LA OPERACION Y GENERAR OPCIONES ALETORIAS DE ACUERDO A UN INTERVALO

        //SUMA
        if (tipodejuego.equals("suma")) {
            if (dificultad == 0) {
                //VARIABLES LOCALES "IF"
                int numeroOP1;
                int numeroOP2;
                int minimo;
                int selecter;

                //RAMDOM
                Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                numeroOP1 = nroAleatorio.nextInt(20) + 1;
                numeroOP2 = nroAleatorio.nextInt(20) + 1;
//CREAMOS EL RESULTADO
                BDDResultado = numeroOP1 + numeroOP2;

                BDDEcuacion = numeroOP1 + " + " + numeroOP2 + " = ?";

                BDDEcuacionCompleta = numeroOP1 + " + " + numeroOP2 + " = " + BDDResultado;

                //INTERVALO
                minimo = BDDResultado - 5;

                //OPCIONES
                nroAleatorio.setSeed(System.currentTimeMillis());
                BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                //PARA EVITAR QUE SE REPITAN
                while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                        (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                        (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                        (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                        (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                }

            }
            if (tipodejuego.equals("suma")) {
                if (dificultad == 1) {
                    //VARIABLES LOCALES "IF"
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;
                    int selecter;

                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(50) + 1;
                    numeroOP2 = nroAleatorio.nextInt(50) + 1;

//CREAMOS EL RESULTADO
                    BDDResultado = numeroOP1 + numeroOP2;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    }


                    BDDEcuacion = numeroOP1 + " + " + numeroOP2 + " = ?";

                    BDDEcuacionCompleta = numeroOP1 + " + " + numeroOP2 + " = " + BDDResultado;


                }
            }
            if (tipodejuego.equals("suma")) {
                if (dificultad == 2) {
                    //VARIABLES LOCALES "IF"
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;
                    int selecter;

                    //RAMDOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(200) + 1;
                    numeroOP2 = nroAleatorio.nextInt(200) + 1;
//CREAMOS EL RESULTADO
                    BDDResultado = numeroOP1 + numeroOP2;

                    BDDEcuacion = numeroOP1 + " + " + numeroOP2 + " = ?";

                    BDDEcuacionCompleta = numeroOP1 + " + " + numeroOP2 + " = " + BDDResultado;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(10) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(10) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(10) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(10) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(10) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(10) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(10) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(10) + minimo + 1;
                    }

                    //ESTE ALGORITMO ES PARA RESTARLE ALETORIAMENTE A UNA OPCION 100 o 10, PARA CONFUDIR MAS
                    selecter = nroAleatorio.nextInt(10) + 1;

                    if (selecter == 1){
                        if(BDDResultado > 100) BDDOpcion1 = BDDResultado - 100;
                        else BDDOpcion1 = BDDResultado - 10;
                    }
                    if (selecter == 2){
                        if(BDDResultado > 100) BDDOpcion2 = BDDResultado - 100;
                        else BDDOpcion2 = BDDResultado - 10;
                    }
                    if (selecter == 3){
                        if(BDDResultado > 100) BDDOpcion3 = BDDResultado - 100;
                        else BDDOpcion3 = BDDResultado - 10;
                    }
                    if (selecter == 4){
                        if(BDDResultado > 100) BDDOpcion4 = BDDResultado - 100;
                        else BDDOpcion4 = BDDResultado - 10;
                    }
                    if (selecter > 4) {

                    }





                }
            }
        }

        //RESTA
        if (tipodejuego.equals("resta")) {
            if (dificultad == 0) {
                String Ecuacion;
                int numeroOP1;
                int numeroOP2;
                int minimo;

                //RAMFOM
                Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                numeroOP1 = nroAleatorio.nextInt(20) + 1;
                numeroOP2 = nroAleatorio.nextInt(20) + 1;
//CREAMOS EL RESULTADO
                BDDResultado = numeroOP1 - numeroOP2;

                //INTERVALO
                minimo = BDDResultado - 5;

                //OPCIONES
                nroAleatorio.setSeed(System.currentTimeMillis());
                BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                //PARA EVITAR QUE SE REPITAN
                while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                        (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                        (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                        (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                        (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                }


                BDDEcuacion = numeroOP1 + " - " + numeroOP2 + " = ?";

                BDDEcuacionCompleta = numeroOP1 + " - " + numeroOP2 + " = " + BDDResultado;

            }
            if (tipodejuego.equals("resta")) {
                if (dificultad == 1) {
                    String Ecuacion;
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;

                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(50) + 1;
                    numeroOP2 = nroAleatorio.nextInt(50) + 1;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    }


//CREAMOS EL RESULTADO
                    BDDResultado = numeroOP1 - numeroOP2;

                    BDDEcuacion = numeroOP1 + " - " + numeroOP2 + " = ?";

                    BDDEcuacionCompleta = numeroOP1 + " - " + numeroOP2 + " = " + BDDResultado;


                }
            }
            if (tipodejuego.equals("resta")) {
                if (dificultad == 2) {
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;

                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(200) + 1;
                    numeroOP2 = nroAleatorio.nextInt(200) + 1;
//CREAMOS EL RESULTADO
                    BDDResultado = numeroOP1 - numeroOP2;

                    BDDEcuacion = numeroOP1 + " - " + numeroOP2 + " = ?";

                    BDDEcuacionCompleta = numeroOP1 + " - " + numeroOP2 + " = " + BDDResultado;

                    //INTERVALO

                    minimo = BDDResultado - 5;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    }




                }
            }
        }


        //MULTILICACION
        if (tipodejuego.equals("multi")) {
            if (dificultad == 0) {
                String Ecuacion;
                int numeroOP1;
                int numeroOP2;
                int minimo;

                //RAMFOM
                Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                numeroOP1 = nroAleatorio.nextInt(10) + 1;
                numeroOP2 = nroAleatorio.nextInt(10) + 1;


//CREAMOS EL RESULTADO
                BDDResultado = numeroOP1 * numeroOP2;

                //INTERVALO
                minimo = BDDResultado - 5;

                //OPCIONES
                nroAleatorio.setSeed(System.currentTimeMillis());
                BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                //PARA EVITAR QUE SE REPITAN
                while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                        (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                        (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                        (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                        (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                }


                BDDEcuacion = numeroOP1 + " x " + numeroOP2 + " = ?";

                BDDEcuacionCompleta = numeroOP1 + " x " + numeroOP2 + " = " + BDDResultado;

            }
            if (tipodejuego.equals("multi")) {
                if (dificultad == 1) {
                    String Ecuacion;
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;
                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    //GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(50) + 1;
                    numeroOP2 = nroAleatorio.nextInt(50) + 1;


//CREAMOS EL RESULTADO
                    BDDResultado = numeroOP1 * numeroOP2;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    }


                    BDDEcuacion = numeroOP1 + " x " + numeroOP2 + " = ?";

                    BDDEcuacionCompleta = numeroOP1 + " x " + numeroOP2 + " = " + BDDResultado;


                }
            }
            if (tipodejuego.equals("multi")) {
                if (dificultad == 2) {
                    String Ecuacion;
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;

                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(200) + 1;
                    numeroOP2 = nroAleatorio.nextInt(200) + 1;
//CREAMOS EL RESULTADO
                    BDDResultado = numeroOP1 * numeroOP2;

                    BDDEcuacion = numeroOP1 + " x " + numeroOP2 + " = ?";

                    BDDEcuacionCompleta = numeroOP1 + " x " + numeroOP2 + " = " + BDDResultado;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    }




                }
            }
        }



        //DIVISION
        if (tipodejuego.equals("divi")) {
            if (dificultad == 0) {
                String Ecuacion;
                int numeroOP1;
                int numeroOP2;
                int minimo;

                //RAMFOM
                Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                numeroOP1 = nroAleatorio.nextInt(10) + 1;
                numeroOP2 = nroAleatorio.nextInt(10) + 1;


//CREAMOS EL RESULTADO
                BDDResultado = numeroOP1 / numeroOP2;

                //INTERVALO
                minimo = BDDResultado - 5;

                //OPCIONES
                nroAleatorio.setSeed(System.currentTimeMillis());
                BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                //PARA EVITAR QUE SE REPITAN
                while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                        (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                        (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                        (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                        (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                }


                BDDEcuacion = numeroOP1 + " ÷ " + numeroOP2 + " = ?";

                BDDEcuacionCompleta = numeroOP1 + " ÷ " + numeroOP2 + " = " + BDDResultado;

            }
            if (tipodejuego.equals("divi")) {
                if (dificultad == 1) {
                    String Ecuacion;
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;

                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(50) + 1;
                    numeroOP2 = nroAleatorio.nextInt(50) + 1;

//CREAMOS EL RESULTADO
                    BDDResultado = numeroOP1 / numeroOP2;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    }


                    BDDEcuacion = numeroOP1 + " ÷ " + numeroOP2 + " = ?";

                    BDDEcuacionCompleta = numeroOP1 + " ÷ " + numeroOP2 + " = " + BDDResultado;


                }
            }
            if (tipodejuego.equals("divi")) {
                if (dificultad == 2) {
                    String Ecuacion;
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;

                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(200) + 1;
                    numeroOP2 = nroAleatorio.nextInt(200) + 1;
//CREAMOS EL RESULTADO
                    BDDResultado = numeroOP1 / numeroOP2;

                    BDDEcuacion = numeroOP1 + " ÷ " + numeroOP2 + " = ?";

                    BDDEcuacionCompleta = numeroOP1 + " ÷ " + numeroOP2 + " = " + BDDResultado;

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(20) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(20) + minimo + 1;
                    }




                }
            }


        }

        //COMBINADO
        if (tipodejuego.equals("combinado")) {
            //NO HAY FACIl
            //En el normal hay 3 terminos y en el dificil hay 4
            //Como tanto los numeros y las operaciones son ALEATORIAS
                if (dificultad == 1) {
                    //VARIABLES LOCALES "IF"
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;
                    int OPCION_OPERADOR1;
                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(200) + 1;
                    numeroOP2 = nroAleatorio.nextInt(200) + 1;
                    //String de la ecuacion
                    String res = "";
                    //OPERACION1
                    OPCION_OPERADOR1 = nroAleatorio.nextInt(4) + 1;
                    if(OPCION_OPERADOR1 == 1){
                        //SUMA
                        BDDResultado = numeroOP1 + numeroOP2;
                        res = numeroOP1 + " + " + numeroOP2;
                    }
                    else if(OPCION_OPERADOR1 == 2){
                        //RESTA
                        BDDResultado = numeroOP1 - numeroOP2;
                        res = numeroOP1 + " - " + numeroOP2;
                    }else if(OPCION_OPERADOR1 == 3){
                        //DIVISION
                        BDDResultado = numeroOP1 / numeroOP2;
                        res = numeroOP1 + " ÷ " + numeroOP2;
                    }else if(OPCION_OPERADOR1 == 4){
                        //MULTIPLICACION
                        BDDResultado = numeroOP1 * numeroOP2;
                        res = numeroOP1 + " x " + numeroOP2;
                    }
                    //GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES

                    //INTERVALO
                    minimo = BDDResultado - 5;

                    //OPCIONES
                    nroAleatorio.setSeed(System.currentTimeMillis());
                    BDDOpcion1 = nroAleatorio.nextInt(10) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(10) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(10) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(10) + minimo + 1;
                    //PARA EVITAR QUE SE REPITAN
                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(10) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(10) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(10) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(10) + minimo + 1;
                    }


//CREAMOS EL RESULTADO
                    BDDEcuacion = res + " = ?";

                    BDDEcuacionCompleta = res + " = " + BDDResultado;



                }
                if (dificultad == 2) {
                    //VARIABLES LOCALES "IF"
                    int numeroOP1;
                    int numeroOP2;
                    int minimo;
                    int OPCION_OPERADOR1;
                    //RAMFOM
                    Random nroAleatorio = new Random(System.currentTimeMillis());
//GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES
                    numeroOP1 = nroAleatorio.nextInt(300) + 1;
                    numeroOP2 = nroAleatorio.nextInt(300) + 1;
                    //String de la ecuacion
                    String res = "";
                    //OPERACION1
                    OPCION_OPERADOR1 = nroAleatorio.nextInt(4) + 1;
                    if(OPCION_OPERADOR1 == 1){
                        //SUMA
                        BDDResultado = numeroOP1 + numeroOP2;
                        res = numeroOP1 + " + " + numeroOP2;
                    }
                    else if(OPCION_OPERADOR1 == 2){
                        //RESTA
                        BDDResultado = numeroOP1 - numeroOP2;
                        res = numeroOP1 + " - " + numeroOP2;
                    }else if(OPCION_OPERADOR1 == 3){
                        //DIVISION
                        BDDResultado = numeroOP1 / numeroOP2;
                        res = numeroOP1 + " ÷ " + numeroOP2;
                    }else if(OPCION_OPERADOR1 == 4){
                        //MULTIPLICACION
                        BDDResultado = numeroOP1 * numeroOP2;
                        res = numeroOP1 + " x " + numeroOP2;
                    }
                    //GENERAMOS LOS NROS ALETORIOS PRINCIPALES Y LAS OPCIONES

                    minimo = BDDResultado - 10;

                    nroAleatorio.setSeed(System.currentTimeMillis());

                    BDDOpcion1 = nroAleatorio.nextInt(15) + minimo + 1;
                    BDDOpcion2 = nroAleatorio.nextInt(15) + minimo + 1;
                    BDDOpcion3 = nroAleatorio.nextInt(15) + minimo + 1;
                    BDDOpcion4 = nroAleatorio.nextInt(15) + minimo + 1;

                    while((BDDOpcion1 == BDDResultado || BDDOpcion2 == BDDResultado || BDDOpcion3 == BDDResultado || BDDOpcion4 == BDDResultado) ||
                            (BDDOpcion1 == BDDOpcion2 || BDDOpcion1 == BDDOpcion3 || BDDOpcion1 == BDDOpcion4) ||
                            (BDDOpcion2 == BDDOpcion1 || BDDOpcion2 == BDDOpcion3 || BDDOpcion2 == BDDOpcion4) ||
                            (BDDOpcion3 == BDDOpcion1 || BDDOpcion3 == BDDOpcion2 || BDDOpcion3 == BDDOpcion4) ||
                            (BDDOpcion4 == BDDOpcion1 || BDDOpcion4 == BDDOpcion2 || BDDOpcion4 == BDDOpcion3)){
                        BDDOpcion1 = nroAleatorio.nextInt(15) + minimo + 1;
                        BDDOpcion2 = nroAleatorio.nextInt(15) + minimo + 1;
                        BDDOpcion3 = nroAleatorio.nextInt(15) + minimo + 1;
                        BDDOpcion4 = nroAleatorio.nextInt(15) + minimo + 1;
                    }

//CREAMOS EL RESULTADO
                    BDDEcuacion = res + " = ?";

                    BDDEcuacionCompleta = res + " = " + BDDResultado;



                }
        }

    }

    public static boolean isPar(int n){
        return (n % 2) == 0;
    }
}


