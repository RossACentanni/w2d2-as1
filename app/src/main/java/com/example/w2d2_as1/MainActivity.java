package com.example.w2d2_as1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName()+"_TAG";
    public static final String HANDLER_TO_PRINT = "com.example.w2d2e1.HANDLER_TEXT";

    private EditText targetInput;
    private static TextView countField;

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            String message = msg.getData().getString(HANDLER_TO_PRINT);
            countField.append(message + "\n");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    public void startCount(View view){
        String textInput = targetInput.getText().toString();
        int targetNumber = Integer.parseInt(textInput);

        CountingThreads myCountingThreads = new CountingThreads(targetNumber);
        CountingThreads.firstThread threadOne = myCountingThreads.new firstThread();
        CountingThreads.secondThread threadTwo = myCountingThreads.new secondThread();

        threadOne.start();
        threadTwo.start();
    }

    //Set up the UI elements.
    private void initUI(){
        targetInput = findViewById(R.id.etTargetNumber);
        countField = findViewById(R.id.tvCount);
    }
}
