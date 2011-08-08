package ru.byss.ampc;

class MPDStatus {
	public static final int MPD_STATE_UNKNOWN = 0;
	public static final int MPD_STATE_STOP    = 1;
	public static final int MPD_STATE_PLAY    = 2;
	public static final int MPD_STATE_PAUSE   = 3;

	// TODO: hide constructor!
	public MPDStatus (int ptr) {
		nativeData = ptr;
	}
	
	protected void finalize () {
		finalizeNativeDataNative ();
	}
	
	public int volume () {
		return volumeNative ();
	}
	
	public boolean repeat () {
		return repeatNative ();
	}
	
	public boolean random () {
		return randomNative ();
	}
	
	public boolean single () {
		return singleNative ();
	}
	
	public boolean consume () {
		return consumeNative ();
	}
	
	public int queueLength () {
		return queueLengthNative ();
	}
	
	public int queueVersion () {
		return queueVersionNative ();
	}
	
	public int state () {
		return stateNative ();
	}
	
	public int crossfade () {
		return crossfadeNative ();
	}
	
	public int songPos () {
		return songPosNative ();
	}
	
	public int songId () {
		return songIdNative ();
	}
	
	public int elapsedTime () {
		return elapsedTimeNative ();
	}
	
	public int elapsedMs () {
		return elapsedMsNative ();
	}
	
	public int totalTime () {
		return totalTimeNative ();
	}
	
	static {
		System.loadLibrary ("ampc");
		classInitNative ();
	}
	
	private final int nativeData;
	
	private native static void classInitNative ();
	private native void finalizeNativeDataNative ();
	
	private native int     volumeNative       ();
	private native boolean repeatNative       ();
	private native boolean randomNative       ();
	private native boolean singleNative       ();
	private native boolean consumeNative      ();
	private native int     queueLengthNative  ();
	private native int     queueVersionNative ();
	private native int     stateNative        ();
	private native int     crossfadeNative    ();
	private native int     songPosNative      ();
	private native int     songIdNative       ();
	private native int     elapsedTimeNative  ();
	private native int     elapsedMsNative    ();
	private native int     totalTimeNative    ();
}
