package dev.teknowledge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.teknowledge.enums.ROLE;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Table(name = "users")
public class User extends BaseEntity {

    private String name;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private ROLE role = ROLE.USER;

}