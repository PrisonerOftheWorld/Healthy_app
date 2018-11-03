package com.devilsoftware.healthy.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.devilsoftware.healthy.main.App;
import com.devilsoftware.healthy.activities.InfoActivity;
import com.devilsoftware.healthy.R;

public class RegistrationFragment  extends Fragment {

    View mainView;
    TextView status;
    EditText editHeight;
    EditText editWeight;
    EditText editAge;


    boolean man;
    float height;
    float weight;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(mainView==null){
            mainView = inflater.inflate(R.layout.fragment_registration, container,false);

            status = mainView.findViewById(R.id.status);
            editHeight = mainView.findViewById(R.id.height);
            editWeight = mainView.findViewById(R.id.weight);
            editAge = mainView.findViewById(R.id.age);

            final CheckBox checkBoxMan = mainView.findViewById(R.id.checkMan);
            final CheckBox checkBoxWoman = mainView.findViewById(R.id.checkWoman);
            checkBoxMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        checkBoxWoman.setChecked(false);
                    } else {
                        checkBoxWoman.setChecked(true);
                    }
                    man=b;
                    saveSex();
                }
            });
            checkBoxWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b){
                        checkBoxMan.setChecked(false);
                    } else {
                        checkBoxMan.setChecked(true);
                    }
                    man=!b;
                    saveSex();
                }
            });

            editHeight.setText(String.valueOf(App.getsPreferencesManager().getField("h")));
            editWeight.setText(String.valueOf(App.getsPreferencesManager().getField("w")));

            if (App.getsPreferencesManager().getField("s")==1f) {
                checkBoxMan.setChecked(true);
                checkBoxWoman.setChecked(false);
            } else {
                checkBoxMan.setChecked(false);
                checkBoxWoman.setChecked(true);
            }

            editHeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                        countIMT();
                        return true;
                    }
                    return false;
                }
            });
            editWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                        countIMT();
                        return true;
                    }
                    return false;
                }
            });
            editAge.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                    saveAge();
                    return true;
                }
            });
            editAge.setText(String.valueOf(App.getsPreferencesManager().getFieldInt("age")));

            mainView.findViewById(R.id.info).setOnClickListener(
                    view -> {
                        Intent intent = new Intent(getActivity(), InfoActivity.class);
                        intent.putExtra("title", "Степени ожирения");
                        intent.putExtra("content", "<div class=\"entry-content cms clearfix\"><p class=\"lead\">Наиболее распространенным методом оценки массы тела является вычисление индекса массы тела (ИМТ). Вычисляют его путем деления массы тела в килограммах на квадрат роста в метрах. <strong>ИМТ=</strong><strong>кг/м<sup>2</sup></strong></p><p><a href=\"http://toitumine.ee/wp-content/uploads/2015/11/yle_alakaal_rus.jpg\"><img class=\"aligncenter wp-image-6630 size-full\" src=\"http://toitumine.ee/wp-content/uploads/2015/11/yle_alakaal_rus.jpg\" alt=\"\" width=\"790\" height=\"430\" srcset=\"http://toitumine.ee/wp-content/uploads/2015/11/yle_alakaal_rus.jpg 790w, http://toitumine.ee/wp-content/uploads/2015/11/yle_alakaal_rus-300x163.jpg 300w\" sizes=\"(max-width: 790px) 100vw, 790px\" /></a></p><p>Например, человек, который при росте 1,70 м весит 67 кг, должен произвести следующие вычисления: 67 : (1,7 × 1,7) = 23,18 <br /> Для взрослых нормальной массой тела считается такая, при которой ИМТ находится в интервале от 18,5 до 25.</p><table width=\"66%\"><tbody><tr><td>&lt;18,5</td><td>недостаточный вес</td></tr><tr><td>18,5–24,9</td><td>нормальный вес</td></tr><tr><td>25–29,9</td><td>избыточный вес</td></tr><tr><td>30–34,9</td><td>ожирение I степени</td></tr><tr><td>35–39,9</td><td>ожирение II степени</td></tr><tr><td>&gt;40</td><td>ожирение III степени</td></tr></tbody></table><h4>Как недостаточный, так и, в особенности, избыточный вес сокращают продолжительность жизни.</h4><p>Риск сердечно-сосудистых и многих других заболеваний существенно возрастает, если ИМТ человека выше 27. Если ИМТ больше 30, это уже считается ожирением. Ожирение – это ситуация, при которой количество жира в организме увеличено до такой степени, что это серьезно сказывается на состоянии здоровья.</p><p>Например, для человека ростом 170 см нормальная масса тела составляет 54–72 кг, что является довольно большим диапазоном. У молодых людей вес мог бы быть ближе к нижней границе диапазона, у пожилых – к верхней.</p><p>Поскольку кости у мужчин более плотные и прочные, чем у женщин, их нормальный вес тоже несколько больше женского. Избыточный вес или начальная стадия ожирения у человека ростом 170 см отмечается тогда, когда он весит 73–87 кг, ожирение – когда еще больше.</p><p>Констатация факта ожирения с помощью ИМТ не во всех случаях на 100 % верна. Например, спортсмены, имеющую большую мышечную массу, по этой классификации оказываются имеющими избыточный вес. Поэтому ожирение можно оценивать и по обхвату талии и бедер. У женщин рекомендуемый обхват талии должен быть меньше 88 см, у мужчин – меньше 102 см. Если соотношение обхвата талии и обхвата бедер (одно деленное на другое) у мужчин больше 1, а у женщин больше 0,8, – это указывает на ожирение.</p></div>");
                        intent.putExtra("urlImage", "https://vashsport.com/wp-content/uploads/kak-rasschitat-indeks-massy-tela-640x480.jpg");
                        getActivity().startActivity(intent);
                    }
            );


            countIMT();
        }

        return mainView;
    }

    void saveSex(){
        float s;
        if (man){
            s = 1f;
        } else {
            s = 0f;
        }
        App.getsPreferencesManager().setField("s",s);
    }

    void saveAge(){
        int age = Integer.parseInt(editAge.getText().toString());
        App.getsPreferencesManager().setFieldInt("age", age);
        float n = 0f;
        if (age<1){
            n = 1f;
        } else if (age<3){
            n = 2f;
        } else if (age<14){
            n = 3f;
        } else if (age<25){
            n = 4f;
        } else if (age<40){
            n = 5f;
        } else if (age<60){
            n = 6f;
        } else if (age>=60){
            n = 7f;
        }
        App.getsPreferencesManager().setField("a",n);
    }

    void countIMT(){
        height = Float.valueOf(editHeight.getText().toString());
        App.getsPreferencesManager().setField("h", height);
        weight = Float.valueOf(editWeight.getText().toString());
        App.getsPreferencesManager().setField("w", weight);


        float height2 = (height/100)*(height/100);

        float imt = weight/height2;
        App.getsPreferencesManager().setIMT(imt);

        Log.d("IMT=", String.valueOf(imt));

        changeStatus(imt);
    }

    void changeStatus(float imt){
        if(imt<=18.49f) {
            Log.d("status id = ", String.valueOf(1));
            status.setText("У вас недостаточный вес");
        } else if(imt<=24.99f){
            Log.d("status id = ", String.valueOf(2));
            status.setText("У вас нормальный вес");
        } else if(imt<=29.99f){
            Log.d("status id = ", String.valueOf(3));
            status.setText("У вас избыточный вес");
        } else if(imt<=34.99f){
            Log.d("status id = ", String.valueOf(4));
            status.setText("У вас ожирение I степени");
        } else if(imt<=39.99){
            Log.d("status id = ", String.valueOf(5));
            status.setText("У вас ожирение II степени");
        } else {
            Log.d("status id = ", String.valueOf(6));
            status.setText("У вас ожирение III степени");
        }
    }
}
