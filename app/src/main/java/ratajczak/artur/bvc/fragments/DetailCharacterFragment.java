package ratajczak.artur.bvc.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratajczak.artur.bvc.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCharacterFragment extends Fragment {

    public static final String BUNDLE_TITLE_TAG = "title";
    public static final String BUNDLE_ABSTRACT_TAG = "abstract";

    private TextView title;
    private TextView abst;

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
        bundle = getActivity().getIntent().getExtras();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        title.setText(bundle.getString(BUNDLE_TITLE_TAG));
        abst.setText(bundle.getString(BUNDLE_ABSTRACT_TAG));
    }
}
