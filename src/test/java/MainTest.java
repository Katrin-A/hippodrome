import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

   @Test
   @Timeout(22)
   @Disabled("Performance Test")
   @DisplayName("When main is executed then it should finish within 22 seconds")
   void whenMainIsExecutedThenItShouldFinishWithin22Seconds() throws Exception {
       Main.main(new String[]{});
   }
}