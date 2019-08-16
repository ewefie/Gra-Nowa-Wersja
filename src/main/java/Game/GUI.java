package Game;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class GUI extends JFrame implements ActionListener {

    private JPanel mainPanel, logPanel, legendPanel, abilitiesPanel;
    private Board board;
    private JScrollPane scrollPane;

    private static final int WIDTH = 690;
    private static final int HEIGHT = 713;
    private ImageIcon gameIcon, antelopeIcon, dandelionIcon, foxIcon, grassIcon, wolfIcon, wolfberryIcon, guaranaIcon, tortioseIcon, playerIcon, tileIcon, sheepIcon;
    private ImageIcon alzursShielIcon, antelopesSpeedIcon, ignitionIcon, immortalityIcon, magicalPotionIcon;
    private JMenuBar menuBar;

    private JMenu gameMenu;
    private JMenuItem help, saveGame, loadGame, newGame;

    private JLabel antelopeLabel, dandelionLabel, foxLabel, grassLabel, guaranaLabel, wolfLabel, wolfberryLabel, tortoiseLabel, playerLabel, sheepLabel;
    private String helpMessage = "Player have 5 special abilities activated by pressing keys 1, 2, 3, 4 or 5. \n" +
            "After using a special ability, it is active for 5 rounds, and after that, for another 5 rounds, it is on cooldown. \n" +
            "* Immortality - player become immortal. \n" +
            "* Ignition - player burns all animals and plants located on tiles next to his location. \n" +
            "* Alzur’s Shield - player moves push back attacks - animals have to move to another tile. \n" +
            "* Antelope’s speed - play" +
            "er move range increases to 2 tiles. \n" +
            "* Magical Potion - player strength increases by 5 points.";

    protected String env = System.getProperty("user.dir");
    Object[] saves = {"Empty", "Empty", "Empty", "Empty", "Empty"};

    private final JFileChooser fc = new JFileChooser();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new GUI();
//            setVisible(true);
        });
    }

    public GUI() {
        run();
    }

    private void run() {
        createComponents(null);
        board.gameInit();
    }


    void runFromSave(Board newBoard) {
        createComponents(newBoard);
        board.gameInitForSavedGame();
        Logger.log("Game loaded");

    }


    private void createComponents(Board newBoard) {
        loadIcons();
        createMenu();
        createGameWindow(newBoard);
        createFrame();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        createLegend();
        createAbilitiesPanel();
        getSaves();
    }

    private void getSaves() {
        for (int i = 1; i <= 5; i++) {
            File file = new File((env + '/' + "saves"), "Save_" + i + ".json");
            if (file.exists()) {
                saves[i - 1] = "Save_" + i;
            }
        }
    }

    private void loadIcons() {
        gameIcon = new ImageIcon(("images/KONIEC.png"));

        tileIcon = new ImageIcon("images/ziemia.png");

        playerIcon = new ImageIcon("images/player.png");

        antelopeIcon = new ImageIcon("images/antylopa.png");
        foxIcon = new ImageIcon("images/fox.png");
        wolfIcon = new ImageIcon("images/wolf.png");
        tortioseIcon = new ImageIcon("images/tortoise.png");
        sheepIcon = new ImageIcon("images/sheep.png");

        dandelionIcon = new ImageIcon("images/mlecz.png");
        grassIcon = new ImageIcon("images/trawa.png");
        wolfberryIcon = new ImageIcon("images/wolfberry.png");
        guaranaIcon = new ImageIcon("images/guarana.png");

        alzursShielIcon = new ImageIcon("images/shield.png");
        antelopesSpeedIcon = new ImageIcon("images/aspeed.png");
        ignitionIcon = new ImageIcon("images/ignition.png");
        immortalityIcon = new ImageIcon("images/immortality.png");
        magicalPotionIcon = new ImageIcon("images/potion.png");
    }

    private void createAbilitiesPanel() {
        GridLayout flo = new GridLayout(5, 1);
        abilitiesPanel.add(new JLabel("Immortality => press \"1\"", immortalityIcon, JLabel.LEFT));
        abilitiesPanel.add(new JLabel("Ignition => press \"2\"", ignitionIcon, JLabel.LEFT));
        abilitiesPanel.add(new JLabel("Alzur's Shield => press \"3\"", alzursShielIcon, JLabel.LEFT));
        abilitiesPanel.add(new JLabel("Antelope's speed=> press \"4\"", antelopesSpeedIcon, JLabel.LEFT));
        abilitiesPanel.add(new JLabel("Magical Potion => press \"5\"", magicalPotionIcon, JLabel.LEFT));
    }

    private void createLegend() {
        GridLayout flo = new GridLayout(5, 2);

        antelopeLabel = new JLabel("Antelope", antelopeIcon, JLabel.CENTER);
        antelopeLabel.setVerticalTextPosition(JLabel.CENTER);
        antelopeLabel.setHorizontalTextPosition(JLabel.RIGHT);

        dandelionLabel = new JLabel("Dandelion", dandelionIcon, JLabel.CENTER);
        dandelionLabel.setVerticalTextPosition(JLabel.CENTER);
        dandelionLabel.setHorizontalTextPosition(JLabel.RIGHT);

        foxLabel = new JLabel("Fox", foxIcon, JLabel.CENTER);
        foxLabel.setVerticalTextPosition(JLabel.CENTER);
        foxLabel.setHorizontalTextPosition(JLabel.RIGHT);

        grassLabel = new JLabel("Grass", grassIcon, JLabel.CENTER);
        grassLabel.setVerticalTextPosition(JLabel.CENTER);
        grassLabel.setHorizontalTextPosition(JLabel.RIGHT);

        guaranaLabel = new JLabel("Guarana", guaranaIcon, JLabel.CENTER);
        guaranaLabel.setVerticalTextPosition(JLabel.CENTER);
        guaranaLabel.setHorizontalTextPosition(JLabel.RIGHT);

        wolfLabel = new JLabel("Wolf", wolfIcon, JLabel.CENTER);
        wolfLabel.setVerticalTextPosition(JLabel.CENTER);
        wolfLabel.setHorizontalTextPosition(JLabel.RIGHT);

        wolfberryLabel = new JLabel("Wolfberry", wolfberryIcon, JLabel.CENTER);
        wolfberryLabel.setVerticalTextPosition(JLabel.CENTER);
        wolfberryLabel.setHorizontalTextPosition(JLabel.RIGHT);

        tortoiseLabel = new JLabel("Tortoise", tortioseIcon, JLabel.CENTER);
        tortoiseLabel.setVerticalTextPosition(JLabel.CENTER);
        tortoiseLabel.setHorizontalTextPosition(JLabel.RIGHT);

        sheepLabel = new JLabel("Sheep", sheepIcon, JLabel.CENTER);
        sheepLabel.setVerticalTextPosition(JLabel.CENTER);
        sheepLabel.setHorizontalTextPosition(JLabel.RIGHT);

        playerLabel = new JLabel("Player", playerIcon, JLabel.CENTER);
        playerLabel.setVerticalTextPosition(JLabel.CENTER);
        playerLabel.setHorizontalTextPosition(JLabel.RIGHT);

        legendPanel.setLayout(flo);
        legendPanel.add(dandelionLabel);
        legendPanel.add(guaranaLabel);
        legendPanel.add(wolfberryLabel);
        legendPanel.add(grassLabel);

        legendPanel.add(antelopeLabel);
        legendPanel.add(foxLabel);
        legendPanel.add(wolfLabel);
        legendPanel.add(tortoiseLabel);
        legendPanel.add(sheepLabel);
        legendPanel.add(playerLabel);
    }


    private void createMenu() {
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        newGame = new JMenuItem("New game");
        newGame.addActionListener(this::actionPerformed);
        saveGame = new JMenuItem("Save game");
        saveGame.addActionListener(this::actionPerformed);
        loadGame = new JMenuItem("Load game");
        loadGame.addActionListener(this::actionPerformed);
        help = new JMenuItem("Help");
        help.addActionListener(this::actionPerformed);
        menuBar.add(help);

        gameMenu.add(newGame);
        gameMenu.add(saveGame);
        gameMenu.add(loadGame);
    }

    private void createFrame() {
        setTitle("\"Virtual World\" Game");
        setIconImage(gameIcon.getImage());
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        setLayout(null);
        setJMenuBar(menuBar);
    }

    public void cleanMainPanel() {
        mainPanel.remove(this.board);
    }

    private void createGameWindow(Board newBoard) {
        mainPanel = (JPanel) getContentPane();
        if (newBoard == null) {
            this.board = new Board();
        } else {
            this.board = newBoard;
        }

        logPanel = new JPanel();
        legendPanel = new JPanel();
        abilitiesPanel = new JPanel();

        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        logPanel.setVisible(true);
        logPanel.setBackground(new Color(135, 206, 250));
        logPanel.setBounds(0, 449, 675, 202);
        logPanel.setBorder(new TitledBorder(new EtchedBorder(), "Actions: "));

        Logger.getInstance().setEditable(false);
        Logger.getInstance().setLineWrap(true);
        Logger.getInstance().setWrapStyleWord(true);
        Logger.getInstance().setColumns(58);
        Logger.getInstance().setRows(10);

        scrollPane = new JScrollPane(Logger.getInstance());

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVisible(true);

        logPanel.add(scrollPane);

        legendPanel.setVisible(true);
        legendPanel.setBounds(449, 0, 226, 224);
        legendPanel.setBackground(Color.ORANGE);
        legendPanel.setBorder(new TitledBorder(new EtchedBorder(), "Legend: "));

        abilitiesPanel.setVisible(true);
        abilitiesPanel.setBackground(Color.PINK);
        abilitiesPanel.setBounds(449, 225, 226, 224);
        abilitiesPanel.setBorder(new TitledBorder(new EtchedBorder(), "Abilities: "));

        mainPanel.add(board);
        mainPanel.add(logPanel);
        mainPanel.add(legendPanel);
        mainPanel.add(abilitiesPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadGame) {
            selectGame();
        } else if (e.getSource() == saveGame) {
            saveGame();
        } else if (e.getSource() == help) {
            JOptionPane.showMessageDialog(this, helpMessage, "Help", JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == newGame) {
            board.gameInit();
            board.setInGame(true);
            board.setLooser(false);
            Logger.log("NEW GAME");
        }

    }

    private void selectGame() {
        int n = JOptionPane.showOptionDialog(this,
                "Choose a game to load",
                "Load",
                JOptionPane.DEFAULT_OPTION,//lub yes_no
                JOptionPane.PLAIN_MESSAGE,
                null,
                saves,
                saves[4]);

        if (saves[n].equals("Empty")) {
            JOptionPane.showMessageDialog(this,
                    "This slot i sempty!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                FileLoader.openFile(n + 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.err.println("INFO: Loading Save_" + (n + 1));
        }
    }


    public void saveGame() {
        //fixme: gra zapisuje zawsze na kolejnym wolnym slocie a nie na wskazanym przez użytkownika
        FileSaver.createSaveDirectory();
        int n = JOptionPane.showOptionDialog(this,
                "Choose a slot to save current game",
                "Save",
                JOptionPane.DEFAULT_OPTION,//lub yes_no
                JOptionPane.PLAIN_MESSAGE,
                null,
                saves,
                saves[4]);

        switch (n) {
            case 0:
                FileSaver.saveToFile(board, 1);
                saves[0] = "Save_1";
                return;
            case 1:
                FileSaver.saveToFile(board, 2);
                saves[1] = "Save_2";
                return;
            case 2:
                FileSaver.saveToFile(board, 3);
                saves[2] = "Save_3";
                return;
            case 3:
                FileSaver.saveToFile(board, 4);
                saves[3] = "Save_4";
                return;
            case 4:
                FileSaver.saveToFile(board, 5);
                saves[4] = "Save_5";
                return;
        }
    }

    public ImageIcon getGameIcon() {
        return gameIcon;
    }

    public ImageIcon getAntelopeIcon() {
        return antelopeIcon;
    }

    public ImageIcon getDandelionIcon() {
        return dandelionIcon;
    }

    public ImageIcon getFoxIcon() {
        return foxIcon;
    }

    public ImageIcon getGrassIcon() {
        return grassIcon;
    }

    public ImageIcon getWolfIcon() {
        return wolfIcon;
    }

    public ImageIcon getWolfberryIcon() {
        return wolfberryIcon;
    }

    public ImageIcon getGuaranaIcon() {
        return guaranaIcon;
    }

    public ImageIcon getTortioseIcon() {
        return tortioseIcon;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public ImageIcon getPlayerIcon() {
        return playerIcon;
    }

    public ImageIcon getTileIcon() {
        return tileIcon;
    }

    public ImageIcon getSheepIcon() {
        return sheepIcon;
    }
}
