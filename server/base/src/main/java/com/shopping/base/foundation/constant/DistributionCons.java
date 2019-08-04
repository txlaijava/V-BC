package com.shopping.base.foundation.constant;

import com.shopping.base.utils.CommUtils;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 吴亚军 on 2016/7/26.
 * 分销相关常量
 */
public class DistributionCons {

	//默认用户logo
	public static final String DEFAULT_USER_LOGO = "/resources/images/system/user.png";

	//默认店铺logo
	public static final String DEFAULT_SHOP_LOGO = "/resources/images/system/shop.png";

	//默认店铺横幅
	public static final String DEFAULT_SHOP_BANNER = "/resources/images/system/store_banner.jpg";

	//默认商品logo
	public static final String DEFAULT_GOODS_LOGO = "/resources/images/system/goods.png";

	public static final String DEFAULT_LOGO_48 = "/resources/images/system/logo_48.png";

	public static final String DEFAULT_LOGO_128 = "/resources/images/system/logo_128.png";

	public static final String DEFAULT_LOGO_256 = "/resources/images/system/logo_256.png";



	//分销员类型（0：红小店 1：一级分销员 2：二级分销员 3：三级分销员 4：四级分销员 5：五级分销员 6：店长 7：店员 ）
	// 红小店
	public static final int REDSHOP_ROLE = 0;

	//一级分销员
	public static final int LEVEL_1_ROLE = 1;

	//二级分销员
	public static final int LEVEL_2_ROLE = 2;

	//三级分销员
	public static final int LEVEL_3_ROLE=3;

	//四级分销员
	public static final int LEVEL_4_ROLE=4;

	//五级分销员
	public static final int LEVEL_5_ROLE = 5;

	//店长
	public static final int SHOPMANAGER=6;

	//店员
	public static final int SHOPASSIATANT=7;



	//分销员注册需支付1元
	public static final BigDecimal FXY_REGISTER_PAY = new BigDecimal(0.01).setScale(2,4);

	//营业员注册需支付1元
	public static final BigDecimal YYY_REGISTER_PAY = new BigDecimal(1).setScale(2,4);

	//红小店注册需支付188元
	public static final BigDecimal HXD_REGISTER_PAY = new BigDecimal(188).setScale(2,4);

	//分销员5级升级红小店需支付18.8元
	public static final BigDecimal FXY_UPDATE_TO_HXD_PAY = new BigDecimal(18.8).setScale(2,4);



	//分销链接是否支持其它分销商品
	public static final Boolean IS_DISTRIBUTION_OF_ALL = true;

	//单笔提现至少需要的金额
	public static final BigDecimal BALANCE_MIN = new BigDecimal(0.01).setScale(2,4);

	//单笔提现最高需要的金额
	public static final BigDecimal BALANCE_MAX = new BigDecimal(1000).setScale(2,4);

	//正常
	public static final int NO_PAY = 0;

	//分销员注册支付
	public static final int DISTRIBUTION_REGISTER_NEED_PAY = 1;

	//红小店注册支付
	public static final int REDSHOP_REGISTER_NEED_PAY = 2;

	//当用户注册验证联系电话必须在有效时间内填写信息，否则无效 （单位：秒）
	public static final int REGISTER_EFFECTIVE_SECONDS = 15 * 60;





	//通用交易
	public static final int DISTRIBUTION_TRADE_TYPE_OF_COMMON = 10;

	//注册支付
	public static final int DISTRIBUTION_TRADE_TYPE_OF_REGISTER = 11;

	//升级支付
	public static final int DISTRIBUTION_TRADE_TYPE_OF_UPDATE = 12;

	//单订单结算
	public static final int DISTRIBUTION_TRADE_TYPE_OF_BALANCE = 13;

	//提现
	public static final int DISTRIBUTION_TRADE_TYPE_OF_TAKE = 14;

	//微信收款
	public static final int DISTRIBUTION_TRADE_TYPE_OF_WX_GET = 15;




