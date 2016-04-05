package com.example.ex00_day_xinshouyindao;

import com.example.ex00_day_.MainActivity;
import com.example.ex00_day_.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * Created with Android Studio.
 * User: ryan@xisue.com
 * Date: 7/14/14
 * Time: 11:10 PM
 * Desc: ThirdGuideFragment
 */
public class SixthGuideFragment extends BaseGuideFragment {

    View mLayoutLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	View inflate = inflater.inflate(R.layout.fragment_guide_sixth, container, false);
        TextView tv_suibian = (TextView) inflate.findViewById(R.id.suibiankankan);
        tv_suibian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getActivity(),MainActivity.class);
				startActivity(intent);
			}
		});
    	return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutLogo = view.findViewById(R.id.guide_item_1);

        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.splash_guide_logo);
        mLayoutLogo.startAnimation(anim);
    }

    @Override
    public int[] getChildViewIds() {
        return new int[]{};
    }

    @Override
    public int getRootViewId() {
        return R.id.layout_guide_sixth;
    }
}
