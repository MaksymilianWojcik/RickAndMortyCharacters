package pl.com.bubka.rickandmortycharacters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class BaseActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        progressBar = constraintLayout.findViewById(R.id.progressBar);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(layoutResID);
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

}
