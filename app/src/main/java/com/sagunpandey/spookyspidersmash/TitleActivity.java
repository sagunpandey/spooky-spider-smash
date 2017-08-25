package com.sagunpandey.spookyspidersmash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class TitleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create full screen window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_title);

        ImageView imageView = (ImageView) findViewById(R.id.titleScreen);
        Picasso
                .with(this)
                .load(R.drawable.titlescreen)
                .fit()
                .into(imageView);

        Button playButton = (Button) findViewById(R.id.buttonPlay);
        Button highScoreButton = (Button) findViewById(R.id.buttonHighScore);
        playButton.setOnClickListener(this);
        highScoreButton.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to quit the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TitleActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch(viewId) {
            case R.id.buttonPlay:
                Intent intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonHighScore:
                Intent intent1 = new Intent(this, SettingsActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
