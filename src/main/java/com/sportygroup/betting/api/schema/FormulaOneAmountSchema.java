package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "monetary representation.",
    example = "50",
    name = "amount",
    type = "int"
)
public @interface FormulaOneAmountSchema { }
