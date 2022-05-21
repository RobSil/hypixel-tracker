package com.robsil.model.mapstruct;

import com.robsil.data.domain.record.TotalRecord;
import com.robsil.model.TotalRecordDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TotalRecordMapper {

    TotalRecordMapper INSTANCE = Mappers.getMapper(TotalRecordMapper.class);

    TotalRecordDto totalRecordToTotalRecordDto(TotalRecord entity);

    TotalRecord totalRecordDtoToTotalRecord(TotalRecordDto dto);
}
