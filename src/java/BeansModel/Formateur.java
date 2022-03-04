/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BeansModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author khali
 */
@ManagedBean
@RequestScoped
public final class Formateur {
    private int id;
    private String nom,prenom,email,mdp,grade,tel;
    private int age,recrutement;
    Connection connection;
    ArrayList<Formateur> listFormateurs;

    public int getId() {
        return id;
    }

   

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getRecrutement() {
        return recrutement;
    }

    public void setRecrutement(int recrutement) {
        this.recrutement = recrutement;
    }
    
      public Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");   //initialiser
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/formation","root",""); //chaine(drive=jdbc:mysql, url="localhost:3306/base,username=root,password="")
            System.out.println("connecté");
        }catch(Exception e){
            System.out.println(e);
        }
        return connection;
    }
      public List<Formateur> showList()
      {
          try
          {
               listFormateurs=new ArrayList<Formateur>();
          //connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from formateur"); 
            while(rs.next()) //tant que rs à des valeurs
            {
                Formateur f= new Formateur();
                f.setAge(rs.getInt("age"));
                f.setEmail(rs.getString("email"));
                f.setGrade(rs.getString("grade"));
                f.setId(rs.getInt("id"));
                f.setMdp(rs.getString("mdp"));
                f.setNom(rs.getString("nom"));
                f.setPrenom(rs.getString("prenom"));
                f.setRecrutement(rs.getInt("recrutement"));
                f.setTel(rs.getString("telephone"));
                listFormateurs.add(f);
                
            }
      
                  connection.close();        

      
          }
          catch(Exception ex)
          {
          }
                  //Formateur f2=new Formateur("nom2", "prenom2", "email2", "mdp2", "grade2", "tel2", 30, 2020);

         // listFormateurs.add(f2);
      
      return listFormateurs;
      }
      
      
      
      
      
      
    public String ajouterFormateur()
    {
                    connection = getConnection();

     int result = 0;
        try{
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO `formateur`( `nom`, `prenom`, `mdp`, `age`, `recrutement`, `grade`, `telephone`, `email`) VALUES(?,?,?,?,?,?,?,?)");
            stmt.setString(1, this.nom);
            stmt.setString(2, this.prenom);
            stmt.setString(3, this.mdp);
            stmt.setInt(4, this.age);
            stmt.setInt(5,this.recrutement);
            stmt.setString(6,this.grade);
            stmt.setString(7,this.tel);
            stmt.setString(8, this.email);
            result = stmt.executeUpdate();
                        connection.close(); //optionelle

            System.out.println("ajouté!");
            
         } catch(Exception e){
            System.out.println(e);
        }
             if(result !=0)
            return "formateurs.xhtml?faces-redirect=true";
        else return "index.xhtml?faces-redirect=true";
    
    }
    public void deleteFormateur(int id)
    {
    try
    {
    connection = getConnection();  
            PreparedStatement stmt = connection.prepareStatement("delete from formateur where id = "+id);  
            stmt.executeUpdate();  
    }
    catch(Exception ex)
    {
        System.out.println(ex);
    
    }
    
    
    }

    public Formateur() {
       // showList();
    }

    public Formateur(String nom, String prenom, String email, String mdp, String grade, String tel, int age, int recrutement) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.grade = grade;
        this.tel = tel;
        this.age = age;
        this.recrutement = recrutement;
    }

    public Formateur(int id, String nom, String prenom, String email, String mdp, String grade, String tel, int age, int recrutement, Connection connection) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.grade = grade;
        this.tel = tel;
        this.age = age;
        this.recrutement = recrutement;
        this.connection = connection;
    }
    
    
    
    
    
}
