package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "the chance which a given driver has to win a given event (it's dynamic)",
    example = "4",
    name = "odd",
    type = "int"
)
public @interface FormulaOneDriverOddSchema { }
