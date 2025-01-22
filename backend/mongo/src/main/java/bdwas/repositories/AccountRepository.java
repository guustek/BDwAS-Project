package bdwas.repositories;

import java.util.Optional;

import bdwas.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findAccountByEmail(String email);
}
