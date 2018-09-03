package com.example.francesco.mytravel;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

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

        Button logout = (Button) v.findViewById(R.id.logout_button);
        logout.setOnClickListener(this);

        new GetProfileInfo(getContext(), mUsernameInfo, mEmailInfo).execute(token);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout_button:
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

}
