package edu.wunschzettel.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "wishes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String kid;

    @Column(nullable = false)
    private String wish;
}
