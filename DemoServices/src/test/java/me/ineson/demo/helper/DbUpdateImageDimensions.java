package me.ineson.demo.helper;


import java.awt.Dimension;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.io.IOUtils;

import me.ineson.demo.service.utils.ImageUtils;
 
/**
 * This program demonstrates how to read file data from database and save the
 * data into a file on disk.
 *
 */
public class DbUpdateImageDimensions {
 
  public static void main(String[] args) throws Exception {
    Class.forName( System.getProperty("db.driver"));
    String url = System.getProperty("db.url");
    String user = System.getProperty("db.userid");
    String password = System.getProperty("db.password");
/*
 *   systemProperties "db.driver": "com.mysql.jdbc.Driver"
  systemProperties "db.url": "jdbc:mysql://localhost:3306/solarSystemTest"
  systemProperties "db.userid": "planetTest"
  systemProperties "db.password": "plan3t"

 *     	
        String url = "jdbc:mysql://localhost:3306/contactdb";
        String user = "root";
        String password = "secret";
*/
    
    try (Connection conn = DriverManager.getConnection(url, user, password)) {
	 
      String selectSql = "SELECT solarBodyId, image FROM solarBodyImage WHERE imageWidth IS NULL OR imageHeight IS NULL ORDER BY solarBodyId ASC";
      try( PreparedStatement statement = conn.prepareStatement(selectSql)) {

        String updateSql = "UPDATE solarBodyImage SET imageWidth = ?, imageHeight = ? WHERE solarBodyId = ?";
        try( PreparedStatement update = conn.prepareStatement(updateSql)) {
      
          try( ResultSet result = statement.executeQuery()) {
            while(result.next()) {
              Long id = result.getLong("solarBodyId");
              Blob blob = result.getBlob("image");
              InputStream inputStream = blob.getBinaryStream();
              byte image[] = IOUtils.toByteArray(inputStream); 
              IOUtils.closeQuietly(inputStream);
              
              Dimension imageDimensions = ImageUtils.getDimentions(image);

              System.out.println( "Upadting image dimensions on solay body id " + id
            		  + ", width " + imageDimensions.getWidth()
                      + ", height " + imageDimensions.getHeight());
              update.setDouble(1, imageDimensions.getWidth());
              update.setDouble(2, imageDimensions.getHeight());
              update.setLong(3, id);
              update.executeUpdate();
            }
   	      }
        }
      }
    }
  }
}
