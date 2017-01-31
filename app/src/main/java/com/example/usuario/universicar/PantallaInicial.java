package com.example.usuario.universicar;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class PantallaInicial extends ActionBarActivity {

    private Timer timer;
    private DonutProgress donutProgress;
    private DonutProgress donutProgress2;
    private DonutProgress donutProgress3;
    private DonutProgress donutProgress4;
    private DonutProgress donutProgress5;
    private DonutProgress donutProgress6;
    private DonutProgress donutProgress7;
    private DonutProgress donutProgress8;
    private DonutProgress donutProgress9;


    boolean cargado = false;

    TextView txttitulo;
    private TextSwitcher mSwitcher;
    ObjectAnimator anim;
    ObjectAnimator anim2;
    ObjectAnimator anim3;
    ObjectAnimator anim4;
    ObjectAnimator anim5;
    ObjectAnimator anim6;
    ObjectAnimator anim7;
    ObjectAnimator anim8;
    ObjectAnimator anim9;

    AnimatorSet set;
    AnimatorSet set2;
    AnimatorSet set3;
    AnimatorSet set4;
    AnimatorSet set5;
    AnimatorSet set6;
    AnimatorSet set7;
    AnimatorSet set8;
    AnimatorSet set9;
    int num = 0;

    private int animacion = 0;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantallainicial);
        txttitulo = (TextView) findViewById(R.id.txttitulo);

        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        animacionTitulo();

        fuente();

        Animation in = AnimationUtils.loadAnimation(this, R.anim.fadein);


        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        donutProgress2 = (DonutProgress) findViewById(R.id.donut_progress2);
        donutProgress3 = (DonutProgress) findViewById(R.id.donut_progress3);
        donutProgress4 = (DonutProgress) findViewById(R.id.donut_progress4);
        donutProgress5 = (DonutProgress) findViewById(R.id.donut_progress5);
        donutProgress6 = (DonutProgress) findViewById(R.id.donut_progress6);
        donutProgress7 = (DonutProgress) findViewById(R.id.donut_progress7);
        donutProgress8 = (DonutProgress) findViewById(R.id.donut_progress8);
        donutProgress9 = (DonutProgress) findViewById(R.id.donut_progress9);


        set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim);
        set2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim2);
        set3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim3);
        set4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim4);
        set5 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim5);
        set6 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim6);
        set7 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim7);
        set8 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim8);
        set9 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.progress_anim9);


        set.setTarget(donutProgress);
        set2.setTarget(donutProgress2);
        set3.setTarget(donutProgress3);
        set4.setTarget(donutProgress4);
        set5.setTarget(donutProgress5);
        set6.setTarget(donutProgress6);
        set7.setTarget(donutProgress7);
        set8.setTarget(donutProgress8);
        set9.setTarget(donutProgress9);

        set.start();
        set2.start();
        set3.start();
        set4.start();
        set5.start();
        set6.start();
        set7.start();
        set8.start();
        set9.start();

        ValueAnimator animation = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        ValueAnimator animation2 = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        ValueAnimator animation3 = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        ValueAnimator animation4 = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        ValueAnimator animation5 = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        ValueAnimator animation6 = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        ValueAnimator animation7 = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        ValueAnimator animation8 = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        ValueAnimator animation9 = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);

        animation.start();
        animation2.start();
        animation3.start();
        animation4.start();
        animation5.start();
        animation6.start();
        animation7.start();
        animation8.start();
        animation9.start();

        PropertyValuesHolder donutAlphaProperty = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutAlphaProperty2 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutAlphaProperty3 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutAlphaProperty4 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutAlphaProperty5 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutAlphaProperty6 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutAlphaProperty7 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutAlphaProperty8 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder donutAlphaProperty9 = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);

        PropertyValuesHolder donutProgressProperty = PropertyValuesHolder.ofInt("donut_progress", 0, 100);
        PropertyValuesHolder donutProgressProperty2 = PropertyValuesHolder.ofInt("donut_progress2", 0, 100);
        PropertyValuesHolder donutProgressProperty3 = PropertyValuesHolder.ofInt("donut_progress3", 0, 100);
        PropertyValuesHolder donutProgressProperty4 = PropertyValuesHolder.ofInt("donut_progress4", 0, 100);
        PropertyValuesHolder donutProgressProperty5 = PropertyValuesHolder.ofInt("donut_progress5", 0, 100);
        PropertyValuesHolder donutProgressProperty6 = PropertyValuesHolder.ofInt("donut_progress6", 0, 100);
        PropertyValuesHolder donutProgressProperty7 = PropertyValuesHolder.ofInt("donut_progress7", 0, 100);
        PropertyValuesHolder donutProgressProperty8 = PropertyValuesHolder.ofInt("donut_progress7", 0, 100);
        PropertyValuesHolder donutProgressProperty9 = PropertyValuesHolder.ofInt("donut_progress7", 0, 100);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(donutProgress, donutAlphaProperty, donutProgressProperty);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(donutProgress2, donutAlphaProperty2, donutProgressProperty2);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(donutProgress3, donutAlphaProperty3, donutProgressProperty3);
        ObjectAnimator animator4 = ObjectAnimator.ofPropertyValuesHolder(donutProgress4, donutAlphaProperty4, donutProgressProperty4);
        ObjectAnimator animator5 = ObjectAnimator.ofPropertyValuesHolder(donutProgress5, donutAlphaProperty5, donutProgressProperty5);
        ObjectAnimator animator6 = ObjectAnimator.ofPropertyValuesHolder(donutProgress6, donutAlphaProperty6, donutProgressProperty6);
        ObjectAnimator animator7 = ObjectAnimator.ofPropertyValuesHolder(donutProgress7, donutAlphaProperty7, donutProgressProperty7);
        ObjectAnimator animator8 = ObjectAnimator.ofPropertyValuesHolder(donutProgress8, donutAlphaProperty8, donutProgressProperty8);
        ObjectAnimator animator9 = ObjectAnimator.ofPropertyValuesHolder(donutProgress9, donutAlphaProperty9, donutProgressProperty9);


        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator3.setInterpolator(new AccelerateDecelerateInterpolator());
        animator4.setInterpolator(new AccelerateDecelerateInterpolator());
        animator5.setInterpolator(new AccelerateDecelerateInterpolator());
        animator6.setInterpolator(new AccelerateDecelerateInterpolator());
        animator7.setInterpolator(new AccelerateDecelerateInterpolator());
        animator8.setInterpolator(new AccelerateDecelerateInterpolator());
        animator9.setInterpolator(new AccelerateDecelerateInterpolator());

        animator.start();
        animator2.start();
        animator3.start();
        animator4.start();
        animator5.start();
        animator6.start();
        animator7.start();
        animator8.start();
        animator9.start();

        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (cargado == false) {
                            anim = ObjectAnimator.ofInt(donutProgress, "progress", 0, 10);
                            anim2 = ObjectAnimator.ofInt(donutProgress2, "progress", 0, 10);
                            anim3 = ObjectAnimator.ofInt(donutProgress3, "progress", 0, 10);
                            anim4 = ObjectAnimator.ofInt(donutProgress4, "progress", 0, 10);
                            anim5 = ObjectAnimator.ofInt(donutProgress5, "progress", 0, 10);
                            anim6 = ObjectAnimator.ofInt(donutProgress6, "progress", 0, 10);
                            anim7 = ObjectAnimator.ofInt(donutProgress7, "progress", 0, 10);
                            anim8 = ObjectAnimator.ofInt(donutProgress8, "progress", 0, 10);
                            anim9 = ObjectAnimator.ofInt(donutProgress9, "progress", 0, 10);

                            anim.setInterpolator(new DecelerateInterpolator());
                            anim2.setInterpolator(new DecelerateInterpolator());
                            anim3.setInterpolator(new DecelerateInterpolator());
                            anim4.setInterpolator(new DecelerateInterpolator());
                            anim5.setInterpolator(new DecelerateInterpolator());
                            anim6.setInterpolator(new DecelerateInterpolator());
                            anim7.setInterpolator(new DecelerateInterpolator());
                            anim8.setInterpolator(new DecelerateInterpolator());
                            anim9.setInterpolator(new DecelerateInterpolator());

                            anim.start();
                            anim2.start();
                            anim3.start();
                            anim4.start();
                            anim5.start();
                            anim6.start();
                            anim7.start();
                            anim8.start();
                            anim9.start();

                            cargado = true;
                            num++;

                        } else {
                            if (num == 1) {
                                abrirLogin();
                                num++;
                            }

                        }
                    }


                });
            }

        }, 0, 4000);


    }

    private void animacionTitulo() {

        Animation a = AnimationUtils.loadAnimation(this, R.anim.zoombackin);
        txttitulo.startAnimation(a);
    }

    private void fuente() {
        String carpetaFuente = "fonts/Fine College.ttf";
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);
        txttitulo.setTypeface(fuente);


    }

    private void abrirLogin() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }


}
