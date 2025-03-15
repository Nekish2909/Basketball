package com.example.basketballteacher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView questionTextView;
    private Button answerButton1, answerButton2, answerButton3;
    private List<Integer> questionIndices;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов интерфейса
        imageView = findViewById(R.id.imageView);
        questionTextView = findViewById(R.id.questionTextView);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);

        // Инициализация списка индексов вопросов и их перемешивание
        questionIndices = new ArrayList<>();
        for (int i = 0; i < getResources().getStringArray(R.array.questions).length; i++) {
            questionIndices.add(i);
        }
        Collections.shuffle(questionIndices);

        // Загрузка первого вопроса
        loadQuestion();
    }

    private void loadQuestion() {
        // Получение текущего индекса вопроса
        int questionIndex = questionIndices.get(currentQuestionIndex);

        // Загрузка вопросов, ответов и правильных ответов из ресурсов
        String[] questions = getResources().getStringArray(R.array.questions);
        String[] answers = getResources().getStringArray(R.array.answers);
        int[] correctAnswers = getResources().getIntArray(R.array.correct_answers);

        // Загрузка изображения для текущего вопроса
        String imageName = "img" + String.format("%03d", questionIndex + 1); // Формат: img001, img002 и т.д.
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        // Отладка: проверка загрузки изображения
        if (imageResId == 0) {
            Log.e("ImageDebug", "Image not found: " + imageName);
        } else {
            imageView.setImageResource(imageResId);
        }

        // Установка текста вопроса
        questionTextView.setText(questions[questionIndex]);

        // Установка текста кнопок с ответами
        answerButton1.setText(answers[questionIndex * 3]);
        answerButton2.setText(answers[questionIndex * 3 + 1]);
        answerButton3.setText(answers[questionIndex * 3 + 2]);

        // Установка обработчиков кликов для кнопок
        answerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(0, correctAnswers[questionIndex]);
            }
        });

        answerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1, correctAnswers[questionIndex]);
            }
        });

        answerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2, correctAnswers[questionIndex]);
            }
        });
    }

    private void checkAnswer(int selectedAnswerIndex, int correctAnswerIndex) {
        // Проверка правильности ответа
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++; // Увеличение счета при правильном ответе
        }

        // Переход к следующему вопросу
        currentQuestionIndex++;
        if (currentQuestionIndex < questionIndices.size()) {
            loadQuestion(); // Загрузка следующего вопроса
        } else {
            // Тест завершен, отображение результата
            questionTextView.setText("Тест завершен! Ваш счет: " + score);
            answerButton1.setVisibility(View.GONE);
            answerButton2.setVisibility(View.GONE);
            answerButton3.setVisibility(View.GONE);
        }
    }
}