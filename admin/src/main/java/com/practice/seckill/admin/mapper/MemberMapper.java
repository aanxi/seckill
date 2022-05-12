package com.practice.seckill.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.practice.seckill.admin.vo.MemberRowVO;
import com.practice.seckill.common.entity.Member;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    List<MemberRowVO> listMember(
            @Param("lockStatus") Integer lockStatus,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("nickname") String nickname,
            @Param("phoneNumber") String phoneNumber);

    Long getMaxId();
}
