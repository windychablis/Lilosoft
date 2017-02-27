package com.chablis.lilosoft.utils;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPoolUtil {

	public static int streamID;
	  private Context context;
	  private SoundPool soundPool;
	  private HashMap<Integer, Integer> soundPoolMap;
	  int streamVolume;

	  public SoundPoolUtil(Context paramContext)
	  {
	    this.context = paramContext;
	    initSounds();
	  }

	  public void initSounds()
	  {
		this.soundPool = new SoundPool(100, 3, 100);
	    this.soundPoolMap = new HashMap();
	    this.streamVolume = ((AudioManager)this.context.getSystemService("audio")).getStreamVolume(3);
	  }
	  
	  public void loadSfx(int paramInt1, int paramInt2)
	  {
	    this.soundPoolMap.put(Integer.valueOf(paramInt2), Integer.valueOf(this.soundPool.load(this.context, paramInt1, paramInt2)));
	  }
	  
	  public void play(int paramInt1, int paramInt2)
	  {
	    stop();
	    streamID = this.soundPool.play(((Integer)this.soundPoolMap.get(Integer.valueOf(paramInt1))).intValue(), this.streamVolume, this.streamVolume, 1, paramInt2, 1.0F);
	  }
	
	  public void stop()
	  {
	    try
	    {
	      this.soundPool.stop(streamID);
	      return;
	    }
	    catch (Exception localException)
	    {
	      localException.printStackTrace();
	    }
	  }
}
