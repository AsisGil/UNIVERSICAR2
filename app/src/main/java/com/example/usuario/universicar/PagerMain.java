package com.example.usuario.universicar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;


public class PagerMain extends Fragment {

    public static int valor;
    TextView txthora, txtdia;
    EditText eddestino, edorigen;
    CalendarView calendario;
    TimePicker timePicker;
    ScrollView sc;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.modelo_pager, container, false);


        sc = (ScrollView) rootView.getChildAt(0);
        txthora = (TextView) rootView.getChildAt(1);
        txtdia = (TextView) rootView.getChildAt(2);
        eddestino = (EditText) rootView.getChildAt(3);
        edorigen = (EditText) rootView.getChildAt(4);
        calendario = (CalendarView) rootView.getChildAt(5);
        timePicker = (TimePicker) rootView.getChildAt(6);


        return rootView;
    }

    public static void setValor(int x) {
        valor = x;
    }


}