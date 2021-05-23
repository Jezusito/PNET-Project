package es.uca.espaciometronomo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.MyViewHolder>{
    private ArrayList<Reserva> reservas;
    private Context context;
    private static final int NOTIF_ALERTA_ID = 1;

    public ReservaAdapter(ArrayList<Reserva> myDataset) {
        reservas = myDataset;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        ImageButton show;

        public MyViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            date = (TextView) v.findViewById(R.id.date);
            show = (ImageButton) v.findViewById(R.id.show);
        }
    }
    @Override
    public ReservaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        context = parent.getContext();
        return vh;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(reservas.get(position).getNombre());
        holder.date.setText(calendarToString(reservas.get(position).getFecha()));
        holder.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar fecha = reservas.get(position).getFecha();
                Calendar actual = calendarToCalendar(Calendar.getInstance());

                // Toast para las fechas que ya han pasado
                if (fecha.compareTo(actual) < 0) {
                    CharSequence text = "El plazo de la reserva ha pasado";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                // SnackBar para fechas que no han pasado
                if (fecha.compareTo(actual) >= 0) {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(fecha.getTime().getTime() - actual.getTime().getTime());

                    if ((c.get(Calendar.DAY_OF_YEAR)-1) == 0)
                        Snackbar.make(v, "Hoy es el día de la reserva", Snackbar.LENGTH_LONG).show();
                    else if ((c.get(Calendar.DAY_OF_YEAR)-1) == 1)
                        Snackbar.make(v, "Falta 1 día para el día de la reserva", Snackbar.LENGTH_LONG).show();
                    else
                        Snackbar.make(v, "Faltan " + (c.get(Calendar.DAY_OF_YEAR)-1) + " días para el día de la reserva", Snackbar.LENGTH_LONG).show();
                }

                // Notificación en la barra de estado
                NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "default")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background))
                        .setContentTitle("Localización")
                        .setContentText("Descubre donde se realizarán las reservas")
                        .setTicker("Alerta!");

                notification.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

                // Tono
                notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

                notification.setLights(Color.WHITE, 3000, 3000);

                //Para que la notificación desaparezca
                notification.setAutoCancel(true);

                Intent noIntent = new Intent(context, MainActivity.class);
                PendingIntent contIntent = PendingIntent.getActivity(context, 0, noIntent, 0);

                notification.setContentIntent(contIntent);

                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("default", "Default Channel",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    channel.enableVibration(true);
                    channel.setVibrationPattern(new long[]{1000, 500, 1000});

                    channel.enableLights(true);
                    channel.setLightColor(Color.WHITE);

                    // Icono
                    channel.setShowBadge(true);
                    mNotificationManager.createNotificationChannel(channel);
                }

                // Para que la notificación se lance
                mNotificationManager.notify(NOTIF_ALERTA_ID, notification.build());
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    public static String calendarToString (Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" +
                calendar.get(Calendar.YEAR);
    }

    public static Calendar calendarToCalendar (Calendar calendar) {
        return new Calendar.Builder().setDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH)).build();
    }
}