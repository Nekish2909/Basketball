package com.example.basketballteacher;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView questionTextView;
    private Button answerButton1, answerButton2, answerButton3;
    private LinearLayout startMenu, quizContent;
    private List<Integer> questionIndices;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов интерфейса
        startMenu = findViewById(R.id.startMenu);
        quizContent = findViewById(R.id.quizContent);
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

        // Обработчик кнопки "Начать"
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            startMenu.setVisibility(View.GONE); // Скрываем меню
            quizContent.setVisibility(View.VISIBLE); // Показываем тест
            loadQuestion(); // Загружаем первый вопрос
        });
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

        // Загрузка изображения
        String imageName = "img" + String.format("%03d", questionIndex + 1);
        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
        if (imageResId == 0) {
            imageView.setImageResource(R.drawable.placeholder); // Заглушка, если изображение не найдено
        } else {
            imageView.setImageResource(imageResId);
        }

        // Установка текста вопроса и ответов
        questionTextView.setText(questions[questionIndex]);
        answerButton1.setText(answers[questionIndex * 3]);
        answerButton2.setText(answers[questionIndex * 3 + 1]);
        answerButton3.setText(answers[questionIndex * 3 + 2]);

        // Сброс цвета кнопок
        answerButton1.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        answerButton2.setBackgroundColor(Color.parseColor("#FFBB86FC"));
        answerButton3.setBackgroundColor(Color.parseColor("#FFBB86FC"));

        // Включаем кнопки (если они были отключены)
        answerButton1.setEnabled(true);
        answerButton2.setEnabled(true);
        answerButton3.setEnabled(true);

        // Установка обработчиков кликов
        answerButton1.setOnClickListener(v -> checkAnswer(0, correctAnswers[questionIndex]));
        answerButton2.setOnClickListener(v -> checkAnswer(1, correctAnswers[questionIndex]));
        answerButton3.setOnClickListener(v -> checkAnswer(2, correctAnswers[questionIndex]));
    }

    private void checkAnswer(int selectedAnswerIndex, int correctAnswerIndex) {
        // Подсветка правильного ответа зеленым
        if (correctAnswerIndex == 0) answerButton1.setBackgroundColor(Color.GREEN);
        if (correctAnswerIndex == 1) answerButton2.setBackgroundColor(Color.GREEN);
        if (correctAnswerIndex == 2) answerButton3.setBackgroundColor(Color.GREEN);

        // Подсветка выбранного ответа (если он неправильный)
        if (selectedAnswerIndex != correctAnswerIndex) {
            if (selectedAnswerIndex == 0) answerButton1.setBackgroundColor(Color.RED);
            if (selectedAnswerIndex == 1) answerButton2.setBackgroundColor(Color.RED);
            if (selectedAnswerIndex == 2) answerButton3.setBackgroundColor(Color.RED);
        }

        // Проверка правильности ответа
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++;
        }

        // Блокировка кнопок после выбора ответа
        answerButton1.setEnabled(false);
        answerButton2.setEnabled(false);
        answerButton3.setEnabled(false);

        // Задержка перед переходом к следующему вопросу
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            currentQuestionIndex++;
            if (currentQuestionIndex < questionIndices.size()) {
                loadQuestion(); // Загрузка следующего вопроса
            } else {
                showResult(); // Тест завершен
            }
        }, 1500); // Задержка 1.5 секунды
    }

    private void showResult() {
        // Скрываем кнопки и изображение
        imageView.setVisibility(View.GONE);
        answerButton1.setVisibility(View.GONE);
        answerButton2.setVisibility(View.GONE);
        answerButton3.setVisibility(View.GONE);

        // Отображаем результат с крупным текстом
        questionTextView.setTextSize(32); // Увеличиваем размер текста
        questionTextView.setText("Ваш результат: " + score);

        // Создаем кнопку "Играть еще раз"
        Button restartButton = new Button(this);
        restartButton.setText("Играть еще раз"); // Устанавливаем текст
        restartButton.setTextSize(18); // Увеличиваем размер текста
        restartButton.setTextColor(Color.BLACK); // Черный текст
        restartButton.setPadding(32, 16, 32, 16); // Увеличиваем отступы
        restartButton.setBackgroundResource(R.drawable.rounded_button_border); // Фиолетовая рамка
        restartButton.setOnClickListener(v -> restartTest()); // Обработчик нажатия

        // Параметры для выравнивания кнопки по центру
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER; // Выравниваем по центру
        params.setMargins(0, 32, 0, 0); // Отступ сверху
        restartButton.setLayoutParams(params);

        // Добавляем кнопку в макет
        quizContent.addView(restartButton);
    }

    private void restartTest() {
        // Сброс переменных
        currentQuestionIndex = 0;
        score = 0;
        Collections.shuffle(questionIndices);

        // Показываем элементы интерфейса
        imageView.setVisibility(View.VISIBLE);
        answerButton1.setVisibility(View.VISIBLE);
        answerButton2.setVisibility(View.VISIBLE);
        answerButton3.setVisibility(View.VISIBLE);

        // Удаляем кнопку "Начать заново"
        if (quizContent.getChildCount() > 3) { // Убедитесь, что удаляем только кнопку restartButton
            quizContent.removeViewAt(quizContent.getChildCount() - 1);
        }

        // Загружаем первый вопрос
        loadQuestion();
    }
}