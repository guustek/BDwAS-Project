package bdwas.objectrelational;

import java.util.List;
import java.util.UUID;

import bdwas.models.Account;
import bdwas.models.Address;
import bdwas.models.BaseEntity;
import bdwas.models.Gender;
import bdwas.models.PersonalData;
import bdwas.models.Role;
import bdwas.repositories.AccountRepository;

public class AccountBenchmark extends RelationalBenchmark<AccountRepository, Account, Long> {

    private static final List<Class<? extends BaseEntity<Long>>> tables = List.of(
            Account.class
    );

    @Override
    protected void updateEntity(Account entity) {
        entity.setPassword(faker.internet().password());
    }

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
    protected Iterable<Class<? extends BaseEntity<Long>>> getTables() {
        return tables;
    }
}
