package ch.band.jumpknock.game.sound;

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
}
