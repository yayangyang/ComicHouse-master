package com.yayangyang.comichouse_master.Bean.user;

public class TencentLoginResult {


    /**
     * "access_token": "848FB789519321B979005DBF16CB8AEA",
     * "authority_cost": 0,
     * "expires_in": 7776000,
     * "login_cost": 278,
     * "msg": "",
     * "openid": "92F002E3592300331FE922C0DC2930A1",
     * "pay_token": "F2AA970C5ACE6B17E0407090ECBDB9CF",
     * "pf": "desktop_m_qq-10000144-android-2002-",
     * "pfkey": "a1a6acb47c88b42e915889a925701e1c",
     * "query_authority_cost": 259,
     * "ret": 0
     */

    public String access_token;
    public String authority_cost;
    public String expires_in;
    public String login_cost;
    public String msg;
    public String openid;
    public String pay_token;
    public String pf;
    public String pfkey;
    public String query_authority_cost;
    public String ret;

    @Override
    public String toString() {
        return "TencentLoginResult{" +
                "ret=" + ret +
                ", pay_token='" + pay_token + '\'' +
                ", pf='" + pf + '\'' +
                ", query_authority_cost=" + query_authority_cost +
                ", authority_cost=" + authority_cost +
                ", openid='" + openid + '\'' +
                ", expires_in=" + expires_in +
                ", pfkey='" + pfkey + '\'' +
                ", msg='" + msg + '\'' +
                ", access_token='" + access_token + '\'' +
                ", login_cost=" + login_cost +
                '}';
    }
}
