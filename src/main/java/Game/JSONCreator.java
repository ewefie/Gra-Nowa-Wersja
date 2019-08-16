package Game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONCreator extends JSONArray {
    public static JSONObject convertToJSON(Board board) {
        JSONObject savedBoard = new JSONObject();

        savedBoard.put("inGame", board.isInGame());
        savedBoard.put("winner", board.isWinner());
        savedBoard.put("looser", board.isLooser());
        savedBoard.put("playersTurn", board.isPlayersTurn());

        JSONArray organismsArray = new JSONArray();

        board.getTempList().stream().forEach(organism ->
                {
                    JSONObject JSONorganism = new JSONObject();
                    JSONObject actualLocation = new JSONObject();
                    JSONObject newLocation = new JSONObject();

                    JSONorganism.put("strength", organism.getStrength());
                    JSONorganism.put("defeated", organism.isDefeated());
                    JSONorganism.put("age", organism.getAge());

                    actualLocation.put("X", organism.getActualLocation().getTileX());
                    actualLocation.put("Y", organism.getActualLocation().getTileY());
                    JSONorganism.put("actualLocation", actualLocation);

                    newLocation.put("X", organism.getNewTile().getTileX());
                    newLocation.put("Y", organism.getNewTile().getTileY());
                    JSONorganism.put("newTile", newLocation);

                    JSONorganism.put("organismType", organism.getOrganismType().toValue());

                    organismsArray.add(JSONorganism);
                }
        );
        savedBoard.put("organisms", organismsArray);

        //tutaj konweruję gracza
        JSONObject player = new JSONObject();

        JSONObject actualLocation = new JSONObject();
        JSONObject newLocation = new JSONObject();

        player.put("strength", board.getPlayer().getStrength());
        player.put("defeated", board.getPlayer().isDefeated());
        player.put("age", board.getPlayer().getAge());

        actualLocation.put("X", board.getPlayer().getActualLocation().getTileX());
        actualLocation.put("Y", board.getPlayer().getActualLocation().getTileY());
        player.put("actualLocation", actualLocation);

        newLocation.put("X", board.getPlayer().getNewTile().getTileX());
        newLocation.put("Y", board.getPlayer().getNewTile().getTileY());
        player.put("newTile", newLocation);


        JSONArray playerAbilities = new JSONArray();

        board.getPlayer().getAbilitiesList().stream().forEach(ability -> {
            JSONObject JSONability = new JSONObject();
            JSONability.put("name", ability.getName());
            JSONability.put("active", ability.isActive());
            JSONability.put("abilityDuration", ability.getAbilityDuration());
            JSONability.put("abilityCooldown", ability.getAbilityCooldown());
            playerAbilities.add(JSONability);
        });
        player.put("abilities", playerAbilities);

        savedBoard.put("player", player);

        return savedBoard;
    }
}
