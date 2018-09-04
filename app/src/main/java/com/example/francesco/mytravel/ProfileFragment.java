package com.example.francesco.mytravel;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TOKEN =
            "com.example.francesco.mytravel.extra.TOKEN";

    private String token;

    private Intent intent;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        token = getArguments().getString(TOKEN);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView mUsernameInfo = (TextView) v.findViewById(R.id.username_text);
        TextView mEmailInfo = (TextView) v.findViewById(R.id.email_text);

        Button logout = (Button) v.findViewById(R.id.logout_button);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_profile);
        logout.setOnClickListener(this);
        fab.setOnClickListener(this);

        new GetProfileInfo(getContext(), mUsernameInfo, mEmailInfo).execute(token);

        return v;
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
