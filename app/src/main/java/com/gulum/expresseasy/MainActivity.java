package com.gulum.expresseasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gulum.expresseasy.util.Config;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_phonenum;
    private TextView tv_agentname;
    private TextView tv_agentaddr;
    private ImageView imgv_agentpic;
    private Button btn_0;
    private Button btn_talk;
    private Button btn_del;
    private GridLayout lyt_keyboard;
    private VoiceHandler voiceHandler;
    private SmsHandler smsHandler;

    private Config config;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        config = new Config(this);

         et_phonenum = (EditText)findViewById(R.id.edit_phonenum);
        tv_agentname = (TextView)findViewById(R.id.tv_agentname);
        tv_agentaddr = (TextView)findViewById(R.id.tv_agentaddr);
        imgv_agentpic = (ImageView)findViewById(R.id.img_agent);
        btn_0 = (Button)findViewById(R.id.btn_0);
        btn_talk = (Button)findViewById(R.id.btn_talk);
        btn_del = (Button)findViewById(R.id.btn_del);
        lyt_keyboard = (GridLayout)findViewById(R.id.layout_keyboard);

        smsHandler = new SmsHandler(this);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                et_phonenum.setText("");
                return false;
            }
        });

        voiceHandler = new VoiceHandler(this,et_phonenum);
        btn_talk.setOnTouchListener(new View.OnTouchListener() {
            private Long startTime;
            private Long endTime;
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startTime = new Date().getTime();
                        int code = voiceHandler.startVoiceRecognition();
                        break;
                    case MotionEvent.ACTION_UP:
                        endTime = new Date().getTime();
                        if ((endTime - startTime ) < 1000 ){
                            voiceHandler.stopVoiceRecognition();
                        }else {
                            voiceHandler.finishSpeech();
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        int inputType = config.getInputType();
        switch (inputType){
            case Config.INPUT_KEYBOARD:
                break;
            case Config.INPUT_VOICE:
                lyt_keyboard.setVisibility(View.GONE);
                btn_0.setVisibility(View.GONE);
                btn_talk.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if(smsHandler != null){
            smsHandler.unregisterReceiver();
            smsHandler = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case  R.id.action_settings:
                Intent intent = new Intent(MainActivity.this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_addagent:
                Toast.makeText(this, "Menu Item search selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_record:
                Toast.makeText(this, "Menu Item search selected",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about:
                Toast.makeText(this, "Menu Item search selected",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btn_chmode:
                onClickChmode();
                break;
            case R.id.btn_del:
                onClickDel();
                break;
            case R.id.btn_talk:

                break;
            case R.id.btn_send:
                switch (config.getSmsType()){
                    case Config.SMS_LOCAL:
                        smsHandler.sendSms(et_phonenum.getText().toString(),config.getSmsTemplate());
                        break;
                    case Config.SMS_SERVER:

                        break;
                }

                break;
            default:
                onClickFigure(v);
                break;
        }
    }

    private void onClickChmode(){
        if(btn_talk.getVisibility() == View.GONE){
            lyt_keyboard.setVisibility(View.GONE);
            btn_0.setVisibility(View.GONE);
            btn_talk.setVisibility(View.VISIBLE);
            config.setInputType(Config.INPUT_VOICE);
        }else {
            btn_talk.setVisibility(View.GONE);
            lyt_keyboard.setVisibility(View.VISIBLE);
            btn_0.setVisibility(View.VISIBLE);
            config.setInputType(Config.INPUT_KEYBOARD);
        }
    }

    private void onClickDel(){
        String num = et_phonenum.getText().toString();
        if(num.length() > 0){
            et_phonenum.setText(num.substring(0,num.length()-1));
            et_phonenum.setSelection(et_phonenum.length());
        }
    }
    private void onClickFigure(View v){
        Button b = (Button)v;
        String fig = b.getText().toString();
        //et_phonenum.setText(et_phonenum.getText() + fig);
        if(et_phonenum.length() < 11){
            et_phonenum.append(fig);
        };
        et_phonenum.setSelection(et_phonenum.length());
    }


}
