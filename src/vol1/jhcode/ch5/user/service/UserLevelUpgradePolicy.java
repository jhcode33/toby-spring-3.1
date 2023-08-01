package vol1.jhcode.ch5.user.service;

import vol1.jhcode.ch5.user.dao.UserDao;
import vol1.jhcode.ch5.user.domain.User;

public interface UserLevelUpgradePolicy {
	
	boolean canUpgradeLevel(User user);
	void upgradeLevel(User user, UserDao userDao);

}
