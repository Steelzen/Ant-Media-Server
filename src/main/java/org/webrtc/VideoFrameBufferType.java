
// Copyright 2023 The Chromium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

// This file is autogenerated by
//     java_cpp_enum.py
// From
//     ../../api/video/video_frame_buffer.h

package org.webrtc;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface VideoFrameBufferType {
  int NATIVE = 0;
  int I420 = 1;
  int I420A = 2;
  int I422 = 3;
  int I444 = 4;
  int I010 = 5;
  int I210 = 6;
  int NV12 = 7;
}
