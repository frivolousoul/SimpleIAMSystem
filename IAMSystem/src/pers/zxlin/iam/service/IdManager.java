/**
 * 
 */
package pers.zxlin.iam.service;

import java.util.ArrayList;
import java.util.Map;

import pers.zxlin.iam.dao.IdentityDbDAO;
import pers.zxlin.iam.model.Identity;

/**
 * @author BoJack
 *
 */
public class IdManager {
	private IdentityDbDAO identityDbDAO;

	public IdManager() {
		identityDbDAO = IdentityDbDAO.getInstance();
	}

	public boolean isExist(String uid) {
		// query the id in the database
		ArrayList<Identity> list = (ArrayList<Identity>) identityDbDAO.getIdentity("uid", uid);
		return !list.isEmpty();
	}

	public String[] getLatestIdAttr() {
		// update the fields because it may be changed through other interface
		return identityDbDAO.getLatestIdAttr();
	}

	public boolean addIdAttribute(String attr) {
		identityDbDAO.addAttribute(attr);
		return true;
	}

	public boolean deleteIdAttribute(String attr) {
		for (String string : identityDbDAO.getLatestIdAttr()) {
			if (attr.equals(string))
				identityDbDAO.deleteAttribute(attr);
		}
		return true;
	}

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

	public boolean updateAttrs(String uid, String attrName, String updatedVal) {

		// judge if the id exist
		if (!isExist(uid))
			return false;

		// update the required info
		identityDbDAO.update(uid, attrName, updatedVal);

		return true;
	}

	public String[][] searchByAttr(String attrName, String val) {
		// judge if the id exist
		String[][] info = null;
		ArrayList<Identity> list = (ArrayList<Identity>) identityDbDAO.getIdentity(attrName, val);
		if (list.size() == 0)
			return info;

		info = new String[list.size()][list.get(0).getAttributes().size() + 3];

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

	public String[][] searchByAttr(Map<String, String> attrVals) {
		// judge if the id exist
		String[][] info = null;
		ArrayList<Identity> list = (ArrayList<Identity>) identityDbDAO.getIdentity(attrVals);
		if (attrVals.size() == 0)
			return info;

		info = new String[list.size()][list.get(0).getAttributes().size() + 3];

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

	public boolean delete(String uid) {
		// judge if the id exist
		identityDbDAO.delete(uid);
		// delete from the database
		return true;
	}

}
