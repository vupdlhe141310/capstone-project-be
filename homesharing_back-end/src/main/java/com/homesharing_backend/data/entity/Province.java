package com.homesharing_backend.data.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Province {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url", length = 5000)
    private String imageUrl;
}
