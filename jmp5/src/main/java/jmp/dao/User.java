package jmp.dao;

import lombok.Data;

import java.util.Date;

/**
 * Created by alex on 01.04.17.
 */
@Data
public class User {
    private Long id;
    private String name;
    private String surname;
    private Date birthday;
}
