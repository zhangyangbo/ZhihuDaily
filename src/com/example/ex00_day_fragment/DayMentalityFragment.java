package com.example.ex00_day_fragment;

import android.os.Bundle;

import com.example.ex00_day_.R;
import com.example.ex00_day_.R.layout;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class DayMentalityFragment extends Fragment {

	public DayMentalityFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_day_mentality, container, false);
	}

}
