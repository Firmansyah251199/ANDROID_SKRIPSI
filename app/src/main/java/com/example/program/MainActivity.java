package com.example.program;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.net.Uri;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Bundle;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.anastr.speedviewlib.SpeedView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText sp_kelembaban,sp_suhu,sp_soil;
    TextView nilai_suhu, nilai_kelembaban,nilai_soil, p_kelembapan, p_suhu, p_soil;
    Button btn_kelembaban,btn_suhu,btn_soil,btn_mode_otomatis,btn_mode_manual,btn_air_on,btn_air_off,btn_desinon, btn_desinoff;
    Firebase datasp_kelembaban,datasp_suhu, datasuhu,datakelembaban,datasoil,datasp_soil,datamode,dataair,datadesinfektan,dataotomatis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel sp_kelembapan = new NotificationChannel("data_sp_kelembapan", "data_sp_kelembapan", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel sp_suhu = new NotificationChannel("data_sp_suhu", "data_sp_suhu", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel sp_soil = new NotificationChannel("data_sp_soil", "data_sp_soil", NotificationManager.IMPORTANCE_DEFAULT);


            NotificationChannel otomatis_kelembapan = new NotificationChannel("datakelembapanterkini", "datakelembapanterkini", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel otomatis_suhu= new NotificationChannel("datasuhuterkini", "datasuhuterkini", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel otomatis_soil= new NotificationChannel("datasoilterkini", "datasoilterkini", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notif_sp_kelembapan = getSystemService(NotificationManager.class);
            NotificationManager notif_sp_suhu = getSystemService(NotificationManager.class);
            NotificationManager notif_sp_soil= getSystemService(NotificationManager.class);

            //Notif Manager Mode Otomatis
            NotificationManager notif_otomatiskelembapan = getSystemService(NotificationManager.class);
            NotificationManager notif_otomatissuhu = getSystemService(NotificationManager.class);
            NotificationManager notif_otomatissoil = getSystemService(NotificationManager.class);


            notif_sp_kelembapan.createNotificationChannel(sp_kelembapan);
            notif_sp_suhu.createNotificationChannel(sp_suhu);
            notif_sp_soil.createNotificationChannel(sp_soil);


            notif_otomatiskelembapan.createNotificationChannel(otomatis_kelembapan);
            notif_otomatissuhu.createNotificationChannel(otomatis_suhu);
            notif_otomatissoil.createNotificationChannel(otomatis_soil);
        }


        sp_kelembaban=(EditText) findViewById(R.id.txtsp_maxph);
        sp_suhu=(EditText) findViewById(R.id.txtmax_suhu);
        sp_soil=(EditText) findViewById(R.id.txtmax_soil);

        btn_kelembaban=(Button)findViewById(R.id.btn_setph);
        btn_suhu=(Button)findViewById(R.id.btn_setsuhu);
        btn_soil=(Button)findViewById(R.id.btn_setsoil);
        btn_mode_otomatis=(Button) findViewById(R.id.btn_setmode_otomatis);
        btn_mode_manual=(Button) findViewById(R.id.btn_setmode_manual);
        btn_air_on= (Button) findViewById(R.id.btn_waterON);
        btn_air_off= (Button) findViewById(R.id.btn_waterOFF);
        btn_desinon = (Button) findViewById(R.id.btn_desinfectanON);
        btn_desinoff = (Button) findViewById(R.id.btn_desinfectanOFF);

        nilai_kelembaban= findViewById(R.id.txt_kelembaban);
        nilai_soil =findViewById(R.id.txt_soil);
        nilai_suhu =findViewById(R.id.txt_suhu);
        p_kelembapan = findViewById(R.id.txtsp_maxph);
        p_suhu = findViewById(R.id.txtmax_suhu);
        p_soil = findViewById(R.id.txtmax_soil);


        datasp_kelembaban= new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Kontrol/sp_kelembaban");
        datasp_suhu= new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Kontrol/sp_suhu");
        datasp_soil= new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Kontrol/sp_soil");
        dataair= new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Kontrol/air");
        datadesinfektan= new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Kontrol/desinfektan");
        datamode= new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Kontrol/mode");


        datasuhu = new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Data/Suhu");
        datakelembaban = new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Data/Kelembaban");
        datasoil = new Firebase("https://firmansyah-monitoring-default-rtdb.firebaseio.com/Data/Soil");


        btn_kelembaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasp_kelembaban.setValue(Float.valueOf(sp_kelembaban.getText().toString()));
            }
        });

        btn_suhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasp_suhu.setValue(Float.valueOf(sp_suhu.getText().toString()));
            }
        });

        btn_soil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datasp_soil.setValue(Float.valueOf(sp_soil.getText().toString()));
            }
        });

        btn_mode_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datamode.setValue(2);
            }
        });
        btn_mode_otomatis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datamode.setValue(1);
            }
        });

        btn_desinoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datadesinfektan.setValue(2);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "btn_desin_of");
                builder.setContentTitle("Kontrol Disinfektan");
                builder.setContentText("Air Berhenti");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(1, builder.build());
            }
        });

        btn_desinon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datadesinfektan.setValue(1);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "btn_desin_on");
                builder.setContentTitle("Kontrol Disinfektan");
                builder.setContentText("Disinfektan Menyala");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(1, builder.build());
            }
        });

        btn_air_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataair.setValue(1);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "btn_air_on");
                builder.setContentTitle("Kontrol Air");
                builder.setContentText("Air Menyala");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify(1, builder.build());
            }
        });

        btn_air_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataair.setValue(2);
            }
        });

        datakelembaban.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    nilai_kelembaban.setText("Nilai Kelembaban = "+ dataSnapshot.getValue().toString()+"%");

                    String value = nilai_kelembaban.getText().toString();
                    String intValue = value.replaceAll("[^0-9]", "");

                    int kelembapan = Integer.parseInt(intValue);
                    int spkelembapan = Integer.parseInt(sp_kelembaban.getText().toString());

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "datakelembapanterkini");
                    builder.setContentTitle("Air Menyala");
                    if (kelembapan < spkelembapan){
                        builder.setContentText(nilai_kelembaban.getText().toString()+ " <" +" nilai sp kelembapan = "+ sp_kelembaban.getText().toString()+ "%");
                        builder.setSmallIcon(R.drawable.ic_launcher_background);
                        builder.setAutoCancel(true);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                        managerCompat.notify(1, builder.build());
                    } else {
//                        builder.setContentText(" Solenoid Mati " );
//                        builder.setSmallIcon(R.drawable.ic_launcher_background);
//                        builder.setAutoCancel(true);
//                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
//                        managerCompat.notify(1, builder.build());
                    }
                } catch (Exception ex) {

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        datasuhu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    nilai_suhu.setText("Nilai Suhu = "+ dataSnapshot.getValue().toString()+"'C");

                    String value = nilai_suhu.getText().toString();
                    String intValue = value.replaceAll("[^0-9]", "");

                    int suhu = Integer.parseInt(intValue);
                    int spsuhu = Integer.parseInt(sp_suhu.getText().toString());

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "datasuhuterkini");
                    builder.setContentTitle("Air Menyala" );
                    if (suhu > spsuhu){
                        builder.setContentText(nilai_suhu.getText().toString()+ " >" +" nilai sp suhu = "+ sp_suhu.getText().toString()+ "'C");
                        builder.setSmallIcon(R.drawable.ic_launcher_background);
                        builder.setAutoCancel(true);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                        managerCompat.notify(1, builder.build());
                    } else {
//                        builder.setContentText(" Solenoid Mati " );
//                        builder.setSmallIcon(R.drawable.ic_launcher_background);
//                        builder.setAutoCancel(true);
//                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
//                        managerCompat.notify(1, builder.build());
                    }
                } catch (Exception ex)
                {

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        datasoil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    nilai_soil.setText("Nilai Soil = "+ dataSnapshot.getValue().toString());

                    String value = nilai_soil.getText().toString();
                    String intValue = value.replaceAll("[^0-9]", "");

                    int soil = Integer.parseInt(intValue);
                    int spsoil = Integer.parseInt(sp_suhu.getText().toString());

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "datasoilterkini");
                    builder.setContentTitle("Air Menyala");
                    if (soil > spsoil){
                        builder.setContentText(nilai_soil.getText().toString() + " >"+" nilai sp soil = " + sp_soil.getText().toString()+"  = Kering ");
                        builder.setSmallIcon(R.drawable.ic_launcher_background);
                        builder.setAutoCancel(true);
                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                        managerCompat.notify(1, builder.build());
                    } else {
//                        builder.setContentText(" Solenoid Mati " );
//                        builder.setSmallIcon(R.drawable.ic_launcher_background);
//                        builder.setAutoCancel(true);
//                        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
//                        managerCompat.notify(1, builder.build());
                    }
                } catch (Exception ex) {

                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        datasp_kelembaban.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                   String kelembapan = dataSnapshot.getValue().toString();
                   String vkelembapan = kelembapan.replaceAll("[.]+[0-9]", "");
                   p_kelembapan.setText(vkelembapan);

                } catch (Exception ex) {

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        datasp_suhu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    String suhu = dataSnapshot.getValue().toString();
                    String vsuhu = suhu.replaceAll("[.]+[0-9]", "");
                    p_suhu.setText(vsuhu);

                } catch (Exception ex) {

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        datasp_soil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    String soil = dataSnapshot.getValue().toString();
                    String vsoil = soil.replaceAll("[.]+[0-9]", "");
                    p_soil.setText(vsoil);


                } catch (Exception ex) {

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
