package GameEngine;

import GameEngine.GameObjects.*;
import GameEngine.States.PlayState.PlayingState;

import java.awt.Rectangle;

public class Collider {
    private PlayingState playingState;
    private boolean playerBallDetectionX = false;
    private boolean playerBallDetectionY = false;
    public Collider(PlayingState playingState) {
        this.playingState = playingState;
    }
    public void update() {
        BallPaddleCollider2();
        BallBrickCollider();
        MagicPlayerCollider();
    }
    public void BallPaddleCollider2() {
        GameObject player=playingState.getObjectsManager().getPlayer();
        GameObject ball=playingState.getObjectsManager().getBall();
        Rectangle ballRectangle = new Rectangle(ball.getPosX(), ball.getPosY(), ball.getWidth(), ball.getHeight());
        Rectangle playerRectangle = new Rectangle(player.getPosX(), player.getPosY()+1, player.getWidth(), 1);
        if (ballRectangle.intersects(playerRectangle)) {
            if (!playerBallDetectionY && (ball.getPosX() + ball.getWidth() > playerRectangle.x -2) && (ball.getPosX() < (playerRectangle.x + playerRectangle.width) + 2)) {
                ((Ball) ball).reverseVelY();
                int val=ball.getPosX()+ball.getWidth()/2;//determinam pozitia in care se afla jumatatea mingii
                val=val-(player.getPosX()+player.getWidth()/2);//comparativ cu jumatatea player-ului
                val=val/(player.getWidth()/7);//de completat comentariul
                if(val<0)
                    ((Ball) ball).decreaseVelX(val);
                else
                    ((Ball) ball).increaseVelX(val);
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
    public void BallPaddleCollider() {
        GameObject ball = playingState.getObjectsManager().getBall();
        GameObject player = playingState.getObjectsManager().getPlayer();
        if (RectCircleColliding(player.getPosX(), player.getWidth(), player.getPosY()+1, player.getHeight())) {
            System.out.println("Hit");
            if ((ball.getPosY() + (ball.getHeight() * 2) / 3 > player.getPosY())) {
                ((Ball) ball).reverseVelX();
            } else {
                ((Ball) ball).reverseVelY();
                    int val=ball.getPosX()+ball.getWidth()/2;//determinam pozitia in care se afla jumatatea mingii
                    val=val-(player.getPosX()+player.getWidth()/2);//comparativ cu jumatatea player-ului
                    val=val/(player.getWidth()/7);//de completat comentariul
                    if(val<0)
                        ((Ball) ball).decreaseVelX(val);
                    else
                        ((Ball) ball).increaseVelX(val);
            }
        }
    }

    public boolean RectCircleColliding(int xBrick, int wBrick, int yBrick, int hBrick) {
        GameObject ball=playingState.getObjectsManager().getBall();
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
        Ball ball=(Ball)playingState.getObjectsManager().getBall();
        for (int i = 0; i < playingState.getObjectsManager().getObjects().size(); ++i) {
            GameObject obj = playingState.getObjectsManager().getObjects().get(i);
            if (obj.getTag().equals("brick")) {//it ball //br brick
                float brickx = obj.getPosX();
                float bricky = obj.getPosY();

                // Center of the ball x and y coordinates
                float ballcenterx = ball.getPosX()+ball.getWidth()/2;
                float ballcentery = ball.getPosY()+ball.getHeight()/2;

                // Center of the brick x and y coordinates
                float brickcenterx = obj.getPosX()+obj.getWidth()/2;
                float brickcentery = obj.getPosY()+obj.getHeight()/2;

                if (ball.getPosX() <= obj.getPosX() + obj.getWidth() &&
                        ball.getPosX()+ball.getWidth() >= brickx &&
                                ball.getPosY() <= bricky + obj.getHeight() &&
                                        ball.getPosY() + ball.getHeight() >= bricky) {
                    // Collision detected, remove the brick
                    ((Brick)obj).decrementHitsRemained();

                    // Asume the ball goes slow enough to not skip through the bricks

                    // Calculate ysize
                    float ymin = 0;
                    if (bricky > ball.getPosY()) {
                        ymin = bricky;
                    } else {
                        ymin = ball.getPosY();
                    }

                    float ymax = 0;
                    if (bricky + obj.getHeight() < ball.getPosY() + ball.getHeight()) {
                        ymax = bricky + obj.getHeight();
                    } else {
                        ymax = ball.getPosY() + ball.getHeight();
                    }

                    float ysize = ymax - ymin;

                    // Calculate xsize
                    float xmin = 0;
                    if (brickx > ball.getPosX()) {
                        xmin = brickx;
                    } else {
                        xmin = ball.getPosX();
                    }

                    float xmax = 0;
                    if (brickx + obj.getWidth() < ball.getPosX() + ball.getWidth()) {
                        xmax = brickx + obj.getWidth();
                    } else {
                        xmax = ball.getPosX() + ball.getWidth();
                    }

                    float xsize = xmax - xmin;
                    int response;
                    // The origin is at the top-left corner of the screen!
                    // Set collision response
                    if (xsize > ysize) {
                        if (ballcentery > brickcentery) {
                            BallBrickResponse(3);
                        } else {
                            // Top
                            BallBrickResponse(1);
                        }
                    } else {
                        if (ballcenterx < brickcenterx) {
                            // Left
                            BallBrickResponse(0);
                        } else {
                            // Right
                            BallBrickResponse(2);
                        }
                    }
                }
            }
        }
    }
    public void BallBrickResponse(int dirindex) {
        // dirindex 0: Left, 1: Top, 2: Right, 3: Bottom
        Ball ball=(Ball)playingState.getObjectsManager().getBall();
        // Direction factors
        int mulx = 1;
        int muly = 1;

        if (ball.getVelX() >= 0) {
            // Ball is moving in the positive x direction
            if (ball.getVelY() > 0) {
                // Ball is moving in the positive y direction
                // +1 +1
                if (dirindex == 0 || dirindex == 3) {
                    mulx = -1;
                } else {
                    muly = -1;
                }
            } else if (ball.getVelY() < 0) {
                // Ball is moving in the negative y direction
                // +1 -1
                if (dirindex == 0 || dirindex == 1) {
                    mulx = -1;
                } else {
                    muly = -1;
                }
            }
        } else if (ball.getVelX() < 0) {
            // Ball is moving in the negative x direction
            if (ball.getVelY() > 0) {
                // Ball is moving in the positive y direction
                // -1 +1
                if (dirindex == 2 || dirindex == 3) {
                    mulx = -1;
                } else {
                    muly = -1;
                }
            } else if (ball.getVelY() < 0) {
                // Ball is moving in the negative y direction
                // -1 -1
                if (dirindex == 1 || dirindex == 2) {
                    mulx = -1;
                } else {
                    muly = -1;
                }
            }
        }

        // Set the new direction of the ball, by multiplying the old direction
        // with the determined direction factors
        if(mulx<0)
        {
            ball.reverseVelX();
        }
        if(muly<0)
        {
            ball.reverseVelY();
        }
        //ball->SetDirection(mulx*ball->dirx, muly*ball->diry);
    }
    public void MagicPlayerCollider() {
        GameObject player=playingState.getObjectsManager().getPlayer();
        for (int i = 0; i < playingState.getObjectsManager().getObjects().size(); ++i) {
            GameObject obj = playingState.getObjectsManager().getObjects().get(i);
            if (obj.getTag().equals("magic")) {
                Rectangle magicRectangle = new Rectangle(obj.getPosX(), obj.getPosY(), obj.getWidth(), obj.getHeight());
                Rectangle playerRectangle = new Rectangle(player.getPosX(), player.getPosY() , player.getWidth(), 1);
                if (magicRectangle.intersects(playerRectangle)) {
                    ((MagicObject)obj).executeCommand();
                    obj.setDead(true);
                }
            }
        }
    }
}
