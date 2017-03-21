package com.example.usuario.universicar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;


public class PublicarViaje extends AppCompatActivity implements OnMapReadyCallback, DirectionFinderListener {
    static String origen, destino, sentido, uni, usuario, fechadeviaje, imagen, fechadepublicacion;
    static String distnacia;
    static String distanciadeaquiaorigen;
    public static boolean venimosdePublicar = false;
    HashMap<String, String> unihash = new HashMap();
    public static int valor;
    TextView txthora, txtdia, txv_calculo, tvDistance, tvDuration;
    EditText ediralauniorigen, edvolverdelaunidestino;
    Spinner spiralauni, spvolverdelauni;
    Vibrator vib;

    ScrollView sc;
    Button btnPublicarViaje, btnCalcular, btn_buscar;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    MapView mapView;
    private ProgressDialog progressDialog;
    GoogleMap mMap;
    private int mes, dia, anio;
    static int hora, minuto;
    Button dialogButtonCancel;
    TimePicker tpickhorapubli;
    private int fechaCompleta;
    private Dialog dialog;
    private TabHost th;
    private String nombrefoto;
    private boolean calculandodistanciadesdeorigen = false;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_viaje);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        setTitle("Publicar Viaje");

        btnPublicarViaje = (Button) findViewById(R.id.btnPublicarViaje);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);

        ediralauniorigen = (EditText) findViewById(R.id.ediralauniorigen);
        edvolverdelaunidestino = (EditText) findViewById(R.id.edvolverdelaunidestino);

        txtdia = (TextView) findViewById(R.id.txtdia);
        txthora = (TextView) findViewById(R.id.txthora);

        spiralauni = (Spinner) findViewById(R.id.spiralauni);
        spvolverdelauni = (Spinner) findViewById(R.id.spvolverdelauni);

        btnCalcular = (Button) findViewById(R.id.btnCalcular);

        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvDuration = (TextView) findViewById(R.id.tvDuration);


        ediralauniorigen.setText("Mi ubicación");
        edvolverdelaunidestino.setText("Mi ubicación");


        crearHashMap();
        rellenarSpinners();


        tabHost();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        this.usuario = LoginActivity.nombrePropietario;
        sc = (ScrollView) findViewById(R.id.sc);

        listeners();

    }

    private void listeners() {


        th.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                edvolverdelaunidestino.setText("");
                edvolverdelaunidestino.setText("");
            }
        });


        btnPublicarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imagenbuena = "";
                if (th.getCurrentTab() == 0) {
                    origen = ediralauniorigen.getText().toString();
                    if (origen.equals("Mi ubicación")) {
                        origen = PosicionActual.calle;

                    }
                    uni = spiralauni.getSelectedItem().toString();
                    destino = spiralauni.getSelectedItem().toString();
                    sentido = "ida";

                    fechadeviaje = mes + "/" + dia + "/" + anio + " a las " + dia + ":" + hora;
                    fechadepublicacion = fecha();

                    imagenbuena = destino.toLowerCase().replace(" ", "");


                    if (destino.equals("Universidad de Alcalá"))
                        imagenbuena = "universidaddealcala";
                    if (destino.equals("Universidad Camilo José Cela"))
                        imagenbuena = "universidadcamilojosecela";
                    if (destino.equals("Universidad Politécnica de Madrid"))
                        imagenbuena = "universidadpolitecnicademadrid";
                    if (destino.equals("Universidad Autónoma de Madrid"))
                        imagenbuena = "universidadautonodemamadrid";


                } else if (th.getCurrentTab() == 1) {
                    origen = spvolverdelauni.getSelectedItem().toString();

                    if (origen.equals("Mi ubicación"))
                        origen = PosicionActual.calle;
                    uni = spvolverdelauni.getSelectedItem().toString();

                    destino = edvolverdelaunidestino.getText().toString();
                    sentido = "vuelta";


                    imagenbuena = origen.toLowerCase().replace(" ", "");


                    if (origen.equals("Universidad de Alcalá"))
                        imagenbuena = "universidaddealcala";
                    if (origen.equals("Universidad Camilo José Cela"))
                        imagenbuena = "universidadcamilojosecela";
                    if (origen.equals("Universidad Politécnica de Madrid"))
                        imagenbuena = "universidadpolitecnicademadrid";
                    if (origen.equals("Universidad Autónoma de Madrid"))
                        imagenbuena = "universidadautonodemamadrid";


                }


                imagen = "drawable/" + imagenbuena;


                Viaje vi = new Viaje(origen, destino, sentido, uni, fechadeviaje, fechadepublicacion, usuario, "0", imagen, "0");

                edvolverdelaunidestino.setText("");
                ediralauniorigen.setText("");

                toast("Viaje publicado!");
                insertarObjeto(vi);


            }
        });

        txtdia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAlertDia();
            }
        });

        txthora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                abrirAlertHora();
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (th.getCurrentTab() == 0) {
                    if (ediralauniorigen.equals("")) {
                        toast("Introduzca un origen");
                    } else {
                        origen = ediralauniorigen.getText().toString();
                        destino = spiralauni.getSelectedItem().toString();

                        if (origen.equals("Mi ubicación")) {
                            origen = PosicionActual.calle;
                        }
                    }
                }
                if (th.getCurrentTab() == 1) {
                    if (edvolverdelaunidestino.equals("")) {
                        toast("Introduzca un origen");
                    } else {
                        origen = spvolverdelauni.getSelectedItem().toString();
                        destino = edvolverdelaunidestino.getText().toString();

                    }
                    if (destino.equals("Mi ubicación")) {
                        destino = PosicionActual.calle;
                    }
                }


                sendRequest(origen, destino);
            }
        });
    }

    private void enviarSendDistancia() {
        if (th.getCurrentTab() == 0) {
            origen = ediralauniorigen.getText().toString();
            if (origen.equals("Mi ubicación"))
                origen = PosicionActual.calle;

        } else if (th.getCurrentTab() == 1) {
            origen = spvolverdelauni.getSelectedItem().toString();

            if (origen.equals("Mi ubicación"))
                origen = PosicionActual.calle;
        }

        sendRequest(PosicionActual.calle, origen);

    }

    private void mostrarViajesPorCercania(List<ViajesConImagen> lista) {


        Collections.sort(Pantallacentral.distanciasenenteros);

        int s = 0;
        Pantallacentral.listacondistanciasordenadas.clear();

        for (int b = 0; b < lista.size(); b++) {
            double cantidadahora = Pantallacentral.distanciasenenteros.get(s);
            double cantidadenlista = Double.parseDouble(lista.get(b).getDistancia().replace("km", "").replace("m", "").replace(" ", ""));
            if (cantidadenlista == cantidadahora) {
                Pantallacentral.listacondistanciasordenadas.add(lista.get(b));
                s++;
                b = -1;
            }
            if (Pantallacentral.listacondistanciasordenadas.size() == Pantallacentral.distanciasenenteros.size()) {
                b = lista.size();
            }


        }

        Pantallacentral.recyclerViajes = (RecyclerView) findViewById(R.id.recyclerViajes);
        Pantallacentral.managerRecyclerViajes = new LinearLayoutManager(this);
        Pantallacentral.recyclerViajes.setLayoutManager(Pantallacentral.managerRecyclerViajes);
        RecyclerAdaptadorViajes adaViajes = new RecyclerAdaptadorViajes(this, (ArrayList<ViajesConImagen>) Pantallacentral.listacondistanciasordenadas);
        Pantallacentral.recyclerViajes.setAdapter(adaViajes);
    }


    private void rellenarSpinners() {
        ArrayList<String> listadeunis = new ArrayList<>();
        for (Map.Entry<String, String> entry : unihash.entrySet()) {
            listadeunis.add(entry.getKey());
        }
        ArrayAdapter<String> spinner3 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listadeunis);

        spiralauni.setAdapter(spinner3);
        spvolverdelauni.setAdapter(spinner3);

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

    private void tabHost() {

        th = (TabHost) findViewById(R.id.th);

        th.setup();

        TabHost.TabSpec Iralauni = th.newTabSpec("Iralauni");
        TabHost.TabSpec Volveruni = th.newTabSpec("Volver de la uni");

        Iralauni.setIndicator("Iralauni");
        Volveruni.setIndicator("Volver de la uni");

        Iralauni.setContent(R.id.Iralauni);
        Volveruni.setContent(R.id.volveruni);

        th.addTab(Iralauni);
        th.addTab(Volveruni);

    }

    public void abrirAlertDia() {

// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alertdialog_calendario);
        // Custom Android Allert Dialog Title
        dialog.setTitle("Custom Dialog Example");
        final CalendarView calendarView = (CalendarView) dialog.findViewById(R.id.calendarView);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btnDialogClose);

        //______Apartado poner fecha en edit text
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mes = month + 1;
                actualizarTextviewDIA(dayOfMonth, mes, year);
            }
        });

        // Click cancel to dismiss android custom dialog box
        /*
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
*/
        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                //String selectedDate = sdf.format(new Date(calendarView.getDate()));
                //actualizarTextviewDIA(selectedDate);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void actualizarTextviewHORA(int hora, int minuto) {
        txthora.setText("La hora seleccionada es: " + hora + ":" + minuto);
        this.hora = hora;
        this.minuto = minuto;
    }

    private Date FechaDeViaje(String s) {
        String datos[] = s.split("/");
        String dia = datos[0];
        String mes = datos[1];
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        if (mes.length() == 1) {
            mes = "0" + mes;
        }


        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date fecha = new Date(s);

        return fecha;

    }

    private Date getFechaActual() {


        long ahora = System.currentTimeMillis();
        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(ahora);
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date fecha = new Date(ahora);

        return fecha;

    }

    public void insertarObjeto(Viaje o) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Viajes publicados");

        String clave = myRef.push().getKey();

        o.setClave(clave);

        Map m = new HashMap<>();
        m.put(myRef.push().getKey(), o);
        myRef.updateChildren(m);


        // ViajesConImagen vi = meterfoto(o);
        // vi.setDistancia(distanciadeaquiaorigen);

        //    Pantallacentral.listaViajesConImagen.add(vi);
        venimosdePublicar = true;

        PosicionActual.distancias.add(distanciadeaquiaorigen);
        Pantallacentral.cargarPrimeravez = true;


        // Pantallacentral.boton = "todos";
