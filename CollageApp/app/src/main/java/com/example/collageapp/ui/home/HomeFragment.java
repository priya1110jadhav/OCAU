package com.example.collageapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.collageapp.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;

public class HomeFragment extends Fragment {

    private SliderLayout sliderLayout;
    private ImageView map;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sliderLayout = view.findViewById(R.id.slider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(1);

        setSliderViews();

        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();


            }
        });


        return view;
    }

    private void openMap() {
        Uri uri =Uri.parse("geo:0, 0?q=Terna Engineering College Nerul");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void setSliderViews() {
        for (int i = 0; i< 5; i++){
            DefaultSliderView sliderView = new DefaultSliderView(getContext());

            switch (i){
                case 0:
                    sliderView.setImageUrl("https://img.collegedekhocdn.com/media/img/institute/crawled_images/None/exterior.jpg?tr=w-250,h-150");
                    break;
                case 1:
                    sliderView.setImageUrl("https://www.collegesearch.in/upload/institute/images/large/130115040233_pic1.gif");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.shiksha.com/mediadata/images/1583136399phpR2bDDr.jpeg");
                    break;
                case 3:
                    sliderView.setImageUrl("https://static.wixstatic.com/media/9a8f13_d8aabc74a5c54831aa9635af189f792c.gif");
                    break;
                case 4:
                    sliderView.setImageUrl("https://images.shiksha.com/mediadata/images/1583136399phpR2bDDr.jpeg");
                    break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            sliderLayout.addSliderView(sliderView);

        }

    }
}