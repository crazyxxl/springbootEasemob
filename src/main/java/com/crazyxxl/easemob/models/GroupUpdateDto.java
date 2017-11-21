/**
 * 
 */
package com.crazyxxl.easemob.models;

/**
 * @author Crazyxxl
 *
 */
public class GroupUpdateDto {
	private String groupname; //群组名称，此属性为必须的
	private String desc ; //":"server create group", //群组描述，此属性为必须的
	private String maxusers; //":300, //群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
	/**
	 * 
	 */
	public GroupUpdateDto() {
		
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getMaxusers() {
		return maxusers;
	}
	public void setMaxusers(String maxusers) {
		this.maxusers = maxusers;
	}
	
}
