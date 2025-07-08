package com.sportygroup.betting.infrastructure.externalapi.formulaone;

import java.util.Collection;

public record FormulaOneEvent(int id, String circuitName, FormulaOneEventLocation location, Collection<FormulaOneDriver> drivers) { }
