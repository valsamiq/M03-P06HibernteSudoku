package hibernateSudoku;

import model.History;
import model.Sudoku;
import model.User;
import exceptions.SadoError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import model.UserRank;
import persistence.HibernateDAO;

public class HibernateSudo {
    
    public static void main(String[] args) throws InterruptedException {

        HibernateDAO hibernateDAO = new HibernateDAO();
        //Declaration area:
        Sudoku sudo1 = new Sudoku(4, "Medium", 
                "27....4.6.687...915..61..2....8.791...7.6.2...821.4....5..83..214...685.8.3....69", 
                "271395486368742591594618327635827914417569238982134675756983142149276853823451769"
        );
        User user1 = new User("Goering", "Alfred", "hailhidra");
        User user2 = new User("Rommel", "Zorro", "hailhidra");
        History play1 = new History(sudo1, user1, 666);
        //Insert Sudoku:
        try {
            System.out.print("Trying to insert Sudoku -> ");
            hibernateDAO.insertSudo(sudo1);
            System.out.print(sudo1.getDescription());
            System.out.println("Insertion Successfull.");
            System.out.println("-----------------------------");
            waitForIt();
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }
        //Get Sudoku:
        int intTmp = 1;
        System.out.println("Trying to get Sudoku by Id -> "+intTmp);
        System.out.println(hibernateDAO.getSudoById(intTmp));
        System.out.println("-----------------------------");
        waitForIt();
        System.out.println("Trying to get all Sudokus -> ");
        List<Sudoku> sudokus = hibernateDAO.getAllSudo();
        for (Sudoku i : sudokus) {
            System.out.println(i);
        }
        System.out.println("-----------------------------");
        waitForIt();
        //Insert Users:
        try {
            System.out.print("Trying to insert User -> ");
            hibernateDAO.insertUser(user1);
            System.out.print(user1.getName());
            System.out.println(" Insertion Successfull.");
            System.out.println("-----------------------------");
            waitForIt();
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }try {
            System.out.print("Trying to insert User -> ");
            hibernateDAO.insertUser(user2);
            System.out.print(user2.getName());
            System.out.println(" Insertion Successfull.");
            System.out.println("-----------------------------");
            waitForIt();
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }
        //Check Login:
        try {
            System.out.print("Checking User's log-in -> ");
            System.out.print(hibernateDAO.checkUser(user1));
            System.out.println(" Check Success.");
            System.out.println("-----------------------------");
            waitForIt();
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }
        //Modify User's Surname
        try {
            System.out.print("Modify user's name from -> ");
            String name = "Edward";
            System.out.println(user1.getName()+" to -> "+name);
            hibernateDAO.modifyUserName(user1, name);
            System.out.println("The user's name has been modified.");
            System.out.println("-----------------------------");
            waitForIt();
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.print("Modify user's password to -> ");
            String pass = "admin";
            System.out.println(pass);
            hibernateDAO.changeUserPass(user1, pass);
            System.out.println("The password has been modified");
            System.out.println("-----------------------------");
            waitForIt();
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.print("Trying to delete User -> ");
            System.out.println(user2.getName());
            hibernateDAO.deleteUser(user2);
            System.out.println("User successfully deleted.");
            System.out.println("-----------------------------");
            waitForIt();
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.print("Insert finished play ->");
            System.out.println(play1);
            hibernateDAO.insertToHistory(play1);
            System.out.println("Insertion success. -> ");
            System.out.println("-----------------------------");
            waitForIt();
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("***Calcular el tiempo medio de un usuario***");
            Double num = (hibernateDAO.calcularTiempoMedio(user1));
            System.out.println(num);
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println("***Obtener Sudoku aleatoriamente que no ha jugado todavia**");
            System.out.println(hibernateDAO.getRandomSudokuNoJugado(user1));
        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }

        System.out.println("***Ranking de los usuarios");
        try {
            Map<String, UserRank> rankingUser = hibernateDAO.getRankingUsuarios();
            Iterator it = rankingUser.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                System.out.println(pair.getValue());
            }

            /* Obtener la posición dentro del ranking para un usuario. */
            System.out.println();
            System.out.println("******** Mostrando la posicion dentro de ranking ********");
            String userRank = "Rusty";
            int posicion = new ArrayList<String>(rankingUser.keySet()).indexOf(userRank);
            System.out.println("PosicionRanking = " + posicion + " " + userRank);

        } catch (SadoError e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Cerrando sesión...");
        hibernateDAO.desconectar();
        System.out.println("Sesión cerrada.");
    }
    private static void waitForIt() throws InterruptedException {
        int seconds = 1;
        int miliseconds = seconds * 1000;
        Thread.sleep(miliseconds);
    }
}
