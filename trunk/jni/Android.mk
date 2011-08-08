LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := libampc
LOCAL_CFLAGS += -std=c99
LOCAL_C_INCLUDES := jni/libmpdclient/include \
                    jni/libmpdclient/src
LOCAL_LDLIBS := -llog
LOCAL_SRC_FILES := \
MPDControl.c \
MPDStatus.c \
libmpdclient/src/async.c \
libmpdclient/src/capabilities.c \
libmpdclient/src/cmessage.c \
libmpdclient/src/connection.c \
libmpdclient/src/coutput.c \
libmpdclient/src/cplaylist.c \
libmpdclient/src/cstats.c \
libmpdclient/src/cstatus.c \
libmpdclient/src/database.c \
libmpdclient/src/directory.c \
libmpdclient/src/entity.c \
libmpdclient/src/error.c \
libmpdclient/src/example.c \
libmpdclient/src/fd_util.c \
libmpdclient/src/idle.c \
libmpdclient/src/ierror.c \
libmpdclient/src/iso8601.c \
libmpdclient/src/list.c \
libmpdclient/src/message.c \
libmpdclient/src/mixer.c \
libmpdclient/src/output.c \
libmpdclient/src/parser.c \
libmpdclient/src/password.c \
libmpdclient/src/player.c \
libmpdclient/src/playlist.c \
libmpdclient/src/queue.c \
libmpdclient/src/quote.c \
libmpdclient/src/rdirectory.c \
libmpdclient/src/recv.c \
libmpdclient/src/resolver.c \
libmpdclient/src/response.c \
libmpdclient/src/rplaylist.c \
libmpdclient/src/run.c \
libmpdclient/src/search.c \
libmpdclient/src/send.c \
libmpdclient/src/settings.c \
libmpdclient/src/socket.c \
libmpdclient/src/song.c \
libmpdclient/src/stats.c \
libmpdclient/src/status.c \
libmpdclient/src/sticker.c \
libmpdclient/src/sync.c \
libmpdclient/src/tag.c

include $(BUILD_SHARED_LIBRARY)
