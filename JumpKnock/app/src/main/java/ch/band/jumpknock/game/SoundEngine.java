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
        }
        sounds.clear();
    }
    public void add(String name, @RawRes int[] soundVariations, float soundVolume,
                    Context applicationContext){
        sounds.put(name,new SoundContainer(soundVariations,soundVolume,applicationContext));
    }
    public void play(String name){
        sounds.get(name).play();
    }
    private class SoundContainer{
        private final String TAG = SoundContainer.class.getCanonicalName();
        private MediaPlayer[] soundVariations;
        private int soundVariationCounter = 0;
        public SoundContainer(@RawRes int[] soundVariations, float soundVolume,
                              Context applicationContext){
            this.soundVariations = new MediaPlayer[soundVariations.length];

            for(int i = 0;i < soundVariations.length;i++){
                MediaPlayer player = MediaPlayer.create(applicationContext,soundVariations[i]);
                player.setVolume(soundVolume,soundVolume);
                player.setOnCompletionListener((mp)->{
                    mp.stop();
                    mp.seekTo(0);
                });
                this.soundVariations[i] = player;
            }
        }
        public void play() {
            soundVariations[soundVariationCounter%soundVariations.length].start();
            soundVariationCounter++;
        }
        public void release(){
            for(MediaPlayer player:soundVariations){
                player.stop();
                player.setOnCompletionListener(null);
                player.release();
            }
            soundVariations = null;
        }
    }
}
