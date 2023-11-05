import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import AbstractFactory.Entity.Object.Object;
import Controller.*;
import AbstractFactory.*;
import AbstractFactory.Entity.Enemy.*;

public class Model extends JPanel implements ActionListener {

	private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);
    private boolean inGame = false;
    private boolean dying = false;

    private final Integer BLOCK_SIZE = 24;
    private final Integer N_BLOCKS = 15;
    private final Integer SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final Integer MAX_GHOSTS = 12;
    private final Integer PACMAN_SPEED = 6;

    private Integer N_GHOSTS = 6;
    private Integer lives, score;
    private Integer scoreupdate=100;
    private Integer[] dx, dy;
    private Integer[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private Image heart, ghost;
    private Image up, down, left, right;
    private Image cherryIMG,strawberryIMG, orangeIMG, appleIMG, bashfulIMG, speedyIMG, pinkyIMG, shadowIMG;

    private Integer pacman_x, pacman_y, pacmand_x, pacmand_y;
    private Integer req_dx, req_dy;
    private java.util.List<Enemy> enemies;
    private List<Object> objects;
    private Timer timer;
    private Paceman pacman;
    private AbstractFactory enemyFactory;
    private AbstractFactory objectFactory;

    private final Short levelData[] = {
    	19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
        0,  0,  0,  0,  0,  0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
        19, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
        17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
        21, 0,  0,  0,  0,  0,  0,   0, 17, 16, 16, 16, 16, 16, 20,
        17, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    };

    private final Integer validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final Integer maxSpeed = 6;

    private Integer currentSpeed = 3;
    private Short[] screenData;

    public Model() {
        // Initialize the game board and objects
        enemies = new ArrayList<>();
        objects = new ArrayList<>();
        enemyFactory = new EnemyFactory();
        objectFactory = new ObjectFactory();
        loadImages();
        initVariables();

        // Crear objeto para lector de joystick de Arduino
        JoystickReader reader = new JoystickReader();

        // Iniciar hilo para leer continuamente los valores del joystick
        Thread joystickThread = new Thread(reader);
        joystickThread.start();

        addKeyListener(new TAdapter(reader));
        setFocusable(true);
        setFocusable(true);
        setBackground(Color.black);
        timer.start();

        initGame();
    }
    
    
    private void loadImages() {
    	down = new ImageIcon("src/images/down.gif").getImage();
    	up = new ImageIcon("src/images/down.gif").getImage();
    	left = new ImageIcon("src/images/left.gif").getImage();
    	right = new ImageIcon("src/images/right.gif").getImage();
        ghost = new ImageIcon("src/images/ghost.gif").getImage();
        heart = new ImageIcon("src/images/heart.png").getImage();

        cherryIMG = new ImageIcon("src/images/cherry.png").getImage();
        strawberryIMG = new ImageIcon("src/images/strawberry.png").getImage();
        orangeIMG = new ImageIcon("src/images/orange.png").getImage();
        appleIMG = new ImageIcon("src/images/apple.png").getImage();

        bashfulIMG = new ImageIcon("src/images/Bashful.gif").getImage();
        speedyIMG = new ImageIcon("src/images/Speedy.gif").getImage();
        pinkyIMG = new ImageIcon("src/images/Pinky.gif").getImage();
        shadowIMG = new ImageIcon("src/images/Shadow.gif").getImage();

    }
       private void initVariables() {

        screenData = new Short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new Integer[MAX_GHOSTS];
        ghost_dx = new Integer[MAX_GHOSTS];
        ghost_y = new Integer[MAX_GHOSTS];
        ghost_dy = new Integer[MAX_GHOSTS];
        ghostSpeed = new Integer[MAX_GHOSTS];
        dx = new Integer[4];
        dy = new Integer[4];
        
        timer = new Timer(40, this);
        timer.start();
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    private void showIntroScreen(Graphics2D g2d) {
 
    	String start = "Press SPACE to start";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (SCREEN_SIZE)/4, 150);
    }

    private void drawScore(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(5, 181, 79));
        String s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
        if(score%10000==0 && score !=0){lives++;score+=scoreupdate;}
        for (int i = 0; i < lives; i++) {
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    private void checkMaze() {

        Integer i = 0;
        boolean finished = true;

        while (i < N_BLOCKS * N_BLOCKS && finished) {

            if ((screenData[i]) != 0) {
                finished = false;
            }

            i++;
        }

        if (finished) {

            score += 50;

            if (N_GHOSTS < MAX_GHOSTS) {
                N_GHOSTS++;
            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

    private void death() {

    	lives--;

        if (lives == 0) {
            inGame = false;
        }

        continueLevel();
    }

    private void moveGhosts(Graphics2D g2d) {

        Integer pos;
        Integer count;

        for (int i = 0; i < N_GHOSTS; i++) {
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0) {
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (int) (ghost_y[i] / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    } else {
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }

            }

            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {

                dying = true;
            }
        }
    }

    private void drawGhost(Graphics2D g2d, Integer x, Integer y) {
    	g2d.drawImage(ghost, x, y, this);
        }

    private void movePacman() {

        Integer pos;
        Short ch;

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (int) (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score+=scoreupdate;
            }

            if (req_dx != 0 || req_dy != 0) {
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                }
            }

            // Check for standstill
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        } 
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
    }

    private void drawPacman(Graphics2D g2d) {

        if (req_dx == -1) {
        	g2d.drawImage(left, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dx == 1) {
        	g2d.drawImage(right, pacman_x + 1, pacman_y + 1, this);
        } else if (req_dy == -1) {
        	g2d.drawImage(up, pacman_x + 1, pacman_y + 1, this);
        } else {
        	g2d.drawImage(down, pacman_x + 1, pacman_y + 1, this);
        }
    }

    private void drawMaze(Graphics2D g2d) {

        Short i = 0;
        Integer x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {

                g2d.setColor(new Color(0,72,251));
                g2d.setStroke(new BasicStroke(5));
                
                if ((levelData[i] == 0)) { 
                	g2d.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
                 }

                if ((screenData[i] & 1) != 0) { 
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 2) != 0) { 
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[i] & 4) != 0) { 
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 8) != 0) { 
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[i] & 16) != 0) { 
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);
               }

                i++;
            }
        }
    }

    private void initGame() {

    	lives = 3;
        score = 0;
        initLevel();
        N_GHOSTS = 6;
        currentSpeed = 3;
    }

    private void initLevel() {

        Integer i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
    }

    private void continueLevel() {

    	Integer dx = 1;
        Integer random;

        for (int i = 0; i < N_GHOSTS; i++) {

            ghost_y[i] = 4 * BLOCK_SIZE; //start position
            ghost_x[i] = 4 * BLOCK_SIZE;
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;
            random = (int) (Math.random() * (currentSpeed + 1));

            if (random > currentSpeed) {
                random = currentSpeed;
            }

            ghostSpeed[i] = validSpeeds[random];
        }

        pacman_x = 7 * BLOCK_SIZE;  //start position
        pacman_y = 11 * BLOCK_SIZE;
        pacmand_x = 0;	//reset direction move
        pacmand_y = 0;
        req_dx = 0;		// reset direction controls
        req_dy = 0;
        dying = false;
    }

    //Se encarga de llamar el factory para crear el Enemy o Object dependiendo del mensaje recibido
    public void factory(String mssg){
        if(mssg.startsWith("CreateEnemy")){
            String enemyType = mssg.substring(12);
            System.out.println("El a crear es:" +enemyType);
            EnemyFactory enemyFactory = new EnemyFactory();
            Enemy enemy = enemyFactory.createEnemy(enemyType);
            // se añade el enemigo a la lista de enemies y se repinta el juego
            enemies.add(enemy);
            repaint();
        }
        else if(mssg.startsWith("CreateObject")){
            String objectType = mssg.substring(13);
            ObjectFactory objectFactory = new ObjectFactory();
            System.out.println("El a crear es:" +objectType);
            Object object = objectFactory.createObject(objectType);
            // se añade el enemigo a la lista de objets y se repinta el juego
            objects.add(object);
            repaint();
        }

    }

 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        //Para dibujar a los objetos
        for (Object object : objects) {
            object.draw(g);
            g.drawString("Load and draw " + object.getImageName() + " at (" + object.getX() + ", " + object.getY() + ")", object.getX(), object.getY() - 10);
        }
        //para dibujar los enemigos
        for (Enemy enemy : enemies) {
            enemy.draw(g);
            g.drawString("Load and draw " + enemy.getImageName() + " at (" + enemy.getX() + ", " + enemy.getY() + ")", enemy.getX(), enemy.getY() - 10);
        }


        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    // Controlador de eventos de teclado
    class TAdapter extends KeyAdapter {
        private JoystickReader reader;

        public TAdapter(JoystickReader reader) {
            this.reader = reader;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT || reader.getXValue() < 440) {
                    //Mover pacman hacia la izquierda
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT || reader.getXValue() > 680) {
                    //Mover pacman hacia la derecha
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP || reader.getYValue() < 440) {
                    //Mover pacman hacia arriba
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN || reader.getYValue() > 680) {
                    //Mover pacman hacia abajo
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                }
                // Para crear enemigos y objetos con ciertas teclas
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    // Create a Shadow enemy
                    factory("CreateEnemy Shadow");
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    // Create a Speedy enemy
                    factory("CreateEnemy Speedy");
                } else if (e.getKeyCode() == KeyEvent.VK_3) {
                    // Create a Bashful enemy
                    factory("CreateEnemy Bashful");
                } else if (e.getKeyCode() == KeyEvent.VK_4) {
                    // Create a Pokey enemy
                    factory("CreateEnemy Pokey");
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    // Create an Apple object
                    factory("CreateObject Apple");
                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    // Create a Cherry object
                    factory("CreateObject Cherry");
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    // Create a Strawberry object
                    factory("CreateObject Strawberry");
                } else if (e.getKeyCode() == KeyEvent.VK_O) {
                    // Create an Orange object
                    factory("CreateObject Orange");
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                }
            }
        }
    }



    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            // Move enemies and check for collisions
            for (Enemy enemy : enemies) {
                enemy.move();
            }
            // Repaint the game board
            repaint();
        } else if (e.getActionCommand().startsWith("CreateEnemy")) {
            String enemyType = e.getActionCommand().substring(11);
            EnemyFactory enemyFactory = new EnemyFactory();
            Enemy enemy = enemyFactory.createEnemy(enemyType);
            // Add the new enemy to the list and repaint the game board
            enemies.add(enemy);
            repaint();
        } else if (e.getActionCommand().startsWith("CreateObject")) {
            String objectType = e.getActionCommand().substring(12);
            ObjectFactory objectFactory = new ObjectFactory();
            Object object = objectFactory.createObject(objectType);
            // Add the new object to the list and repaint the game board
            objects.add(object);
            repaint();
        }
    }
    /*
    public void handleMessage(String message) {
        // Handle messages received from the server
        if (message.startsWith("CreateEnemy")) {
            String enemyType = message.substring(12);
            Enemy enemy = enemyFactory.createEnemy(enemyType);
            // Add the new enemy to the list and repaint the game board
            model.getEnemies().add(enemy);
            model.repaint();
        } else if (message.startsWith("CreateObject")) {
            String objectType = message.substring(13);
            Object object = objectFactory.createObject(objectType);
            // Add the new object to the list and repaint the game board
            model.getObjects().add(object);
            model.repaint();
        }
    }

     */




}