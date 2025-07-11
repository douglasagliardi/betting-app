package com.sportygroup.betting.domain;

import com.sportygroup.betting.api.schema.FormulaOneDriverIdSchema;
import com.sportygroup.betting.api.schema.FormulaOneDriverNameSchema;
import com.sportygroup.betting.api.schema.FormulaOneDriverOddSchema;
import io.swagger.v3.oas.annotations.media.Schema;

public record FormulaOneDriver(
    @Schema(implementation = FormulaOneDriverIdSchema.class) int id,
    @Schema(implementation = FormulaOneDriverNameSchema.class) String displayName,
    @Schema(implementation = FormulaOneDriverOddSchema.class) int odd) {}
