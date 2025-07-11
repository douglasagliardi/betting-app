package com.sportygroup.betting.domain;

import com.sportygroup.betting.api.schema.FormulaOneCircuitNameSchema;
import com.sportygroup.betting.api.schema.FormulaOneIdSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

public record FormulaOneEvent(
    @Schema(implementation = FormulaOneIdSchema.class) int id,
    @Schema(implementation = FormulaOneCircuitNameSchema.class) String circuitName,
    FormulaOneEventLocation location,
    Collection<FormulaOneDriver> drivers) { }
