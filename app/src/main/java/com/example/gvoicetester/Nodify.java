package com.example.gvoicetester;

import com.tencent.gcloud.voice.IGCloudVoiceNotify;

import java.util.Locale;

public class Nodify implements IGCloudVoiceNotify {
  private MainActivity activity;
  public Nodify(MainActivity activity){
    this.activity = activity;
  }

  @Override
  public void OnJoinRoom(int i, String s, int i1) {
    String content = String.format(Locale.CHINA, "OnJoinRoom(code:%d, roomName:%s, memberID:%d)", i, s, i1);
    this.activity.console(content);
  }

  @Override
  public void OnMemberVoice(int[] ints, int i) {
    String str = "";
    for (int anInt : ints) { str += str.isEmpty() ? anInt: "," + anInt; }
    str = "[" + str + "]";
    this.activity.console(String.format(Locale.CHINA,"OnJoinRoom(inits: %s)", str));
  }

  @Override
  public void OnMemberVoice(String s, int i, int i1) {
    this.activity.console(String.format(Locale.CHINA, "onMemberVoice(roomName: %s, memberID: %d, status: %d)", s, i , i1));
  }

  @Override
  public void OnRoleChanged(int i, String s, int i1, int i2) {
    this.activity.console(String.format(Locale.CHINA, "OnRoleChanged(code: %s, roomName: %s, memberID: %d, status: %d)", i, s, i1 , i2));
  }

  @Override
  public void OnStatusUpdate(int completeCode, String roomName, int memberID) {
    this.activity.console(String.format(Locale.CHINA, "OnRoleChanged(code: %s, roomName: %s, memberID: %d)", completeCode, roomName, memberID));
  }

  @Override
  public void OnQuitRoom(int completeCode, String roomName) {
    this.activity.console(String.format(Locale.CHINA, "OnQuitRoom(code: %s, roomName: %s)", completeCode, roomName));

  }

  @Override
  public void OnApplyMessageKey(int i) {

  }

  @Override
  public void OnRecording(char[] chars, int i) {

  }

  @Override
  public void OnUploadFile(int i, String s, String s1) {

  }

  @Override
  public void OnDownloadFile(int i, String s, String s1) {

  }

  @Override
  public void OnPlayRecordedFile(int i, String s) {

  }

  @Override
  public void OnSpeechToText(int i, String s, String s1) {

  }

  @Override
  public void OnStreamSpeechToText(int i, int i1, String s, String s1) {

  }

  @Override
  public void OnSpeechTranslate(int i, String s, String s1, String s2, int i1) {

  }

  @Override
  public void OnEvent(int i, String s) {
    this.activity.console(String.format(Locale.CHINA, "OnEvent(event: %d, info: %s)", i, s));
  }
}
