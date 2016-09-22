package thebank;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Accounts, Integer> {
	public Accounts findAccountsById(Integer id);

	public Accounts findAccountsByAccountNumber(Integer accountNumber);

	@Query("select max(accountNumber) from Accounts")
	public int getMaxAccountNumber();


}
