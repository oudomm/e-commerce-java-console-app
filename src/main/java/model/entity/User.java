package model.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Integer id;
    private String uUuid;
    private String userName;
    private String email;
    private String password;
    private Boolean isDeleted;
}
