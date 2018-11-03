package com.devilsoftware.healthy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.devilsoftware.healthy.Models.ArticleModel;
import com.devilsoftware.healthy.Models.IllnessesResult;
import com.devilsoftware.healthy.R;

import java.util.List;

public class ResultListAdapter extends BaseAdapter {

    IllnessesResult mIllnessesResult;
    LayoutInflater inflater;
    Context mContext;

    public ResultListAdapter(Context context, IllnessesResult list){
        mContext = context;
        inflater = LayoutInflater.from(context);
        mIllnessesResult = list;
    }

    @Override
    public int getCount() {
        return mIllnessesResult.articleModels.size();
    }

    @Override
    public ArticleModel getItem(int position) {
        return getCorrectArticle(mIllnessesResult.illnessResults.get(position).id);
    }

    @Override
    public long getItemId(int position) {
        return mIllnessesResult.articleModels.get(position).id;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.illness_item, parent, false);
        }

        ArticleModel model = getItem(position);

        ((TextView) view.findViewById(R.id.title)).setText(model.title
                + " - "
                + mIllnessesResult.illnessResults.get(position).percent*100 + "%");
        ((TextView) view.findViewById(R.id.description)).setText(model.description);
        Glide
                .with(mContext)
                .load(model.urlMainImage)
                .into(((ImageView) view.findViewById(R.id.image)));

        return view;
    }

    ArticleModel getCorrectArticle(int id){
        for (ArticleModel articleModel : mIllnessesResult.articleModels){
            if(articleModel.id==id) return articleModel;
        }
        return null;
    }
}