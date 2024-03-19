package my.edu.utarinv_agmt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonCompareNumbers;
    private Button buttonOrderNumbers;
    private Button buttonComposeNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        buttonCompareNumbers = findViewById(R.id.buttonCompareNumbers);
        buttonOrderNumbers = findViewById(R.id.buttonOrderNumbers);
        buttonComposeNumbers = findViewById(R.id.buttonComposeNumbers);

        // Set click listeners for each button
        buttonCompareNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start CompareNumbers activity
                Intent intent = new Intent(MainActivity.this, CompareNumbersActivity.class);
                startActivity(intent);
            }
        });

        buttonOrderNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start OrderNumbers activity
                Intent intent = new Intent(MainActivity.this, OrderNumbersActivity.class);
                startActivity(intent);
            }
        });

        buttonComposeNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start ComposeNumbers activity
                Intent intent = new Intent(MainActivity.this, ComposeNumbersActivity.class);
                startActivity(intent);
            }
        });
    }
}
