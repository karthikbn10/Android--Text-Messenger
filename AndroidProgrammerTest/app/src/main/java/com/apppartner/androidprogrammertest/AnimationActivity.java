package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import static android.R.attr.angle;
import static android.R.attr.pivotX;
import static android.R.attr.pivotY;
import static android.R.attr.reversible;


public class AnimationActivity extends ActionBarActivity implements Animation.AnimationListener
{
    ImageButton btn;
    ImageView imageView;
    Animation animation;
    Animation anim;
    Animation ani;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        android.support.v7.app.ActionBar actionBar= getSupportActionBar();
        actionBar.setTitle("Animation");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        btn = (ImageButton) findViewById(R.id.fadebutton);
        imageView = (ImageView) findViewById(R.id.appartner_icon);
        animation = AnimationUtils.loadAnimation(this,R.anim.fade_in);
        animation.setAnimationListener(this);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //imageView.setVisibility(View.INVISIBLE);
                imageView.startAnimation(animation);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public void rotateClick(View view)
    {
        anim = AnimationUtils.loadAnimation(this,R.anim.rotate_image);
        anim.setRepeatCount(1);
        anim.setRepeatMode(reversible);
        anim.setStartOffset(0);
        imageView = (ImageView) findViewById(R.id.appartner_icon);
        imageView.startAnimation(anim);


    }

    public void translateClick(View view){
        ani = AnimationUtils.loadAnimation(this,R.anim.translate_image);
        ani.setDuration(2000);
        imageView = (ImageView) findViewById(R.id.appartner_icon);
        imageView.startAnimation(ani);

    }

}
