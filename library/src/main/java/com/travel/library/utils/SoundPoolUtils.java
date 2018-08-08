package com.travel.library.utils;

import android.media.AudioManager;
import android.media.SoundPool;

import com.travel.library.helper.AudioManagerHelper;

import java.util.HashMap;

/**
 * Created by Wisn on 2018/5/29 上午11:42.
 */
public class SoundPoolUtils {
    private static SoundPoolUtils SoundPoolUtils;
    private static SoundPool mSoundPool;
    private static HashMap<Integer, Integer> soundPoolMap;

    public static SoundPoolUtils getInstance() {
        if (SoundPoolUtils == null) {
            synchronized (SoundPoolUtils.class) {
                if (SoundPoolUtils == null) {
                    SoundPoolUtils = new SoundPoolUtils();
                }
            }
        }
        return SoundPoolUtils;
    }

    public SoundPoolUtils() {
        mSoundPool = new SoundPool(1, AudioManager.STREAM_RING, 5);
        soundPoolMap = new HashMap<>();
    }

    /**
     * priority —— 流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理；
     * loop —— 循环播放的次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次（例如，3为一共播放4次）.
     * rate —— 播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
     *
     * @param rawId
     * @param volume
     * @param loop
     *
     * @return
     */
    public SoundPool play(int rawId, final float volume, final int loop) {
        try {
            Integer id = soundPoolMap.get(rawId);
            if (id == null || id == 0) {
                id = mSoundPool.load(Utils.getApp().getApplicationContext(), rawId, 1);
                soundPoolMap.put(rawId, id);
                mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        mSoundPool.play(sampleId, volume, volume, 100, loop, 1);
                    }
                });
                return mSoundPool;
            }
            mSoundPool.play(id, volume, volume, 100, loop, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mSoundPool;
    }

    public SoundPool play(int rawId) {
        return play(rawId, 1, 0);
    }

    public SoundPool play(int rawId, float volume) {
        return play(rawId, volume, 0);
    }

    public void stop(int rawId) {
        try {
            Integer id = soundPoolMap.get(rawId);
            if (id == null || id == 0) {
                id = mSoundPool.load(Utils.getApp().getApplicationContext(), rawId, 1);
                soundPoolMap.put(rawId, id);
            }
            mSoundPool.stop(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
