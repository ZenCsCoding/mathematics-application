package my.edu.utarinv_agmt;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ComposeNumbersActivity extends AppCompatActivity {

    private TextView textViewFirstNumber, textViewResultNumber;
    private int correctAnswer;
    private final Random random = new Random();
    private LinearLayout linearLayoutOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_numbers);

        textViewFirstNumber = findViewById(R.id.textViewFirstNumber);
        textViewResultNumber = findViewById(R.id.textViewResultNumber);
        linearLayoutOptions = findViewById(R.id.linearLayoutOptions);

        generateNewEquation();
    }

    private void generateNewEquation() {
        generateRandomEquation();
        generateAnswerOptions();
    }

    private void generateRandomEquation() {
        int firstNumber = random.nextInt(9) + 1; // Generates a number between 1 and 9
        correctAnswer = random.nextInt(9) + 1; // Generates the correct answer between 1 and 9
        int resultNumber = firstNumber + correctAnswer;

        textViewFirstNumber.setText(String.valueOf(firstNumber));
        textViewResultNumber.setText(String.valueOf(resultNumber));
    }

    private void generateAnswerOptions() {
        linearLayoutOptions.removeAllViews(); // Clear previous options
        List<Integer> options = generateOptions();

        for (int option : options) {
            Button button = new Button(this);
            button.setText(String.valueOf(option));
            button.setOnClickListener(v -> checkAnswer(option, button)); // Pass the button as an argument
            linearLayoutOptions.addView(button);
        }
    }


    private List<Integer> generateOptions() {
        List<Integer> options = new ArrayList<>();
        options.add(correctAnswer); // Ensure the correct answer is included
        while (options.size() < 4) {
            int option = random.nextInt(9) + 1;
            // Avoid duplicate options
            if (!options.contains(option)) {
                options.add(option);
            }
        }
        Collections.shuffle(options); // Shuffle the options to randomize positions
        return options;
    }

    private void checkAnswer(int selectedOption, Button selectedButton) {
        // If the selected option is incorrect, disable only the selected button and change its color to red.
        if (selectedOption != correctAnswer) {
            selectedButton.setEnabled(false); // Disable the incorrect button
            selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            Toast.makeText(this, "Try again.", Toast.LENGTH_SHORT).show();
        } else {
            // If the selected option is correct, change its color to green and disable all options.
            for (int i = 0; i < linearLayoutOptions.getChildCount(); i++) {
                Button button = (Button) linearLayoutOptions.getChildAt(i);
                button.setEnabled(false); // Disable all buttons after the correct answer is chosen
                if(button == selectedButton) {
                    button.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                }
            }
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            // Wait a bit before generating a new equation to improve IU
            new Handler().postDelayed(() -> {
                generateNewEquation();
                // Re-enable the buttons and reset colors for the new equation
                for (int i = 0; i < linearLayoutOptions.getChildCount(); i++) {
                    Button button = (Button) linearLayoutOptions.getChildAt(i);
                    button.setEnabled(true);
                    button.setBackgroundColor(getResources().getColor(android.R.color.transparent)); // Reset to default
                }
            }, 2000);
        }
    }


}
