package com.henu.oto.service;

import com.henu.oto.BaseTest;
import com.henu.oto.dto.LocalAuthExecution;
import com.henu.oto.entity.LocalAuth;
import com.henu.oto.entity.PersonInfo;
import com.henu.oto.enums.WechatAuthStateEnum;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest {
    @Autowired
    private LocalAuthService localAuthService;

    @Test
    public void testABindLocalAuth() throws Exception{
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        String username = "testusername";
        String password = "testpassword";
        personInfo.setUserId(1L);
        // 给平台账号绑定上用户信息
        localAuth.setPersonInfo(personInfo);
        // 设置用户信息
        localAuth.setUsername(username);
        localAuth.setPassword(password);

        // 绑定账号
        LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(), lae.getState());
        // 通过userId找到新增的localAuth
        localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());

        System.out.println("名称：" + localAuth.getPersonInfo().getName());
        System.out.println("密码：" + localAuth.getPassword());
    }

    @Test
    public void testBModifyLocalAuth(){
        // 设置账号信息
        long userId = 1;
        String username = "testusername";
        String password = "testpassword";
        String newPassword = "testnewpassword";

        LocalAuthExecution lae = localAuthService.modifyLocalAuth(userId, username, password, newPassword);
        assertEquals(WechatAuthStateEnum.SUCCESS.getState(), lae.getState());

        LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(username, newPassword);

        System.out.println(localAuth.getPersonInfo().getName());
    }
}
