import entity.Contatto;
import entity.Indirizzi;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Scanner;

public class AppRubrica {

    private static final SessionFactory factory = ServerConnectionMySql.buildSessionFactory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int input;

        do {
            printChoicheMethod();
            input = scanner.nextInt();
            switch (input) {
                case 0:
                    System.out.println("Hai terminato il programma");
                    break;
                case 1:
                    addContact();
                    break;
                case 2:
                    getContatti();
                    break;
                case 3:
                    truncateDatabase();
                    break;
                case 4:
                    getNumeroContatti();
                    break;
            }
        } while (input != 0);
    }

    public static void addContact() {
        System.out.println("Mi sto connettendo al database MySQL");
        Transaction transaction = null;

        Contatto contatto = createContact();
        Indirizzi indirizzo = createAddress();

        indirizzo.setContatto(contatto);
        contatto.addIndirizzo(indirizzo);

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(contatto);
            session.persist(indirizzo);

            transaction.commit();
            System.out.println("Inserimento riuscito");
        } catch (NullPointerException ex) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(ex.getMessage());
            System.out.println("Hai sbagliato qualcosa");
        }
    }

    public static void truncateDatabase() {
        Transaction tr = null;

        try (Session session = factory.openSession()) {

            tr = session.beginTransaction();
            Long rowTable;
            rowTable = (Long) session.createNativeQuery("SELECT COUNT(*) FROM contatti").getSingleResult();

            if (rowTable.intValue() != 0) {
                session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
                // -- prima svuoti la tabella figlia
                session.createNativeQuery("TRUNCATE TABLE indirizzi;").executeUpdate();
                // -- ora puoi svuotare la tabella padre
                session.createNativeQuery("TRUNCATE TABLE contatti;").executeUpdate();
                session.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
                System.out.println("I dati delle tabelle sono state eliminate");
            } else
                System.out.println("La tabella è già vuota");

            tr.commit();
        } catch (NullPointerException ex) {
            if (tr != null)
                tr.rollback();

            System.out.println(ex.getMessage());
        }
    }

    public static void printChoicheMethod() {
        System.out.println("Scegli una operazione");
        String[] str = {"Inserisci un utente", "Stampa utenti", "Cancella dati dal database", "Stampa MAX id", "Termina programma"};

        for (int i = 0; i < str.length; i++) {
            if (i == str.length - 1)
                System.out.println(0 + " - " + str[i]);
            else
                System.out.println(i + 1 + " - " + str[i]);
        }
    }

    public static void getContatti() {
        Transaction tr = null;
        try (Session session = factory.openSession()) {
            tr = session.beginTransaction();
            List<Contatto> list = session.createQuery("FROM Contatto").list();

            for (Contatto c : list)
                System.out.println(c);
            tr.commit();
        }
    }

    public static Long getNumeroContatti() {
        Transaction tr = null;
        Long maxID = null;
        Long numRow;
        try (Session session = factory.openSession()) {
            tr = session.beginTransaction();

            maxID = (Long) session.createNativeQuery("SELECT max(c.id) FROM contatti c").getSingleResult();
            numRow = (Long) session.createNativeQuery("SELECT COUNT(*) FROM contatti").getSingleResult();

            if (maxID != null && numRow != null) {
                System.out.println("Ci sono " + numRow + " contatti in rubrica");
                System.out.println("L'id più alto è: " + maxID);
            } else
                System.out.println("Non ci sono contatti in rubrica");

            tr.commit();
            return numRow;
        } catch (ClassCastException ccEx) { // è una eccezione unchecked
            System.out.println(ccEx.getMessage());
        }
        return null;
    }

    public static Contatto createContact() {
//        String nome = scanner.nextLine();
        String name = Contatto.addName();
        String surname = Contatto.addSurname();
        String email = Contatto.addEmail();
        String tel = Contatto.addTel();

        Contatto contatto = new Contatto(name, surname, email, tel);
        return contatto;
    }

    /**
     * address riceve in input la stringa che riceve dal metodo addAddress()
     * addressNum riceve in input la stringa che riceve dal metodo addAddressNum()
     * cap riceve in input la stringa che riceve dal metodo addCap()
     * city riceve in input la stringa che riceve dal metodo addCity()
     * province riceve in input la stringa che riceve dal metodo addProvince()
     *
     * @return indirizzo -> oggetto della classe Indirizzi
     */

    public static Indirizzi createAddress() {
        String address = Indirizzi.addAddress();
        String addressNum = Indirizzi.addAddressNum();
        String cap = Indirizzi.addCap();
        String city = Indirizzi.addCity();
        String province = Indirizzi.addProvince();

        Indirizzi indirizzo = new Indirizzi(address, addressNum, cap, city, province);
        return indirizzo;
    }

}
