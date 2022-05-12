package com.practice.seckill.admin.service;


import com.practice.seckill.admin.bo.MemberCacheBO;
import com.practice.seckill.admin.dto.MemberDTO;
import com.practice.seckill.admin.dto.MemberQueryConditionDTO;
import com.practice.seckill.admin.vo.MemberRowVO;
import com.practice.seckill.admin.vo.PageVO;

public interface MemberService {
    /**
     * 获取会员缓存
     *
     * @param memberId memberId
     * @return bo
     */
    MemberCacheBO getCachedMember(Long memberId);

    /**
     * 重建会员缓存
     *
     * @param memberId memberId
     * @return bo
     */
    MemberCacheBO buildMemberCacheBO(Long memberId);

    /**
     * 删除会员缓存
     *
     * @param memberId memberId
     * @return 
     */
    void deleteMemberCacheBO(Long memberId);

    /**
     * 变更会员信息并更新缓存
     *
     * @param memberId memberId
     * @param dto     dto
     * @return dto
     */
    MemberDTO modifyMember(Long memberId, MemberDTO dto);

    /**
     * 创建会员
     *
     * @param dto dto
     * @return dto with id filled
     */
    MemberDTO createMember(MemberDTO dto);
    /**
     * page member
     *
//     * @param categoryId      会员分类
//     * @param memberName       会员名称(模糊)
//     * @param priceLowerBound 价格下界
//     * @param priceUpperBound 价格上界
//     * @param startTime       时间段左界（发布时间不早于）
//     * @param endTime         时间段右界（发布时间晚于）
//     * @param memberStatus     发布状态,0:未上架,1:已上架
//     * @param recommended     推荐状态,0:未推荐,1:已推荐
//     * @param pageNo          第几页
//     * @param pageSize        pageSize
     * @return page
     */
    PageVO<MemberRowVO> pageMember(MemberQueryConditionDTO dto);

   /**
    * 锁定会员
    *
    * @param memberId
    * @return Boolean
    */
    Boolean deleteMemberById(Long memberId);
}
