package databas;

import server.Journal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.sun.javafx.geom.transform.GeneralTransform3D;

public class DBHandler {
	
	HashMap<String, Journal> map;
	ArrayList<Journal> journalList;
	FileWriter fw = null;
	Scanner scan = null;
	
	public DBHandler(){
		journalList = new ArrayList<>();
		map = new HashMap<>();
		try {
			scan = new Scanner(new File("journal.txt"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String info = null;
		String pnr,id,name,division,doctor,nurse,data = "";
		scan.nextLine();
		while(scan.hasNextLine()){
			info = scan.nextLine();
			pnr = info.split(";")[0];
			id = info.split(";")[1];
			name = info.split(";")[2];
			division = info.split(";")[3];
			doctor = info.split(";")[4];
			nurse = info.split(";")[5];
			data = info.split(";")[6];
			
			map.put(id, new Journal(pnr, id, name, division, doctor, nurse, data));
		}
		scan.close();
	}
	
	private void updateDB(){
		PrintWriter pw = null;
		try {
			pw = new PrintWriter("journal.txt");
		} catch (Exception e) {
			// TODO: handle exception
		}
		pw.println("Pnr;id;name;division;doctor;nurse;data");
		for(Journal j : map.values()){
			pw.println(j.toString());
		}
	}

//	public HashMap<String, Journal> getMap(){
//		return map;
//	}
	
	public ArrayList<Journal> read(String wanted){
		
		if(wanted.length() == 10){
			return readByPnr(wanted);
		}
		else{
			return readById(wanted);
		}
	}
	
	public ArrayList<Journal> readByPnr(String pNr){
		journalList.clear();
		for(Journal j : map.values()){
			if (pNr.equals(j.getPnr())){
				journalList.add(j);
			}
		}
		
		return journalList;
	}
	
	public ArrayList<Journal> readById(String id){
		Journal journal = map.get(id);
		journalList.clear();
		if(journal != null){
			journalList.add(journal);
			return journalList;
		}
		return null;
	}
	
	public boolean add(Journal journal) throws IOException{
		if (map.containsKey(journal.getId())){
			return false;
		}
		map.put(journal.getId(), journal);
		try {
			fw = new FileWriter(new File("journal.txt"), true);
		} catch (Exception e) {
			// TODO: handle exception
		}
		fw.write("\n" + journal.toString());
		fw.close();
		return true;
	}
	
	public void write(String id, String data, boolean append){
		Journal j = map.get(id);
		if (j != null){
			j.write(data, append);
		}
		updateDB();
	}
	
	public boolean delete(String id){
		return map.remove(id)!=null?true:false;
	}


			
	
}
