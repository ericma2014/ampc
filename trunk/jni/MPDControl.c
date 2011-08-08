#include "MPDControl.h"

#include <stdlib.h>

#include <mpd/player.h>
#include <mpd/connection.h>

#define LOG_TAG "MPDControl"

static jfieldID field_nativeData;

static inline struct native_data_t *get_native_data (JNIEnv *env, jobject object) {
	LOGD (">>>");

	struct native_data_t *nat =	(struct native_data_t *)((*env)->GetIntField (env, object, field_nativeData));
	if (!nat) {
		LOGE ("Uninitialized native data\n");
	}

	LOGD ("<<<");
	return nat;
}

void Java_ru_byss_ampc_MPDControl_classInitNative (JNIEnv* env, jclass clazz) {
	field_nativeData = (*env)->GetFieldID (env, clazz, "nativeData", "I");
}

jboolean Java_ru_byss_ampc_MPDControl_initializeNativeDataNative (JNIEnv *env, jobject thiz) {
	LOGD (">>>");
	struct native_data_t *nat =	(struct native_data_t *) malloc (sizeof (struct native_data_t));
	(*env)->SetIntField (env, thiz, field_nativeData, (jint) nat);
	
	if (!nat) {
		LOGE ("Malloc failed!");
		LOGD ("<<<");
		return JNI_FALSE;
	}
	nat->conn = NULL;
	
	LOGD ("Native data init OK!");
	
	LOGD ("<<<");
	return JNI_TRUE;
}

void Java_ru_byss_ampc_MPDControl_finalizeNativeDataNative (JNIEnv* env, jobject thiz) {
	LOGD (">>>");
	struct native_data_t *nat = get_native_data (env, thiz);
	
	if (nat) {
		if (nat->conn) {
			mpd_connection_free (nat->conn);
		}
		free (nat);
	}

	LOGD ("<<<");
}

jboolean Java_ru_byss_ampc_MPDControl_connectNative (JNIEnv* env, jobject thiz, jstring host, jint port, jint timeout) {
	LOGD (">>>");
	struct native_data_t *nat = get_native_data (env, thiz);
	
	const char *c_host = (*env)->GetStringUTFChars (env, host, NULL);
	if (!c_host) {
		LOGE ("Out of memory!");
		LOGD ("<<<");
		return JNI_FALSE;
	}
	
	LOGD ("Trying to connect (%s:%d, tout = %d)", c_host, port, timeout);
	
	nat->conn = mpd_connection_new (c_host, port, timeout);

	(*env)->ReleaseStringUTFChars (env, host, c_host);

	if (!nat->conn) {
		LOGE ("Out of memory!");
		LOGD ("<<<");
		return JNI_FALSE;
	}

	if (mpd_connection_get_error (nat->conn) != MPD_ERROR_SUCCESS) {
		LOGE ("Cannot connect, error message: %s", mpd_connection_get_error_message (nat->conn));
		LOGD ("<<<");
		return JNI_FALSE;
	}

	LOGD ("Connect OK!");
	
	LOGD ("<<<");
	return JNI_TRUE;
}

jboolean Java_ru_byss_ampc_MPDControl_connectedNative (JNIEnv* env, jobject thiz) {
	LOGD (">>>");
	struct native_data_t *nat = get_native_data (env, thiz);
	
	if (nat->conn) {
		LOGD ("<<<");
		return JNI_TRUE;
	} else {
		LOGD ("<<<");
		return JNI_FALSE;
	}
}

void Java_ru_byss_ampc_MPDControl_disconnectNative (JNIEnv* env, jobject thiz) {
	LOGD (">>>");
	struct native_data_t *nat = get_native_data (env, thiz);
	
	mpd_connection_free (nat->conn);
	nat->conn = NULL;
	
	LOGD ("<<<");
}

jstring Java_ru_byss_ampc_MPDControl_getErrorMessageNative (JNIEnv* env, jobject thiz) {
	LOGD (">>>");
	struct native_data_t *nat = get_native_data (env, thiz);
	
	jstring ret;
	if (mpd_connection_get_error (nat->conn) != MPD_ERROR_SUCCESS) {
		ret = (*env)->NewStringUTF (env, mpd_connection_get_error_message (nat->conn));
	} else {
		ret = (*env)->NewStringUTF (env, "OK");
	}
	
	LOGD ("<<<");
	return ret;
}

#define check_conn \
	if (!nat->conn) { \
		LOGW ("Not connected!"); \
		LOGD ("<<<"); \
		return JNI_FALSE; \
	}

jboolean Java_ru_byss_ampc_MPDControl_sendPlayNative (JNIEnv* env, jobject thiz) {
	LOGD (">>>");
	struct native_data_t *nat = get_native_data (env, thiz);
	
	check_conn;
	
	if (mpd_run_play (nat->conn)) {
		LOGD ("<<<");
		return JNI_TRUE;
	} else {
		LOGD ("<<<");
		return JNI_FALSE;
	}
}

jboolean Java_ru_byss_ampc_MPDControl_sendPauseNative (JNIEnv* env, jobject thiz) {
	LOGD (">>>");
	struct native_data_t *nat = get_native_data (env, thiz);
	
	check_conn;
	
	if (mpd_send_pause (nat->conn, 1)) {
		LOGD ("<<<");
		return JNI_TRUE;
	} else {
		LOGD ("<<<");
		return JNI_FALSE;
	}
}

jboolean Java_ru_byss_ampc_MPDControl_sendStopNative (JNIEnv* env, jobject thiz) {
	LOGD (">>>");
	struct native_data_t *nat = get_native_data (env, thiz);
	
	check_conn;
	
	if (mpd_send_stop (nat->conn)) {
		LOGD ("<<<");
		return JNI_TRUE;
	} else {
		LOGD ("<<<");
		return JNI_FALSE;
	}
}

