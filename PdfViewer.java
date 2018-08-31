package com.consite.e_reader;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class PdfViewer extends AppCompatActivity {

    private ImageView img;
    private Button next;
    private Button previous;
    private int pgCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        previous = (Button) findViewById(R.id.pre);
        next = (Button) findViewById(R.id.next);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void next(View view){
        pgCount++;
        render();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void previous(View view){
        pgCount--;
        render();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void render(){
        try{
            img = (ImageView)findViewById(R.id.image);
            int width = img.getWidth();
            int height = img.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_4444);
            File file = new File("/Phone storage/Download/Yogi.pdf");
            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file,ParcelFileDescriptor.MODE_READ_ONLY));

            if(pgCount<0){
                pgCount=0;
            }else if(pgCount>renderer.getPageCount()){
                pgCount = renderer.getPageCount()-1;
            }

            Matrix m = img.getImageMatrix();
            Rect rect = new Rect(0,0,width,height);
            renderer.openPage(pgCount).render(bitmap,rect,m, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            img.setImageMatrix(m);
            img.setImageBitmap(bitmap);
            img.invalidate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
