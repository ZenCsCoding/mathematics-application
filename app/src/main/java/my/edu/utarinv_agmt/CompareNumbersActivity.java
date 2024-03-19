package my.edu.utarinv_agmt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CompareNumbersActivity extends AppCompatActivity {

    private TextView number1TextView;
    private TextView number2TextView;
    private Button greaterThanButton;
    private Button lessThanButton;
    private Button equalToButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_numbers);

        number1TextView = findViewById(R.id.number1TextView);
        number2TextView = findViewById(R.id.number2TextView);
        greaterThanButton = findViewById(R.id.greaterThanButton);
        lessThanButton = findViewById(R.id.lessThanButton);
        equalToButton = findViewById(R.id.equalToButton);

        generateRandomNumbers();

        greaterThanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(">");
            }
        });

        lessThanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer("<");
            }
        });

        equalToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer("=");
            }
        });
    }

    private void generateRandomNumbers() {
        Random random = new Random();
        int num1 = random.nextInt(100); // Generate a number between 0 and 99
        int num2 = random.nextInt(100);
        number1TextView.setText(String.valueOf(num1));
        number2TextView.setText(String.valueOf(num2));
    }

    private void checkAnswer(String userChoice) {
        int num1 = Integer.parseInt(number1TextView.getText().toString());
        int num2 = Integer.parseInt(number2TextView.getText().toString());
        boolean answerIsCorrect = false;
        String correctAnswer;

        if (num1 > num2) {
            correctAnswer = ">";
        } else if (num1 < num2) {
            correctAnswer = "<";
        } else { // num1 == num2
            correctAnswer = "=";
        }

        answerIsCorrect = userChoice.equals(correctAnswer);

        String feedbackMessage;
        if (answerIsCorrect) {
            feedbackMessage = "Correct!";
        } else {
            feedbackMessage = "Incorrect! The correct answer is " + num1 + " " + correctAnswer + " " + num2;
        }

        showToast(feedbackMessage);

        // Generate new numbers for the next question
        generateRandomNumbers();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
