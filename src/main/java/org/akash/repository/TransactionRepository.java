package org.akash.repository;

import org.akash.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, UUID> {
    @Query("select t from Transaction t where t.isProcessed=false order by t.timestamp")
    Optional<List<Transaction>> findAllNotProcessed();
}
