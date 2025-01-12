package bdwas.services;

import java.util.List;
import java.util.Optional;

import bdwas.models.Account;
import bdwas.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(long id) {
        return accountRepository.findById(id);
    }

    public Account createAccount(Account account) {
        // Additional logic like validation or password hashing can be added here
        return accountRepository.save(account);
    }

    public Account updateAccount(long id, Account updatedAccount) {
        return accountRepository.findById(id)
                                .map(existingAccount -> {
                                    existingAccount.setEmail(updatedAccount.getEmail());
                                    existingAccount.setPassword(updatedAccount.getPassword());
                                    existingAccount.setRole(updatedAccount.getRole());
                                    existingAccount.setPersonalData(updatedAccount.getPersonalData());
                                    return accountRepository.save(existingAccount);
                                })
                                .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }
}
