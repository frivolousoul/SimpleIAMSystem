/**
 * 
 */
package pers.zxlin.iam.dao;

import java.util.List;

import pers.zxlin.iam.model.Identity;

/**
 * @author BoJack
 *
 */
public interface IdentityDAO {

	/**
	 * Get the password of the corresponding userName
	 * 
	 * @param userName
	 * @return
	 */
	public String getPwd(String userName);

	public String[] getLatestIdAttr();

	/**
	 * Insert a identity
	 * 
	 * @param identity
	 * @return
	 */
	public boolean insert(Identity identity);

	/**
	 * Delete a identity
	 * 
	 * @param uid
	 * @return
	 */
	public boolean delete(String uid);

	/**
	 * Get a identity information
	 * 
	 * @param attr
	 *            specified attribute
	 * @param val
	 *            the value for search
	 * @return the list of identities
	 */
	public List<Identity> getIdentity(String attr, String val);

	/**
	 * Update the identity info according to the specified attribute and val
	 * 
	 * @param uid
	 * @param attr
	 * @param val
	 * @return
	 */
	public boolean update(String uid, String attr, String val);

	public boolean addAttribute(String fieldName);
}
