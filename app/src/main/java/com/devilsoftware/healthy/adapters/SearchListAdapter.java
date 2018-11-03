package com.devilsoftware.healthy.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devilsoftware.healthy.Models.Organisations.Feature;
import com.devilsoftware.healthy.Models.Organisations.Organisations;
import com.devilsoftware.healthy.Models.Organisations.Phone;
import com.devilsoftware.healthy.R;

public class SearchListAdapter extends BaseAdapter {

    Organisations mOrganisations;
    LayoutInflater inflater;
    Context mContext;

    public SearchListAdapter(Context context, Organisations organisations){
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        mOrganisations = organisations;
    }

    @Override
    public int getCount() {
        return mOrganisations.features.size();
    }

    @Override
    public Object getItem(int position) {
        return mOrganisations.features.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(mOrganisations.features.get(position).properties.companyMetaData.id);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_search_org, parent, false);
        }

        Feature model = mOrganisations.features.get(position);

        StringBuilder builder = new StringBuilder();
        if (model.properties.companyMetaData.hours!=null){
            builder.append("Время работы: ")
                    .append(model.properties.companyMetaData.hours.text)
                    .append(System.getProperty("line.separator"));
        }
        if (model.properties.companyMetaData.phones!=null){
            int index = 0;
            for (Phone phone : model.properties.companyMetaData.phones) {
                if (index>=1){
                    builder.append(System.getProperty("line.separator"));
                    builder.append(phone.formatted);
                } else {
                    builder.append("Контакты: ").append(phone.formatted);
                }
                index++;
            }
            builder.append(System.getProperty("line.separator"));
        }
        if (model.properties.companyMetaData.url!=null){
            builder.append("Сайт: ");
            builder.append(model.properties.companyMetaData.url);
        }


        ((TextView) view.findViewById(R.id.title)).setText(model.properties.name + " - " + model.properties.companyMetaData.address);
        ((TextView) view.findViewById(R.id.info)).setText(builder.toString());

        return view;
    }



}