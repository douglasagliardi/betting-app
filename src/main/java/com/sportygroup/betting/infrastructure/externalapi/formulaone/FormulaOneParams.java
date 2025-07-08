package com.sportygroup.betting.infrastructure.externalapi.formulaone;

public record FormulaOneParams(String eventType, String countryCode, Integer year) {

  public static final class Builder {

    String eventType;
    String countryCode;
    Integer year;

    public FormulaOneParams.Builder withEventType(final String eventType) {
      this.eventType = eventType;
      return this;
    }

    public FormulaOneParams.Builder withCountryCode(final String countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public FormulaOneParams.Builder withYear(final int year) {
      this.year = year;
      return this;
    }
  }
}
