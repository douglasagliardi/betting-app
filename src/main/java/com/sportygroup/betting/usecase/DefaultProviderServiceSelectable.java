package com.sportygroup.betting.usecase;

import com.sportygroup.betting.infrastructure.database.ProviderDatasource;
import com.sportygroup.betting.infrastructure.database.ProviderDatasourceRepository;
import com.sportygroup.betting.infrastructure.database.SportyType;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.FormulaOneApiService;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class DefaultProviderServiceSelectable implements ProviderServiceSelectable {

  private final ProviderDatasourceRepository repository;
  private final Collection<FormulaOneApiService> formulaOneServices;

  public DefaultProviderServiceSelectable(final ProviderDatasourceRepository repository,
      final Collection<FormulaOneApiService> formulaOneServices) {
    this.repository = repository;
    this.formulaOneServices = formulaOneServices;
  }

  @Override
  public FormulaOneApiService getApiService() {
    final var provider = repository.findByType(SportyType.FORMULA_ONE)
        .map(ProviderDatasource::getProvider)
        .orElseThrow();
    return formulaOneServices.stream()
        .filter(e -> e.getProvider().equalsIgnoreCase(provider))
        .findFirst()
        .orElseThrow();
  }
}
