import java.sql.Connection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.sql.*;

class DatabaseConnection {
    private static Connection con = null;

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            return con;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return null;
        }
    }
}

class DatabaseOperations {
    public static void putData(String productId, String productName, String manufacturerName, String manufacturerCity,
            String manufacturerGSTIN, String productPrice) throws ClassNotFoundException {

        String query = "insert into inventory(item_information) values(?::jsonb)";

        
        // Correct JSON structure with proper string handling
        String jsonDataString = "{" +
                "\"product_id\":" + productId + "," +
                "\"product_name\":\"" + productName + "\"," +
                "\"manufacturer\":{" +
                "\"name\":\"" + manufacturerName + "\"," +
                "\"city\":\"" + manufacturerCity + "\"," +
                "\"GSTIN\":\"" + manufacturerGSTIN + "\"" +
                "}," +
                "\"product_price\":" + productPrice +
                "}";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

            // Set the JSON string parameter
            stmt.setObject(1, jsonDataString, Types.OTHER);
            stmt.executeUpdate();
            System.out.println("Data inserted successfully!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void getByManufacturerById(String id) {
        String query = "select item_information from inventory ";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.setString(1, query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                String jsonData = rs.getString("item_information");

                System.out.println("Item ID: " + item_id + ", Data: " + jsonData);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void getData() {
        String query = "SELECT item_id, item_information FROM inventory";

        try (Connection con = DatabaseConnection.getConnection();
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("item_id");
                String jsonData = rs.getString("item_information");

                System.out.println("Item ID: " + id + ", Data: " + jsonData);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

public class JsonOprSQL {
    public static void main(String[] args) {
        Connection con = DatabaseConnection.getConnection();
        // DatabaseOperations.putData();
        // DatabaseOperations.getData();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the Choice");
            System.out.println("1-> Insert, 2->Display,3->Exit,4->Update Product Details");
            String choice = br.readLine();

            switch (choice) {
                case "1":
                    System.out.println("Enter Product ID:-");
                    String productId = br.readLine();
                    System.out.println("Enter Product Name:-");
                    String productName = br.readLine();
                    System.out.println("Enter Manufacturer Name:-");
                    String manufacturerName = br.readLine();
                    System.out.println("Enter Manufacturer City:-");
                    String manufacturerCity = br.readLine();
                    System.out.println("Enter Manufacturer GSTIN:-");
                    String manufacturerGSTIN = br.readLine();
                    System.out.println("Enter Product Price:-");
                    String productPrice = br.readLine();

                    DatabaseOperations.putData(productId, productName, manufacturerName, manufacturerCity,
                            manufacturerGSTIN, productPrice);

                    break;

                case "2":
                    DatabaseOperations.getData();

                    break;
                case "3":
                    System.out.println("Exiting...");
                    break;
                case "4":
                    System.out.println("Enter ID:- ");
                    String id = br.readLine();
                    DatabaseOperations.getByManufacturerById(id);
                    break;

            }

        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}