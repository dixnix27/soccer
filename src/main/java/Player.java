import java.io.IOException;

public class Player implements Runnable {
    private String name;
    private boolean startingPlayer;
    private String team;
    private SoccerField soccerField;

    public Player(String team, boolean startingPlayer, String name, SoccerField soccerField) {
        this.team = team;
        this.startingPlayer = startingPlayer;
        this.name = name;
        this.soccerField = soccerField;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public boolean isStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(boolean startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    int time=0;

    @Override
    public void run() {
        // JOACA DUREAZA 90 DE SECUNDE
        while (time<90) {
            try {
                if (soccerField.getGameActive() && this.startingPlayer) {
                    System.out.println(this);
                }
                soccerField.play();

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            time++;
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}

