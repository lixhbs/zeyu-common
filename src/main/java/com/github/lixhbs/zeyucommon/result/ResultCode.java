package com.github.lixhbs.zeyucommon.result;

/**
 * 
 * @Description: TODO
 * @author Lix.
 * @date Sep 6, 2017 5:34:18 PM 
 * @ClassName: ResultCode
 *
 */
public enum ResultCode
{

	SUCCESS(0, "成功"),
	FAIL(1001, "失败"),
	EXCEPTION(9001, "异常"),

	ACCOUNT(8001, "用户"),
	ACCOUNT_LOGIN_FALSE(8404, "未获取登录信息");


	private ResultCode(int value, String msg)
	{
		this.val = value;
		this.msg = msg;
	}

	public int val()
	{
		return val;
	}

	public String msg()
	{
		return msg;
	}

	private int val;
	private String msg;
}
