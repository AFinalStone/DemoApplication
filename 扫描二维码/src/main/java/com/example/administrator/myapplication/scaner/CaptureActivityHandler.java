package com.example.administrator.myapplication.scaner;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.administrator.myapplication.MainActivity;
import com.example.administrator.myapplication.R;
import com.google.zxing.Result;

import static android.content.ContentValues.TAG;


/**
 * Author: Vondear
 * 描述: 扫描消息转发
 */
public final class CaptureActivityHandler extends Handler {

    DecodeThread decodeThread = null;
    Activity activity = null;
    private State state;

    public CaptureActivityHandler(Activity activity) {
        this.activity = activity;
        decodeThread = new DecodeThread(activity);
        decodeThread.start();
        state = State.SUCCESS;
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    @Override
    public void handleMessage(Message message) {
        if (message.what == R.id.auto_focus) {
            if (state == State.PREVIEW) {
                CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
            }
        } else if (message.what == R.id.restart_preview) {
            restartPreviewAndDecode();
        } else if (message.what == R.id.decode_succeeded) {
            state = State.SUCCESS;
            ((MainActivity)activity).handleDecode((Result) message.obj);// 解析成功，回调
        } else if (message.what == R.id.decode_failed) {
            state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
        }
        Log.e(TAG, "handleMessage: 消息消息");
    }

    public void quitSynchronously() {
        state = State.DONE;
        CameraManager.get().stopPreview();
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
        removeMessages(R.id.decode);
        removeMessages(R.id.auto_focus);
    }

    private void restartPreviewAndDecode() {
        if (state == State.SUCCESS) {
            state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(decodeThread.getHandler(), R.id.decode);
            CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
        }
    }

    private enum State {
        PREVIEW, SUCCESS, DONE
    }

}
