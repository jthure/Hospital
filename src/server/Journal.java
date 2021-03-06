package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Journal {
	
	private String pNr = null;
	private String id = null;
	private String name = null;
	private String division = null;
	private String doctor = null;
	private String nurse = null;
	private String data = null;
	private static String counterFilePath="jcount.txt";
	
	public Journal(String pNr, String name, String division, String doctor, String nurse, String data){
		this.pNr = pNr;
		this.doctor = doctor;
		this.nurse = nurse;
		this.name = name;
		this.data = data;
		this.division = division;

		if(!Files.exists(Paths.get(counterFilePath))){
			try {
				Files.createFile(Paths.get(counterFilePath));
				PrintWriter pw = new PrintWriter(new File(counterFilePath));
				pw.println(0);
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Scanner scan = null;
		try {
			scan = new Scanner(new File(counterFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int count = scan.nextInt();
		count ++;
		id = String.valueOf(count);
		scan.close();
		PrintWriter pw = null;
		
		try {
			pw = new PrintWriter(new File(counterFilePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		pw.println(id);
		pw.close();
		
	}
	
	public Journal(String pNr, String id, String name, String division, String doctor, String nurse, String data){
		this.pNr = pNr;
		this.id = id;
		this.name = name;
		this.division = division;
		this.doctor = doctor;
		this.nurse = nurse;
		this.data = data;
	}
	
	public void write(String data, boolean append){
		if(append){
			this.data = this.data + " + " + data;
		}else {
			this.data = data;
		}
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(pNr + ";");
		sb.append(id + ";");
		sb.append(name + ";");
		sb.append(division + ";");
		sb.append(doctor + ";");
		sb.append(nurse + ";");
		sb.append(data);
		
		return sb.toString();
	}
	
	public String getPnr() {
		return pNr;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDoctor(){
		return doctor;
	}
	
	public String getNurse(){
		return nurse;
	}
	
	public String getId(){
		return id;
	}
	
	public String getDivision(){
		return division;
	}
	
//	public static void main(String[] args){
//		PrintWriter pw = null;
//		try {
//			pw = new PrintWriter("jcount.txt");
//		} catch (FileNotFoundException e) {
//
//			e.printStackTrace();
//		}
//		pw.println(0);
//		pw.close();
//		Journal j = new Journal("9302213138", "Alexander Arcombe", "Jonas", "Salas", "gynikolog", "Alexander har kli");
//		System.out.println(j.toString());
//	}
	
}
