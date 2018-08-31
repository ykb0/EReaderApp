package com.consite.e_reader;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class BookReader extends AppCompatActivity {

    Button yogi,gandhi,sachin,magic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        yogi = findViewById(R.id.byogi);
        gandhi = findViewById(R.id.bgandhi);
        sachin = findViewById(R.id.bpitmyway);
        magic = findViewById(R.id.bap);

        setContentView(R.layout.activity_book_reader);
 }

 public void yogi(View view){
        startActivity(new Intent(this,Yogi.class));
 }

    public void gandhi(View view){
        startActivity(new Intent(this,Gandhi.class));
    }

    public void sachin(View view){
        startActivity(new Intent(this,Sachin.class));
    }

    public void magic(View view){
        startActivity(new Intent(this,TheMagic.class));
    }

 public void theMagic(View view){
     AlertDialog.Builder alert = new AlertDialog.Builder(this);
     alert.setMessage(R.string.theMagic);
     alert.setTitle("The Magic");
     alert.show();
 }

    public void theLastLecture(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.theLastLecture);
        alert.setTitle("The Last Lecture");
        alert.show();
    }

    public void presence(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.presence);
        alert.setTitle("Presence: Bringing Your Boldest Self to Your Biggest Challenges");
        alert.show();
    }

    public void beyondGoodAndEvil(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.beyondGoodAndEvil);
        alert.setTitle("Beyond Good and Evil");
        alert.show();
    }

    public void spiritWorld(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(R.string.spiritWorld);
        alert.setTitle("The Laws of the Spirit World");
        alert.show();
    }

}
