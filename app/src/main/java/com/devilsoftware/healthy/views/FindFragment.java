package com.devilsoftware.healthy.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.devilsoftware.healthy.main.App;
import com.devilsoftware.healthy.adapters.ArticlesListAdapter;
import com.devilsoftware.healthy.activities.InfoActivity;
import com.devilsoftware.healthy.Models.ArticleModel;
import com.devilsoftware.healthy.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindFragment extends Fragment {

    View mainView;
    ListView mListView;
    EditText mEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mainView==null){
            App.getAPIService().initRetrofit(App.URL);
            mainView  = inflater.inflate(R.layout.fragment_find, container, false);
            mListView = mainView.findViewById(R.id.findResults);
            mEditText = mainView.findViewById(R.id.editText);

            mEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                    App.getAPIService().getHealthyAPI().getIllnesses(editable.toString()).enqueue(new Callback<List<ArticleModel>>() {

                        @Override
                        public void onResponse(Call<List<ArticleModel>> call, Response<List<ArticleModel>> response) {
                            List<ArticleModel> articleModels = response.body();
                            Log.d("SUCCESS", " FIND = " + new Gson().toJson(articleModels));
                            if (articleModels!=null){
                                ArticlesListAdapter listAdapter = new ArticlesListAdapter(getActivity(), articleModels);
                                mListView.setAdapter(listAdapter);
                                mListView.setOnItemClickListener((adapterView, view, i, l) -> {
                                    ArticleModel model = (ArticleModel) mListView.getAdapter().getItem(i);
                                    Intent intent = new Intent(getContext(), InfoActivity.class);
                                    intent.putExtra("title", model.title);
                                    intent.putExtra("content", model.content);
                                    intent.putExtra("urlImage", model.urlMainImage);
                                    startActivity(intent);
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<ArticleModel>> call, Throwable t) {
                            Log.d("FAILURE", " FIND");
                        }

                    });
                }
            });


        }

        return mainView;
    }

}
