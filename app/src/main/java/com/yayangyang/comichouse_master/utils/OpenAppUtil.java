package com.yayangyang.comichouse_master.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.yayangyang.comichouse_master.BuildConfig;
import com.yayangyang.comichouse_master.base.Constant;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/17.
 */

public class OpenAppUtil {

    /**
     * 打开相册
     * @param activity
     */
    public static void openAlbum(Activity activity){
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(Constant.IMAGE_TYPE);
        if (Build.VERSION.SDK_INT <19) {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        activity.startActivityForResult(intent, Constant.GET_PHOTO_WITH_DATA);
    }

    /**
     * 打开相机
     * @param activity
     */
    public static String openCamera(Activity activity){
        //网上说保存在默认目录,但是找不到照片
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        activity.startActivityForResult(intent,Constant.TAKE_PHOTO_WITH_DATA);

        // 利用系统自带的相机应用:拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri= null;
        String pathHead = Constant.PATH_PICTURES ;

        FileUtils.createDir(pathHead);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "IMG_" + timeStamp + ".jpg";
//        LogUtils.e("openCamera-fileName"+fileName);
        String path=pathHead+ File.separator+fileName;
        File file = new File(path);


        //判断是否是AndroidN以及更高的版本
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//给目标应用一个临时的授权
//            fileUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider", file);
//        } else {
//            fileUri=Uri.fromFile(new File(path));
//        }

        //不兼容不能识别content://格式的URI的app
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//给目标应用一个临时的授权
        fileUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider", file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);//第二个参数是uri
        activity.startActivityForResult(intent, Constant.TAKE_PHOTO_WITH_DATA);

        return path;
    }

}
