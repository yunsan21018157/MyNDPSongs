package sg.edu.rp.c346.id21018157.ndpsongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ModifySong extends AppCompatActivity {

    TextView tvID, tvTitle, tvSingers, tvYear, tvStars;
    Button btnUpdate, btnDelete, btnCancel;
    EditText etTitle, etSingers, etYear;
    RadioGroup rgStars;
    RadioButton rb1, rb2, rb3, rb4, rb5;

    Song data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_song);

        tvID = findViewById(R.id.tvID);
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
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");

        //for autofill on modifySong page
        etTitle.setText(data.getTitle());
        etSingers.setText(data.getSingers());
        etYear.setText(data.getYear()+"");
        int star = data.getStars();

        switch (star) {
            case 1:
                rb1.setChecked(true);
                break;
            case 2:
                rb2.setChecked(true);
                break;
            case 3:
                rb3.setChecked(true);
                break;
            case 4:
                rb4.setChecked(true);
                break;
            case 5:
                rb5.setChecked(true);
                break;

        }


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySong.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSingers.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));

                int star = 1;
                switch (rgStars.getCheckedRadioButtonId()) {
                    case R.id.rb1:
                        star = 1;
                        break;
                    case R.id.rb2:
                        star = 2;
                        break;
                    case R.id.rb3:
                        star = 3;
                        break;
                    case R.id.rb4:
                        star = 4;
                        break;
                    case R.id.rb5:
                        star = 5;
                        break;

                }

                data.setStars(star);
                dbh.updateSong(data);
                dbh.close();

                finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifySong.this);
                dbh.deleteSong(data.get_id());

                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}