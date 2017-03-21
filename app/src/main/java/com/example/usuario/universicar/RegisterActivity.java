package com.example.usuario.universicar;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via settings/password.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    //-----------------------------------------
    private FirebaseAuth aut;
    private EditText usuario;
    private EditText rep_password;
    private EditText telefono;
    private CheckBox coche;
    private final String EMAIL = "administrador@hotmail.com";
    private final String PWD = "administrador";
    private Button btnregistrar;
    private boolean usr_rep = true;
    private ArrayList<Usuario> lista_usus;
    Vibrator vib;
    Usuario c;
    private LinearLayout activity_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        lista_usus = new ArrayList<>();
        usuario = (EditText) findViewById(R.id.usuario);
        rep_password = (EditText) findViewById(R.id.rep_password);
        telefono = (EditText) findViewById(R.id.telefono);
        coche = (CheckBox) findViewById(R.id.checkBoxCoche);
        aut = FirebaseAuth.getInstance();
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.edpassword);
        btnregistrar = (Button) findViewById(R.id.email_sign_in_button);
        activity_register = (LinearLayout) findViewById(R.id.activity_register);
        cargarVistas();
        fondoCambiante();
    }

    private void fondoCambiante() {
        AnimationDrawable anim = (AnimationDrawable) activity_register.getBackground();
        anim.setEnterFadeDuration(2000);
        anim.setExitFadeDuration(2000);
        anim.start();
    }


    public void comprobarRepeticionUsuario() {
        final String email = mEmailView.getText().toString().trim();
        String pwd = mPasswordView.getText().toString().trim();
        if (contraseñasIguales() && validarEmail()) {
            aut.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            boolean aux = task.isSuccessful();
                            if (aux) {
                                Toast.makeText(RegisterActivity.this, "Usuario creado!", Toast.LENGTH_SHORT).show();
                                insertarUsuario();
                                String user_id = aut.getCurrentUser().getUid();
                                limpiarTexto();
                                LoginActivity.nombrePropietario = email;

                                // insertarUsuario(c);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Email repetido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


        /*

        auth.createUserWithEmailAndPassword(settings, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        String nombre=inputName.getText().toString();
                        String apellido=inputSurname.getText().toString();
                        String settings=inputEmail.getText().toString();
                        Usuario user=new Usuario(nombre, apellido, settings);
                        setUser(user);

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Intent intent = new Intent(SignupActivity.this,SignupActivity2.class);
                            startActivity(intent);
                        }
                    }
                });



    }
});





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("usuarios");
        String usr = usuario.getText().toString();
        Query consulta = ref.orderByChild("usuario_string").equalTo(usr);

        consulta.addValueEventListener(new ValueEventListener() {
                                           @Override
                                           public void onDataChange(DataSnapshot dataSnapshot) {
                                               Iterable i = dataSnapshot.getChildren();
                                               Iterator<DataSnapshot> iterador = i.iterator();
                                               lista_usus.clear();
                                               if (iterador.hasNext()) {
                                                   Toast.makeText(getApplicationContext(), "Error usuario repetido",
                                                           Toast.LENGTH_SHORT).show();

                                               }
                                           }

                                           @Override
                                           public void onCancelled(DatabaseError databaseError) {

                                           }
                                       }

        );
        /*consulta.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //String key = dataSnapshot.getKey();
                //en u guardamos el resultado de la consulta
               // Usuario u = dataSnapshot.getValue(Usuario.class);
                usr_rep=false;
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.v("noREPE", "onChildChanged");
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.v("noREPE", "onChildRemoved");
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.v("noREPE", "onChildMoved");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("noREPE", "onCancelled");
            }
        });
        /*ref.addValueEventListener(new ValueEventListener() {
                                      @Override
                                      public void onDataChange(DataSnapshot dataSnapshot) {
                                          Iterable i = dataSnapshot.getChildren();
                                          Iterator<DataSnapshot> iterador = i.iterator();
                                          lista_usus.clear();
                                          while (iterador.hasNext()) {
                                              Usuario p = iterador.next().getValue(Usuario.class);
                                              lista_usus.add(p);
                                          }
                                      }
                                      @Override
                                      public void onCancelled(DatabaseError databaseError) {
                                      }
                                  }
        );*/


    }

    private void insertarUsuario() {


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Usuarios");

            String clave = myRef.push().getKey();



            Map m = new HashMap<>();
            m.put(myRef.push().getKey(), c);
            myRef.updateChildren(m);


    }

    public boolean validarEmail() {
        String email = mEmailView.getText().toString();
        boolean arroba = false, punto = false;
        for (int i = 0; i < email.length(); i++) {
            if (email.charAt(i) == '@') {
                arroba = true;
            } else {
                if (email.charAt(i) == '.') {
                    punto = true;
                }
            }

        }
        boolean aux = (arroba && punto);
        if (!aux) {
            Toast.makeText(getApplicationContext(), "El settings no es válido", Toast.LENGTH_SHORT).show();
        }
        return aux;

    }

    public boolean contraseñasIguales() {
        boolean aux = false;
        String contraseña = mPasswordView.getText().toString();
        if (mPasswordView.getText().toString().equals(rep_password.getText().toString())) {
            if (contraseña.length() > 6)
                aux = true;
            else
                Toast.makeText(getApplicationContext(), "La contraseña debe ser mayor de 6 caracteres", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();

        }
        return aux;
    }

    protected void cargarVistas() {
        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vib.vibrate(90);
                String usuario_string = usuario.getText().toString();
                String email_string = mEmailView.getText().toString();
                String password_string = mPasswordView.getText().toString();
                String rep_password_string = rep_password.getText().toString();
                String telefono_string = telefono.getText().toString();
                boolean coche_boolean = coche.isChecked();
                c = new Usuario(usuario_string, email_string, password_string, rep_password_string, telefono_string, coche_boolean);
                comprobarRepeticionUsuario();

            }
        });
    }

    private void limpiarTexto() {
        usuario.setText("");
        mEmailView.setText("");
        mPasswordView.setText("");
        rep_password.setText("");
        telefono.setText("");
        coche.setChecked(false);
        abrirMain();
    }

    private void abrirMain() {
        Intent i = new Intent(this, Pantallacentral.class);
        overridePendingTransition(R.anim.zoombackin, R.anim.zoombackout);
        startActivity(i);
    }
}