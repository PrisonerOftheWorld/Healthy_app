package com.devilsoftware.healthy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devilsoftware.healthy.Models.ArticleModel;
import com.devilsoftware.healthy.Models.IllnessesResult;
import com.devilsoftware.healthy.R;
import com.devilsoftware.healthy.adapters.ResultListAdapter;
import com.google.gson.Gson;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String result = getIntent().getStringExtra("result");

        Gson gson = new Gson();

        IllnessesResult illnessesResult = gson.fromJson(result, IllnessesResult.class);

        ResultListAdapter resultListAdapter = new ResultListAdapter(this, illnessesResult);

        ListView listView = findViewById(R.id.resultList);

        listView.setAdapter(resultListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArticleModel model = (ArticleModel) listView.getAdapter().getItem(i);
                Intent intent = new Intent(ResultActivity.this, InfoActivity.class);
                intent.putExtra("title", model.title);
                intent.putExtra("content", model.content);
                intent.putExtra("urlImage", model.urlMainImage);
                startActivity(intent);
            }
        });

    }
}
