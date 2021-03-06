package com.example.shoespricecomparision;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import url.Url;

public class ShoesDescriptionActivity extends AppCompatActivity {

    int onStartCount = 0;
    private Toolbar toolbar;
    private TextView tvNameDes,tvReviewDescription, tvShoesBrandDescription, tvShoesNameDescription, tvShoesPriceDescription;

    private int shoesId;
    private ImageView imgDisplayHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_description);

//        setting slide animation for this page
        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
            onStartCount++;
        } else // already created so reverse animation
        {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

            onStartCount = 2;
        }



        imgDisplayHeader = findViewById(R.id.header);
        tvNameDes = findViewById(R.id.tvNameDescription);
        tvReviewDescription = findViewById(R.id.tvReviewDescription);

        tvShoesBrandDescription = findViewById(R.id.tvShoesBrandDescription);
        tvShoesNameDescription = findViewById(R.id.tvShoesNameDescription);
        tvShoesPriceDescription  = findViewById(R.id.tvShoesPriceDescription);



//      setting toolbar for this activity
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      setting back navigation button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);


//        setting clickListener on tvReviewDescription

        tvReviewDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                calling startActivity to set animation style
                Intent intent = new Intent(ShoesDescriptionActivity.this,ReviewActivity.class);
                Toast.makeText(ShoesDescriptionActivity.this, "before going shoesId :" + shoesId, Toast.LENGTH_SHORT).show();
                Log.d("tag" , "new value here we have" + shoesId);
                intent.putExtra("shoeId : ", shoesId);
                startActivity(intent);
                finish();
            }
        });


//       setting collapsing toolbar
        collapsingToolbar();

//        setting splash animation in textView
        settingAnimation();


//      load shoes
        strictMode();
        URL url = null;
        Bundle bundle = getIntent().getExtras();
        shoesId = bundle.getInt("shoeId");
        Url.shoesId = bundle.getInt("shoeId");

        Toast.makeText(this, "shoesId" + shoesId, Toast.LENGTH_SHORT).show();
        if (bundle != null) {
            imgDisplayHeader.setImageResource(bundle.getInt("image"));

            try {
                url = new URL("http://10.0.2.2:8005/uploads/" + bundle.getString("image"));
                imgDisplayHeader.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvShoesNameDescription.setText(bundle.getString("shoesName"));
            tvShoesBrandDescription.setText(bundle.getString("shoesBrand"));
            tvShoesPriceDescription.setText(String.valueOf(bundle.getString("shoesPrice")));
        }
    }

    private void settingAnimation() {
        int[] attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = this    .obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getResourceId(0, 0);
        tvNameDes.setBackgroundResource(backgroundResource);
    }

    private void collapsingToolbar() {

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }if (scrollRange + i == 0) {
//                    collapsingToolbarLayout.setTitle("title");
//                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }


    private void strictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    //     onStart() is called when activity is visible to user
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);

        } else if (onStartCount == 1) {
            onStartCount++;
        }

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(ShoesDescriptionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        return  true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shoes_description,menu);
        return true;
    }


//      this is slide animation for this activity which loads when page gets started

//    public void setAnimation() {
//        if (Build.VERSION.SDK_INT > 20) {
//            Slide slide = new Slide();
//            slide.setSlideEdge(Gravity.LEFT);
//            slide.setDuration(1000);
//            slide.setInterpolator(new FastOutSlowInInterpolator());
////            slide.setStartDelay(100);
////            slide.setInterpolator(new AccelerateDecelerateInterpolator());
//            getWindow().setExitTransition(slide);
//            getWindow().setEnterTransition(slide);
//        }
//    }

//    this is used to set animation i.e. for review activity page

//    public void startActivity(){
//        Intent i = new Intent(ShoesDescriptionActivity.this, ReviewActivity.class);
//        if(Build.VERSION.SDK_INT>20){
//            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(ShoesDescriptionActivity.this);
//            startActivity(i,options.toBundle());
//        }
//        else {
//            startActivity(i);
//        }
//    }


}
