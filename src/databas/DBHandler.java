package databas;

import server.Journal;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import model.Authority;
import model.Doctor;
import model.Nurse;
import model.Patient;
import model.User;

public class DBHandler {

	public static final String TABLE_HEADER="Personal number;Journal ID;Patient name;Division;Doctor;Nurse;Data";
	private String dbFilePath="journal.txt";
	private HashMap<String, Journal> map;
	private ArrayList<Journal> journalList;
	private FileWriter fw = null;
	private Scanner scan = null;


	public DBHandler() {
		journalList = new ArrayList<>();
		map = new HashMap<>();
		if(!Files.exists(Paths.get(dbFilePath))){
			try {
				Files.createFile(Paths.get(dbFilePath));
				Logger.log("Created new database");
				updateDB();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			scan = new Scanner(new File(dbFilePath));
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}

		String info = null;
		String pnr, id, name, division, doctor, nurse, data = "";
		scan.nextLine();
		while (scan.hasNextLine()) {
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

	private void updateDB() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(dbFilePath);
		} catch (Exception e) {
		}
		pw.println("Pnr;id;name;division;doctor;nurse;data");
		for (Journal j : map.values()) {
			pw.println(j.toString());
		}
		pw.close();
	}


	
	public ArrayList<Journal> read(String wanted, User user){
		
		if(wanted.length() == 10){
			return readByPnr(wanted, user);
		}
		else{
			return readById(wanted, user);

		}
	}
	

	
	public ArrayList<Journal> readByPnr(String pNr, User user){

		journalList.clear();
		for (Journal j : map.values()) {
			if (pNr.equals(j.getPnr()) && checkPremissionToRead(j, user)) {
				journalList.add(j);
			}
		}
		return journalList;
	}

	public ArrayList<Journal> readById(String id, User user){

		Journal journal = map.get(id);
		journalList.clear();
		if(journal != null && checkPremissionToRead(journal, user)){
			journalList.add(journal);
		}
		return journalList;
	}
	
	private boolean checkPremissionToRead(Journal journal, User user){
		
		if (user.getDivision().equals(journal.getDivision()))
			return true;
		else if(user.getType().equals(Doctor.class.getSimpleName()) && user.getName().equals(journal.getDoctor()))
			return true;
		else if(user.getType().equals(Nurse.class.getSimpleName()) && user.getName().equals(journal.getNurse()))
			return true;
		else if(user.getType().equals(Patient.class.getSimpleName()) && user.getName().equals(journal.getName()))
			return true;
		else if(user.getType().equals(Authority.class.getSimpleName()))
			return true;
		
		return false;
	}

	public boolean add(Journal journal) throws IOException {
		if (map.containsKey(journal.getId())) {
			return false;
		}
		map.put(journal.getId(), journal);
		try {
			fw = new FileWriter(new File(dbFilePath), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		fw.write("\n" + journal.toString());
		fw.close();
		return true;
	}

	public boolean write(String id, String data, User user, boolean append) {
		Journal j = map.get(id);
		if (j == null) {
			return false;
		} else if (user.getType().equals(Doctor.class.getSimpleName())) {
			if (j.getDoctor().equals(user.getName())) {
				j.write(data, append);
				updateDB();
				return true;
			}
		} else if (j.getNurse().equals(user.getName())) {
			j.write(data, append);
			updateDB();
			return true;

		}

		return false;
	}


	public boolean delete(String id) {
		boolean result = map.remove(id) != null ? true : false;
		if (result)
			updateDB();
		return result;
	}

	// public static void main(String[] args) throws IOException{
	// DBHandler db = new DBHandler();
	// System.out.println(db.read("1").get(0).toString());
	// Journal j = new Journal("0402213138", "Jonas Thuresson",
	// "barnmotagningen", "Alexander", "Salas", "Jonas har problem med
	// alkoholen");
	// Journal jb = new Journal("0402213138", "Jonas Thuresson",
	// "barnmotagningen", "Alexander", "Salas", "Jonas måste skickas till
	// rehab");
	// db.add(j);
	// db.add(jb);
	// System.out.println(db.read("0402213138").get(0).toString());
	// System.out.println(db.read("0402213138").get(1).toString());
	//
	// }

}
