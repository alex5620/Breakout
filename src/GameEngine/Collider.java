package GameEngine;

import GameEngine.GameObjects.*;
import GameEngine.States.PlayState.PlayingState;

import java.awt.Rectangle;


public class Collider {
    private PlayingState playingState;
    private boolean paddleBallDetectionX = false;
    private boolean paddleBallDetectionY = false;
    public Collider(PlayingState playingState) {
        this.playingState = playingState;
    }
    public void update() {
        BallPaddleCollision();
        BallBrickCollision();
        MagicPaddleCollision();
        BallLaserCollision();
    }
    public void BallPaddleCollision() {
        Paddle paddle =playingState.getObjectsManager().getPaddle();
        Ball ball=playingState.getObjectsManager().getBall();
        Rectangle ballRectangle = new Rectangle(ball.getPosX(), ball.getPosY(), ball.getDiameter(), ball.getDiameter());
        Rectangle paddleRectangle = new Rectangle(paddle.getPosX(), paddle.getPosY()+1, paddle.getWidth(), paddle.getHeight());
        if (ballRectangle.intersects(paddleRectangle)) {
            playingState.getSoundsLoader().getSound("ballBouncing").play();
            if (!paddleBallDetectionX && (ball.getPosY()+ ball.getDiameter() > paddle.getPosY()+paddle.getHeight()/3+4))
            {
                ball.reverseVelX();
                paddleBallDetectionX = true;
            }
            else {
                if (!paddleBallDetectionY) {
                    (ball).reverseVelY();
                    int val = ball.getPosX() + ball.getDiameter() / 2;
                    val = val - (paddle.getPosX() + paddle.getWidth() / 2);
                    val = val / (paddle.getWidth() / 9);
                    ball.changeVelX(val);
                    paddleBallDetectionY = true;
                }
            }
        } else {
            paddleBallDetectionX = false;
            paddleBallDetectionY = false;
        }
    }
    public void BallBrickCollision() {
        Ball ball=playingState.getObjectsManager().getBall();
        for (int i = 0; i < playingState.getObjectsManager().getObjects().size(); ++i) {
            if (playingState.getObjectsManager().getObjects().get(i).getTag().equals("brick")) {
                Brick brick = (Brick)playingState.getObjectsManager().getObjects().get(i);
                int xBrick = brick.getPosX();
                int yBrick = brick.getPosY();
                int xBall= ball.getPosX();
                int yBall= ball.getPosY();
                if ((xBall <= (xBrick + brick.getWidth())) &&
                        ((xBall+ball.getDiameter()) >= xBrick) &&
                        (yBall <= (yBrick + brick.getHeight())) &&
                        ((yBall + ball.getDiameter()) >= yBrick)) {
                    brick.decrementHitsRemained();
                    int ySup = 0;
                    if (yBrick > yBall) {
                        ySup = yBrick;
                    } else {
                        ySup = yBall;
                    }
                    int yInf = 0;
                    if (yBrick + brick.getHeight() < yBall + ball.getDiameter()) {
                        yInf = yBrick + brick.getHeight();
                    } else {
                        yInf = yBall + ball.getDiameter();
                    }
                    int yDif = yInf - ySup;
                    int xStg = 0;
                    if (xBrick > xBall) {
                        xStg = xBrick;
                    } else {
                        xStg = xBall;
                    }
                    int xDrt = 0;
                    if (xBrick + brick.getWidth() < xBall + ball.getDiameter()) {
                        xDrt = xBrick + brick.getWidth();
                    } else {
                        xDrt = xBall + ball.getDiameter();
                    }
                    int xDif = xDrt - xStg;
                    int xBallCenter = xBall+ball.getDiameter()/2;
                    int yBallCenter = yBall+ball.getDiameter()/2;
                    int brickcenterx = xBrick+brick.getWidth()/2;
                    int brickcentery = yBrick+brick.getHeight()/2;
                    if (xDif > yDif) {
                        if (yBallCenter > brickcentery) {
                            BallBrickResponse(3);
                        }
                        else {
                            BallBrickResponse(1);
                        }
                    }
                    else {
                        if (xBallCenter < brickcenterx) {
                            BallBrickResponse(0);
                        }
                        else {
                            BallBrickResponse(2);
                        }
                    }
                }
            }
        }
    }
    public void BallBrickResponse(int direction) {
        Ball ball=playingState.getObjectsManager().getBall();
        boolean changeX = false;
        boolean changeY = false;
        if (ball.getVelX() >= 0) {
            if (ball.getVelY() > 0) {
                if (direction == 0 || direction == 3) {
                    changeX = true;
                } else {
                    changeY = true;
                }
            } else if (ball.getVelY() < 0) {
                if (direction == 0 || direction == 1) {
                    changeX=true;
                } else {
                    changeY=true;
                }
            }
        } else if (ball.getVelX() < 0) {
            if (ball.getVelY() > 0) {
                if (direction == 2 || direction == 3) {
                    changeX=true;
                } else {
                    changeY=true;
                }
            } else if (ball.getVelY() < 0) {
                if (direction == 1 || direction == 2) {
                    changeX=true;
                } else {
                    changeY=true;
                }
            }
        }
        if(changeX)
        {
            ball.reverseVelX();
        }
        if(changeY)
        {
            ball.reverseVelY();
        }
    }
    public void MagicPaddleCollision() {
        Paddle paddle =playingState.getObjectsManager().getPaddle();
        for (int i = 0; i < playingState.getObjectsManager().getObjects().size(); ++i) {
            if (playingState.getObjectsManager().getObjects().get(i).getTag().equals("magic")) {
                MagicObject obj = (MagicObject) playingState.getObjectsManager().getObjects().get(i);
                Rectangle magicRectangle = new Rectangle(obj.getPosX(), obj.getPosY(), obj.getWidth(), obj.getHeight());
                Rectangle paddleRectangle = new Rectangle(paddle.getPosX(), paddle.getPosY() , paddle.getWidth(), paddle.getHeight());
                if (magicRectangle.intersects(paddleRectangle)) {
                    playingState.getSoundsLoader().getSound("bonusCollected").play();
                    obj.executeCommand();
                    obj.setDead(true);
                }
            }
        }
    }
    public void BallLaserCollision() {
        Laser laser=playingState.getObjectsManager().getLaser();
        if(laser==null)
        {
            return;
        }
        Ball ball=playingState.getObjectsManager().getBall();
        Rectangle ballRectangle = new Rectangle(ball.getPosX(), ball.getPosY(), ball.getDiameter(), ball.getDiameter());
        Rectangle laserRectangle = new Rectangle(laser.getPosX(), laser.getPosY(), laser.getWidth(), laser.getHeight());
        if (ballRectangle.intersects(laserRectangle)) {
            playingState.getSoundsLoader().getSound("laserDestroyed").play();
            ball.reverseVelY();
            laser.decrementHits();
        }
    }
}
