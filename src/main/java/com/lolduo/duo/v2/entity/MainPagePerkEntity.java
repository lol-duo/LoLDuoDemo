package com.lolduo.duo.v2.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "main_page_perk")
public class MainPagePerkEntity {
    @Id
    private Long id;
    @Column
    private String name;
    @Column
    private String imgUrl;

    public MainPagePerkEntity(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }
}