package de.outlook.therealspeedy.besocial.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ListStore {
	
	private File storageFile;
	private ArrayList<String> values;
	
	public ListStore(File file) {
		this.storageFile  = file;
		this.values = new ArrayList<>();
		
		if (!this.storageFile.exists()) {
			try {
				this.storageFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("[BeSocial] [ERROR] Cant write to disk.");
			}
		}
	}
	
	public void load() {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				if (!this.contains(line)) {
					this.values.add(line);
				}
			}
			
			reader.close();
			input.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		
		try {
			FileWriter stream = new FileWriter(this.storageFile);
			BufferedWriter out = new BufferedWriter(stream);
			
			for (String value : this.values) {
				out.write(value);
				out.newLine();
			}
			
			out.close();
			stream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("[BeSocial] [ERROR] Cant write to disk.");
		}
	}
	
	public boolean contains(String value) {
		return this.values.contains(value);
	}
	
	public int length() {
		return values.size();
	}
	
	public void add(String value) {
		if (!this.contains(value)) {
			this.values.add(value);
		}
	}
	
	public void remove(String value) {
		this.values.remove(value);
	}
	
	public ArrayList<String> getValues(){
		return this.values;
	}

}
