package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 资金明细
 * Created by Alashi on 2016/1/27.
 */
@RequestConfig(api = "?c=game&a=orderList", method = Request.Method.GET, response = OrderListResponse.class)
public class OrderListCommand {
    private int curPage;
    private int perPage = 20;
    /**0：全部
     1：投注，含撤单返款
     2：派奖，含撤销派奖
     3：充值
     4：提现， 含取消取现
     */
    private int orderType;

    private String startDate;//		开始日期
    private  String endDate	;//		结束日期

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }
}
