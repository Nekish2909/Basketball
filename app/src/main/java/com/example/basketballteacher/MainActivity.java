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

public class MainActivity extends AppCompatActivity {

    private ImageView imageView, ruleImage;
    private TextView questionTextView, ruleTitle, ruleText;
    private Button answerButton1, answerButton2, answerButton3, nextRuleButton;
    private LinearLayout startMenu, quizContent, rulesContent;
    private List<Integer> questionIndices;
    private int currentQuestionIndex = 0;
    private int score = 0;

    // Данные для правил
    private List<Rule> rules;
    private int currentRuleIndex = 0;
    private Object rounded_button_border;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов интерфейса
        startMenu = findViewById(R.id.startMenu);
        quizContent = findViewById(R.id.quizContent);
        rulesContent = findViewById(R.id.rulesContent);
        imageView = findViewById(R.id.imageView);
        questionTextView = findViewById(R.id.questionTextView);
        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        ruleTitle = findViewById(R.id.ruleTitle);
        ruleText = findViewById(R.id.ruleText);
        ruleImage = findViewById(R.id.ruleImage);
        nextRuleButton = findViewById(R.id.nextRuleButton);

        // Инициализация списка индексов вопросов и их перемешивание
        questionIndices = new ArrayList<>();
        for (int i = 0; i < getResources().getStringArray(R.array.questions).length; i++) {
            questionIndices.add(i);
        }
        Collections.shuffle(questionIndices);

        // Инициализация данных для правил
        rules = new ArrayList<>();
        rules.add(new Rule("Как правильно начинать игру в баскетбол?", "Игра начинается с спорного броска в центральном круге. Судья подбрасывает мяч между двумя игроками (обычно центровыми), которые пытаются отбить его своим партнёрам. Этот момент важен, так как он определяет, какая команда получит первое владение мячом.", R.drawable.rule1));
        rules.add(new Rule("Что такое \"дриблинг\" в баскетболе?", "это ведение мяча ударами об пол. Это один из ключевых навыков в баскетболе, позволяющий игроку перемещаться по площадке, обходить защитников и создавать голевые моменты.", R.drawable.rule2));
        rules.add(new Rule("Что такое \"двойное ведение\"?\n" +
                "\n", "это нарушение, когда игрок останавливается, а затем снова начинает вести мяч. Это правило было введено, чтобы предотвратить нечестное преимущество. Например, если игрок ведёт мяч, останавливается, а затем снова начинает ведение, судья фиксирует нарушение.", R.drawable.rule3));
        rules.add(new Rule("Какой рукой лучше вести мяч, если вы правша?", "Игрок должен использовать обе руки попеременно для ведения мяч. Это позволяет лучше контролировать мяч и обходить защитников. Например, правша может использовать левую руку для ведения, чтобы защитник не смог предугадать направление движения.", R.drawable.rule4));
        rules.add(new Rule("Что такое \"подбор\" в баскетболе?", " это получение мяча после неудачного броска. Подборы делятся на наступательные (подбор в атаке) и защитные (подбор в защите). Например, если мяч отскакивает от кольца, игрок может подобрать его и либо продолжить атаку, либо передать партнёру.", R.drawable.rule5));
        rules.add(new Rule("Как правильно выполнить штрафной бросок?", "Штрафной бросок выполняется с места, не заступая за линию. Это один из самых важных элементов игры, так как штрафные броски часто решают исход матча.", R.drawable.rule6));
        rules.add(new Rule("Что такое \"блок-шот\"?", " это прерывание броска соперника. Это один из самых зрелищных элементов защиты. Например, такие игроки, как Хаким Оладжьювон и Дикембе Мутомбо, известны своими блок-шотами.", R.drawable.rule7));
        rules.add(new Rule("Как называется зона, из которой засчитываются двухочковые броски?", "Двухочковые броски засчитываются из зоны, называемой \"краска\" (или запретная зона). Это область под кольцом, ограниченная линией штрафного броска.", R.drawable.rule8));
        rules.add(new Rule("Что такое \"ассист\"?\n" +
                "\n", "это передача, которая приводит к попаданию в кольцо. Это один из ключевых показателей командной игры.", R.drawable.rule9));
        rules.add(new Rule("Как называется нарушение, когда игрок держит мяч более 5 секунд при вбрасывании?", "Это нарушение называется \"нарушение 5 секунд\". Игрок должен вбросить мяч в игру в течение 5 секунд после сигнала судьи. Это правило было введено, чтобы ускорить игру и предотвратить затягивание времени", R.drawable.rule10));
        rules.add(new Rule("Что такое \"быстрый прорыв\" (фастбрек)?\n" +
                "\n", "это быстрая атака после подбора. Это одна из самых эффективных стратегий в баскетболе.", R.drawable.rule11));
        rules.add(new Rule("Какой высоты должно быть кольцо в баскетболе?", " Высота кольца составляет 3.05 метра. Это стандартный размер для всех профессиональных и любительских соревнований.", R.drawable.rule12));
        rules.add(new Rule("Что такое \"перехват\"?", "это отбор мяча у соперника. Это один из ключевых элементов защиты.", R.drawable.rule13));
        rules.add(new Rule("Какой тип броска чаще всего используется для набора очков?", "Чаще всего используются броски с близкого расстояния (двухочковые). Они более точные и дают больше шансов на успех. Например, бросок из-под кольца или с линии штрафного броска считается более надёжным, чем трёхочковый бросок.", R.drawable.rule14));
        rules.add(new Rule("Что такое \"зональная защита\"?", "это защита, при которой игроки защищают определенные зоны площадки. В отличие от персональной защиты, игроки не опекают конкретных соперников. Например, в зоне 2-3 два игрока находятся наверху, а три — внизу, чтобы защитить кольцо.", R.drawable.rule15));
        rules.add(new Rule("Что такое фол в баскетболе (общий термин)?", "это нарушение правил, связанное с физическим контактом или неспортивным поведением. Фолы могут быть персональными, техническими или неспортивными. Например, если игрок толкает соперника, судья фиксирует персональный фол. За несколько фолов игрок может быть удалён с площадки.", R.drawable.rule16));

