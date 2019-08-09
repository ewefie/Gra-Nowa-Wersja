
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class GUI extends JFrame implements ActionListener {

    private JPanel mainPanel, gamePanel, logPanel, legendPanel;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private final static String newline = "\n";
    private static final int WIDTH = 690;
    private static final int HEIGHT = 713;
    private ImageIcon gameIcon;

    private JMenuBar menuBar;
    private JMenu gameMenu;

    private JMenuItem help;
    private JMenuItem saveGame;
    private JMenuItem loadGame;


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
        createMenu();
        createGameWindow();
        createFrame();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
        gameIcon = new ImageIcon(("images/KONIEC.png"));
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
        gamePanel = new JPanel();
        logPanel = new JPanel();
        legendPanel = new JPanel();

        mainPanel.setLayout(null);
        mainPanel.setVisible(true);

        gamePanel.setBackground(Color.PINK);
        gamePanel.setBounds(0, 0, 448, 448);
        gamePanel.setVisible(true);
        gamePanel.setBorder(new TitledBorder(new EtchedBorder(), "Game area"));

        logPanel.setVisible(true);
        logPanel.setBackground(Color.ORANGE);
        logPanel.setBounds(0, 449, 675, 202);
        logPanel.setBorder(new TitledBorder(new EtchedBorder(), "Logs: "));

        textArea = new JTextArea(10, 58);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVisible(true);

        logPanel.add(scrollPane);

        legendPanel.setVisible(true);
        legendPanel.setBounds(449, 0, 226, 448);
        legendPanel.setBackground(Color.ORANGE);
        legendPanel.setBorder(new TitledBorder(new EtchedBorder(), "Legend: "));

        mainPanel.add(gamePanel);
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
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
            }
        } else if (e.getSource() == help) {

        }
    }
}
