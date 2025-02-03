package sf.USF.Controller.Record;

import java.util.UUID;

public record RecipeDTO(UUID training, UUID exercise, String weight, String reps, Integer sets) {
}
