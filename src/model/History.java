package model;
// Generated 02-mar-2018 9:45:52 by Hibernate Tools 4.3.1



/**
 * History generated by hbm2java
 */
public class History  implements java.io.Serializable {


     private Integer idhistory;
     private Sudoku sudoku;
     private User user;
     private Integer time;

    public History() {
    }

    public History(Sudoku sudoku, User user, Integer time) {
       this.sudoku = sudoku;
       this.user = user;
       this.time = time;
    }
   
    public Integer getIdhistory() {
        return this.idhistory;
    }
    
    public void setIdhistory(Integer idhistory) {
        this.idhistory = idhistory;
    }
    public Sudoku getSudoku() {
        return this.sudoku;
    }
    
    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Integer getTime() {
        return this.time;
    }
    
    public void setTime(Integer time) {
        this.time = time;
    }




}

