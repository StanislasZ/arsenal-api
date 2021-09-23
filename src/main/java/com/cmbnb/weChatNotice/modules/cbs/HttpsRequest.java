package com.cmbnb.weChatNotice.modules.cbs;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public class HttpsRequest {


	/**
	 * 	处理https GET/POST请求
	 * @param requestUrl   请求地址
	 * @param requestMethod   请求方法
	 * @param outputStr    参数
	 * @return
	 */
	public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){
		StringBuffer buffer=null;
		try{

			//创建SSLContext
			SSLContext sslContext=SSLContext.getInstance("TLS");
			TrustManager[] tm={new MyX509TrustManager()};
			//初始化
			sslContext.init(null, tm, new java.security.SecureRandom());;
			//获取SSLSocketFactory对象
			SSLSocketFactory ssf=sslContext.getSocketFactory();
			URL url=new URL(requestUrl);
			HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			//设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			 //设置默认证书--如果没有这句将会报错,错误信息↓↓↓
			 //java.io.IOException: HTTPS hostname wrong:  should be <127.0.0.1>
			conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(requestMethod);
			//设置当前实例使用的SSLSoctetFactory
			conn.setSSLSocketFactory(ssf);
			conn.connect();

			//往服务器端写内容
			if(null!=outputStr){
				OutputStream os=conn.getOutputStream();
				os.write(outputStr.getBytes("utf-8"));
				os.close();
			}

			//读取服务器端返回的内容
			InputStream is=conn.getInputStream();
			InputStreamReader isr=new InputStreamReader(is,"utf-8");
			BufferedReader br=new BufferedReader(isr);
			buffer=new StringBuffer();
			String line=null;
			while((line=br.readLine())!=null){
				buffer.append(line);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	//处理http请求  requestUrl为请求地址  requestMethod请求方式，值为"GET"或"POST"
	public static String httpRequest(String requestUrl,String requestMethod,String outputStr){

//		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");

		StringBuffer buffer=null;
		try{
			URL url=new URL(requestUrl);
			HttpURLConnection conn=(HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(requestMethod);
			conn.connect();
			//往服务器端写内容 也就是发起http请求需要带的参数
			if(null!=outputStr){
				OutputStream os=conn.getOutputStream();
				os.write(outputStr.getBytes("utf-8"));
				os.close();
			}

			//读取服务器端返回的内容
			InputStream is=conn.getInputStream();
//			InputStreamReader isr=new InputStreamReader(is,"utf-8");
			InputStreamReader isr=new InputStreamReader(is,"GBK");
			BufferedReader br=new BufferedReader(isr);
			buffer=new StringBuffer();
			String line=null;
			while((line=br.readLine())!=null){
				buffer.append(line);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return buffer.toString();
	}


	public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, IOException{


		String s= httpRequest("http://127.0.0.1:10666","GET","<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
				"<PGK>\n" +
				"    <DATA><![CDATA[<?xml version=\"1.0\" encoding=\"GBK\" ?>\n" +
				"<CBSERPPGK>\n" +
				"    <INFO>\n" +
				"        <FUNNAM>ERCURDTL</FUNNAM>\n" +
				"    </INFO>\n" +
				"    <ERCURDTLA>\n" +
				"        <ITMDIR>A</ITMDIR>\n" +
				"        <VTLFLG>0</VTLFLG>\n" +
				"    </ERCURDTLA>\n" +
				"</CBSERPPGK>\n" +
				"]]></DATA>\n" +
				"    <CHECKCODE>Z721E8878</CHECKCODE>\n" +
				"</PGK>");







		System.out.println(s);
	}


	private static class TrustAnyHostnameVerifier implements HostnameVerifier {

		public boolean verify(String hostname, SSLSession session) {
			 return true;
		}
	}
}
