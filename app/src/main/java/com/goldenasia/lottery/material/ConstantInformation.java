package com.goldenasia.lottery.material;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.SparseArray;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.data.PayGgcCommand;
import com.goldenasia.lottery.data.PayMoneyCommand;
import com.goldenasia.lottery.data.Platform;

import org.apache.commons.lang3.RandomStringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created on 2016/01/14.
 *
 * @author ACE
 * @功能描述: 信息存储
 */
public class ConstantInformation
{
    public static int ONE = 1;
    public static int TEN = 10;
    public static int HUNDRED = 100;
    public static int THOUSAND = 1000;
    public static int MYRIABIT = 10000;
    public static int[] DIGIT_KEYS = new int[]{ONE, TEN, HUNDRED, THOUSAND, MYRIABIT};
    public static SparseArray<String> DIGIT_MAP = new SparseArray<String>()
    {
        {
            put(ONE, "个");
            put(TEN, "十");
            put(HUNDRED, "百");
            put(THOUSAND, "千");
            put(MYRIABIT, "万");
        }
    };

    //六合彩正码任选
    public  static class LhcZmrx{
        public static int ONE = 1;
        public static int TWO = 10;
        public static int THREE = 100;
        public static int FOUR = 1000;
        public static int FIVE = 10000;
        public static int SIX = 100000;

        public static final int[] DIGIT_KEYS = new int[]{ONE, TWO, THREE, FOUR, FIVE,SIX};

        public static final int[] DIGIT_LHC_KEYS = new int[]{ONE, TWO, THREE, FOUR, FIVE,SIX};

        public static SparseArray<String> DIGIT_LHC_MAP = new SparseArray<String>()
        {
            {
                put(ONE, "一");
                put(TWO, "二");
                put(THREE, "三");
                put(FOUR, "四");
                put(FIVE, "五");
                put(SIX, "六");
            }
        };
    }

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static final long ONE_DAY = 24 * 60 * 60 * 1000L;
    public static final long ONE_HOUR = 60 * 60 * 1000L;
    public static final long ONE_MINUTE = 60 * 1000L;
    public static final long ONE_SECOND = 1000L;
    
    public static final int EXIT_RESULT = 101;
    public static final int REFRESH_RESULT = 102;
    public static final int LAUNCH_RESULT = 103;
    
    public static final String APP_INFO = "app";
    public static final String ACCOUNT_INFO = "account";
    public static final String FAVOURITE_INFO = "favourite";

    public static final ArrayList<Integer> CURRENT_LOTTERY_ID_LIST = new ArrayList<Integer>()
    {
        {
            add(1);
            add(2);
            add(4);
            add(7);
            add(8);
            add(9);
            add(10);
            add(11);
            add(15);
            add(16);
            add(17);
            add(19);
            add(20);
            add(21);
            add(24);
            add(26);
            add(27);
            add(28);
            add(35);
            add(101);
            add(103);
            add(104);
            add(108);
        }
    };
    
    private static SparseArray<int[]> sLotteryLogo = new SparseArray<>();
    private static SparseArray<int[]> rechargeLogo = new SparseArray<>();
    private static List<Platform> transferList = new ArrayList<>();
    
    private static List<String> issueSerials = new ArrayList<>();
    private static String infogather = "";
    private static String[] multiple = new String[235];
    private static String[] chaseissue = {"5期", "10期", "15期", "20期", "25期", "30期"};
    private static String[] province = {"深圳", "安徽", "北京", "广东", "云南", "福建", "甘肃", "广西", "贵州", "海南", "河北", "河南",
            "黑龙江", "湖北", "湖南", "吉林", "江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川", "天津", "西藏",
            "新疆", "浙江", "重庆", "香港", "澳门", "台湾"};
    private static Map<String, String[]> cityMap = new HashMap<>();
    
