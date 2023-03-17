import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class LoadGameFile {
	private Minesweeper minesweeper;
	
	/**
	 * Returns the minesweeper object.
	 * @return Minesweeper
	 */
	public Minesweeper getMinesweeper() {
		return minesweeper;
	}

	/**
	 * Exception class for Invalid Input File. Exception is called when the input file 
	 * has wrong description (aka wrong line number)
	 * @author aliga
	 *
	 */
	public static class InvalidDescription extends Exception {
		public InvalidDescription(String message) {
			super(message);
		}
	}
	
	/**
	 * Exception class for Invalid Input Value. Exception is either called when a invalid level is specified (not 1 or 2)
	 * or Wrong Mine Count (out of bounds for each level) or Wrong Time Value (out of bounds for each level) or Wrong 
	 * Supemine setting (only level 2 of difficulty can have a supermine active).
	 * @author aliga
	 *
	 */
	public static class InvalidValue extends Exception {
		public InvalidValue(String message) {
			super(message);
		}
	}
	
	/**
	 * The method reads from a file input named SCENARIO-id.txt and parses its line. The file must be of the following form:
	 * 	- level
	 * 	- mines
	 *  - time
	 *  - supermine
	 * Each value line is identified being inbound or out of bounds. In the latter case the corresponding exception is called.
	 * @param id Specifies the SCENARIO-id.txt. to be loaded from file system. Only valid id numbers are
	 * accepted or an exception is called. 
	 */
    public void LoadScenario(String id) {
    	int level, mines, time, supermine; 
    	
        String filePath = "./medialab/SCENARIO-" + id + ".txt"; // replace with your file path
        
        try (BufferedReader init_br = new BufferedReader(new FileReader(filePath))) {
        	int lines = 0;
            String line;
            while ((line = init_br.readLine()) != null) {
            	lines++;
                System.out.println(line);
            }
            if (lines != 4) {
            	throw new InvalidDescription("Input file lines must be equal to 4!");
            }
            init_br.close();
            
            BufferedReader br =  new BufferedReader(new FileReader(filePath));
            
            line = br.readLine();
            level = Integer.parseInt(line);
            if (level != 1 && level != 2) {
            	br.close();
            	throw new InvalidValue("Level can only be of type 1 or 2!");            	
            }            
            
            line = br.readLine();
            mines = Integer.parseInt(line);
            switch (level) {
            	case 1:
            		if (mines < 9 || mines > 11) {
            			br.close();
            			throw new InvalidValue("For level of difficulty 1 the mines must be between 9 and 11!");
            		}
            		break;
            	case 2: 
            		if (mines < 35 || mines > 45) {
            			br.close();
            			throw new InvalidValue("For level of difficulty 2 the mines must be between 35 and 45!");
            		}
            		break;
            }
            
            line = br.readLine();
            time = Integer.parseInt(line);
            switch (level) {
        	case 1:
        		if (time < 120 || time > 180) {
        			br.close();
        			throw new InvalidValue("For level of difficulty 1 the time must be between 120 and 180!");
        		}
        		break;
        	case 2: 
        		if (time < 240 || time > 360) {
        			br.close();
        			throw new InvalidValue("For level of difficulty 2 the mines must be between 240 and 360!");
        		}
        		break;
            }
            
            line = br.readLine();
            supermine = Integer.parseInt(line);
            switch(level) {
            case 1:
            	if (supermine != 0) {
        			br.close();
        			throw new InvalidValue("For level of difficulty 1 the supermine is unavailable!");
            	}
            	break;
            case 2: 
            	if (supermine != 0 && supermine != 1) {
        			br.close();
        			throw new InvalidValue("For level of difficulty 2 the supermine must be either 0 or 1!");
            	}
            	break;
            }
            br.close();
            
            Minesweeper minesweeper = new Minesweeper(level, mines, time, supermine);
            this.minesweeper = minesweeper;
            
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch	(InvalidDescription e) {
        	System.out.println("Error: " + e.getMessage());
        } catch	(InvalidValue e) {
        	System.out.println("Error: " + e.getMessage());
        }
    }
}
