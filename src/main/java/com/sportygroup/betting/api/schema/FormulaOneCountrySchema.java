package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "country name which the event takes place",
    example = "Spain",
    name = "country",
    type = "string"
)
public @interface FormulaOneCountrySchema { }
