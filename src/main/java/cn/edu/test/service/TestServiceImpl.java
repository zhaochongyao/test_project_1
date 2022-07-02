package cn.edu.test.service;

import cn.edu.test.api.TestService;
import cn.edu.test.mapper.TestUserMapper;
import cn.edu.test.model.TestUser;
import cn.edu.test.model.rpc.LoginRequest;
import cn.edu.test.model.rpc.LoginResponse;
import cn.edu.test.model.rpc.RpcPerson;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private TestUserMapper testUserMapper;
    @Autowired
    public void setTestUserMapper(TestUserMapper userMapper) {
        this.testUserMapper = userMapper;
    }
    @Override
    public List<RpcPerson> SearchPerson(RpcPerson person) {
        QueryWrapper<TestUser> userQuery = new QueryWrapper<>();
        //userQuery.eq("name", person.name);
        List<TestUser> users = testUserMapper.selectList(userQuery);
        System.out.println("role_key:" + users.get(0).role_key);
        List<RpcPerson> resultList = new ArrayList<>();
        for (TestUser u : users) {
            RpcPerson p = new RpcPerson();
            p.account = u.account;
            p.name = u.name;
            p.password = u.password;
            p.role_key = u.role_key;
            resultList.add(p);
        }
        return resultList;
    }

    @Override
    public LoginResponse Login(LoginRequest request) {
        QueryWrapper<TestUser> userQuery = new QueryWrapper<>();
        userQuery.eq("account", request.account);
        userQuery.eq("password", request.password);
        TestUser user = testUserMapper.selectOne(userQuery);
        if (user == null) {
            // 失败
            LoginResponse response = new LoginResponse();
            response.status = "登录失败";
            return response;
        }
        LoginResponse response = new LoginResponse();
        response.token = user.account + user.password;
        response.status = "登录成功";
        return response;
    }
}
