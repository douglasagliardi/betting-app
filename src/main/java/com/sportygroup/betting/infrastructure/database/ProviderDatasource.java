package com.sportygroup.betting.infrastructure.database;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class ProviderDatasource extends BaseEntity {

  @Id
  private long id;
  @Enumerated(EnumType.STRING)
  private SportyType type;
  private String provider;

  public ProviderDatasource() {
  }

  public long getId() {
    return id;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public SportyType getType() {
    return type;
  }

  public void setType(final SportyType type) {
    this.type = type;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(final String provider) {
    this.provider = provider;
  }
}
