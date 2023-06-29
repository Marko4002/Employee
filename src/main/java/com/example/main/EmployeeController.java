package com.example.main;

// Importovanje neophodnih paketa za rad sa MySQL bazom podataka
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EmployeeController {

// Navođenje @FXML annotation tagova, private polja i metoda kontrolera, za upotrebu u FXML fajlu.
    @FXML private TextField id;
    @FXML private TextField ename;
    @FXML private TextField dept;
    @FXML private TextField mobileNoTF;
    @FXML private TextField salary;

    @FXML private TableView<Employee> table;
    @FXML private TableColumn<Employee, Integer> eid;
    @FXML private TableColumn<Employee, String> name;
    @FXML private TableColumn<Employee, String> department;
    @FXML private TableColumn<Employee, Float> salary1;
    @FXML private TableColumn<Employee, Integer> mobileNoTC;   //* String

    @FXML private Label lavel;

// Navođenje objekata
    Employee emp;
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    // create table employee ( id number,name varchar2(15),department varchar2(15),mobileNo varchar2(10),salary number);

    public void initialize() {
        show();
    }

// CRUD operacija - C
    public void register(ActionEvent ae) throws SQLException  {

        emp = new Employee();
        emp.setName(ename.getText());
        emp.setDepartment(dept.getText());
        emp.setMobileNo(Integer.parseInt(mobileNoTF.getText()));
        emp.setSalary(Float.parseFloat(salary.getText()));



    /* *   if(emp.getMobileNo().length()<10 || emp.getMobileNo().length()>10){
            lavel.setText("please enter correct mobile No");
            return;
        }
    */
        try {
// Otvaranje konekcije sa BP
            con = DBUtil.getConnection();  // SELECT SUM (IFNULL(Sales,100)) FROM Sales_Data;
            // oracle   String sql="insert into employee values((select nvl(max(id),0)+1 from employee),?,?,?,?)";
            //String sql = "INSERT INTO employee (eid, name, department, mobileNo, salary) VALUES(SELECT IFNULL(eid, eid+1) FROM employee,?,?,?,?)";
            //String sql = "INSERT INTO employee (name, department, mobileNo, salary) VALUES(3,?,?,?,?)";
// Postavljanje MySQL upita
            String sql = "INSERT INTO employee(name, department, mobileNo, salary ) VALUES(?,?,?,?)";
// Postavljanje prepareStatement objekta za izvršavanje paremetrizovanog SQL upita
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getDepartment());
            pstmt.setInt(3, emp.getMobileNo());
            pstmt.setFloat(4, emp.getSalary());
            //rs=pstmt.executeQuery();
            pstmt.executeUpdate();

            //closeConnection(con);
        //    DBUtil.closeConnection(con);
        //    show();



        /*    if(rs.next()){
                lavel.setText("Register Sucessfully.");
                eid.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("eid"));
                name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
                department.setCellValueFactory(new PropertyValueFactory<Employee, String>("department"));
                salary1.setCellValueFactory(new PropertyValueFactory<Employee, Float>("salary1"));
                mobile.setCellValueFactory(new PropertyValueFactory<Employee, String>("mobile"));
                ObservableList<Employee> data = FXCollections.observableArrayList(
                        new Employee(101,emp.getName(),emp.getDepartment(),123,"12312323")
                );

                table.getItems().addAll(data);
            */

            } catch (SQLException e) {
                e.printStackTrace();
            }

            show();
        DBUtil.closeConnection(con);
            //    System.out.println(emp.getSalary()+"...........");
    }


    public void show() {
        try {
// Otvaranje konekcije sa BP
            con = DBUtil.getConnection();
// Postavljanje MySQL upita
            String sql = "select * from employee";
// Postavljanje prepareStatement objekta za izvršavanje SQL upita
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            ObservableList<Employee> temp = FXCollections.observableArrayList();
            table.getItems().setAll(temp);

            while(rs.next()){
                eid.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("eid"));
                name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
                department.setCellValueFactory(new PropertyValueFactory<Employee, String>("department"));
                salary1.setCellValueFactory(new PropertyValueFactory<Employee, Float >("salary"));
                mobileNoTC.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("mobileNo"));
             //   System.out.println(rs.getFloat("salary"));
                ObservableList<Employee> data = FXCollections.observableArrayList(
                        new Employee(rs.getInt("eid"),rs.getString("name"),rs.getString("department"),rs.getFloat("salary"),rs.getInt("mobileNo"))
                );

                table.getItems().addAll(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
       // DBUtil.closeConnection(con);
    }


 /*   public void showAll(ActionEvent ae){
        try {
            con =DBUtil.getConnection();
            String sql="select * from employee";
            pstmt=con.prepareStatement(sql);

            rs=pstmt.executeQuery();
            while(rs.next()){
                eid.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("eid"));
                name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
                department.setCellValueFactory(new PropertyValueFactory<Employee, String>("department"));
                salary1.setCellValueFactory(new PropertyValueFactory<Employee, Float>("salary1"));
                mobile.setCellValueFactory(new PropertyValueFactory<Employee, String>("mobile"));
                ObservableList<Employee> data = FXCollections.observableArrayList(
                        new Employee(rs.getInt("id"),rs.getString("name"),rs.getString("department"),rs.getFloat("salary"),rs.getString("mobileNo"))
                );
                table.getItems().addAll(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 */

    public void showAll(ActionEvent ae) {
        show();
    }


    public void deleteEmployee(ActionEvent ae){
        try {
            con = DBUtil.getConnection();
         //*   String sql="delete from employee where id=?";
            String sql="delete from employee where eid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id.getText()));
            //* rs=pstmt.executeQuery();
            pstmt.executeUpdate();
        /*    if(rs!=null){
                lavel.setText("Record deleted ");
            }else{
                lavel.setText("please check employee id");
            }
        */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void update(ActionEvent ae) throws IOException{
        Stage primaryStage= new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Update.fxml"));
//			Parent root = FXMLLoader.load(getClass().getResource(arg0));
        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}