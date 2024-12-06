package com.example.ds2_java;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.chart.NumberAxis;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;





public class HelloController {


    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField dateofbirthField;

    @FXML
    private TextField ConceptionField;

    @FXML
    private TextField JavaField;

    @FXML
    private TextField DBField;

    @FXML
    private TextField WebField;
    @FXML
    private TextField Userid;
    @FXML
    private Label exception;

    @FXML
    private TextField emailField;

    @FXML
    private TextField Userid1;

    @FXML
    private Button AddButton;
    @FXML
    private Button EditButton;
    @FXML
    private TableView<User> userTable;

    @FXML
    private TextField searchBar;
    @FXML
    private CheckBox Java;

    @FXML
    private CheckBox Conception;

    @FXML
    private CheckBox Web;
    @FXML
    private CheckBox DB;

    private Platform platform = new Platform();

    @FXML
    private Label UsersNumber;
    @FXML
    private  BorderPane borderPane;

    public static void saveUserInfo(String filePath, String userInfo) {
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                file.createNewFile();
            }

            Files.write(Paths.get(filePath), (userInfo + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void readFile(String filePath) throws InvalidEmailException,InvalidNameException{
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String firstName = data[0].trim();
                String lastName = data[1].trim();
                String birthdate = data[2].trim();
                String email = data[3].trim();
                int id = Integer.parseInt(data[4].trim());

                List<Course> courses = new ArrayList<>();
                for (int i = 5; i < data.length; i++) {
                    String[] courseData = data[i].split(" ");
                    String courseName = courseData[0].trim();
                    int points = Integer.parseInt(courseData[1].trim());
                    courses.add(new Course(courseName, points));
                }


                    User user = new User(firstName, lastName, birthdate, email, id, courses);
                    platform.addUser(user);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void deleteUserFromFile(String filePath, int Id) {
        Path path = Paths.get(filePath);
        List<String> lines;

        try {
            lines = Files.readAllLines(path);
            List<String> updatedLines = lines.stream()
                    .filter(line -> !line.contains(Integer.toString(Id))).toList();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : updatedLines) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateUser(String filePath, int userId, User updatedUser) {
        Path path = Paths.get(filePath);
        List<String> lines;

        try {
            lines = Files.readAllLines(path);
            List<String> updatedLine = lines.stream()
                    .map(line -> {
                        if (line.contains(Integer.toString(userId))) {
                            return updatedUser.toString();
                        } else {
                            return line;
                        }
                    }).toList();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : updatedLine) {
                    bw.write(line);
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onSearch() {
        String searchText = searchBar.getText().toLowerCase();
        ObservableList<User> userList = FXCollections.observableArrayList(platform.getUserList().stream()
                .filter(user -> user.getFirstName().toLowerCase().contains(searchText)
                        || user.getLastName().toLowerCase().contains(searchText)
                        || user.getEmail().toLowerCase().contains(searchText))
                .toList());
        userTable.setItems(userList);

    }
    @FXML
    protected void onAddUser(ActionEvent event) {
        int index;
        List<Course> selectedOptions = new ArrayList<>();
        if (Java.isSelected()) {
            index=Integer.parseInt(JavaField.getText());
            selectedOptions.add(new Course("Java",index));
        }
        if (DB.isSelected()) {
            index=Integer.parseInt(DBField.getText());
            selectedOptions.add(new Course("DataBase",index));
        }
        if (Conception.isSelected()) {
            index=Integer.parseInt(ConceptionField.getText());
            selectedOptions.add(new Course("Conception",index));
        }
        if (Web.isSelected()) {
            index=Integer.parseInt(WebField.getText());
            selectedOptions.add(new Course("Web",index));
        }
        try {

            User user = new User(firstNameField.getText(), lastNameField.getText(), dateofbirthField.getText(), emailField.getText(), platform.getUserList().size()+1,selectedOptions );

            platform.addUser(user);

            // Update the table view with the new user
            ObservableList<User> userList = FXCollections.observableArrayList(platform.getUserList());
            userTable.setItems(userList);
            UsersNumber();
            saveUserInfo("C:\\Users\\Oumayma\\Desktop\\DS2_JAVA\\src\\main\\java\\com\\example\\ds2_java\\DataBase",user.toString());
        } catch (InvalidNameException e) {
            exception.setText(e.InvalidNameException());
            exception.setVisible(true);

        } catch (InvalidEmailException e) {
            exception.setText(e.InvalidEmailException());
            exception.setVisible(true);
        }
    }
    @FXML
    protected void onEditUser(ActionEvent event)  {
              int index;
        try {
            List<Course> selectedOptions = new ArrayList<>();
            if (Java.isSelected()) {
                index=Integer.parseInt(JavaField.getText());
                selectedOptions.add(new Course("Java",index));
            }
            if (DB.isSelected()) {
                index=Integer.parseInt(DBField.getText());
                selectedOptions.add(new Course("DataBase",index));
            }
            if (Conception.isSelected()) {
                index=Integer.parseInt(ConceptionField.getText());
                selectedOptions.add(new Course("Conception",index));
            }
            if (Web.isSelected()) {
                index=Integer.parseInt(WebField.getText());
                selectedOptions.add(new Course("Web",index));
            }
        index = Integer.parseInt(Userid.getText());
            User user = new User(firstNameField.getText(), lastNameField.getText(), dateofbirthField.getText(), emailField.getText(), index,selectedOptions);
        platform.updateUser(user,index);
            updateUser("C:\\Users\\Oumayma\\Desktop\\DS2_JAVA\\src\\main\\java\\com\\example\\ds2_java\\DataBase", index, user);


        } catch (InvalidNameException e) {
        exception.setText(e.InvalidNameException());
        exception.setVisible(true);

    } catch (InvalidEmailException e) {
        exception.setText(e.InvalidEmailException());
        exception.setVisible(true);
    }
        ObservableList<User> userList = FXCollections.observableArrayList(platform.getUserList());
        userTable.setItems(userList);
        UsersNumber();
    }
    @FXML
    protected void onDeleteUser(ActionEvent event)  {
      User user = platform.getUserById(Integer.parseInt(Userid1.getText().trim()));
      platform.deleteUser(user);
        ObservableList<User> userList = FXCollections.observableArrayList(platform.getUserList());
        userTable.setItems(userList);
        UsersNumber();
        deleteUserFromFile("C:\\Users\\Oumayma\\Desktop\\DS2_JAVA\\src\\main\\java\\com\\example\\ds2_java\\DataBase",user.getId());
    }

    @FXML
    private void check() {
        boolean check = (!emailField.getText().trim().isEmpty() && !firstNameField.getText().trim().isEmpty() &&
                !lastNameField.getText().trim().isEmpty() && !dateofbirthField.getText().trim().isEmpty()) &&
                !(JavaField.getText().trim().isEmpty() && WebField.getText().trim().isEmpty() &&
                        DBField.getText().trim().isEmpty() && ConceptionField.getText().trim().isEmpty());
        AddButton.setDisable(!check);
    }

    @FXML
    private void checkEditButton() {
        boolean check = (!emailField.getText().trim().isEmpty() && !Userid.getText().trim().isEmpty() && !firstNameField.getText().trim().isEmpty() &&
                !lastNameField.getText().trim().isEmpty() && !dateofbirthField.getText().trim().isEmpty()) &&
                !(JavaField.getText().trim().isEmpty() && WebField.getText().trim().isEmpty() &&
                        DBField.getText().trim().isEmpty() && ConceptionField.getText().trim().isEmpty());
        EditButton.setDisable(!check);
    }
@FXML
private void CheckCourse(){
    if (Java.isSelected()) {
        JavaField.setDisable(false);
        JavaField.setText("0");
    } else {
        JavaField.setDisable(true);
        JavaField.setText("");
    }

    if (Conception.isSelected()) {
        ConceptionField.setDisable(false);
        ConceptionField.setText("0");

    } else {
        ConceptionField.setDisable(true);
        ConceptionField.setText("");
    }

    if (Web.isSelected()) {
        WebField.setDisable(false);
        WebField.setText("0");
    } else {
        WebField.setDisable(true);
        WebField.setText("");

    }

    if (DB.isSelected()) {
        DBField.setDisable(false);
        DBField.setText("0");
    } else {
        DBField.setDisable(true);
        DBField.setText("");
    }
}
public void UsersNumber()
{
    Integer number = platform.getUserList().size();
    UsersNumber.setText(number+" TOTAL USER ");}

    @FXML
    private void handleShowBarChart(ActionEvent event){
        CategoryAxis xAxis= new CategoryAxis();
        xAxis.setLabel("Cours");

        NumberAxis yAxis= new NumberAxis();
        yAxis.setLabel("Nombre d'Apprenants");

        BarChart barChart = new BarChart(xAxis,yAxis);

        XYChart.Series data= new XYChart.Series<>();
        data.setName("Cours terminés");
        int countjava=Platform.countUsersByCourseName(platform.getUserList(),"Java");
        int countdatabase=0;int countconception=0;int countweb=0;


        /*for (User user: platform.getUserList())
        {
            for (Course course: user.getCourse())
            {
                if (course.getCourseName()=="Java")
                    countjava++;
                else if (course.getCourseName()=="DataBase")
                    countdatabase++;
                else if (course.getCourseName()=="Conception")
                    countconception++;
                else countweb++;
            }

        }

         */
        //provide data
        data.getData().add(new XYChart.Data("java",countjava));
        data.getData().add(new XYChart.Data("DataBase",7));
        data.getData().add(new XYChart.Data("Conception",8));
        data.getData().add(new XYChart.Data("Web",9));



        barChart.getData().add(data);
        barChart.setLegendVisible(false);

        //add bar chart to borderpane
        borderPane.setCenter(barChart);

    }
   // @FXML
          //  private void nombreParticipantsTotal(){
       // FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
       // StackPane card = (StackPane) loader.load();
      //  Scene scene = new Scene(card);
       // stage.setScene(scene);
       // stage.show();
    //}


    @FXML
    private void handleClose(ActionEvent event){
        System.exit(0);
    }


    public void initialize() {
            AddButton.setDisable(true);
            EditButton.setDisable(true);
        DBField.setDisable(true);
        WebField.setDisable(true);
        ConceptionField.setDisable(true);
        JavaField.setDisable(true);


        try {
            readFile("C:\\Users\\Oumayma\\Desktop\\DS2_JAVA\\src\\main\\java\\com\\example\\ds2_java\\DataBase");
        } catch (InvalidNameException e) {
            e.InvalidNameException();
        } catch (InvalidEmailException e) {
            e.InvalidEmailException();
        }



        JavaField.textProperty().addListener((observable, oldValue, newValue) -> check());
        WebField.textProperty().addListener((observable, oldValue, newValue) -> check());
        DBField.textProperty().addListener((observable, oldValue, newValue) -> check());
        ConceptionField.textProperty().addListener((observable, oldValue, newValue) -> check());
        emailField.textProperty().addListener((observable, oldValue, newValue) -> check());
            firstNameField.textProperty().addListener((observable, oldValue, newValue) -> check());
            lastNameField.textProperty().addListener((observable, oldValue, newValue) -> check());
            dateofbirthField.textProperty().addListener((observable, oldValue, newValue) -> check());
            Userid.textProperty().addListener((observable, oldValue, newValue) -> check());



        JavaField.textProperty().addListener((observable, oldValue, newValue) -> checkEditButton());
        WebField.textProperty().addListener((observable, oldValue, newValue) ->checkEditButton());
        DBField.textProperty().addListener((observable, oldValue, newValue) -> checkEditButton());
        ConceptionField.textProperty().addListener((observable, oldValue, newValue) -> checkEditButton());
            emailField.textProperty().addListener((observable, oldValue, newValue) -> checkEditButton());
            firstNameField.textProperty().addListener((observable, oldValue, newValue) -> checkEditButton());
            lastNameField.textProperty().addListener((observable, oldValue, newValue) -> checkEditButton());
            dateofbirthField.textProperty().addListener((observable, oldValue, newValue) -> checkEditButton());
            Userid.textProperty().addListener((observable, oldValue, newValue) -> checkEditButton());
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> onSearch());



        TableColumn<User, String> Courses = new TableColumn<>("Courses");
        Courses.setCellValueFactory(cellDataFeatures -> {
            User user = cellDataFeatures.getValue();
            List<Course> CourseList = user.getCourse();
            String courses = "";
            for (Course course : CourseList) {
                courses += course.getCourseName() + " " + course.getPoints() + "\n";
            }
            return new SimpleStringProperty(courses);
        });

        ObservableList<User> userList = FXCollections.observableArrayList(platform.getUserList());
        userTable.setItems(userList);
        userTable.getColumns().add(Courses);
        UsersNumber();

    }
}