package com.example.basketballteacher; // Замените на ваш пакет

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        // Проверка, что вопросы не закончились
        if (currentQuestionIndex >= questionIndices.size()) {
            showResult(); // Если вопросы закончились, показываем результат
            return;
        }

        int questionIndex = questionIndices.get(currentQuestionIndex);
        String[] questions = getResources().getStringArray(R.array.questions);
        String[] answers = getResources().getStringArray(R.array.answers);
        int[] correctAnswers = getResources().getIntArray(R.array.correct_answers);

        // Проверка, что questionIndex не выходит за пределы массива
        if (questionIndex >= questions.length || questionIndex >= answers.length / 3 || questionIndex >= correctAnswers.length) {
            Log.e("QuestionError", "Invalid question index: " + questionIndex);
            showResult(); // Показываем результат, если индекс неверный
            return;
        }

        // Загрузка изображения
        String imageName = "img" + String.format("%03d", questionIndex + 1);
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId == 0) {
            Log.e("ImageDebug", "Image not found: " + imageName);
        } else {
            imageView.setImageResource(imageResId);
        }

        // Установка текста вопроса и ответов
        questionTextView.setText(questions[questionIndex]);
        answerButton1.setText(answers[questionIndex * 3]);
        answerButton2.setText(answers[questionIndex * 3 + 1]);
        answerButton3.setText(answers[questionIndex * 3 + 2]);

        // Установка обработчиков кликов
        answerButton1.setOnClickListener(v -> checkAnswer(0, correctAnswers[questionIndex]));
        answerButton2.setOnClickListener(v -> checkAnswer(1, correctAnswers[questionIndex]));
        answerButton3.setOnClickListener(v -> checkAnswer(2, correctAnswers[questionIndex]));
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
            showResult();
        }
    }

    private void showResult() {
        // Скрываем кнопки и изображение
        imageView.setVisibility(View.GONE);
        answerButton1.setVisibility(View.GONE);
        answerButton2.setVisibility(View.GONE);
        answerButton3.setVisibility(View.GONE);

        // Отображаем результат
        questionTextView.setText("Тест завершен! Ваш счет: " + score + " из " + questionIndices.size());

        // (Опционально) Добавьте кнопку для повторного прохождения теста
        Button restartButton = new Button(this);
        restartButton.setText("Пройти тест снова");
        restartButton.setOnClickListener(v -> restartTest());

        // Добавляем кнопку в макет
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.addView(restartButton);
    }

    private void restartTest() {
        // Сброс переменных и запуск теста заново
        currentQuestionIndex = 0;
        score = 0;
        Collections.shuffle(questionIndices);

        // Показываем элементы интерфейса
        imageView.setVisibility(View.VISIBLE);
        answerButton1.setVisibility(View.VISIBLE);
        answerButton2.setVisibility(View.VISIBLE);
        answerButton3.setVisibility(View.VISIBLE);

        // Удаляем кнопку "Пройти тест снова"
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        if (mainLayout.getChildCount() > 3) { // Убедитесь, что удаляем только кнопку restartButton
            mainLayout.removeViewAt(mainLayout.getChildCount() - 1);
        }

        // Загружаем первый вопрос
        loadQuestion();
    }
}