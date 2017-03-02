/**
 * 
 */
package pers.zxlin.iam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import pers.zxlin.iam.dao.IdentityDbDAO;
import pers.zxlin.iam.model.Identity;

/**
 * This class provides the public interfaces related with identify management for the upper invoker
 * 
 * @author BoJack
 */
public class IdManager {
	private static final Logger LOGGER = Logger.getLogger(IdManager.class.getName());
	private IdentityDbDAO identityDbDAO;

	public IdManager() {
		identityDbDAO = IdentityDbDAO.getInstance();
	}

	/**
	 * Check if a identity exists
	 * 
	 * @param uid
	 *            the user id to be checked
	 * @return
	 */
	public boolean isExist(String uid) {
		// query the id in the database
		ArrayList<Identity> list = (ArrayList<Identity>) identityDbDAO.getIdentity("uid", uid);
		return !list.isEmpty();
	}

	/**
	 * Get the latest public identity attribute list
	 * 
	 * @return the list of identity attribute
	 */
	public String[] getLatestIdAttr() {
		// update the fields because it may be changed through other interface
		return identityDbDAO.getLatestIdAttr();
	}

	/**
	 * Add a new public attribute for all identities
	 * 
	 * @param attr
	 *            attribute name to be added
	 * @return
	 */
	public boolean addTableField(String attr) {
		identityDbDAO.addTableField(attr);
		return true;
	}

	/**
	 * Delete specified public attribute
	 * 
	 * @param attr
	 *            attribute name to be deleted
	 * @return
	 */
	public boolean deleteTableField(String attr) {
		for (String string : identityDbDAO.getLatestIdAttr()) {
			if (attr.equals(string))
				identityDbDAO.deleteTableField(attr);
		}
		return true;
	}

	/**
	 * Add a new private attribute for the specified user
	 * 
	 * @param uid
	 *            specified user id
	 * @param attrName
	 *            the new attribute name
	 * @param attrVal
	 *            the new attribute value
	 * @return
	 */
	public boolean addIdAttr(String uid, String attrName, String attrVal) {
		return identityDbDAO.addIdAttr(uid, attrName, attrVal);
	}

	/**
	 * Delete the specified private attribute
	 * 
	 * @param uid
	 *            the specified user id
	 * @param attrName
	 *            the attribute name to be deleted
	 * @return
	 */
	public boolean delIdAttr(String uid, String attrName) {
		return identityDbDAO.deleteIdAttr(uid, attrName);
	}

	/**
	 * Create a new identity
	 * 
	 * @param name
	 * @param email
	 * @param extraInfo
	 *            the string array including extra attribute values (corresponding to the attrNames array)
	 * @param attrNames
	 *            the string array including extra attribute names
	 * @return
	 */
	public boolean create(String name, String email, String[] extraInfo, String[] attrNames) {
		// construct the identity
		Identity identity = new Identity(name, "-1", email);
		int i = 3;

		if (extraInfo != null) {
			for (String item : extraInfo) {
				identity.getAttributes().put(attrNames[i++], item);
			}
		}
		// insert the record into database
		identityDbDAO.insert(identity);

		return true;
	}

	/**
	 * Create a new identity using a map
	 * 
	 * @param allAttr
	 *            containing all attribute name to value pairs
	 * @return
	 */
	public boolean create(Map<String, String> allAttr) {
		// construct the identity
		Identity identity = new Identity(allAttr.get("displayName"), "-1", allAttr.get("email"));

		if (allAttr.size() > 2) {
			allAttr.remove("displayName");
			allAttr.remove("email");
			identity.getAttributes().putAll(allAttr);
		}
		// insert the record into database
		identityDbDAO.insert(identity);

		return true;
	}

	/**
	 * Update the specified identity attribute
	 * 
	 * @param uid
	 *            specified user id
	 * @param attrName
	 *            the attribute name to be updated
	 * @param updatedVal
	 *            the new value for the attribute
	 * @return
	 */
	public boolean updateAttrs(String uid, String attrName, String updatedVal) {

		// judge if the id exist
		if (!isExist(uid))
			return false;

		// update the required info
		identityDbDAO.update(uid, attrName, updatedVal);

		return true;
	}

	/**
	 * Search for the identities fulfilling single criteria
	 * 
	 * @param attrName
	 *            the attribute name to be searched by
	 * @param val
	 *            required value
	 * @return string[] array, in which each string array contains the attribute value for one identity
	 */
	public String[][] searchByAttr(String attrName, String val) {
		// judge if the id exist
		String[][] info = null;
		ArrayList<Identity> list = (ArrayList<Identity>) identityDbDAO.getIdentity(attrName, val);
		if (list.isEmpty())
			return info;

		String[] attrNames = getLatestIdAttr();
		int infoCount = list.get(0).getAttributes().size() + 3;
		info = new String[list.size()][infoCount];

		int i = 0;
		for (String[] strings : info) {
			Identity curId = list.get(i);
			strings[0] = curId.getUid();
			strings[1] = curId.getDisplayName();
			strings[2] = curId.getEmail();

			for (int j = 3; j < infoCount; j++) {
				strings[j] = curId.getAttributes().get(attrNames[j]);
			}
			i++;
		}

		return info;
	}

	/**
	 * Search for identities with the map
	 * 
	 * @param attrVals
	 *            containing all the attribute to value pairs
	 * @return string[] array, in which each string array contains the attribute value for one identity
	 */
	public String[][] searchByAttr(Map<String, String> attrVals) {
		// judge if the id exist
		String[][] info = null;
		ArrayList<Identity> list = (ArrayList<Identity>) identityDbDAO.getIdentity(attrVals);
		if (attrVals.size() == 0)
			return info;

		info = new String[list.size()][getLatestIdAttr().length];

		int i = 0;
		for (String[] strings : info) {
			Identity curId = list.get(i);
			strings[0] = curId.getUid();
			strings[1] = curId.getDisplayName();
			strings[2] = curId.getEmail();
			int j = 3;
			for (String item : curId.getAttributes().values()) {
				strings[j++] = item;
			}
			i++;
		}
		return info;
	}

	/**
	 * Get private attributes for specified user
	 * 
	 * @param id
	 *            specified user id
	 * @return two string arrays, in which one for private attribute names, the other for corresponding values
	 */
	public String[][] getIdDetail(String id) {
		String[][] info = null;
		// judge if the id exist
		try {
			ArrayList<Identity> list = (ArrayList<Identity>) identityDbDAO.getIdentity("uid", id);
			Identity target = list.get(0);
			HashMap<String, String> extraInfos = (HashMap<String, String>) identityDbDAO.getExtraInfo(id);
			int infoCount = target.getAttributes().size() + 3 + extraInfos.size();
			info = new String[2][infoCount];

			int i = 3;
			info[0][0] = "uid";
			info[1][0] = target.getUid();
			info[0][1] = "displayName";
			info[1][1] = target.getDisplayName();
			info[0][2] = "email";
			info[1][2] = target.getEmail();

			for (String s : target.getAttributes().keySet()) {
				info[0][i] = s;
				info[1][i++] = target.getAttributes().get(s);
			}
			for (Entry<String, String> entry : extraInfos.entrySet()) {
				info[0][i] = entry.getKey();
				info[1][i++] = entry.getValue();
			}
		} catch (IndexOutOfBoundsException e) {
			// Not found
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		return info;

	}

	/**
	 * Delete a specified user
	 * 
	 * @param uid
	 *            specified user id
	 * @return
	 */
	public boolean delete(String uid) {
		// judge if the id exist
		identityDbDAO.delete(uid);
		// delete from the database
		return true;
	}

}
