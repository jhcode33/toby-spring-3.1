package vol1.kitec.ch5.study1_5.springframe.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import vol1.kitec.ch5.study1_5.springframe.domain.Level;
import vol1.kitec.ch5.study1_5.springframe.domain.User;

public class UserTest {
	
	User user;
	
	@BeforeEach
	public void setUp() {
		user = new User();
	}
	
	@Test()
	public void upgradeLevel() {
		Level[] levels = Level.values();
		
		for (Level level : levels) {
			if(level.nextLevel() == null) continue;
			user.setLevel(level);
			user.upgradeLevel();
			assertEquals(user.getLevel(), level.nextLevel());
		}
	}

}
