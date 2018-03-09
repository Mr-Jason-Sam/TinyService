package smart.sftinyservice.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SfNetUtils {
    
    private static final String TAG = SfNetUtils.class.getSimpleName();
    
    private static final int REQUEST_TIMEOUT_MILLILS = 1000 * 30;
    public static final String UTF_8                 = "UTF-8";
    public static final String ISO_8859_1            = "ISO-8859-1";
    
    public static String getIpAddressFromUrl(String url) {

        String ip = "";
        if (ObjectCheck.isEmptyString(url)) {
            return ip;
        }
        String regexString="\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern p = Pattern.compile(regexString);
        Matcher m = p.matcher(url);
        if(m.find())
            ip = m.group();
        return ip;
    }
    
    public static String addPasswordToUrl(String url, String name, String password){
        
        String outUrl = null;
        
        if (ObjectCheck.isEmptyString(url)) {
            return null;
        }
        
        int beginIndex = url.indexOf("://");
        if(beginIndex < 0)
            throw new RuntimeException("input url has no protocol head(" + url + ")");
        
        outUrl = url.replace("://", "://" + name + ":" + password + "@");
        
        return outUrl;
    }
    
    public static String getResultByPost(String requestURL, Map<String, String> pMap){
        //Log.i(TAG, "getResultByPost, params requestURL:" + requestURL + ", pMap:" + pMap);
        if(ObjectCheck.isEmptyString(requestURL)) {
            return "";
        }
        HttpClient client = new HttpClient();

        PostMethod post = new PostMethod(requestURL);
        HttpMethodParams httparams= post.getParams();
        httparams.setSoTimeout(REQUEST_TIMEOUT_MILLILS);
        httparams.setContentCharset("utf-8");
        
        if (pMap != null && !pMap.isEmpty()) {
            Set<String> keySet = pMap.keySet();
            for (String name : keySet) {
                String value = pMap.get(name);
                post.setParameter(name, value);
            }
        }
        String respStr = "";
        try {
            client.executeMethod(post);
            InputStream is = post.getResponseBodyAsStream();
            respStr = IoUtil.inputStreamToStringByByteArrayOutputStream(is, UTF_8);
        } catch (IOException e) {
            Log.w(TAG, "getResultByPost, IOException:" + e);
            e.printStackTrace();
        }
        //Log.i(TAG, "getResultByPost, resStr:" + respStr);
        return respStr;
    }
    
    public static String getLocalMacAddress(Context context) {  
        ConnectivityManager connectivityManager = (ConnectivityManager) 
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null || connectivityManager.getActiveNetworkInfo() == null) {
            Log.e(TAG, "getLocalMacAddress, Can not get network status. using 00:11:22:33:44:55 for test.");
            return "00:11:22:33:44:55";
        }
        
        String mac = "";
        
        switch(connectivityManager.getActiveNetworkInfo().getType()){
        case ConnectivityManager.TYPE_WIFI:
            mac = getHardwareAddress("wlan0");
            break;
        case ConnectivityManager.TYPE_ETHERNET:
        default:
            mac = getHardwareAddress("eth0");
            break;
        }
        if(mac.equals("")){
            Log.w(TAG, "getLocalMacAddress, Can not loading hardware address. using 00:11:22:33:44:55 for test.");
            return "00:11:22:33:44:55";
        }
        else {
            return mac;
        }
    }
    
    private static String getHardwareAddress(String name){
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName(name);
            byte[] macBytes = networkInterface.getHardwareAddress();
            String macStr = "";
            for (int i = 0; i < macBytes.length; i++) {
                String sTemp = String.format("%02X", 0xFF & macBytes[i]);
                macStr = macStr + sTemp + ":";
            }

            return macStr.substring(0, macStr.lastIndexOf(":"));
            
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String getLocalIpAddressV4()  
    {  
        try  
        {  
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)  
            {  
               NetworkInterface intf = en.nextElement();
               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)  
               {
                   InetAddress inetAddress = enumIpAddr.nextElement();  
                   if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address)  
                   {  
                       return inetAddress.getHostAddress().toString();  
                   }
               }  
           }  
        }  
        catch (SocketException ex)  
        {  
            ex.printStackTrace();  
        }  
        return null;  
    }

    /*public static String get() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        final Request request = new Request.Builder()
                .url("https://xxxx.易大师.xxxxx")
                .build();
        Call call = okHttpClient.newCall(request);
        //04.请求加入调度
        call.enqueue(new Callback() { //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                // TODO Auto-generated method stub
                
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //请求成功执行的方法,response就是从服务器得到的参数,response.body()可以得到任意类型,字符串,字节
                //这段代码可以拿到服务器的字符串.(通过response.body还可以拿到多种数据类型)
                //String htmlStr = response.body().string();　
            }
        });
        return "";
    }
    
    public static void post() {//TODO fix for using in high version of Android.
        //0.0.RequestBody里所使用的上传参数的设定.(传一个字符串) 定义为成员变量:JSON:
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");//"类型,字节码"
        String json = "{\n" + "\"name\": \"hello.android6.0\"\n" + "}";//就是一个字符串.
        //0.0.通过post传一个文件,非常方便.

        //MediaType FIle = MediaType.parse("application/octet-stream");
        //File file = new File(Environment.getExternalStorageDirectory(), "文件名.mp4");//得到一个mp4的文件,file对象

        RequestBody requestBody = new FormBody.Builder()
                .add("XXX","易大师")
                .add("YYY","YCF")
                .build();
        
        //01.创建OkHttpClient对象.
        OkHttpClient mOkHttpClient=new OkHttpClient();
        //02.通过create,创建一个RequestBody(参数1:数据类型 参数2:字符串,文件,byte数组..)
        RequestBody requestBody = RequestBody.create(JSON, json);
        //RequestBody requestBody = RequestBody.create(FIle , file );//上传一个文件的话
        //03.通过build来创建一个Request请求,需要指定post方式,并把RequestBody对象放入.
        Request request = new Request.Builder()
                                   .url("http://XXX/易大师/YYY")
                                   .post(requestBody)
                                   .build();
        //04.创建一个call对象( 也可以省略的写为:mOkHttpClient.newCall(request).enqueue(new Callback(){.......});)
        Call call = mOkHttpClient.newCall(request);
        //05.请求加入调度(发送请求)
        call.enqueue(new Callback()
        {    //请求失败执行的方法
            public void onFailure(Call call, IOException e){}
            //请求成功执行的方法,response就是从服务器得到的参数
            public void onResponse(Call call, final Response response) throws IOException
            {
                //String htmlStr =  response.body().string();
            }
        });
    }*/
}
