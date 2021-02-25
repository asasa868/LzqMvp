package com.shuyi.lzqmvp.baseMVP.net;

/**
 * created by Lzq
 * on 2021/2/24 0024
 * Describe ：
 */
public class ApiException extends RuntimeException {
    private String mErrorCode;

    public ApiException(String errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    /**
     * 判断是否是token失效
     *
     * @return 失效返回true, 否则返回false;
     */
    public boolean isTokenExpried() {
        return mErrorCode.equals("0");
    }
}