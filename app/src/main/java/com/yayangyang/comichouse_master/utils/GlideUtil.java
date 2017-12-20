package com.yayangyang.comichouse_master.utils;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2017/12/8.
 */

public class GlideUtil {

    private static BitmapTransformation sRoundBitmapTransformation;
    private static RequestOptions sDefaultRequestOptions;
    private static RequestOptions sRoundCornerRequestOptions;
    private static RequestOptions sCircleCornerRequestOptions;

    public static BitmapTransformation getCommonTransformation(){
        if(sRoundBitmapTransformation==null){
            sRoundBitmapTransformation = new RoundedCornersTransformation(ScreenUtils.dpToPxInt(6), 0);
        }
        return sRoundBitmapTransformation;
    }

    public static RequestOptions getDefaultRequestOptions(){
        if(sDefaultRequestOptions==null){
            sDefaultRequestOptions = new RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
//                    .disallowHardwareConfig()
                    .skipMemoryCache(true);
        }
        return sDefaultRequestOptions;
    }

    public static RequestOptions getRoundCornerRequestOptions(){
        if(sRoundCornerRequestOptions==null){
            sRoundCornerRequestOptions = new RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
//                    .disallowHardwareConfig()
                    .transform(GlideUtil.getCommonTransformation())
                    .skipMemoryCache(true);
        }
        return sRoundCornerRequestOptions;
    }

    public static RequestOptions getCircleCornerRequestOptions(){
        if(sCircleCornerRequestOptions==null){
            sCircleCornerRequestOptions = new RequestOptions()
                    .format(DecodeFormat.PREFER_RGB_565)
//                    .disallowHardwareConfig()
                    .skipMemoryCache(true)
                    .circleCrop();
        }
        return sCircleCornerRequestOptions;
    }

}
