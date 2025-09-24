package com.hmall.aireader.translate;
import com.hmall.aireader.config.BaiduTransConfig;
import jakarta.annotation.Resource;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;



@Service
public class TranslateServiceImpl implements TranslateService {

    @Autowired
    private BaiduTransConfig baiduTransConfig;

    private String APP_ID =baiduTransConfig.getId();
    private String SECRET_KEY=baiduTransConfig.getSecret();
    private String API_URL=baiduTransConfig.getSalt();


    // OkHttp 客户端（复用连接，提高效率）
    private OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final Gson GSON = new Gson();

    private static class TranslateResponse {
        private String from; // 源语言
        private String to;   // 目标语言
        // 翻译结果列表（trans_result 对应 JSON 中的字段名）
        @SerializedName("trans_result")
        private List<TransResultItem> transResult;

        // 翻译结果项（每个 src-dst 对）
        private static class TransResultItem {
            private String src; // 原文
            private String dst; // 译文
        }

        // 获取译文（取第一个结果，百度 API 通常返回单条结果）
        public String getTranslation() {
            if (transResult == null || transResult.isEmpty()) {
                return null;
            }
            return transResult.get(0).dst;
        }
    }
    /**
     * 调用百度翻译 API 翻译英文到中文
     * @param english 待翻译的英文内容
     * @return 翻译结果（JSON 解析后的中文）
     */
    @Override
    public String translateToChinese(String english) {
        // 1. 校验输入
        if (english == null || english.trim().isEmpty()) {
            return "错误：请输入有效的英文内容！";
        }
        String query = english.trim();

        // 2. 生成 API 必要参数（多单词编码逻辑正确，无需修改）
        String q = URLEncoder.encode(query, StandardCharsets.UTF_8); // 如 "how are you" → "how+are+you"
        String from = "en"; // 源语言：英文
        String to = "zh";   // 目标语言：中文
        String salt = UUID.randomUUID().toString().replace("-", "").substring(0, 10); // 随机盐值
        // 签名生成：AppID + 编码后的原文 + 盐值 + 密钥，再 MD5 加密
        String sign = DigestUtils.md5Hex(APP_ID + query + salt + SECRET_KEY);

        // 3. 构建请求 URL（多单词编码后的 q 会自动拼入，API 支持）
        String url = API_URL + "?q=" + q + "&from=" + from + "&to=" + to +
                "&appid=" + APP_ID + "&salt=" + salt + "&sign=" + sign;

        // 4. 发送 HTTP GET 请求
        Request request = new Request.Builder().url(url).build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            // 5. 解析响应（核心修复：用 Gson 替代字符串截取）
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                return "翻译失败（HTTP错误）：" + response.code() + "，详情：" + responseBody;
            }

            // 用 Gson 解析 JSON，自动处理 Unicode 转义（如 \u4f60\u597d → 你好）
            TranslateResponse translateResponse = GSON.fromJson(responseBody, TranslateResponse.class);
            String translation = translateResponse.getTranslation();

            // 6. 返回结果
            return translation != null ? translation : "未找到翻译结果";

        } catch (IOException e) {
            return "网络错误：" + e.getMessage();
        } catch (Exception e) {
            return "解析错误：" + e.getMessage();
        }

    }
}
