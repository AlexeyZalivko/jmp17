package jmp.services.data;

import lombok.Data;

/**
 * Created by alex on 24.03.17.
 */
@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private byte[] logo;
}
