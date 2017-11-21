/**
 * 
 */
package com.crazyxxl.easemob.models;

/**
 * @author Crazyxxl
 *
 */
public class Entity {
	private String uuid;
	private String type;
	private long created;
	private long modified;
	private String username;
	private boolean activated;
	/**
	 * 
	 */
	public Entity() {
		
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public long getModified() {
		return modified;
	}
	public void setModified(long modified) {
		this.modified = modified;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	
}
