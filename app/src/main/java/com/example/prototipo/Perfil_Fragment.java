package com.example.prototipo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import static com.example.prototipo.MainActivity.monedas;
import static com.example.prototipo.MainActivity.nombre;

/*FRAGMENT PERFIL----------------------------------
 * Aca puedes ver tus logoros, y colocar alguna informacion acerca de ti*/
public class Perfil_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_perfil_, container, false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        Button btn = v.findViewById(R.id.btnEditName);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialogo con un input
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                LayoutInflater inflater = getLayoutInflater();
                final View v_dialog = inflater.inflate(R.layout.dialog_input_user, null);
                alert.setView(v_dialog);
                //CREAMOS EL DIALOGO
                alert.create();
                final AlertDialog mDialog = alert.show();

                Button btn = v_dialog.findViewById(R.id.btnOk);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                        //Agarramos el Texto y lo guardamos
                        EditText editText = v_dialog.findViewById(R.id.edit_user);
                        String usuarioInput = editText.getText().toString().trim();

                        if(usuarioInput.length() > 10){
                            mDialog.dismiss();
                            //Mostramos el dialogo que el texto debe ser menor a 10
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                            //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                            LayoutInflater inflater = getLayoutInflater();
                            View v_ = inflater.inflate(R.layout.dialog_standart_ok, null);
                            alert.setView(v_);
                            //CREAMOS EL DIALOGO
                            alert.create();
                            final AlertDialog mDialog = alert.show();
                            //Texto
                            TextView tv1 = v_.findViewById(R.id.tv1_dialog_ok);
                            tv1.setText("Ingresar más de 10 caracteres no esta permitido, intentalo nuevamente");

                            Button btnok = v_.findViewById(R.id.btnOk);
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDialog.dismiss();
                                }
                            });

                        }else{
                            //Guardamos los datos Obtenidos
                            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
                            SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();
                            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
                            nombre = usuarioInput;
                            ContentValues values = new ContentValues();
                            values.put("nombre", nombre);
                            dbwrite.update("usuarios", values, "id = 1", null);
                            adminSQLiteOpenHelper.close();

                            //Mostramos el dialogo que el texto debe ser menor a 10
                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                            //Inflater para enlazar el layout del dialogo con la variable, para mostrarlo y crear el listener
                            LayoutInflater inflater = getLayoutInflater();
                            View v_ = inflater.inflate(R.layout.dialog_standart_ok, null);
                            alert.setView(v_);
                            //CREAMOS EL DIALOGO
                            alert.create();
                            final AlertDialog mDialog = alert.show();
                            TextView tv1 = v_.findViewById(R.id.tv1_dialog_ok);
                            tv1.setText("Nombre actualizado Correctamente");

                            Button btnok = v_.findViewById(R.id.btnOk);
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    mDialog.dismiss();
                                }
                            });

                            TextView tvname = v.findViewById(R.id.inputName);
                            if (nombre == "") {

                                tvname.setText("Usuario");
                            }else{
                                tvname.setText(nombre+"!");
                            }


                        }




                    }
                });



            }
        });

        //Comprobar logros

        comprobarLogro("fiebreoro", v);
        comprobarLogro("matematicoexp", v);
        comprobarLogro("matematicoprinc", v);

        TextView tvname = v.findViewById(R.id.inputName);
        if (nombre == "") {
            tvname.setText("Usuario");

        }else{
            tvname.setText(nombre+"!");
        }



        return v;
    }


    public Boolean comprobarLogro(String logro, View view) {
        //If para cada logro
        if (logro == "fiebreoro") {
            //Comprobamos en la base de datos
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

            //Primero comprobamos que tenga ya el logro
            String sql2 = "SELECT * FROM logros";
            Cursor c2 = dbread.rawQuery(sql2, null);
            //Si encontro, recogemos los datos
            int isFiebreOro = 0;
            if (c2.moveToNext()) {
                isFiebreOro = c2.getInt(c2.getColumnIndex("fiebreoro"));
            }

            if (isFiebreOro == 1) {
                TextView tv1 = view.findViewById(R.id.tv_logro1);
                tv1.setText("Fiebre de Oro");

                TextView tv1_desc = view.findViewById(R.id.tv_logro1_desc);
                tv1_desc.setText("Completado");

                ImageView img1 = view.findViewById(R.id.logro1_img);
                TypedValue value = new TypedValue();
                getResources().getValue(R.drawable.ic_money_bag, value, true);
                img1.setImageDrawable(getResources().getDrawable(value.resourceId));

                return true;
            }

            return false;
        }
        if (logro == "matematicoprinc") {
            //Comprobamos en la base de datos
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

            //Primero comprobamos que tenga ya el logro
            String sql2 = "SELECT * FROM logros";
            Cursor c2 = dbread.rawQuery(sql2, null);
            //Si encontro, recogemos los datos
            int isMatematicoPrinc = 0;
            if (c2.moveToNext()) {
                isMatematicoPrinc = c2.getInt(c2.getColumnIndex("matematicoprincipiante"));
            }

            if (isMatematicoPrinc == 1) {
                //Pintamos el logro
                TextView tv2 = view.findViewById(R.id.tv_logro2);
                tv2.setText("Matematico Principiante");

                TextView tv1_desc = view.findViewById(R.id.tv_logro2_desc);
                tv1_desc.setText("Completado");

                ImageView img1 = view.findViewById(R.id.logro2_img);
                TypedValue value = new TypedValue();
                getResources().getValue(R.drawable.ic_star, value, true);
                img1.setImageDrawable(getResources().getDrawable(value.resourceId));
                return true;
            }
            return false;
        }

        if (logro == "matematicoexp") {
            //Comprobamos en la base de datos
            AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(getActivity());
            SQLiteDatabase dbwrite = adminSQLiteOpenHelper.getWritableDatabase();
            SQLiteDatabase dbread = adminSQLiteOpenHelper.getReadableDatabase();

            //Primero comprobamos que tenga ya el logro
            String sql2 = "SELECT * FROM logros";
            Cursor c2 = dbread.rawQuery(sql2, null);
            //Si encontro, recogemos los datos
            int isMatematicoExperto = 0;
            if (c2.moveToNext()) {
                isMatematicoExperto = c2.getInt(c2.getColumnIndex("matematicoexperto"));
            }

            if (isMatematicoExperto == 1) {
                //Pintamos el logro
                TextView tv2 = view.findViewById(R.id.tv_logro3);
                tv2.setText("Matematico Experto");

                TextView tv1_desc = view.findViewById(R.id.tv_logro3_desc);
                tv1_desc.setText("Completado");

                ImageView img1 = view.findViewById(R.id.logro3_img);
                TypedValue value = new TypedValue();
                getResources().getValue(R.drawable.ic_maths, value, true);
                img1.setImageDrawable(getResources().getDrawable(value.resourceId));
                return true;
            }
            return false;
        }


        return false;
    }
}