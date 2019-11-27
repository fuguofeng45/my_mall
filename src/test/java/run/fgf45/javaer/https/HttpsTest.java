package run.fgf45.javaer.https;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class HttpsTest {

    public static final String HTTPS = "https://";
    public static final String host = "123.157.208.25:20000";
    public static final String appKey = "24907742";
    public static final String appSecret = "0UcajYnQd4X6LSmjxfCG";
    public static final String ARTEMIS_PATH = "/artemis";
    private static long expireTime;
    private static String token;

    public static String getToken(String httpSchema, String host, String appKey, String appSecret) {
        String responseMsg = "";
        String token = null;
        String url = httpSchema + host + ARTEMIS_PATH + "/oauth/token";
        PostMethod postMethod = new PostMethod(url);
        postMethod.addParameter("client_id", appKey);
        postMethod.addParameter("client_secret", appSecret);
        responseMsg = getResponseMsg(postMethod);
        if (!"".equals(responseMsg)) {
            JSONObject json = JSONObject.parseObject(responseMsg);
            if (null != json.getString("access_token")) {
                token = json.getString("access_token");
                Integer expire_in = json.getIntValue("expires_in");
                if (expire_in != null) {
                    expireTime = System.currentTimeMillis() + (expire_in - 5) * 1000;
                }
            }
        }
        return token;
    }

    public static boolean getTokenRefresh(Long expireTime) {
        boolean flag = false;
        if (expireTime != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime >= expireTime) {
                flag = true;
            }
        }
        return flag;
    }

    public static String getResponseMsg(HttpMethod httpMethod) {
        String responseMsg = "";
        int statusCode = 0;
        HttpClient httpClient = new HttpClient();
        Protocol httpsProtocol = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
        Protocol.registerProtocol("https", httpsProtocol);
        InputStream in = null;
        try {
            httpClient.getParams().setContentCharset("UTF-8");
            statusCode = httpClient.executeMethod(httpMethod);
            if (statusCode != 200) {
                throw new RuntimeException("请求异常：" + statusCode);
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in = httpMethod.getResponseBodyAsStream();
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            responseMsg = out.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpMethod.releaseConnection();
            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return responseMsg;
    }

    public static String doPostHttp(String url, String access_token, String jsonStr) {
        String responseMsg = null;
        try {
            if (null != access_token) {
                PostMethod postMethod = new PostMethod(url);
                postMethod.addRequestHeader("access_token", access_token);
                RequestEntity se = new StringRequestEntity(jsonStr, "application/json", "UTF-8");
                postMethod.setRequestEntity(se);
                responseMsg = getResponseMsg(postMethod);
            } else {
                responseMsg = "access_token is null";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return responseMsg;
    }

    public static void main(String[] args) {
        if (getTokenRefresh(expireTime)) {
            token = getToken(HttpsTest.HTTPS, host, appKey, appSecret);
            System.out.println(token);
        }
    }

}


