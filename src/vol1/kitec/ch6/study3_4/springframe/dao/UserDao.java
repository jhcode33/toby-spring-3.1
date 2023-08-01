package vol1.kitec.ch6.study3_4.springframe.dao;

import java.util.List;
import java.util.Optional;

import vol1.kitec.ch6.study3_4.springframe.domain.User;

public interface UserDao {
	
	void add(User user);

	Optional<User> get(String id);

	List<User> getAll();

	void deleteAll();

	int getCount();
	
	public void update(User user);
}
