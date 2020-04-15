package com.esiran.greenpay.test;

import com.esiran.greenpay.common.sign.SignType;
import com.esiran.greenpay.common.util.*;
import okhttp3.*;
import org.apache.catalina.util.URLEncoder;
import org.bouncycastle.util.encoders.UrlBase64Encoder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OpenApiTest {
    private OkHttpClient okHttpClient;
    private Map<String,String> params;
    private static final String API_URL = "http://localhost:8081/api/v1/invoices";
    @Before
    public void init(){
        okHttpClient = new OkHttpClient.Builder().build();
    }
    @Before
    public void initParams(){
        params = new HashMap<>();
        params.put("appId","123456");
        params.put("outOrderNo","12346546");
        params.put("amount","10");
        params.put("subject","10");
        params.put("channel","wx_jsapi");
        params.put("apiKey","abc");

    }
    private static <K extends String,V extends String> FormBody map2fromBody(Map<K,V> map){
        FormBody.Builder formbodyBuider = new FormBody.Builder();
        map.forEach(formbodyBuider::addEncoded);
        return formbodyBuider.build();
    }

    @Test
    public void testMd5Sign(){
        long timestamp = System.currentTimeMillis();
        params.put("timestamp",String.valueOf(timestamp));
        params.put("signType","md5");
        String principal = MapUtil.sortAndSerialize(params,new String[]{"sign"});
        String sign = EncryptUtil.md5(principal.concat("abc"));
        params.put("sign", sign);
    }

    private String getPrivateKey() throws Exception {
        InputStream fis = new FileInputStream(new File("..\\keys\\private_pkcs8.key"));
        String privateKeyWrapper = IOUtil.readInputString(fis);
        return RSAUtil.resolvePrivateKey(privateKeyWrapper);
    }

    private String getPublicKey() throws Exception {
        InputStream fis = new FileInputStream(new File("..\\keys\\public.pem"));
        String privateKeyWrapper = IOUtil.readInputString(fis);
        return RSAUtil.resolvePublicKey(privateKeyWrapper);
    }

    @Test
    public void testSignRsaSign() throws Exception {
        long timestamp = System.currentTimeMillis();
        params.put("timestamp",String.valueOf(timestamp));
        params.put("signType","rsa");
        String privateKey = getPrivateKey();
        String principal = MapUtil.sortAndSerialize(params,null);
        System.out.println(String.format("principal: %s", principal));
        byte[] data = principal.getBytes(StandardCharsets.UTF_8);
        String sign = RSAUtil.sign(data,privateKey);
        System.out.println(String.format("sign: %s", sign));
        sign = UrlSafeB64.encode(sign);
        String vSign = UrlSafeB64.decode(sign);
        String publicKey = getPublicKey();
        boolean pass = RSAUtil.verify(data,publicKey,vSign);
        System.out.println(String.format("pass: %s", pass));
        params.put("sign", sign);
    }

    @After
    public void sendRequest() throws IOException {
        FormBody body = map2fromBody(params);
        Request rq = new Request.Builder().url(API_URL).post(body).build();
        Response response = okHttpClient.newCall(rq).execute();
        ResponseBody responseBody = response.body();
        assert responseBody != null;
        String bodyString = responseBody.string();
        System.out.println(bodyString);
    }

}
