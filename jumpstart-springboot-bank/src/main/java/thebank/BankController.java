package thebank;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bank")
@RestController
public class BankController {

	// @Autowired
	// BankService bank;
	//
	// @RequestMapping(value = "/accounts", method = RequestMethod.POST)
	// public AccountVO createAccount(@RequestParam(value = "startbalance") int
	// startBalance)
	// throws AccountCreationException {
	// return bank.createAccount(AccountType.SAVING, startBalance, 0);
	// }

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public String testResponse() {
		System.out.println("Test");
		return "Test response";
	}

	// @RequestMapping(value="/accounts/123", method=RequestMethod.GET)
	// public String testResponseWithID(){}

}
