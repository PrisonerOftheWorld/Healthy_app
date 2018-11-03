package com.devilsoftware.healthy.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.devilsoftware.healthy.main.App;
import com.devilsoftware.healthy.Models.HashRegions;
import com.devilsoftware.healthy.R;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplyFragmentLevel extends Fragment{

    View mainView;
    ArrayAdapter<String> arrayAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mainView==null){
            App.getAPIService().initRetrofit(App.URL);
            mainView = inflater.inflate(R.layout.fragment_apply,container,false);

            final ListView listView = mainView.findViewById(R.id.regions);

            int level = 0;

            boolean first = true;

            int admin = getArguments() != null ? getArguments().getInt("admin") : 0;

            if (getArguments() != null) {
                level = getArguments().getInt("level");
                if (level != 0) first = false;
            }

            final boolean finalFirst = first;
            int finalAdmin = admin;
            App.getAPIService().getHealthyAPI().getRegions(level).enqueue(new Callback<HashRegions>() {
                @Override
                public void onResponse(Call<HashRegions> call, Response<HashRegions> response) {

                    final HashRegions hashRegions = response.body();
                    if(hashRegions!=null){

                        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);

                        for (Map.Entry i : hashRegions.map.entrySet()){


                            arrayAdapter.add(hashRegions.map.get((Integer) i.getKey()));
                            Log.d("TAG", String.valueOf(i));

                        }
                        listView.setAdapter(arrayAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                int index = 0;

                                String selected = arrayAdapter.getItem(i);
                                for (String s : hashRegions.map.values()) {

                                    arrayAdapter.getItem(i);

                                    if (!s.equals(selected)) index++; else break;
                                }

                                int index1 = 0;

                                int nextLevel = -1;
                                for (int p : hashRegions.map.keySet()){

                                    if(index1==index){
                                        nextLevel  = p;
                                        break;
                                    } else {
                                        index1++;
                                    }
                                }

                                if (finalFirst){
                                    ApplyFragmentLevel applyFragmentLevel = new ApplyFragmentLevel();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("level", nextLevel);
                                    bundle.putInt("admin", finalAdmin);
                                    applyFragmentLevel.setArguments(bundle);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container, applyFragmentLevel);
                                    fragmentTransaction.commit();
                                } else {

                                    MainApplyFragment mainApplyFragment = new MainApplyFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("region", nextLevel);
                                    bundle.putInt("admin", finalAdmin);
                                    mainApplyFragment.setArguments(bundle);
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container, mainApplyFragment);
                                    fragmentTransaction.commit();
                                }
                            }
                        });


                    }
                }

                @Override
                public void onFailure(Call<HashRegions> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        return mainView;
    }
}
