package quiz.project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import java.util.concurrent.atomic.AtomicBoolean;


public class MenuActivity extends AppCompatActivity {

    private CardView clothingCard;
    private ImageButton profileButton,settingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        profileButton = findViewById(R.id.profileBtn);
        settingButton = findViewById(R.id.settingBtn);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        clothingCard = findViewById(R.id.clothingCard);
        clothingCard.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, science.class);
            startActivity(intent);
        });
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
        settingButton.setOnClickListener(v -> {
            showDialog();
        });

    }
    Dialog dialog;
    private void showDialog() {
        if (dialog == null || !dialog.isShowing()) {
            ImageButton SoundOn1,SoundOn2;
            Button contact;
            TextView On1,On2;
            AtomicBoolean isSoundOn = new AtomicBoolean(true);
            AtomicBoolean isSoundOn2 = new AtomicBoolean(true);
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.setting_layout);

            SoundOn1 = dialog.findViewById(R.id.on1);
            SoundOn2 = dialog.findViewById(R.id.on2);
            On1 = dialog.findViewById(R.id.texton1);
            On2 = dialog.findViewById(R.id.texton2);
            contact = dialog.findViewById(R.id.contact);
            SoundOn1.setOnClickListener(v -> {
                if (!isSoundOn.get()) {
                    isSoundOn.set(true);
                    SoundOn1.setImageDrawable(getResources().getDrawable(R.drawable.soundon));
                    On1.setText("เพลงเปิดอยู่");
                } else {
                    isSoundOn.set(false);
                    SoundOn1.setImageDrawable(getResources().getDrawable(R.drawable.soundoff));
                    On1.setText("เพลงปิดอยู่");
                }
            });
            SoundOn2.setOnClickListener(v -> {
                if (!isSoundOn2.get()) {
                    isSoundOn2.set(true);
                    SoundOn2.setImageDrawable(getResources().getDrawable(R.drawable.soundon));
                    On2.setText("เอฟเฟคเปิดอยู่");
                } else {
                    isSoundOn2.set(false);
                    SoundOn2.setImageDrawable(getResources().getDrawable(R.drawable.soundoff));
                    On2.setText("เอฟเฟคปิดอยู่");
                }
            });
            contact.setOnClickListener(v -> {
                Intent intent = new Intent(MenuActivity.this, main_con.class);
                startActivity(intent);
            });
            dialog.show();
        }
    }

}