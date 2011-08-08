#ifndef _MPDCONTROL_H_
#define _MDPCONTROL_H_ 1

#include <jni.h>
#include <mpd/connection.h>

struct native_data_t {
	JavaVM *vm; // not needed yet but may be useful later
	struct mpd_connection *conn;
};


#endif /* _MPDCONTROL_H_ */
