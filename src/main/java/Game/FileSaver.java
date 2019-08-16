package Game;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FileSaver {
    private static final FileSaver instance = new FileSaver();
    public static String saveName = "Save_";
    protected static String env = System.getProperty("user.dir");

    public static FileSaver getInstance() {
        return instance;
    }

    private FileSaver() {
        if (instance != null) {
            throw new IllegalStateException("Cannot instantiate a new singleton instance");
        }
    }

    public static void createSaveDirectory() {
        //sprawdzam czy istnieje folder do savów i jak nie to go tworzę
        File saveFolder = new File(env + '/' + "saves");
        if (!saveFolder.exists()) {
            System.err.println("INFO: Creating new save directory in " + env);
            saveFolder.mkdir();
        }
    }

    public static void saveToFile(Board board, int saveSlot) {
        JSONObject savedGame = JSONCreator.convertToJSON(board);
        try (FileWriter file = new FileWriter(new File((env + '/' + "saves"), saveName + saveSlot + ".json"))) {
            file.write(savedGame.toJSONString());
            System.err.println("INFO: Game saved");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
