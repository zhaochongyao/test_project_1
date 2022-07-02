package cn.edu.test.service;

import cn.edu.test.api.TestService;
import cn.edu.test.mapper.TestTableMapper;
import cn.edu.test.model.TestTable;
import cn.edu.test.model.rpc.LoginRequest;
import cn.edu.test.model.rpc.LoginResponse;
import cn.edu.test.model.rpc.RpcPerson;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private TestTableMapper testTableMapper;
    @Autowired
    public void setTestTableMapper(TestTableMapper userMapper) {
        this.testTableMapper = userMapper;
    }
    @Override
    public List<RpcPerson> SearchPerson(RpcPerson person) {
        QueryWrapper<TestTable> userQuery = new QueryWrapper<>();
        List<TestTable> users = testTableMapper.selectList(userQuery);
        System.out.println("role_key:" + users.get(0).role_key);
        List<RpcPerson> resultList = new ArrayList<>();
        for (TestTable u : users) {
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
        QueryWrapper<TestTable> userQuery = new QueryWrapper<>();
        userQuery.eq("account", request.account);
        userQuery.eq("password", request.password);
        TestTable user = testTableMapper.selectOne(userQuery);
        if (user == null) {
            // 失败
            LoginResponse response = new LoginResponse();
            response.status = "登录失败";
            return response;
        }
        LoginResponse response = new LoginResponse();
        response.token = user.account + user.password;
        if (user.account.equals("xiaoming")) {
            user.role_key = "admin";
        }
        response.role_key = user.role_key;
        response.status = "登录成功";
        return response;
    }
}
