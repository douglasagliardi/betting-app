package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "unique identifier of a given driver",
    example = "50",
    name = "driver_id",
    type = "string"
)
public @interface FormulaOneDriverIdSchema { }
