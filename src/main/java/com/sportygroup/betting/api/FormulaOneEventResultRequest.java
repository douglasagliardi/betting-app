package com.sportygroup.betting.api;

import java.util.Collection;

public record FormulaOneEventResultRequest(long eventId, Collection<FormulaOneRacer> placements) {}
