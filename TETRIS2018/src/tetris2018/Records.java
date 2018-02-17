package tetris2018;

import java.io.Serializable;



public class Records implements Serializable{
int points;
String nickname;

    public Records(String nickname, int points) {
    this.nickname = nickname;
    this.points = points;    
    }

    @Override
    public String toString() {
    return nickname + ":" + "         Points "+ points + ".";   
    }
}
