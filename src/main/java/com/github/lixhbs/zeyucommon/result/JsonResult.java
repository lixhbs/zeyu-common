package com.github.lixhbs.zeyucommon.result;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * @author li
 */
public class JsonResult
{
	private int code;
	private String message;
	private String session_key;
	private Object data;

	public JsonResult()
	{
		this.setCode(ResultCode.SUCCESS);
		this.setMessage(ResultCode.SUCCESS.msg());
	}
	
	public JsonResult(ResultCode code)
	{
		this.setCode(code);
		this.setMessage(code.msg());
	}
	
	public JsonResult(String message, ResultCode code)
	{
		this.setCode(code);
		this.setMessage(message);
	}

	public JsonResult(ResultCode code, Object data)
	{
		this.setCode(code);
		this.setMessage(code.msg());
		this.setData(data);
	}

	public JsonResult(ResultCode code, Object data, String message)
	{
		this.setCode(code);
		this.setMessage(message);
		this.setData(data);
	}
	public JsonResult(int code, Object data, String message)
	{
		this.code = code;
		this.setMessage(message);
		this.setData(data);
	}

	@Override
	public String toString()
	{

		JSONObject json = new JSONObject();
		try
		{
			json.put("code", code);
			json.put("message", message);
			json.put("data", data);
			json.put("session_key", session_key);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		return json.toString();
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(ResultCode code)
	{
		this.code = code.val();
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public String getSessionKey()
	{
		return session_key;
	}

	public void setSessionKey(String sessionKey)
	{
		this.session_key = sessionKey;
	}

}
