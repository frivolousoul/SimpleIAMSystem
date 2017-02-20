package pers.zxlin.iam.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pers.zxlin.iam.model.Identity;

public class IdentityDbDAO implements IdentityDAO {
	private DbManager dbManager;
	private static IdentityDbDAO identityDbDAO = null;
	private static String driverName = "com.mysql.jdbc.Driver";
	private static String userName = "root";
	private static String pwd = "root";
	private static String connectUrl = "jdbc:mysql://localhost:3306/db_iam";
	private Connection connection;
	private String[] attrName;

	private IdentityDbDAO() {
		dbManager = new DbManager(connectUrl, driverName, userName, pwd);
		connection = dbManager.getConnection();
	}

	public static IdentityDbDAO getInstance() {
		if (identityDbDAO == null)
			identityDbDAO = new IdentityDbDAO();
		return identityDbDAO;
	}

	@Override
	public String[] getLatestIdAttr() {
		attrName = dbManager.getColumnNames("t_identity");
		return attrName;
	}

	@Override
	public boolean insert(Identity identity) {
		PreparedStatement preStat = null;

		try {
			String strSql = "INSERT INTO t_identity";
			String strAttr = " (displayName,email,";
			String strVal = " VALUES (?,?,";

			// get the extra attribute set

			Set<String> extraAttr = identity.getAttributes().keySet();
			String[] vals = new String[extraAttr.size()];
			int i = 0;
			for (String item : extraAttr) {
				strAttr += (item + ",");
				strVal += "?,";
				vals[i++] = identity.getAttributes().get(item);

			}
			strAttr = strAttr.subSequence(0, strAttr.length() - 1) + ")";
			strVal = strVal.subSequence(0, strVal.length() - 1) + ")";

			strSql = strSql + strAttr + strVal;
			preStat = connection.prepareStatement(strSql);

			preStat.setString(1, identity.getDisplayName());
			preStat.setString(2, identity.getEmail());
			for (int j = 0; j < vals.length; j++) {
				preStat.setString(j + 3, vals[j]);
			}

			dbManager.excuteNonQuery(preStat);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	@Override
	public boolean delete(String uid) {
		PreparedStatement preStat = null;
		boolean result = false;
		try {
			String strSql = "DELETE FROM t_identity WHERE uid = ?";
			preStat = connection.prepareStatement(strSql);
			preStat.setString(1, uid);
			dbManager.excuteNonQuery(preStat);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {
				e.printStackTrace();
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
			attrName = dbManager.getColumnNames("t_identity");

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
			e.printStackTrace();
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

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
			conditionBuilder.append("'" + attrVals.get(attr).toString() + "'");
		}
		condition = conditionBuilder.substring(5);
		try {
			String strSql = "SELECT * FROM t_identity WHERE " + condition;
			attrName = dbManager.getColumnNames("t_identity");
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
			e.printStackTrace();
		} finally {
			try {
				if (preStat != null)
					preStat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
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
			e.printStackTrace();
		} finally {
			if (preStat != null)
				try {
					preStat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return foundPwd;
	}

	@Override
	public boolean addAttribute(String attrName) {
		// TODO Auto-generated method stub
		String sql = "ALTER TABLE t_identity ADD ? varchar(30) DEFAULT ''";
		Statement stat = null;

		sql = sql.replace("?", attrName);
		try {
			stat = connection.createStatement();
			stat.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return true;
	}

	public boolean deleteAttribute(String attrName) {
		// TODO Auto-generated method stub
		String sql = "ALTER TABLE t_identity DROP ?";
		Statement stat = null;

		sql = sql.replace("?", attrName);
		try {
			stat = connection.createStatement();
			stat.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stat != null)
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return false;
	}

}
