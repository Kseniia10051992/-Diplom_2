package generator;

import model.ingredient.Ingredient;
import model.ingredient.NamesOfIngredients;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeneratorIngredients {
    List<Ingredient> ingredientsData = new NamesOfIngredients().getIngredients();
    private Map<String, String[]> ingredientsMap = new HashMap<>();
    private Random random = new Random();


    public Map<String, String[]> getValidIngredients() {
        String[] ingredients = new String[random.nextInt(ingredientsData.size() + 1)];
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i] = ingredientsData.get(random.nextInt(ingredientsData.size())).get_id();
        }
        ingredientsMap.put("ingredients", ingredients);
        return ingredientsMap;
    }

    public Map<String, String[]> getNotFilledIngredients() {

        return ingredientsMap;
    }

    public Map<String, String[]> getWrongIngredients() {
        ingredientsMap.put("ingredients", new String[] {"incorrect hash"});
        return ingredientsMap;
    }
}
