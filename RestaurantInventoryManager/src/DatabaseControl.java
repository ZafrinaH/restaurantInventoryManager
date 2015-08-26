public class DatabaseControl {
	private static DatabaseControl databaseControl = new DatabaseControl();

	private DatabaseControl() {}
	
	public static DatabaseControl getInstance( ) {
	      return databaseControl;
	}

	
}