    static
    {
        
        for (int m = 1, l = multiple.length; m <= l; m++)
        {
            multiple[m - 1] = m + "倍";
        }
        
        //重庆时时彩
        sLotteryLogo.put(1, new int[]{R.drawable.id_lottery_cqssc, R.drawable.id_lottery_cqssc_rest});
        //山东11选5
        sLotteryLogo.put(2, new int[]{R.drawable.id_lottery_sd, R.drawable.id_lottery_sd_rest});
        //黑龙江时时彩
        sLotteryLogo.put(3, new int[]{R.drawable.id_lottery_hljssc_m, R.drawable.id_lottery_hljssc_m});
        //新疆时时彩
        sLotteryLogo.put(4, new int[]{R.drawable.id_lottery_xjssc, R.drawable.id_lottery_xjssc_rest});
        //江西11选5
        sLotteryLogo.put(6, new int[]{R.drawable.id_lottery_jx11x5, R.drawable.id_lottery_jx11x5_rest});
        //广东11选5
        sLotteryLogo.put(7, new int[]{R.drawable.id_lottery_gd, R.drawable.id_lottery_gd_rest});
        //天津时时彩
        sLotteryLogo.put(8, new int[]{R.drawable.id_lottery_tjssc, R.drawable.id_lottery_tjssc_rest});
        //福彩3D
        sLotteryLogo.put(9, new int[]{R.drawable.id_lottery_fc3d, R.drawable.id_lottery_fc3d_rest});
        //P3P5
        sLotteryLogo.put(10, new int[]{R.drawable.id_lottery_p3p5, R.drawable.id_lottery_p3p5_rest});
        //亚洲分分彩
        sLotteryLogo.put(11, new int[]{R.drawable.id_lottery_ffc, R.drawable.id_lottery_ffc_rest});
        //江苏快三
        sLotteryLogo.put(12, new int[]{R.drawable.id_lottery_jsks, R.drawable.id_lottery_jsks});
        //快三分分彩
        sLotteryLogo.put(13, new int[]{R.drawable.id_lottery_k3ffc, R.drawable.id_lottery_k3ffc});
        //亚洲秒秒彩
        sLotteryLogo.put(15, new int[]{R.drawable.id_lottery_yzmmc, R.drawable.id_lottery_yzmmc_rest});
        //11选5分分彩
        sLotteryLogo.put(16, new int[]{R.drawable.id_lottery_ffdj, R.drawable.id_lottery_ffdj_rest});
        //香港六合彩
        sLotteryLogo.put(17, new int[]{R.drawable.id_lottery_xglhc, R.drawable.id_lottery_xglhc_rest});
        //亚洲5分彩
        sLotteryLogo.put(19, new int[]{R.drawable.id_lottery_yzwfc, R.drawable.id_lottery_yzwfc_rest});
        //北京11选5
        sLotteryLogo.put(20, new int[]{R.drawable.id_lottery_bj, R.drawable.id_lottery_bj_rest});
        //上海11选5
        sLotteryLogo.put(21, new int[]{R.drawable.id_lottery_sh, R.drawable.id_lottery_sh_rest});
        //安徽快三
        sLotteryLogo.put(22, new int[]{R.drawable.id_lottery_ahks, R.drawable.id_lottery_ahks});
        //湖北快三
        sLotteryLogo.put(23, new int[]{R.drawable.id_lottery_hbks, R.drawable.id_lottery_hbks});
        //超快3D
        sLotteryLogo.put(24, new int[]{R.drawable.id_lottery_ck3d, R.drawable.id_lottery_ck3d_rest});
        //六合分分彩
        sLotteryLogo.put(26, new int[]{R.drawable.id_lottery_lhffc, R.drawable.id_lottery_lhffc_rest});
        //北京PK10
        sLotteryLogo.put(27, new int[]{R.drawable.id_lottery_bjpk10, R.drawable.id_lottery_bjpk10_rest});
        //刮刮彩
        sLotteryLogo.put(28, new int[]{R.drawable.id_lottery_ggc, R.drawable.id_lottery_ggc_rest});
        //江苏11选5
        sLotteryLogo.put(32, new int[]{R.drawable.id_lottery_js11x5, R.drawable.id_lottery_js11x5_rest});
        //浙江11选5
        sLotteryLogo.put(33, new int[]{R.drawable.id_lottery_zj11x5, R.drawable.id_lottery_zj11x5_rest});
        //福建11选5
        sLotteryLogo.put(34, new int[]{R.drawable.id_lottery_fj11x5, R.drawable.id_lottery_fj11x5_rest});
        //台湾五分彩
        sLotteryLogo.put(35, new int[]{R.drawable.id_lottery_tw5fc, R.drawable.id_lottery_tw5fc_rest});
        //山西11选5
        sLotteryLogo.put(36, new int[]{R.drawable.id_lottery_sx11x5, R.drawable.id_lottery_sx11x5_2});
        //亚洲2分彩
        sLotteryLogo.put(37, new int[]{R.drawable.id_lottery_yz2fc, R.drawable.id_lottery_yz2fc_2});
        //PK10分分彩
        sLotteryLogo.put(38, new int[]{R.drawable.id_lottery_pk10ffc, R.drawable.id_lottery_pk10ffc_2});
        //百家乐
        sLotteryLogo.put(101, new int[]{R.drawable.id_lottery_mybjl, R.drawable.id_lottery_mybjl_rest});
        //幸运大转盘
        sLotteryLogo.put(102, new int[]{R.drawable.ic_ga_xydzp, R.drawable.ic_ga_xydzp});
        //龙虎斗
        sLotteryLogo.put(103, new int[]{R.drawable.id_lottery_lfd, R.drawable.id_lottery_lfd_rest});
        //骰宝
        sLotteryLogo.put(104, new int[]{R.drawable.id_lottery_tb, R.drawable.id_lottery_tb_rest});
        //俄罗斯轮盘
        sLotteryLogo.put(105, new int[]{R.drawable.id_lottery_elslp, R.drawable.id_lottery_elslp});
        //西施早餐
        sLotteryLogo.put(106, new int[]{R.drawable.ic_ga_brnn, R.drawable.ic_ga_brnn});
        //21点
        sLotteryLogo.put(107, new int[]{R.drawable.ic_ga_21dian, R.drawable.ic_ga_21dian});
        //赢三张
        sLotteryLogo.put(108, new int[]{R.drawable.id_lottery_ysz, R.drawable.id_lottery_ysz_rest});
        //水果机
        sLotteryLogo.put(109, new int[]{R.drawable.sgj, R.drawable.sgj1});
        //多人百家乐
        sLotteryLogo.put(111, new int[]{R.drawable.ga_duorenbjl, R.drawable.ga_duorenbjl});
        //龙虎斗
        sLotteryLogo.put(112, new int[]{R.drawable.id_lottery_lfd, R.drawable.id_lottery_lfd_rest});
        //趣味牛牛
        sLotteryLogo.put(113, new int[]{R.drawable.qwnn01, R.drawable.qwnn01});
        //江苏骰宝
        sLotteryLogo.put(114, new int[]{R.drawable.jstb01, R.drawable.jstb01});
        //德州扑克
        sLotteryLogo.put(115, new int[]{R.drawable.id_lottery_dzpk, R.drawable.id_lottery_dzpk});
        //三国水果机
        sLotteryLogo.put(116, new int[]{R.drawable.id_lottery_sgsgj, R.drawable.id_lottery_sgsgj});

        //支付logo
        rechargeLogo.put(0, new int[]{1, 101, R.drawable.zfb_icon});
        rechargeLogo.put(1, new int[]{2, 0, R.drawable.unionpay});
        rechargeLogo.put(2, new int[]{3, 0, R.drawable.unionpay});
        rechargeLogo.put(3, new int[]{6, 0, R.drawable.pay_baidu_icon});
        rechargeLogo.put(4, new int[]{7, 0, R.drawable.qq});
        rechargeLogo.put(5, new int[]{8, 0, R.drawable.pay_weixin_icon});
        rechargeLogo.put(6, new int[]{9, 0, R.drawable.zfb_icon});
        rechargeLogo.put(7, new int[]{1, 100, R.drawable.pay_wyzz_icon});
        rechargeLogo.put(8, new int[]{5, 0, R.drawable.jd_icon});//京东支付 id=5
        
        transferList.add(new Platform(0, "主钱包", "JYZ"));
        /*transferArray.put(1, new String[]{"pt", "PT游戏"});
        transferArray.put(2, new String[]{"jc", "竞彩游戏"});*/
        transferList.add(new Platform(3, "GA游戏", "GA"));
        
        cityMap.put("深圳", new String[]{"罗湖区", "福田区", "盐田区", "南山区", "龙岗区", "宝安区", "光明新区", "市内"});
        cityMap.put("安徽", new String[]{"安庆", "蚌埠", "巢湖", "池州", "滁州", "阜阳", "合肥", "淮北", "淮南", "黄山", "六安", "马鞍山", "宿州",
                "铜陵", "芜湖", "宣城", "亳州"});
        cityMap.put("北京", new String[]{"北京"});
        cityMap.put("广东", new String[]{"深圳", "潮州", "东莞", "佛山", "广州", "河源", "惠州", "江门", "揭阳", "茂名", "梅州", "清远", "汕头",
                "汕尾", "韶关", "阳江", "云浮", "湛江", "肇庆", "中山", "珠海"});
        cityMap.put("云南", new String[]{"昆明", "曲靖", "大理", "保山", "玉溪", "楚雄", "丽江", "德宏", "迪庆", "红河", "临沧", "昭通", "怒江",
                "思茅", "文山", "西双版纳"});
        cityMap.put("福建", new String[]{"福州", "龙岩", "南平", "宁德", "莆田", "泉州", "三明", "厦门", "漳州"});
        cityMap.put("甘肃", new String[]{"白银", "定西", "甘南藏族自治州", "嘉峪关", "金昌", "酒泉", "兰州", "临夏回族自治州", "陇南", "平凉", "庆阳",
                "天水", "武威", "张掖"});
        cityMap.put("广西", new String[]{"南宁", "玉林", "百色", "北海", "崇左", "防城港", "桂林", "贵港", "河池", "贺州", "来宾", "柳州", "钦州",
                "梧州"});
        cityMap.put("贵州", new String[]{"安顺", "毕节", "贵阳", "六盘水", "黔东南苗族侗族自治州", "黔南布依族苗族自治州", "黔西南布依族苗族自治州", "铜仁", "遵义"});
        cityMap.put("海南", new String[]{"白沙黎族自治县", "保亭黎族苗族自治县", "昌江黎族自治县", "澄迈县", "定安县", "东方", "海口", "乐东黎族自治县", "临高县",
                "陵水黎族自治县", "琼海", "琼中黎族苗族自治县", "三亚", "屯昌县", "万宁", "文昌", "五指山", "儋州"});
        cityMap.put("河北", new String[]{"保定", "沧州", "承德", "邯郸", "衡水", "廊坊", "秦皇岛", "石家庄", "唐山", "邢台", "张家口"});
        cityMap.put("河南", new String[]{"安阳", "鹤壁", "济源", "焦作", "开封", "洛阳", "南阳", "平顶山", "三门峡", "商丘", "新乡", "信阳",
                "许昌", "郑州", "周口", "驻马店", "漯河", "濮阳"});
        cityMap.put("黑龙江", new String[]{"大庆", "大兴安岭", "哈尔滨", "鹤岗", "黑河", "鸡西", "佳木斯", "牡丹江", "七台河", "齐齐哈尔", "双鸭山",
                "绥化", "伊春"});
        cityMap.put("湖北", new String[]{"鄂州", "恩施土家族苗族自治州", "黄冈", "黄石", "荆门", "荆州", "潜江", "神农架林区", "十堰", "随州", "天门",
                "武汉", "仙桃", "咸宁", "襄樊", "孝感", "宜昌"});
        cityMap.put("湖南", new String[]{"常德", "长沙", "郴州", "衡阳", "怀化", "娄底", "邵阳", "湘潭", "湘西土家族苗族自治州", "益阳", "永州",
                "岳阳", "张家界", "株洲"});
        cityMap.put("吉林", new String[]{"白城", "白山", "长春", "吉林", "辽源", "四平", "松原", "通化", "延边朝鲜族自治州"});
        cityMap.put("江苏", new String[]{"常州", "淮安", "连云港", "南京", "南通", "苏州", "宿迁", "泰州", "无锡", "徐州", "盐城", "扬州", "镇江"});
        cityMap.put("江西", new String[]{"抚州", "赣州", "吉安", "景德镇", "九江", "南昌", "萍乡", "上饶", "新余", "宜春", "鹰潭"});
        cityMap.put("辽宁", new String[]{"鞍山", "本溪", "朝阳", "大连", "丹东", "抚顺", "阜新", "葫芦岛", "锦州", "辽阳", "盘锦", "沈阳", "铁岭",
                "营口"});
        cityMap.put("内蒙古", new String[]{"阿拉善盟", "巴彦淖尔盟", "包头", "赤峰", "鄂尔多斯", "呼和浩特", "呼伦贝尔", "通辽", "乌海", "乌兰察布盟",
                "锡林郭勒盟", "兴安盟"});
        cityMap.put("宁夏", new String[]{"固原", "石嘴山", "吴忠", "银川"});
        cityMap.put("青海", new String[]{"果洛藏族自治州", "海北藏族自治州", "海东", "海南藏族自治州", "海西蒙古族藏族自治州", "黄南藏族自治州", "西宁",
                "玉树藏族自治州"});
        cityMap.put("山东", new String[]{"滨州", "德州", "东营", "菏泽", "济南", "济宁", "莱芜", "聊城", "临沂", "青岛", "日照", "泰安", "威海",
                "潍坊", "烟台", "枣庄", "淄博"});
        cityMap.put("山西", new String[]{"长治", "大同", "晋城", "晋中", "临汾", "吕梁", "朔州", "太原", "忻州", "阳泉", "运城", "五指山"});
        cityMap.put("陕西", new String[]{"安康", "宝鸡", "汉中", "商洛", "铜川", "渭南", "西安", "咸阳", "延安", "榆林"});
        cityMap.put("上海", new String[]{"上海"});
        cityMap.put("四川", new String[]{"阿坝藏族羌族自治州", "巴中", "成都", "达州", "德阳", "甘孜藏族自治州", "张家口", "广安", "广元", "乐山",
                "凉山彝族自治州", "眉山", "绵阳", "南充", "内江", "攀枝花", "遂宁", "雅安", "宜宾", "资阳", "自贡", "泸州"});
        cityMap.put("天津", new String[]{"天津"});
        cityMap.put("西藏", new String[]{"阿里", "昌都", "拉萨", "林芝", "那曲", "日喀则", "山南"});
        cityMap.put("新疆", new String[]{"阿克苏", "阿拉尔", "巴音郭楞蒙古自治州", "博尔塔拉蒙古自治州", "昌吉回族自治州", "哈密", "和田", "喀什", "克拉玛依",
                "克孜勒苏柯尔克孜自治州", "石河子", "图木舒克", "吐鲁番", "乌鲁木齐", "五家渠", "伊犁哈萨克自治州"});
        cityMap.put("浙江", new String[]{"杭州", "湖州", "嘉兴", "金华", "丽水", "宁波", "绍兴", "台州", "温州", "舟山", "衢州"});
        cityMap.put("重庆", new String[]{"重庆"});
        cityMap.put("香港", new String[]{"香港岛", "九龙半岛", "新界本土", "新界离岛"});
        cityMap.put("澳门", new String[]{"澳门半岛", "氹仔岛", "路环岛"});
        cityMap.put("台湾", new String[]{"台湾"});
    }
    
    
    public static int getLotteryLogo(int lotteryID, boolean available)
    {
        int[] ids = sLotteryLogo.get(lotteryID);
        if (ids != null)
        {
            return available ? ids[0] : ids[1];
        }
        return R.drawable.jia;
    }
    
