package com.sportygroup.betting.domain;

import java.util.Collection;

public record FormulaOneEvent(int id, String circuitName, FormulaOneEventLocation location, Collection<FormulaOneDriver> drivers) { }
