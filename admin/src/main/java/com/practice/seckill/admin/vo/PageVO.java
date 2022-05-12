package com.practice.seckill.admin.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> implements Serializable {

    private final static Integer DEFAULT_PAGE_SIZE = 10;

    @ApiModelProperty(notes = "每页显示数量")
    private Integer pageSize;

    @ApiModelProperty(notes = "总页数")
    private Long pages;

    @ApiModelProperty(notes = "当前页码")
    private Integer pageNo;

    @ApiModelProperty(notes = "总记录数")
    private Long total;

    @ApiModelProperty(notes = "是否有下一页")
    private Boolean hasNext;

    @ApiModelProperty(notes = "是否有上一页")
    private Boolean hasPre;

    @ApiModelProperty(notes = "数据列表")
    private List<T> records;

    /**
     * 分页构造器
     *
     * @param pageNo   当前页数
     * @param pageSize 每页显示多少
     * @param total    总记录数
     */
    public PageVO(Integer pageNo, Integer pageSize, Integer total) {
        this(pageNo, pageSize, total.longValue());
    }

    /**
     * 分页构造器
     *
     * @param pageNo   当前页数
     * @param pageSize 每页显示多少
     * @param total    总记录数
     * @param records  records
     */
    public PageVO(Integer pageNo, Integer pageSize, Long total, List<T> records) {
        this(pageNo, pageSize, total);
        this.records = records;
    }

    /**
     * 分页构造器
     *
     * @param pageSize 每页显示多少
     * @param pageNo   当前页数
     * @param total    总记录数
     */
    public PageVO(Integer pageNo, Integer pageSize, Long total) {
        if (pageSize < 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        this.pageSize = pageSize;
        this.total = total;
        this.pages = (total + pageSize - 1) / pageSize;

        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pages == 0) {
            pageNo = 1;
        } else {
            if (pageNo != 1 && pageNo > pages) {
                pageNo = pages.intValue();
            }
        }
        //是否有上一页
        this.hasPre = pageNo > 1;
        //是否有下一页
        this.hasNext = pageNo.longValue() < pages;

        this.pageNo = pageNo;
    }


    public static <T> PageVO<T> transform(IPage<T> page) {
        return transform(page, page.getRecords());
    }

    public static <T> PageVO<T> transform(IPage<T> page, List<T> records) {
        return new PageVO<>((int) page.getCurrent(), (int) page.getSize(), page.getTotal(), records);
    }

}

