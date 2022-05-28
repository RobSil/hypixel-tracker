package com.robsil.model.mapstruct;

import com.robsil.data.domain.record.TotalRecord;
import com.robsil.model.*;
import com.robsil.util.ExperienceUtil;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Log4j2
public abstract class TotalRecordMapper {

    ExperienceUtil experienceUtil = new ExperienceUtil();

    public static final TotalRecordMapper INSTANCE = Mappers.getMapper(TotalRecordMapper.class);


    protected PlayerClassRecordInfoDto handlePlayerClassRecord(TotalRecord entity) {
        PlayerClassRecordInfoDto playerClassRecord = entity.getPlayerClassRecord();

        if (playerClassRecord != null) {
            playerClassRecord.setPlayerClasses(playerClassRecord.getPlayerClasses()
                                                       .stream()
                                                       .map(playerClass -> new PlayerClassDto(playerClass.getClassName(),
                                                                                              playerClass.getExp(),
                                                                                              experienceUtil.dungeoneeringXpToLevel(playerClass.getExp().intValue())))
                                                       .collect(Collectors.toList()));


        }
        return playerClassRecord;
    }

    protected ExperienceSkillRecordInfoDto handleExperienceSkillRecord(TotalRecord entity) {
        ExperienceSkillRecordInfoDto experienceSkillRecord = entity.getExperienceSkillRecord();

        if (experienceSkillRecord != null) {
            experienceSkillRecord
                    .setExperienceSkills(experienceSkillRecord.getExperienceSkills() != null ?
                                                 experienceSkillRecord.getExperienceSkills()
                                                         .stream()
                                                         .map(experienceSkill -> new ExperienceSkillDto(experienceSkill.getSkillName(),
                                                                                                        experienceSkill.getSkillEntity(),
                                                                                                        experienceSkill.getExp(),
                                                                                                        experienceUtil.universalXpToLevel(experienceSkill.getExp().intValue())))
                                                         .collect(Collectors.toList()) : null);
        }

        return experienceSkillRecord;
    }

    @Mapping(target = "playerClassRecord", expression = "java(handlePlayerClassRecord(entity))")
    @Mapping(target = "experienceSkillRecord", expression = "java(handleExperienceSkillRecord(entity))")
    public abstract TotalRecordDto toTotalRecordDto(TotalRecord entity);

    public abstract TotalRecord toTotalRecord(TotalRecordDto dto);
}
