package com.sportygroup.betting.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.Collection;

public record FormulaOneEventResultRequest(@Min(1) @Max(Integer.MAX_VALUE) long eventId,
                                           @Size(min = 1) Collection<FormulaOneRacer> placements) { }
