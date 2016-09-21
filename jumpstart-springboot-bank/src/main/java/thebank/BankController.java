package thebank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

	@Autowired
	private AccountsRepository accountRepo;

	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public Accounts createAccount(@RequestBody Accounts account) throws AccountCreationException {
		if (account != null)
		{
			if (accountRepo.save(account) != null)
			{
				return accountRepo.save(account);
			} else
			{
				throw new AccountCreationException();
			}
		} else
		{
			throw new AccountCreationException();
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

	@ResponseBody
	@RequestMapping("/accounts/{id}")
	public Accounts findById(@PathVariable Integer id) {
		Accounts foundAccount = accountRepo.findAccountsById(id);

		return foundAccount;
	}

	// @RequestMapping(value="/accounts/123", method=RequestMethod.GET)
	// public String testResponseWithID(){}

}
