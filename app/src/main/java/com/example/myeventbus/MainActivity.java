package com.example.myeventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.libbus.EventBus;
import com.example.libbus.LoginEvent;
import com.example.libbus.Subscribe;
import com.example.libbus.ThreadModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getInstance().register(this);

        findViewById(R.id.tv_h).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getInstance().postEvent(new LoginEvent("登录成功"));
            }
        });
    }

    @Subscribe(thread = ThreadModel.BACKGROUND)
    public void haha(LoginEvent loginEvent){
        Log.e("EventBus",loginEvent.getLoginStatus()+Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unRegister(this);
    }
}
