package sg.edu.rp.c346.id21018157.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class ShowSong extends AppCompatActivity {

    Button btnFiveStars;
    ListView lv;
    ArrayList<Song> al;
//    ArrayAdapter<Song> aa;
    CustomAdapter adapter;

    Song data;
    Spinner spnYear;

    @Override
    protected void onResume() {
        super.onResume();

        DBHelper dbh = new DBHelper(ShowSong.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song);

        btnFiveStars = findViewById(R.id.btnFiveStars);
        lv = findViewById(R.id.lv);
        spnYear = findViewById(R.id.spnYear);


        al = new ArrayList<Song>();
        adapter = new CustomAdapter(this, R.layout.row, al);
        lv.setAdapter(adapter);


        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        btnFiveStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowSong.this);
                al.clear();
                al.addAll(dbh.getAll5StarSongs());
                adapter.notifyDataSetChanged();

                dbh.close();

            }
        });

        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ShowSong.this);
                al.clear();
                al = dbh.getYears(data.getYear()+"");
                adapter.notifyDataSetChanged();
//                switch (position) {
//                    case 0:
//                        spnYear.setSelection(0);
//                        break;
//
//                    case 1:
//                        break;
//
//                    case 2:
//                        break;
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = al.get(position);
                Intent i = new Intent(ShowSong.this,
                        ModifySong.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

    }
}