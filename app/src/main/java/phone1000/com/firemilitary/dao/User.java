package phone1000.com.firemilitary.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table "USER".
 */
@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String account;
    private String password;
    private String nickname;

    @Generated
    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    @Generated
    public User(Long id, String account, String password, String nickname) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getAccount() {
        return account;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setAccount(@NotNull String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
