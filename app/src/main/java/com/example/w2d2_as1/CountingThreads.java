package com.example.w2d2_as1;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class CountingThreads {

    private static final String TAG = CountingThreads.class.getSimpleName()+"_TAG";
    private static int targetNumber;
    //When this boolean is true, it's the first thread's turn to print. If it's false, it's second's.
    private static boolean turn;
    private static final Object lock = new Object();

    public CountingThreads(int num){
        this.targetNumber = num;
        turn = true;
    }

    public void sendNumberToUI(int i, int threadID){
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString(MainActivity.HANDLER_TO_PRINT, Integer.toString(threadID) + ": " + Integer.toString(i));
        msg.setData(data);
        MainActivity.handler.sendMessage(msg);
    }

    //Only counts odd numbers.
    public class firstThread extends Thread{
        private final int id = 1;
        public void run(){
            for(int i=1; i<=targetNumber; i= i+2){
                synchronized (lock){
                    while(turn == false){
                        try{
                            lock.wait();
                        }
                        catch (InterruptedException e){
                            Log.e(TAG, "run: Uh oh in firstThread!");
                        }
                    }

                    sendNumberToUI(i, id);
                    turn = false;
                    lock.notifyAll();

                }
            }
        }
    }

    //Only counts even numbers.
    public class secondThread extends Thread{
        private final int ID = 2;
        public void run(){
            for(int i=2; i<=targetNumber; i= i+2){
                synchronized (lock){
                    while(turn == true){
                        try{
                            lock.wait();
                        }
                        catch (InterruptedException e){
                            Log.e(TAG, "run: Uh oh in secondThread!");
                        }
                    }

                    sendNumberToUI(i, ID);
                    turn = true;
                    lock.notifyAll();

                }
            }
        }
    }
}
