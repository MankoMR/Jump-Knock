package ch.band.jumpknock.game.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.annotation.RawRes;
/*
 *Copyright (c) 2020 Fredy Stalder, Manuel Koloska, All rights reserved.
 */
public class SoundContainer {

    private final String TAG = SoundContainer.class.getCanonicalName();
    
    private MediaPlayer[] soundVariations;
    private int soundVariationPosition = 0;


    /**
     * Instantiates a new Sound container.
     *
     * @param soundVariations    the sound variations
     * @param soundVolume        the sound volume
     * @param applicationContext the application context
     */
     public SoundContainer(@RawRes int[] soundVariations, float soundVolume, Context applicationContext){
         this.soundVariations = new MediaPlayer[soundVariations.length];

         for(int i = 0;i < soundVariations.length;i++){
            MediaPlayer player = MediaPlayer.create(applicationContext,soundVariations[i]);
            //On some devices the source file can't be read and initialization fails. It those Situation it returns null.
            //Therefore all access needs to be null checked.
            if (player == null){
               // Log.d(TAG,"Error during MediaPlayer creation disallows playing Sound");
                continue;
            }

            player.setVolume(soundVolume,soundVolume);
            player.setOnCompletionListener((mp)->{
                if(mp == null){
                    return;
                }
                mp.pause();
                mp.seekTo(0);
            });
            this.soundVariations[i] = player;
         }
     }

     /**
      * Play.
      * Plays the next variation.
      */
     public void play() {
        MediaPlayer player = soundVariations[soundVariationPosition %soundVariations.length];
        if (player != null){
            player.start();
        }
        soundVariationPosition++;
     }

     /**
      * Release.
      * Needs to be called to properly release
      */
     public void release(){
        for(MediaPlayer player:soundVariations){
            if(player == null) {
                continue;
            }
            player.stop();
            player.setOnCompletionListener(null);
            player.release();
        }
     }
}
