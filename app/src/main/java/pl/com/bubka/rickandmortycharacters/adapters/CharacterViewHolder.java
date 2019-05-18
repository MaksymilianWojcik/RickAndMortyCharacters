package pl.com.bubka.rickandmortycharacters.adapters;

import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.models.Character;

public class CharacterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.character_name_rd) TextView name;
    @BindView(R.id.character_status_rd) TextView status;
    @BindView(R.id.character_gender_rd) TextView gender;
    @BindView(R.id.character_image_rd) AppCompatImageView imageView;
    @BindView(R.id.character_status_image_rd) AppCompatImageView statusImageView;
    @BindView(R.id.character_gender_image_rd) AppCompatImageView genderImageView;

    RequestManager requestManager;
    OnCharacterClickListener onCharacterClickListener;

    //TODO: Caching glide - preloaders

    public CharacterViewHolder(@NonNull View itemView, RequestManager requestManager, OnCharacterClickListener onCharacterClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.requestManager = requestManager;
        this.onCharacterClickListener = onCharacterClickListener;

        itemView.setOnClickListener(this);
    }

    public void onBind(Character character) {
        requestManager.load(character.getImageUrl())
                .into(imageView);
        name.setText(character.getName());
        status.setText(character.getStatus());
        gender.setText(character.getGender());
        if(character.getStatus().equalsIgnoreCase("alive")){
            statusImageView.setImageResource(R.drawable.ic_alive);
        } else {
            statusImageView.setImageResource(R.drawable.ic_crossbone);
        }
        if(character.getGender().equalsIgnoreCase("male")){
            genderImageView.setImageResource(R.drawable.ic_male);
        } else if (character.getGender().equalsIgnoreCase("female")){
            genderImageView.setImageResource(R.drawable.ic_female);
        } else { //Unknown or empty
            genderImageView.setImageResource(R.drawable.ic_unknown);
        }
    }

    @Override
    public void onClick(View view) {
        onCharacterClickListener.onCharacterSelected(getAdapterPosition());
    }

}
