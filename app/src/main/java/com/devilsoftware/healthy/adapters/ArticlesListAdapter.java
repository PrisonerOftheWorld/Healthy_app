package com.devilsoftware.healthy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.devilsoftware.healthy.Models.ArticleModel;
import com.devilsoftware.healthy.R;
import com.devilsoftware.healthy.activities.InfoActivity;

import java.util.List;

public class ArticlesListAdapter extends BaseAdapter {

    List<ArticleModel> models;
    LayoutInflater inflater;
    Context mContext;

    public ArticlesListAdapter(Context context, List<ArticleModel> list){
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        models = list;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return models.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.illness_item, parent, false);
        }

        ArticleModel model = models.get(position);

        ((TextView) view.findViewById(R.id.title)).setText(model.title);
        ((TextView) view.findViewById(R.id.description)).setText(model.description);
        Glide
                .with(mContext)

                .asBitmap()

                .load(model.urlMainImage)

                .into(new BitmapImageViewTarget(((ImageView) view.findViewById(R.id.image))) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable bitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                       // bitmapDrawable.setCircular(true);//comment this line and uncomment the next line if you dont want it fully cricular
                        bitmapDrawable.setCornerRadius(25);
                        ((ImageView) view.findViewById(R.id.image)).setImageDrawable(bitmapDrawable);
                    }
                });



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, InfoActivity.class);
                intent.putExtra("title", model.title);
                intent.putExtra("content", model.content);
                intent.putExtra("urlImage", model.urlMainImage);
                mContext.startActivity(intent);
            }
        });

        return view;
    }
}