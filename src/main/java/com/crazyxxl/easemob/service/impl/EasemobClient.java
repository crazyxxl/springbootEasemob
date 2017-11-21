package com.crazyxxl.easemob.service.impl;

import com.crazyxxl.easemob.Constants.EnvParam;
import com.crazyxxl.easemob.models.*;
import com.crazyxxl.easemob.service.IEasemobClient;
import com.crazyxxl.easemob.utils.JsonUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


@Service
public class EasemobClient implements IEasemobClient {
	@Autowired
	private HttpClientService httpClientService;
	@Autowired
	private static final Logger logger = Logger.getLogger(EasemobClient.class);
	@Value("${easemob.url}")
	private String url;
	@Value("${easemob.userId}")
	private String userId;
	@Value("${easemob.passwd}")
	private String passwd;

	@Override
	public String userRegister(UserRegisterDto dto) {
		try{
			String token = getToken();
			Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token); 
			String resp = httpClientService.postJson(url + "/users", JsonUtil.objectToJsonString(dto), header);
			logger.info("get Resp :" + resp);
			UserRegisterResp userRegisterResp = JsonUtil.jsonStringToObject(resp, UserRegisterResp.class);
			return userRegisterResp.getEntities().get(0).getUuid();
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public String usersRegister(List<UserRegisterDto> dtos) {
		try{
			String token = getToken();
			Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);
			String resp = httpClientService.postJson(url + "/users", JsonUtil.objectToJsonString(dtos), header);
			logger.info("get Resp :" + resp);
			UserRegisterResp userRegisterResp = JsonUtil.jsonStringToObject(resp, UserRegisterResp.class);
			return userRegisterResp.getEntities().get(0).getUuid();
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	private String getTokenFromServer(GetTokenDto dto) {
		try{
			String resp = httpClientService.postJson(url +"/token", JsonUtil.objectToJsonString(dto));
			logger.info("get Resp :" + resp);
			GetTokenResp getTokenResp = JsonUtil.jsonStringToObject(resp, GetTokenResp.class);
			EnvParam.easemobToken  = new EasemobToken(getTokenResp.getAccess_token(),System.currentTimeMillis() + getTokenResp.getExpires_in()*1000,getTokenResp.getApplication());
			return getTokenResp.getAccess_token();
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public String addContacts(String owner, String friend) {
		String token = getToken();
		String postUrl = url + "/users/" + owner + "/contacts/users/" + friend;
		Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);  
		try{
			String resp = httpClientService.post(postUrl, header);
			logger.info("get Resp :" + resp);
			return "success";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public String deleteContacts(String owner, String friend) {
		String token = getToken();
		String postUrl = url + "/users/" + owner + "/contacts/users/" + friend;
		Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);
		try{
			String resp = httpClientService.delete(postUrl, header);
			logger.info("get Resp :" + resp);
			return "success";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public String deleteUser(String userName) {
		String token = getToken();
		String postUrl = url + "/users/" + userName;
		Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);
		try{
			String resp = httpClientService.delete(postUrl, header);
			logger.info("get Resp :" + resp);
			return "success";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public String getToken(){
		EasemobToken easemobToken = EnvParam.easemobToken;
		GetTokenDto dto = new GetTokenDto();
		dto.setClient_id(userId);
		dto.setClient_secret(passwd);
		if (easemobToken == null){
			return getTokenFromServer(dto);
		}else {
			if (System.currentTimeMillis() < easemobToken.getExpiresIn()) {
				return easemobToken.getToken();
			} else {
				return getTokenFromServer(dto);
			}
		}
	}

	@Override
	public String updateNick(String userId, String nick) {
		String token = getToken();
		String postUrl = url + "/users/" + userId;
		String jsonStr = "{\"nickname\" : \"" + nick + "\"}";
		Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);
		try{
			String resp = httpClientService.put(postUrl, header, jsonStr);
			logger.info("get Resp :" + resp);
			System.out.println(resp);
			return "success";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public String resetPassword(String userId, String password) {
		String token = getToken();
		String postUrl = url + "/users/" + userId + "/password";
		String jsonStr = "{\"newpassword\" : \"" + password + "\"}";
		Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);
		try{
			String resp = httpClientService.put(postUrl, header, jsonStr);
			logger.info("get Resp :" + resp);
			return "success";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public String createGroup(GroupDto dto) {
		try{
			String token = getToken();
			String postUrl = url + "/chatgroups/" ;
			String jsonStr = JsonUtil.objectToJsonString(dto);
			logger.info("get request :" + jsonStr);
			Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token); 
			String resp = httpClientService.postJson(postUrl, jsonStr, header);
			logger.info("get Resp :" + resp);
			GroupCreateResp groupCreateResp = JsonUtil.jsonStringToObject(resp, GroupCreateResp.class);
			return groupCreateResp.getData().getGroupid();
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}	
	}

	@Override
	public String deleteGroup(String groupId) {
		
		String token = getToken();
		String postUrl = url + "/chatgroups/" + groupId;
		Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);
		try{
			String resp = httpClientService.delete(postUrl, header);
			logger.info("get Resp :" + resp);
			return "success";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}
	
	@Override
	public String addGroupMember(String groupId, String username) {
		try{
			String token = getToken();
			String postUrl = url + "/chatgroups/" + groupId + "/users/" + username;
			//String jsonStr = JsonUtil.objectToJsonString(usernames);
			Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token); 
			String resp = httpClientService.post(postUrl,  header);
			logger.info("get Resp :" + resp);
			
			return "";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}	
	}

	@Override
	public String deleteGroupMember(String groupId, List<String> usernames) {
		String token = getToken();
		Joiner joiner = Joiner.on(",");
		
		String postUrl = url + "/chatgroups/" + groupId + "/users" + joiner.join(usernames);
		Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);
		try{
			String resp = httpClientService.delete(postUrl, header);
			logger.info("get Resp :" + resp);
			return "success";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

	@Override
	public String updateGroup(String groupId, GroupUpdateDto dto) {
		String token = getToken();
		String postUrl = url + "/chatgroups/" + groupId;
		Map<String, String> header =  ImmutableMap.of("Authorization", "Bearer " + token);
		String jsonStr = JsonUtil.objectToJsonString(dto);
		try{
			String resp = httpClientService.put(postUrl, header, jsonStr);
			logger.info("get Resp :" + resp);
			return "success";
		} catch (Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return "";
		}
	}

}
