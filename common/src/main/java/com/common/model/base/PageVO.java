package com.common.model.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页参数对象
 */
@ApiModel(value="分页参数对象",description="分页参数对象")
public class PageVO implements Serializable {

    private static final long serialVersionUID = -6543060569690235452L;
    /** 页码 */
    @ApiModelProperty(value="页码",hidden=true)
    private int pageNo = 1;
    /** 每页显示的记录数，默认是15 */
    @ApiModelProperty(value="每页显示的记录数",hidden=true)
    private int pageSize = 10;
    /** 开始索引 */
    @ApiModelProperty(value="开始索引",hidden=true)
    private int startIndex;
    /** 结束索引 */
    @ApiModelProperty(value="结束索引",hidden=true)
    private int endIndex;

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

    public int getStartIndex() {
        return (this.pageNo -1)*pageSize;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex = this.pageSize;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
