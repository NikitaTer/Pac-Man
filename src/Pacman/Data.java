package Pacman;

public class Data {
    private String nickname;
    private long score;

    public Data(String nickname, long score) {
        this.nickname = nickname;
        this.score = score;
    }

    public Data(Data data) {
        this.nickname = data.nickname;
        this.score = data.score;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getScore() {
        return score;
    }
}
