package com.sportygroup.betting.domain;

import com.sportygroup.betting.api.schema.FormulaOneCountryCodeSchema;
import com.sportygroup.betting.api.schema.FormulaOneCountrySchema;
import io.swagger.v3.oas.annotations.media.Schema;

public record FormulaOneEventLocation(
    @Schema(implementation = FormulaOneCountrySchema.class) String country,
    @Schema(implementation = FormulaOneCountryCodeSchema.class) String countryCode) { }
