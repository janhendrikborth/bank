package thebank;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterReturning("execution(* thebank.BankService.*(..)) && args(account,..)")
	public void logServiceAccess(JoinPoint joinPoint, Accounts account) {

		logger.info("Completed: " + joinPoint + "from account " + account.getAccountNumber());
	}

}
