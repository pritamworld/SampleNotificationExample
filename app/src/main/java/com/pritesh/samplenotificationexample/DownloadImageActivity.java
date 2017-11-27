package com.pritesh.samplenotificationexample;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class DownloadImageActivity extends AppCompatActivity
{

    // Set your Image URL into a string
    String url = "http://api.androidhive.info/images/sample.jpg";
    ImageView image;
    Button button;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);

        // Locate the ImageView in activity_main.xml
        image = (ImageView) findViewById(R.id.image);

        // Locate the Button in activity_main.xml
        button = (Button) findViewById(R.id.button);

        // Capture button click
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {

                // Execute DownloadImage AsyncTask
                new DownloadImage().execute(url);
            }
        });
    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DownloadImageActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image Tutorial");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... urls)
        {

            String imageURL = urls[0];

            Bitmap bitmap = null;
            try
            {
                return getBitmap(imageURL);
            } catch (Exception e)
            {
                Log.d("ERROR",e.toString());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result)
        {
            // Set the bitmap into ImageView
            if(result==null)
                image.setImageResource(R.drawable.tfs_large_notify_icon);
            else
                image.setImageBitmap(result);
            // Close progress dialog
            mProgressDialog.dismiss();
        }
    }

    private Bitmap getBitmap(String url)
    {

        //from web
        try
        {
            Bitmap bitmap;
            java.net.URL imageUrl = new java.net.URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception ex)
        {
            //ex.printStackTrace();
            return null;
        }
    }
}
