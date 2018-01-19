package com.zhulaozhijias.zhulaozhijia.base;

/**
 * Created by asus on 2017/9/22.
 */

/**
 * 系统常量
 * @author fly
 */
public class SystemConstant {


    /**
     * SharedPreferences存储名
     */
    public static final String SHARED_PREFERENCE_NAME = "ZhuLaoZhiJia";
    /**
     * 运营服务器
     */
    public static final String HTTP = "http://hbhz.sesdf.org/";

    /**
     * 公共接口
     */
    public static final class PublicConstant{

        //首页公告
        public static final String Public_SECRET="z!l@z#j$";

        //身份证正则
        public static final String id_regular="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";

        //首页公告
        public static final String Public_BROADCAST =HTTP
                + "api/article";

        //三大排行
        public static final String Public_Ranking=HTTP
                + "api/ranker";

        //公示页
        public static final String Public_GONGSHI=HTTP
                + "api/notice";

        //item公示页
        public static final String Public_GONGSHI_ITEM=HTTP
                + "api/notice-person";

        //调查过程信息
        public static final String Public_GONGSHI_PROCESS=HTTP
                + "api/process";

        //公示材料照片
        public static final String Public_GONGSHI_PHOTO=HTTP
                + "api/photo";

        //各省市下的参与人数
        public static final String Public_PROVINCE_COUNT=HTTP
                + "api/province-count";


        //指定的s市的名称和参与人数
        public static final String Public_CITY=HTTP
                + "api/city";


        //最新动态详情
        public static final String Public_WECHAT_RECENTNEWS=HTTP
                + "api/newsdetail";

        //首页弹幕
        public static final String Public_WECHAT_LUNBO=HTTP
                + "wechat/lunbo";


    }
    /**
     * 登录注册忘记密码
     */
    public static final class UserConstant {


         //对用户账号的检测Check
        public static final String USER_ACCOUNT_CHECK = HTTP
                + "register/check";

        //登录
        public static final String USER_ACCOUNT_LOGIN = HTTP
                + "register/app-login";

        //注册
        public static final String USER_ACCOUNT_REGISTER= HTTP
                + "register/app-register";


        //微信端修改和添加密码
        public static final String USER_WECHAT_PASSWORD= HTTP
                + "register/app-password";


         //忘记密码发送短信验证码以及错误信息
        public static final String USER_VERIFICATION =HTTP
                 +"register/sms";

        //短信发送出错
        public static final int YANZHENG_ERROR=4085;

        //app修改密码
        public static final String USER_MAKE_PASSWORD =HTTP
                +"api/update-pwd";

        //app自动登录
        public static final String USER_AUTO_L0GIN =HTTP
                +"api/auto-login";


        //app忘记密码
        public static final String USER_SER_PASSWORD =HTTP
                +"register/set-password";

        //app端微信登录授权
        public static final String USER_APP_OAUTH =HTTP
                +"app/oauth";

        //android和ios端版本更新接口
        public static final String USER_UPDATE_CHECK =HTTP
                +"update/check";

        //app完善信息
        public static final String USER_APP_REGISTER =HTTP
                +"register/app";

    }


    public static final  class LoginedInformatio{

        //三大排行 分享榜个人排行
        public static final String USER_SHARE_RANK = HTTP
                + "api/share";

        //三大排行 捐助人数个人排行
        public static final String USER_DONATE_RANK = HTTP
                + "api/donation-count";

        //三大排行 捐助金额个人排行
        public static final String USER_DONATE_MONEY_RANK = HTTP
                + "api/donation-total";

        //三大排行 点赞
        public static final String USER_PRAISE_RANK = HTTP
                + "api/praise";


        //用户签到
        public static final String USER_SIGN = HTTP
                + "api/signer";

        //捐助记录
        public static final String USER_JUANZHUJILU = HTTP
                + "api/record-detail";

    }


    /**
     * 互助计划
     */
    public static final class HuZhuPlan{
        //计划参与人数
        public static final String HuZhuPlan_NUMBBR = HTTP
                + "api/service-count";

        //计划全部信息
        public static final String HuZhuPlan_ALL_MESSAGE = HTTP
                + "api/plan";


        //全民防癌互助计划
        public static final String HuZhuPlan_PACT_CANCER = HTTP
                + "plan/pact-cancer";


        //会员公约
        public static final String HuZhuPlan_PACT_VIP = HTTP
                + "plan/pact-vip";


        //健康要求
        public static final String HuZhuPlan_PACT_HEALTH = HTTP
                + "plan/pact-health";



        //用户加入计划添加
        public static final String JOJN_PLAN = HTTP
                + "api/join-plan";


        //申请互助
        public static final String NOTICE_NOTICE = HTTP
                + "notice/notice";

        //查询申请互助状态
        public static final String NOTICE_SEL_STATUS = HTTP
                + "notice/sel-status";

        //获取用户数据
        public static final String NOTICE_MEMBER= HTTP
                + "api/member";

        //购买计划查询关系
        public static final String NOTICE_API_XINXI= HTTP
                + "api/xinxi";

    }


    /**
     * 个人中心信息
     */
    public static final class GEREN_ZHONGXIN{


        //公约章程
        public static final String Mine_USER_ABOUT_CONVENTION = HTTP
                + "about/convention";

        //常见问题
        public static final String Mine_USER_ABOUT_ISSUE = HTTP
                + "about/issue";

        //真实头像
        public static final String Mine_USER_IMG = HTTP
                + "api/user-img";


        //保障凭证
        public static final String Mine_PINGZHENG = HTTP
                + "api/safeguard";


         //保障凭证
        public static final String Mine_SetPWD = HTTP
                    + "api/setpwd";



