package com.yayangyang.comichouse_master.ui.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.yayangyang.comichouse_master.Bean.ComicDetailBody;
import com.yayangyang.comichouse_master.Bean.ComicReview;
import com.yayangyang.comichouse_master.Bean.UploadImageResult;
import com.yayangyang.comichouse_master.R;
import com.yayangyang.comichouse_master.app.ComicApplication;
import com.yayangyang.comichouse_master.base.BaseActivity;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.component.AppComponent;
import com.yayangyang.comichouse_master.component.DaggerComicComponent;
import com.yayangyang.comichouse_master.ui.contract.PublishReviewContract;
import com.yayangyang.comichouse_master.ui.presenter.PublishReviewActivityPresenter;
import com.yayangyang.comichouse_master.utils.BitmapUtil;
import com.yayangyang.comichouse_master.utils.FileUtils;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.NetworkUtils;
import com.yayangyang.comichouse_master.utils.OpenAppUtil;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/12/16.
 */

public class PublishReviewActivity extends BaseActivity
        implements PublishReviewContract.View,View.OnClickListener{

    private int index=0,compressSuccessCount=0,screenHeight,keyHeight;
    private String objectId;

    private String imagePathZ[]={"","",""};

    private Map<String,String> params;
    private Map<String, RequestBody> imageMap;

    private List<String> mFileNameList;
//    private List<File> mFiles;
    private List<File> mRealFiles;
    private List<File> mUploadFile;

    private Bitmap mBitmap;

    private CustomPopWindow popWindow;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.tv_content_size)
    TextView tv_content_size;
    @BindViews({R.id.iv_add_image01,R.id.iv_add_image02,R.id.iv_add_image03})
    ImageView imgz[];
    @BindViews({R.id.iv_del01,R.id.iv_del02,R.id.iv_del03})
    ImageView imgdelz[];

    @Inject
    PublishReviewActivityPresenter mPresenter;

    public static void startActivity(Context context,String objectId){
        Intent intent = new Intent(context, PublishReviewActivity.class);
        intent.putExtra(Constant.OBJECT_ID,objectId);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_add_image01|| v.getId()==R.id.iv_add_image02||
                v.getId()==R.id.iv_add_image03){
            if(popWindow==null){
                createPopupWindow();
            }
            //设置遮罩
            WindowManager.LayoutParams lp =getWindow().getAttributes();
            lp.alpha = 0.5f;
            getWindow().setAttributes(lp);

            popWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
        }else if(v.getId()==R.id.iv_del01|| v.getId()==R.id.iv_del02||
                v.getId()==R.id.iv_del03){
            deleteImage(v);
        }else if(v.getId()==R.id.bt_take_a_picture){
            String path = OpenAppUtil.openCamera(this);
            imagePathZ[index]=path;
            popWindow.dissmiss();
        }else if(v.getId()==R.id.bt_select_from_photo_album){
            OpenAppUtil.openAlbum(this);
            popWindow.dissmiss();
        }else if(v.getId()==R.id.bt_cancel){
            popWindow.dissmiss();
        }
    }

    @OnClick(R.id.tv_send)
    public void tv_send(){
        if(et_content.getText().toString().length()!=0){
            getDialog().show();
            getData();
            LogUtils.e("getWidth:"+imgz[0].getWidth());
            LogUtils.e("getHeight:"+imgz[0].getHeight());
            if(mRealFiles.size()>0){
                yasuo();
//                mPresenter.uploadImage(imageMap);
            }else{
                getReviewData();
                mPresenter.publishReview(params);
            }
        }else{
            ToastUtils.showToast("请输入评论内容!");
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_review;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerComicComponent.builder()
                .appComponent(appComponent)
                .build().inject(this);
    }

    @Override
    public void initToolBar() {
        mCommonToolbar.setTitle("");
        mCommonToolbar.setNavigationIcon(R.drawable.img_back);
    }

    @Override
    public void initDatas() {
        mPresenter.attachView(this);

        Intent intent = getIntent();
        objectId = intent.getStringExtra(Constant.OBJECT_ID);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_new_comment_add_image);
        for(int i=0;i<imgz.length;i++){
            imgz[i].setImageBitmap(mBitmap);
        }
    }

    @Override
    public void configViews() {
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        keyHeight = screenHeight / 3;

        findViewById(android.R.id.content).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                LogUtils.e("keyHeight:"+keyHeight);
                LogUtils.e("oldBottom:"+oldBottom);
                LogUtils.e("bottom:"+bottom);
                LogUtils.e("oldBottom - bottom:"+(oldBottom - bottom));
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT);
//                    scrollView.setLayoutParams(params);
                    LogUtils.e("键盘弹起");
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT);
//                    scrollView.setLayoutParams(params);
                    LogUtils.e("键盘落下");
                }
            }
        });

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtils.e("beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtils.e("onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.e("afterTextChanged");
                tv_content_size.setText(1000-s.length()+"/1000");
            }
        });

        for(int i=0;i<imgz.length;i++){
            imgz[i].setOnClickListener(this);
            imgdelz[i].setOnClickListener(this);
        }
    }

    @Override
    public void showError() {
        getDialog().dismiss();
        if(!NetworkUtils.isAvailable(this)){
            ToastUtils.showToast("网络异常");
        }
    }

    @Override
    public void complete() {

    }

    @Override
    public void publishReviewResult(ComicReview review) {
        if(review.result.equals("1000")){
            ComicDetailBody comicDetailBody = new ComicDetailBody();
            comicDetailBody.id=review.data.id;
            comicDetailBody.obj_id=review.data.obj_id;
            comicDetailBody.content=review.data.content;
            comicDetailBody.sender_uid=review.data.sender_uid;
            comicDetailBody.like_amount=review.data.like_amount;
            comicDetailBody.create_time=review.data.create_time;
            comicDetailBody.to_uid=review.data.to_uid;
            comicDetailBody.to_comment_id=review.data.to_comment_id;
            comicDetailBody.origin_comment_id=review.data.origin_comment_id;
//            comicDetailBody.masterComment=review.data.masterComment;//发表评论时回复为0,不用赋值
            comicDetailBody.masterCommentNum=review.data.masterCommentNum;
            comicDetailBody.upload_images=review.data.upload_images;
            setResult(Constant.RETURN_DATA,new Intent().putExtra(Constant.OBJECT_ID,comicDetailBody));
            finish();
        }
        LogUtils.e("msg:"+review.msg);
        ToastUtils.showToast(review.msg);

        getDialog().dismiss();
    }

    @Override
    public void uploadImageResult(UploadImageResult result) {
        LogUtils.e("uploadImageResult:"+result.data.toString());
        if(result.status.equals("200")){
            //上传图片完成提交评论
            getReviewData();
            ArrayList arrayList= (ArrayList) result.data;
            params.put("img",arrayList.toString());//上传图片才有这个参数
            mPresenter.publishReview(params);

            ToastUtils.showToast("上传图片成功"+result.status);
        }else{
            ToastUtils.showToast("上传图片失败"+result.status);
            finish();
        }
    }

    @Override
    public void compressImageResult(List<File> list) {
        ToastUtils.showToast("压缩成功");
        mUploadFile=list;
        getImageData();
        mPresenter.uploadImage(imageMap);
    }

    public void getReviewData(){
        params=new HashMap<>();
        params.put("obj_id",objectId);
        params.put("to_comment_id","0");
        params.put("to_uid","0");
        params.put("sender_terminal","1");
        params.put("origin_comment_id","0");
        params.put("sender_uid", ComicApplication.sLogin.data.uid);//105670846(小明)
        params.put("type","4");
        params.put("content",et_content.getText().toString());
    }

    public void getImageData(){
        imageMap = new HashMap<>();
        for (int i = 0; i < mRealFiles.size(); i++) {
            LogUtils.e("mRealFiles.get(i).getName():"+mUploadFile.get(i).getName());

            RequestBody requestBody = RequestBody
                    .create(MediaType.parse("application/octet-stream; charset=utf-8"), mUploadFile.get(i));
            imageMap.put("userfile[]"+"\";filename=\"file.pngmimeType=\"image/png", requestBody);
        }
    }

    public void getData() {
//        List<Integer> randomNummberList=new ArrayList<>();

        ArrayList<String> upLoadImageList=new ArrayList<>();
        mRealFiles=new ArrayList<>();
        for(int i=0;i<imgz.length;i++){
            BitmapDrawable drawable = (BitmapDrawable) imgz[i].getDrawable();
            if(drawable.getBitmap()!=mBitmap){
                upLoadImageList.add(imagePathZ[i]);

                File srcDir  = new File(imagePathZ[i]);
                mRealFiles.add(srcDir);
            }
        }
        LogUtils.e("upLoadImageList.size()"+upLoadImageList.size());
    }

    private void createPopupWindow() {
        View contentView = View.inflate(mContext, R.layout.view_bottom_select_image_popupwindow, null);
        contentView.findViewById(R.id.bt_take_a_picture).setOnClickListener(this);
        contentView.findViewById(R.id.bt_select_from_photo_album).setOnClickListener(this);
        contentView.findViewById(R.id.bt_cancel).setOnClickListener(this);

        popWindow = new CustomPopWindow.PopupWindowBuilder(mContext)
                .setView(contentView)//显示的布局，还可以通过设置一个View
                .size(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0f)
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //取消遮罩
//                        WindowManager.LayoutParams lp =getWindow().getAttributes();
//                        lp.alpha = 1.0f;
//                        getWindow().setAttributes(lp);
                    }
                })
                .create();//创建PopupWindow
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        LogUtils.e("onActivityResult-requestCode:"+requestCode);
        Bitmap bitmap=null;
        String path="";
        if(requestCode==Constant.TAKE_PHOTO_WITH_DATA){
//            bitmap = intent.getParcelableExtra("data");
//            if(bitmap!=null) ToastUtils.showToast("不为空");

            if(intent==null||intent.getData()==null){
                LogUtils.e("intent:"+intent);
                LogUtils.e("path:"+imagePathZ[index]);
                bitmap=BitmapUtil.getimage(imagePathZ[index],
                        imgz[index].getWidth(),imgz[index].getHeight());
            }
        }else if(requestCode==Constant.GET_PHOTO_WITH_DATA){
            //外界的程序访问ContentProvider所提供数据,可以通过ContentResolver接口
            if(intent==null) return;
            ContentResolver resolver = getContentResolver();
            Uri originalUri = intent.getData();//照片的原始资源地址
            String[] proj = {MediaStore.Images.Media.DATA};// 获取照片路径
            // 好像是Android多媒体数据库的封装接口,具体的看Android文档
            Cursor cursor = managedQuery(originalUri, proj, null, null, null);
            // 按我个人理解,这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();// 将光标移至开头,这个很重要,不小心很容易引起越界
            path = cursor.getString(column_index);// 最后根据索引值获取图片路径
            Log.e("path",">>>>>>>>>>...."+path);
            try {
                bitmap=  MediaStore.Images.Media.getBitmap(resolver, originalUri);
                LogUtils.e("bitmap:"+bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(bitmap==null) return;
        if(!TextUtils.isEmpty(path)) imagePathZ[index]=path;
        imgdelz[index].setVisibility(View.VISIBLE);
        imgz[index].setImageBitmap(bitmap);
        imgz[index].setScaleType(ImageView.ScaleType.CENTER_CROP);
//        GlideApp.with(imgz[index]).load(bitmap)
//                .centerCrop()
//                .into(imgz[index]);
        imgz[index].setEnabled(false);
        index++;
        if(index<3) imgz[index].setVisibility(View.VISIBLE);
        super.onActivityResult(requestCode, resultCode, intent);
    }

    private void yasuo() {
        if(!new File(Constant.PATH_UP_LOAD_PICTURES).exists()){
            FileUtils.createDir(Constant.PATH_UP_LOAD_PICTURES);
        }
        mPresenter.compressImage(mRealFiles,150,Constant.PATH_UP_LOAD_PICTURES,
                imgz[0].getWidth(),imgz[0].getHeight());
    }

    private void deleteImage(View v) {
        int myIndex=0;
        if(v.getId()==R.id.iv_del01) myIndex=0;
        if(v.getId()==R.id.iv_del02) myIndex=1;
        if(v.getId()==R.id.iv_del03) myIndex=2;
        imagePathZ[myIndex]="";
        BitmapDrawable drawable = (BitmapDrawable)imgz[myIndex].getDrawable();
        drawable.getBitmap().recycle();
        for(int i=myIndex;i<imgdelz.length-1;i++){
            imagePathZ[i]=imagePathZ[i+1];
            imgz[i].setImageDrawable(imgz[i+1].getDrawable());
        }
        if(myIndex==0){
            if(imgz[2].getVisibility()==View.VISIBLE){
                checkIsNeedGone(2);
            }else{
                checkIsNeedGone(1);
            }
        }else if(myIndex==1){
            checkIsNeedGone(2);
        }else{
            imagePathZ[2]="";
            imgz[2].setImageBitmap(mBitmap);
            imgz[2].setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        for(int i=0;i<imgz.length;i++){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imgz[i].getDrawable();
            if(bitmapDrawable.getBitmap()==mBitmap){
                imgz[i].setEnabled(true);
                imgdelz[i].setVisibility(View.GONE);
            }else{
                imgz[i].setEnabled(false);
                imgdelz[i].setVisibility(View.VISIBLE);
            }
        }
        index--;
    }

    private void checkIsNeedGone(int index) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imgz[index].getDrawable();
        if(bitmapDrawable.getBitmap()==mBitmap){
            imgz[index].setVisibility(View.GONE);
        }else{
            imagePathZ[index]="";
            imgz[index].setImageBitmap(mBitmap);
            imgz[index].setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mBitmap.recycle();
    }
}
