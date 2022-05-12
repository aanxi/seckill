package com.practice.seckill.admin.service.impl;


import com.practice.seckill.admin.bo.MemberCacheBO;
import com.practice.seckill.admin.constant.RdsKeyGenor;
import com.practice.seckill.admin.dto.MemberDTO;
import com.practice.seckill.admin.dto.MemberQueryConditionDTO;
import com.practice.seckill.admin.mapper.MemberMapper;
import com.practice.seckill.admin.service.MemberService;
import com.practice.seckill.admin.vo.MemberRowVO;
import com.practice.seckill.admin.vo.PageVO;
import com.practice.seckill.common.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MemberMapper memberMapper;


    @Override
    public MemberCacheBO getCachedMember(Long memberId) {
        MemberCacheBO MemberCacheBO = (MemberCacheBO) redisTemplate.opsForValue().get(RdsKeyGenor.memberKey(memberId));
        if (MemberCacheBO != null) {
            return MemberCacheBO;
        }
        return buildMemberCacheBO(memberId);
    }

    @Override
    public MemberCacheBO buildMemberCacheBO(Long memberId) {
        Member member = memberMapper.selectById(memberId);
        return buildMemberCacheBO(member);
    }

    private MemberCacheBO buildMemberCacheBO(Member member) {
        MemberCacheBO memberCache = MemberCacheBO.builder()
                .headImageUrl(member.getHeadImageUrl())
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .district(member.getDistrict())
                .lockStatus(member.getLockStatus())
                .createAt(member.getCreateAt())
                .build();
        redisTemplate.opsForValue().set(
                RdsKeyGenor.memberKey(member.getId()),
                memberCache,
                RdsKeyGenor.memberExpire());
        return memberCache;
    }

    @Override
    @Transactional
    public MemberDTO modifyMember(Long memberId, MemberDTO dto) {
        Member member = Member.builder()
                .id(memberId)
                .headImageUrl(dto.getHeadImageUrl())
                .nickname(dto.getNickname())
                .phoneNumber(dto.getPhoneNumber())
                .district(dto.getDistrict())
                .lockStatus(0)
                .createAt(dto.getCreateAt())
                .build();
        if(memberMapper.updateById(member) > 0) {
            //更新缓存
            buildMemberCacheBO(member);
            return dto;
        }
        return null;
    }
    @Override
    public void deleteMemberCacheBO(Long memberId) {
        String redisKey = RdsKeyGenor.memberKey(memberId);
        redisTemplate.delete(redisKey);
    }

    /**
     * 锁定用户
     *
     * @param memberId memberId
     * @return boolean
     */
    @Override
    public Boolean deleteMemberById(Long memberId) {
        Member member = Member.builder()
                .id(memberId)
                .lockStatus(1)
                .build();
        if(memberMapper.updateById(member) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public PageVO<MemberRowVO> pageMember(MemberQueryConditionDTO dto) {
        List<MemberRowVO> memberList = memberMapper.listMember(dto.getLockStatus(),
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getNickname(),
                dto.getPhoneNumber()
        );
        PageVO<MemberRowVO> page =  new PageVO(dto.getPageNo(), dto.getPageSize(), (long) memberList.size());
        page.setRecords(memberList);
        return page;
    }

    @Override
    public MemberDTO createMember(MemberDTO dto) {
        Member newMember = Member.builder()
                .headImageUrl(dto.getHeadImageUrl())
                .nickname(dto.getNickname())
                .phoneNumber(dto.getPhoneNumber())
                .district(dto.getDistrict())
                .lockStatus(dto.getLockStatus())
                .createAt(dto.getCreateAt()==null? LocalDateTime.now():dto.getCreateAt())
                .build();
        memberMapper.insert(newMember);
        dto.setId(newMember.getId());
        return dto;
    }

}
