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


/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */

/**
 * The type Sound engine.
 *
 * It handles the lifetime of mediaplayers and playing of sounds when many sound-types with variations are needed.
 */
public class SoundEngine {
    HashMap<String, SoundContainer> sounds = new HashMap<>();

    /**
     * löscht den sound
     */
    public void release(){
        for (HashMap.Entry<String, SoundContainer> entry:sounds.entrySet()){
            entry.getValue().release();
        }
        sounds.clear();
    }

    /**
     * fügt einen sound hinzu
     * @param name
     * @param soundVariations
     * @param soundVolume
     * @param applicationContext
     */
    public void add(String name, @RawRes int[] soundVariations, float soundVolume,
                    Context applicationContext){
        sounds.put(name,new SoundContainer(soundVariations,soundVolume,applicationContext));
    }

    /**
     * spielt den sound ab
     * @param name
     */
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
                    mp.pause();
                    mp.seekTo(0);
                });
                this.soundVariations[i] = player;
            }
        }

        /**
         * spielt den sound ab
         */
        public void play() {
            soundVariations[soundVariationCounter%soundVariations.length].start();
            soundVariationCounter++;
        }

        /**
         * stopt den soundplayer
         */
        public void release(){
            for(MediaPlayer player:soundVariations){
                player.stop();
                player.setOnCompletionListener(null);
                player.release();
            }
        }
    }
}
