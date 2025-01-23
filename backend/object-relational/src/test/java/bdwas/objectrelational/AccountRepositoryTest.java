package bdwas.objectrelational;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Stream;

import bdwas.models.Account;
import bdwas.models.Address;
import bdwas.models.Gender;
import bdwas.models.PersonalData;
import bdwas.models.Role;
import bdwas.repositories.AccountRepository;
import jakarta.persistence.EntityTransaction;

public class AccountRepositoryTest extends RepositoryBenchmarkBase<AccountRepository, Account, Long> {

    @Override
    protected Account generateEntity() {
        var address = Address.builder()
                             .country("Poland")
                             .city(faker.address().city())
                             .street(faker.address().streetAddress())
                             .postalCode(faker.address().postcode())
                             .build();

        var personalData = PersonalData.builder()
                                       .firstName(faker.name().malefirstName())
                                       .lastName(faker.name().lastName())
                                       .phoneNumber(faker.phoneNumber().phoneNumber())
                                       .address(address)
                                       .gender(Gender.MALE)
                                       .birthDate(faker.timeAndDate().birthday())
                                       .build();

        return Account.builder()
                      .email(faker.internet().emailAddress().replace("@", "-%s@".formatted(UUID.randomUUID())))
                      .password(faker.internet().password())
                      .role(Role.CLIENT)
                      .personalData(personalData)
                      .build();
    }

    @Override
    protected void updateEntities(Collection<Account> entities) {
        entities.forEach(entity -> entity.setPassword(faker.internet().password()));
    }

    @Override
    protected void clearTables() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Stream.of(Account.class)
              .forEachOrdered(entityClass -> {
                  String tableName = getTableName(entityClass);
                  entityManager.createNativeQuery("TRUNCATE TABLE " + tableName + " CASCADE")
                               .executeUpdate();
              });

        transaction.commit();


    }
}
