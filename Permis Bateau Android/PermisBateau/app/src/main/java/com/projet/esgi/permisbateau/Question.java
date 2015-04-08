package com.projet.esgi.permisbateau;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.projet.esgi.myapplication.R;

import database.DatabaseHelper;

public class Question extends ActionBarActivity {

    private DatabaseHelper dbHelper;
    private TextView enonce;
    private TextView repA;
    private TextView repB;
    private TextView repC;
    private TextView repD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        enonce = (TextView) findViewById(R.id.enonce);
        repA = (TextView) findViewById(R.id.q1);
        repB = (TextView) findViewById(R.id.q2);
        repC = (TextView) findViewById(R.id.q3);
        repD = (TextView) findViewById(R.id.q4);

        chargeAQuestion();
    }

    public void chargeAQuestion(){
        Bundle extras = getIntent().getExtras();
        dbHelper = (DatabaseHelper)extras.getSerializable("DB");

        // Open database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Construct and execute query
        Cursor cursor = db.query("Question",  // TABLE
                new String[] { "enoncer","reponse_A","reponse_B","reponse_C","reponse_D" }, // SELECT
                "id" + "= ?", new String[] { "1" },  // WHERE, ARGS
                null, null, "id ASC", "100"); // GROUP BY, HAVING, ORDER BY, LIMIT
        if (cursor != null)
            cursor.moveToFirst();
        // Load result into model object
        enonce.setText(cursor.getString(1));
        repA.setText(cursor.getString(2));
        repB.setText(cursor.getString(3));
        repC.setText(cursor.getString(4));
        repD.setText(cursor.getString(5));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
