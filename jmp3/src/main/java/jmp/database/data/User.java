package jmp.database.data;

import lombok.Data;

/**
 * Created by alex on 15.03.17.
 */
@Data
public class User {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String mail;
}
