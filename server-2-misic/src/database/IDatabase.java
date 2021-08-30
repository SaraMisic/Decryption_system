package database;

import utils.CustomException;

import java.sql.SQLException;

public interface IDatabase {

	public void connect(String pathToDatabase);
	public String getKey(String fileName) throws CustomException;
	public void logAction (String action,String datetime);


}
