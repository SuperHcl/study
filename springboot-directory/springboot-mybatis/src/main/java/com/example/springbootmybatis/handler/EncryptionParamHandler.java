package com.example.springbootmybatis.handler;

import java.util.List;

/**
 * 入参加密处理器
 *
 * @author Hu.ChangLiang
 * @date 2023/6/4 20:31
 */
public class EncryptionParamHandler extends AbstractEncryptAndDecryptHandler {

    public EncryptionParamHandler(List<String> targetFields) {
        super(targetFields);
    }

    @Override
    protected String getOperatedContent(String content) {
        return content + "__加密后的";
    }


}
