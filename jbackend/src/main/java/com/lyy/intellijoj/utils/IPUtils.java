package com.lyy.intellijoj.utils;

import com.alibaba.fastjson.JSONObject;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;

import static com.lyy.intellijoj.common.Constants.UNKNOWN;


@Slf4j
public class IPUtils {
    private final static String resource_name = "ip2region.xdb";

    // 多次反向代理后会有多个ip值 的分割符
    private final static String IP_UTILS_FLAG = ",";
    // 本地 IP
    private final static String LOCALHOST_IP = "0:0:0:0:0:0:0:1";
    private final static String LOCALHOST_IP1 = "127.0.0.1";

    //别人的key，先不用
//    private final static String tencentPositionServiceUrl = "https://apis.map.qq.com/ws/location/v1/ip?ip={}&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77";



    private static Searcher searcher = null;

    static {
        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff = new byte[0];
        try {
            String path = Objects.requireNonNull(IPUtils.class.getClassLoader().getResource(resource_name)).getPath();
            cBuff = Searcher.loadContentFromFile(path);
        } catch (Exception e) {
            System.out.printf("failed to load content from `%s`: %s\n", resource_name, e);
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            System.out.printf("failed to create content cached searcher: %s\n", e);
        }
    }

    /**
     * 获取ip地址
     * @param request
     * @return
     */
    public static String getIP(ServerHttpRequest request){
        // 根据 HttpHeaders 获取 请求 IP地址
        String ip = request.getHeaders().getFirst("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("x-forwarded-for");
            if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                if (ip.contains(IP_UTILS_FLAG)) {
                    ip = ip.split(IP_UTILS_FLAG)[0];
                }
            }
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeaders().getFirst("X-Real-IP");
        }
        //兼容k8s集群获取ip
        if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddress().getAddress().getHostAddress();
            if (LOCALHOST_IP1.equalsIgnoreCase(ip) || LOCALHOST_IP.equalsIgnoreCase(ip)) {
                //根据网卡取本机配置的IP
                InetAddress iNet = null;
                try {
                    iNet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    log.error("getClientIp error: ", e);
                }
                ip = iNet.getHostAddress();
            }
        }
        return ip;
    }

    /**
     * 解析ip地址
     *
     * @param ip ip地址
     * @return 解析后的ip地址
     */
    private static String ip2Region(String ip)  {
        //解析ip地址，获取省市区
        String s = getRegion(ip);
//        System.out.println(s);
        Map map = JSONObject.parseObject(s, Map.class);
        Integer status = (Integer) map.get("status");
        String address = UNKNOWN;
        if(status == 0){
            Map result = (Map) map.get("result");
            Map addressInfo = (Map) result.get("ad_info");
            String nation = (String) addressInfo.get("nation");
            String province = (String) addressInfo.get("province");
            String city = (String) addressInfo.get("city");
            address = nation + "-" + province + "-" + city;
        }
        return address;
    }

    public static String getRegion(ServerHttpRequest request){
        String ip = getIP(request);
//        System.out.println(ip);
        String cityInfo = ip2Region(ip);
        return cityInfo;
    }

    /**
     * 根据ip2region解析ip地址
     * @param ip ip地址
     * @return 解析后的ip地址信息
     */
    public static String getRegion(String ip)  {
        if(searcher == null){
            log.error("Error: DbSearcher is null");
            return null;
        }
        try {
            String ipInfo = searcher.search(ip);
            if (!StringUtils.isEmpty(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    /**
//     * 根据在腾讯位置服务上申请的key进行请求解析ip
//     * https://lbs.qq.com/service/address_service/SmartGeocoder
//     * @param ip ip地址
//     * @return
//     */
//    public static String analyzeIp(String ip) {
//        StringBuilder result = new StringBuilder();
//        BufferedReader in = null;
//        try {
//            String url = format_url.replace("{}", ip);
//            URL realUrl = new URL(url);
//            // 打开和URL之间的链接
//            URLConnection connection = realUrl.openConnection();
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            // 创建实际的链接
//            connection.connect();
//            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(
//                    connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
//        } catch (Exception e) {
//            log.error("发送GET请求出现异常！异常信息为:{}",e.getMessage());
//        }
//        // 使用finally块来关闭输入流
//        finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return result.toString();
//    }

    /**
     * 获取访问设备
     *
     * @param request 请求
     * @return {@link UserAgent} 访问设备
     */
    public static UserAgent getUserAgent(ServerHttpRequest request){
        return UserAgent.parseUserAgentString(request.getHeaders().getFirst("User-Agent"));
    }

    /**
     * 获取IP地址
     *
     * @return 本地IP地址
     */
    public static String getHostIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }catch (UnknownHostException e){
        }
        return LOCALHOST_IP1;
    }

    /**
     * 获取主机名
     *
     * @return 本地主机名
     */
    public static String getHostName(){
        try{
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }
        return "未知";
    }

}