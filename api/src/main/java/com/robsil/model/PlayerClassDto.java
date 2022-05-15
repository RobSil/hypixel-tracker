package com.robsil.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PlayerClassDto extends PlayerClass{

    private String className;

    private BigDecimal exp;

    private int level;
}
