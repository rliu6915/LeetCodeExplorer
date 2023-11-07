import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ExplorerUITest {
    @Test
    public void test() {
        InputStream input = new ByteArrayInputStream("1\ninteger\nno\nrandom\nq\n".getBytes());
        InputStream originalInput = System.in;
        System.setIn(input);
        ExplorerUI.main(new String[]{"test.csv"});
    }
}