    public static int getRechargeLogo(int tradeType, int bankID)
    {
        int logo = 0;
        for (int i = 0; i < rechargeLogo.size(); i++)
        {
            int[] ico = rechargeLogo.get(i);
            if (tradeType == ico[0])
            {
                if (tradeType == 1 && bankID > 100)
                    return ico[2];
                logo = ico[2];
            }
        }
        if (logo > 0)
        {
            return logo;
        } else
        {
            return R.drawable.jia;
        }
    }
    
    public static RecordTime getLasttime(String currenttime, String end)
    {
        RecordTime recTime = new RecordTime();
        try
        {
            Date d1 = df.parse(currenttime);
            Date d2 = df.parse(end);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别
            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("GMT-0"));
            calendar.setTimeInMillis(diff);
            recTime.setDay(calendar.get(Calendar.DATE) - 1);
            recTime.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            recTime.setMinute(calendar.get(Calendar.MINUTE));
            recTime.setSecond(calendar.get(Calendar.SECOND));
            return recTime;
        } catch (Exception e)
        {
        }
        return recTime;
    }
    
    public static String[] getMultiple()
    {
        return multiple;
    }
    
    public static String[] getChaseissue()
    {
        return chaseissue;
    }
    
    public static String[] getProvince()
    {
        return province;
    }
    
    public static List<Platform> getTransferArray()
    {
        return transferList;
    }
    
    public static String[] getCityList(String province)
    {
        return cityMap.get(province);
    }
    
    public static String randomToken()
    {
        return RandomStringUtils.randomNumeric(20);
    }
    
    
    public static List<String> getIssueSerials()
    {
        return issueSerials;
    }
    
    public static void setIssueSerials(List<String> issues)
    {
        if (issues == null || issues.size() <= 0)
        {
            return;
        }
        issueSerials.clear();
        for (String issue : issues)
        {
            int issueIndex = issue.indexOf("-");
            if (issueIndex != -1)
            {
                issueSerials.add(issue.substring(issueIndex + 1, issue.length()));
            } else
            {
                issueSerials.add(issue);
            }
        }
    }
    
    public static String getInfogather()
    {
        return infogather;
    }
    
    @SuppressLint("LongLogTag")
    public static String getLocalIpAddress(Context context)
    {
        try
        {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex)
        {
            return "127.0.0.1";
        }
    }
    
    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }
    
    public static String gatherInfo(Activity activity, PayMoneyCommand command)
    {
        TelephonyManager mTm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = null;
        String iesi = null;
        try
        {
            imei = mTm.getDeviceId();
            iesi = mTm.getSubscriberId();
        } catch (Exception e)
        {
            Log.e("VersionInfo", "Exception", e);
        }
        return "手机IMEI号：" + imei + ",\n手机IESI号：" + iesi + ",\n手机型号：" + android.os.Build.MODEL + ",\n手机品牌：" + android
                .os.Build.BRAND + ",\nAndroid：" + android.os.Build.VERSION.SDK_INT + ",\nAppName：" + BuildConfig
                .VERSION_NAME + ",\nAppCode：" + BuildConfig.VERSION_CODE + ",\nUserID：" + GoldenAsiaApp.getUserCentre
                ().getUserID() + "," + "\nData：" + df.format(new Date()) + ",\nLotteryId：" + command.getLotteryId() +
                "," + "" + "\nIssue：" + command.getIssue() + ",\nPrizeMode：" + command.isPrizeMode() + ",\nModes：" +
                command.getModes() + ",\nCodes：" + command.getCodes() + "," + "\nMultiple：" + command.getMultiple() +
                "," + "\nTraceNum：" + command.getTraceNum() + ",\nStopOnWin：" + command.isStopOnWin() + ",\nToken：" +
                command.getToken();
    }
    
    public static String gatherGgcInfo(Activity activity, PayGgcCommand command)
    {
        TelephonyManager mTm = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = null;
        String iesi = null;
        try
        {
            imei = mTm.getDeviceId();
            iesi = mTm.getSubscriberId();
        } catch (Exception e)
        {
            Log.e("VersionInfo", "Exception", e);
        }
        return "手机IMEI号：" + imei + ",\n手机IESI号：" + iesi + ",\n手机型号：" + android.os.Build.MODEL + ",\n手机品牌：" + android
                .os.Build.BRAND + ",\nAndroid：" + android.os.Build.VERSION.SDK_INT + ",\nAppName：" + BuildConfig
                .VERSION_NAME + ",\nAppCode：" + BuildConfig.VERSION_CODE + ",\nUserID：" + GoldenAsiaApp.getUserCentre
                ().getUserID() + "," + "\nData：" + df.format(new Date()) + ",\nLotteryId：" + "28," + "\nScID：" +
                command.getSc_ids() + "\nPackageID：" + command.getPackage_id() + "\nScrapeType：" + command
                .getScrape_type();
    }
    
    /**
     * 获取版本号(内部识别号)
     */
    public static int getVersionCode(Context context)
    {
        try
        {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static ArrayList<String> LEGAL_NUMBER_SSC = new ArrayList<String>()
    {
        {
            add("0");
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
        }
    };
    
    public static ArrayList<String> LEGAL_NUMBER_SYXW = new ArrayList<String>()
    {
        {
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
            add("01");
            add("02");
            add("03");
            add("04");
            add("05");
            add("06");
            add("07");
            add("08");
            add("09");
            add("10");
            add("11");
        }
    };
    
    public static ArrayList<String> LEGAL_NUMBER_PK10 = new ArrayList<String>()
    {
        {
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
            add("9");
            add("01");
            add("02");
            add("03");
            add("04");
            add("05");
            add("06");
            add("07");
            add("08");
            add("09");
            add("10");
        }
    };
    
    public static ArrayList<Integer> MOUSE_NUM = new ArrayList<Integer>()
    {
        {
            add(10);
            add(22);
            add(34);
            add(46);
        }
    };
    public static ArrayList<Integer> COW_NUM = new ArrayList<Integer>()
    {
        {
            add(9);
            add(21);
            add(33);
            add(45);
        }
    };
    public static ArrayList<Integer> TIGER_NUM = new ArrayList<Integer>()
    {
        {
            add(8);
            add(20);
            add(32);
            add(44);
        }
    };
    public static ArrayList<Integer> RABBIT_NUM = new ArrayList<Integer>()
    {
        {
            add(7);
            add(19);
            add(31);
            add(43);
        }
    };
    public static ArrayList<Integer> DRAGON_NUM = new ArrayList<Integer>()
    {
        {
            add(6);
            add(18);
            add(30);
            add(42);
        }
    };
    public static ArrayList<Integer> SNAKE_NUM = new ArrayList<Integer>()
    {
        {
            add(5);
            add(17);
            add(29);
            add(41);
        }
    };
    public static ArrayList<Integer> HORSE_NUM = new ArrayList<Integer>()
    {
        {
            add(4);
            add(16);
            add(28);
            add(40);
        }
    };
    public static ArrayList<Integer> SHEEP_NUM = new ArrayList<Integer>()
    {
        {
            add(3);
            add(15);
            add(27);
            add(39);
        }
    };
    public static ArrayList<Integer> MONKEY_NUM = new ArrayList<Integer>()
    {
        {
            add(2);
            add(14);
            add(26);
            add(38);
        }
    };
    public static ArrayList<Integer> CHIKEN_NUM = new ArrayList<Integer>()
    {
        {
            add(1);
            add(13);
            add(25);
            add(37);
            add(49);
        }
    };
    public static ArrayList<Integer> DOG_NUM = new ArrayList<Integer>()
    {
        {
            add(12);
            add(24);
            add(36);
            add(48);
        }
    };
    public static ArrayList<Integer> PIG_NUM = new ArrayList<Integer>()
    {
        {
            add(11);
            add(23);
            add(35);
            add(47);
        }
    };
    
    public static ArrayList<ArrayList<Integer>> POULTRY_NUM = new ArrayList<ArrayList<Integer>>()
    {
        {
            add(COW_NUM);
            add(HORSE_NUM);
            add(SHEEP_NUM);
            add(CHIKEN_NUM);
            add(DOG_NUM);
            add(PIG_NUM);
        }
    };
    
    public static ArrayList<ArrayList<Integer>> BEAST_NUM = new ArrayList<ArrayList<Integer>>()
    {
        {
            add(MOUSE_NUM);
            add(TIGER_NUM);
            add(RABBIT_NUM);
            add(DRAGON_NUM);
            add(SNAKE_NUM);
            add(MONKEY_NUM);
        }
    };
    
    public static ArrayList<Integer> RED_WAVE_NUM = new ArrayList<Integer>()
    {
        {
            add(1);
            add(2);
            add(7);
            add(8);
            add(12);
            add(13);
            add(18);
            add(19);
            add(23);
            add(24);
            add(29);
            add(30);
            add(34);
            add(35);
            add(40);
            add(45);
            add(46);
        }
    };
    
    public static ArrayList<Integer> BLUE_WAVE_NUM = new ArrayList<Integer>()
    {
        {
            add(3);
            add(4);
            add(9);
            add(10);
            add(14);
            add(15);
            add(20);
            add(25);
            add(26);
            add(31);
            add(36);
            add(37);
            add(41);
            add(42);
            add(47);
            add(48);
        }
    };
    
    public static ArrayList<Integer> GREEN_WAVE_NUM = new ArrayList<Integer>()
    {
        {
            add(5);
            add(6);
            add(11);
            add(16);
            add(17);
            add(21);
            add(22);
            add(27);
            add(28);
            add(32);
            add(33);
            add(38);
            add(39);
            add(43);
            add(44);
            add(49);
        }
    };
}