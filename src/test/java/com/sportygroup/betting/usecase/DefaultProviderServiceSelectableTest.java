package com.sportygroup.betting.usecase;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sportygroup.betting.infrastructure.database.ProviderDatasource;
import com.sportygroup.betting.infrastructure.database.ProviderDatasourceRepository;
import com.sportygroup.betting.infrastructure.database.SportyType;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.FormulaOneApiService;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.livepulse.LivePulseF1ApiService;
import com.sportygroup.betting.infrastructure.externalapi.formulaone.client.openf1.OpenF1ApiService;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Provider Datasource Selectable Tests")
@ExtendWith(MockitoExtension.class)
class DefaultProviderServiceSelectableTest {

  @Mock
  private ProviderDatasourceRepository repository;

  @DisplayName("get registered provider datasource should return matching API client implementation")
  @Test
  void existingProviderConfigurationShouldReturnExpectedF1ApiInterface() {

    //arrange
    final var mockedOpenF1ApiService = Mockito.mock(OpenF1ApiService.class);
    final Collection<FormulaOneApiService> formulaOneApiCandidates = List.of(mockedOpenF1ApiService);
    final var service = new DefaultProviderServiceSelectable(repository, formulaOneApiCandidates);

    final var providerDatasourceConfig = new ProviderDatasource();
    providerDatasourceConfig.setProvider("open-f1");

    when(mockedOpenF1ApiService.getProvider()).thenCallRealMethod();
    when(repository.findByType(SportyType.FORMULA_ONE)).thenReturn(Optional.of(providerDatasourceConfig));

    //act
    final var result = service.getApiService();

    //assert
    assertInstanceOf(OpenF1ApiService.class, result);
    verify(repository).findByType(SportyType.FORMULA_ONE);
    verify(mockedOpenF1ApiService).getProvider();
  }

  @DisplayName("when no provider datasource is provided fallback should be returned to avoid exceptions and jeopardize customer journey")
  @Test
  void getProviderApiClientWithNonRegisteredProviderDatasourceShouldReturnFallback() {

    //arrange
    final var mockedOpenF1ApiService = Mockito.mock(OpenF1ApiService.class);
    final Collection<FormulaOneApiService> formulaOneApiCandidates = List.of(mockedOpenF1ApiService);
    final var service = new DefaultProviderServiceSelectable(repository, formulaOneApiCandidates);

    when(mockedOpenF1ApiService.getProvider()).thenCallRealMethod();
    when(mockedOpenF1ApiService.isFallback()).thenReturn(true);

    //act
    final var result = service.getApiService();

    //assert
    assertInstanceOf(OpenF1ApiService.class, result);
    verify(repository).findByType(SportyType.FORMULA_ONE);
    verify(mockedOpenF1ApiService).getProvider();
    verify(mockedOpenF1ApiService).isFallback();
  }

  @DisplayName("when no provider datasource is provided and not even fallback is available, then throw an exception")
  @Test
  void getProviderApiClientWithNonRegisteredProviderDatasourceShouldThrowException() {

    //arrange
    final var mockedOpenF1ApiService = Mockito.mock(LivePulseF1ApiService.class);
    final Collection<FormulaOneApiService> formulaOneApiCandidates = List.of(mockedOpenF1ApiService);
    final var service = new DefaultProviderServiceSelectable(repository, formulaOneApiCandidates);

    when(mockedOpenF1ApiService.getProvider()).thenCallRealMethod();

    //act
    assertThrows(NoSuchElementException.class, service::getApiService);

    //assert
    verify(repository).findByType(SportyType.FORMULA_ONE);
    verify(mockedOpenF1ApiService).getProvider();
    verify(mockedOpenF1ApiService).isFallback();
  }
}