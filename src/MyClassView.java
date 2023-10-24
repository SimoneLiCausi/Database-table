
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class MyClassView {
    public class Student {
        private String name;
        private String surname;

        public Student(String name, String surname) {
            this.name = name;
            this.surname = surname;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public void main(String[] args) {
            String jdbcUrl = "jdbc:mysql://localhost:3306/newdb";
            String user = "root";
            String password = "Simone!996";

            try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {
                System.out.println("Connected to the database successfully.");

                // Creo la view "italian_students" per gli studenti italiani
                String createItalianViewQuery = "CREATE OR REPLACE VIEW italian_students AS " +
                        "SELECT name, surname FROM students WHERE country = 'Italy'";
                Statement createItalianViewStatement = conn.createStatement();
                createItalianViewStatement.execute(createItalianViewQuery);
                System.out.println("Created 'italian_students' view.");

                // Creo la view "german_students" per gli studenti tedeschi
                String createGermanViewQuery = "CREATE OR REPLACE VIEW german_students AS " +
                        "SELECT name, surname FROM students WHERE country = 'Germany'";
                Statement createGermanViewStatement = conn.createStatement();
                createGermanViewStatement.execute(createGermanViewQuery);
                System.out.println("Created 'german_students' view.");

                // Eseguo la query per gli studenti italiani
                String italianSelectQuery = "SELECT name, surname FROM italian_students";
                Statement italianStatement = conn.createStatement();
                ResultSet italianResultSet = italianStatement.executeQuery(italianSelectQuery);

                ArrayList<Student> italianStudents = new ArrayList<>();

                while (italianResultSet.next()) {
                    String name = italianResultSet.getString("name");
                    String surname = italianResultSet.getString("surname");

                    italianStudents.add(new Student(name, surname));
                }

                // Eseguo la query per gli studenti tedeschi
                String germanSelectQuery = "SELECT name, surname FROM german_students";
                Statement germanStatement = conn.createStatement();
                ResultSet germanResultSet = germanStatement.executeQuery(germanSelectQuery);

                ArrayList<Student> germanStudents = new ArrayList<>();

                while (germanResultSet.next()) {
                    String name = germanResultSet.getString("name");
                    String surname = germanResultSet.getString("surname");

                    germanStudents.add(new Student(name, surname));
                }

            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}
