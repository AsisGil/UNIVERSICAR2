package com.example.usuario.universicar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via settings/password.
 */

public class LoginActivity extends AppCompatActivity {

    public static boolean abriremail = false;


    Usuario user;
    Button btnregistrar;
    Vibrator vib;
    RelativeLayout activity_main;

    private FirebaseAuth aut;
    private EditText edemail;
    private EditText edpassword;
    private Button btnLogin;
    private TextView login_title;
    private String email;
    private String pwd;
    private boolean yaSeHaQuejado = false;
    public static String nombrePropietario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        aut = FirebaseAuth.getInstance();

        edemail = (EditText) findViewById(R.id.edemail);
        edpassword = (EditText) findViewById(R.id.edpassword);

        btnregistrar = (Button) findViewById(R.id.btnregistrar);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        //cambiar fuente
        fuente();
        fondoCambiante();

        listeners();
    }

    private void fuente() {
        String carpetaFuente = "fonts/Fine College.ttf";
        login_title = (TextView) findViewById(R.id.login_title);
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);
        login_title.setTypeface(fuente);
    }

    private void fondoCambiante() {
        AnimationDrawable anim = (AnimationDrawable) activity_main.getBackground();
        anim.getFrame(3);

        anim.setEnterFadeDuration(2000);
        anim.setExitFadeDuration(2000);
        anim.start();
    }


    public void listeners() {

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vib.vibrate(90);
                abrirActivityRegistrar();

            }
        });

        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                vib.vibrate(90);
                PosicionActual.solounavez = false;


                email = edemail.getText().toString();
                String contraseña = edpassword.getText().toString();


                boolean quejado = false;

                if (email.equals("")) {
                    toast("Introduca un settings");
                    quejado = true;

                }
                if (contraseña.equals("") & !quejado) {
                    toast("Introduzca una contraseña");
                    quejado = true;
                }
                if (!quejado)
                    hacerLogIn(email, contraseña);


            }
        });
    }

    public void abrirActivityRegistrar() {

        Intent i = new Intent(this, RegisterActivity.class);
        overridePendingTransition(R.anim.zoombackin, R.anim.zoombackout);

        startActivity(i);
    }


    public void onBackPressed() {

        alertaSalir("¿Desea salir de la app?");


    }

    private void toast(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void alertaSalir(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setMessage(s);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Salir",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                        finish();
                    }
                }
        );
        alertDialog.show();
    }


    private void hacerLogIn(final String email, final String contraseña) {
        yaSeHaQuejado = false;

        aut.signInWithEmailAndPassword(email, contraseña).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                boolean aux = task.isSuccessful();

                if (aux && !yaSeHaQuejado) {
                    nombrePropietario = email;

                    abrirPantallaCentral();

                } else if (!yaSeHaQuejado)
                    toast("Fallo de autenticacion");


            }


        });

    }


    private void abrirPantallaCentral() {
        Intent i = new Intent(this, Pantallacentral.class);
        startActivity(i);
    }

    public Usuario getUsuarioLogIn() {
        return user;
    }
}