package com.urlspace.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "urls", uniqueConstraints = {
        @UniqueConstraint(columnNames = "shortCode"),
        @UniqueConstraint(columnNames = { "user_id", "longUrl" })

})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String shortCode;
    private String longUrl;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
