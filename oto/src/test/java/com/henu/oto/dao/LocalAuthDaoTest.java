package com.henu.oto.dao;

import com.henu.oto.BaseTest;
import com.henu.oto.entity.LocalAuth;
import com.henu.oto.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;

    private static final String username = "testussername";
    private static final String password = "testpassword";

    @Test
    public void testAInsertLocalAuth() throws Exception{
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        // 给平台账号绑定上用户信息
        localAuth.setPersonInfo(personInfo);
        // 设置用户信息
        localAuth.setUsername(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testBQueryLocalByUserNameAndPwd() throws Exception{
        LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, password);
        assertEquals("天天", localAuth.getPersonInfo().getName());
    }

    @Test
    public void testCQueryLocalByUserId() throws Exception{
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        assertEquals("天天", localAuth.getPersonInfo().getName());
    }

    @Test
    public void testDUpdateLocalAuth() throws Exception{
        // 依据账号以及旧密码，修改平台账号密码
        Date now = new Date();
        int effectedNum = localAuthDao.updateLocalAuth(1L, username,
                password, password + "new", now);
        assertEquals(1, effectedNum);

        // 查询最新信息
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        // 输出新密码
        System.out.println(localAuth.getPassword());
    }
}
