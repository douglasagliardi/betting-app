package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "full name of a given driver",
    example = "Michael Schumacher",
    name = "full_name",
    type = "string"
)
public @interface FormulaOneDriverNameSchema { }
