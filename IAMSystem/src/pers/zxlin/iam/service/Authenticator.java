/**
 * 
 */
package pers.zxlin.iam.service;

import pers.zxlin.iam.dao.IdentityDbDAO;

/**
 * This class allows to perform an authentication for a user trying to access the system
 * 
 * @author BoJack
 */
public class Authenticator {
	private Authenticator() {
	}

	/**
	 * Authenticate whether the login and password are corresponding
	 * 
	 * @param userName
	 *            checked login
	 * @param pwd
	 *            checked password
	 * @return true if corresponding
	 */
	public static boolean authenticate(String userName, String pwd) {

		String correctPwd = getLoginPwd(userName);
		if (correctPwd == null)
			// user not found
			return false;

		return correctPwd.equals(pwd);

	}

	/**
	 * Query the password for the current login
	 * 
	 * @param userName
	 *            checked login
	 * @return
	 */
	private static String getLoginPwd(String userName) {
		// query in the database for the correct pwd
		IdentityDbDAO identityDbDAO = IdentityDbDAO.getInstance();

		return identityDbDAO.getPwd(userName);
	}
}
