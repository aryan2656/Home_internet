//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//import java.util.List;
//
//class SpellCheckingServiceTest {
//
//    @Test
//    void testEditDistance() {
//        assertEquals(3, SpellCheckingService.EditDistance("example", "samples"));
//        assertEquals(0, SpellCheckingService.EditDistance("hello", "hello"));
//    }
//
//    @Test
//    void testGetSpellSuggestions() {
//        SpellCheckingService service = new SpellCheckingService();
//        List<String> dictionary = List.of("hello", "help", "hero", "world");
//        List<String> suggestions = service.getSpellSuggestions("helo", dictionary);
//        assertTrue(suggestions.contains("hello"));
//        assertTrue(suggestions.contains("help"));
//    }
//}
