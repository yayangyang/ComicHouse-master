package com.yayangyang.comichouse_master.base;

import android.content.Intent;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yayangyang.comichouse_master.Bean.user.Login;
import com.yayangyang.comichouse_master.Bean.user.TencentLoginResult;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ToastUtils;
import com.yayangyang.comichouse_master.view.LoginPopupWindow;

import org.json.JSONObject;

public abstract class BaseLoginRvActivity<T,K extends BaseViewHolder> extends BaseRVActivity<T,K>
        implements BaseLoginContract.View,LoginPopupWindow.LoginTypeListener{
    protected LoginPopupWindow popupWindow;
    protected BaseUIListener loginListener;

    protected boolean isLogin=false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR||
                requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class BaseUIListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            LogUtils.e("onComplete");
            JSONObject jsonObject = (JSONObject) o;
            String json = jsonObject.toString();
            Gson gson = new Gson();
            TencentLoginResult result = gson.fromJson(json, TencentLoginResult.class);
            LogUtils.e(result.toString());
            loginComicHouse(result);
        }

        @Override
        public void onError(UiError uiError) {
            ToastUtils.showToast("QQ授权出错");
        }

        @Override
        public void onCancel() {
            ToastUtils.showToast("QQ授权被取消");
            loginCancel();
        }
    }

    /**
     * qq操作取得数据后的回调(例:qq登录,qq分享)
     * @param result
     */
    protected abstract void loginComicHouse(TencentLoginResult result);

    /**
     * qq取消回调(可选择重写)
     */
    protected void loginCancel(){}

    /**
     * 追书神器登录成功的回调(有登录功能需要重写)
     * @param login
     */
    public void loginSuccess(Login login){}

    /**
     * 登录弹出框的回调(有实现LoginPopupWindow监听需要重写)
     * @param view
     * @param type
     */
    public void onLogin(ImageView view, String type){}

}
