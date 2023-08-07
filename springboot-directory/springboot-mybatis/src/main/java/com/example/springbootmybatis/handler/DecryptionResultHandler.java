package com.example.springbootmybatis.handler;

import java.util.List;

/**
 * 解密
 *
 * @author Hu.ChangLiang
 * @date 2023/6/1 09:53
 */
public class DecryptionResultHandler extends AbstractEncryptAndDecryptHandler {

    public DecryptionResultHandler(List<String> targetFields) {
        super(targetFields);
    }

    @Override
    protected String getOperatedContent(String content) {
        return content + "__解密后的";
    }

}
