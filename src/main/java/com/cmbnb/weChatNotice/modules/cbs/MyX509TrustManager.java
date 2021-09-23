package com.cmbnb.weChatNotice.modules.cbs;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager implements X509TrustManager {

	 X509TrustManager sunJSSEX509TrustManager; 
	 public MyX509TrustManager() throws Exception{

		 KeyStore ks = KeyStore.getInstance("JKS"); 
       
         java.io.FileInputStream fis = null;
         try {
             fis = new java.io.FileInputStream("D:\\cacerts");
             //fis = new java.io.FileInputStream("D:\\cacerts1"); ////用这个就会报错  No trusted certificate found ，因为cacerts1 没有cbs的证书。
             ks.load(fis, null);
         } finally {
             if (fis != null) {
                 fis.close();
             }
         }
          
         TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509", "SunJSSE"); 
         tmf.init(ks); 
         TrustManager tms [] = tmf.getTrustManagers(); 
         for (int i = 0; i < tms.length; i++) { 
             if (tms[i] instanceof X509TrustManager) { 
                 sunJSSEX509TrustManager = (X509TrustManager) tms[i]; 
                 return; 
             } 
         } 
		 
	 }

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		   sunJSSEX509TrustManager.checkClientTrusted(chain, authType); 
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
		sunJSSEX509TrustManager.checkServerTrusted(chain, authType);
	}

	@Override
	public X509Certificate[] getAcceptedIssuers() {
		  return sunJSSEX509TrustManager.getAcceptedIssuers(); 
	}

}
