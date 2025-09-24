package validator;

public class Validator {
    private static final String regexName = "^\\s*[a-zA-Z]+\\s*$";
    private static final String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String regexTel = "^\\d{1,10}$";

    public static String addName(String nome) {
        System.out.println("Inserisci il nome:");
        nome = nome.trim();

        if ((!nome.isEmpty() || nome.length() <= 45) && nome.matches(regexName)) {
            nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
            return nome;
        } else {
            System.out.println("Il campo non è corretto, inserisci un nome valido.");
            return null; // <--- qui è importante il "return"
        }
    }
}
