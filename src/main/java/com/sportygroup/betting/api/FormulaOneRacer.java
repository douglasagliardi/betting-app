package com.sportygroup.betting.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record FormulaOneRacer(@Min(1) @Max(Integer.MAX_VALUE) long driverId,
                              @Min(1) @Max(Integer.MAX_VALUE)int position) { }
