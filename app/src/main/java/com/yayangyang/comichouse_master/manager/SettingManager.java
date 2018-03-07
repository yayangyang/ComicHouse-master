package com.yayangyang.comichouse_master.manager;

import com.yayangyang.comichouse_master.Bean.support.BookMark;
import com.yayangyang.comichouse_master.Bean.user.Login;
import com.yayangyang.comichouse_master.base.Constant;
import com.yayangyang.comichouse_master.utils.LogUtils;
import com.yayangyang.comichouse_master.utils.ScreenUtils;
import com.yayangyang.comichouse_master.utils.SharedPreferencesUtil;
import com.yayangyang.comichouse_master.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class SettingManager {

    private volatile static SettingManager manager;

    public static SettingManager getInstance() {
        return manager != null ? manager : (manager = new SettingManager());
    }

    /**
     * 保存书籍阅读字体大小
     *
     * @param bookId     需根据bookId对应，避免由于字体大小引起的分页不准确
     * @param fontSizePx
     * @return
     */
    public void saveFontSize(String bookId, int fontSizePx) {
        // 书籍对应
        SharedPreferencesUtil.getInstance().putInt(getFontSizeKey(bookId), fontSizePx);
    }

    /**
     * 保存全局生效的阅读字体大小
     *
     * @param fontSizePx
     */
    public void saveFontSize(int fontSizePx) {
        saveFontSize("", fontSizePx);
    }

    public int getReadFontSize(String bookId) {
        return SharedPreferencesUtil.getInstance().getInt(getFontSizeKey(bookId), ScreenUtils.dpToPxInt(16));
    }

    public int getReadFontSize() {
        return getReadFontSize("");
    }

    private String getFontSizeKey(String bookId) {
        return bookId + "-readFontSize";
    }

    public int getReadBrightness() {
        return ScreenUtils.getScreenBrightness();
    }

    /**
     * 保存阅读界面屏幕亮度
     *
     * @param percent 亮度比例 0~100
     */
    public void saveReadBrightness(int percent) {
        if(percent > 100){
            ToastUtils.showToast("saveReadBrightnessErr CheckRefs");
            percent = 100;
        }
        SharedPreferencesUtil.getInstance().putInt(getLightnessKey(), percent);
    }

    private String getLightnessKey() {
        return "readLightness";
    }

    public void removeReadProgress(String bookId) {
        SharedPreferencesUtil.getInstance()
                .remove(getChapterKey(bookId))
                .remove(getStartPosKey(bookId))
                .remove(getEndPosKey(bookId));
    }

    private String getChapterKey(String bookId) {
        return bookId + "-chapter";
    }

    private String getStartPosKey(String bookId) {
        return bookId + "-startPos";
    }

    private String getEndPosKey(String bookId) {
        return bookId + "-endPos";
    }


    public boolean addBookMark(String bookId, BookMark mark) {
        List<BookMark> marks = SharedPreferencesUtil.getInstance().getObject(getBookMarksKey(bookId), ArrayList.class);
        if (marks != null && marks.size() > 0) {
            for (BookMark item : marks) {
                if (item.chapter == mark.chapter && item.startPos == mark.startPos) {
                    return false;
                }
            }
        } else {
            marks = new ArrayList<>();
        }
        marks.add(mark);
        SharedPreferencesUtil.getInstance().putObject(getBookMarksKey(bookId), marks);
        return true;
    }

    public List<BookMark> getBookMarks(String bookId) {
        return SharedPreferencesUtil.getInstance().getObject(getBookMarksKey(bookId), ArrayList.class);
    }

    public void clearBookMarks(String bookId) {
        SharedPreferencesUtil.getInstance().remove(getBookMarksKey(bookId));
    }

    private String getBookMarksKey(String bookId) {
        return bookId + "-marks";
    }

    public void saveReadTheme(int theme) {
        SharedPreferencesUtil.getInstance().putInt("readTheme", theme);
    }

    public int getReadTheme() {
        if (SharedPreferencesUtil.getInstance().getBoolean(Constant.ISNIGHT, false)) {
            return ThemeManager.NIGHT;
        }
        return SharedPreferencesUtil.getInstance().getInt("readTheme", 3);
    }

    /**
     * 是否可以使用音量键翻页
     *
     * @param enable
     */
    public void saveVolumeFlipEnable(boolean enable) {
        SharedPreferencesUtil.getInstance().putBoolean("volumeFlip", enable);
    }

    public boolean isVolumeFlipEnable() {
        return SharedPreferencesUtil.getInstance().getBoolean("volumeFlip", true);
    }

    public void saveAutoBrightness(boolean enable) {
        SharedPreferencesUtil.getInstance().putBoolean("autoBrightness", enable);
    }

    public boolean isAutoBrightness() {
        return SharedPreferencesUtil.getInstance().getBoolean("autoBrightness", false);
    }


    /**
     * 保存用户选择的性别
     *
     * @param sex male female
     */
    public void saveUserChooseSex(String sex) {
        SharedPreferencesUtil.getInstance().putString("userChooseSex", sex);
    }

    /**
     * 获取用户选择性别
     *
     * @return
     */
    public String getUserChooseSex() {
        return SharedPreferencesUtil.getInstance().getString("userChooseSex", Constant.Gender.MALE);
    }

    public boolean isUserChooseSex() {
        return SharedPreferencesUtil.getInstance().exists("userChooseSex");
    }

    public boolean isNoneCover() {
        return SharedPreferencesUtil.getInstance().getBoolean("isNoneCover", false);
    }

    public void saveNoneCover(boolean isNoneCover) {
        SharedPreferencesUtil.getInstance().putBoolean("isNoneCover", isNoneCover);
    }

    //自己定义-------------------------------------------------------------------------

    public boolean isFirstEnter(){
        return SharedPreferencesUtil.getInstance().getBoolean("isFirstEnter",true);
    }

    public void savaFirstEnter(boolean isFirstEnter){
        SharedPreferencesUtil.getInstance().putBoolean("isFirstEnter",isFirstEnter);
    }

    public Login getLoginInfo(){
        return SharedPreferencesUtil.getInstance().getObject("loginInfo",Login.class);
    }

    public void saveLoginInfo(Object loginInfo){
        SharedPreferencesUtil.getInstance().putObject("loginInfo",loginInfo);
    }

    public boolean getIsHelpful(String sectionId){
        return SharedPreferencesUtil.getInstance().getBoolean(sectionId,false);
    }

    public void saveHelpful(String sectionId,boolean is_helpful){
        SharedPreferencesUtil.getInstance().putBoolean(sectionId,is_helpful);
    }

    public int getReadLightProgress(){
        return SharedPreferencesUtil.getInstance().getInt("readLightProgress",50);
    }

    public void saveReadLightProgress(int readLight){
        SharedPreferencesUtil.getInstance().putInt("readLightProgress",readLight);
    }

    public boolean getIsSystemLight(){
        return SharedPreferencesUtil.getInstance().getBoolean("isSystemLight",false);
    }

    public void saveIsSystemLight(boolean isSystemLight){
        SharedPreferencesUtil.getInstance().putBoolean("isSystemLight",isSystemLight);
    }

    public boolean getIsVisibleReview(){
        return SharedPreferencesUtil.getInstance().getBoolean("isVisibleReview",false);
    }

    public void saveIsVisibleReview(boolean isVisibleReview){
        SharedPreferencesUtil.getInstance().putBoolean("isVisibleReview",isVisibleReview);
    }

    public boolean getIsViewPointPattern(){
        return SharedPreferencesUtil.getInstance().getBoolean("isViewPointPattern",true);
    }

    public void saveIsViewPointPattern(boolean isViewPointPattern){
        SharedPreferencesUtil.getInstance().putBoolean("isViewPointPattern",isViewPointPattern);
    }

    public String getReadProgress(String comicId){
        return SharedPreferencesUtil.getInstance().getString(comicId,"");
    }

    public void saveReadProgress(String comicId,String readProgress){
        SharedPreferencesUtil.getInstance().putString(comicId,readProgress);
    }

    public boolean getIsAlreadyFabulous(String objectId){
        return SharedPreferencesUtil.getInstance().getBoolean(objectId,false);
    }

    public void saveFabulous(String objectId,boolean is_fabulous){
        SharedPreferencesUtil.getInstance().putBoolean(objectId,is_fabulous);
    }

    public List<String> getSearchHistory(String type){
        return SharedPreferencesUtil.getInstance().getObject(type,ArrayList.class);
    }

    public void saveSearchHistory(String type,List<String> list){
        SharedPreferencesUtil.getInstance().putObject(type,list);
    }

}