//        Pantallacentral.universidad="TODAS";
    }


    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private String fecha() {
        Calendar calendario = new GregorianCalendar();
        int mes, dia, hora, minuto, segundo;
        mes = calendario.get(Calendar.MONTH);
        mes += 1;
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);
        segundo = calendario.get(Calendar.SECOND);

        String fecha = hora + ":" + minuto + ":" + segundo + "  " + dia + "/" + mes;

        return fecha;
    }

    private ViajesConImagen meterfoto(Viaje o) {
        String origen, destino, sentido, uni, fechadeviaje, fechadepublicacion, usuario, distancia, imagen, clave;

        int imageResource2 = getResources().getIdentifier("drawable/uem", null, getPackageName());
        Drawable imagendrawable2 = getResources().getDrawable(imageResource2);

        uni = o.getUni();
        sentido = o.getSentido();
        origen = o.getOrigen();
        destino = o.getDestino();
        fechadeviaje = o.getFechadeviaje();
        fechadepublicacion = o.getFechadepublicacion();
        usuario = o.getUsuario();
        distancia = o.getDistancia();
        clave = o.getClave();
        ViajesConImagen vi = new ViajesConImagen(origen, destino, sentido, uni, fechadeviaje, fechadepublicacion, usuario, distancia, clave, imagendrawable2);


        return vi;
    }

    public void actualizarTextviewDIA(int dia, int mes, int anio) {
        txtdia.setText("El dia seleccionado es: " + dia + "/" + mes + "/" + anio);
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public void abrirAlertHora() {

// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.alertdialog_hora);
        // Custom Android Allert Dialog Title
        dialog.setTitle("Custom Dialog Example");
        final TimePicker tpickhorapubli = (TimePicker) dialog.findViewById(R.id.tpickhorapubli);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.btnDialogCloseH);
        // Click cancel to dismiss android custom dialog box
        /*
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
*/
        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hora = tpickhorapubli.getHour();
                int minuto = tpickhorapubli.getMinute();

                actualizarTextviewHORA(hora, minuto);
                dialog.dismiss();

                enviarSendDistancia();

            }
        });

        dialog.show();
    }

    public void sendRequest(String origin, String destination) {


        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }


        try {
            new DirectionFinder(this, origin, destination).execute();
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
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

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
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            tvDuration.setText(route.duration.text);
            tvDistance.setText(route.distance.text);
            distnacia = route.distance.text;
            distanciadeaquiaorigen = route.distance.text;
            vib.vibrate(40);


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


}
