package com.biz.tizzy.tickle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by tizzy on 1/19/18.
 */

public class TickleFragment extends Fragment {

    public static TickleFragment newInstance() {
        return new TickleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tickle, container, false);
        return v;
    }
}
