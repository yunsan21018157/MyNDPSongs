package sg.edu.rp.c346.id21018157.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle, tvSingers, tvYear, tvStars;
    EditText etTitle, etSingers, etYear;
    RadioGroup rgStars;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    Button btnInsert, btnShowList;

    Song data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvSingers = findViewById(R.id.tvSingers);
        tvYear = findViewById(R.id.tvYear);
        tvStars = findViewById(R.id.tvStars);
        etTitle = findViewById(R.id.etTitle);
        etSingers = findViewById(R.id.etSingers);
        etYear = findViewById(R.id.etYear);
        rgStars = findViewById(R.id.rgStars);
        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);
        rb4 = findViewById(R.id.rb4);
        rb5 = findViewById(R.id.rb5);
        btnInsert = findViewById(R.id.btnInsert);
        btnShowList = findViewById(R.id.btnShowList);

        ArrayList<Song> al;
        ArrayAdapter<Song> aa;

        al = new ArrayList<Song>();
        //changed for CA
        aa = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, al);


        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");


        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Song target = al.get(0);
                Intent i = new Intent(MainActivity.this,
                        ShowSong.class);
//                i.putExtra("data", target);
                startActivity(i);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int stars = 1;
                int checkedRadioId = rgStars.getCheckedRadioButtonId();
                if (checkedRadioId == R.id.rb1) {
                    stars = 1;
                } else if (checkedRadioId == R.id.rb2) {
                    stars = 2;
                } else if (checkedRadioId == R.id.rb3) {
                    stars = 3;
                } else if (checkedRadioId == R.id.rb4) {
                    stars = 4;
                } else if (checkedRadioId == R.id.rb5) {
                    stars = 5;
                }


                String title = etTitle.getText().toString();
                String singers = etSingers.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());


                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertSong(title, singers, year, stars);

                if (inserted_id != -1) {
                    al.clear();
                    al.addAll(dbh.getAllSongs()); //to add the songs retrieved from the database using dbh.getAllSongs() into the arraylist.
                    aa.notifyDataSetChanged();

                }
                Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
            }
        });


    }
}