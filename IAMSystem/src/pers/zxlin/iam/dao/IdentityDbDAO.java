package pers.zxlin.iam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import pers.zxlin.iam.model.Identity;

/**
 * Implement DAO interface by MySQL database
 * 
 * @author BoJack
 */
public class IdentityDbDAO implements IdentityDAO {
	private static final Logger LOGGER = Logger.getLogger(IdentityDbDAO.class.getName());
	private DbManager dbManager;
	private static IdentityDbDAO identityDbDAO = null;
	private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PWD = "root";
	private static final String DBURL = "jdbc:mysql://localhost:3306/db_iam";
	private static final String TABLENAMEFORID = "t_identity";
	private Connection connection;
	private String[] attrName;

	/**
	 * Single instance mode
	 */
	private IdentityDbDAO() {
		dbManager = new DbManager(DBURL, DRIVERNAME, USERNAME, PWD);
		connection = dbManager.getConnection();
	}

	/**
	 * @return the single instance of the DAO
	 */
	public static IdentityDbDAO getInstance() {
		if (identityDbDAO == null)
			identityDbDAO = new IdentityDbDAO();
		return identityDbDAO;
	}

	@Override
	public String[] getLatestIdAttr() {
		attrName = dbManager.getColumnNames(TABLENAMEFORID);
		return attrName;
	}

