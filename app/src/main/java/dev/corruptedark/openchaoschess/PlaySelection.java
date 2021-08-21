package dev.corruptedark.openchaoschess;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import dev.corruptedark.openchaoschess.databinding.ActivityPlaySelectionBinding;

public class PlaySelection extends AppCompatActivity {

    volatile boolean buttonsClickable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPlaySelectionBinding binding = ActivityPlaySelectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
    }
    public void playButtonClicked(View view){
        if (buttonsClickable) {
            buttonsClickable = false;
            if(SingleGame.getInstance().isKnightsOnly()) {
                SingleGame.getInstance().newGame();
                SingleGame.getInstance().setKnightsOnly(false);
            }
            Intent intent = new Intent(PlaySelection.this, SinglePlayerBoard.class);
            startActivity(intent);
        }
    }

    public void hostGameButtonClicked(View view)
    {
        if (buttonsClickable) {
            buttonsClickable = false;
            Intent intent = new Intent(PlaySelection.this, StartHostActivity.class);
            startActivity(intent);
        }
    }

    public void joinGameButtonClicked(View view)
    {
        if (buttonsClickable) {
            buttonsClickable = false;
            Intent intent = new Intent(PlaySelection.this, StartClientActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPostResume() {
        buttonsClickable = true;
        super.onPostResume();
    }

}