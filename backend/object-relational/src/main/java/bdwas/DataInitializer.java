package bdwas;

import java.util.Locale;

import bdwas.models.Account;
import bdwas.models.Address;
import bdwas.models.Gender;
import bdwas.models.PersonalData;
import bdwas.models.Role;
import bdwas.repositories.AccountRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final Faker faker;
    private final AccountRepository accountRepository;

    public DataInitializer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.faker = new Faker(Locale.getDefault());
    }

    @Override
    public void run(String... args) {
        if(accountRepository.count() == 0) {
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

            var account = Account.builder()
                    .email("test@test.com")
                    .password("test")
                    .role(Role.CLIENT)
                    .personalData(personalData)
                    .build();

            accountRepository.save(account);
        }
    }
}

