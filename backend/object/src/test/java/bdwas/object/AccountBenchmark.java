package bdwas.object;

import java.util.UUID;

import bdwas.models.Account;
import bdwas.models.Address;
import bdwas.models.Gender;
import bdwas.models.PersonalData;
import bdwas.models.Role;
import bdwas.repositories.AccountRepository;

public class AccountBenchmark extends ObjectBenchmark<AccountRepository, Account, Long> {

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

}
