package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

	@Autowired
	private AccountsRepository accountRepo;

	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public Accounts createAccount(@RequestBody Accounts account) throws AccountCreationException {
		if (account != null) {
			if (accountRepo.save(account) != null) {
				return accountRepo.save(account);
			} else {
				throw new AccountCreationException("Account could not be persisted!");
			}
		} else {
			throw new AccountCreationException("Account data corrupted or incomplete");
		}

	}

	@RequestMapping("/accounts")
	public Iterable<Accounts> findAllAccounts() {

		Iterable<Accounts> accountList = accountRepo.findAll();

		return accountList;
	}

	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
	public void deleteAccount(@PathVariable Integer id) {
		accountRepo.delete(id);
	}
	
	@RequestMapping("/accounts/{id}")
	public Accounts findById(@PathVariable Integer id) {
		Accounts foundAccount = accountRepo.findAccountsById(id);

		return foundAccount;
	}

	@RequestMapping("/accounts/findbynumber/{accountnumber}")
	public Accounts findByAccountNumber(@PathVariable Integer accountnumber) {
		Accounts foundAccount = accountRepo.findAccountsByAccountNumber(accountnumber);
		return foundAccount;
	}

	@RequestMapping(value = "/accounts/book", method = RequestMethod.POST)
	public String doTransaction(@RequestBody AtmIncomingMsg incomingMessage) throws TransferFailedException {
		AtmIncomingMsg receivedMsg = incomingMessage;
		Accounts account;
		if (receivedMsg != null) {
			switch (incomingMessage.getOperationType()) {
			case deposit:
				if ((account = accountRepo.findAccountsByAccountNumber(receivedMsg.getAccountNumber())) != null) {
					account.setBalance(account.getBalance() + receivedMsg.getAmount());
					accountRepo.save(account);
					return "Transaction successful! Deposited amount: " + receivedMsg.getAmount();
				} else {
					throw new TransferFailedException("Account does not exist");
				}
			case withdraw:
				if ((account = accountRepo.findAccountsByAccountNumber(receivedMsg.getAccountNumber())) != null) {
					if (!((account.getBalance() - receivedMsg.getAmount()) < 0)) {
						account.setBalance(account.getBalance() - receivedMsg.getAmount());
						accountRepo.save(account);
						return "Transaction successful! Withdrawn amount: " + receivedMsg.getAmount();
					} else {
						throw new TransferFailedException("Not enough funds!");
					}

				} else {
					throw new TransferFailedException("Account does not exist");
				}
			default:
				throw new TransferFailedException(
						"Operation type is not of the defined type! Acceptable types: deposit | withdraw");

			}
		} else {
			throw new TransferFailedException("Transaction corrupted or empty");
		}
		// throw new TransferFailedException("Transaction went wrong. Contact
		// your admin");
	}
	
	@RequestMapping("/test")
	public String testString(){
		return "teeesss123123est";
	}
	

}
