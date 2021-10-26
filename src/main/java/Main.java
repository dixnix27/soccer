import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<Player> teamA = new ArrayList<>();
    public static List<Player> teamAReserve = new ArrayList<>();
    public static List<Player> teamB = new ArrayList<>();
    public static List<Player> teamBReserve = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Init teams...");
        SoccerField soccerField = new SoccerField();

// POPULAM ECHIPA A CU 11 JUCATORI
        for(int i=1;i<=11;i++){
            Player player = new Player("TeamA", true, "Player" + i, soccerField);
            teamA.add(player);
        }

// POPULAM ECHIPA A DE REZERVA CU 5 JUCATORI
        for(int i=1;i<=5;i++){
            Player player = new Player("TeamA-Reserve", false, "Player" + i, soccerField);
            teamAReserve.add(player);
        }

// POPULAM ECHIPA B CU 11 JUCATORI
        for(int i=1;i<=11;i++){
            Player player = new Player("TeamB", true, "Player" + i, soccerField);
            teamB.add(player);
        }

// POPULAM ECHIPA B DE REZERVA CU 5 JUCATORI
        for(int i=1;i<=5;i++){
            Player player = new Player("TeamB-Reserve", false, "Player" + i, soccerField);
            teamBReserve.add(player);
        }
// UN THREAD CE VA ASTEPTA SA INTRODUCEM S PENTRU A STOPA JOCUL si C PENTRU A CONTINUA
        new Thread(() -> {
            while (true) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String name = null;
                try {
                    name = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (name != null && name.equalsIgnoreCase("s")) {
                    System.out.println("Pause game");
                    soccerField.setGameActive(false);

// ALEGEREA ECHIPEI
                    System.out.println("\nALEGE ECHIPA: 1 SAU 2");
                    Scanner sc = new Scanner(System.in);
                    int a = sc.nextInt();

// SCHIMB DE JUCATORI PENTRU ECHIPA A
                    if (a == 1) {
// AFISAREA MEMBRILOR DIN ECHIPA A
                        for (int i = 0; i < teamA.size() ; i++) {
                            System.out.println("NUMBER ["+(i+1)+"] => "+teamA.get(i));
                        }
                        System.out.println("Alege jucatorul pe care il schimbam din [ NR.]:");
                        int id = sc.nextInt();

// DESCALIFICAREA JUCATORULUI SI SETAREA PLAYING - FALSE
                        if (id != 0) {
                            // JUCATORUL ALES VA FI SCOS DIN ECHIPA
                            teamA.get(id-1).setStartingPlayer(false);
                            teamA.remove(id-1);
                        }
// AFISAREA MEMBRILOR DIN ECHIPA A DE REZERVA
                        for (int i = 0; i < teamAReserve.size() ; i++) {
                            System.out.println("NUMBER ["+(i+1)+"] => "+teamAReserve.get(i));
                        }
                        System.out.println("Alege jucatorul nou din echipa de rezerva din [ NR.]:");
                        int idRez = sc.nextInt();
                        if (idRez != 0) {

// SCOATEREA LA JOC MEMBRULUI ALES DIN ECHIPA DE REZERVA
                            teamA.add(teamAReserve.get(idRez-1));
                            teamAReserve.get(idRez-1).setStartingPlayer(true);
                            teamAReserve.remove(idRez-1);

// AFISAM LISTEI NOI CU JUCATORI
                            System.out.println("Lista noua cu jucatori din Echipa A");
                            teamA.forEach(System.out::println);
                        }
                    }
// SCHIMB DE JUCATORI PENTRU ECHIPA B
                    else if (a == 2) {
// AFISAREA MEMBRILOR DIN ECHIPA B
                        for (int i = 0; i < teamB.size() ; i++) {
                            System.out.println("NUMBER ["+(i+1)+"] => "+teamB.get(i));
                        }
                        System.out.println("Alege jucatorul pe care il schimbam din [ NR.]:");
                        int id = sc.nextInt();
                        if (id != 0) {
// DESCALIFICAREA JUCATORULUI SI SETAREA PLAYING - FALSE
                            teamB.get(id-1).setStartingPlayer(false);
                            teamB.remove(id - 1);
                        }
// AFISAREA MEMBRILOR DIN ECHIPA B DE REZERVA
                        for (int i = 0; i < teamBReserve.size() ; i++) {
                            System.out.println("NUMBER ["+(i+1)+"] => "+teamBReserve.get(i));
                        }
                        System.out.println("Alege jucatorul nou din echipa de rezerva din [ NR.]:");
                        int idRez = sc.nextInt();
                        if (idRez != 0) {

// SCOATEREA LA JOC MEMBRULUI ALES DIN ECHIPA DE REZERVA
                            teamB.add(teamBReserve.get(idRez-1));
                            teamBReserve.get(idRez-1).setStartingPlayer(true);
                            teamBReserve.remove(idRez-1);
                            // AFISAM NOUA LISTA DE JUCATORI
                            System.out.println("Lista noua cu jucatori din Echipa B");
                            teamB.forEach(System.out::println);
                             }
                        }else{
                        System.out.println("ECHIPA INCORECTA");
                    }
// ASTEPTAM C PENTRU A CONTINUA
                        Scanner scc = new Scanner(System.in);
                        System.out.println("Press C to continue");
                        String cont = scc.nextLine();
                        if (cont != null && cont.equalsIgnoreCase("c")) {
                            System.out.println("start game");
                            try {
                                soccerField.reload();

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        }).start();


// THREADURILE CE VOR PORNI TOATE ECHIPELE
        new Thread(() -> {
            teamA.forEach(player -> {
                Thread thread = new Thread(player);
                thread.setName(player.getName());
                thread.start();
            });
            teamB.forEach(player -> {
                Thread thread = new Thread(player);
                thread.setName(player.getName());
                thread.start();
            });
            teamBReserve.forEach(player -> {
                Thread thread = new Thread(player);
                thread.setName(player.getName());
                thread.start();
            });
            teamAReserve.forEach(player -> {
                Thread thread = new Thread(player);
                thread.setName(player.getName());
                thread.start();
            });
        }).start();


        new Thread(() -> {
            try {
                soccerField.play();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}




