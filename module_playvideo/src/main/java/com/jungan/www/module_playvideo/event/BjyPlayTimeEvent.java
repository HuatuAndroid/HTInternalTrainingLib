package com.jungan.www.module_playvideo.event;

import java.io.Serializable;

/**
 * Created by KaelLi on 2018/12/18.
 */
public class BjyPlayTimeEvent implements Serializable {
    public long playId;
    public String begin_time;   // 2018-12-16 19:53:01
    public String end_time;
    public int watch_time;
    public int abort_time;

    public BjyPlayTimeEvent(long playId, String begin_time, String end_time, int watch_time, int abort_time) {
        this.playId = playId;
        this.begin_time = begin_time;
        this.end_time = end_time;
        this.watch_time = watch_time;
        this.abort_time = abort_time;
    }

    @Override
    public String toString() {
        return "BjyPlayTimeEvent{" +
                "playId=" + playId +
                ", begin_time='" + begin_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", watch_time=" + watch_time +
                ", abort_time=" + abort_time +
                '}';
    }
}
