package smart.sftinyservice.utils;


public class ObjectCheck {
	
	/**
	 * true:if the string has content.
	 * */
	public static boolean validString(String s) {
		if (s != null && !s.trim().equals(""))
			return true;
		return false;
    }

	/**
	 * false:if the string has content.
	 * */
	public static boolean isEmptyString(String s) {
		if (s != null && !s.trim().equals(""))
			return false;
		return true;
	}
	
	/**
	 * true:if the object not null
	 * */
	public static boolean validObject(Object o) {
		if (o != null)
			return true;
		return false;
	}
	
	/**
	 * true:if all of the parameters not null and string parameter has content
	 * */
	public static boolean validParams(Object... params) {
		for(Object o:params) {
			if (o == null) {
				return false;
			}
			if (o instanceof String && ((String)o).trim().equals("")) {
				return false;
			}
		}
		return true;
	}
}
