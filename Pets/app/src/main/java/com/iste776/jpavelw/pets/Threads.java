package com.iste776.jpavelw.pets;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class Threads extends AppCompatActivity {

    private Button message_btn = null;
    private Button choice_btn = null;
    private ImageView first_view = null;
    private ImageView second_view = null;
    private final Random random = new Random();
    private final int[] ids = { R.drawable.fox, R.drawable.kitten, R.drawable.pug };
    private int flag = 0;
    private final Object zync = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        first_view = (ImageView) findViewById(R.id.first_view);
        second_view = (ImageView) findViewById(R.id.second_view);

        message_btn = (Button) findViewById(R.id.message_btn);
        message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast cold_msg = Toast.makeText(getApplicationContext(), getResources().getText(R.string.cold_text), Toast.LENGTH_SHORT);
                cold_msg.show();
            }
        });

        choice_btn = (Button) findViewById(R.id.choice_btn);
        choice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 0;
                first_view.setImageDrawable(null);
                second_view.setImageDrawable(null);
                setImage(first_view);
                setImage(second_view);
                choice_btn.setEnabled(false);
            }
        });
    }

    private void setImage(final ImageView view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int time = (random.nextInt(10) + 1) * 1000;
                final int resource = ids[random.nextInt(3)];

                try {
                    Thread.sleep(time);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageResource(resource);
                            synchronized (zync){
                                ++flag;
                                if(2 == flag)
                                    choice_btn.setEnabled(true);
                            }
                        }
                    });

                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
