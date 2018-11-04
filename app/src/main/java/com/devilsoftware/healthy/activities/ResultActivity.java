package com.devilsoftware.healthy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.devilsoftware.healthy.Models.ArticleModel;
import com.devilsoftware.healthy.Models.IllnessesResult;
import com.devilsoftware.healthy.Models.Status;
import com.devilsoftware.healthy.R;
import com.devilsoftware.healthy.adapters.ResultListAdapter;
import com.devilsoftware.healthy.main.App;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.wrong_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.strike:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                EditText editText = new EditText(this);
                builder.setView(editText);
                builder.setTitle("Опишите жалобу");
                builder.setPositiveButton("Отправить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
