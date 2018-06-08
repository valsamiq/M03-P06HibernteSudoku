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
    private static void waitForIt() throws InterruptedException {
        int seconds = 1;
        int miliseconds = seconds * 1000;
        Thread.sleep(miliseconds);
    }

    public static void main(String[] args) {

        HibernateDAO hibernateDAO = new HibernateDAO();
        Sudoku sudo1 = new Sudoku(4, "Medium", 
                "27....4.6.687...915..61..2....8.791...7.6.2...821.4....5..83..214...685.8.3....69", 
                "271395486368742591594618327635827914417569238982134675756983142"
        );
        User user1 = new User("Goering", "Alfred", "hailhidra");
        User user2 = new User("Rommel", "Zorro", "hailhidra");
        History history1 = new History(sudo1, user1, 1200);
        try {
            System.out.print("Trying to insert Sudoku -> ");
            hibernateDAO.insertarSudoku(sudo1);
            System.out.println("Insertion Successfull...");
            System.out.println("-----------------------------");
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }
        int intTmp = 1;
        System.out.println("Trying to get Sudoku by Id -> "+intTmp);
        System.out.println(hibernateDAO.getSudokuByid(intTmp));
        System.out.println("-----------------------------");
        System.out.println("Trying to get all Sudokus -> ");
        List<Sudoku> slist = hibernateDAO.getAllSudoku();
        for (Sudoku i : slist) {
            System.out.println(i);
        }
        System.out.println("-----------------------------");

        try {
            System.out.println("***Insertar un usuario***");
            hibernateDAO.insertarUsuario(user1);
            System.out.println("Se ha insertado correctamente el usuario " + user1.getName());
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("***Validar la entrada de un usuario ***");
            System.out.println(hibernateDAO.validarUsuario(user1));
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("***Modificar el perfil de un usuario existente");
            hibernateDAO.modifcarPerfilUsuario(user1, "Luis");
            System.out.println("Se ha modificado el perfil del usuario " + user1.getUsername());
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("***Cambiar la contraseña de un usuario existente");
            hibernateDAO.cambiarContraseñaUsuario(user1, "1234");
            System.out.println("Se ha modifacado correctamente la contraseña del usuario " + user1.getUsername());
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("***Eliminar un usuario");
            hibernateDAO.eliminarUsuario(user2);
            System.out.println("Se ha borrado correctamente.");
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("***Insertar una partida finalizada (Historial)");
            hibernateDAO.insertarHistorial(history1);
            System.out.println("Se ha insertado una partida finalizada(Historial)");
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("***Calcular el tiempo medio de un usuario***");
            Double num = (hibernateDAO.calcularTiempoMedio(user1));
            System.out.println(num);
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }

        try {
            System.out.println("***Obtener Sudoku aleatoriamente que no ha jugado todavia**");
            System.out.println(hibernateDAO.getRandomSudokuNoJugado(user1));
        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
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

        } catch (SadoError ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Cerrando sesión...");
        hibernateDAO.desconectar();
        System.out.println("Sesión cerrada.");
    }
}
