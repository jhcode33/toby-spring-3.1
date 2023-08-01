package vol1.kitec.ch1.study3_3.dao;

import java.sql.SQLException;

import vol1.kitec.ch1.study3_3.domain.User;

public class UserDaoTest {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		UserDao dao = new DUserDao();
		dao.createProduct();
		
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);
			
		System.out.println(user.getId() + "등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
			
		System.out.println(user2.getId() + "조회 성공");
	}

}
