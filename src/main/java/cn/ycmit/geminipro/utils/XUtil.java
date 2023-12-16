package cn.ycmit.geminipro.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

public class XUtil {

    public static String getGeminiBody(String text,String key){
        HttpResponse<String> response = Unirest.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key="+key)
                .header("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .header("Host", "generativelanguage.googleapis.com")
                .header("Connection", "keep-alive")
                .body(XUtil.textJson(text))
                .asString();
        String res= response.getBody();
        return res;
    }
    public static String getOutput(String res){
        String output ="";
        try {
            JSONObject jsonObject = JSON.parseObject(res);
            output = jsonObject.getJSONArray("candidates").getJSONObject(0).getJSONObject("content").getJSONArray("parts").getJSONObject(0).getString("text");
        }catch (Exception e){

        }
        return output;
    }
    public static String textJson(String text){
        String jsonStr= "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \""+text+"\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"generationConfig\": {\n" +
                "    \"temperature\": 0.9,\n" +
                "    \"topK\": 1,\n" +
                "    \"topP\": 1,\n" +
                "    \"maxOutputTokens\": 2048,\n" +
                "    \"stopSequences\": []\n" +
                "  },\n" +
                "  \"safetySettings\": [\n" +
                "    {\n" +
                "      \"category\": \"HARM_CATEGORY_HARASSMENT\",\n" +
                "      \"threshold\": \"BLOCK_MEDIUM_AND_ABOVE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"category\": \"HARM_CATEGORY_HATE_SPEECH\",\n" +
                "      \"threshold\": \"BLOCK_MEDIUM_AND_ABOVE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"category\": \"HARM_CATEGORY_SEXUALLY_EXPLICIT\",\n" +
                "      \"threshold\": \"BLOCK_MEDIUM_AND_ABOVE\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"category\": \"HARM_CATEGORY_DANGEROUS_CONTENT\",\n" +
                "      \"threshold\": \"BLOCK_MEDIUM_AND_ABOVE\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        return jsonStr;
    }
}
