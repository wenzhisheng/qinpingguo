package com.common.model.base;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.util.List;


/**
 * @Author A jun
 * @ClassName com.com.member.member.common.model
 * @Description 分页返回对象
 * @Date 2018/3/26 0026 14:27
 * @Version 1.0
 */
public class PageResult<T> {
    /** 页码，默认是第一页 */
    @ApiModelProperty(value = "页码，默认是第一页")
    private int pageNo = 1;
    /** 每页显示的记录数，默认是15 */
    @ApiModelProperty(value = "每页显示的记录数，默认是15")
    private int pageSize = 15;
    /** 总记录数 */
    @ApiModelProperty(value = "总记录数")
    private int totalRecord;
    /** 总页数 */
    @ApiModelProperty(value = "总页数")
    private int totalPage;
    /** 结果列表集 */
    @ApiModelProperty(value = "")
    private List<T> result;

    public PageResult() {

    }

    public PageResult(int pageNo, int pageSize, int totalRecord,
                List<T> results) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalRecord = totalRecord;
        this.setResult(results);
        int totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : totalRecord / pageSize + 1;
        this.setTotalPage(totalPage);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
