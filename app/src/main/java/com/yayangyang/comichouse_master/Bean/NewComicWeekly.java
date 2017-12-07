package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

/**
 * Created by Administrator on 2017/12/5.
 */

public class NewComicWeekly extends Base {

    /**
     * "mobile_header_pic": "http:\/\/images.dmzj.com\/subject\/251\/mobile_header_pic_1509703386.jpg",
     * "title": "\u65b0\u6f2b\u5468\u520a\u7b2c38\u671f \u4e00\u5468\u65b0\u6f2b\u63a8\u8350",
     * "description": "\u5929\u6c14\u5dee\u4e0d\u591a\u5f00\u59cb\u771f\u6b63\u8f6c\u51c9\u4e86\u2026\u2026\u5927\u5bb6\u8bf7\u6ce8\u610f\u52a0\u8863\u9632\u6b62\u7740\u51c9\u3002\u672c\u671f\u7684\u4e3b\u6253\u4f5c\u54c1\u662f\u6c34\u65e0\u6708\u5fd8\u5403\u836f\u7206\u7b11\u65b0\u8fde\u8f7d\u3001\u4f5b\u8df3\u5899\u540c\u6b3e\u5e08\u751f\u4eb2\u3001\u67aa\u52c7\u91cd\u751f\u5916\u4f20\u3001\u559c\u6b22\u8c03\u620f\u4e3b\u4eba\u7684\u9ed1\u5973\u4ec6\u3001\u5c11\u5e74\u72af\u5c11\u5e74A\u7684\u6545\u4e8b\u3001\u6740\u4eba\u9b3c\u8001\u5e08\u548c\u5973\u5b66\u751f\u7684\u6545\u4e8b\u3001\u8f6c\u751f\u6210\u767d\u718a\u3001\u590f\u65e5\u8f6e\u56de\u3001\u4eba\u5916\u9752\u6885\u7af9\u9a6c\uff0c\u4ee5\u53ca\u7f8e\u7f8e\u9999\u7684\u65b0\u72f1\u53cb(\u4f1a\u5458\u5410\u69fd)\u3002",
     * "comment_amount": 25
     * "comics": [{
     * "alias_name": "",
     * "cover": "http://images.dmzj.com/webpic/8/duzhicooking.jpg",
     * "id": 41856,
     * "name": "毒贽Cooking",
     * "recommend_brief": "新作力推臭脚少女",
     * "recommend_reason": "水无月老湿的惯例就是一部正常连载+一部无厘头搞笑连载，被掠夺者虐到的同学可以在这里寻求一点安慰"
     * }]
     */

    public String mobile_header_pic;
    public String title;
    public String description;
    public String comment_amount;
    public DataBean[] comics;

    public static class DataBean{
        public String alias_name;
        public String cover;
        public String id;
        public String name;
        public String recommend_brief;
        public String recommend_reason;
    }
}