        // Обработчик кнопки "Старт"
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> {
            startMenu.setVisibility(View.GONE); // Скрываем меню
            quizContent.setVisibility(View.VISIBLE); // Показываем тест
            currentQuestionIndex = 0; // Сбрасываем индекс на первый вопрос
            score = 0; // Сбрасываем счет
            loadQuestion(); // Загружаем первый вопрос
        });

        // Обработчик кнопки "Узнать"
        Button learnButton = findViewById(R.id.learnButton);
        learnButton.setOnClickListener(v -> {
            startMenu.setVisibility(View.GONE); // Скрываем меню
            rulesContent.setVisibility(View.VISIBLE); // Показываем правила
            currentRuleIndex = 0; // Сбрасываем индекс на первый слайд
            loadRule(); // Загружаем первое правило
        });

        // Обработчик кнопки "Следующее правило"
        nextRuleButton.setOnClickListener(v -> {
            currentRuleIndex = (currentRuleIndex + 1) % rules.size(); // Переключаем на следующее правило
            loadRule();
        });

        // Обработчик кнопки "Крестик"
        ImageView closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(v -> {
            rulesContent.setVisibility(View.GONE); // Скрываем правила
            startMenu.setVisibility(View.VISIBLE); // Показываем меню
        });

        // Обработчики для кнопок ответов
        answerButton1.setOnClickListener(v -> checkAnswer(0));
        answerButton2.setOnClickListener(v -> checkAnswer(1));
        answerButton3.setOnClickListener(v -> checkAnswer(2));
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
    }

    private void checkAnswer(int selectedAnswerIndex) {
        int questionIndex = questionIndices.get(currentQuestionIndex);
        int[] correctAnswers = getResources().getIntArray(R.array.correct_answers);
        int correctAnswerIndex = correctAnswers[questionIndex];

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
        questionTextView.setText("Ваш результат: " + score + " из " + questionIndices.size());

        // Кнопка "Пройти тест снова"
        Button restartButton = new Button(this);
        restartButton.setText("Пройти тест снова");
        restartButton.setTextSize(18);
        restartButton.setPadding(32, 16, 32, 16);
        restartButton.setBackgroundResource(R.drawable.rounded_button_border);
        restartButton.setTextColor(Color.WHITE);
        restartButton.setOnClickListener(v -> restartTest());

        // Добавляем кнопку в макет
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 32, 0, 0);
        restartButton.setLayoutParams(params);
        quizContent.addView(restartButton);
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
        if (quizContent.getChildCount() > 3) {
            quizContent.removeViewAt(quizContent.getChildCount() - 1);
        }

        // Загружаем первый вопрос
        loadQuestion();
    }

    private void loadRule() {
        Rule currentRule = rules.get(currentRuleIndex);
        ruleTitle.setText(currentRule.getTitle());
        ruleText.setText(currentRule.getDescription());
        ruleImage.setImageResource(currentRule.getImageResId());
    }

    // Класс для хранения данных о правиле
    private static class Rule {
        private String title;
        private String description;
        private int imageResId;

        public Rule(String title, String description, int imageResId) {
            this.title = title;
            this.description = description;
            this.imageResId = imageResId;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public int getImageResId() {
            return imageResId;
        }
    }
}