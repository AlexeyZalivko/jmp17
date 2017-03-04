package org.jmp.part2.patterns.data;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by alex on 04.03.17.
 */
@Data
public class Person implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
}
