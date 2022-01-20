package com.example.program;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {
    Button about, login;
    TextView tutorial_aplikasi, tutorial_alat;
    ImageView ig, wa;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        login = (Button) findViewById(R.id.login);
        about = (Button) findViewById(R.id.about);
        tutorial_aplikasi = findViewById(R.id.tutorial_aplikasi);
        tutorial_alat = findViewById(R.id.tutorial_alat);
        ig = findViewById(R.id.ig);
        wa = findViewById(R.id.wa);
        tutorial_aplikasi.setMovementMethod(LinkMovementMethod.getInstance());
        tutorial_alat.setMovementMethod(LinkMovementMethod.getInstance());

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.instagram.com/frmn_syah25/"));
                startActivity(intent);
            }
        });

        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=6282233505325"));
                startActivity(intent);
            }
        });



    }
}
