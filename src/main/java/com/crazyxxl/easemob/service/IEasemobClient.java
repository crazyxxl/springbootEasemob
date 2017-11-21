/**
 * 
 */
package com.crazyxxl.easemob.service;

import com.crazyxxl.easemob.models.GroupDto;
import com.crazyxxl.easemob.models.GroupUpdateDto;
import com.crazyxxl.easemob.models.UserRegisterDto;

import java.util.List;

public interface IEasemobClient {

	public String userRegister(UserRegisterDto dto);

	public String usersRegister(List<UserRegisterDto> dtos);

	public String deleteUser(String userName);

	public String getToken();
	
	public String addContacts(String owner, String friend);

	public String deleteContacts(String owner, String friend);

	public String updateNick(String userId, String nick);
	
	public String resetPassword(String userId, String password);
	
	public String createGroup(GroupDto dto);
	
	public String deleteGroup(String groupId);
	
	public String addGroupMember(String groupId, String username);
	
	public String deleteGroupMember(String groupId, List<String> usernames);
	
	public String updateGroup(String groupId, GroupUpdateDto dto);
}
