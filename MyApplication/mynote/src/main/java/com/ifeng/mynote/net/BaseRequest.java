package com.ifeng.mynote.net;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class BaseRequest {
	
	public static void post(RequestQueue requestQueue,String url,final PostResponseListener listener)
	{
		StringRequest request = new StringRequest(Request.Method.POST, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				listener.success(response);
			}
			
		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				listener.failed(error.getMessage());
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				if(null!=listener.setParams())
					return listener.setParams();
				return super.getParams();
			}
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				if(null!=listener.setHeader())
					return listener.setHeader();
				return super.getHeaders();
			}
		};
		
		requestQueue.add(request);
	}
	public static void get(RequestQueue requestQueue,String url,final PostResponseListener listener)
	{
		StringRequest request = new StringRequest(Request.Method.GET, url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				listener.success(response);
			}

		},new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				listener.failed(error.getMessage());
			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				if(null!=listener.setParams())
					return listener.setParams();
				return super.getParams();
			}
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				if(null!=listener.setHeader())
					return listener.setHeader();
				return super.getHeaders();
			}
		};

		requestQueue.add(request);
	}
	public interface PostResponseListener
	{
		public Map<String, String> setParams();
		public Map<String, String> setHeader();
		public void success(String result);
		public void failed(String message);
	}
	
	
}
