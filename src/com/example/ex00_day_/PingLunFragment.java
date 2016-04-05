package com.example.ex00_day_;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class PingLunFragment extends Fragment {

	public PingLunFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = initUI(inflater, container);
		return view;
	}

	private View initUI(LayoutInflater inflater, ViewGroup container) {
		View view = inflater.inflate(R.layout.fragment_ping_lun, container, false);
		return view;
	}

	
	
	

}
