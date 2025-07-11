package com.sportygroup.betting.api;

import com.sportygroup.betting.api.schema.FormulaOneDriverIdSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record FormulaOneRacer(
    @Schema(implementation = FormulaOneDriverIdSchema.class) @Min(1) @Max(Integer.MAX_VALUE) long driverId,
    @Schema(implementation = FormulaOneDriverIdSchema.class) @Min(1) @Max(Integer.MAX_VALUE)int position) { }
