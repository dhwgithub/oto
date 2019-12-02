package com.henu.oto.service;

import com.henu.oto.dto.LocalAuthExecution;
import com.henu.oto.entity.LocalAuth;

public interface LocalAuthService {
	/**
	 * 
	 * @param username
	 * @return
	 */
	LocalAuth getLocalAuthByUserNameAndPwd(String username, String password);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);

	/**
	 * 绑定微信，生成平台专属账号
	 * @param localAuth
	 * @return
	 * @throws RuntimeException
	 */
	LocalAuthExecution bindLocalAuth(LocalAuth localAuth)
			throws RuntimeException;

	/**
	 *
	 * @param userId
	 * @param username
	 * @param password
	 * @param newPassword
	 * @return
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String username,
                                       String password, String newPassword)
											throws RuntimeException;
}
