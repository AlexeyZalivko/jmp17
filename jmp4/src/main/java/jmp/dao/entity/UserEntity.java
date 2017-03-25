package jmp.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by alex on 24.03.17.
 */
@Entity
@Table(name = "USERS")
@Data
@NamedQueries({
        @NamedQuery(name = "UserEntity.all",
                query = "select u from UserEntity u"),
        @NamedQuery(name = "UserEntity.byLogin",
                query = "select u from UserEntity u where u.login=:login")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "MAIL")
    private String mail;
    @Column(name = "LOGIN")
    private String login;
}
