package com.example.tantanauto.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.Rect;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.Toast;

import com.example.tantanauto.R;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by hp on 2017/5/15.
 */

public class MyAutoService extends AccessibilityService {
    private final static String TAG = "MyAutoService";
    private final static String PACKAGE_NAME_TANTAN = "com.p1.mobile.putong";
    private AccessibilityNodeInfo accessibilityNodeInfo;
    private AccessibilityNodeInfo mLoveButton;

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isPerformed = accessibilityNodeInfo.performAction(GESTURE_SWIPE_RIGHT);
            Log.d(TAG, "isPeformed :" + isPerformed);
        }
    };

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        if (PACKAGE_NAME_TANTAN.equals(packageName)) {
            Log.d(TAG, event.getPackageName().toString() + event.getEventType());
            switch (event.getEventType()) {
                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    AccessibilityNodeInfo mNodeInfo = getRootInActiveWindow();
                    Rect rect = new Rect();
                    mNodeInfo.getBoundsInScreen(rect);
                    boolean isPerformed = mNodeInfo.performAction(GESTURE_SWIPE_RIGHT);
                    accessibilityNodeInfo = mNodeInfo;
                    traversalNodeInfo(mNodeInfo);
                    performLoveButtonClick(mLoveButton);
//                    performSwipeRight(mNodeInfo);
                    Log.d(TAG, "child counts is :" + mNodeInfo.getChildCount() + "\nrect is :" + rect.toString() + "\n performed is :" + isPerformed);
                    break;
            }
        }
    }

    private void performLoveButtonClick(final AccessibilityNodeInfo mLoveButton) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        boolean isClicked = mLoveButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        Log.d(TAG, "love Button is clicked = " + isClicked);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void traversalNodeInfo(AccessibilityNodeInfo mNodeInfo) {
        int mChildCount = mNodeInfo.getChildCount();
        Log.d(TAG, "class name:" + mNodeInfo.getClassName().toString() + "  child count is :" + mChildCount + "\n");
        mLoveButton = mNodeInfo;
        if (mChildCount > 0) {
            for (int i = 0; i < mChildCount; i++) {
                traversalNodeInfo(mNodeInfo.getChild(i));
            }
        }
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, getResources().getString(R.string.service_interuppted), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(this, getResources().getString(R.string.service_connected), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected boolean onGesture(int gestureId) {
        return super.onGesture(gestureId);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return super.onKeyEvent(event);
    }

    @Override
    public List<AccessibilityWindowInfo> getWindows() {
        return super.getWindows();
    }

    @Override
    public AccessibilityNodeInfo getRootInActiveWindow() {
        return super.getRootInActiveWindow();
    }

    @Override
    public AccessibilityNodeInfo findFocus(int focus) {
        return super.findFocus(focus);
    }

    private void performSwipeRight(final AccessibilityNodeInfo accessibilityNodeInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        mHandler.sendEmptyMessage(0);
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

}
