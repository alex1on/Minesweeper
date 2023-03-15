import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LoadGameFile {
	public static class InvalidLineNumber extends Exception {
		public InvalidLineNumber(String message) {
			super(message);
		}
	}
	
	public static class InvalidLevelValue extends Exception {
		public InvalidLevelValue(String message) {
			super(message);
		}
	}
	
	public static class InvalidMineValue extends Exception {
		public InvalidMineValue(String message) {
			super(message);
		}
	}
	
	public static class InvalidTimeValue extends Exception {
		public InvalidTimeValue(String message) {
			super(message);
		}
	}
	
	public static class InvalidSupermineValue extends Exception {
		public InvalidSupermineValue(String message) {
			super(message);
		}
	}
	
	
    public static void LoadScenario(String[] args) {
    	int level, mines, time, supermine; 
    	
        String filePath = "./medialab/level_1_example.txt"; // replace with your file path
        
        try (BufferedReader init_br = new BufferedReader(new FileReader(filePath))) {
        	int lines = 0;
            String line;
            while ((line = init_br.readLine()) != null) {
            	lines++;
                System.out.println(line);
            }
            if (lines != 4) {
            	throw new InvalidLineNumber("Input file lines must be equal to 4!");
            }
            init_br.close();
            
            BufferedReader br =  new BufferedReader(new FileReader(filePath));
            
            line = br.readLine();
            level = Integer.parseInt(line);
            System.out.println(level);
            if (level != 1 && level != 2) {
            	br.close();
            	throw new InvalidLevelValue("Level can only be of type 1 or 2!");            	
            }            
            
            line = br.readLine();
            mines = Integer.parseInt(line);
            System.out.println(mines);
            switch (level) {
            	case 1:
            		if (mines < 9 || mines > 11) {
            			br.close();
            			throw new InvalidMineValue("For level of difficulty 1 the mines must be between 9 and 11!");
            		}
            		break;
            	case 2: 
            		if (mines < 35 || mines > 45) {
            			br.close();
            			throw new InvalidMineValue("For level of difficulty 2 the mines must be between 35 and 45!");
            		}
            		break;
            }
            
            line = br.readLine();
            time = Integer.parseInt(line);
            switch (level) {
        	case 1:
        		if (time < 120 || time > 180) {
        			br.close();
        			throw new InvalidTimeValue("For level of difficulty 1 the time must be between 120 and 180!");
        		}
        		break;
        	case 2: 
        		if (time < 240 || time > 360) {
        			br.close();
        			throw new InvalidTimeValue("For level of difficulty 2 the mines must be between 240 and 360!");
        		}
        		break;
            }
            
            line = br.readLine();
            supermine = Integer.parseInt(line);
            switch(level) {
            case 1:
            	if (supermine != 0) {
        			br.close();
        			throw new InvalidSupermineValue("For level of difficulty 1 the supermine is unavailable!");
            	}
            	break;
            case 2: 
            	if (supermine != 0 && supermine != 1) {
        			br.close();
        			throw new InvalidSupermineValue("For level of difficulty 2 the supermine must be either 0 or 1!");
            	}
            	break;
            }
            br.close();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch	(InvalidLineNumber e) {
        	System.out.println("Error: " + e.getMessage());
        } catch	(InvalidLevelValue e) {
        	System.out.println("Error: " + e.getMessage());
        } catch	(InvalidMineValue e) {
        	System.out.println("Error: " + e.getMessage());
        } catch	(InvalidTimeValue e) {
        	System.out.println("Error: " + e.getMessage());
        } catch	(InvalidSupermineValue e) {
        	System.out.println("Error: " + e.getMessage());
        }
        
    }
}
