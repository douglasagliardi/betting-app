package com.sportygroup.betting.domain;

import java.util.Optional;

public record FormulaOneParams(String eventType, String countryCode, Integer year) {

  public static final class Builder {

    String eventType;
    String countryCode;
    Integer year;

    public FormulaOneParams.Builder withEventType(final String eventType) {
      Optional.ofNullable(eventType).ifPresent(value -> this.eventType = value);
      return this;
    }

    public FormulaOneParams.Builder withCountryCode(final String countryCode) {
      Optional.ofNullable(countryCode).ifPresent(value -> this.countryCode = value);
      return this;
    }

    public FormulaOneParams.Builder withYear(final Integer year) {
      Optional.ofNullable(year).ifPresent(value -> this.year = value);
      return this;
    }

    public FormulaOneParams build() {
      return new FormulaOneParams(eventType, countryCode, year);
    }
  }
}
