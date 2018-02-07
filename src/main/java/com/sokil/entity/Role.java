package com.sokil.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "users")
@ToString(exclude = "users")
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "ROLE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Column(name = "ROLE")
    private String role;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private Set<UserEntity> users = new HashSet<>();
}
