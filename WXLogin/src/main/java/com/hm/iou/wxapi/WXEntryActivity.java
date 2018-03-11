package com.hm.iou.wxapi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hm.iou.MainActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.iwxapi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.e("WXTest", "onResp OK");

                if (baseResp instanceof SendAuth.Resp) {
                    SendAuth.Resp newResp = (SendAuth.Resp) baseResp;
                    //获取微信传回的code
                    String code = newResp.code;
                    String state = newResp.state;
                    String lang = newResp.lang;
                    String contry = newResp.country;
                    Log.e("WXTest", "onResp code = " + code);
                    Log.e("WXTest", "onResp state = " + state);
                    Log.e("WXTest", "onResp lang = " + lang);
                    Log.e("WXTest", "onResp contry = " + contry);
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.e("WXTest", "onResp ERR_USER_CANCEL ");
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.e("WXTest", "onResp ERR_AUTH_DENIED");
                //发送被拒绝
                break;
            default:
                Log.e("WXTest", "onResp default errCode " + baseResp.errCode);
                //发送返回
                break;
        }
        finish();
    }
}
