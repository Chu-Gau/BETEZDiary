package com.betezteam.util;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betezteam.DiaryModel.Diary;
import com.betezteam.DiaryModel.DiaryPage;
import com.betezteam.betezdiary.DiaryPageActivity;
import com.betezteam.betezdiary.R;

import java.util.List;

public class DiaryPageAdapter extends BaseAdapter {

    private Context context;
    private List<DiaryPage> diaryPages;
    private int itemLayout;

    @Override
    public int getCount() {
        return diaryPages.size();
    }

    @Override
    public DiaryPage getItem(int i) {
        return diaryPages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public DiaryPageAdapter(Context context, List<DiaryPage> diaryPages) {
        this.context = context;
        this.diaryPages = diaryPages;
        this.itemLayout = R.layout.top30_list_item_layout;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(itemLayout, null);

        TextView date = view.findViewById(R.id.top_30_list_date);
        final TextView content = view.findViewById(R.id.top_30_list_content);

        final DiaryPage page = getItem(i);

        date.setText(page.getDateDisplayed());
        content.setText(page.getContent());

        //assign events
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //assign Anim
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.list_item_anim);
                view.startAnimation(animation);

                Intent intent = new Intent(context, DiaryPageActivity.class);
                intent.putExtra("diaryPage", page);
                context.startActivity(intent);
            }
        });




        return view;
    }
}
