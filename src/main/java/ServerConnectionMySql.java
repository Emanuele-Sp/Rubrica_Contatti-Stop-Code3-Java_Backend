import entity.Contatto;
import entity.Indirizzi;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ServerConnectionMySql {

    /**
     * Parametri che verranno passati per la connessione alla stringa JDBC_URL
     */

    private static final String HOST = "localhost";
    private static final Integer PORT = 3307;
    private static final String SCHEMA = "esercizio_rubrica_base";

    /**
     * Parametri che verranno passati per la connessione al database MySQL
     */

    public static final String JDBC_URL = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, SCHEMA); // tcp
    public static final String USER = "root";
    public static final String PASS = "root";

    /**
     * Configuration permette di mappare le classi Java ai database relazionali
     * e come connettersi a essi
     *
     * La SessionFactory è un singleton che gestisce la connessione al database e la creazione di oggetti Session.
     * @return
     */

    public static SessionFactory buildSessionFactory(){
        Configuration config = new Configuration();
        try{
            config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            config.setProperty("hibernate.connection.url",JDBC_URL);
            config.setProperty("hibernate.connection.username",USER);
            config.setProperty("hibernate.connection.password", PASS);
            config.setProperty("hibernate.hbm2ddl.auto", "update"); // Specifica se creare DB allo start dell'app
            config.addAnnotatedClass(Contatto.class); // aggiungi classi al contesto
            config.addAnnotatedClass(Indirizzi.class); // aggiungi classi al contesto
            return config.buildSessionFactory();
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
