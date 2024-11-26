package com.Bookie.entities;

import com.Bookie.enums.GenreLiterary;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "HistoryEntity")
@Table(name = "history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class HistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "syopsis")
    private String syopsis;
    @Column(name = "publish")
    private Boolean publish = false;
    @ManyToOne
    @JoinColumn(name = "UserEntity_id")
    @JsonIgnore
    private UserEntity creator;
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private GenreLiterary genre;
    @Column(name = "img", columnDefinition = "text", length = 1000)
    private String img;

}
