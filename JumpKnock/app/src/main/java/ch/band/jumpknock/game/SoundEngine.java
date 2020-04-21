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

<<<<<<< HEAD
     /*
     * Releases resources associated with the SoundEngine object.
     * It is considered good practice to call this method when you're
     * done playing sounds. In particular, whenever an Activity
     * of an application is paused (its onPause() method is called),
     * or stopped (its onStop() method is called), this method should be
     * invoked to release the MediaPlayer objects, unless the application
     * has a special need to keep the object around. In addition to
     * unnecessary resources (such as memory and instances of codecs)
     * being held, failure to call this method immediately if a
     * MediaPlayer object is no longer needed may also lead to
     * continuous battery consumption for mobile devices, and playback
     * failure for other applications if no multiple instances of the
     * same codec are supported on a device. Even if multiple instances
     * of the same codec are supported, some performance degradation
     * may be expected when unnecessary multiple instances are used
     * at the same time.
      */
=======
    /**
     * löscht den sound
     */
>>>>>>> a0ef24cbe7d54a69208c022107a762bfe78b848b
    public void release(){
        for (HashMap.Entry<String, SoundContainer> entry:sounds.entrySet()){
            entry.getValue().release();
        }
        sounds.clear();
    }

<<<<<<< HEAD
=======
    /**
     * fügt einen sound hinzu
     * @param name
     * @param soundVariations
     * @param soundVolume
     * @param applicationContext
     */
>>>>>>> a0ef24cbe7d54a69208c022107a762bfe78b848b
    public void add(String name, @RawRes int[] soundVariations, float soundVolume,
                    Context applicationContext){
        sounds.put(name,new SoundContainer(soundVariations,soundVolume,applicationContext));
    }

<<<<<<< HEAD
=======
    /**
     * spielt den sound ab
     * @param name
     */
>>>>>>> a0ef24cbe7d54a69208c022107a762bfe78b848b
    public void play(String name){
        sounds.get(name).play();
    }

    private class SoundContainer{
        private final String TAG = SoundContainer.class.getCanonicalName();
        private MediaPlayer[] soundVariations;
        private int soundVariationCounter = 0;


        /**
         * Instantiates a new Sound container.
         *
         * @param soundVariations    the sound variations
         * @param soundVolume        the sound volume
         * @param applicationContext the application context
         */
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
<<<<<<< HEAD
         * Play.
         * Plays the next variation.
=======
         * spielt den sound ab
>>>>>>> a0ef24cbe7d54a69208c022107a762bfe78b848b
         */
        public void play() {
            soundVariations[soundVariationCounter%soundVariations.length].start();
            soundVariationCounter++;
        }

        /**
<<<<<<< HEAD
         * Release.
         * Needs to be called to properly release
=======
         * stopt den soundplayer
>>>>>>> a0ef24cbe7d54a69208c022107a762bfe78b848b
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
