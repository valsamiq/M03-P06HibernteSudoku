package persistence;

import model.History;
import model.Sudoku;
import model.User;
import exceptions.SadoError;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.UserRank;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateDAO {

    public static HibernateDAO getInstance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Session session;
    Transaction ts;

    public HibernateDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void insertSudo(Sudoku s) throws SadoError {
        if(checkIfExists(s.getSolution())){
            ts = session.beginTransaction();
            session.save(s);
            ts.commit();
        }else{
            throw new SadoError("Sudoku already on the DB.");
        }
    }
    public boolean checkIfExists(String s){
        List<Sudoku> sudokus = getAllSudo();
        for (Sudoku i : sudokus) {
            if(s.equals(i.getSolution())){
                return true;
            }
        }
            return false;
    }

    public Sudoku getSudoById(int id) {
        return (Sudoku) session.get(Sudoku.class, id);
    }

    public List<Sudoku> getAllSudo() {
        Query query = session.createQuery("select s from Sudoku s");
        return query.list();
    }

    public void insertUser(User usu) throws SadoError {
        User tmp = getUserByUsername(usu.getUsername());
        if (tmp != null) {
            throw new SadoError("This user already exists.");
        }
        ts = session.beginTransaction();
        session.save(usu);
        ts.commit();
    }

    public boolean checkUser(User u) throws SadoError {
        Query query = session.createQuery("select u from User u where u.username = :username and u.password = :password");
        query.setParameter("username", u.getUsername());
        query.setParameter("password", u.getPassword());
        if (query.uniqueResult() != null) {
            return true;
        }
        throw new SadoError("Wrong User or Password");
    }

    public void modifyUserName(User u, String name) throws SadoError {
        User user = getUserByUsername(u.getUsername());
        if (user == null) {
            throw new SadoError("The user does not exist.");
        }
        ts = session.beginTransaction();
        user.setName(name);
        session.update(user);
        ts.commit();
    }

    public void changeUserPass(User usu, String newPassword) throws SadoError {
        User usuario = getUserByUsername(usu.getUsername());
        if (usuario == null) {
            throw new SadoError("The user does not Exist.");
        }
        ts = session.beginTransaction();
        usuario.setPassword(newPassword);
        session.update(usuario);
        ts.commit();
    }

    public void deleteUser(User u) throws SadoError {
        User usu = getUserByUsername(u.getUsername());
        if (usu == null) {
            throw new SadoError("Can't delete: User does not Exist.");
        }
        ts = session.beginTransaction();
        session.delete(usu);
        ts.commit();
    }

    public void insertToHistory(History h) throws SadoError {
        ts = session.beginTransaction();
        session.save(h);
        ts.commit();
    }

    public Double calcularTiempoMedio(User usu) throws SadoError {
        Query q = session.createQuery("select avg(time) from History h where h.user.username =  :username");
        q.setParameter("username", usu.getUsername());
        return (Double) q.uniqueResult();
    }

    public Sudoku getRandomSudokuNoJugado(User u) throws SadoError {
        User aux = getUserByUsername(u.getUsername());
        if (aux == null) {
            throw new SadoError("User does not Exist.");
        }
        Query q = session.createQuery("select s from Sudoku s where s not in (select h.sudoku from History h "
                + "where h.user.username ='" + u.getUsername() + "')  ORDER BY RAND()").setMaxResults(1);
        return (Sudoku) q.uniqueResult();
    }
    
    public Map<String, UserRank> getRankingUsuarios() throws SadoError {
        Query query = session.createQuery("select avg(h.time),h.user.username from History h group by h.user.username order by avg(h.time)");

        Map<String, UserRank> ranking = new LinkedHashMap<>();
        List<Object[]> rows = query.list();
        for (Object[] row : rows) {
            ranking.put(row[1].toString(), new UserRank(
                    Double.parseDouble(row[0].toString()),
                    row[1].toString()
            ));
        }
        if (query.list().isEmpty()) {
            throw new SadoError("Can't show: Ranking Empty.");
        }
        return ranking;
    }
//    *******************auxiliares*******************
    public User getUserByUsername(String username) {
        return (User) session.get(User.class, username);
    }
    public void desconectar() {
        session.close();
        HibernateUtil.close();
    }
}
