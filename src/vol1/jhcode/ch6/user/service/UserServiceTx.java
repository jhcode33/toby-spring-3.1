package vol1.jhcode.ch6.user.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import vol1.jhcode.ch6.user.domain.User;

public class UserServiceTx implements UserService {

	UserService userService;
	PlatformTransactionManager transactionManager;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	
	//== DI 받은 UserService에게 비즈니스 로직 위임 ==//
	@Override
	public void add(User user) {
		userService.add(user);
	}

	@Override
	public void upgradeLevels() {
		TransactionStatus status = this.transactionManager
				.getTransaction(new DefaultTransactionDefinition());
		
		try {
			
			userService.upgradeLevels();
			
			this.transactionManager.commit(status);
			
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}
	

}
