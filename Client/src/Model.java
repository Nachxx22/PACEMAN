package Client.src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.IOException;


import Client.src.AbstractFactory.*;
import Client.src.AbstractFactory.Entity.Enemy.*;
import Client.src.AbstractFactory.Entity.Object.Object;
import Client.src.Controller.JoystickReader;
import Client.src.socket.Cliente;
import Client.src.socket.Json;

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

    private Integer N_GHOSTS = 3;

    private Integer powered;
    private Integer lives, score;
    private Integer scoreupdate=100;
    private Integer[] dx, dy;
    private Integer[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;
    private Integer[] enemy_x, enemy_y, enemy_dx, enemy_dy, enemySpeed;

    private JoystickReader reader;
    private Image heart, ghost;
    private Image up, down, left, right;
    private Image cherryIMG,strawberryIMG, pastillaIMG, appleIMG, bashfulIMG, speedyIMG, pinkyIMG, shadowIMG;

    private Integer pacman_x, pacman_y, pacmand_x, pacmand_y;
    private  Integer req_dx, req_dy;
    private List<Enemy> enemies;
    private List<Object> objects;
    private Timer timer;

    private Cliente clientes;

    private String[] tags;
    private Integer[] valores;
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
    static public Model modelptr = null;
    static public Model getInstance() throws IOException {
        if (modelptr == null) {
            modelptr = new Model();
        }
        return modelptr;
    }

    public Model() throws IOException {
        // Initialize the game board and objects
        enemies = new ArrayList<>();
        objects = new ArrayList<>();
        enemyFactory = new EnemyFactory();
        objectFactory = new ObjectFactory();
        loadImages();
        // Crear objeto para lector de joystick de Arduino
        JoystickReader reader = new JoystickReader();

        // Iniciar hilo para leer continuamente los valores del joystick
        Thread joystickThread = new Thread(reader);
        joystickThread.start();
        initVariables();

        Socket socket = new Socket("localhost",8884);
        clientes = new Cliente(socket , "luis");
        clientes.readMessaje();


        addKeyListener(new TAdapter());
        addKeyListener(new TAdapter2(reader));
        setFocusable(true);
        setFocusable(true);
        setBackground(Color.black);
        timer.start();

        initGame();
    }
    
    
    private void loadImages() {
    	down = new ImageIcon("Client/src/images/down.gif").getImage();
    	up = new ImageIcon("Client/src/images/down.gif").getImage();
    	left = new ImageIcon("Client/src/images/left.gif").getImage();
    	right = new ImageIcon("Client/src/images/right.gif").getImage();
        ghost = new ImageIcon("Client/src/images/ghost.gif").getImage();
        heart = new ImageIcon("Client/src/images/heart.png").getImage();

        cherryIMG = new ImageIcon("Client/src/images/cherry.png").getImage();
        strawberryIMG = new ImageIcon("Client/rc/images/strawberry.png").getImage();
        pastillaIMG = new ImageIcon("Client/src/images/pastilla.png").getImage();
        appleIMG = new ImageIcon("Client/src/images/apple.png").getImage();

        bashfulIMG = new ImageIcon("Client/src/images/Bashful.gif").getImage();
        speedyIMG = new ImageIcon("Client/src/images/Speedy.gif").getImage();
        pinkyIMG = new ImageIcon("Client/src/images/Pinky.gif").getImage();
        shadowIMG = new ImageIcon("Client/src/images/Shadow.gif").getImage();

    }
       private void initVariables() {

        screenData = new Short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new Integer[MAX_GHOSTS];
        ghost_dx = new Integer[MAX_GHOSTS];
        ghost_y = new Integer[MAX_GHOSTS];
        ghost_dy = new Integer[MAX_GHOSTS];
        ghostSpeed = new Integer[MAX_GHOSTS];
        enemy_x = new Integer[MAX_GHOSTS];
        enemy_dx = new Integer[MAX_GHOSTS];
        enemy_y = new Integer[MAX_GHOSTS];
        enemy_dy = new Integer[MAX_GHOSTS];
        enemySpeed = new Integer[MAX_GHOSTS];
        tags= new String[5];
        valores=new Integer[5];
        dx = new Integer[4];
        dy = new Integer[4];
        powered=0;
        
        timer = new Timer(40, this);
        timer.start();
    }

    private void playGame(Graphics2D g2d) {

        if (dying) {

            death();

        } else {

            //System.out.println(powered);
            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            moveEnemys(g2d);
            CollisionObjects(g2d);
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
        if(score%10000==0 && score !=0){lives++;score+=scoreupdate;} //importante cambiar esto con lo del servidor
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
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (Integer) (ghost_y[i] / BLOCK_SIZE);

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

            //Aqui se maneja las colisiones con los fantasmas
            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12)
                    && inGame) {

                if(powered==1){
                    powered=0;
                    tags[0]="colision2";
                    valores[0]=100;
                    clientes.sendMessage(tags,valores);
                    //sumar puntaje, los ghost no son eliminados
                }else{
                    tags[0]="colision1";
                    valores[0]=1;
                    clientes.sendMessage(tags,valores);
                    dying = true;
                }

            }
        }
    }
    private void moveEnemys(Graphics2D g2d) {

        Integer pos;
        Integer count;

        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).getX() % BLOCK_SIZE == 0 && enemies.get(i).getY() % BLOCK_SIZE == 0) {
                pos = enemies.get(i).getX() / BLOCK_SIZE + N_BLOCKS * (Integer) (enemies.get(i).getY() / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && enemies.get(i).getdX()  != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && enemies.get(i).getdY() != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && enemies.get(i).getdX() != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && enemies.get(i).getdY() != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        //enemy_dx[i] = 0;
                        enemies.get(i).setdX(0);
                        enemies.get(i).setdY(0);
                        //enemy_dy[i] = 0;
                    } else {
                       // enemy_dx[i] = -enemy_dx[i];
                        enemies.get(i).setdX(-(enemies.get(i).getdX()));
                        enemies.get(i).setdY(-(enemies.get(i).getdY()));
                        //enemy_dy[i] = -enemy_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    //enemy_dx[i] = dx[count];
                    enemies.get(i).setdX(dx[count]);
                    enemies.get(i).setdY(dy[count]);
                    //enemy_dy[i] = dy[count];
                }

            }

            enemy_x[i] = (enemies.get(i).getX())+ ((enemies.get(i).getdX())*(enemies.get(i).getSpeed()));
            enemy_y[i] = (enemies.get(i).getY()) + ((enemies.get(i).getdY())*(enemies.get(i).getSpeed()));
            enemies.get(i).setX(enemy_x[i]);
            enemies.get(i).setY(enemy_y[i]);
            //drawGhost(g2d,enemy_x[i] + 1, enemy_y[i] + 1);
            enemies.get(i).draw(g2d,enemies.get(i).getX() + 1, enemies.get(i).getY() + 1);

            //Para manejar las colisiones del pacman con los enemigos
            if (pacman_x > (enemy_x[i] - 12) && pacman_x < (enemy_x[i] + 12)
                    && pacman_y > (enemy_y[i] - 12) && pacman_y < (enemy_y[i] + 12)
                    && inGame) {

                //mensaje al servidor colision con enemy
                //mensaje del servidor con la instruccion
                if(powered==1){
                    //sumar puntaje, los ghost no son eliminados
                    powered=0;
                    enemies.remove(i);
                    tags[0]="colision2";
                    valores[0]=100;
                    clientes.sendMessage(tags,valores);
                }else{
                    //PARA INDICARLE AL SERVIDOR QUE HUBO UNA
                    //COLISION.
                    tags[0]="colision1";
                    valores[0]=1;
                    clientes.sendMessage(tags,valores);
                    dying = true;
                }
            }
        }
    }
    private void CollisionObjects(Graphics2D g2d) {

        Integer pos;
        Integer count;

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getX() % BLOCK_SIZE == 0 && objects.get(i).getY() % BLOCK_SIZE == 0) {
                pos = objects.get(i).getX() / BLOCK_SIZE + N_BLOCKS * (Integer) (objects.get(i).getY() / BLOCK_SIZE);

                count = 0;

                if ((screenData[pos] & 1) == 0 && enemies.get(i).getdX()  != 1) {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 2) == 0 && enemies.get(i).getdY() != 1) {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                if ((screenData[pos] & 4) == 0 && enemies.get(i).getdX() != -1) {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                if ((screenData[pos] & 8) == 0 && enemies.get(i).getdY() != -1) {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                if (count == 0) {

                    if ((screenData[pos] & 15) == 15) {
                        //enemy_dx[i] = 0;
                        enemies.get(i).setdX(0);
                        enemies.get(i).setdY(0);
                        //enemy_dy[i] = 0;
                    } else {
                        // enemy_dx[i] = -enemy_dx[i];
                        enemies.get(i).setdX(-(enemies.get(i).getdX()));
                        enemies.get(i).setdY(-(enemies.get(i).getdY()));
                        //enemy_dy[i] = -enemy_dy[i];
                    }

                } else {

                    count = (int) (Math.random() * count);

                    if (count > 3) {
                        count = 3;
                    }

                    //enemy_dx[i] = dx[count];
                    //enemies.get(i).setdX(dx[count]);
                    //enemies.get(i).setdY(dy[count]);
                    //enemy_dy[i] = dy[count];
                }

            }

            //object_x[i] = (objects.get(i).getX())+ ((objects.get(i).getdX())*(enemies.get(i).getSpeed()));
            //object_y[i] = (objects.get(i).getY()) + ((objects.get(i).getdY())*(enemies.get(i).getSpeed()));
            //enemies.get(i).setX(enemy_x[i]);
            //enemies.get(i).setY(enemy_y[i]);
            //drawGhost(g2d,enemy_x[i] + 1, enemy_y[i] + 1);
            //enemies.get(i).draw(g2d,enemies.get(i).getX() + 1, enemies.get(i).getY() + 1);

            //Para manejar las colisiones del pacman con los objetos
            if (pacman_x > (objects.get(i).getX() - 12) && pacman_x < (objects.get(i).getX() + 12)
                    && pacman_y > (objects.get(i).getY() - 12) && pacman_y < ( objects.get(i).getY() + 12)
                    && inGame) {
                System.out.println(objects.get(i));

                //if (objects.get(i). instanceof PastillaObject) {
                if(objects.get(i).getScore()== 500){
                    System.out.println("Colisionó con pastilla");
                    score+=objects.get(i).getScore();//Sumar el puntaje del objeto
                    tags[0]="colision4";
                    valores[0]=objects.get(i).getScore();
                    clientes.sendMessage(tags,valores);
                    powered=1;
                }else{
                    System.out.println("Se suman: "+objects.get(i).getScore()+" Puntos");
                    score+=objects.get(i).getScore();//Sumar el puntaje del objeto
                    tags[0]="colision3";
                    valores[0]=objects.get(i).getScore();
                    clientes.sendMessage(tags,valores);

                    //Eliminar el objeto
                    //mensaje al servidor colision con enemy
                    //mensaje del servidor con la instruccion
                    //dying = true;

                }
                objects.remove(i);
            }
        }
    }
    /*
    private void ControllerMoves(JoystickReader reader) {
        if (inGame) {
            if (reader.getXValue() < 480) {
                //Mover pacman hacia la izquierda
                req_dx = -1;
                req_dy = 0;
            } else if (reader.getXValue() > 680) {
                //Mover pacman hacia la derecha
                req_dx = 1;
                req_dy = 0;
            } else if (reader.getYValue() < 460) {
                //Mover pacman hacia arriba
                req_dx = 0;
                req_dy = -1;
            } else if (reader.getYValue() > 680) {
                //Mover pacman hacia abajo
                req_dx = 0;
                req_dy = 1;
            }
        }
    }

     */

    private void HandleMessage(String mssg){
        //Para manejar los mensajes del servidor recibidos por socket
    }

    private void drawGhost(Graphics2D g2d, Integer x, Integer y) {
    	g2d.drawImage(ghost, x, y, this);
        }

    private void movePacman() {

        Integer pos;
        Short ch;

        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (Integer) (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            //Colisiones con los puntos normales
            if ((ch & 16) != 0) {
                screenData[pos] = (short) (ch & 15);
                score+=scoreupdate;
                tags[0]="colision3";
                valores[0]=scoreupdate;
                clientes.sendMessage(tags,valores);
                //mensaje al servidor colision con punto
                //mensaje recibido y instruccion
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
                for (Object object: objects){
                    object.draw(g2d);
                }

                i++;
            }
        }
    }

    private void initGame() {

    	lives = 3;
        score = 0;
        initLevel();
        N_GHOSTS = 3;
        currentSpeed = 3;
    }

    private void initLevel() {

        Integer i;
        for (i = 0; i < N_BLOCKS * N_BLOCKS; i++) {
            screenData[i] = levelData[i];
        }

        continueLevel();
        continueLevel2();
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
    private void continueLevel2() {

        Integer dx = 1;
        Integer random;

        for (int i = 0; i < enemies.size(); i++) {

            enemy_y[i] = 4 * BLOCK_SIZE; //start position
            enemy_x[i] = 4 * BLOCK_SIZE;
            enemies.get(i).setY(4 * BLOCK_SIZE);
            enemies.get(i).setX(4 * BLOCK_SIZE);
            enemy_dy[i] = 0;
            enemies.get(i).setdX(0);
            enemy_dx[i] = dx;
            enemies.get(i).setdX(dx);
            dx = -dx;

            System.out.println("cantidad de enemigos: "+ enemies.size());
            if (enemies.size() != 0){
                enemySpeed[i] = enemies.get(i).getSpeed();
            }else{
                factory("CreateEnemy Shadow");
                enemySpeed[i]=enemies.get(i).getSpeed();
            }
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
    public void  factory(String mssg){
        if(mssg.startsWith("CreateEnemy")){
            String enemyType = mssg.substring(12);
            System.out.println("El enemigo a crear es:" +enemyType);
            EnemyFactory enemyFactory = new EnemyFactory();
            Enemy enemy = enemyFactory.createEnemy(enemyType);
            // se añade el enemigo a la lista de enemies y se repinta el juego
            enemies.add(enemy);
            repaint();
        }
        else if(mssg.startsWith("CreateObject")){
            String objectType = mssg.substring(13);
            ObjectFactory objectFactory = new ObjectFactory();
            System.out.println("El objeto a crear es:" +objectType);
            Object object = objectFactory.createObject(objectType);
            // se añade el enemigo a la lista de objets y se repinta el juego
            objects.add(object);
            repaint();
        }

    }
    public void updateData(String mssg){
        if(mssg.startsWith("UpdateScore")){
            Integer update = Integer.valueOf(mssg.substring(12));
            System.out.println("El puntaje es: "+update);
            score+=update;

        }
        else if(mssg.startsWith("UpdateLife")){
            Integer update = Integer.valueOf(mssg.substring(11));
            System.out.println("La vida es: "+update);
            lives+=update;
        }
    }

 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    // Controlador de eventos de teclado
    //controls
    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_LEFT) {
                    req_dx = -1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    req_dx = 1;
                    req_dy = 0;
                } else if (key == KeyEvent.VK_UP) {
                    req_dx = 0;
                    req_dy = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    req_dx = 0;
                    req_dy = 1;
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                }
                // Para crear enemigos y objetos con ciertas teclas
                else if (e.getKeyCode() == KeyEvent.VK_1) {
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
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    // Create an Orange object
                    factory("CreateObject Pastilla");
                }
            } else {
                if (key == KeyEvent.VK_SPACE) {
                    inGame = true;
                    initGame();
                }
            }
        }
    }
    class TAdapter2 extends KeyAdapter {
        private JoystickReader reader;

        public TAdapter2(JoystickReader reader) {
            this.reader = reader;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (inGame) {
                if ( reader.getXValue() < 480) {
                    //Mover pacman hacia la izquierda
                    req_dx = -1;
                    req_dy = 0;
                } else if ( reader.getXValue() > 680) {
                    //Mover pacman hacia la derecha
                    req_dx = 1;
                    req_dy = 0;
                } else if ( reader.getYValue() < 460) {
                    //Mover pacman hacia arriba
                    req_dx = 0;
                    req_dy = -1;
                } else if ( reader.getYValue() > 680) {
                    //Mover pacman hacia abajo
                    req_dx = 0;
                    req_dy = 1;
                }
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
