package es.uca.espaciometronomo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;

public class ImportantDates extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        // Referenciamos al RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // Mejoramos rendimiento con esta configuración
        mRecyclerView.setHasFixedSize(true);

        // Creamos un LinearLayoutManager para gestionar el item_dates.xml creado antes
        mLayoutManager = new LinearLayoutManager(this);
        // Lo asociamos al RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Creamos un ArrayList de Resevas
        ArrayList<Booking> bookings = new ArrayList<Booking>();

        bookings.add(new Booking(1, "Booking 1", new Calendar.Builder().setDate(2021, 05, 22).build()));
        bookings.add(new Booking(2, "Booking 2", new Calendar.Builder().setDate(2021, 05, 23).build()));
        bookings.add(new Booking(3, "Booking 3", new Calendar.Builder().setDate(2021, 05, 24).build()));
        bookings.add(new Booking(4, "Booking 4", new Calendar.Builder().setDate(2021, 07, 24).build()));

        // Creamos un BookingAdapter pasándole todas nuestras bookings
        mAdapter = new BookingAdapter(bookings);
        // Asociamos el adaptador al RecyclerView
        mRecyclerView.setAdapter(mAdapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_books) {
            startActivity(new Intent(getApplicationContext(), BookingMainActivity.class));
        }

        if (id == R.id.action_program) {
            startActivity(new Intent(getApplicationContext(), ImportantBookingsActivity.class));
        }

        if (id == R.id.action_dates) {
            startActivity(new Intent(getApplicationContext(), ImportantDates.class));
        }

        if (id == R.id.action_location) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
