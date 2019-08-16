package Game;

import Game.Organisms.Organism;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {
    private static final FileLoader instance = new FileLoader();
    protected static String env = System.getProperty("user.dir");
    protected static Board board;

    public static FileLoader getInstance() {
        return instance;
    }

    private FileLoader() {
        if (instance != null) {
            throw new IllegalStateException("Cannot instantiate a new singleton instance");
        }
    }

    public static void openFile(int n) throws Exception {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(env + '/' + "saves" + '/' + "Save_" + n + ".json")) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonBoard = (JSONObject) obj;
            convertToBoard(jsonBoard);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void convertToBoard(JSONObject jsonBoard) {
        board = new Board();
        board.setInGame((Boolean) jsonBoard.get("inGame"));
        board.setWinner((Boolean) jsonBoard.get("winner"));
        board.setLooser((Boolean) jsonBoard.get("looser"));
        board.setPlayersTurn((Boolean) jsonBoard.get("playersTurn"));

        JSONObject player = (JSONObject) jsonBoard.get("player");
        parsePlayer(player);

        JSONArray organisms = (JSONArray) jsonBoard.get("organisms");
        organisms.forEach(organism -> parseOrganisms((JSONObject) organism));

        runGame();
    }

    private static void runGame() {
        GUI gui = new GUI();
        gui.cleanMainPanel();
        gui.setVisible(true);
        gui.runFromSave(board);
    }

    private static void parsePlayer(JSONObject player) {
        Player newPlayer = new Player(board);

        JSONObject actualLocation = (JSONObject) player.get("actualLocation");
//parser JSON wszystkie int traktuje jak longi, więc trzeba je zrutowac na inty
        Tile aLocation = new Tile();
        aLocation.setTileX((int) ((long) actualLocation.get("X")));
        aLocation.setTileY((int) ((long) actualLocation.get("Y")));

        JSONObject newLocation = (JSONObject) player.get("newTile");

        Tile nLocation = new Tile();
        long x = (long) newLocation.get("X");
        long y = (long) newLocation.get("Y");

        nLocation.setTileX((int) x);
        nLocation.setTileY((int) y);

        newPlayer.setActualLocation(aLocation);
        newPlayer.setNewTile(nLocation);

        newPlayer.setStrength((int) ((long) player.get("strength")));
        newPlayer.setDefeated((Boolean) player.get("defeated"));
        newPlayer.setAge((int) ((long) player.get("age")));

        board.setPlayer(newPlayer);
        board.getOrganismMap().put(aLocation, newPlayer);

        JSONArray abilities = (JSONArray) player.get("abilities");
        abilities.forEach(ability -> parseAbilities((JSONObject) ability));
    }

    private static void parseAbilities(JSONObject ability) {
        String name = (String) ability.get("name");

        board.getPlayer().getAbilitiesList().stream().forEach(playerAbility -> {
            if (playerAbility.getName().equalsIgnoreCase(name)) {
                playerAbility.setAbilityCooldown((int) ((long) ability.get("abilityCooldown")));
                playerAbility.setAbilityDuration((int) ((long) ability.get("abilityDuration")));
                playerAbility.setActive((Boolean) ability.get("active"));
            }
        });
    }

    private static void parseOrganisms(JSONObject organism) {
        JSONObject actualLocation = (JSONObject) organism.get("actualLocation");

        Tile aLocation = new Tile();
        aLocation.setTileX((int) ((long) actualLocation.get("X")));
        aLocation.setTileY((int) ((long) actualLocation.get("Y")));

        JSONObject newLocation = (JSONObject) organism.get("newTile");

        Tile nLocation = new Tile();
        nLocation.setTileX((int) ((long) newLocation.get("X")));
        nLocation.setTileY((int) ((long) newLocation.get("Y")));

        Organism newOrganism = OrganismFactory.create(
                OrganismType.fromValue((String) organism.get("organismType")), aLocation, board
        );

        newOrganism.setStrength((int) ((long) organism.get("strength")));
        newOrganism.setDefeated((Boolean) organism.get("defeated"));
        newOrganism.setAge((int) ((long) organism.get("age")));

        newOrganism.setNewTile(nLocation);

        board.getOrganismMap().put(newOrganism.getActualLocation(), newOrganism);
    }
}
