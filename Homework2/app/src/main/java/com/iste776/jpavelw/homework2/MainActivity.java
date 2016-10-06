package com.iste776.jpavelw.homework2;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyListFragment myListFragment;
    private Button schoolListBtn;

    private MyListFragment.SchoolSelectedListener schoolSelectedListener =
            new MyListFragment.SchoolSelectedListener(){
                @Override
                public void onSelectedSchool(String schoolAbbreviation) {
                    ItemDetail details = ItemDetail.newInstance(schoolAbbreviation);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.fragment_animation_fade_in, R.animator.fragment_animation_fade_out);
                    ft.replace(R.id.list_frame, details);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        schoolListBtn = (Button) findViewById(R.id.school_list_btn);
        schoolListBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                loadDetail();
            }
        });
        loadDetail();
    }

    private void loadDetail(){
        myListFragment = new MyListFragment();
        myListFragment.setSchoolSelectedListener(this.schoolSelectedListener);
        getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.list_frame, myListFragment);
        ft.commit();
    }
}
