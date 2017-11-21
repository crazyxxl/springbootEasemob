/**
 * 
 */
package com.crazyxxl.easemob.models;



import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Crazyxxl
 *
 */
public class GroupDto extends GroupUpdateDto{
	
	@JsonProperty("public")
	private boolean isPublic = true; //":true, //是否是公开群，此属性为必须的
	
	private boolean approval; //":true, //加入公开群是否需要批准，默认值是false（加入公开群不需要群主批准），此属性为必选的，私有群必须为true
	private String owner; //":"jma1", //群组的管理员，此属性为必须的
	//private List<String> members ;//":["jma2","jma3"] //群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个（注：群主jma1不需要写入到members里面）
	/**
	 * 
	 */
	public GroupDto() {
		
	}
	
	public boolean isPublic() {
		return isPublic;
	}
	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	
	public boolean isApproval() {
		return approval;
	}
	public void setApproval(boolean approval) {
		this.approval = approval;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	//public List<String> getMembers() {
	//	return members;
	//}
	//public void setMembers(List<String> members) {
	//	this.members = members;
	//}
	
}
