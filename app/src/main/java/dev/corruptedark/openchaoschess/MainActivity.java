/*
 * Open Chaos Chess is a free as in speech version of Chaos Chess
 * Chaos Chess is a chess game where you control the piece that moves, but not how it moves
 *     Copyright (C) 2019  Noah Stanford <noahstandingford@gmail.com>
 *
 *     Open Chaos Chess is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Open Chaos Chess is distributed in the hope that it will be fun,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.corruptedark.openchaoschess;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public final int YOU = -1;
    public final int OPPONENT = 1;
    public final int NONE = 0;
    private final double RATIO_THRESHOLD = 0.2;
    final int SELECT_PLAYERS = 420;
    final int KNIGHT_TIME = 10000;
    volatile boolean buttonsClickable = true;

    RelativeLayout mainLayout;
    ImageView singleplayerButton;
    ImageView multiplayerButton;
    ImageView achievementsButton;
    ImageView settingsButton;
    ImageButton knightButton;
    Handler knightHandler;
    AchievementHandler achievementHandler;
    ColorManager colorManager;
    Random rand = new Random(System.currentTimeMillis());

    Runnable knightChecker = new Runnable() {
        @Override
        public void run() {
            try{
                knightButton.setX(mainLayout.getWidth()*rand.nextFloat());
                knightButton.setY(mainLayout.getHeight()*rand.nextFloat());
            }
            finally {
                knightHandler.postDelayed(knightChecker,KNIGHT_TIME);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        achievementHandler = AchievementHandler.getInstance(this);
        singleplayerButton = (ImageView)findViewById(R.id.singleplayer_button);
        multiplayerButton = (ImageView)findViewById(R.id.multiplayer_button);
        settingsButton = (ImageView) findViewById(R.id.settings_button);
        achievementsButton = (ImageView) findViewById(R.id.achievements_button);
        knightButton = (ImageButton)findViewById(R.id.knight_button);
        mainLayout = (RelativeLayout)findViewById(R.id.activity_main);
        colorManager = ColorManager.getInstance(this);
        knightHandler = new Handler(Looper.getMainLooper());
        startKnight();
    }

    void startKnight(){
        knightChecker.run();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stopKnight();
    }

    void stopKnight(){
        knightHandler.removeCallbacks(knightChecker);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);


        Display display;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            display = getDisplay();
        }
        else {
            display = getWindowManager().getDefaultDisplay();
        }
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;
        int iconWidth;
        int buttonHeight;
        int buttonGap;
        int titleHeight;
        int buttonText;
        int textHeight;
        int horizontalPadding = convertDpToPx(10);

        if (Math.abs(((double) width)/height - 1.0) <= RATIO_THRESHOLD || (double)width/height > 1) { // ratio not long
            double titleWeight = 0.1;
            double iconWeight = 0.4;
            double buttonWeight = 0.2;
            double gapWeight = 0.03;
            double textWeight = 0.05;

            double totalWeight = iconWeight + 6 * buttonWeight + 2 * textWeight + 8 * gapWeight + titleWeight;

            titleHeight = (int)(height * titleWeight / totalWeight);
            iconWidth = (int)(height * iconWeight / totalWeight);
            buttonHeight = (int)(height * buttonWeight / totalWeight);
            buttonText = (int)(height * 0.7 * buttonWeight / totalWeight);
            buttonGap = (int)(height * gapWeight / totalWeight);
            textHeight = (int)(height * textWeight / totalWeight);
        }
        else {
            iconWidth = (int)(width * 0.3);
            buttonHeight = (int)(height * .075);
            titleHeight = buttonHeight;
            buttonGap = (int)(height * .015);
            textHeight = (int)(height * .03);
            buttonText = textHeight;
            horizontalPadding = convertDpToPx(10);
        }

            knightButton.getDrawable().setColorFilter(colorManager.getColorFromFile(ColorManager.PIECE_COLOR), PorterDuff.Mode.MULTIPLY);
            mainLayout.setBackgroundColor(colorManager.getColorFromFile(ColorManager.BACKGROUND_COLOR));

        knightButton.bringToFront();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorManager.getColorFromFile(ColorManager.BAR_COLOR));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Display display;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            display = getDisplay();
        }
        else {
            display = getWindowManager().getDefaultDisplay();
        }
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = (int) (0.9 * size.y);

        int iconWidth;
        int buttonHeight;
        int buttonGap;

        int titleHeight;
        int buttonText;

        int textHeight;
        int horizontalPadding = convertDpToPx(10);

        if (Math.abs(((double) width)/height - 1.0) <= RATIO_THRESHOLD || (double)width/height > 1) { // ratio not long
            double titleWeight = 0.15;
            double iconWeight = 0.3;
            double buttonWeight = 0.1;
            double gapWeight = 0.02;
            double textWeight = 0.05;

            double totalWeight = iconWeight + 6 * buttonWeight + 2 * textWeight + 8 * gapWeight + titleWeight;

            titleHeight = (int)(height * titleWeight / totalWeight);
            iconWidth = (int)(height * iconWeight / totalWeight);
            buttonHeight = (int)(height * buttonWeight / totalWeight);
            buttonText = (int)(height * 0.7 * buttonWeight / totalWeight);
            buttonGap = (int)(height * gapWeight / totalWeight);
            textHeight = (int)(height * textWeight / totalWeight);
        }
        else {
            iconWidth = (int)(width * 0.3);
            buttonHeight = (int)(height * .075);
            titleHeight = buttonHeight;
            buttonGap = (int)(height * .015);
            textHeight = (int)(height * .03);
            buttonText = textHeight;
            horizontalPadding = convertDpToPx(10);
        }

        colorManager = ColorManager.getInstance(this);

        knightButton.getDrawable().setColorFilter(colorManager.getColorFromFile(ColorManager.PIECE_COLOR), PorterDuff.Mode.MULTIPLY);
        mainLayout.setBackgroundColor(colorManager.getColorFromFile(ColorManager.BACKGROUND_COLOR));

        knightButton.bringToFront();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(colorManager.getColorFromFile(ColorManager.BAR_COLOR));
        }
    }
    public void singleplayerButtonClicked(View view){
        if (buttonsClickable) {
            buttonsClickable = false;
            if(SingleGame.getInstance().isKnightsOnly()) {
                SingleGame.getInstance().newGame();
                SingleGame.getInstance().setKnightsOnly(false);
            }
            Intent intent = new Intent(MainActivity.this, SinglePlayerBoard.class);
            startActivity(intent);
        }
    }
    public void multiplayerButtonClicked(View view){
        if (buttonsClickable) {
            buttonsClickable = false;
            startActivity(new Intent(MainActivity.this, PlaySelection.class));
        }
    }
    /*
    public void aboutButtonClicked(View view){
        if (buttonsClickable) {
            buttonsClickable = false;
            achievementHandler.incrementInMemory(AchievementHandler.OPENED_ABOUT);
            achievementHandler.saveValues();

            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        }
    }
     */

    public void issuesButtonClicked(View view)
    {
        if (buttonsClickable) {
            buttonsClickable = false;
            String url = getString(R.string.issues_url);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }


    public void quitButtonClicked(View view){
        finish();
    }

    public void settingsButtonClicked(View view) {
        if (buttonsClickable) {
            buttonsClickable = false;
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        }
    }

    public void achievementsClicked(View view){
        if (buttonsClickable) {
            buttonsClickable = false;
            startActivity(new Intent(MainActivity.this, AchievementsActivity.class));
        }
    }

    public void knightButtonClicked(View view){
        if (buttonsClickable) {
            buttonsClickable = false;
            Intent intent = new Intent(MainActivity.this, SinglePlayerBoard.class);

            if(!SingleGame.getInstance().isKnightsOnly()) {
                SingleGame.getInstance().newGame();
                SingleGame.getInstance().setKnightsOnly(true);
            }

            achievementHandler.incrementInMemory(AchievementHandler.HORSING_AROUND);

            startActivity(intent);
        }
    }

    private int convertDpToPx(int dp){
        return Math.round(dp*(getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT));

    }

    protected void initializeGameData(int request) {
        //TODO

        if (request == SELECT_PLAYERS) {
            MultiGame.getInstance().setTurn(YOU);
        } else {
            MultiGame.getInstance().setTurn(OPPONENT);
        }

    }

    protected void showTurnUI()
    {
        //TODO
        Intent intent = new Intent(MainActivity.this, MultiPlayerBoard.class);
        intent.putExtra("knightsOnly", false);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(final int request, int response, Intent data) {
        super.onActivityResult(request, response, data);
    }

    @Override
    protected void onPostResume() {
        buttonsClickable = true;
        super.onPostResume();
    }
}
