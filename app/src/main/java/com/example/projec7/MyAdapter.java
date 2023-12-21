package com.example.projec7;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;

    public MyAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if (index == 0) return new viewholder_main();
        else if (index == 1) return new viewholder_2();
        else return new viewholder_3();


    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) {
        return position % mCount;
    }
}