package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "position on the grid which a given driver have in an event. This will be used to evaluate bet winners",
    example = "1",
    name = "position",
    type = "int"
)
public @interface FormulaOneDriverPositionSchema { }
