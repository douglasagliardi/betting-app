package com.sportygroup.betting.api;

import com.sportygroup.betting.api.schema.FormulaOneEventIdSchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.Collection;

public record FormulaOneEventResultRequest(@Schema(implementation = FormulaOneEventIdSchema.class) @Min(1) @Max(Integer.MAX_VALUE) long eventId,
                                           @Size(min = 1) Collection<FormulaOneRacer> placements) { }
