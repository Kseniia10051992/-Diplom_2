package model.order;

import lombok.*;
import model.ingredient.Ingredient;
import model.user.ForOrderUser;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderAfterCreate {
    private Ingredient[] ingredients;
    private String _id;
    private ForOrderUser owner;
    private String status;
    private String name;
    private String createAt;
    private String updateAt;
    private int number;
    private int price;
}
