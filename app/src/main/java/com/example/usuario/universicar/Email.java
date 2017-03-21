package com.example.usuario.universicar;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Email extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView txtorigen, txtdestino, txtasunto, txtfecha, txttexto;

    private static int posicionTarjeta;

    public static ViewFlipper vfapp;
    Vibrator vib;
    public static RecyclerView recyclerenviados;
    public LinearLayoutManager managerRecyclerEnviados;
    public static RecyclerView recyclerrecibidos;
    public LinearLayoutManager managerRecyclerRecibidos;
    private RecyclerView recyclerforo;
    public LinearLayoutManager managerRecyclerForo;
    EditText edemail;
    EditText edenviar;
    private TabHost th;
    Button btnenviar;


    TextView txtenviados, txtrecibidos;

    private String nombrePropietario;
    EditText ednuevocorreo;
    final ArrayList<Mensaje> listamensajesenviados = new ArrayList();
    final ArrayList<MensajeForo> listamensajesforo = new ArrayList();
    final static ArrayList<Mensaje> listamensajesrecibidos = new ArrayList();
    final ArrayList<Mensaje> objetosstr = new ArrayList();
    ArrayList<Integer> numeros = new ArrayList<>();
    Spinner spcontactos;
    private ArrayList<String> listaNombres = new ArrayList<>();
    private ArrayList<String> listaTexto = new ArrayList<>();
    private long numeroDeMensaje;
    private EditText eddestino;
    static ViewPager pager;
    private EditText edasunto;
    private Toast mToast;
    public ViajesConImagen conquiencontactar;

    EditText edenviarforo;
    Button btnenviarforo;
    boolean estoyenemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        setTitle("Bandeja de entrada");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtorigen = (TextView) findViewById(R.id.txtorigen);
        txtdestino = (TextView) findViewById(R.id.txtdestinoo);
        txttexto = (TextView) findViewById(R.id.txttexto);
        txtasunto = (TextView) findViewById(R.id.txtasunto);
        txtfecha = (TextView) findViewById(R.id.txtfecha);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        vfapp = (ViewFlipper) findViewById(R.id.vfapp);

        btnenviar = (Button) findViewById(R.id.btnenviar);
        edasunto = (EditText) findViewById(R.id.edasunto);

        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        edenviar = (EditText) findViewById(R.id.edenviar);
        eddestino = (EditText) findViewById(R.id.eddestino);
        nombrePropietario = LoginActivity.nombrePropietario;


        btnenviarforo = (Button) findViewById(R.id.btnenviarforo);
        edenviarforo = (EditText) findViewById(R.id.edenviarforo);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        establecerTransiciones();


        recogerMensajesEnviadosFirebase();
        recogerMensajesRecibidosFirebase();
        recogerMensajesFOROFirebase();

        if (AbrirMensaje.responderMensaje) {
            if (AbrirMensaje.dedondeabrimos.equals("enviados")) {
                setTitle("Escribir de nuevo a " + AbrirMensaje.destino);
                eddestino.setText(AbrirMensaje.destino);
                edasunto.setText("Quiero viajar contigo!");
                edenviar.setText("");
            }
            if (AbrirMensaje.dedondeabrimos.equals("recibidos")) {
                setTitle("Responder a " + AbrirMensaje.origen);
                eddestino.setText(AbrirMensaje.origen);
                edasunto.setText("Quiero viajar contigo!");
                edenviar.setText("");

            }
            vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.rlescribircorreo)));
            AbrirMensaje.responderMensaje = false;
            AbrirMensaje.dedondeabrimos="";

        } else {
            if (LoginActivity.abriremail) {
                setTitle("Foro UniVersicar");
                vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.rlforo)));

            }

            if (!LoginActivity.abriremail) {
                setTitle("Nuevo mensaje");
                conquiencontactar = RecyclerAdaptadorViajes.viajeconimagen;
                vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.rlescribircorreo)));
                eddestino.setText(conquiencontactar.getUsuario());
                edasunto.setText("Quiero viajar contigo!");
                edenviar.setText("Contacto contigo por que estoy interesado en tu viaje con origen  \n " + RecyclerAdaptadorViajes.viajeconimagen.getOrigen() + " y destino " + RecyclerAdaptadorViajes.viajeconimagen.getDestino());
            }
        }


        LoginActivity.abriremail = false;


        listeners();


    }

    private void recogerMensajesFOROFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Mensajes foro");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listamensajesforo.clear();
                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {
                    MensajeForo mensajeForo = iterador.next().getValue(MensajeForo.class);
                    listamensajesforo.add(mensajeForo);
                }
                cergarMensajesForo();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void cergarMensajesForo() {
        recyclerforo = (RecyclerView) findViewById(R.id.reciclador);

        managerRecyclerForo = new LinearLayoutManager(this);
        recyclerforo.setLayoutManager(managerRecyclerForo);


        RecyclerAdaptadorForo adarecyclerforo = new RecyclerAdaptadorForo(this, listamensajesforo);
        recyclerforo.setAdapter(adarecyclerforo);

    }

    private void establecerTransiciones() {

        int position = getIntent().getIntExtra("contactar", -1);
        Window window = getWindow();

        switch (position) {
            // EXPLODE
            case 0:
                Explode t0 = new Explode();
                window.setEnterTransition(t0);
                break;
            // SLIDE
            case 1:
                Slide t1 = new Slide();
                t1.setSlideEdge(Gravity.END);
                window.setEnterTransition(t1);
                break;
            // FADE

            case 2:
                Fade t2 = new Fade();
                window.setEnterTransition(t2);
                break;
            // PERSONALIZADA
            case 3:
                Transition t3 = TransitionInflater.from(this).inflateTransition(R.transition.detail_enter_trasition);
                window.setEnterTransition(t3);
                break;
            case 4:
                Fade t4 = new Fade();
                t4.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {

                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {

                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {

                    }

                    @Override
                    public void onTransitionPause(Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(Transition transition) {

                    }
                });
                window.setEnterTransition(t4);
                break;
            // POR DEFECTO
            case 5:
                window.setEnterTransition(null);
                break;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void launch(Activity context, int position) {
        posicionTarjeta = position;

        Intent intent = new Intent(context, Email.class);
        intent.putExtra("contactar", position);

        ActivityOptions options0 = ActivityOptions.makeSceneTransitionAnimation(context);
        context.startActivity(intent, options0.toBundle());


    }


    private String fecha() {

        Calendar calendario = new GregorianCalendar();
        int mes, dia, hora, minuto, segundo;

        Calendar rightNow = calendario.getInstance();

        mes = calendario.get(Calendar.MONTH);
        mes += 1;
        dia = rightNow.get(Calendar.DAY_OF_MONTH);
        hora = rightNow.get(Calendar.HOUR_OF_DAY);
        minuto = rightNow.get(Calendar.MINUTE);
        segundo = rightNow.get(Calendar.SECOND);

        String fecha = hora + ":" + minuto + ":" + segundo + "  " + dia + " " + mes;

        return fecha;
    }


    private void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    private void listeners() {

        btnenviarforo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String texto = edenviarforo.getText().toString();
                String origen = nombrePropietario;
                String fecha = fecha();


                MensajeForo mensajeforo = new MensajeForo(texto, origen, fecha);
                insertarObjetoMensajeForo(mensajeforo);

                edenviarforo.setText("");

            }
        });


        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vib.vibrate(60);
                showToast("Mensaje enviado!");

                String destino = eddestino.getText().toString();
                String texto = edenviar.getText().toString();
                String fecha = fecha();

                String asunto = edasunto.getText().toString();
                Mensaje m = new Mensaje(texto, nombrePropietario, destino, fecha, asunto, "no", "no", "0");

                eddestino.setText("");
                edenviar.setText("");
                edasunto.setText("");
                insertarObjeto(m);


            }
        });


    }

    private void insertarObjetoMensajeForo(MensajeForo mensajeforo) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Mensajes foro");

        Map m = new HashMap<>();
        m.put(myRef.push().getKey(), mensajeforo);
        myRef.updateChildren(m);

    }

    private void recogerMensajesRecibidosFirebase() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Mensajes");
        Query q = myRef.orderByChild("destino").equalTo(nombrePropietario);


        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listamensajesrecibidos.clear();
                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {
                    Mensaje o = iterador.next().getValue(Mensaje.class);
                    listamensajesrecibidos.add(o);
                }
                cergarMensajesRecibidos();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void recogerMensajesEnviadosFirebase() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Mensajes");
        Query q = myRef.orderByChild("origen").equalTo(nombrePropietario);


        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listamensajesenviados.clear();
                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {
                    Mensaje o = iterador.next().getValue(Mensaje.class);
                    listamensajesenviados.add(o);
                }
                cergarMensajesEnviados();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    private void cergarMensajesRecibidos() {

        recyclerrecibidos = (RecyclerView) findViewById(R.id.recyclerrecibidos);
        managerRecyclerRecibidos = new LinearLayoutManager(this);
        recyclerrecibidos.setLayoutManager(managerRecyclerRecibidos);
        Collections.reverse(listamensajesrecibidos);
        RecyclerAdaptadorEmailRecibidos adarecibidos = new RecyclerAdaptadorEmailRecibidos(this, listamensajesrecibidos);
        recyclerrecibidos.setAdapter(adarecibidos);

    }

    public void cergarMensajesEnviados() {
        recyclerenviados = (RecyclerView) findViewById(R.id.recyclerenviados);
        managerRecyclerEnviados = new LinearLayoutManager(this);
        recyclerenviados.setLayoutManager(managerRecyclerEnviados);
        Collections.reverse(listamensajesenviados);

        RecyclerAdaptadorEmailEnviados adaenviados = new RecyclerAdaptadorEmailEnviados(this, listamensajesenviados);
        recyclerenviados.setAdapter(adaenviados);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void insertarObjeto(Mensaje o) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Mensajes");

        String clave = myRef.push().getKey();

        o.setClave(clave);

        Map m = new HashMap<>();
        m.put(myRef.push().getKey(), o);
        myRef.updateChildren(m);


    }

    private void animacion(int num) {
        switch (num) {
            case 1:
                vfapp.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.leftin));
                vfapp.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.leftout));


                break;
            case 2:
                vfapp.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.fadein));
                vfapp.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.fadeout));


                break;
            case 3:
                vfapp.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slideup));
                vfapp.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.slidedown));


                break;
            case 4:
                vfapp.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.zoombackin));
                vfapp.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.zoombackout));


                break;
            case 5:
                vfapp.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomout));
                vfapp.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.zoomin));
                break;
            case 6:
                vfapp.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.hacialaderecha));
                vfapp.setOutAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.zoomin));
                break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    private void abrir(MenuItem item) {

        if (item.toString().equals("Foro Universicar")) {
            vib.vibrate(90);
            animacion(4);
            setTitle("Foro UniversiCar");


            vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.rlforo)));
        }

        if (item.toString().equals("Enviados")) {
            vib.vibrate(90);
            animacion(4);
            setTitle("Enviados");

            recogerMensajesEnviadosFirebase();
            cergarMensajesEnviados();

            vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.rlenviados)));
        }
        if (item.toString().equals("Recibidos")) {
            vib.vibrate(90);
            animacion(5);


            recogerMensajesRecibidosFirebase();
            cergarMensajesRecibidos();

            int ahora = vfapp.indexOfChild(vfapp.getCurrentView());

            avisarFirebaseMensajesVistos();
            cergarMensajesRecibidos();


            vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.rlrecibidos)));


            setTitle("Recibidos");

        }
        if (item.toString().equals("Escribir mensaje")) {
            vib.vibrate(90);
            setTitle("Escribir mensaje");


            cergarMensajesEnviados();

            animacion(2);
            vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.rlescribircorreo)));
        }


    }


    private void avisarFirebaseMensajesVistos() {


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query q = database.getReference("Mensajes").orderByChild("destino").equalTo(LoginActivity.nombrePropietario);

        q.addValueEventListener(new ValueEventListener() {

            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    dato.getRef().child("visto").setValue("si");

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        abrir(item);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        abrir(item);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
