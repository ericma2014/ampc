package ru.byss.ampc;

class MPDControl {
	public MPDControl () {
		initializeNativeDataNative ();
	}
	
	protected void finalize () {
		finalizeNativeDataNative ();
	}
	
	public boolean initOK () {
		return (nativeData != 0);
	}
	
	public boolean connect (String host, int port, int timeout) {
		return connectNative (host, port, timeout);
	}
	
	public boolean connect (String host, int port) {
		return connect (host, port, 0);
	}
	
	public boolean connect (String host) {
		return connect (host, 6600, 0);
	}
	
	public boolean connected () {
		return connectedNative ();
	}
	
	public String getErrorMessage () {
		return getErrorMessageNative ();
	}
	
	public boolean sendPlay () {
		return sendPlayNative ();
	}
	
	public boolean sendPause () {
		return sendPauseNative ();
	}
	
	public boolean sendStop () {
		return sendStopNative ();
	}
	
	static {
		System.loadLibrary ("ampc");
		classInitNative ();
	}
	
	private int nativeData = 0;
	
	private native static void classInitNative ();
	private native boolean initializeNativeDataNative ();
	private native boolean finalizeNativeDataNative ();

	private native String getErrorMessageNative ();
	
	private native boolean connectNative (String host, int port, int timeout);
	private native boolean connectedNative ();
	private native boolean disconnectNative ();

	private native boolean sendPlayNative ();
	private native boolean sendPauseNative ();
	private native boolean sendStopNative ();
};
