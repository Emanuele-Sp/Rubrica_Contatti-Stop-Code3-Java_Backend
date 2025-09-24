package entity;

import jakarta.persistence.*;

import java.util.*;


@Entity
@Table(name = "contatti")
public class Contatto {

    /**
     *  Permette di creare una variabile scanner
     *  senza doverla istanziarla più volte
     */

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Stringhe immutabili ai quali vengono associate stringhe per le regex
     * da passare all'interno dei metodi.
     */
    private static final String regexName = "^\\s*[a-zA-Z]+\\s*$";
    private static final String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String regexTel = "^\\d{1,10}$";


    @Id // Rende questa colonna un primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String nome;

    @Column(length = 45, nullable = false)
    private String cognome;

    @Column(length = 255, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String telefono;

    @OneToMany(mappedBy = "contatto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Indirizzi> indirizzi = new HashSet<>();

    public Contatto() {
    }

    public Contatto(Long id, String nome, String cognome, String email, String telefono, Set<Indirizzi> indirizzi) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.indirizzi = indirizzi;
    }

    public Contatto(String nome, String cognome, String email, String telefono) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
    }

    /** Permette l'inserimento del nome.
     * Il metodo è richiamato nel metodo del Main
     * e il valore se valido viene associato per creare il nome del contatto,
     * in caso di errore permette il reinserimento di una nuova stringa.
     *
     * @return la stringa che viene inserita tramite lo scanner
     */

    public static String addName() {
        System.out.println("Inserisci il nome:");
        String nome = scanner.nextLine().trim();

        if ((!nome.isEmpty() || nome.length() <= 45) && nome.matches(regexName)) {
            nome = nome.substring(0, 1).toUpperCase() + nome.substring(1).toLowerCase();
            return nome;
        } else {
            System.out.println("Il campo non è corretto, inserisci un nome valido.");
            return addName(); // <--- qui è importante il "return"
        }
    }

    /** Permette l'inserimento del cognome.
     * Il metodo è richiamato nel metodo del Main
     * e il valore se valido viene associato per creare il cognome del contatto,
     * in caso di errore permette il reinserimento di una nuova stringa.
     *
     * @return la stringa che viene inserita tramite lo scanner
     */

    public static String addSurname() {
        System.out.println("Inserisci il cognome");
        String cognome = scanner.nextLine().trim();
        if ((!cognome.isEmpty() || cognome.length() <= 45) && cognome.matches(regexName)) {
            cognome = cognome.substring(0, 1).toUpperCase() + cognome.substring(1).toLowerCase();
            return cognome;
        } else {
            System.out.println("Il campo non è corretto, inserisci un cognome valido.");
            return addSurname();
        }
    }

    /** Permette l'inserimento della email.
     * Il metodo è richiamato nel metodo del Main
     * e il valore se valido viene associato per creare l'email del contatto,
     * in caso di errore permette il reinserimento di una nuova stringa.
     *
     * @return la stringa che viene inserita tramite lo scanner
     */

    public static String addEmail() {

        System.out.println("Inserisci l'email");
        String email = scanner.nextLine().trim();
        if ((!email.isEmpty() || email.length() <= 255) && email.matches(regexEmail)) {
            email = email.toLowerCase();
            return email;
        } else {
            System.out.println("Il campo non è corretto, inserisci una email valida.");
            return addEmail();
        }
    }

    /** Permette l'inserimento del numero telefonico.
     * Il metodo è richiamato nel metodo del Main
     * e il valore se valido viene associato per creare il telefono del contatto,
     * in caso di errore permette il reinserimento di una nuova stringa.
     *
     * @return la stringa che viene inserita tramite lo scanner
     */

    public static String addTel() {

        System.out.println("Inserisci il tuo numero di telefono");
        String telefono = scanner.nextLine().trim();
        if ((!telefono.isEmpty() || telefono.length() != 10) && telefono.matches(regexTel)) {
            return telefono;
        } else {
            System.out.println("Il campo non è corretto, inserisci un numero valido.");
            return addTel();
        }
    }

    /**
     * Metodo che gestisce la relazione OneToMany/ManyToOne
     * tra le entità Contatto e Indirizzi aggiornando
     * il riferimento contatto dentro l'oggetto indirizzo
     * e poi inserendo l'indirizzo alla lista degli indirizzi del contatto
     *
     * @param indirizzo è l'oggetto creato della classe Indirizzi
     */

    public void addIndirizzo(Indirizzi indirizzo) {
        indirizzo.setContatto(this); // aggiorna il lato ManyToOne
        this.indirizzi.add(indirizzo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<Indirizzi> getIndirizzi() {
        return indirizzi;
    }

    public void setIndirizzi(Set<Indirizzi> indirizzi) {
        this.indirizzi = indirizzi;
    }

    @Override
    public String toString() {
        return "Contatto: " +
                "id: " + id +
                ", nome: " + nome +
                ", cognome: " + cognome +
                ", email: " + email +
                ", telefono: " + telefono + '\n' +
                "indirizzi: " + indirizzi
                ;
    }
}
