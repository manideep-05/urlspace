package com.urlspace.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "urls", uniqueConstraints = {
        @UniqueConstraint(columnNames = "shortCode")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shortCode;
    private String longUrl;
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
