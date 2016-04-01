package com.tamilglitz.sidharthyatish.tamilglitzbeta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class AboutUs extends Fragment {
    public AboutUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView web_button = (ImageView) getActivity().findViewById(R.id.web_button);
        web_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wintent = new Intent();
                wintent.setAction(Intent.ACTION_VIEW);
                wintent.addCategory(Intent.CATEGORY_BROWSABLE);
                wintent.setData(Uri.parse("https://tamilglitz.in"));
                startActivity(wintent);
            }
        });
        ImageView fb_button= (ImageView) getActivity().findViewById(R.id.facebook_button);
        fb_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fintent = new Intent();
                fintent.setAction(Intent.ACTION_VIEW);
                fintent.addCategory(Intent.CATEGORY_BROWSABLE);
                fintent.setData(Uri.parse("https://www.facebook.com/TamilGlitzofficial/"));
                startActivity(fintent);
            }
        });
        ImageView g_button= (ImageView) getActivity().findViewById(R.id.gplus_button);
        g_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gintent = new Intent();
                gintent.setAction(Intent.ACTION_VIEW);
                gintent.addCategory(Intent.CATEGORY_BROWSABLE);
                gintent.setData(Uri.parse("https://plus.google.com/+TamilglitzInOfficial"));
                startActivity(gintent);
            }
        });
        ImageView t_button= (ImageView) getActivity().findViewById(R.id.twitter_button);
        t_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tintent = new Intent();
                tintent.setAction(Intent.ACTION_VIEW);
                tintent.addCategory(Intent.CATEGORY_BROWSABLE);
                tintent.setData(Uri.parse("https://twitter.com/TamilGlitzin"));
                startActivity(tintent);
            }
        });
        ImageView y_button= (ImageView) getActivity().findViewById(R.id.youtube_button);
        y_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yintent = new Intent();
                yintent.setAction(Intent.ACTION_VIEW);
                yintent.addCategory(Intent.CATEGORY_BROWSABLE);
                yintent.setData(Uri.parse("https://www.youtube.com/channel/UCpgwfEjqkCGDjEdsj_C67Kg"));
                startActivity(yintent);
            }
        });


    }
}
