package com.yayangyang.comichouse_master.Bean;

import com.yayangyang.comichouse_master.Bean.base.Base;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class UploadImageResult extends Base {

    /**
     * {
     * "status": 200,
     * "data": ["20171219193747_520.jpg", "20171219193747_116.jpg", "20171219193747_919.jpg"]
     * }
     */

    public String status;
    public List<String> data;

}
