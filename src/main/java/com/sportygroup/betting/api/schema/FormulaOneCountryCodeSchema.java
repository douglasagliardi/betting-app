package com.sportygroup.betting.api.schema;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    description = "country code (3 letter) which the event takes place",
    example = "ESP",
    minLength = 3,
    maxLength = 3,
    name = "country_code",
    type = "string"
)
public @interface FormulaOneCountryCodeSchema { }
