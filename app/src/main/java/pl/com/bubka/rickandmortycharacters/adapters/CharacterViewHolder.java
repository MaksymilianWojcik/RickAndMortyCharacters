package pl.com.bubka.rickandmortycharacters.adapters;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.models.Character;

public class CharacterViewHolder extends RecyclerView.ViewHolder {

    RequestManager requestManager;
    TextView name, status, species;
    AppCompatImageView imageView;

    //TODO: Caching glide - preloaders

    public CharacterViewHolder(@NonNull View itemView, RequestManager requestManager) {
        super(itemView);
        this.requestManager = requestManager;

        name = itemView.findViewById(R.id.character_name);
        status = itemView.findViewById(R.id.character_status);
        species = itemView.findViewById(R.id.character_species);
        imageView = itemView.findViewById(R.id.character_image);
    }

    public void onBind(Character character) {
        requestManager.load(character.getImageUrl())
                .into(imageView);
        name.setText(character.getName());
        status.setText(character.getStatus());
        species.setText(character.getSpecies());
    }

}
