package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "unique identifier of a given event",
    example = "7787",
    minLength = 1,
    name = "id",
    type = "long"
)
public @interface FormulaOneEventIdSchema { }
