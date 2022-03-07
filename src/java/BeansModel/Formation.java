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
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author khali
 */
@ManagedBean
@RequestScoped
public class Formation {
   private int id;
   private String description;
   private double prix;
   private String titre;
       private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();


    public Formation(String description, double prix, String titre) {
        this.description = description;
        this.prix = prix;
        this.titre = titre;
    }

    public Formation(int id, String description, double prix, String titre, Connection connection) {
        this.id = id;
        this.description = description;
        this.prix = prix;
        this.titre = titre;
        this.connection = connection;
    }

   Connection connection;
    
    ArrayList<Formation> listFormations;

    public Formation() {
    //    showList();
    }
   
   
   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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
      public List<Formation> showList()
      {
          try
          {
            listFormations=new ArrayList<>();
//          connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from formation"); 
            while(rs.next()) //tant que rs à des valeurs
            {
                Formation fo= new Formation();
                fo.setId(rs.getInt("id"));
                fo.setTitre(rs.getString("titre"));
                fo.setDescription(rs.getString("description"));

                fo.setPrix(rs.getDouble("prix"));
                
                listFormations.add(fo);
                
            }
               
                                  connection.close();        

      
          }
          catch(Exception ex)
          {
          }
                  //Formateur f2=new Formateur("nom2", "prenom2", "email2", "mdp2", "grade2", "tel2", 30, 2020);

         // listFormateurs.add(f2);
      
      return listFormations;
      }
      
      
      
      
      
      
    public String ajouterFormation()
    {
                            connection = getConnection();

     int result = 0;
        try{
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO `formation`(`titre`, `description`, `prix`) VALUES(?,?,?)");
            
            stmt.setString(1, this.titre);
            stmt.setString(2, this.description);
            stmt.setDouble(3, this.prix);
            
            result = stmt.executeUpdate();
            System.out.println("ajouté!");
            connection.close(); //optionelle
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "formations.xhtml?faces-redirect=true";
        else return "index.xhtml?faces-redirect=true";
    
    }
    
        // Used to fetch record to update

    public String modifFormation(int id){
        Formation form = null;
        System.out.println(id);
        try{
            connection = getConnection();
            Statement stmt=getConnection().createStatement();  
            ResultSet rs=stmt.executeQuery("select * from formation where id = "+(id));
            rs.next();
            form = new Formation();
            form.setId(rs.getInt("id"));
            form.setTitre(rs.getString("titre"));
            form.setDescription(rs.getString("description"));
            form.setPrix(rs.getDouble("prix"));
            
            sessionMap.put("editFormation", form);
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }       
        return "editFormation.xhtml?faces-redirect=true";
    }
    
    // Used to update user record
    public String updateFormation(Formation f){
        //int result = 0;
        try{
            connection = getConnection();  
            PreparedStatement stmt=connection.prepareStatement("update formation set titre=?,description=?,prix=? where id=?");  
            stmt.setString(1,f.getTitre());  
            stmt.setString(2,f.getDescription());  
            stmt.setDouble(3,f.getPrix());  
            stmt.setInt(4,f.getId());  
            
            stmt.executeUpdate();
            connection.close();
        }catch(Exception e){
            System.out.println();
        }
        return "formations.xhtml?faces-redirect=true";      
    }
        // Used to delete user record

    public void deleteFormation(int id)
    {
    try
    {
    connection = getConnection();  
            PreparedStatement stmt = connection.prepareStatement("delete from formation where id = "+id);  
            stmt.executeUpdate();  
    }
    catch(Exception ex)
    {
        System.out.println(ex);
    
    }
    
    
    }
}
