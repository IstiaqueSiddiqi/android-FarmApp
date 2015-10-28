package smartfarms.validation1;

public class Validation {

	public static boolean isEmpty(String input) {
		if (input.matches("")) {
			return true;
		}
		return false;
	}

	public static boolean isEmail(String input) {
		if (input.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			return true;
		}
		return false;
	}

	public static boolean isPassword(String input) {

		if (input != null && input.length() > 5) {
			return true;
		}
		return false;
	}

	public static boolean validateFirstName(String firstName) {
		return firstName.matches("[a-zA-Z]*");
	}

	public static boolean validateAlpha(String firstName) {
		return firstName.matches("[a-zA-Z]*");
	}

	public static boolean validateLastName(String lastName) {
		return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
	}

	public static boolean validateAddress(String address) {
		return address.matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
	}

	public static boolean validatePhone(String phone) {
		return phone.matches("[1-9]\\d{2}-[1-9]\\d{2}-\\d{4}");
	}

	public static boolean number(String num) {
		return num.matches("[0-9]*");
	}

}
