#include "debug.h"

#include <jni.h>

#include <mpd/status.h>

#define LOG_TAG "MPDStatus-native"

static jfieldID field_nativeData;

static inline struct mpd_status *get_native_data (JNIEnv *env, jobject object) {
	LOGD (">>>");

	struct mpd_status *nat =	(struct mpd_status *)((*env)->GetIntField (env, object, field_nativeData));
	if (!nat) {
		LOGW ("Uninitialized native data\n");
	}

	LOGD ("<<<");
	return nat;
}

void Java_ru_byss_ampc_MPDStatus_classInitNative (JNIEnv* env, jclass clazz) {
	field_nativeData = (*env)->GetFieldID (env, clazz, "nativeData", "I");
}

void Java_ru_byss_ampc_MPDStatus_finalizeNativeDataNative (JNIEnv* env, jobject thiz) {
	LOGD (">>>");
	struct mpd_status *nat = get_native_data (env, thiz);
	
	if (nat) {
		mpd_status_free (nat);
	}

	LOGD ("<<<");
}

#define get_property(type, name, jname) \
type Java_ru_byss_ampc_MPDStatus_ ## jname ## Native (JNIEnv* env, jobject thiz) { \
	LOGD (">>>"); \
	struct mpd_status *nat = get_native_data (env, thiz); \
	\
	type ret = mpd_status_get_ ## name (nat); \
	\
	LOGD ("<<<"); \
	return ret; \
}

get_property (jint,     volume,        volume      )
get_property (jboolean, repeat,        repeat      )
get_property (jboolean, random,        random      )
get_property (jboolean, single,        single      )
get_property (jboolean, consume,       consume     )
get_property (jint,     queue_length,  queueLength )
get_property (jint,     queue_version, queueVersion)
get_property (jint,     state,         state       )
get_property (jint,     crossfade,     crossfade   )
get_property (jint,     song_pos,      songPos     )
get_property (jint,     song_id,       songId      )
get_property (jint,     elapsed_time,  elapsedTime )
get_property (jint,     elapsed_ms,    elapsedMs   )
get_property (jint,     total_time,    totalTime   )

jstring Java_ru_byss_ampc_MPDStatus_getErrorNative (JNIEnv* env, jobject thiz) {
	struct mpd_status *nat = get_native_data (env, thiz);
	
	const char *error = mpd_status_get_error (nat);
	return (*env)->NewStringUTF (env, error);
}

