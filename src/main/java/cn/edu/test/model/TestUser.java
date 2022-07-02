package cn.edu.test.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class TestUser {
    public long id;
    public String name;
    public String account;
    public String password;
    public String role_key;
    public long test_column;
}
