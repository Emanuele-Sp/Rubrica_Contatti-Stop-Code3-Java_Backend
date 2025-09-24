package entity;

import jakarta.persistence.*;

import java.util.Scanner;

@Entity
@Table(name = "indirizzi")
public class Indirizzi {

    /**
     *  Permette di creare una variabile scanner
     *  senza doverla istanziarla più volte
     */

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Stringhe immutabili ai quali vengono associate stringhe per le regex
     * da passare all'interno dei metodi.
     */

    private static final String regexAddress = "^\\s*[A-Za-zÀ-ÿ']+(?:\\s[A-Za-zÀ-ÿ']+)*\\s*$";
    private static final String regexAddressNum = "^\\s*\\d{1,5}([/.]?[a-zA-Z])?\\s*$";
    private static final String regexCap = "^\\s*\\d{5}\\s*$";
    private static final String regexCity = "^\\s*[A-Za-zÀ-ÿ']+(?:[\\s-][A-Za-zÀ-ÿ']+)*\\s*$";
    private static final String regexProvince = "^\\s*[a-zA-Z]{2}\\s*$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String via;

    @Column(length = 45, nullable = false)
    private String civico;

    @Column(length = 5, nullable = false)
    private String cap;

    @Column(length = 45, nullable = false)
    private String comune;

    @Column(length = 2, nullable = false)
    private String provincia;


    @ManyToOne
    @JoinColumn(name = "id_contatto")
    private Contatto contatto;

    public Indirizzi(){

    }

    public Indirizzi(Long id, String via, String civico, String cap, String comune, String provincia) {
        this.id = id;
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.comune = comune;
        this.provincia = provincia;


    }

    public Indirizzi(String via, String civico, String cap, String comune, String provincia) {
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.comune = comune;
        this.provincia = provincia;

    }

    /** Permette l'inserimento della via.
     * Il metodo è richiamato nel metodo del Main
     * e il valore se valido viene associato per creare
     * il nome della via dell'oggetto indirizzo
     *
     * @return la stringa che viene inserita tramite lo scanner
     */

    public static String addAddress() {
        System.out.println("Inserisci la via / corso / piazza:");
        String address = scanner.nextLine().trim();

        if ((!address.isEmpty() || address.length() <= 45) && address.matches(regexAddress)) {
            address = address.substring(0, 1).toUpperCase() + address.substring(1).toLowerCase();
            return address;
        } else {
            System.out.println("Il campo non è corretto, inserisci una via / corso / piazza validi.");
            return addAddress(); // <--- qui è importante il "return"
        }
    }
    public static String addAddressNum() {
        System.out.println("Inserisci il addressNum:");
        String addressNum = scanner.nextLine().trim();

        if ((!addressNum.isEmpty() || addressNum.length() <= 45) && addressNum.matches(regexAddressNum)) {
            addressNum = addressNum.substring(0, 1).toUpperCase() + addressNum.substring(1).toLowerCase();
            return addressNum;
        } else {
            System.out.println("Il campo non è corretto, inserisci un addressNum valido.");
            return addAddressNum(); // <--- qui è importante il "return"
        }
    }
    public static String addCap() {
        System.out.println("Inserisci il cap:");
        String cap = scanner.nextLine().trim();

        if ((!cap.isEmpty() || cap.length() <= 45) && cap.matches(regexCap)) {
            cap = cap.substring(0, 1).toUpperCase() + cap.substring(1).toLowerCase();
            return cap;
        } else {
            System.out.println("Il campo non è corretto, inserisci un cap valido.");
            return addCap(); // <--- qui è importante il "return"
        }
    }
    public static String addCity() {
        System.out.println("Inserisci il city:");
        String city = scanner.nextLine().trim();

        if ((!city.isEmpty() || city.length() <= 45) && city.matches(regexCity)) {
            city = city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
            return city;
        } else {
            System.out.println("Il campo non è corretto, inserisci un city valido.");
            return addCity(); // <--- qui è importante il "return"
        }
    }
    public static String addProvince() {
        System.out.println("Inserisci il province:");
        String province = scanner.nextLine().trim();

        if ((!province.isEmpty() || province.length() <= 45) && province.matches(regexProvince)) {
            province = province.toUpperCase();
            return province;
        } else {
            System.out.println("Il campo non è corretto, inserisci un province valido.");
            return addProvince(); // <--- qui è importante il "return"
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCivico() {
        return civico;
    }

    public void setCivico(String civico) {
        this.civico = civico;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


    public Contatto getContatto() {
        return contatto;
    }

    public void setContatto(Contatto contatto) {
        this.contatto = contatto;
    }

    @Override
    public String toString() {
        return  "via: " + via +
                ", civico: " + civico +
                ", cap: " + cap +
                ", comune: " + comune +
                ", provincia: " + provincia + '\n'
                ;
    }
}
