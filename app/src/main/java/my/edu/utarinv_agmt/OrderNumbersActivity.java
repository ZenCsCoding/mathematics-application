package my.edu.utarinv_agmt;



import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class OrderNumbersActivity extends AppCompatActivity {

    private LinearLayout placeholderContainer, numberOptionsContainer;

    private List<Integer> numbers = new ArrayList<>();
    private List<TextView> placeholders = new ArrayList<>();
    private int[] correctOrder;
    private Set<Integer> correctPlaceholdersIndices = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_numbers);

        placeholderContainer = findViewById(R.id.placeholderContainer);
        numberOptionsContainer = findViewById(R.id.numberOptionsContainer);
        Button checkAnswerButton = findViewById(R.id.checkAnswerButton);

        generateNumberOptions();
        populatePlaceholders();

        checkAnswerButton.setOnClickListener(v -> checkAnswer());

        Button resetGameButton = findViewById(R.id.buttonResetGame);
        resetGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        Button clearWrongButton = findViewById(R.id.buttonClearWrong);
        clearWrongButton.setOnClickListener(v -> clearWrongAnswers());
    }

    private void generateNumberOptions() {
        Random random = new Random();
        while (numbers.size() < 5) { // 5 numbers
            int randomNumber = random.nextInt(101); // Generates a number from 0 to 100
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }

        correctOrder = numbers.stream().mapToInt(i -> i).toArray();

        for (Integer number : numbers) {
            Button button = new Button(this);
            button.setBackground(getResources().getDrawable(R.drawable.circle_background, null));
            button.setText(String.valueOf(number));
            button.setOnClickListener(v -> {
                button.setEnabled(false); // Disable button after click
                fillNextPlaceholder(number);
            });
            numberOptionsContainer.addView(button);
        }
    }

    private void populatePlaceholders() {
        for (int i = 0; i < numbers.size(); i++) {
            TextView textView = new TextView(this);
            textView.setBackground(getResources().getDrawable(android.R.drawable.dialog_holo_light_frame));
            textView.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
            placeholderContainer.addView(textView);
            placeholders.add(textView);
        }
    }

    private void fillNextPlaceholder(Integer number) {
        for (TextView placeholder : placeholders) {
            if (placeholder.getText().toString().isEmpty()) {
                placeholder.setText(String.valueOf(number));
                break;
            }
        }
    }

    private void checkAnswer() {
        boolean isCorrect = true;

        // Extract numbers from placeholders
        List<Integer> userOrder = new ArrayList<>();
        for (TextView placeholder : placeholders) {
            try {
                int num = Integer.parseInt(placeholder.getText().toString());
                userOrder.add(num);
                // Reset placeholder backgrounds to a default state before validation
                placeholder.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            } catch (NumberFormatException e) {
                // Handle case where placeholder is empty
                isCorrect = false;
                placeholder.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }

        // Sort a copy of userOrder to find the correct sequence
        List<Integer> sortedOrder = new ArrayList<>(userOrder);
        Collections.sort(sortedOrder);

        // Compare userOrder to sortedOrder to check positions
        for (int i = 0; i < userOrder.size(); i++) {
            if (userOrder.get(i).equals(sortedOrder.get(i))) {
                // Correct
                placeholders.get(i).setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            } else {
                // Incorrect
                isCorrect = false;
                placeholders.get(i).setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }

        if (isCorrect) {
            Toast.makeText(this, "Correct order!", Toast.LENGTH_SHORT).show();
            //to post delay task
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startNextGame();
                }
            }, 3000);
        } else {
            Toast.makeText(this, "Some numbers are in the wrong position, try again.", Toast.LENGTH_SHORT).show();
        }
    }




    private void resetGame() {
        for (TextView placeholder : placeholders) {
            placeholder.setText(""); // to clear the text
            placeholder.setBackgroundResource(android.R.drawable.dialog_holo_light_frame); // reset background
        }
        // Re-enable number option buttons if they are disabled
        for (int i = 0; i < numberOptionsContainer.getChildCount(); i++) {
            Button button = (Button) numberOptionsContainer.getChildAt(i);
            button.setEnabled(true);
        }
    }

    private void clearWrongAnswers() {
        // Extract numbers from placeholders in the order they were placed by the user
        List<Integer> userNumbers = new ArrayList<>();
        for (TextView placeholder : placeholders) {
            try {
                int num = Integer.parseInt(placeholder.getText().toString());
                userNumbers.add(num);
            } catch (NumberFormatException e) {
                // This placeholder is empty or not a number, skip it
            }
        }

        // Determine which numbers are out of order
        List<Integer> sortedUserNumbers = new ArrayList<>(userNumbers);
        Collections.sort(sortedUserNumbers);

        for (int i = 0; i < userNumbers.size(); i++) {
            int number = userNumbers.get(i);
            TextView placeholder = placeholders.get(i);

            // If the number does not match the sorted list, it's in the wrong position
            if (i < sortedUserNumbers.size() && number != sortedUserNumbers.get(i)) {
                placeholder.setText(""); // Clear the placeholder text
                placeholder.setBackground(getResources().getDrawable(android.R.drawable.dialog_holo_light_frame)); // Reset background
                enableButtonWithNumber(number); // Re-enable the corresponding button
            } else if (i < sortedUserNumbers.size()) {
                // If the number is in the correct position, optionally highlight it
                placeholder.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
            }
        }
    }

    private void enableButtonWithNumber(int number) {
        for (int i = 0; i < numberOptionsContainer.getChildCount(); i++) {
            Button button = (Button) numberOptionsContainer.getChildAt(i);
            if (Integer.parseInt(button.getText().toString()) == number) {
                button.setEnabled(true); // Re-enable the button
                break;
            }
        }
    }


    private void startNextGame() {
        // Clear the current state
        numbers.clear();
        placeholderContainer.removeAllViews();
        numberOptionsContainer.removeAllViews();
        placeholders.clear();

        //new set of numbers
        generateNumberOptions();
        populatePlaceholders();
    }

}
