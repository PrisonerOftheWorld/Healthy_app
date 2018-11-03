package com.devilsoftware.healthy.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devilsoftware.healthy.main.App;
import com.devilsoftware.healthy.adapters.ArticlesListAdapter;
import com.devilsoftware.healthy.activities.InfoActivity;
import com.devilsoftware.healthy.Models.ArticleModel;
import com.devilsoftware.healthy.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {

    View mainView;
    ListView mListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mainView==null){
            App.getAPIService().initRetrofit(App.URL);

            mainView  = inflater.inflate(R.layout.fragment_main, container, false);

            mListView = mainView.findViewById(R.id.main_list);

            App.getAPIService().getHealthyAPI().getArticles().enqueue(new Callback<List<ArticleModel>>() {
                @Override
                public void onResponse(Call<List<ArticleModel>> call, Response<List<ArticleModel>> response) {
                    List<ArticleModel> articleModels = response.body();

                    if (articleModels != null & getContext()!=null){
                        ArticlesListAdapter articlesListAdapter = new ArticlesListAdapter(getContext(), articleModels);

                        mListView.setAdapter(articlesListAdapter);
                        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                ArticleModel model = (ArticleModel) mListView.getAdapter().getItem(i);
                                Intent intent = new Intent(getContext(), InfoActivity.class);
                                intent.putExtra("title", model.title);
                                intent.putExtra("content", model.content);
                                intent.putExtra("urlImage", model.urlMainImage);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<List<ArticleModel>> call, Throwable t) {

                }
            });


        }

        return mainView;
    }

}
