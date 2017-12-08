package com.example.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;

import com.example.loginbytencent.R;

import java.util.HashMap;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    AppCompatAutoCompleteTextView etName;

    @BindView(R.id.et_password)
    AppCompatAutoCompleteTextView etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_loginByQQ, R.id.btn_loginByWx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                break;
            case R.id.btn_loginByQQ:
                loginQQ(QQ.NAME);
                break;
            case R.id.btn_loginByWx:
                loginQQ(Wechat.NAME);
                break;
        }
    }

    private void loginQQ(String typeName) {
        ShareSDK.initSDK(this);
        Platform weibo = ShareSDK.getPlatform(typeName);
        weibo.SSOSetting(false);  //设置false表示使用SSO授权方式
        //回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
        weibo.setPlatformActionListener(new PlatformActionListener() {

            @Override
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                // TODO Auto-generated method stub
                arg2.printStackTrace();
                etName.setText("错误信息"+arg2.getMessage());
            }

            @Override
            public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                // TODO Auto-generated method stub
                //输出所有授权信息
//                arg0.getDb().exportData();
                //采用Iterator遍历HashMap
                Iterator it = arg2.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    System.out.println("key:" + key);
                    System.out.println("value:" + arg2.get(key));
                }

            }

            @Override
            public void onCancel(Platform arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        //authorize与showUser单独调用一个即可
//        weibo.authorize();//单独授权,OnComplete返回的hashmap是空的
        weibo.showUser(null);//授权并获取用户信息
        //移除授权
        //weibo.removeAccount(true);
    }
}
