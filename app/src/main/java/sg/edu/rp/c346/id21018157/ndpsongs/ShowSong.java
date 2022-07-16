package sg.edu.rp.c346.id21018157.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowSong extends AppCompatActivity {

    Button btnFiveStars;
    ListView lv;
    ArrayList<Song> al;
    ArrayAdapter<Song> aa;

    Song data;

    @Override
    protected void onResume() {
        super.onResume();

        btnFiveStars.performClick();  //button fivestars or showList?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song);

        btnFiveStars = findViewById(R.id.btnFiveStars);
        lv = findViewById(R.id.lv);

        al = new ArrayList<Song>();
        aa = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        btnFiveStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowSong.this);
                al.clear();
                //if user enters a text in EditText and click RETRIEVE, filter result by text.
                //BUT IF EditText is empty, retrieve everyth.
                String filterText = data.getTitle().trim() + data.getSingers().trim() + data.getYear(); //alr IS a string so dont need toString()
//                String filterText = data.getStars().toString();
                if(filterText.length() == 0) {
                    al.addAll(dbh.getAllSongs());
                }
                else{
                    al.addAll(dbh.getAllSongs(filterText));
                }
                aa.notifyDataSetChanged();

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