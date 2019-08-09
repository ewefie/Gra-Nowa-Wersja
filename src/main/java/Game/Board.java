package Game;

import Game.Organisms.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Board extends JPanel implements ActionListener {

    Random random = new Random();
    private Timer timer;
    private Player player;
    private boolean inGame = false;
    private boolean winner = false;
    private boolean looser = false;
    private boolean playersTurn = true;

    private final int blockNumber = 14;
    private final int blockSize = 32;
    private final int boardSize = blockSize * blockNumber;
    protected Map<Tile, Organism> organismMap;
    protected java.util.List<Organism> tempList;

    private java.util.List<OrganismType> organismTypes = new ArrayList<>(Arrays.asList(OrganismType.ANTELOPE,
            OrganismType.DANDELION, OrganismType.FOX, OrganismType.GRASS, OrganismType.GUARANA,
            OrganismType.SHEEP, OrganismType.TORTOISE, OrganismType.WOLF, OrganismType.WOLFBERRY));

    private final static String newline = "\n";

    private ImageIcon ground;

    public Board() {
        setBounds(0, 0, boardSize, boardSize);
        setVisible(true);
        setBackground(Color.BLUE);
        setLayout(null);
        gameInit();
    }

    public void gameInit() {
        ground = new ImageIcon("images/ziemia.png");
        player = new Player(this);
        organismMap = new HashMap<>();
        tempList = new ArrayList<>();

        fillWorldWithOrganisms();
        addKeyListener(new Al());
        setFocusable(true);

        timer = new Timer(30, this::actionPerformed);
        timer.start();
    }

    public void setLooser(boolean looser) {
        this.looser = looser;
    }

    public java.util.Map<Tile, Organism> getOrganismMap() {
        return organismMap;
    }


    public int getBlockNumber() {
        return blockNumber;
    }


    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public Player getPlayer() {
        return player;
    }

    public void fillWorldWithOrganisms() {
        int tilesNumber = (blockNumber - 2) * 2;
        int howManyOrganisms = tilesNumber * 1;
        int numberOfEveryType = (int) (howManyOrganisms * 0.1);
        Tile location;
        organismMap.put(player.getActualLocation(), player);

        for (int i = 0; i < numberOfEveryType; i++) {
            for (OrganismType organismType : organismTypes) {
                location = findLocationForAnimal();
                organismMap.put(location, OrganismFactory.create(organismType, location, this));
            }
        }
    }

    public Tile findLocationForAnimal() {
        Random random = new Random();
        int tileX;
        int tileY;
        Tile tile;
        do {
            tileX = random.nextInt(this.getBlockNumber());
            tileY = random.nextInt(this.getBlockNumber());
            tile = new Tile(tileX, tileY);
        } while (organismMap.containsKey(tile));
        return tile;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
        if (!playersTurn) {
            Logger.log("**NEW ROUND**");

            organismsAction();
            this.player.reduceCooldowns();
            playersTurn = true;
            player.action();
            checkifwinner();//jesli gracz jest ostatnim obiektem to przestawiam flagę na true
        }
    }

    public List<Organism> getTempList() {
        return tempList;
    }

    public void prepareTempList() {
        tempList = organismMap.values().stream()
                .sorted(Comparator.comparing(Organism::getInitiative, Comparator.reverseOrder())
                        .thenComparing(Organism::getAge, Comparator.reverseOrder()))
                .collect(Collectors.toCollection(LinkedList::new));
        tempList.remove(player);
    }

    public void organismsAction() {
        prepareTempList();
        for (Organism organism : tempList) {
            organism.action();
        }
        increaseOrganismsAge();
    }

    public void increaseOrganismsAge() {
        for (Organism organism : organismMap.values()) {
            organism.setAge(organism.getAge() + 1);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (!inGame) {
            if (winner) {
                winner(g);
            }
            if (looser) {
                looser(g);
            }
            if (!looser && !winner) {
                showIntroScreen(g);
            }

        } else {
            for (int y = 0; y < blockNumber; y++) {
                for (int x = 0; x < blockNumber; x++) {
                    g.drawImage(ground.getImage(), x * blockSize, y * blockSize, null);
                }
            }
            for (Organism organism : organismMap.values()) {
                g.drawImage(organism.getImage(), (organism.getActualLocation().getTileX() * blockSize), (organism.getActualLocation().getTileY() * blockSize), null);

            }

        }
    }


    private void checkifwinner() {
        if (this.organismMap.containsValue(player) && this.organismMap.size() == 1) {
            Logger.log("PLAYER WINS!");
            winner = true;
            inGame = false;
        }
    }
    

    private void showIntroScreen(Graphics g2d) {
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(0, 0 / 2 - 30, 690, 485);
        g2d.setColor(Color.white);
        g2d.drawRect(boardSize / 2 - 150, boardSize / 2 - 30, 300, 50);

        String s = "Press s to start new game.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (boardSize - metr.stringWidth(s)) / 2, boardSize / 2);
    }

    private void looser(Graphics g2d) {
        g2d.setColor(Color.red);
        g2d.fillRect(0, 0 / 2 - 30, 690, 485);
        g2d.setColor(Color.white);
        g2d.drawRect(boardSize / 2 - 150, boardSize / 2 - 30, 300, 50);

        String s = "GAME OVER! Press SPACE to continue";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (boardSize - metr.stringWidth(s)) / 2, boardSize / 2);
    }

    private void winner(Graphics g2d) {
        g2d.setColor(Color.green);
        g2d.fillRect(0, 0 / 2 - 30, 690, 485);
        g2d.setColor(Color.white);
        g2d.drawRect(boardSize / 2 - 150, boardSize / 2 - 30, 300, 50);

        String s = "WINNER! Press SPACE to continue";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (boardSize - metr.stringWidth(s)) / 2, boardSize / 2);
    }


    public class Al extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int stepLength = 1;
            if (player.getAbilitiesList().get(3).isActive()) {
                if (player.getAbilitiesList().get(3).getAbilityDuration() > 2 || random.nextBoolean()) {
                    stepLength = 2;
                }
            } else {
                stepLength = 1;
            }

            int keyCode = e.getKeyCode();
            if (inGame && playersTurn) {
                if (keyCode == KeyEvent.VK_1) {
                    if (!player.getAbilitiesList().get(0).canBeUsed()) {
                    } else {
                        player.getAbilitiesList().get(0).activate();
                        player.move(0, 0);
                        playersTurn = false;
                    }
                } else if (keyCode == KeyEvent.VK_2) {
                    if (!player.getAbilitiesList().get(1).canBeUsed()) {
                    } else {
                        player.getAbilitiesList().get(1).activate();
                        player.move(0, 0);
                        playersTurn = false;
                    }
                } else if (keyCode == KeyEvent.VK_3) {

                    if (!player.getAbilitiesList().get(2).canBeUsed()) {
                    } else {
                        player.getAbilitiesList().get(2).activate();
                        player.move(0, 0);
                        playersTurn = false;
                    }
                } else if (keyCode == KeyEvent.VK_4) {
                    if (!player.getAbilitiesList().get(3).canBeUsed()) {
                    } else {
                        player.getAbilitiesList().get(3).activate();
                        player.move(0, 0);
                        playersTurn = false;
                    }
                } else if (keyCode == KeyEvent.VK_5) {
                    if (!player.getAbilitiesList().get(4).canBeUsed()) {
                    } else {
                        player.getAbilitiesList().get(4).activate(player);
                        player.move(0, 0);
                        playersTurn = false;
                    }
                } else if (keyCode == KeyEvent.VK_UP) {
                    if ((player.getActualLocation().getTileY() - stepLength) >= 0) {
                        player.move(0, -stepLength);
                        Logger.log("Player moved up.");
                        playersTurn = false;
                    }
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    if ((player.getActualLocation().getTileY() + stepLength) < blockNumber) {
                        player.move(0, stepLength);
                        Logger.log("Player moved down.");
                        playersTurn = false;
                    }
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    if ((player.getActualLocation().getTileX() - stepLength) >= 0) {
                        player.move(-stepLength, 0);
                        Logger.log("Player moved left.");
                        playersTurn = false;
                    }
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    if ((player.getActualLocation().getTileX() + stepLength) < blockNumber) {
                        player.move(stepLength, 0);
                        Logger.log("Player moved right.");
                        playersTurn = false;
                    }
                }
            } else if (!inGame) {
                if (winner || looser) {
                    if (keyCode == KeyEvent.VK_SPACE) {
                        winner = false;
                        looser = false;
                    }
                } else if (keyCode == 's' || keyCode == 'S') {
                    gameInit();
                    inGame = true;
                    Logger.log("NEW GAME");
                }
            }
        }
    }
}

