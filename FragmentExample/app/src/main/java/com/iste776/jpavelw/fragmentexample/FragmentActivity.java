package com.iste776.jpavelw.fragmentexample;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FragmentActivity extends Activity {

    private MyListFragment myListFragment;
    private FragmentTwo fragmentTwo;
    private Button listBtn;
    private Button fragmentTwoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        listBtn = (Button) findViewById(R.id.list_fragment_button);
        listBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadFragment("List");
            }
        });
        fragmentTwoBtn = (Button) findViewById(R.id.fragment_two_button);
        fragmentTwoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadFragment("Two");
            }
        });
        //would need to keep track of which fragment is being displayed for
        //rotation if desired in the appropriate methods
        loadFragment("List");
    }

    private void loadFragment(String which){
        if(which.equals("List")){
            //get the MyListFragment
            myListFragment = new MyListFragment();
            //set the change listener
            myListFragment.setItemChangedlistener(itemChangedListener);
            //note: no transition or backstack - but clear backstack
            getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.app_info, myListFragment);
            ft.commit();
        } else if(which.equals("Two")){
            //get the Fragment Two Fragment
            fragmentTwo = new FragmentTwo();

            //note: no transition or backstack - but clear backstack
            getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.app_info, fragmentTwo);
            ft.commit();
        }
    }

    //listener for this fragment
    private MyListFragment.ItemChangedListener itemChangedListener =
            new MyListFragment.ItemChangedListener() {
                @Override
                public void onSelectedItemChanged(String itemNameString) {
                    //create and show the fragment
                    DetailFragment details = DetailFragment.newInstance(itemNameString);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.fragment_animation_fade_in, R.animator.fragment_animation_fade_out);
                    ft.replace(R.id.app_info, details);
                    ft.addToBackStack(null);
                    //optional name for this backstack state or null – needs to be just before the commit –
                    //if multiple “transactions”  (add/remove/replace in any combination) happen before “addToBackStack” ,
                    // they ALL are undone with one back button. if you use addToBackStack, the fragment is stopped and
                    // resumed when the user navigates to it, if you don’t use addToBackStack it is destroyed.
                    ft.commit();
                }
            };
}
