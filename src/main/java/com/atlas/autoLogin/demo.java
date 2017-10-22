package com.atlas.autoLogin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class demo {
    private static final Logger LOGGER = LoggerFactory.getLogger(demo.class);
    private StringBuffer log = new StringBuffer();

    @RequestMapping("/hello")
    public String register() {
        try {
            this.autoLoginHetian();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return log.toString();
    }

    @RequestMapping("/autoLoginHetian")
    public String autoLogin() {
        return "autoLogin";
    }

    @Scheduled(cron = "0 0/10 * * * *")
    public void autoLoginHetian() throws IOException {
        LOGGER.info("start to autoLogin Hetian");
        String firsUrl = "http://www.hetianlab.com/pages/personalCenter.jsp";
        String action = "http://www.hetianlab.com/userInfo!getUserHeadInfo.action";
        String outc = "http://www.hetianlab.com/outUserInfo!getUserInfo.action";
        String[] urls = new String[]{firsUrl, action};
        for (String url : urls) {
            URL realUrl = new URL(url);
            PrintWriter out = null;
            BufferedReader in = null;
            String result = "";
            String param = "";
            // 打开和URL之间的连接
            try {
                URLConnection conn = realUrl.openConnection();
                // 设置通用的请求属性
                conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.81");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("Cache-Contro", "max-age=0");
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
                conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                conn.setRequestProperty("Cookie", "__qc_wId=767; pgv_pvid=9734467974; Hm_lvt_154e641631a6b80d11431eed01e625c4=1508649861; Hm_lpvt_154e641631a6b80d11431eed01e625c4=1508679209; userName=398080994@qq.com; JSESSIONID=B597AE90C63A86C61DB3D768353EF496.jvm3; token=ff02b158d4ac2cd99b454cd96b378da1");
                conn.setRequestProperty("Host", "www.hetianlab.com");
                conn.setRequestProperty("Referer", "http://www.hetianlab.com/AllExperiments.jsp");
                conn.setRequestProperty("userId", "REG-81cf-dd70-4f12-80c2-842b26394ca9");
                conn.setRequestProperty("Upgrade-Insecure-Requests", "1");


                conn.setRequestProperty("user-agent",
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

                if (url.contains("action")) {
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    out = new PrintWriter(conn.getOutputStream());
                    out.print("userId=REG-81cf-dd70-4f12-80c2-842b26394ca9");
                    out.flush();
                    in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        result += line;
                    }
                    if (url.contains("getUserHeadInfo") && result.contains("18107346803")) {
                        this.log.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        this.log.append("  ");
                        this.log.append("getUserHeadInfo Success");
                        int index = result.indexOf("cion");
                        this.log.append("coin is:" + result.substring(index + 6, index + 8));
                        this.log.append(System.getProperty("line.separator"));
                        this.log.append("</br>");
                        LOGGER.info(this.log.toString());
                    } else {
                        LOGGER.info("cookie out of date ");
                    }

                    continue;
                }

                conn.connect();
                // 获取URLConnection对象对应的输出流
                Map<String, List<String>> map = conn.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    System.out.println(key + "--->" + map.get(key));
                }
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                LOGGER.info("connection exception");
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }

        }
    }
}
