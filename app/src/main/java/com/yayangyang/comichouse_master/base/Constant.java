package com.yayangyang.comichouse_master.base;

import android.graphics.Color;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.yayangyang.comichouse_master.utils.AppUtils;
import com.yayangyang.comichouse_master.utils.FileUtils;

import org.apache.commons.collections4.map.HashedMap;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Constant {

    public static final String IMG_BASE_URL = "http://images.dmzj.com";

    public static final String API_BASE_URL = "http://v2.api.dmzj.com";

    public static String PATH_DATA = FileUtils.createRootPath(AppUtils.getAppContext()) + "/cache";

    public static String PATH_RESPONSES = FileUtils.createRootPath(AppUtils.getAppContext());

    public static String PATH_COLLECT = FileUtils.createRootPath(AppUtils.getAppContext()) + "/collect";

    public static String PATH_TXT = PATH_DATA + "/book/";

    public static String PATH_EPUB = PATH_DATA + "/epub";

    public static String PATH_CHM = PATH_DATA + "/chm";

    public static String BASE_PATH = AppUtils.getAppContext().getCacheDir().getPath();

    public static final String ISNIGHT = "isNight";

    public static final String ISBYUPDATESORT = "isByUpdateSort";
    public static final String FLIP_STYLE = "flipStyle";

    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";

    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    @StringDef({
            Gender.MALE,
            Gender.FEMALE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        String MALE = "male";

        String FEMALE = "female";
    }

    @StringDef({
            CateType.HOT,
            CateType.NEW,
            CateType.REPUTATION,
            CateType.OVER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface CateType {
        String HOT = "hot";

        String NEW = "new";

        String REPUTATION = "reputation";

        String OVER = "over";
    }

    @StringDef({
            Distillate.ALL,
            Distillate.DISTILLATE
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Distillate {
        String ALL = "";

        String DISTILLATE = "true";
    }

    @StringDef({
            SortType.DEFAULT,
            SortType.COMMENT_COUNT,
            SortType.CREATED,
            SortType.HELPFUL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface SortType {
        String DEFAULT = "updated";

        String CREATED = "created";

        String HELPFUL = "helpful";

        String COMMENT_COUNT = "Comment-count";
    }

    public static List<String> sortTypeList = new ArrayList<String>() {{
        add(SortType.DEFAULT);
        add(SortType.CREATED);
        add(SortType.COMMENT_COUNT);
        add(SortType.HELPFUL);
    }};

    //------------------------------------------------------------------

    public static final int FINISH_ACTIVITY=1;
    public static final int RETURN_DATA=2;

    public static int ALL_COMIC=100;
    public static int ORIGINAL_COMIC=1;
    public static int DUBBING_COMIC=0;
    public static int LINEARLAYOUT_MANAGER=1;
    public static int GRIDLAYOUT_MANAGER=2;

    public static int POPULAR_RANK=0;
    public static int REVIEW_RANK=1;
    public static int SUSCRIBE_RANK=2;

    public static String CHANNEL="Android";
    public static String VERSION="2.7.001";

    public static String MY_INDEX="my_index";
    public static String CURRENT_COMIC_TYPE="currentComicType";;
    public static String CURRENT_DATE="currentdate";;
    public static String CURRENT_RANK_TYPE="currentRankType";;

    @IntDef({
            //定义限定值
            ComicType.ALL,
            ComicType.JOYFUL,
            ComicType.HAREM,
            ComicType.WARM_BLOOD,
            ComicType.DRAG_QUEEN,
            ComicType.LILY,
            ComicType.SEX_CONVERSION,
            ComicType.SCIENCE_FICTION,
            ComicType.LOVE,
            ComicType.MAGIC,
            ComicType.TERROR,
            ComicType.AMERICA,
            ComicType.THE_END,
            ComicType.JUVENILE_COMIC,
            ComicType.GRIL_COMIC,
            ComicType.YOUTH_COMIC
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ComicType {
        //定义变量
        int ALL=0;
        int JOYFUL=5;
        int HAREM=3249;;
        int WARM_BLOOD=3248;
        int DRAG_QUEEN=3244;
        int LILY=3243;
        int SEX_CONVERSION=4518;
        int SCIENCE_FICTION=7;
        int LOVE=8;
        int MAGIC=11;
        int TERROR=14;
        int AMERICA=2306;
        int THE_END=2310;
        int JUVENILE_COMIC=3262;
        int GRIL_COMIC=3263;
        int YOUTH_COMIC=3264;
    }

    public static List<Integer> comicTypeList = new ArrayList<Integer>() {{
        add(ComicType.ALL);
        add(ComicType.JOYFUL);
        add(ComicType.HAREM);
        add(ComicType.WARM_BLOOD);
        add(ComicType.DRAG_QUEEN);
        add(ComicType.LILY);
        add(ComicType.SEX_CONVERSION);
        add(ComicType.SCIENCE_FICTION);
        add(ComicType.LOVE);
        add(ComicType.MAGIC);
        add(ComicType.TERROR);
        add(ComicType.AMERICA);
        add(ComicType.THE_END);
        add(ComicType.JUVENILE_COMIC);
        add(ComicType.GRIL_COMIC);
        add(ComicType.YOUTH_COMIC);
    }};

    public static Map<Integer, String> comicTypeMap = new HashedMap<Integer, String>() {{
        put(ComicType.ALL, "全部");
        put(ComicType.JOYFUL, "欢乐向");
        put(ComicType.HAREM, "后宫");
        put(ComicType.WARM_BLOOD, "热血");
        put(ComicType.DRAG_QUEEN, "伪娘");
        put(ComicType.LILY, "百合");
        put(ComicType.SEX_CONVERSION, "性转换");
        put(ComicType.SCIENCE_FICTION, "科幻");
        put(ComicType.LOVE, "爱情");
        put(ComicType.MAGIC, "魔法");
        put(ComicType.TERROR, "恐怖");
        put(ComicType.AMERICA, "欧美");
        put(ComicType.THE_END, "已完结");
        put(ComicType.JUVENILE_COMIC, "少年漫画");
        put(ComicType.GRIL_COMIC, "少女漫画");
        put(ComicType.YOUTH_COMIC, "青年漫画");
    }};

    @IntDef({
            DateType.DAY,
            DateType.WEEK,
            DateType.MONTH,
            DateType.TOTAL
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface DateType {
        int DAY=0;
        int WEEK=1;
        int MONTH=2;
        int TOTAL=3;
    }

    public static List<Integer> dateTypeList = new ArrayList<Integer>() {{
        add(DateType.DAY);
        add(DateType.WEEK);
        add(DateType.MONTH);
        add(DateType.TOTAL);
    }};

    public static Map<Integer, String> dateTypeMap = new HashedMap<Integer, String>() {{
        put(DateType.DAY, "日");
        put(DateType.WEEK, "周");
        put(DateType.MONTH, "月");
        put(DateType.TOTAL, "总");
    }};
}
