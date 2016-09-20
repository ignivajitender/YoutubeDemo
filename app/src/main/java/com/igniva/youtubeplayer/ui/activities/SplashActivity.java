package com.igniva.youtubeplayer.ui.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.igniva.youtubeplayer.R;
import com.igniva.youtubeplayer.db.DatabaseHandler;
import com.igniva.youtubeplayer.model.DataGalleryPojo;
import com.igniva.youtubeplayer.model.DataYoutubePojo;
import com.igniva.youtubeplayer.utils.Constants;

import io.fabric.sdk.android.Fabric;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by igniva-php-08 on 18/7/16.
 */
public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    String s;
    DatabaseHandler db;
    List<DataYoutubePojo> mAllData;
    List<DataGalleryPojo> mAllImages;
    public static ArrayList<String> listCategories, listDuration, listNames, listRating, listFavourite;
    AsyncTask<Void, Void, Void> execute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        AsyncTask<Void, Void, Void> execute = new CreateDatabaseAsyncTask();

        execute.execute();




    }

    public class CreateDatabaseAsyncTask
    extends AsyncTask<Void, Void, Void>

    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {



            db=new DatabaseHandler(SplashActivity.this);

            try {
                db.createDataBase();
            } catch (Exception e) {
                e.printStackTrace();
            }

            db.openDataBase();

            mAllData=db.getAllContacts();

            mAllImages=db.getAllImages();

            for (DataGalleryPojo cn : mAllImages) {
                String log = "Image_index_no: " + cn.getImage_no() + " , Image_link: " + cn.getImage_link();

                Log.e("Images_Splash: ", log);
            }

            for (DataYoutubePojo cn : mAllData) {
                String log = "video_no: " + cn.getVideo_no() + " , Video_Title: " + cn.getVideo_title()+" Video_id: "+cn.getVideo_id()+"Video_channel"+cn.getVideo_channel() +
                        " ,Duration: " + cn.getVideo_duration()+" Rating: " + cn.getVideo_rating()+" Thumb: " + cn.getVideo_thumb()+" Playlist: " + cn.getVideo_playlist()+
                        " order: " + cn.getVideo_order()+" Favourite= "+cn.getVideo_favourite();
                // Writing Contacts to log
                Log.e("Name_Splash: ", log);

            }

            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class).putExtra(Constants.ALL_DATA,(Serializable) mAllData).putExtra(Constants.ALL_IMAGES,(Serializable) mAllImages);
            startActivity(mainIntent);
            finish();

        }
    }


}
