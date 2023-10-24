
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class MyClassAlter {

    public class Main {
        public static void main(String[] args) {
            String jdbcUrl = "jdbc:mysql://localhost:3306/newdb";
            String user = "root";
            String password = "Simone!996";

            try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password)) {
                System.out.println("Connected to the database successfully.");

                // Qui aggiungo "country" alla tabella "students"
                String addColumnQuery = "ALTER TABLE students ADD COLUMN country VARCHAR(30)";
                Statement addColumnStatement = conn.createStatement();
                addColumnStatement.executeUpdate(addColumnQuery);
                System.out.println("Added 'country' column to the 'students' table.");

                // E poi aggiungo alla nuova colonna dei valori
                String populateDataQuery = "UPDATE students SET country = ? WHERE student_id = ?";
                try (java.sql.PreparedStatement preparedStatement = conn.prepareStatement(populateDataQuery)) {
                    // Aggiungo alla colonna "country" i suoi valori
                    // In questo caso, assegno "Italy" ai primi due studenti e "Germany" agli altri due
                    preparedStatement.setString(1, "Italy");
                    preparedStatement.setInt(2, 1);
                    preparedStatement.executeUpdate();

                    preparedStatement.setString(1, "Italy");
                    preparedStatement.setInt(2, 2);
                    preparedStatement.executeUpdate();

                    preparedStatement.setString(1, "Germany");
                    preparedStatement.setInt(2, 3);
                    preparedStatement.executeUpdate();

                    preparedStatement.setString(1, "Germany");
                    preparedStatement.setInt(2, 4);
                    preparedStatement.executeUpdate();

                    System.out.println("Updated 'country' column with the desired values.");
                }
            } catch (SQLException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

}
