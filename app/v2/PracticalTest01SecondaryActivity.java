package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);
        Intent i = getIntent();
        final int sum = i.getIntExtra("sum", 0);
        final Context c = this;
        TextView t1 = findViewById(R.id.textView4);
        t1.setText(sum + "");

        Button bok = findViewById(R.id.button3);
        Button bcancel = findViewById(R.id.button4);
        bok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, sum + "", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        bcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, sum + "", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