        //充值记录(捐赠记录)
        public static final String Mine_RECHARGE_DETAIL= HTTP
                + "api/recharge-detail";

        //兑换豆数
        public static final String Mine_EXCHANGE_BEAN= HTTP
                + "api/exchange-bean";

        //返回用户健康豆
        public static final String Mine_SHOW_BEAN= HTTP
                + "api/show-bean";

        //兑换记录
        public static final String Mine_EXCHANGE_RECORD= HTTP
                + "api/exchange-record";

        //个人中心设置关系
        public static final String Mine_BETWEEN= HTTP
                + "api/between";

        //查询关系
        public static final String Mine_SEL_BETWEEN= HTTP
                + "api/sel-between";

//        //游戏奖励
//        public static final String Mine_RECHARGE_DETAIL= HTTP
//                + "api/recharge-detail";

        //获取最新6个加入头像
        public static final String Mine_INVITW_SHARE= HTTP
                + "invite/share";


        //钱包支付
        public static final String Mine_SEL_PWD= HTTP
                + "api/sel-pwd";


        //随手捐生成订单
        public static final String Mine_PUBLICITY_RECORD= HTTP
                + "publicity/record";

        //随手捐账户扣款
        public static final String Mine_RECORD_ACCOUNT= HTTP
                + "publicity/record-account";

        //支付宝支付随手捐生成订单
        public static final String Mine_ALIPAY_RECORD= HTTP
                + "alipay/record";


        //支付宝随手捐支付
        public static final String Mine_ALIPAY_ALIPAY= HTTP
                + "alipay/alipay";


        //支付宝充值生成订单
        public static final String Mine_ALIPAY_RECHARGE= HTTP
                + "alipay/recharge";


        //支付宝充值生成订单信息(支付)(2)
        public static final String Mine_RECHARGE_ALIPAY= HTTP
                + "alipay/recharge-alipay";


        //支付宝加入计划支付(2)
        public static final String Mine_ALIPAY_JOIN_PLAN= HTTP
                + "alipay/join-plan";


        //支付宝早起打卡支付一元(1)
        public static final String Mine_EARLY_TRADE= HTTP
                + "activity/early-trade";


        //支付宝早起打卡支付(2)
        public static final String Mine_ALIPAY_EARLY_PAY= HTTP
                + "alipay/early-pay";



        //微信充值接口
        public static final String Mine_WECHATPAY_RECHARGE= HTTP
                + "wechatpay/recharge";


        //微信随手捐接口
        public static final String Mine_WECHATPAY_PUBLICITY= HTTP
                + "wechatpay/publicity";


        //微信加入计划接口
        public static final String Mine_WECHATPAY_JOIN_PLAN= HTTP
                + "wechatpay/join-plan";


        //微信早起打卡接口
        public static final String Mine_WECHATPAY_PAY_EARLY= HTTP
                + "wechatpay/pay-early";


        //捐助取消订单
        public static final String Mine_PAY_DEL_RECORD= HTTP
                + "pay/del-record";
    }


    /**
     * 游戏
     */
    public static final class GAME{


        //早起打卡查询状态
        public static final String Game_SEL_EARLY= HTTP
                + "activity/sel-early";


        //早起打卡签到
        public static final String Game_CLOCK= HTTP
                + "activity/clock";


        //查询当天打卡数据
        public static final String Game_SEL_NUMBER= HTTP
                + "activity/sel-number";


        //话题押宝基本信息
        public static final String Game_ACTIVITY_FIRStT= HTTP
                + "activity/first";

        //话题押宝(下注)
        public static final String Game_TOPIC= HTTP
                + "activity/topic";

        //早起打卡-查询游戏记录
        public static final String Game_EARLY_RECRD= HTTP
                + "activity/sel-early-record";


        //话题押宝-往期数据
        public static final String Game_HISTORY_TOPIC= HTTP
                + "activity/history-topic";

        //游戏奖励查询(早起打卡,话题押宝)
        public static final String Game_ACTIVITY= HTTP
                + "activity/activity";

        //早起打卡游戏领取奖励
        public static final String Game_REWARD= HTTP
                + "reward/daka";


        //押宝领取奖励
        public static final String Game_REWARD_YABAO= HTTP
                + "reward/yabao";


        //游戏总记录(2个游戏)
        public static final String Game_AbOUT_SEL= HTTP
                + "about/sel";

        //分享成功加积分
        public static final String Game_API_POINT= HTTP
                + "api/point";

        //时间银行首页
        public static final String Game_BANK_INDEX= HTTP
                + "bank/bank-index";

        //时间银行兑换时间
        public static final String Game_BANK_RECEIVE_TIME= HTTP
                + "bank/receive-time";

        //时间银行兑换时间
        public static final String Game_BANK_RECEIVE_BEAN= HTTP
                + "bank/receive-bean";


    }

    /**
     * 活动页领取奖励
     */
    public static final class ACTIVITY_REWARD{

        //签到领取奖励
        public static final String ACTIVITY_REWARD_QIANDAO= HTTP
                + "reward/qiandao";

        //分享领取奖励
        public static final String ACTIVITY_REWARD_FENXIANG= HTTP
                + "reward/fenxiang";

        //捐助领取奖励
        public static final String ACTIVITY_REWARD_JUANZHU= HTTP
                + "reward/juanzhu";


        //计划领取奖励
        public static final String ACTIVITY_REWARD_JIHUA= HTTP
                + "reward/jihua";

        //充值领取奖励
        public static final String ACTIVITY_REWARD_CHONGZHI= HTTP
                + "reward/chongzhi";
    }
}

