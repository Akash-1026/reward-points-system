package org.akash.repository;

import org.akash.entity.Payer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayerRepository extends CrudRepository<Payer, UUID> {
    @Query("select c from Payer c where c.name=:name")
    Optional<Payer> findByName(@Param("name") String payer);
}
