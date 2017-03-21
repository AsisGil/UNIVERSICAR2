package com.example.usuario.universicar;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class Pantallacentral extends AppCompatActivity implements GooeyMenu.GooeyMenuInterface, OnMapReadyCallback, DirectionFinderListener {

    // EditText edbuscarviaje;

    boolean botonida = false;
    boolean botonvuelta = false;
    boolean botontodos = false;


    static ArrayList<String> distancias = new ArrayList<>();
    static ArrayList<Double> distanciasenenteros = new ArrayList<>();

    ArrayList<Viaje> listaViajesPorCercania = new ArrayList<>();
    public static RecyclerView recyclerViajes;
    public static LinearLayoutManager managerRecyclerViajes;

    Button btnida, btntodos, btnvuelta;

    ArrayList<ViajesConImagen> stlistacondistanciasordenadas = new ArrayList<>();
    Vibrator vib;

    boolean lanzarpeti = true;
    public static List<ViajesConImagen> listaViajesConImagen = new ArrayList();
    List<ViajesConImagen> listafiltroidavuelta = new ArrayList<>();
    List<ViajesConImagen> listafiltroidavueltayuni = new ArrayList<>();
    Spinner spfiltro, spuniversidad;
    FloatingActionButton fablupa;
    //FloatingActionButton fabrecargar;
    ArrayList<Viaje> viajesdist = new ArrayList<>();
    static int i = 0;
    public static Viaje v;
    private GooeyMenu mGooeyMenu;
    private Timer timer;
    private Toast mToast;
    static ArrayList<Viaje> listaViajes = new ArrayList<>();
    GoogleMap mMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    static String distnacia;
    public static String posicionactual;
    private static String nombreahora;
    private boolean abriremail;
    HashMap<String, String> unihash = new HashMap();
    public static String universidad;
    public static String boton;
    static boolean cargarPrimeravez = true;
    boolean recargando = false;
    static ArrayList<ViajesConImagen> listacondistanciasordenadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantallacentral);
        setTitleColor(R.color.colorAccent);
        mGooeyMenu = (GooeyMenu) findViewById(R.id.gooey_menu);
        //    spfiltro = (Spinner) findViewById(R.id.spfiltro);
        spuniversidad = (Spinner) findViewById(R.id.spuniversidad);
        //  edbuscarviaje = (EditText) findViewById(R.id.edbuscarviaje);
        //  fablupa = (FloatingActionButton) findViewById(R.id.fablupa);
        // fabrecargar = (FloatingActionButton) findViewById(R.id.fabrecargar);
        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        btnida = (Button) findViewById(R.id.btnida);
        btntodos = (Button) findViewById(R.id.btntodos);
        btnvuelta = (Button) findViewById(R.id.btnvuelta);


        mGooeyMenu.setOnMenuListener(this);
        crearHashMap();
        poblarSpUniversidades();
        //poblarSpinner();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mapFragment.getView().setVisibility(View.INVISIBLE);

        // universidad = "todos";
        // filtro = spfiltro.getSelectedItem().toString();
        //  boton = "todos";
        // buscarViajesFirebase(universidad, boton);

        listeners();
    }

    public void toast(String s) {
        Toast.makeText(Pantallacentral.this, s, Toast.LENGTH_SHORT).show();
    }

    private void poblarSpUniversidades() {
        ArrayList<String> listadeunis = new ArrayList<>();
        listadeunis.add("TODAS");

        for (Map.Entry<String, String> entry : unihash.entrySet()) {
            listadeunis.add(entry.getKey());
        }
        ArrayAdapter<String> spinner3 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listadeunis);

        spuniversidad.setAdapter(spinner3);

    }

    private void crearHashMap() {

        unihash.put("Universidad de Alcalá", "40.483232,-3.363301");
        unihash.put("Universidad Autónoma de Madrid", "40.546698,-3.69436189999999");
        unihash.put("Universidad Carlos III de Madrid", "40.316966,-3.7270794999999453");
        unihash.put("Universidad Complutense de Madrid", "40.4478246,-3.7285871999999927");
        unihash.put("Universidad Politécnica de Madrid", "40.4486372,-3.719279799999981");
        unihash.put("Universidad Rey Juan Carlos", "40.3366526,-3.8748921999999766");
        unihash.put("Universidad Alfonso X el Sabio", "40.4535134,-3.98554850000005");
        unihash.put("Universidad Antonio de Nebrija", "40.599766,-3.94868299999996");
        unihash.put("Universidad Camilo José Cela", "40.472508,-3.9403429999999844");
        unihash.put("Universidad CEU San Pablo", "40.4429357,-3.7161945000000287");
        unihash.put("Universidad Europea de Madrid", "40.3751828,-3.924904999999967");
        unihash.put("Universidad Francisco de Vitoria", "40.440245,-3.8350840000000517");
        unihash.put("Universidad Pontificia Comillas", "40.42971319999999,-3.7113334000000577");
        unihash.put("Universidad a distancia de Madrid (UDIMA)", "40.6315495,-3.9987833999999793");


    }


    private void listeners() {

    /*    fablupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* universidad = spuniversidad.getSelectedItem().toString();
                boton = queFiltrodeBoton();
                while (boton.equals("")) {
                    toast("Por favor seleccione un filtro de sentido");
                    boton = queFiltrodeBoton();
                }
                recargando = true;
                buscarViajesFirebase(universidad, boton);
                recargando = true;
                universidad = "TODAS";
                boton = "todos";
                while (boton.equals("")) {
                    toast("Por favor seleccione un filtro de sentido");
                    boton = queFiltrodeBoton();
                }
                // toast(distancias.toString());
                buscarViajesFirebase(universidad, boton);


            }
        });
*/
        spuniversidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //UUUNNNIII
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String item = spuniversidad.getSelectedItem().toString();


                if (cargarPrimeravez == true) {
                    //CARGAR TODOS
                    universidad = spuniversidad.getSelectedItem().toString();
                    boton = "todos";
                    buscarViajesFirebase(universidad, boton);
                } else {
                    universidad = spuniversidad.getSelectedItem().toString();
                    boton = queFiltrodeBoton();
                    if (boton.equals("")) {
                        toast("Por favor seleccione un filtro de sentido");
                    } else
                        rellenarRecycler();

                    // toast(distancias.toString());
                    //   buscarViajesFirebase(universidad, boton);


                }

                //          Toast toast1 = Toast.makeText(getApplicationContext(), "Filtro: " + item, Toast.LENGTH_SHORT);
                //        toast1.show();


                //   filtrarSentidoYUni();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }


        });


        btntodos.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (botontodos == false) {
                    btntodos.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5fb0c9")));
                    btntodos.setTextColor(Color.WHITE);
                    if (botonida) {
                        btnida.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        btnida.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR
                        botonida = false;
                    }
                    if (botonvuelta) {
                        btnvuelta.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        btnvuelta.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR
                        botonvuelta = false;
                    }
                }
                if (botontodos == true) {
                    btntodos.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    btntodos.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR
                }
                if (botontodos)
                    botontodos = false;
                else
                    botontodos = true;
            }
        });

        btnida.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View v) {

                if (botonida == false) {
                    btnida.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5fb0c9")));
                    btnida.setTextColor(Color.WHITE);
                    if (botonvuelta) {
                        btnvuelta.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        btnvuelta.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR
                        botonvuelta = false;
                    }
                    if (botontodos) {
                        btntodos.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        btntodos.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR
                        botontodos = false;
                    }

                }
                if (botonida == true) {
                    btnida.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    btnida.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR
                }
                if (botonida)
                    botonida = false;
                else
                    botonida = true;


            }
        });


        btnvuelta.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (botonvuelta == false) {

                    btnvuelta.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5fb0c9")));
                    btnvuelta.setTextColor(Color.WHITE);

                    if (botonida) {
                        btnida.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        btnida.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR
                        botonida = false;
                    }
                    if (botontodos) {
                        btntodos.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        btntodos.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR
                        botontodos = false;
                    }


                }
                if (botonvuelta == true) {
                    btnvuelta.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    btnvuelta.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TAKE DEFAULT COLOR

                }

                if (botonvuelta)
                    botonvuelta = false;
                else
                    botonvuelta = true;


            }
        });


    }


    private void mostrarViajesPorCercania(List<ViajesConImagen> lista) {
        distanciasenenteros.clear();
        //AÑADIMOS LAS DISTNACIAS CONSEGUIDAS A CADA VIAJE
     /*   for (int i = 0; i < lista.size(); i++) {
           lista.get(i).setDistancia(PosicionActual.distancias.get(i));

        }

  */
        //ORDENAMOS LA LISTA POR DISTANCIA
        for (int i = 0; i < lista.size(); i++) {
            String datos[] = lista.get(i).getDistancia().split(" ");
            String dist = datos[0];
            distanciasenenteros.add(Double.parseDouble(dist));
        }
        Collections.sort(distanciasenenteros);

        int s = 0;
        listacondistanciasordenadas.clear();

        for (int b = 0; b < lista.size(); b++) {
            double cantidadahora = distanciasenenteros.get(s);
            double cantidadenlista = Double.parseDouble(lista.get(b).getDistancia().replace("km", "").replace("m", "").replace(" ", ""));
            if (cantidadenlista == cantidadahora) {
                listacondistanciasordenadas.add(lista.get(b));
                s++;
                b = -1;
            }
            if (listacondistanciasordenadas.size() == distanciasenenteros.size()) {
                b = lista.size();
            }


        }

        recyclerViajes = (RecyclerView) findViewById(R.id.recyclerViajes);
        managerRecyclerViajes = new LinearLayoutManager(this);
        recyclerViajes.setLayoutManager(managerRecyclerViajes);
        RecyclerAdaptadorViajes adaViajes = new RecyclerAdaptadorViajes(this, (ArrayList<ViajesConImagen>) listacondistanciasordenadas);
        recyclerViajes.setAdapter(adaViajes);

        if (listacondistanciasordenadas.size() == 0)
            toast("No hay viajes encontrados");

        if (listacondistanciasordenadas.size() != 0)
            toast(listacondistanciasordenadas.size() + " Viajes encontrados!");
    }


    private String queFiltrodeBoton() {
        String filtro = "";
        if (botonvuelta)
            filtro = "vuelta";
        if (botontodos)
            filtro = "todos";
        if (botonida)
            filtro = "ida";

        return filtro;

    }

    private void filtrarSentidoYUni() {

        listafiltroidavuelta.clear();
        listafiltroidavueltayuni.clear();


        //NOS QUEDAMOS SOLO CON LOS DE IDA
        if (boton.equals("ida")) {
            for (int i = 0; i < listaViajesConImagen.size(); i++) {
                if (listaViajesConImagen.get(i).getSentido().equals("ida")) {
                    listafiltroidavuelta.add(listaViajesConImagen.get(i));
                }
            }
        }

        //NOS QUEDAMOS SOLO CON LOS DE VUELTA
        if (boton.equals("vuelta")) {
            for (int i = 0; i < listaViajesConImagen.size(); i++) {
                if (listaViajesConImagen.get(i).getSentido().equals("vuelta")) {
                    listafiltroidavuelta.add(listaViajesConImagen.get(i));
                }
            }
        }
        //NOS QUEDAMOS CON LOS DE IDA Y VUELTA
        if (boton.equals("todos")) {
            for (int i = 0; i < listaViajesConImagen.size(); i++) {
                listafiltroidavuelta.add(listaViajesConImagen.get(i));

            }
        }


        for (int i = 0; i < listafiltroidavuelta.size(); i++) {
            if (universidad.equals("TODAS")) {
                listafiltroidavueltayuni.add(listaViajesConImagen.get(i));
            } else if (!universidad.equals("TODAS")) {
                if (listafiltroidavuelta.get(i).getUni().equals(universidad))
                    listafiltroidavueltayuni.add(listafiltroidavuelta.get(i));
            }
        }


        RecyclerAdaptadorViajes adaViajes = null;
        recyclerViajes = (RecyclerView) findViewById(R.id.recyclerViajes);
        managerRecyclerViajes = new LinearLayoutManager(this);
        recyclerViajes.setLayoutManager(managerRecyclerViajes);

        if (universidad.equals("TODAS")) {
            if (cargarPrimeravez) {
                adaViajes = new RecyclerAdaptadorViajes(this, (ArrayList<ViajesConImagen>) listaViajesConImagen);
                mostrarViajesPorCercaniaPrimeraCarga(listaViajesConImagen);
            } else if (!cargarPrimeravez) {
                adaViajes = new RecyclerAdaptadorViajes(this, (ArrayList<ViajesConImagen>) listafiltroidavueltayuni);
                mostrarViajesPorCercania(listafiltroidavueltayuni);
            }


        } else {
            adaViajes = new RecyclerAdaptadorViajes(this, (ArrayList<ViajesConImagen>) listafiltroidavueltayuni);
            mostrarViajesPorCercania(listafiltroidavueltayuni);


        }


    }


    private void mostrarViajesPorCercaniaPrimeraCarga(List<ViajesConImagen> lista) {
        //AÑADIMOS LAS DISTNACIAS CONSEGUIDAS A CADA VIAJE
        for (int i = 0; i < lista.size(); i++) {
            lista.get(i).setDistancia(PosicionActual.distancias.get(i));

        }
        distanciasenenteros.clear();
        //ORDENAMOS LA LISTA POR DISTANCIA
        for (String str : PosicionActual.distancias) {
            String datos[] = str.split(" ");
            String dist = datos[0];
            distanciasenenteros.add(Double.parseDouble(dist));

        }

        Collections.sort(distanciasenenteros);
        int s = 0;


        listacondistanciasordenadas.clear();
        for (int b = 0; b < lista.size(); b++) {
            double cantidadahora = distanciasenenteros.get(s);
            double cantidadenlista = Double.parseDouble(lista.get(b).getDistancia().replace("km", "").replace("m", "").replace(" ", ""));
            if (cantidadenlista == cantidadahora) {
                listacondistanciasordenadas.add(lista.get(b));
                s++;
                b = -1;
            }
            if (listacondistanciasordenadas.size() == distanciasenenteros.size()) {
                b = lista.size();
            }


        }

        recyclerViajes = (RecyclerView) findViewById(R.id.recyclerViajes);
        managerRecyclerViajes = new LinearLayoutManager(this);
        recyclerViajes.setLayoutManager(managerRecyclerViajes);
        RecyclerAdaptadorViajes adaViajes = new RecyclerAdaptadorViajes(this, (ArrayList<ViajesConImagen>) listacondistanciasordenadas);
        recyclerViajes.setAdapter(adaViajes);

        if (listacondistanciasordenadas.size() == 0)
            toast("No hay viajes encontrados");

        if (listacondistanciasordenadas.size() != 0)
            toast(listacondistanciasordenadas.size() + " Viajes encontrados!");
    }


    private void buscarViajesFirebase(String universidad, final String boton) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Viajes publicados");
        Query q = myRef.orderByChild("uni").equalTo(universidad);

        if (universidad.equals("TODAS"))
            q = myRef;

        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                listaViajes.clear();
                listaViajesPorCercania.clear();
                listafiltroidavuelta.clear();
                listaViajesConImagen.clear();


                Iterable i = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterador = i.iterator();
                while (iterador.hasNext()) {
                    Viaje v = iterador.next().getValue(Viaje.class);
                    listaViajes.add(v);
                }

                rellenarRecycler();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void rellenarRecycler() {


        if (cargarPrimeravez == true) {
            meterfoto();
        }

        // filtrarSentido();
        filtrarSentidoYUni();
        cargarPrimeravez = false;

        // toast(distancias.toString());


    }


    private void meterfoto() {
        String origen, destino, sentido, uni, fechadeviaje, fechadepublicacion, usuario, distancia, imagen, clave;

        for (int i = 0; i < listaViajes.size(); i++) {
            String imagen2 = listaViajes.get(i).getIdImage();
            int imageResource2 = getResources().getIdentifier(imagen2, null, getPackageName());
            Drawable imagendrawable2 = getResources().getDrawable(imageResource2);


            uni = listaViajes.get(i).getUni();
            sentido = listaViajes.get(i).getSentido();
            origen = listaViajes.get(i).getOrigen();
            destino = listaViajes.get(i).getDestino();
            fechadeviaje = listaViajes.get(i).getFechadeviaje();
            fechadepublicacion = listaViajes.get(i).getFechadepublicacion();
            usuario = listaViajes.get(i).getUsuario();
            distancia = listaViajes.get(i).getDistancia();
            clave = listaViajes.get(i).getClave();
            ViajesConImagen vi = new ViajesConImagen(origen, destino, sentido, uni, fechadeviaje, fechadepublicacion, usuario, distancia, clave, imagendrawable2);

            listaViajesConImagen.add(vi);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.toString().equals("Mi cuenta")) {
            Intent i = new Intent(this, Perfil.class);
            startActivity(i);

        }
        if (item.toString().equals("Compartir App")) {
            redesSociales();
        }


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void redesSociales() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Descárgate la app UniversiCar y disfruta compartiendo coche mientras conoces gente de tu uni");
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    private synchronized void distanciasbasico() {
        for (Viaje vi : listaViajes) {
            // String origen = PosicionActual.calle;
            String origen = "Av de Marsil, 19, Las Rozas, Madrid, España";
            String destino = vi.getOrigen();
            try {
                sendRequest(origen, destino);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void menuOpen() {
        showToast("Menu Open");

    }

    @Override
    public void menuClose() {
        showToast("Menu Close");
    }

    @Override
    public void menuItemClicked(int menuNumber) {
        abrirVentana(menuNumber);

    }

    private void abrirVentana(int menuNumber) {
        switch (menuNumber) {
            case 1:

                btntodos.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                btntodos.setTextColor(getApplication().getResources().getColor(R.color.colorApp));

                btnida.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                btnida.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TA


                btnvuelta.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                btnvuelta.setTextColor(getApplication().getResources().getColor(R.color.colorApp)); //TA

                botonvuelta = false;
                botonida = false;
                botontodos = false;

                abrirPublicarViaje();
                break;
            case 2:
                abrirViajesPublicando();
                break;
            case 3:
                refrescarRecycler();
                break;
            case 4:
                LoginActivity.abriremail = true;
                abrirEmail();
                break;
            case 5:
                //   LoginActivity.abriremail = true;
                abrirPerfil();
                break;

        }
    }

    private void refrescarRecycler() {


        universidad = spuniversidad.getSelectedItem().toString();
        boton = queFiltrodeBoton();

        if (RecyclerAdaptadorPublicando.borrando) {
            buscarViajesFirebase(universidad, boton);

        }
        RecyclerAdaptadorPublicando.borrando = false;

        if (boton.equals("")) {
            toast("Por favor seleccione un filtro de sentido");
        } else
            rellenarRecycler();
        // toast(distancias.toString());
        //   buscarViajesFirebase(universidad, boton);


    }


    private void abrirPerfil() {
        Intent i = new Intent(this, Perfil.class);
        startActivity(i);
    }

    private void abrirViajesPublicando() {
        Intent i = new Intent(this, ViajesPublicando.class);
        startActivity(i);
    }

    private void abrirEmail() {
        Intent i = new Intent(this, Email.class);
        startActivity(i);
    }

    private void abrirSettings() {
        // Intent i = new Intent(this, Settings.class);
        //startActivity(i);
    }

    private void abrirPublicarViaje() {
        Intent i = new Intent(this, PublicarViaje.class);
        startActivity(i);
    }

    private void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    //MAPA
    public void sendRequest(String origen, String destino) {


        try {
            new DirectionFinder(this, origen, destino).execute();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng hcmus = new LatLng(40.373139, -3.919055);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Universidad Europea de Madrid")
                .position(hcmus)));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onDirectionFinderStart() {

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));

            distnacia = route.distance.text;
            vib.vibrate(40);


            distancias.add(distnacia);


            //  v.setDistancia(distnacia);
            // listaViajesPorCercania.add(v);
            /// toast(distnacia + "  a  " + nombreahora);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }


    }

    //MAPA


}
