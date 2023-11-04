package org.caveman.ssh;

import org.caveman.util.Parser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class SshIO {

    private final Session session;
    private static final byte[] V_C = "SSH-2.0-CavemanSSH_0.0\r\n".getBytes(StandardCharsets.US_ASCII);


    public SshIO(Session session) {
        this.session = session;
    }



    public byte[] readV_S() throws IOException {
        byte[] input = session.read();
        return Parser.parseV_S(input);
    }

    public void sendV_C() throws IOException {
        session.write(V_C);
    }

}
