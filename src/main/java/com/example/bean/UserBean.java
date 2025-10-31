package com.example.bean;

import com.example.dao.UserRepository;
import com.example.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "userBean")
@SessionScoped
//@ApplicationScoped -> CDI
@Getter
@Setter
public class UserBean implements Serializable {

    private User user = new User();
    //@Inject -> CDI
    private UserRepository repository = new UserRepository();
    private List<User> userList;

    public String save() {
        repository.save(user);
        user = new User();
        loadUsers();
        return null;
    }

    public void loadUsers() {
        this.userList = repository.findAll();
    }

    public void edit(User u) {
        this.user = u;
    }

    public void delete(User u) {
        repository.delete(u.getId());
        loadUsers();
    }

    public UserBean() {
    }

    @PostConstruct
    public void initData() {
        if (repository.findAll().isEmpty()) {
            repository.save(new User("ali ahmadi", "ali@example.com", "09123456789"));
            repository.save(new User("zahra mohammadi", "zahra@example.com", "09129876543"));
            repository.save(new User("mohammad rezaee", "mohammad@example.com", "09137654321"));
            repository.save(new User("fateme hoseini", "fateme@example.com", "09141234567"));
            repository.save(new User("hosein karimi", "hossein@example.com", "09155678901"));
            System.out.println("5 users added!");
        }
        loadUsers();
    }
}