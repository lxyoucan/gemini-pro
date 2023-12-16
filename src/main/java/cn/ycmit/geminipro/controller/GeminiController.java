package cn.ycmit.geminipro.controller;


import cn.ycmit.geminipro.utils.XUtil;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/chat")
public class GeminiController {
    @Value("${gemini.key}")
    private String key;

    @Value("${gemini.proxy}")
    private String proxy;

    @Value("${gemini.proxyIp}")
    private String proxyIp;

    @Value("${gemini.proxyPort}")
    private String proxyPort;

    @PostMapping("/msg")
    public String msg(String text)
    {
        if("true".equals(proxy.trim())){
            //设置代理服务
            Unirest.config().reset();
            Unirest.config().proxy(proxyIp, Integer.parseInt(proxyPort));
        }
        String res= XUtil.getGeminiBody(text,key);
        if(res.indexOf("candidates")<=0){
            return res;
        }
        return XUtil.getOutput(res);
    }

}
