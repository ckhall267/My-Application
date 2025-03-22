package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import adapter.StarAdapter;
import beans.Star;
import service.StarService;

public class ListActivity extends AppCompatActivity {

    private static final String TAG = "ListActivity";
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list); // ✅ Correction ici

        // Initialisation des données
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();

        // Configuration du RecyclerView
        recyclerView = findViewById(R.id.recycle_view);
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialisation de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void init() {
        service.create(new Star("Kate Bosworth", "https://cdn.britannica.com/20/218820-050-9E834C60/Kate-Bosworth-American-actress.jpg", 3.5f));
        service.create(new Star("George Clooney", "https://upload.wikimedia.org/wikipedia/commons/7/71/George_Clooney-69990.jpg", 3));
        service.create(new Star("Michelle Rodriguez", "https://upload.wikimedia.org/wikipedia/commons/f/fd/Michelle_Rodriguez_Cannes_2018_cropped.jpg", 5));
        service.create(new Star("Angelina Jolie", "https://th.bing.com/th/id/R.1e95322003df43be1cb1f9a10e9146e5?rik=XW14EfMnxFbD3g&riu=http%3a%2f%2fwww.hawtcelebs.com%2fwp-content%2fuploads%2f2012%2f01%2fAngelina-Jolie-at-18th-Annual-Screen-Actors-Guild-Awards-in-Los-Angeles-30.jpg&ehk=Nct4gBaiL%2bL7mZAYTf3QnH%2f%2fxmhrvT0ykFAg6m7HDzk%3d&risl=&pid=ImgRaw&r=0", 1));
        service.create(new Star("Louise Bourgoin", "https://celebmafia.com/wp-content/uploads/2019/01/louise-bourgoin-2019-cesar-revelations-bash-more-pics-9.jpg", 5));
        service.create(new Star("Emy Coligado", "https://th.bing.com/th/id/OIP.FV5wye_FxzCkeGkx5ObmRgHaLG?rs=1&pid=ImgDetMain", 1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // Récupération de l'élément SearchView
        MenuItem menuItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        // Écoute des événements du SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Recherche soumise : " + query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "Texte changé : " + newText);

                // 🔥 Filtrage de la liste en fonction du texte saisi
                if (starAdapter != null) {
                    starAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            String txt = "Liste des Stars 📸✨";
            String mimeType = "text/plain";

            ShareCompat.IntentBuilder
                    .from(this)
                    .setType(mimeType)
                    .setChooserTitle("Partager via")
                    .setText(txt)
                    .startChooser();

            return true; // ✅ Indique que l'événement a été géré
        }
        return super.onOptionsItemSelected(item);
    }
}
