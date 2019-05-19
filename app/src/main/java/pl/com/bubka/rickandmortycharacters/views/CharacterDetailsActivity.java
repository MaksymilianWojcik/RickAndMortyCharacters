package pl.com.bubka.rickandmortycharacters.views;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.bubka.rickandmortycharacters.R;
import pl.com.bubka.rickandmortycharacters.models.Character;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class CharacterDetailsActivity extends AppCompatActivity {

    @BindView(R.id.character_detail_image) ImageView characterImageView;
    @BindView(R.id.character_details_gender) TextView vGender;
    @BindView(R.id.character_details_species) TextView vSpecies;
    @BindView(R.id.character_details_status) TextView vStatus;
    @BindView(R.id.character_details_origin) TextView vOrigin;
    @BindView(R.id.character_details_last_location) TextView vLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_details);
        ButterKnife.bind(this);

        Character character = getIntent().getExtras().getParcelable("CharacterExtra");

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background); //TODO: icons
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(character.getImageUrl())
                .into(characterImageView);

        vGender.setText(character.getGender());
        vSpecies.setText(character.getSpecies());
        vStatus.setText(character.getStatus());
        vOrigin.setText("UNKNOWN");
        vLocation.setText("CITADEL OF RICKS");
    }
}