package com.lixlim.rest_api.memo.mapper;

import com.lixlim.rest_api.memo.dto.MemoCreateRequest;
import com.lixlim.rest_api.memo.dto.MemoServiceInDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemoCreateRequestMapper {
    MemoCreateRequestMapper INSTANCE = Mappers.getMapper(MemoCreateRequestMapper.class);
    MemoCreateRequest toServiceInDto(MemoServiceInDto inDto);
}
