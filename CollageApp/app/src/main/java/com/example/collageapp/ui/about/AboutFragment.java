package com.example.collageapp.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.collageapp.R;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;
    private ImageView map;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_it, "Computer Science", "The department of Computer Engineering was established in 1991 with initial intake of 60 to meet the demand of society and later it increased to 180 (First shift 120 and Second shift 60). It is approved by AICTE, DTE and affiliated to University of Mumbai and accredited by the National Board of Accreditation (NBA).The department also has PG programme (ME) with 18 intake and has laboratories equipped with the latest hardware and software."));
        list.add(new BranchModel(R.drawable.ic_mech,"Mechanical Engineering", "Welcome to Mechanical Engineering Department at Terna Engineering College, Nerul, Navi Mumbai. In this institute, Mechanical Engineering started in 2014 with the intake of 60. Later on, it was increased to 120 in the year 2017.Our goal for undergraduate students is to give them a quality education with hands on experience which will be useful to the society at large"));
        list.add(new BranchModel(R.drawable.ic_civil,"Civil Engineering", "The department of civil engineering is established in the year 2017. The mission of Department is to promote the disciplines of Planning, Design, Construction, Operation, Maintenance and Research. It offers students technical knowledge with technique for better utilization of available resources and greater standardization of construction processes required by construction industry."));
        list.add(new BranchModel(R.drawable.ic_comp, "Information Technology", "Terna Engineering College enriched IT Infrastructure for students to build IT skills, apply innovative ideas, implement real world projects and make them competent in the IT Sector. The Department is reputed in Mumbai University for both its academic excellence and innovative approach. The Faculty forms the nerve of the department and has been successfully organizing seminars, workshops, guest lectures, internship, real time projects and industrial visits for students to keep them updated with the real world."));
        list.add(new BranchModel(R.drawable.ic_electronics, "Electronic Engineering", "Electronics Engineering Department, since its inception in 1991, has achieved great recognition in the field of technical education. The department offers B.E. in Electronics Engineering, a four-year degree program with an intake of 60 students. The National Board of Accreditation accredited the department in 2016. It comprises highly qualified and professionally skilled faculty members with good record of the published work. The faculty members organize training programs in the various areas of engineering such as embedded systems, VLSI and networking."));

        adapter = new BranchAdapter(getContext(), list);
        viewPager = view.findViewById(R.id.viewPager);

        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.college_image);

        Glide.with(getContext())
                .load("https://ternaengg.ac.in/wp-content/uploads/2018/06/IMG_5878.jpg")
                .into(imageView);


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


}