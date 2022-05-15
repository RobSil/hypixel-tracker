package com.robsil.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceSkillDto extends ExperienceSkill {

    private String skillName;

    private String skillEntity;

    private BigDecimal exp;

    private int level;
}
