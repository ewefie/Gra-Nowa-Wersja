package Game;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class GUI extends JFrame implements ActionListener {

    private JPanel mainPanel, logPanel, legendPanel;
    private Board board;
    private JScrollPane scrollPane;

    private JTextArea textArea;

    private final static String newline = "\n";
    private static final int WIDTH = 690;
    private static final int HEIGHT = 713;
    private ImageIcon gameIcon, antelopeIcon, dandelionIcon, foxIcon, grassIcon, wolfIcon, wolfberryIcon, guaranaIcon, tortioseIcon, playerIcon, tileIcon, sheepIcon;
    private JMenuBar menuBar;

    private JMenu gameMenu;
    private JMenuItem help, saveGame, loadGame;

    private JLabel antelopeLabel, dandelionLabel, foxLabel, grassLabel, guaranaLabel, wolfLabel, wolfberryLabel, tortoiseLabel, playerLabel, sheepLabel;
    private String helpMessage = "Help" + newline + "Help" + newline + "Help" + newline + "Help" + newline + "Help" + newline + "Help" + newline + "Help" + newline;

    private final JFileChooser fc = new JFileChooser();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }

    public GUI() {
        run();
    }

    private void run() {
        createComponents();
    }

    private void createComponents() {
        loadIcons();
        createMenu();
        createGameWindow();
        createFrame();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        createLegend();
    }

    private void loadIcons() {
        gameIcon = new ImageIcon(("images/KONIEC.png"));
        antelopeIcon = new ImageIcon("images/antylopa.png");
        dandelionIcon = new ImageIcon("images/mlecz.png");
        foxIcon = new ImageIcon("images/fox.png");
        grassIcon = new ImageIcon("images/trawa.png");
        wolfIcon = new ImageIcon("images/wolf.png");
        wolfberryIcon = new ImageIcon("images/wolfberry.png");
        playerIcon = new ImageIcon("images/player.png");
        tileIcon = new ImageIcon("images/ziemia.png");
        sheepIcon = new ImageIcon("images/sheep.png");
        tortioseIcon = new ImageIcon("images/tortoise.png");
        guaranaIcon = new ImageIcon("images/guarana.png");
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

        playerLabel = new JLabel("Game.Player", playerIcon, JLabel.CENTER);
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
        saveGame = new JMenuItem("Save game");
        saveGame.addActionListener(this::actionPerformed);
        loadGame = new JMenuItem("Load game");
        loadGame.addActionListener(this::actionPerformed);
        help = new JMenuItem("Help");
        help.addActionListener(this::actionPerformed);
        menuBar.add(help);

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

    private void createGameWindow() {
        mainPanel = (JPanel) getContentPane();
        board = new Board();

        logPanel = new JPanel();
        legendPanel = new JPanel();

        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        logPanel.setVisible(true);
        logPanel.setBackground(Color.ORANGE);
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
        legendPanel.setBounds(449, 0, 226, 448);
        legendPanel.setBackground(Color.ORANGE);
        legendPanel.setBorder(new TitledBorder(new EtchedBorder(), "Legend: "));

        mainPanel.add(board);
        mainPanel.add(logPanel);
        mainPanel.add(legendPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadGame) {
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
            }
        } else if (e.getSource() == saveGame) {
            int returnVal = fc.showSaveDialog(this);

        } else if (e.getSource() == help) {
            JOptionPane.showMessageDialog(this, helpMessage, "Help", JOptionPane.PLAIN_MESSAGE);
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

    public JTextArea getTextArea() {
        return textArea;
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
