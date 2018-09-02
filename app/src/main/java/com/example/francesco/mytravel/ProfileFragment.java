package com.example.francesco.mytravel;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String token = getArguments().getString(TOKEN);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView mUsernameInfo = (TextView) v.findViewById(R.id.username_text);
        TextView mEmailInfo = (TextView) v.findViewById(R.id.email_text);

        new GetProfileInfo(getContext(), mUsernameInfo, mEmailInfo).execute(token);

        return v;
    }

}
