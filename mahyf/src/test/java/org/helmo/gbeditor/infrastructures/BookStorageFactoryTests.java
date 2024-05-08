package org.helmo.gbeditor.infrastructures;

import static org.junit.jupiter.api.Assertions.*;

import org.helmo.gbeditor.repositories.ConnectionFailedException;
import org.helmo.gbeditor.repositories.JdbcDriverNotFoundException;
import org.junit.jupiter.api.Test;

class BookStorageFactoryTests {


	@Test
	void goodDriverName() {
		try {
			BookStorageFactory factory = new BookStorageFactory(
					"org.apache.derby.jdbc.EmbeddedDriver",
		            "jdbc:derby:derby;create=true;",
		            "",
		            ""	
		    );
		}catch(JdbcDriverNotFoundException e) {
			fail();
		}		
	}
	
	@Test
	void errorDriverName() {
		try {
			BookStorageFactory factory = new BookStorageFactory(
					"hello",
		            "jdbc:derby:derby;create=true;",
		            "",
		            ""	
		    );
		}catch(JdbcDriverNotFoundException e) {
			assertEquals(e.getMessage(), "Unable to load driver hello. Is it available from the class path?");
		}		
	}

	@Test
	void goodCaseNewStorageSession() {
		try {
			BookStorageFactory factory = new BookStorageFactory(
					"org.apache.derby.jdbc.EmbeddedDriver",
		            "jdbc:derby:derby;create=true;",
		            "",
		            ""	
		    );
			
			SqlBookStorage storage = factory.newStorageSession();
		}catch(ConnectionFailedException e) {
			fail();
		}		
	}
}
