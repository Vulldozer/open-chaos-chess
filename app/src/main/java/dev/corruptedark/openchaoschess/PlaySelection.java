package dev.corruptedark.openchaoschess;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dev.corruptedark.openchaoschess.databinding.ActivityPlaySelectionBinding;

public class PlaySelection extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPlaySelectionBinding binding;
    volatile boolean buttonsClickable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlaySelectionBinding.inflate(getLayoutInflater());
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