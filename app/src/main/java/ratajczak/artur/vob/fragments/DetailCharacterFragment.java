package ratajczak.artur.vob.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ratajczak.artur.bvc.R;

/**
 * Created by Artur Ratajczak on 23.05.16.
 */
public class DetailCharacterFragment extends Fragment implements View.OnClickListener{

    private final String BASEURLPATH = "http://batman.wikia.com";

    public static final String BUNDLE_TITLE_TAG = "title";
    public static final String BUNDLE_ABSTRACT_TAG = "abstract";
    public static final String BUNDLE_ARTICLE_URL = "url";
    public static final String BUNDLE_THUMBNAIL_URL = "thumbnailURL";
    public static final String BUNDLE_LIKED_TAG = "liked";

    private TextView title;
    private TextView abst;
    private ImageView thumbnail;
    private Button readMoreBtn;
    private Bundle bundle;
    private CheckBox liked;

    public DetailCharacterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_character, container, false);
        title = (TextView)view.findViewById(R.id.article_title_detail);
        abst = (TextView)view.findViewById(R.id.article_abstract_detail);
        thumbnail = (ImageView)view.findViewById(R.id.thumbnail_detail);
        readMoreBtn = (Button)view.findViewById(R.id.ReadMoreButton);
        readMoreBtn.setOnClickListener(this);
        liked = (CheckBox)view.findViewById(R.id.liked_detail);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bundle = getArguments();
        if(bundle!=null) {
            try{
                title.setText(bundle.getString(BUNDLE_TITLE_TAG));
                abst.setText(bundle.getString(BUNDLE_ABSTRACT_TAG));
                liked.setChecked(bundle.getBoolean(BUNDLE_LIKED_TAG));
                if(bundle.getString(BUNDLE_THUMBNAIL_URL) != null){
                    Picasso.with(view.getContext()).load(bundle.getString(BUNDLE_THUMBNAIL_URL)).error(R.drawable.batman).into(thumbnail);
                }else {
                    Picasso.with(view.getContext()).load(R.drawable.batman).into(thumbnail);
                }
                if(bundle.getString(BUNDLE_ARTICLE_URL) != null){
                    readMoreBtn.setVisibility(View.VISIBLE);
                }else {
                    readMoreBtn.setVisibility(View.INVISIBLE);
                }
            }catch (NullPointerException e){
                Log.e("DetailFragment",e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(BASEURLPATH + bundle.getString(BUNDLE_ARTICLE_URL)));
        startActivity(intent);
    }
}
