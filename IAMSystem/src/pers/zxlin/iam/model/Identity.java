package pers.zxlin.iam.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The entity class to persist identity information
 * 
 * @author BoJack
 */
public class Identity {

	private String displayName;
	private String uid;
	private String email;
	Map<String, String> attributes;

	public Identity(String displayName, String uid, String email) {
		super();
		this.displayName = displayName;
		this.uid = uid;
		this.email = email;
		attributes = new HashMap<String, String>();
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getUid() {
		return uid;
	}

	public String getEmail() {
		return email;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
