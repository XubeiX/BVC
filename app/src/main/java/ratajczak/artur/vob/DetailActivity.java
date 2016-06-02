package ratajczak.artur.vob;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ratajczak.artur.bvc.R;
import ratajczak.artur.vob.fragments.DetailCharacterFragment;
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailCharacterFragment fragment = new DetailCharacterFragment();
        fragment.setArguments(getIntent().getExtras());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.DetailsFragment, fragment);
        transaction.commit();
    }
}
