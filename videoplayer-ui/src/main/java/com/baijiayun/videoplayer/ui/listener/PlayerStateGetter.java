/*
 * Copyright 2017 jiajunhui<junhui_jia@163.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.baijiayun.videoplayer.ui.listener;


import android.support.annotation.Nullable;

import com.baijiayun.constant.MediaPlayerDebugInfo;
import com.baijiayun.videoplayer.bean.BJYVideoInfo;
import com.baijiayun.videoplayer.player.PlayerStatus;

/**
 * player state getter for Receivers.
 * <p>
 * Created by Taurus on 2018/6/8.
 */
public interface PlayerStateGetter {
    /**
     * 获取播放器状态
     *
     * @return 播放器状态
     */
    PlayerStatus getPlayerStatus();

    /**
     * get player current play progress.
     *
     * @return
     */
    int getCurrentPosition();

    /**
     * get video duration
     *
     * @return
     */
    int getDuration();

    /**
     * get player buffering percentage.
     *
     * @return
     */
    int getBufferPercentage();

    /**
     * 获取播放速率
     * @return
     */
    float getPlayRate();

    /**
     * 获取当前播放的视频信息，如果通过setupLocalVideoWithFilePath设置数据源则该方法返回null
     *
     * @return Video Info
     */
    @Nullable
    BJYVideoInfo getVideoInfo();

    MediaPlayerDebugInfo getMediaPlayerDebugInfo();
}