	//禁用状态
	public static final int DISTRIBUTION_USER_STATUS_OF_ENABLE = -1;

	//审核中
	public static final int DISTRIBUTION_USER_STATUS_OF_AUDIT = 0;

	//正常
	public static final int DISTRIBUTION_USER_STATUS_OF_ABLE = 1;



	//交易取消
	public static final int DISTRIBUTION_TRADE_STATUS_OF_CANCEL = 0;

	//交易审核中
	public static final int DISTRIBUTION_TRADE_STATUS_OF_AUDIT = 10;

	//交易已审核
	public static final int DISTRIBUTION_TRADE_STATUS_OF_AUDITED = 20;

	//待支付
	public static final int DISTRIBUTION_TRADE_STATUS_OF_WAIT_PAY = 30;

	//交易完成
	public static final int DISTRIBUTION_TRADE_STATUS_OF_SUCCESS = 40;


	public static List<String> getNotShowClasses(){
		List<String> lists = new ArrayList<>();
		lists.add("66172");//优惠券
		lists.add("66177");//0
		lists.add("66171");//券类
		lists.add("66182");//娱乐
		lists.add("65988");//超市
		return lists;
	}


	public static Long TRADE_ID = CommUtils.null2Long(29); //红小店行业 （百货）

	public static Long GRADE_ID = CommUtils.null2Long(4); //红小店等级


	public static void setDistributionConsInMv(ModelAndView mv){

		mv.addObject("REDSHOP_ROLE",REDSHOP_ROLE);
		mv.addObject("LEVEL_1_ROLE",LEVEL_1_ROLE);
		mv.addObject("LEVEL_2_ROLE",LEVEL_2_ROLE);
		mv.addObject("LEVEL_3_ROLE",LEVEL_3_ROLE);
		mv.addObject("LEVEL_4_ROLE",LEVEL_4_ROLE);
		mv.addObject("LEVEL_5_ROLE",LEVEL_5_ROLE);
		mv.addObject("SHOPMANAGER",SHOPMANAGER);
		mv.addObject("SHOPASSIATANT",SHOPASSIATANT);

		mv.addObject("FXY_REGISTER_PAY",FXY_REGISTER_PAY);
		mv.addObject("YYY_REGISTER_PAY",YYY_REGISTER_PAY);
		mv.addObject("HXD_REGISTER_PAY",HXD_REGISTER_PAY);
		mv.addObject("FXY_UPDATE_TO_HXD_PAY",FXY_UPDATE_TO_HXD_PAY);

		mv.addObject("TRADE_COMMON",DISTRIBUTION_TRADE_TYPE_OF_COMMON);
		mv.addObject("TRADE_REGISTER",DISTRIBUTION_TRADE_TYPE_OF_REGISTER);
		mv.addObject("TRADE_UPDATE",DISTRIBUTION_TRADE_TYPE_OF_UPDATE);
		mv.addObject("TRADE_BALANCE",DISTRIBUTION_TRADE_TYPE_OF_BALANCE);
		mv.addObject("TRADE_TAKE",DISTRIBUTION_TRADE_TYPE_OF_TAKE);
		mv.addObject("TRADE_WX_GET",DISTRIBUTION_TRADE_TYPE_OF_WX_GET);

		mv.addObject("TRADE_CANCEL",DISTRIBUTION_TRADE_STATUS_OF_CANCEL);
		mv.addObject("TRADE_AUDIT",DISTRIBUTION_TRADE_STATUS_OF_AUDIT);
		mv.addObject("TRADE_AUDITED",DISTRIBUTION_TRADE_STATUS_OF_AUDITED);
		mv.addObject("TRADE_WAIT_PAY",DISTRIBUTION_TRADE_STATUS_OF_WAIT_PAY);
		mv.addObject("TRADE_SUCCESS",DISTRIBUTION_TRADE_STATUS_OF_SUCCESS);
	}

}
