import entity.Contatto;
import entity.Indirizzi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import validator.Validator;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing per i metodi della classe Contatto")

public class TestContatto {



    @Test
    @DisplayName("Test del metodo per l'inserimento del nome")
    public void testAddName() {
        // inserimento di un valore valido
        assertEquals("Jason", Validator.addName("Jason"));

        //inserimento del nome con spazi prima e dopo
        assertEquals("Jason", Validator.addName("    Jason     "));

        //inserimento del nome con lettere minucole e maiuscole
        assertEquals("Jason", Validator.addName("JaSoN     "));

        //inserimento del nome con la prima lettere minucola
        assertEquals("Jason", Validator.addName("    jason     "));

        // inserimento del nome con spazi interni
        assertNotEquals("Jason", Validator.addName("Jas on"));

        // inserimento del nome con numeri
        assertNotEquals("Jason", Validator.addName("Jaso2n"));

        // inserimento del nome con caratteri speciali
        assertNotEquals("Jason", Validator.addName("Jas@n"));

        // inserimento di una stringa vuota
        assertNotEquals("Jason", Validator.addName(""));
    }

    @Test
    @DisplayName("Test per la gestione della relazione delle entità Contatto e Indirizzi")
    public void testAddIndirizzo() {

        // Crea il contatto
        Contatto contatto = new Contatto(1L, "Jason", "Taylor", "jason.taylor@test.it", "3332221110", new HashSet<>());
        // Crea l'indirizzo
        Indirizzi indirizzo = new Indirizzi(1L, "Corso Roma", "16", "50100", "Roma", "RM");
        Indirizzi indirizzo2 = new Indirizzi(2L, "Via Milano", "22", "20100", "Milano", "MI");

        // Aggiungi l'indirizzo al contatto
        contatto.addIndirizzo(indirizzo);

        // Verifica che l'indirizzo sia stato aggiunto
        assertEquals(1, contatto.getIndirizzi().size());
        assertTrue(contatto.getIndirizzi().contains(indirizzo));

        // Test se inserisco per errore lo stesso indirizzo un seconda volta
        contatto.addIndirizzo(indirizzo);
        assertEquals(1, contatto.getIndirizzi().size());

        // Test dopo l'aggiunto di un secondo indirizzo
        contatto.addIndirizzo(indirizzo2);
        assertEquals(2, contatto.getIndirizzi().size());

        // Verifica che il contatto sia stato impostato sull'indirizzo
        assertEquals(contatto, indirizzo.getContatto());
    }

}