	@Override
	public boolean insert(Identity identity) {
		PreparedStatement preStat = null;
		try {
			String strSql = "INSERT INTO " + TABLENAMEFORID + " ";
			StringBuilder strAttr = new StringBuilder("(displayName,email,");
			StringBuilder strVal = new StringBuilder(" VALUES (?,?,");

			// get the extra attribute set
			Set<String> extraAttr = identity.getAttributes().keySet();
			String[] vals = new String[extraAttr.size()];
			int i = 0;

			// construct the insert sentence
			for (String item : extraAttr) {
				strAttr.append(item + ",");
				strVal.append("?,");
				// store the value for each extra attribute in the array
				vals[i++] = identity.getAttributes().get(item);
			}

			strSql = strSql + strAttr.subSequence(0, strAttr.length() - 1) + ")" + strVal.subSequence(0, strVal.length() - 1) + ")";
			preStat = connection.prepareStatement(strSql);

			preStat.setString(1, identity.getDisplayName());
			preStat.setString(2, identity.getEmail());
			for (int j = 0; j < vals.length; j++) {
				preStat.setString(j + 3, vals[j]);
			}

			dbManager.excuteNonQuery(preStat);

		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {

				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}

		return true;
	}

	@Override
	public boolean delete(String uid) {
		PreparedStatement preStat = null;
		try {
			String strSql = "DELETE FROM t_identity WHERE uid = ?";
			preStat = connection.prepareStatement(strSql);
			preStat.setString(1, uid);
			dbManager.excuteNonQuery(preStat);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}
		return true;
	}

	@Override
	public List<Identity> getIdentity(String attr, String val) {
		ArrayList<Identity> result = new ArrayList<>();
		PreparedStatement preStat = null;
		try {
			String strSql = "SELECT * FROM t_identity WHERE $ = ?";
			attrName = dbManager.getColumnNames(TABLENAMEFORID);

			strSql = strSql.replace("$", attr);
			preStat = connection.prepareStatement(strSql);
			preStat.setString(1, val);
			// get the list of result, then convert it into Identity
			ArrayList<List<Object>> list = (ArrayList<List<Object>>) dbManager.executeQuery(preStat);
			for (List<Object> infos : list) {
				int infoCount = infos.size();
				Identity identity = new Identity(infos.get(1).toString(), infos.get(0).toString(),
						infos.get(2).toString());
				for (int i = 3; i < infoCount; i++) {
					identity.getAttributes().put(attrName[i], infos.get(i).toString());
				}
				result.add(identity);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}

		return result;
	}

	/**
	 * Search identity according to all key-value pairs in the map
	 * 
	 * @param attrVals
	 *            map containing attrName to value pairs which serve as search criteria
	 * @return list of the identities
	 */
	public List<Identity> getIdentity(Map<String, String> attrVals) {
		ArrayList<Identity> result = new ArrayList<>();
		PreparedStatement preStat = null;

		// construct the condition with the map
		Set<String> keys = attrVals.keySet();
		StringBuilder conditionBuilder = new StringBuilder();
		String condition;
		for (String attr : keys) {
			conditionBuilder.append(" and ");
			conditionBuilder.append(attr);
			conditionBuilder.append("=");
			conditionBuilder.append("'" + attrVals.get(attr) + "'");
		}
		condition = conditionBuilder.substring(5);
		try {
			String strSql = "SELECT * FROM t_identity WHERE " + condition;

			attrName = dbManager.getColumnNames(TABLENAMEFORID);
			preStat = connection.prepareStatement(strSql);
			// get the list of result, then convert it into Identity
			ArrayList<List<Object>> list = (ArrayList<List<Object>>) dbManager.executeQuery(preStat);
			for (List<Object> infos : list) {
				int infoCount = infos.size();
				Identity identity = new Identity(infos.get(1).toString(), infos.get(0).toString(),
						infos.get(2).toString());
				for (int i = 3; i < infoCount; i++) {
					identity.getAttributes().put(attrName[i], infos.get(i).toString());
				}
				result.add(identity);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}

		return result;
	}

	/**
	 * Get the private extra attribute for specified identity
	 * 
	 * @param uid
	 *            specified user id
	 * @return
	 */
	public Map<String, String> getExtraInfo(String uid) {
		Map<String, String> extraInfos = new HashMap<>();

		PreparedStatement preStat = null;
		try {
			String strSql = "SELECT * FROM t_extraInfo WHERE uid = ?";
			preStat = connection.prepareStatement(strSql);
			preStat.setString(1, uid);
			// get the list of result, then convert it into Identity
			ArrayList<List<Object>> list = (ArrayList<List<Object>>) dbManager.executeQuery(preStat);
			for (List<Object> infos : list) {
				extraInfos.put(infos.get(1).toString(), infos.get(2).toString());
			}
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}
		}

		return extraInfos;
	}

	@Override
	public boolean update(String uid, String attr, String val) {
		PreparedStatement preStat = null;
		try {
			String strSql = "UPDATE t_identity SET $=? WHERE uid=?";
			strSql = strSql.replace("$", attr);
			preStat = connection.prepareStatement(strSql);
			preStat.setString(1, val);
			preStat.setString(2, uid);
			dbManager.excuteNonQuery(preStat);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			if (preStat != null)
				try {
					preStat.close();
				} catch (SQLException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
		}
		return true;

	}

	@Override
	public String getPwd(String userName) {
		String foundPwd = null;
		String sql = "SELECT password FROM t_user WHERE userName = ?";
		PreparedStatement stat = null;
		try {
			stat = connection.prepareStatement(sql);
			stat.setString(1, userName);
			ArrayList<List<Object>> lists = (ArrayList<List<Object>>) dbManager.executeQuery(stat);
			if (lists.isEmpty())
				return null;
			ArrayList<Object> list = (ArrayList<Object>) lists.get(0);
			foundPwd = list.get(0).toString();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
		}
		return foundPwd;
	}

	@Override
	public boolean addTableField(String attrName) {
		String sql = "ALTER TABLE t_identity ADD ? varchar(30) DEFAULT ''";
		Statement stat = null;

		sql = sql.replace("?", attrName);
		try {
			stat = connection.createStatement();
			stat.execute(sql);
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {

					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
		}
		return true;
	}

	@Override
	public boolean deleteTableField(String attrName) {
		String sql = "ALTER TABLE t_identity DROP ?";
		Statement stat = null;

		sql = sql.replace("?", attrName);
		try {
			stat = connection.createStatement();
			stat.execute(sql);
		} catch (SQLException e) {

			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
		}
		return true;
	}

	@Override
	public boolean addIdAttr(String uid, String attrName, String attrVal) {
		String sql = "INSERT INTO t_extraInfo VALUES (?,?,?)";
		PreparedStatement stat = null;

		try {
			stat = connection.prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(uid));
			stat.setString(2, attrName);
			stat.setString(3, attrVal);
			stat.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {

					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
		}
		return true;
	}

	@Override
	public boolean deleteIdAttr(String id, String attrName) {
		String sql = "DELETE FROM t_extraInfo WHERE uid=? and attrName=?";
		PreparedStatement stat = null;

		try {
			stat = connection.prepareStatement(sql);
			stat.setInt(1, Integer.parseInt(id));
			stat.setString(2, attrName);
			stat.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
		}
		return true;

	}
}
