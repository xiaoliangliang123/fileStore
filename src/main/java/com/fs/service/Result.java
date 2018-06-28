package com.fs.service;

/**
 * @Auther: WANG_LIANG(XIAO)
 * @Date: 2018/6/27 10:09
 * @Description:
 */
public class Result {

    public Integer totalCount = 0;
    public Integer successCount = 0;
    public  Integer failedCount = 0;

    public Result(){
        successCount = 0;
        failedCount = 0 ;
        totalCount = 0;
    }
}
