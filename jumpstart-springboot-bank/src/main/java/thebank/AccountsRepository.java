package thebank;

import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Accounts, Integer> {
	public Accounts findAccountsById(Integer id);

}
