package com.jit.lib.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.util.SparseIntArray;

/**
 * 
 * 
 * FileName: SoundManager.java
 * Description：播放声音的工具类
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class SoundManager {

	private static SoundManager soundManager;
	private static Vibrator vibrator;
	private Context context;
	private SoundPool soundPool;
	private SparseIntArray soundPoolMap;
	public static final int SHOOT = 1;

	private SoundManager(Context context) {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new SparseIntArray();
		vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		this.context = context;
	}

	public static SoundManager newInstance(Context context) {
		if (soundManager == null) {
			soundManager = new SoundManager(context);
		}
		return soundManager;
	}

	public void playSound(int type) {
		AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;
		soundPoolMap.put(SHOOT, soundPool.load(context, type, 1));
		soundPool.play(soundPoolMap.get(type), volume, volume, 1, 0, 1f);
		vibrator.vibrate(new long[] { 100, 10, 10, 100 }, -1);
	}
}
