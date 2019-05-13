package pl.com.bubka.rickandmortycharacters.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import pl.com.bubka.rickandmortycharacters.R;

import android.os.Bundle;

public class CharactersListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_list);

        recyclerView = findViewById(R.id.characters_list);
        searchView = findViewById(R.id.search_view);
    }
}
