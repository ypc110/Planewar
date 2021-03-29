package cn.tedu.shoot;
import java.applet.AudioClip; 
import java.io.*; 
import java.applet.Applet; 

import java.net.MalformedURLException; 
import java.net.URL;
public class music{ 
static URL cb; 
static File f = new File("背景音乐.wav"); //引号里面的是音乐文件所在的绝对鹿筋
static AudioClip aau; 

public music(){ 
super();
}
public static void play() {
 try { 
 cb = f.toURL(); 
 aau = Applet.newAudioClip(cb); 
 // TODO Auto-generated method stub
 aau.play();
 } catch (MalformedURLException e) { 
 e.printStackTrace(); 
 } 
 }
public static void stop() {
 // TODO Auto-generated method stub
 aau.stop(); 
}
}