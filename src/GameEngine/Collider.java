package GameEngine;

import GameEngine.GameObjects.*;

import java.awt.Rectangle;

public class Collider {
    Game game;
    private GameObject player;
    private GameObject ball;
    private boolean playerBallDetectionX = false;
    private boolean playerBallDetectionY = false;
    private boolean []hits;

    public Collider(Game game) {
        this.game = game;
        player = findPlayer();
        ball = findBall();
        hits=new boolean[4];
    }

    public void update() {
        if (ball != null)
            BallPaddleCollider();
        BallBrickCollider();
        MagicPlayerCollider();
    }

    public GameObject findPlayer()//playerul nu va fi niciodata distrus, deci nu se ve returna null
    {
        GameObject player = null;
        for (GameObject obj : game.getObjectsManager().getObjects()) {
            if (obj instanceof Player) {
                player = obj;
                break;
            }
        }
        return player;
    }

    public GameObject findBall() {
        GameObject ball = null;//mingea va fi distrusa, dar atunci nu va mai fi randata sau detectata de collider
        for (GameObject obj : game.getObjectsManager().getObjects()) {
            if (obj instanceof Ball) {
                ball = obj;
                break;
            }
        }
        return ball;
    }

    public void BallPaddleCollider() {
        Rectangle ballRectangle = new Rectangle(ball.getPosX(), ball.getPosY(), ball.getWidth(), ball.getHeight());
        Rectangle playerRectangle = new Rectangle(player.getPosX(), player.getPosY() + 7, player.getWidth(), 1);
        if (ballRectangle.intersects(playerRectangle)) {
            resetHits();
            if (!playerBallDetectionY && (ball.getPosX() + ball.getWidth() > playerRectangle.x + 2) && (ball.getPosX() < (playerRectangle.x + playerRectangle.width) - 2)) {
                ((Ball) ball).reverseVelY();
                if (ball.getPosX() + ball.getWidth() < player.getPosX() + player.getWidth() / 2) {
                    ((Ball) ball).decrementVelX();
                } else {
                    ((Ball) ball).incrementVelX();
                }
                playerBallDetectionY = true;
            }
            if (!playerBallDetectionX && (ball.getPosX() + ball.getWidth() > player.getPosX() && (ball.getPosX() + ball.getWidth() < player.getPosX() + 1)) || ((ball.getPosX() < player.getPosX() + player.getWidth()) && (ball.getPosX() > player.getPosX() + player.getWidth() - 1))) {
                ((Ball) ball).reverseVelX();
                playerBallDetectionX = true;
            }
        } else {
            playerBallDetectionX = false;
            playerBallDetectionY = false;
        }
    }

    public boolean RectCircleColliding(int xBrick, int wBrick, int yBrick, int hBrick) {
        int distX = Math.abs(ball.getPosX() + ball.getWidth() / 2 - (xBrick + wBrick / 2));
        int distY = Math.abs(ball.getPosY() + ball.getWidth() / 2 - (yBrick + hBrick / 2));

        if (distX > (wBrick / 2 + ball.getWidth() / 2)) {
            return false;
        }
        if (distY > (hBrick / 2 + ball.getWidth() / 2)) {
            return false;
        }

        if (distX <= (wBrick / 2)) {
            return true;
        }
        if (distY <= (hBrick / 2)) {
            return true;
        }

        int dx = distX - wBrick / 2;
        int dy = distY - hBrick / 2;
        return ((dx * dx + dy * dy) <= ((ball.getWidth() / 2) * (ball.getWidth() / 2)));
    }

    public void BallBrickCollider() {
        for (int i = 0; i < game.getObjectsManager().getObjects().size(); ++i) {
            GameObject obj = game.getObjectsManager().getObjects().get(i);
            if (obj.getTag().equals("brick")) {
                if (RectCircleColliding(obj.getPosX(), obj.getWidth(), obj.getPosY(), obj.getHeight())) {
                    if ((ball.getPosX() + ball.getWidth() -3 <= obj.getPosX()) || (ball.getPosX() +3 >= obj.getPosX() + obj.getWidth())) {
                        boolean left = ball.getPosX() < obj.getPosX();
                        boolean right = ball.getPosX() + ball.getWidth() > obj.getPosX() + obj.getWidth();
                        boolean reversed=false;
                        if(hits[0]!=left){
                                System.out.println("XXXXXX");
                            ((Brick) obj).decrementHitsRemained();
                                ((Ball) ball).reverseVelX();
                                if(left) {
                                    hits[0] = true;
                                    hits[1]=false;
                                }
                                if(right) {
                                    hits[1] = true;
                                    hits[0]=false;
                                }
                                //reversed=true;
                        }
                        if(hits[1]!=right && reversed==false){
                            System.out.println("XXXXXX");
                            ((Brick) obj).decrementHitsRemained();
                            ((Ball) ball).reverseVelX();
                            if(left) {
                                hits[0] = true;
                                hits[1]=false;
                            }
                            if(right) {
                                hits[1] = true;
                                hits[0]=false;
                            }
                        }
                        hits[2]=false;
                        hits[3]=false;
                    }else {
                        boolean up=ball.getPosY()<obj.getPosY();
                        boolean down=ball.getPosY()+ball.getHeight()>obj.getPosY()+obj.getHeight();
                        boolean reversed=false;
                        if(hits[2]!=up){
                            System.out.println("YYYYYYY");
                            ((Brick) obj).decrementHitsRemained();
                            ((Ball) ball).reverseVelY();
                            if(up) {
                                hits[2] = true;
                                hits[3]=false;
                            }
                            if(down) {
                                hits[3] = true;
                                hits[2]=false;
                            }
                            reversed=true;
                        }
                        if(hits[3]!=down && reversed==false){
                            System.out.println("YYYYYYY");
                            ((Ball) ball).reverseVelY();
                            ((Brick) obj).decrementHitsRemained();
                            if(up) {
                                hits[2] = true;
                                hits[3]=false;
                            }
                            if(down) {
                                hits[3] = true;
                                hits[2]=false;
                            }
                        }
                        hits[1]=false;
                        hits[0]=false;
                    }
                    //((Brick) obj).decrementHitsRemained();
                    game.getSoundsLoader().playBrickHitSound();
                    break;
                }
            }
            resetHits();
        }
    }

    public void resetHits()
    {
        for (int i=0;i<hits.length;++i)
        {
            hits[i]=false;
        }
    }

    public void MagicPlayerCollider() {
        for (int i = 0; i < game.getObjectsManager().getObjects().size(); ++i) {
            GameObject obj = game.getObjectsManager().getObjects().get(i);
            if (obj.getTag().equals("magic")) {
                Rectangle magicRectangle = new Rectangle(obj.getPosX(), obj.getPosY(), obj.getWidth(), obj.getHeight());
                Rectangle playerRectangle = new Rectangle(player.getPosX(), player.getPosY() + 7, player.getWidth(), 1);
                if (magicRectangle.intersects(playerRectangle)) {
                    ((MagicObject)obj).execute();
                    obj.setDead(true);
                }
            }
        }
    }
}
