/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chenpan.commoner.playmusic;

public class MusicPlayMode {

	public static final int MPM_SINGLE_LOOP_PLAY = 0; // 单曲循环

	public static final int MPM_ORDER_PLAY = 1; // 顺序播放

	public static final int MPM_LIST_LOOP_PLAY = 2; // 列表循环

	public static final int MPM_RANDOM_PLAY = 3; // 随即播放


	public static final String ACTION_MEDIA_PLAY_PAUSE = "me.wcy.music.ACTION_MEDIA_PLAY_PAUSE";
	public static final String ACTION_MEDIA_NEXT = "me.wcy.music.ACTION_MEDIA_NEXT";
	public static final String ACTION_MEDIA_PREVIOUS = "me.wcy.music.ACTION_MEDIA_PREVIOUS";
	public static final String VOLUME_CHANGED_ACTION = "android.media.VOLUME_CHANGED_ACTION";

}
