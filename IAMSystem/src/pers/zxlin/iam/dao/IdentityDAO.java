/**
 * 
 */
package pers.zxlin.iam.dao;

import java.util.List;

import pers.zxlin.iam.model.Identity;

/**
 * Regulate the interface for the data access object
 * 
 * @author BoJack
 */
public interface IdentityDAO {

	/**
	 * Get the password for the specified login
	 * 
	 * @param userName
	 *            current login
	 * @return password of the current login
	 */
	public String getPwd(String userName);

	/**
	 * Get the latest public identity attribute list
	 * 
	 * @return string array of the attribute list
	 */
	public String[] getLatestIdAttr();

	/**
	 * Insert a identity
	 * 
	 * @param identity
	 *            the identity object needed to be inserted
	 * @return
	 */
	public boolean insert(Identity identity);

	/**
	 * Delete a identity
	 * 
	 * @param uid
	 *            the specified user ID
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
	 * @return the list of identity objects
	 */
	public List<Identity> getIdentity(String attr, String val);

	/**
	 * Update the identity info according to the specified attribute and val
	 * 
	 * @param uid
	 *            the specified user ID
	 * @param attr
	 *            the attribute to update
	 * @param val
	 *            the value to the updated attribute
	 * @return
	 */
	public boolean update(String uid, String attr, String val);

	/**
	 * Add a new field for all identities
	 * 
	 * @param fieldName
	 *            to-be-deleted field name
	 * @return
	 */
	public boolean addTableField(String fieldName);

	/**
	 * Delete the public field for all identities
	 * 
	 * @param fieldName
	 *            to-be-deleted field name
	 * @return
	 */
	public boolean deleteTableField(String fieldName);

	/**
	 * Add a private attribute for specified identity
	 * 
	 * @param id
	 *            uid of the specified identity
	 * @param attrName
	 *            the new attribute name
	 * @param attrVal
	 *            the new attribute value
	 * @return
	 */
	public boolean addIdAttr(String id, String attrName, String attrVal);

	/**
	 * Delete a private attribute for specified identity
	 * 
	 * @param id
	 *            uid of the specified identity
	 * @param attrName
	 *            the to-be-deleted attribute name
	 * @return
	 */
	public boolean deleteIdAttr(String id, String attrName);
}
