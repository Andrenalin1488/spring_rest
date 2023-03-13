package com.zakharov.entity;

import com.zakharov.annotation.FieldInfo;
import com.zakharov.annotation.TypeInfo;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@TypeInfo(name = "Persons")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @FieldInfo(name = "#")
    private Long id;

    @Column(name = "ник_участника", nullable = false)
    @FieldInfo(name = "Ник StarMaker")
    private String nickName;

    @Column(name = "id_аккаунта", nullable = false, unique = true)
    @FieldInfo(name = "ID Аккаунта")
    private Long account_id;

    @Column(name = "название_семьи", nullable = false)
    @FieldInfo(name = "Название семьи")
    private String family_name;

    @Column(name = "жанр")
    @FieldInfo(name = "Жанр")
    private String genre;

    @Column(name = "Общий_балл")
    @FieldInfo(name = "Общий балл")
    private Long total_score;
}
