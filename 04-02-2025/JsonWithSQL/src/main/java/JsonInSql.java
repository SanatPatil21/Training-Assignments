import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;

import org.json.*;

class DatabaseConnection {
    private static Connection con = null;

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/demodb", "postgres", "tiger");
            return con;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
            return null;
        }
    }
}

class DatabaseOperations{
	public static void putData(String productId, String productName, String manufacturerName, String manufacturerCity,
            String manufacturerGSTIN, String productPrice) {
		String query = "insert into inventory(item_information) values(?::jsonb)";
		
		JSONObject json = new JSONObject();
		json.put("product_id", productId);
		json.put("product_name", productName);
		json.put("product_price", productPrice);
		json.put("manufacturer", new JSONObject().put("city", manufacturerCity).put("name", manufacturerName).put("GSTIN", manufacturerGSTIN));
		
		try(
				PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(query);
				){
			stmt.setObject(1, json,Types.OTHER);
			stmt.execute();
			
		}catch(Exception e) {
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
	
	public static void deleteProduct(int id) {
		String query = "DELETE from inventory where item_id="+id;
		try(
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(query);
				){

			stmt.execute();
			System.out.println("Product with id: "+id+" sucessfully deleted");
		}catch(Exception e) {
			System.out.println(e);
		}	
	}
	
	public static void updateProduct(int id, BufferedReader br) {
		String selectQuery = "SELECT item_id, item_information FROM inventory WHERE item_id = ?";
        String updateQuery = "UPDATE inventory SET item_information = ?::jsonb WHERE item_id = ?";
		try(
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement selectStmt = con.prepareStatement(selectQuery);
                PreparedStatement updateStmt = con.prepareStatement(updateQuery);
				
				){
			selectStmt.setInt(1, id);
            try (ResultSet rs = selectStmt.executeQuery()) {
			if(rs.next()) {
			System.out.println("Product Details:");
            JSONObject jsonData = new JSONObject(rs.getString("item_information"));
            System.out.println(jsonData);

            System.out.println("What do you want to update?");
            String keyToUpdate = br.readLine();
            System.out.println("Update to?");
            String valueToUpdate = br.readLine();

            jsonData.put(keyToUpdate, valueToUpdate);
            updateStmt.setString(1, jsonData.toString());
            updateStmt.setInt(2, id);
            updateStmt.executeUpdate();
            
            System.out.println("Updated product details:");
            System.out.println(jsonData);
        } else {
            System.out.println("Product not found.");
        }
            }
			
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}


public class JsonInSql {

	public static void main(String[] args) {
		String choice="";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
        	do{
            System.out.println("Enter the Choice");
            System.out.println("1->Add Product \n2->Display\n3->Exit\n4->Delete Product\n5->Update Product Details");
            choice = br.readLine();

            switch (choice) 
            {
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
                    int id_delete = Integer.parseInt(br.readLine());
                    DatabaseOperations.deleteProduct(id_delete);
                    break;
                    
                case "5":
                    System.out.println("Enter Product ID:- ");
                    int id_update = Integer.parseInt(br.readLine());
                    DatabaseOperations.updateProduct(id_update,br);
                    break;

            }
        	}while(!choice.equals("3"));

        } catch (Exception e) {
            // TODO: handle exception
        }

        }
}


