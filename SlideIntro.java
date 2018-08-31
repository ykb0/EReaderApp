package com.consite.e_reader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SlideIntro extends AppCompatActivity {

    private  PrefManager prefManager;
    private int[] slideLayouts;
    private LinearLayout dotsLayout;
    private Button next;
    private ViewPager viewPager;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check for the first Time - before calling setContentView()

        prefManager = new PrefManager(this);
        if(!prefManager.isFirstTimelaunch()){
            launchIntroSlides();
            finish();
        }

        //Make notification bar TRANSPARENT

        if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        setContentView(R.layout.activity_slide_intro);

        mAuth = FirebaseAuth.getInstance();

        viewPager = findViewById(R.id.viewPager);
        dotsLayout = findViewById(R.id.dots);
   //     Button skip = findViewById(R.id.skip);
        next = findViewById(R.id.next);

        //Layouts of all slides

        slideLayouts = new int[]{
                R.layout.slide1,
                R.layout.slide2,
                R.layout.slide3
        };

        //add bottom dots

        addBottomDots(0);

        //make notification bar transparent

        changeStatusBarColor();

        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

//        skip.setOnClickListener(new View.OnClickListener(){
//          @Override
//            public void onClick(View v){
//              launchIntroSlides();
//          }
//        });

        next.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View view){

              //Check for last page because if it is last then home screen will be launched

              int current = getItem(+1);
              if(current < slideLayouts.length){

                  //Move to Next Slide

                  viewPager.setCurrentItem(current);
              }
              else{
                  launchIntroSlides();
              }
          }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    // Function launchIntroSlides()

    public void launchIntroSlides(){
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(SlideIntro.this,BookReader.class));
        finish();
    }

    // Function addBottomDots

    private TextView[] dots;
    public void addBottomDots(int currentPage){
        dots = new TextView[slideLayouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.activeDots);
        int[] colorsInactive = getResources().getIntArray(R.array.inactiveDots);

        dotsLayout.removeAllViews();
        for(int i=0;i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsActive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if(dots.length > 0){
            dots[currentPage].setTextColor(colorsInactive[currentPage]);
        }
    }

    //Function changeStatusBarColor

    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    // Class MyViewPagerAdapter

    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter(){}

        @Override
        public Object instantiateItem(ViewGroup container, int position){
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(slideLayouts[position],container,false);
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public int getCount(){
            return slideLayouts.length;
        }

        @Override
        public void destroyItem(ViewGroup container,int position, Object object){
            View view = (View) object;
            container.removeView(view);
        }
    }

    // ViewPager PageChangeListener

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // Changing the Next Button to Done Button
            if(position == slideLayouts.length-1){
                next.setText(getString(R.string.done));
              //  next.setVisibility(View.GONE);
            }
            else{
                next.setText(getString(R.string.next));
              //  next.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    // Function getItem()

    public int getItem(int i){
        return viewPager.getCurrentItem()+i;
    }

}
