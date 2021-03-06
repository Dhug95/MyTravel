package com.example.francesco.mytravel.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.francesco.mytravel.tasks.GetProfileInfo;
import com.example.francesco.mytravel.activities.MainActivity;
import com.example.francesco.mytravel.R;
import com.example.francesco.mytravel.activities.UpdateUserActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private String token;

    private Intent intent;

    private TextView mUsernameInfo;
    private TextView mEmailInfo;
    private FloatingActionButton fab;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        token = getArguments().getString(TOKEN);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mUsernameInfo = (TextView) v.findViewById(R.id.username_text);
        mEmailInfo = (TextView) v.findViewById(R.id.email_text);

        Button logout = (Button) v.findViewById(R.id.logout_button);
        fab = (FloatingActionButton) v.findViewById(R.id.fab_profile);
        logout.setOnClickListener(this);
        fab.setOnClickListener(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        new GetProfileInfo(getContext(), mUsernameInfo, mEmailInfo, fab).execute(token);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                break;

            case R.id.fab_profile:
                Intent intent = new Intent(getContext(), UpdateUserActivity.class);
                intent.putExtra(TOKEN, token);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

}
