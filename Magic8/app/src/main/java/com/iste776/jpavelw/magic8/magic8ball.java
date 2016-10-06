package com.iste776.jpavelw.magic8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class magic8ball extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic8ball);
    }

    public void onClickPredictBtn(View v){
        Random rdm = new Random();
        String[] answers = new String[] {
                "Signs point to yes. ",
                "Yes.",
                "Reply hazy, try again.",
                "Without a doubt.",
                "My sources say no.",
                "As I see it, yes.",
                "You may rely on it.",
                "Concentrate and ask again.",
                "Outlook not so good.",
                "It is decidedly so.",
                "Better not tell you now.",
                "Very doubtful.",
                "Yes - definitely.",
                "It is certain.",
                "Cannot predict now.",
                "Most likely.",
                "Ask again later.",
                "My reply is no.",
                "Outlook good.",
                "Don't count on it.",
                "No."
        };

        String answer = answers[rdm.nextInt(21)];
        Toast toastMessage = Toast.makeText(getApplicationContext(), answer, Toast.LENGTH_LONG);
        toastMessage.show();
    }
}
