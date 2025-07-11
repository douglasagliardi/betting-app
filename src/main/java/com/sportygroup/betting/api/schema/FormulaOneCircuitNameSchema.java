package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "popular name of a given event",
    example = "Melbourne",
    name = "circuit name",
    minLength = 3,
    maxLength = 150,
    type = "string"
)
public @interface FormulaOneCircuitNameSchema { }
