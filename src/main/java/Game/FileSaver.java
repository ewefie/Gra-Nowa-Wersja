package Game;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class FileSaver {
    private static final FileSaver instance = new FileSaver();
    public String saveName = "Saved Game";
    protected static String env = System.getProperty("user.dir");
//    private static File logFile;

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
        //ścieżka - sprawdzam czy istnieją pliki

//        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh-mm");
//        Calendar cal = Calendar.getInstance();
    }

//    public void createSaveFile() {
//        File saveFile;
//        File saveFolder = new File(env + '/' + "saves");
//        if (!saveFolder.exists()) {
//            System.err.println("INFO: Creating new logs directory in " + env);
//            saveFolder.mkdir();
//        }
//
//        DateFormat dateFormat = new SimpleDateFormat("MMdd/hhmmss");
//        Calendar cal = Calendar.getInstance();
//
//        saveName = saveName + '-' + dateFormat.format(cal.getTime()) + ".json";
//        saveFile = new File(saveFolder.getName(), saveName);
//        try {
//            if (saveFile.createNewFile()) {
//                System.err.println("INFO: Creating new save file");
//            }
//        } catch (IOException e) {
//            System.err.println("ERROR: Cannot create save file");
//            System.exit(1);
//        }
//    }

    public static void saveToFile(Board board, int saveSlot) {
        JSONObject savedGame = JSONCreator.convertToJSON(board);
        try (FileWriter file = new FileWriter(new File((env + '/' + "saves"), "Save_" + saveSlot + ".json"))) {
            file.write(savedGame.toJSONString());
            System.err.println("INFO: Game saved");
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
