/**
 * 
 */
package pers.zxlin.iam.service;

import pers.zxlin.iam.dao.IdentityDbDAO;

/**
 * This class allows to perform an authentication for a user trying to access
 * the system
 * 
 * @author BoJack
 *
 */
public class Authenticator {
	private Authenticator() {
	}

	public static boolean authenticate(String userName, String pwd) {

		String correctPwd = getLoginPwd(userName);
		if (correctPwd == null)
			// user not found
			return false;

		return correctPwd.equals(pwd);

	}

	private static String getLoginPwd(String userName) {
		// query in the database for the correct pwd
		IdentityDbDAO identityDbDAO = IdentityDbDAO.getInstance();

		return identityDbDAO.getPwd(userName);
	}
}
