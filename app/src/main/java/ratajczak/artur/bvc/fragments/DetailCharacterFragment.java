package ratajczak.artur.bvc.fragments;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

import ratajczak.artur.bvc.R;
import ratajczak.artur.bvc.utils.DownloadThumbnailTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCharacterFragment extends Fragment implements View.OnClickListener{

    private final String BASEURLPATH = "http://batman.wikia.com";

    public static final String BUNDLE_TITLE_TAG = "title";
    public static final String BUNDLE_ABSTRACT_TAG = "abstract";
    public static final String BUNDLE_ARTICLE_URL = "url";
    public static final String BUNDLE_THUMBNAIL_URL = "thumbnailURL";

    private TextView title;
    private TextView abst;
    private ImageView thumbnail;
    private Button readMoreBtn;
    private Bundle bundle;

    public DetailCharacterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_character, container, false);

        title = (TextView)view.findViewById(R.id.article_title_detail);
        abst = (TextView)view.findViewById(R.id.atricle_abstract_detail);
        thumbnail = (ImageView)view.findViewById(R.id.thumbnail_detail);
        readMoreBtn = (Button)view.findViewById(R.id.ReadMoreButton);
        readMoreBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bundle = getArguments();
        title.setText(bundle.getString(BUNDLE_TITLE_TAG));
        abst.setText(bundle.getString(BUNDLE_ABSTRACT_TAG));
        new DownloadThumbnailTask(thumbnail).execute(bundle.getString(BUNDLE_THUMBNAIL_URL));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASEURLPATH + bundle.getString(BUNDLE_ARTICLE_URL)));
        startActivity(intent);
    }
}
