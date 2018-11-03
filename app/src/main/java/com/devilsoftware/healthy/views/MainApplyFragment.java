package com.devilsoftware.healthy.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.devilsoftware.healthy.main.App;
import com.devilsoftware.healthy.Models.Field;
import com.devilsoftware.healthy.Models.Fields;
import com.devilsoftware.healthy.Models.IllnessAddRequest;
import com.devilsoftware.healthy.Models.IllnessRequest;
import com.devilsoftware.healthy.Models.IllnessesResult;
import com.devilsoftware.healthy.Models.Status;
import com.devilsoftware.healthy.R;
import com.devilsoftware.healthy.activities.ResultActivity;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainApplyFragment extends Fragment {

    View mainView;

    IllnessRequest mIllnessRequest = new IllnessRequest();
    Fields mFields = new Fields();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mIllnessRequest.map.put(1,App.getsPreferencesManager().getField("a"));//возраст
        mIllnessRequest.map.put(2,App.getsPreferencesManager().getField("s"));//пол
        mIllnessRequest.map.put(3, (float) getArguments().getInt("region"));//область

        if (mainView==null){
            mainView = inflater.inflate(R.layout.fragment_main_apply,container,false);

            container = mainView.findViewById(R.id.apply_container);

            final int region = getArguments().getInt("region");
            final ViewGroup finalContainer = container;
            App.getAPIService().getHealthyAPI().getFields(region).enqueue(new Callback<Fields>() {
                @Override
                public void onResponse(Call<Fields> call, Response<Fields> response) {
                    Fields fields = response.body();
                    if(fields!=null){
                        int index = 0;
                        for (Field field : fields.list){
                            makeField(field, (LinearLayout) finalContainer, index);
                            index++;
                        }
                    }
                }

                @Override
                public void onFailure(Call<Fields> call, Throwable t) {

                }
            });

            Button button = mainView.findViewById(R.id.apply);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (getArguments().getInt("admin")==1){
                        IllnessAddRequest illnessAddRequest = new IllnessAddRequest();
                        illnessAddRequest.map = mIllnessRequest.map;
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        EditText editText = new EditText(getActivity());
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        builder.setView(editText);
                        builder.setTitle("Id болезни");
                        builder.setPositiveButton("send", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                illnessAddRequest.id = Integer.parseInt(editText.getText().toString());
                                App.getAPIService().getHealthyAPI().addIllness(illnessAddRequest).enqueue(new Callback<Status>() {
                                    @Override
                                    public void onResponse(Call<Status> call, Response<Status> response) {
                                        Status status = response.body();
                                        if (status!=null){
                                            Toast.makeText(getActivity(), status.message, Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Status> call, Throwable t) {

                                    }
                                });
                            }
                        });
                        builder.show();


                    } else {
                        App.getAPIService().getHealthyAPI().detectIllness(mIllnessRequest).enqueue(new Callback<IllnessesResult>() {
                            @Override
                            public void onResponse(Call<IllnessesResult> call, Response<IllnessesResult> response) {
                                Gson gson = new Gson();
                                IllnessesResult result = response.body();
                                if (result != null) {
                                    getContext().startActivity(new Intent(getActivity(), ResultActivity.class).putExtra("result", gson.toJson(result)));
                                }
                                Log.d("TGA", gson.toJson(result));
                            }

                            @Override
                            public void onFailure(Call<IllnessesResult> call, Throwable t) {

                            }
                        });
                    }
                }
            });

        }

        return mainView;
    }


    void makeField(final Field field, LinearLayout container, final int index){
        LinearLayout layout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50,50,50,50);
        layout.setLayoutParams(layoutParams);
        layout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(getActivity());
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setTextSize(18f);
        textView.setLayoutParams(layoutParams);
        textView.setText(field.title);

        layout.addView(textView);

        Spinner spinner = new Spinner(getActivity());
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(layoutParams);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, field.symptoms);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mIllnessRequest.map.put(mFields.list.get(index).id, mFields.list.get(index).ids.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner.setAdapter(arrayAdapter);


        mFields.list.add(field);
        layout.addView(spinner);

        container.addView(layout);
    }
}
