package com.hep.terminalhep.webapi;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;
import android.content.Context;

import com.hep.terminalhep.utils.LogManager;

/**
 * @Title: @WebApiClient.java
 * @author zhangj
 * @version 1.0
 * @date 2015年4月28日
 */

public class WebApiClient {

	private static class SingletonHolder {
		private static final WebApiClient Instance = new WebApiClient();
	}
	
	public static WebApiClient getInstance() {
		return SingletonHolder.Instance;
	}

	public String soaphttp(String methodName, Map<String, Object> map,Context context){
		
		Element[] header = new Element[1];
		
		header[0] = new Element().createElement(ApiEnvironment.SOAPSPACE, ApiEnvironment.SOAPHEADER);
		
		Element userName = new Element().createElement(ApiEnvironment.SOAPSPACE, ApiEnvironment.KEYNUMBER);
		
		userName.addChild(Node.TEXT, ApiEnvironment.KEYVALUE);
		
		header[0].addChild(Node.ELEMENT, userName);
		
		Element pass = new Element().createElement(ApiEnvironment.SOAPSPACE, ApiEnvironment.KEYNUMBERS);
		
		pass.addChild(Node.TEXT, ApiEnvironment.KEYVALUES);
		
		header[0].addChild(Node.ELEMENT, pass);
		
		SoapObject rpc = new SoapObject(ApiEnvironment.SOAPSPACE, methodName);
		
		if (map != null) {
		
			for (Entry<String, Object> e : map.entrySet()) {
				
				rpc.addProperty(e.getKey(), e.getValue());
			
			}
		}
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
		
		envelope.headerOut = header;
		
		envelope.bodyOut = rpc;
		
		envelope.dotNet = true;
		
		envelope.setOutputSoapObject(rpc);
		
		HttpTransportSE transport = new HttpTransportSE(ApiEnvironment.SOAPIPADDRESS+ApiEnvironment.ENDPOINT);
		
		try {
			
			try {
				
				transport.call(ApiEnvironment.SOAPSPACE+"/"+methodName, envelope);
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
			
				LogManager.getLogger().e("FileName: %s, "+ "Method %s,Content %s","WebApiClint","soaphttp:IOException",e);
				
				e.printStackTrace();
			
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
			
				e.printStackTrace();
		
			}

			return envelope.getResponse().toString();
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			
			LogManager.getLogger().e("FileName: %s, Method %s,"+ "Content %s","WebApiClint","soaphttp:SoapFault",e);
			
			e.printStackTrace();
		}
		return "";
	}
}
