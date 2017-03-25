package jmp.dao.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by alex on 24.03.17.
 */
@Entity
@Table(name = "LOGO")
@Data
public class LogoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGO")
    private byte[] image;
}
