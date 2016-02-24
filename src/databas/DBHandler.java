package databas;

import server.Journal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DBHandler {
	
	ArrayList<Journal> journalList;
	FileWriter fw = null;
	Scanner scan = null;
	
	public DBHandler(){
		journalList = null;
	}
	
	public ArrayList<Journal> read(String wanted){
		
		try {
			scan = new Scanner("journal.txt");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(wanted.length() == 10){
			readByPnr(wanted);
		}
		else{
			
		}
		
		return null;
	}
	
	public ArrayList<Journal> readByPnr(String pNr){
		
		return null;
	}
	
	

			
	
}
