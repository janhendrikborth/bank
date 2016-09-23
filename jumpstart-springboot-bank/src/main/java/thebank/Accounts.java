package thebank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Accounts implements AccountMO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int accountNumber;

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	protected int balance;

	public Accounts() {
	}

	public Accounts(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}



	/**
	 * book method uses abstract method checkBookingPreCondition()
	 * --> Template method pattern 
	 * @throws AccountOverdrawnException 
	 */
	@Override
	public void book(int amount) throws AccountOverdrawnException {
		if (checkBookingPreCondition(amount)) {
			balance += amount;
		} else {
			throw new AccountOverdrawnException(
					"Could not book '" + amount + "' from account: " + this.getAccountNumber());
		}
	}

	boolean checkBookingPreCondition(int amount) {
		return false;
	};

	@Override
	public int getBalance() {
		return balance;
	}

	@Override
	public int getAccountNumber() {
		return accountNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Accounts other = (Accounts) obj;
		if (accountNumber != other.accountNumber)
			return false;
		return true;
	}

}
