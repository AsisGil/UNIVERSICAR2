package com.example.usuario.universicar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static int numerodepaginas =3;
    Toolbar toolbar;

    Toolbar toolbarnav;
    Vibrator vib;
    static ViewFlipper vfapp;
    private PagerAdapter mPagerAdapter;
    static ViewPager pager;
    static int pag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigator);
        vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vfapp = (ViewFlipper) findViewById(R.id.vfapp);
        pager = (ViewPager) findViewById(R.id.pager);

        establecerPager();

        setToolbar();

        navigator();

        listeners();

    }

    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
 toolbar.setTitle("UniversiCar");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


    private void establecerPager() {
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setPageTransformer(false, new ViewPager.PageTransformer() {
            public void transformPage(View page, float position) {
                page.setRotationY(position * -30);
            }
        });
        pager.setAdapter(mPagerAdapter);
        MainActivity.pag++;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
             PagerTomas.setValor(position);
            PagerTomas p = new PagerTomas();

             return p;

        }

        public int getCount() {
            return numerodepaginas;
        }
    }

    private void toast(String texto) {
        Toast.makeText(this,
                texto, Toast.LENGTH_LONG).show();
    }

    private void navigator() {

        toolbarnav = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarnav);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarnav, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void listeners() {

    }

    private void abrirCirculo() {
        Intent i = new Intent(this, PantallaInicial.class);
        startActivity(i);
    }

    private void abrir(MenuItem item) {

        if (item.toString().equals("Nuevo viaje")) {
            vib.vibrate(90);
            animacion(4);
            // vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.viaje)));
        }
        if (item.toString().equals("Favoritos e Historial")) {
            vib.vibrate(90);

            animacion(5);
            // abrirhisyfav();
        }
        if (item.toString().equals("")) {
            vib.vibrate(90);
            animacion(6);
            //  abrirWeb();
        }
        if (item.toString().equals("Compartir App")) {
            vib.vibrate(90);
            animacion(1);
            redesSociales();
        }
        if (item.toString().equals("Compartir Dato")) {
            vib.vibrate(90);
            animacion(1);
            //  vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.zona)));
        }
        if (item.toString().equals("Como ahorrar cone el coche")) {
            vib.vibrate(90);
            animacion(1);
            //  vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.cuidarelplaneta)));
        }

    }

    private void redesSociales() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Descárgate la app UniversiCar y disfruta compartiendo coche mientras conoces gente de tu uni");
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    private void animacion(int num) {
        switch (num) {
            case 1:
                vfapp.setInAnimation(AnimationUtils.loadAnimation(this,
                        R.anim.leftin));
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

    public boolean onCreateOptionsMenu(Menu menu) {
        vib.vibrate(90);
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        abrir(item);

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        abrir(item);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {


        int ventana = vfapp.getDisplayedChild();
        switch (ventana) {
            case 0:
                abrirPantallaInicial();
                break;
          /*  case 1:
                animacion(2);
                vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.Principal)));
                break;
            case 2:
                animacion(3);
                vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.zona)));
                break;
            case 3:
                animacion(4);
                vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.Principal)));
                break;
            case 4:
                animacion(5);
                vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.Principal)));
                break;
            case 5:
                animacion(6);
                vfapp.setDisplayedChild(vfapp.indexOfChild(findViewById(R.id.Principal)));
                break;
*/
        }
    }

    private void abrirPantallaInicial() {
        Intent i = new Intent(this, PantallaInicial.class);
        startActivity(i);
    }

}
