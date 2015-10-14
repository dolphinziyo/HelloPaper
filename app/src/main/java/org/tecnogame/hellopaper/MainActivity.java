package org.tecnogame.hellopaper;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.tecnogame.hellopaper.model.Album;
import org.tecnogame.hellopaper.model.Artist;
import org.tecnogame.hellopaper.model.Genre;
import org.tecnogame.hellopaper.model.Song;
import org.tecnogame.hellopaper.util.CropCircleTransformation;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String KEY_LIST = "objects_list";

    private ArrayList<Song> songsList;
    private Button btnCreateList;
    private Button btnGetList;
    private Button btnRemoveList;
    private TextView tvError;
    private LinearLayout llListaCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        llListaCanciones = (LinearLayout)findViewById(R.id.ll_datos_canciones);
        tvError = (TextView)findViewById(R.id.tv_error);
        btnCreateList = (Button)findViewById(R.id.btn_create_songs_list);
        btnGetList = (Button)findViewById(R.id.btn_get_songs_list);
        btnRemoveList = (Button)findViewById(R.id.btn_remove_songs_list);
        btnCreateList.setOnClickListener(this);
        btnGetList.setOnClickListener(this);
        btnRemoveList.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == btnCreateList){
            fillList();
            drawSongs();
        }else if(v == btnGetList){
            if(songsList != null && songsList.size() > 0){
                drawSongs();
            }else{
                tvError.setText("Empty songs list");
            }
        }else if(v == btnRemoveList){
            clearObjects();
            if(songsList != null){
                songsList.clear();
            }
            clearSongs();
        }
    }

    private void clearSongs(){
        for(int i = 0; i< llListaCanciones.getChildCount(); i++){
            llListaCanciones.removeViewAt(i);
        }
    }

    private void drawSongs(){
        clearSongs();
        tvError.setText("");
        for(Song song:songsList){
            LinearLayout llSongLayout = new LinearLayout(this);
            llSongLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            llSongLayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            iv.getLayoutParams().width = 150;
            iv.getLayoutParams().height = 150;
            Glide.with(this).load(song.getAlbum().getCover()).bitmapTransform(new CropCircleTransformation(this)).into(iv);
            TextView tvSong = new TextView(this);
            tvSong.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            ((LinearLayout.LayoutParams)tvSong.getLayoutParams()).setMargins(10, 0, 0, 0);
            tvSong.setText(song.getAlbum().getArtist().getName() + "\n" + song.getName() + " (" + song.getAlbum().getName() + ")\nLength: " + song.getLength() + " secs");
            llSongLayout.addView(iv);
            llSongLayout.addView(tvSong);
            llListaCanciones.addView(llSongLayout);
            View separator = new View(this);
            separator.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
            ((LinearLayout.LayoutParams)separator.getLayoutParams()).setMargins(10, 4, 10, 4);
            separator.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_light));
            llListaCanciones.addView(separator);
        }
    }

    private void fillList(){
        songsList = new ArrayList<>();
        Artist deLaSoul = new Artist();
        deLaSoul.setName("De La Soul");
        deLaSoul.setPhoto("https://upload.wikimedia.org/wikipedia/commons/6/66/De_La_Soul_Demon_Days_Live_crop.jpg");
        Album deLaSoulAlbum = new Album();
        deLaSoulAlbum.setName("3 Feet High and Rising");
        deLaSoulAlbum.setCover(R.drawable.delasoul);
        deLaSoulAlbum.setReleaseYear(1989);
        deLaSoulAlbum.setArtist(deLaSoul);
        Genre rap = new Genre();
        rap.setName("Rap");
        Song meMyselfAndI = new Song();
        meMyselfAndI.setName("Me myself and I");
        meMyselfAndI.setLength(218);
        meMyselfAndI.setAlbum(deLaSoulAlbum);
        meMyselfAndI.setGenre(rap);

        Artist gorillaz = new Artist();
        gorillaz.setName("Gorillaz");
        gorillaz.setPhoto("https://lh6.googleusercontent.com/-UXcxdSDLo08/AAAAAAAAAAI/AAAAAAAAAAA/FP5NbxM7TzU/s0-c-k-no-ns/photo.jpg");
        Album gorillazAlbum = new Album();
        gorillazAlbum.setName("G Sides");
        gorillazAlbum.setCover(R.drawable.gorillaz);
        gorillazAlbum.setReleaseYear(2001);
        gorillazAlbum.setArtist(gorillaz);
        Genre altRock = new Genre();
        altRock.setName("Alternative Rock");
        Song gorillazSong = new Song();
        gorillazSong.setName("19-2000");
        gorillazSong.setLength(208);
        gorillazSong.setAlbum(gorillazAlbum);
        gorillazSong.setGenre(altRock);

        songsList.add(meMyselfAndI);
        songsList.add(gorillazSong);
    }

    private void saveObjects(){
        Paper.book().write(KEY_LIST, songsList);
    }

    private void restoreObjects(){
        songsList = Paper.book().read(KEY_LIST, null);
    }

    private void clearObjects(){
        Paper.book().delete(KEY_LIST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveObjects();
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreObjects();
    }
}
