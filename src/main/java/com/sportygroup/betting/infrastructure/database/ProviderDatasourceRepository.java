package com.sportygroup.betting.infrastructure.database;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderDatasourceRepository extends JpaRepository<ProviderDatasource, Long> {

  Optional<ProviderDatasource> findByType(SportyType id);
}
