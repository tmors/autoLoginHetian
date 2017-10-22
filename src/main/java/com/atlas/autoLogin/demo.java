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
import java.util.List;
import java.util.Map;

@RestController
public class demo {
    private static final Logger LOGGER = LoggerFactory.getLogger(demo.class);

    @RequestMapping("/hello")
    public String register() {
        return "hello world";
    }

    @RequestMapping("/autoLoginHetian")
    public String autoLogin() {
        return "autoLogin";
    }

    @Scheduled(cron = "* */30 * * * *")
    public void autoLoginHetian() throws IOException {
        String url = "http://www.hetianlab.com/AllExperiments.jsp";
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
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Cookie", "__qc_wId=767; pgv_pvid=9734467974; token=ff02b158d4ac2cd99b454cd96b378da1; userName=398080994@qq.com; JSESSIONID=82A7116C97887CF014E6C0DFAAE9AFC4.jvm3; Hm_lvt_154e641631a6b80d11431eed01e625c4=1508649861; Hm_lpvt_154e641631a6b80d11431eed01e625c4=1508657804");
            conn.setRequestProperty("Host", "www.hetianlab.com");
            conn.setRequestProperty("Referer", "http://www.hetianlab.com/pages/profile.jsp?w=dyns&u=REG-81cf-dd70-4f12-80c2-842b26394ca9");
            conn.setRequestProperty("Upgrade-Insecure-Requests", "1");


            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");

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
            System.out.println(result);
        } catch (Exception e) {
            LOGGER.debug("connection exception");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        System.out.println("schedule start");
    }
}
