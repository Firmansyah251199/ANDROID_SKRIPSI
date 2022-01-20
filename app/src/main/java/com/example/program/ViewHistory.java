package com.example.program;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;



import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewHistory extends AppCompatActivity {
    Button btn_delete,btn_export;

    ListView list;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        btn_delete = findViewById(R.id.id_delete);
        btn_export = findViewById(R.id.id_export);


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Delete();
            }
        });


        SQLiteDatabase db = openOrCreateDatabase("db_datetime", Context.MODE_PRIVATE,null);
        list = findViewById(R.id.id_list);
        final Cursor c = db.rawQuery("select * from db_datetime", null);
        int id = c.getColumnIndex("id");
        int name = c.getColumnIndex("name");
        int datetime = c.getColumnIndex("datetime");
        titles.clear();

        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,titles);
        list.setAdapter(arrayAdapter);

        final ArrayList<History> historyAdapter = new ArrayList();
        if (c.moveToFirst())
        {
            Log.d("bisa", "onCreate: ");
            do{
               History history = new History();
               history.id = c.getString(id);
               history.name = c.getString(name);
               history.datetime = c.getString(datetime);

               historyAdapter.add(history);

                titles.add(c.getString(name) + "\n" + c.getString(datetime));

            }
            while (c.moveToNext());
            arrayAdapter.notifyDataSetChanged();
            list.invalidateViews();
        }
        Log.d("tidak", "onCreate: ");

    }

    private void Delete() {
        try {

            SQLiteDatabase db = openOrCreateDatabase("db_datetime", Context.MODE_PRIVATE,null);

            String sql = "delete from db_datetime";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.execute();
            Toast.makeText(this, "Record delete", Toast.LENGTH_LONG).show();
            Intent Intent = new Intent(this,ViewHistory.class);
            startActivity(Intent);
        }catch (Exception e){
            Toast.makeText(this, "Record Fail", Toast.LENGTH_LONG).show();
        }

}


}



