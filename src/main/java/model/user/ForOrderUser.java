package model.user;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ForOrderUser {
    private String name;
    private String email;
    private String createAt;
    private String updateAt;
}
