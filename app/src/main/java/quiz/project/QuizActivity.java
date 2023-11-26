package quiz.project;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private int currentQuestionIndex = 0;
    private int score = 0;
    private CountDownTimer timer;
    private Dialog dialog;
    private TextView currentQuestionText;
    private TextView timerText;
    private ImageView questionImage;
    private Button ans1, ans2, ans3, ans4;
    private ImageButton skipButton, hintButton, pauseButton;
    private View dimmingView;
    private boolean isTimerPaused = false;
    private long remainingTimeMillis;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        currentQuestionText = findViewById(R.id.current_question);
        timerText = findViewById(R.id.textView);
        questionImage = findViewById(R.id.question);
        ans1 = findViewById(R.id.ans1);
        ans2 = findViewById(R.id.ans2);
        ans3 = findViewById(R.id.ans3);
        ans4 = findViewById(R.id.ans4);
        skipButton = findViewById(R.id.skip);
        hintButton = findViewById(R.id.hint);
        pauseButton = findViewById(R.id.pausebtn);
        dimmingView = findViewById(R.id.dimmingView);
        hintButton = findViewById(R.id.hint);

        setQuestion();

        // Set up timer
        startTimer();

        ans1.setOnClickListener(v -> checkAnswer(ans1));
        ans2.setOnClickListener(v -> checkAnswer(ans2));
        ans3.setOnClickListener(v -> checkAnswer(ans3));
        ans4.setOnClickListener(v -> checkAnswer(ans4));
        skipButton.setOnClickListener(v -> nextQuestion());
        pauseButton.setOnClickListener(v -> pauseTimer());
        hintButton.setOnClickListener(v -> showHint());
    }

    private void setQuestion() {
        // Set question text, image, and choices based on currentQuestionIndex
        currentQuestionText.setText("ข้อที่ " + (currentQuestionIndex + 1));
        questionImage.setImageResource(getResources().getIdentifier("ques" + (currentQuestionIndex + 1), "drawable", getPackageName()));

        ans1.setText(choices[currentQuestionIndex][0]);
        ans2.setText(choices[currentQuestionIndex][1]);
        ans3.setText(choices[currentQuestionIndex][2]);
        ans4.setText(choices[currentQuestionIndex][3]);
    }

    private void checkAnswer(Button selectedAnswer) {
        // Check if the selected answer is correct
        String selectedText = selectedAnswer.getText().toString();
        String correctAnswer = correctAnswers[currentQuestionIndex];

        if (selectedText.equals(correctAnswer)) {
            // Correct answer
            selectedAnswer.setBackgroundColor(Color.GREEN);
            score++;
        } else {
            // Incorrect answer
            selectedAnswer.setBackgroundColor(Color.RED);
            getCorrectAnswerButton().setBackgroundColor(Color.GREEN);
        }

        // Move to the next question after a delay
        ans1.setEnabled(false);
        ans2.setEnabled(false);
        ans3.setEnabled(false);
        ans4.setEnabled(false);

        new android.os.Handler().postDelayed(() -> {
            nextQuestion();
            resetButtonColors();
            ans1.setEnabled(true);
            ans2.setEnabled(true);
            ans3.setEnabled(true);
            ans4.setEnabled(true);
        }, 1000);
    }

    private Button getCorrectAnswerButton() {
        // Get the button corresponding to the correct answer
        String correctAnswer = correctAnswers[currentQuestionIndex];
        if (ans1.getText().toString().equals(correctAnswer)) {
            return ans1;
        } else if (ans2.getText().toString().equals(correctAnswer)) {
            return ans2;
        } else if (ans3.getText().toString().equals(correctAnswer)) {
            return ans3;
        } else {
            return ans4;
        }
    }

    private void resetButtonColors() {
        // Reset button colors to default
        ans1.setBackgroundColor(Color.WHITE);
        ans2.setBackgroundColor(Color.WHITE);
        ans3.setBackgroundColor(Color.WHITE);
        ans4.setBackgroundColor(Color.WHITE);
    }

    private void nextQuestion() {
        ans1.setVisibility(View.VISIBLE);
        ans2.setVisibility(View.VISIBLE);
        ans3.setVisibility(View.VISIBLE);
        ans4.setVisibility(View.VISIBLE);
        if (currentQuestionIndex < choices.length - 1) {
            currentQuestionIndex++;
            setQuestion();
        } else {
            // Quiz completed, show result
            showResult();
        }
    }


    private void showDialog() {
        if (dialog == null || !dialog.isShowing()) {
            dimmingView.setVisibility(View.VISIBLE);
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.pause_screen);

            Button resumeButton = dialog.findViewById(R.id.resume);
            Button menuButton = dialog.findViewById(R.id.menu);

            resumeButton.setOnClickListener(v -> {
                dialog.dismiss();
                dimmingView.setVisibility(View.GONE);
                resumeTimer();
            });

            menuButton.setOnClickListener(v -> {
                Intent intent = new Intent(QuizActivity.this, MenuActivity.class);
                startActivity(intent);
            });

            dialog.show();
        }
    }
    private void startTimer() {
        timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!isTimerPaused) {
                    remainingTimeMillis = millisUntilFinished;
                    timerText.setText(millisUntilFinished / 1000 + " วินาที");
                }
            }

            @Override
            public void onFinish() {
                if (!isTimerPaused) {
                    showResult();
                }
            }
        }.start();
    }

    private void pauseTimer() {
        if (!isTimerPaused) {
            isTimerPaused = true;
            timer.cancel();
            showDialog();
        }
    }

    private void resumeTimer() {
        if (isTimerPaused) {
            isTimerPaused = false;
            startTimerFromRemainingTime();
        }
    }

    private void startTimerFromRemainingTime() {
        timer = new CountDownTimer(remainingTimeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!isTimerPaused) {
                    remainingTimeMillis = millisUntilFinished;
                    timerText.setText(String.valueOf(millisUntilFinished / 1000));
                }
            }

            @Override
            public void onFinish() {
                //if (!isTimerPaused) {
                //}
            }
        }.start();
    }


    private void showResult() {
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
    }
    private void showHint() {
        // Get the correct answer
        String correctAnswer = correctAnswers[currentQuestionIndex];

        // Find one incorrect answer
        String incorrectAnswer = "";
        for (String answer : choices[currentQuestionIndex]) {
            if (!answer.equals(correctAnswer)) {
                incorrectAnswer = answer;
                break;
            }
        }

        // Hide all answer buttons except the correct one and one incorrect answer
        hideIncorrectAnswerButtons(correctAnswer, incorrectAnswer);
    }

    private void hideIncorrectAnswerButtons(String answerToKeep, String answerToKeep2) {
        if (!ans1.getText().toString().equals(answerToKeep) && !ans1.getText().toString().equals(answerToKeep2)) {
            ans1.setVisibility(View.INVISIBLE);
        } else {
            ans1.setVisibility(View.VISIBLE);
        }

        if (!ans2.getText().toString().equals(answerToKeep) && !ans2.getText().toString().equals(answerToKeep2)) {
            ans2.setVisibility(View.INVISIBLE);
        } else {
            ans2.setVisibility(View.VISIBLE);
        }

        if (!ans3.getText().toString().equals(answerToKeep) && !ans3.getText().toString().equals(answerToKeep2)) {
            ans3.setVisibility(View.INVISIBLE);
        } else {
            ans3.setVisibility(View.VISIBLE);
        }

        if (!ans4.getText().toString().equals(answerToKeep) && !ans4.getText().toString().equals(answerToKeep2)) {
            ans4.setVisibility(View.INVISIBLE);
        } else {
            ans4.setVisibility(View.VISIBLE);
        }
    }


    public static String choices[][] ={
            {"ตะไคร่น้ำ มอส","แบคทีเรีย เฟิน","สาหร่าย เห็ดรา","เฟิร์น ยีสต์"},
            {"คว่ำตายหงายเป็น กุหลาบหิน","ต้นหม้อข้าวหม้อแกงลิง หยาดน้ำค้าง","ตะบองเพชร ผักตบชวา","ตำลึง ฟักทอง"},
            {"ปุ๋ยหมัก","ปุ๋ยคอก","ปุ๋ยเคมี","ปุ๋ยอินทรีย์"},
            {"ไข่อ่อน","ไซโกต","รังไข่","เมล็ด"},
            {"เชื้อโรคถูกทำลาย","เชื้อโรคหยุดการเจริญเติบโต","เชื้อราถูกทำลายด้วยอุณหภูมิต่ำในตู้เย็น","แบคทีเรียหยุดการเจริญเติบโต"},
            {"พู่ระหง มีดอกที่มีเกสรตัวผู้และเกสรตัวเมียอยู่ภายในดอกเดียวกัน","เมื่อเกิดการปฏิสนธิในพืชที่มีดอก ออวุลจะเจริญไปเป็นเมล็ด","ภายในรังไข่ของพืชดอกจะมีออวุล และภายในออวุลจะมีเซลล์สืบพันธุ์เพศผู้","ข้าวโพดมีดอกไม่สมบูรณ์เพศ"},
            {"การเพาะเมล็ด","การตอน","การติดตา","การปักชำ"},
            {"ทำให้รากของพืชแข็งแรง","ได้พืชในลักษณะที่เหมือนเดิม","ได้พันธุ์พืชตามต้องการ","ได้พืชในจำนวนมาก โดยใช้เวลาสั้นๆ"},
            {"รูเล็กๆ บนใบไม้ ที่มีหน้าที่รับอากาศและคายน้ำออก เรียกว่า ปากใบ","หน้าที่สำคัญของลำต้นคือ ลำเลียงอาหารและน้ำจากรากไปสู่ใบเพื่อปรุงอาหาร","ประโยชน์ทางตรงของพืช คือการใช้เป็นอาหาร","พืชปรุงอาหารในเวลากลางคืนและคายน้ำในเวลากลางวัน"},
            {"ดอก ใบ","ดอก ผล","ลำต้น ใบ","ผล ราก"},
            {"เป็นเม็ดเล็กๆ","เป็นก้อนหลายก้อน","เป็นละอองเล็กๆ คล้ายฝุ่น","เป็นน้ำเหลวๆ"},
            {"ลำตัวของพยาธิตัวตืด พยาธิใบไม้ มีลักษณะเป็นตัวกลม","ปะการัง แมงกะพรุน ขยายพันธุ์โดยการแตกหน่อ","อะมีบา พารามีเซียม สืบพันธุ์โดยใช้วิธีแบ่งตัว","พะยูน แมวลายหินอ่อน วัว เป็นสัตว์เลี้ยงลูกด้วยนม"},
            {"ช้าง","กระต่าย","สุนัข","แมว"},
            {"กบ ปลา","แมวน้ำ สิงโตทะเล","งู พะยูน","ไก่ อึ่งอ่าง"},
            {"พยาธิเส้นด้าย","อะมีบา","ดอกไม้ทะเล","กัลปังหา"}
    };
    public static String correctAnswers[] ={
            "ตะไคร่น้ำ มอส",
            "ต้นหม้อข้าวหม้อแกงลิง หยาดน้ำค้าง",
            "ปุ๋ยเคมี",
            "รังไข่",
            "เชื้อโรคหยุดการเจริญเติบโต",
            "ภายในรังไข่ของพืชดอกจะมีออวุล และภายในออวุลจะมีเซลล์สืบพันธุ์เพศผู้",
            "การเพาะเมล็ด",
            "ทำให้รากของพืชแข็งแรง",
            "พืชปรุงอาหารในเวลากลางคืนและคายน้ำในเวลากลางวัน",
            "ดอก ผล",
            "เป็นละอองเล็กๆ คล้ายฝุ่น",
            "ลำตัวของพยาธิตัวตืด พยาธิใบไม้ มีลักษณะเป็นตัวกลม",
            "กระต่าย",
            "กบ ปลา",
            "อะมีบา"
    };

}
