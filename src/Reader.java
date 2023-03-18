import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Reader {
	public static LinkedList<String> readFile(File file, String regex) {
		LinkedList<String> list = new LinkedList<>();
		
		FileInputStream fis;
		InputStreamReader is;
		BufferedReader br;
		try {
			list.add("0");
			
			fis = new FileInputStream(file);
			is = new InputStreamReader(fis, StandardCharsets.UTF_8);
			br = new BufferedReader(is);
			
			int count = 0;
			String line;
			while((line = br.readLine()) != null) {
				count++;
				
				if(!line.matches(regex)) {
					list.clear();
					list.add("1");
					list.add("Ogilitga karakt�rer finns i f�ltet, endast A-� och blanksteg i filen "
							+ "\n"+file.getAbsolutePath()+"\np� Linje: "+count+"\n"+line);
					
					br.close();
					is.close();
					fis.close();
					
					return list;
				}
				
				list.add(line);
			}
			
			br.close();
			is.close();
			fis.close();
		}
		catch (Exception e) {
			list.clear();
			
			list.add("1");
			list.add("N�got gick fel");
		}
		
		return list;
	}
}
