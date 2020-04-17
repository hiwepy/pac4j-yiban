package org.pac4j.yiban;

import com.alibaba.fastjson.JSONObject;
import org.pac4j.core.context.ContextHelper;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.exception.CredentialsException;
import org.pac4j.core.ext.credentials.extractor.TokenParameterExtractor;

import java.util.Optional;

/**
 * @ClassName YiBanTokenParameterExtractor
 * @Description 易班轻应用提取参数
 * @Author zd
 * @Date 2019/12/4 15:33
 * @Version 1.0
 **/
@SuppressWarnings("all")
public class YiBanTokenParameterExtractor extends TokenParameterExtractor {


    public YiBanTokenParameterExtractor(String parameterName) {
        super(parameterName);
    }

    public YiBanTokenParameterExtractor(String parameterName, boolean supportGetRequest, boolean supportPostRequest) {
        super(parameterName, supportGetRequest, supportPostRequest);
    }

    public YiBanTokenParameterExtractor(String parameterName, boolean supportGetRequest, boolean supportPostRequest, String charset) {
        super(parameterName, supportGetRequest, supportPostRequest, charset);
    }

    @Override
    public Optional<TokenCredentials> extract(WebContext context) {
        logger.debug("supportGetRequest: {}", this.isSupportGetRequest());
        logger.debug("supportPostRequest: {}", this.isSupportPostRequest());
        if (ContextHelper.isGet(context) && !isSupportGetRequest()) {
            throw new CredentialsException("GET requests not supported");
        } else if (ContextHelper.isPost(context) && !isSupportPostRequest()) {
            throw new CredentialsException("POST requests not supported");
        }
        logger.debug("ParameterName: {}", this.getParameterName());
        Optional<String> value = context.getRequestParameter(this.getParameterName());
        if (!value.isPresent()) {
            return Optional.empty();
        }
        logger.debug("RequestContent: {}", context.getRequestContent());
        logger.debug("RequestParameters: {}", JSONObject.toJSONString(context.getRequestParameters()));
        String tokenString = value.get();
        logger.debug("token : {}", tokenString);
        return Optional.of(new TokenCredentials(tokenString));
    }
}
