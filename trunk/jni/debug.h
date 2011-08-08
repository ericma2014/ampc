#ifndef DEBUG_H
#define DEBUG_H 1

#include <android/log.h>

#define LOG(prio, fmt, args...) __android_log_print (prio, LOG_TAG, "%s (%d): " fmt, __FUNCTION__, __LINE__, ## args)

#define LOGD(fmt, args...) LOG (ANDROID_LOG_DEBUG, fmt, ##args)
#define LOGW(fmt, args...) LOG (ANDROID_LOG_WARN,  fmt, ##args)
#define LOGE(fmt, args...) LOG (ANDROID_LOG_ERROR, fmt, ##args)


#endif /* DEBUG_H */
