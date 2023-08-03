package vol1.kitec.ch7.study5_1.springframe.dao;

import java.util.List;
import java.util.Optional;

import vol1.kitec.ch7.study5_1.springframe.domain.User;

public interface UserDao {
	
	void add(User user);

	Optional<User> get(String id);

	List<User> getAll();

	void deleteAll();

	int getCount();
	
	public void update(User user);
}
