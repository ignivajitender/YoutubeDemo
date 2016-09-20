package com.igniva.youtubeplayer.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.igniva.youtubeplayer.model.DataGalleryPojo;
import com.igniva.youtubeplayer.ui.activities.MainActivity;
import com.igniva.youtubeplayer.ui.adapters.CategoryListAdapter;
import com.igniva.youtubeplayer.R;
import com.igniva.youtubeplayer.model.DataYoutubePojo;
import com.igniva.youtubeplayer.db.DatabaseHandler;
import com.igniva.youtubeplayer.ui.adapters.CategoryListAdapterChannels;
import com.igniva.youtubeplayer.ui.adapters.CategoryListAdapterGallery;
import com.igniva.youtubeplayer.utils.UtilsUI;
import com.igniva.youtubeplayer.ui.activities.All_Image_View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by igniva-php-08 on 18/5/16.
 */
public class CategoriesFragment extends BaseFragment{
    View mView;
    public static RecyclerView mRvCategories;
    private Menu menu;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    ArrayList<String> small_images_url,medium_images_url,large_images_url ;
    List<DataYoutubePojo> mAllData;
    public static  ArrayList<String> channels_name,channel_thumb,listCategories,listDuration,listNames,listRating,listFavourite;
MainActivity main=new MainActivity();
    List<DataGalleryPojo> mAllImages=main.getMyImages();
    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_category, container, false);
        setHasOptionsMenu(true);

        MainActivity activity = (MainActivity) getActivity();
        mAllData=activity.getMyData();

        for (DataYoutubePojo cn : mAllData) {
            String log = "video_Id: " + cn.getVideo_no() + " , Video_Title: " + cn.getVideo_title()+" Video_id"+cn.getVideo_id()+"Video_channel"+cn.getVideo_channel() +
                    " ,Duration: " + cn.getVideo_duration()+" Rating: " + cn.getVideo_rating()+" Thumb: " + cn.getVideo_thumb()+" Playlist: " + cn.getVideo_playlist()+
                    " order: " + cn.getVideo_order()+" Favourite= "+cn.getVideo_favourite();

//            listCategories.add(cn.getVideo_id().toString());
//            listNames.add(cn.getVideo_title().toString());
//            listDuration.add(cn.getVideo_duration().toString());
//            listRating.add(cn.getVideo_rating().toString());
//            listFavourite.add(cn.getVideo_favourite());


            // Writing Contacts to log
//            Log.e("Name2: ", log);

        }


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();

        channels_name =new ArrayList<>();
                channel_thumb=new ArrayList<>();

        small_images_url=new ArrayList<>();
        medium_images_url=new ArrayList<>();
        large_images_url=new ArrayList<>();

                 listCategories = new ArrayList<String>();
        listDuration = new ArrayList<String>();
         listNames = new ArrayList<String>();
        listRating = new ArrayList<String>();
        listFavourite= new ArrayList<String>();

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
//        List<DataYoutubePojo> contacts = db.getAllContacts();

        for (DataYoutubePojo cn : mAllData) {
            String log = "video_Id: " + cn.getVideo_no() + " , Video_Title: " + cn.getVideo_title()+" Video_id"+cn.getVideo_id()+"Video_channel"+cn.getVideo_channel() +
                    " ,Duration: " + cn.getVideo_duration()+" Rating: " + cn.getVideo_rating()+" Thumb: " + cn.getVideo_thumb()+" Playlist: " + cn.getVideo_playlist()+
                    " order: " + cn.getVideo_order()+" Favourite= "+cn.getVideo_favourite();

            listCategories.add(cn.getVideo_id().toString());
            listNames.add(cn.getVideo_title().toString());
            listDuration.add(cn.getVideo_duration().toString());
            listRating.add(""+cn.getVideo_rating());
            listFavourite.add(cn.getVideo_favourite());


            // Writing Contacts to log
//            Log.e("Name: ", log);

        }

        for (DataGalleryPojo cn : mAllImages) {


            large_images_url.add(cn.getImage_link().toString());


        }
        setUpLayouts();
        return mView;
    }

    @Override
    public void setUpLayouts() {

        mRvCategories = (RecyclerView) mView.findViewById(R.id.rv_categories);
//        getCategoriesData(2);
        try {
            CategoriesFragment.mRvCategories.setVisibility(View.VISIBLE);
            mRvCategories.setAdapter(new CategoryListAdapter(getActivity(), listCategories,listNames,listDuration,listRating,listFavourite));
            mRvCategories.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
            mRvCategories.setLayoutManager(mLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setDataInViewLayouts() {
    }

    private void getCategoriesDataOfGalary(int pos) {
        try {




            CategoriesFragment.mRvCategories.setVisibility(View.VISIBLE);

            mRvCategories.setAdapter(new CategoryListAdapterGallery(getActivity(), large_images_url,pos));
            mRvCategories.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), pos);
            mRvCategories.setLayoutManager(mLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCategoriesData(int pos) {
        try {
            CategoriesFragment.mRvCategories.setVisibility(View.VISIBLE);
//            mRvCategories.setAdapter(new CategoryListAdapter(getActivity(), listCategories,listNames,listDuration,listRating,listFavourite));
            mRvCategories.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), pos);
            mRvCategories.setLayoutManager(mLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCategoriesDataOfChannels(int pos) {
        try {

            channel_thumb.clear();
            channels_name.clear();

            CategoriesFragment.mRvCategories.setVisibility(View.VISIBLE);
            channels_name.add("Bollywood");
            channel_thumb.add("http://img.youtube.com/vi/aWMTj-rejvc/hqdefault.jpg");
            channels_name.add("English");
            channel_thumb.add("http://img.youtube.com/vi/iS1g8G_njx8/hqdefault.jpg");
            channels_name.add("Punjabi");
            channel_thumb.add("http://img.youtube.com/vi/ojAIYTXU7ZI/hqdefault.jpg");
            channels_name.add("Coke Studio");
            channel_thumb.add("http://img.youtube.com/vi/7w8AR7jnhpc/hqdefault.jpg");



            CategoriesFragment.mRvCategories.setAdapter(new CategoryListAdapterChannels(getActivity(), channels_name,channel_thumb));
//
            MainActivity.toolbar.setTitle("Channels");
            mRvCategories.setHasFixedSize(true);
            GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), pos);
            mRvCategories.setLayoutManager(mLayoutManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        this.menu = menu;

        if(sharedPreferences.getInt("cat",2)==2)
        {
            menu.getItem(0).setIcon(getResources().getDrawable(R.mipmap.list));
            getCategoriesData(2);
        }
        else
        {
            menu.getItem(0).setIcon(getResources().getDrawable(R.mipmap.grid));
            getCategoriesData(1);
        }


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_settings:

                CategoriesFragment.mRvCategories.setVisibility(View.VISIBLE);

                if(menu.getItem(0).getTitle().equals(getResources().getString(R.string.grid))) {
                    menu.getItem(0).setIcon(getResources().getDrawable(R.mipmap.list));
                    menu.getItem(0).setTitle(getResources().getString(R.string.list));

                    if(UtilsUI.galery_status) {

                        getCategoriesDataOfGalary(2);
                    }
                    else
                    if(UtilsUI.channels_status) {

                        getCategoriesDataOfChannels(2);
                    }
                    else
                    {
                        getCategoriesData(2);
                    }
                    int cat=2;
                    editor.putInt("cat",cat);
                    editor.commit();
                }else{
                    menu.getItem(0).setIcon(getResources().getDrawable(R.mipmap.grid));
                    menu.getItem(0).setTitle(getResources().getString(R.string.grid));
                    if(UtilsUI.galery_status) {
                        getCategoriesDataOfGalary(1);
                    }
                    else
                    if(UtilsUI.channels_status) {
                        getCategoriesDataOfChannels(1);
                    }
                    else
                    {
                        getCategoriesData(1);
                    }
                    int cat=1;
                    editor.putInt("cat",cat);
                    editor.commit();
                }
                break;

        }
        return false;


    }

}