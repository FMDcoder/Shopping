import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

public class Reader {
	public static LinkedList<String> readFile(File file, String regex) {
		LinkedList<String> list = new LinkedList<>();
		
		FileReader fr; 
		BufferedReader br;
		try {
			list.add("0");
			
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			
			int count = 0;
			String line;
			while((line = br.readLine()) != null) {
				count++;
				
				if(!line.matches(regex)) {
					list.clear();
					list.add("1");
					list.add("Ogilitga karaktärer finns i fältet, endast A-Ö och blanksteg i filen "
							+ "\n"+file.getAbsolutePath()+"\npå Linje: "+count);
					
					br.close();
					fr.close();
					
					return list;
				}
				
				list.add(line);
			}
			
			br.close();
			fr.close();
		}
		catch (Exception e) {
			list.clear();
			
			list.add("1");
			list.add("Något gick fel");
		}
		
		return list;
	}
}
