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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Session session;
    Transaction ts;
    public HibernateDAO() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    public void insertarSudoku(Sudoku s)throws SadoError{
        ts = session.beginTransaction();
        session.save(s);
        ts.commit();
    }


    public Sudoku getSudokuByid(int idsudoku){
        return (Sudoku) session.get(Sudoku.class, idsudoku);
    }

    public List<Sudoku> getAllSudoku(){
       Query query = session.createQuery("select s from Sudoku s");
       return query.list();
    }
    
    public void insertarUsuario(User usu) throws SadoError{
            User aux = getUserByUsername(usu.getUsername());
            if(aux != null){
                throw new SadoError("Ya existe ese usuario");
            }
            ts = session.beginTransaction();
            session.save(usu);
            ts.commit();
    }


    
    public boolean validarUsuario(User u)throws SadoError{
        Query query = session.createQuery("select u from User u where u.username = :username and u.password = :password");
        query.setParameter("username", u.getUsername());
        query.setParameter("password", u.getPassword());
        if(query.uniqueResult() != null){
            return true;
        }
        throw new SadoError("Usuario o contraseña incorrecto");
    }


    public void modifcarPerfilUsuario(User u, String newPerfil)throws SadoError{
        User user = getUserByUsername(u.getUsername());
        if(user == null){
            throw new SadoError("No existe el usuario");
        }

        ts = session.beginTransaction();
        user.setName(newPerfil);
        session.update(user);
        ts.commit();
    }

    public void cambiarContraseñaUsuario(User usu, String newPassword)throws SadoError{
        User usuario = getUserByUsername(usu.getUsername());
        if(usuario == null){
            throw new SadoError("No existe el usuario");
        }
        ts = session.beginTransaction();
        usuario.setPassword(newPassword);
        session.update(usuario);
        ts.commit();
    }

    public void eliminarUsuario(User u) throws SadoError{
        User usu = getUserByUsername(u.getUsername());
        if(usu == null){
            throw new SadoError("No existe el usuario que quieres borrar");
        }
        ts = session.beginTransaction();
        session.delete(usu);
        ts.commit();

    }
    
    public void insertarHistorial(History h) throws SadoError{
        ts = session.beginTransaction();
        session.save(h);
        ts.commit();
    }
    

    public Double calcularTiempoMedio(User usu) throws SadoError{
        Query q = session.createQuery("select avg(time) from History h where h.user.username =  :username");
        q.setParameter("username",usu.getUsername());
        return (Double) q.uniqueResult();
    }
    
    public Sudoku getRandomSudokuNoJugado(User u) throws SadoError{
        User aux = getUserByUsername(u.getUsername());
        if(aux == null){
            throw new SadoError("No existe el usuario");
        }
        Query q = session.createQuery("select s from Sudoku s where s not in (select h.sudoku from History h "
                + "where h.user.username ='" + u.getUsername() +"')  ORDER BY RAND()").setMaxResults(1);
        return (Sudoku) q.uniqueResult();
    }
    
    
       public Map<String,UserRank> getRankingUsuarios() throws SadoError{
           Query query = session.createQuery("select avg(h.time),h.user.username from History h group by h.user.username order by avg(h.time)");
          
           Map<String, UserRank> ranking = new LinkedHashMap<>();
           List<Object[]> rows = query.list();
            for(Object[] row : rows){
			 ranking.put(row[1].toString(), new UserRank(
					 Double.parseDouble(row[0].toString()),
					 row[1].toString()
					 ));
			 
		 }
            if(query.list().isEmpty()){
                throw new SadoError("No se puede hacer ranking: porque no hay registrados en historial");
            }
            return ranking;
        
       }
       
       
      

//    *******************auxiliares*******************

    public User getUserByUsername(String username){
        return (User) session.get(User.class, username);
    }
    


    public void desconectar() {
        session.close();
        HibernateUtil.close();
    }

}
