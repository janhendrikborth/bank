package thebank;

public class AtmIncomingMsg {
	private int accountNumber;
	private int amount;
	private OperationTypes operationType;

	public AtmIncomingMsg() {

	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public OperationTypes getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationTypes operationType) {
		this.operationType = operationType;
	}

}
