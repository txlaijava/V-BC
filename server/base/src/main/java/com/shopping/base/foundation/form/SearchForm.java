package com.shopping.base.foundation.form;

import com.aliyun.opensearch.sdk.generated.search.Aggregate;
import lombok.Data;

/**
 * 搜索form
 */
@Data
public class SearchForm {

	/**
	 * 搜索accesskey
	 */
	private String accesskey;

	/**
	 * 阿里云密钥
	 */
	private String secret;

	private String host;

	/**
	 * 搜索类型
	 * goods:商品
	 * store:店铺
	 */
	private String searchType;

	/**
	 * 搜索字段
	 */
	private String searchKey;

	/**
	 * 搜索关键字
	 */
	private String searchText;

	/**
	 * 排序
	 */
	private String sort;

	/**
	 * 排序类型
	 */
	private String sortType;

	/**
	 * 当前页数
	 */
	private int currentPage;

	/**
	 * 每页记录数
	 */
	private int pageSize;

	private String[] filter;

	/**
	 * 需要统计
	 */
	private Aggregate aggregate;

	public SearchForm(String accesskey,String secret,String host){
		this.accesskey = accesskey;
		this.secret = secret;
		this.host = host;
	}
}
