package ch.band.jumpknock.game;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.RawRes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import ch.band.jumpknock.Constants;
import ch.band.jumpknock.GameActivity;

public class SoundEngine {
    Random ranGenerator = new Random();
    HashMap<String, SoundContainer> sounds = new HashMap<>();
    public void release(){
        for (HashMap.Entry<String, SoundContainer> entry:sounds.entrySet()){
            entry.getValue().release();
            sounds.remove(entry.getKey());
        }
    }
    public void Add(String name, @RawRes int[] soundVariations, float soundVolume,
                    int mediaPlayerCount, boolean shouldPlayRandom,
                    Context applicationContext){
        sounds.put(name,new SoundContainer(soundVariations,soundVolume,mediaPlayerCount,shouldPlayRandom,applicationContext));
    }
    public void Play(String name){
        sounds.get(name).play();
    }
    private class SoundContainer{
        private final String TAG = SoundContainer.class.getCanonicalName();
        private Queue<MediaPlayer> mediaPlayers;
        @RawRes
        private int[] soundVariations;
        private int soundVariationCounter = 0;
        private boolean shouldPlayRandom = false;
        public SoundContainer(@RawRes int[] soundVariations, float soundVolume,
                              int mediaPlayerCount, boolean shouldPlayRandom,
                              Context applicationContext){

            this.soundVariations = soundVariations;
            this.shouldPlayRandom = shouldPlayRandom;
            mediaPlayers = new ArrayBlockingQueue<MediaPlayer>(mediaPlayerCount);

            for(int i = 0;i < mediaPlayerCount;i++){
                MediaPlayer player = new MediaPlayer();
                setMediaPlayerSource(player,applicationContext);
                player.setOnCompletionListener((mp)->{
                    setMediaPlayerSource(mp,applicationContext);
                });
                mediaPlayers.add(player);
            }
        }
        private void setMediaPlayerSource(MediaPlayer player, Context applicationContext){
            if(shouldPlayRandom){
                try {
                    player.setDataSource(applicationContext,
                            Uri.parse(Constants.RESSOURCE_URI_PREFIX+soundVariations[ranGenerator.nextInt(soundVariations.length)]));
                    player.prepareAsync();
                } catch (IOException e) {
                    Log.d(TAG,"play: ",e);
                }
            }else {
                try {
                    player.setDataSource(applicationContext,
                            Uri.parse(Constants.RESSOURCE_URI_PREFIX+soundVariations[soundVariationCounter %soundVariations.length]));
                    player.prepareAsync();
                } catch (IOException e) {
                    Log.d(TAG,"play: ",e);
                }
            }
        }
        public void play() {
            MediaPlayer player = mediaPlayers.poll();
            player.start();
            mediaPlayers.add(player);
        }
        public void release(){
            for(MediaPlayer player:mediaPlayers){
                player.release();
                mediaPlayers.remove(player);
                player == null;
            }
        }
    }
}
