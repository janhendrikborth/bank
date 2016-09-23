package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BankController {
	final String partnerbankurl = "http://10.10.3.241:8050/bank/accounts/test";
	final String myurl = "http://localhost:8080/accounts/test";

	@Autowired
	private AccountsRepository accountRepo;

	// curl -X POST http://localhost:8080/accounts
	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public Accounts createAccount() throws AccountCreationException {
		Accounts account = new Accounts();
		int nextnumber;
		if (accountRepo.countRowsInAccount() == 0) {
			account.setAccountNumber(2000);
			account.setBalance(0);

		} else {
			nextnumber = accountRepo.getMaxAccountNumber() + 1;
			account.setAccountNumber(nextnumber);
			account.setBalance(0);

		}
		accountRepo.save(account);
		return account;
	}

	// curl http://localhost:8080/accounts
	@RequestMapping("/accounts")
	public Iterable<Accounts> findAllAccounts() {

		Iterable<Accounts> accountList = accountRepo.findAll();

		return accountList;
	}

	// curl -X DELETE http://localhost:8080/accounts/{id}
	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.DELETE)
	public void deleteAccount(@PathVariable Integer id) throws AccountNotExistentException {
		try {
			accountRepo.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AccountNotExistentException(e.getMessage());
		}
	}

	// curl http://localhost:8080/accounts/{id}
	@RequestMapping("/accounts/{id}")
	public Accounts findById(@PathVariable Integer id) {
		Accounts foundAccount = accountRepo.findAccountsById(id);

		return foundAccount;
	}

	// curl
	// http://localhost:8080/accounts/findbynumber/{account_number_must_start_with_2}
	@RequestMapping("/accounts/findbynumber/{accountnumber}")
	public Accounts findByAccountNumber(@PathVariable Integer accountnumber) {
		Accounts foundAccount = accountRepo.findAccountsByAccountNumber(accountnumber);
		return foundAccount;
	}

	private boolean isInternalAccount(int accountNumber) {
		String accountNumberAsSTring = Integer.toString(accountNumber);
		char bankID = accountNumberAsSTring.charAt(0);
		char localID = '2';
		if (bankID == localID) {
			return true;
		} else {
			return false;
		}
	}

	// curl -X POST -H "Content-Type:application/json" -d
	// "{"""accountNumber""":102, """amount""":70,
	// """operationType""":"""withdraw"""}" http://localhost:8080/accounts/book
	@RequestMapping(value = "/accounts/book", method = RequestMethod.POST)
	public AtmIncomingMsg doTransaction(@RequestBody AtmIncomingMsg incomingMessage) throws TransferFailedException {
		AtmIncomingMsg receivedMsg = incomingMessage;
		Accounts account;

		if (receivedMsg != null) {
			if (!isInternalAccount(receivedMsg.getAccountNumber())) {
				RestTemplate restTemplate = new RestTemplate();
				AtmIncomingMsg answer = restTemplate.postForObject(partnerbankurl, receivedMsg, AtmIncomingMsg.class);
				if (answer != null) {
					return answer;
				} else {
					throw new TransferFailedException("Remote transaction failed");
				}
			}
			switch (incomingMessage.getOperationType()) {
			case deposit:
				if ((account = accountRepo.findAccountsByAccountNumber(receivedMsg.getAccountNumber())) != null) {
					account.setBalance(account.getBalance() + receivedMsg.getAmount());
					accountRepo.save(account);
					receivedMsg.setAmount(account.getBalance());
					System.out.println("Transaction successful! New balance: " + incomingMessage.getAmount());
					return receivedMsg;
				} else {
					throw new TransferFailedException("Account does not exist");
				}
			case withdraw:
				if ((account = accountRepo.findAccountsByAccountNumber(receivedMsg.getAccountNumber())) != null) {
					if (!((account.getBalance() - receivedMsg.getAmount()) < 0)) {
						account.setBalance(account.getBalance() - receivedMsg.getAmount());
						accountRepo.save(account);
						receivedMsg.setAmount(account.getBalance());
						System.out.println("Transaction successful! New balance: " + incomingMessage.getAmount());
						return receivedMsg;
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
	}

}
