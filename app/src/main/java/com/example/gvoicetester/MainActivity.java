package com.example.gvoicetester;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.gcloud.voice.GCloudVoiceEngine;
import com.tencent.gcloud.voice.IGCloudVoiceNotify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
  private boolean isInit = false;
  private GCloudVoiceEngine engine = GCloudVoiceEngine.getInstance();
  private List<String> messages = new ArrayList<>();
  private IGCloudVoiceNotify notify = new Nodify(this);
  private TextView logger;
  private EditText room_id;
  private EditText open_id;
  private TextView mic_level;
  private TextView speaker_level;
  private Handler levelHandler = new Handler();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.init_engine).setOnClickListener(this);
    findViewById(R.id.set_app_info).setOnClickListener(this);
    findViewById(R.id.open_mic).setOnClickListener(this);
    findViewById(R.id.close_mic).setOnClickListener(this);
    findViewById(R.id.open_speaker).setOnClickListener(this);
    findViewById(R.id.close_speaker).setOnClickListener(this);
    findViewById(R.id.join_room).setOnClickListener(this);
    findViewById(R.id.quit_room).setOnClickListener(this);
    logger = findViewById(R.id.logger);
    open_id = findViewById(R.id.open_id);
    room_id = findViewById(R.id.room_id);
    mic_level = findViewById(R.id.mic_level);
    speaker_level = findViewById(R.id.speaker_level);
    open_id.setText(android.os.Build.SERIAL);
    this.console("onCreate()");
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.set_app_info:
        setAppInfo();
        break;
      case R.id.init_engine:
        initEngine();
        break;
      case R.id.join_room:
        joinRoom();
        break;
      case R.id.open_mic:
        openMic();
        break;
      case R.id.close_mic:
        closeMic();
        break;
      case R.id.open_speaker:
        openSpeaker();
        break;
      case R.id.close_speaker:
        closeSpeaker();
        break;
      case R.id.quit_room:
        quitRoom();
    }
  }
  private void initEngine(){
    int ret;
    ret = GCloudVoiceEngine.getInstance().init(this.getApplicationContext(), this);
    this.console("init(ApplicationContext, Activity):" + ret);
    ret = GCloudVoiceEngine.getInstance().Init();
    this.console("Init():" + ret);
    ret = GCloudVoiceEngine.getInstance().SetMode(0);
    this.console("SetMode(0):" + ret);
    ret = GCloudVoiceEngine.getInstance().SetNotify(notify);
    this.console("SetNotify(0):" + ret);
    if (!isInit) {
      isInit = true;
      this.startLevelTimer();
    }
  }
  private void startLevelTimer(){
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        speaker_level.setText( R.string.speaker_level + engine.GetSpeakerLevel());
        mic_level.setText(R.string.mic_level + engine.GetSpeakerLevel());
      }
    };
    timer.schedule(task, 100, 100);
  }
  private class LevelHander extends Handler {
    han
  }
  private void startPoll(){
    Timer timer = new Timer();
    TimerTask pollTask = new TimerTask() {
      @Override
      public void run() {
        engine.Poll();
      }
    };
    timer.schedule(pollTask, 500, 500);
  }
  private void setAppInfo(){
    TextView view = findViewById(R.id.open_id);
    String openID = view.getText().toString();
    String appID = "1145892056";
    String appKey = "d2336bb32ff97ec8f818d97d13ed2abd";
    int ret = GCloudVoiceEngine.getInstance().SetAppInfo(appID, appKey, openID);
    this.console(String.format(Locale.CHINA,"SetAppInfo(appID:%s, appKey:%s, openID:%s):%d", appID, appKey, openID, ret));
  }
  private void joinRoom(){
    String roomID = room_id.getText().toString();
    int timeout = 10000;
    int ret = GCloudVoiceEngine.getInstance().JoinTeamRoom(roomID, timeout);
    this.console(String.format(Locale.CHINA,"JoinTeamRoom(roomID:%s, timeout:%d):%d", roomID, timeout, ret));
  }
  private void quitRoom(){
    String roomID = room_id.getText().toString();
    int timeout = 10000;
    int ret = engine.QuitRoom(roomID, timeout);
    this.console(String.format(Locale.CHINA,"QuitRoom(roomID:%s, timeout:%d):%d", roomID, timeout, ret));
  }
  private void openMic(){
    int ret = engine.OpenMic();
    this.console(String.format(Locale.CHINA,"OpenMic():%d", ret));
  }
  private void closeMic(){
    int ret = engine.OpenMic();
    this.console(String.format(Locale.CHINA,"CloseMic():%d", ret));
  }
  private void openSpeaker(){
    int ret = engine.OpenSpeaker();
    this.console(String.format(Locale.CHINA,"OpenSpeaker():%d", ret));
  }
  private void closeSpeaker(){
    int ret = engine.CloseSpeaker();
    this.console(String.format(Locale.CHINA,"CloseSpeaker():%d", ret));
  }
  public void console(String content){
    Calendar dt = Calendar.getInstance();
    content = "[" + dt.get(Calendar.MINUTE) + ":" + dt.get(Calendar.SECOND) + "." + dt.get(Calendar.MILLISECOND) +"] " + content ;
    this.messages.add(0, content);
    while (this.messages.size() > 6) { this.messages.remove(6); }
    StringBuilder sb = new StringBuilder();
    for(String s: this.messages) {
      if (sb.length() == 0) {
        sb.append(s);
      } else {
        sb.append("\n").append(s);
      }
    }
    logger.setText(sb.toString());
  }

  @Override
  protected void onResume() {
    super.onResume();
    if(isInit) {
      engine.Resume();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    if(isInit) {
      engine.Pause();
    }
  }
}
