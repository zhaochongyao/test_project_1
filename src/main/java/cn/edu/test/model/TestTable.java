package cn.edu.test.model;

import lombok.Data;

@Data
public class TestTable {
    public long id;
    public String name;
    public String account;
    public String password;
    public String role_key;
}
