#ifndef _MPDCONTROL_H_
#define _MDPCONTROL_H_ 1

#include <jni.h>
#include <android/log.h>

#include <mpd/connection.h>

#define LOG(prio, fmt, args...) __android_log_print (prio, LOG_TAG, "%s (%d): " fmt, __FUNCTION__, __LINE__, ## args)

#define LOGD(fmt, args...) LOG (ANDROID_LOG_DEBUG, fmt, ##args)
#define LOGW(fmt, args...) LOG (ANDROID_LOG_WARN,  fmt, ##args)
#define LOGE(fmt, args...) LOG (ANDROID_LOG_ERROR, fmt, ##args)

struct native_data_t {
	JavaVM *vm; // not needed yet but may be useful later
	struct mpd_connection *conn;
};


#endif /* _MPDCONTROL_H_ */
