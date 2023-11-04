package org.caveman;

import org.caveman.ssh.Session;
import org.caveman.ssh.SshIO;
import org.caveman.util.Logger;
import org.caveman.util.Util;

public class Client {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 22;
    private final static Logger log = Logger.getInstance();


    public static void main(String[] args) {
        log.setLogLevels(Logger.Level.ALL);

        try (var session = new Session(HOSTNAME, PORT)) {
            SshIO ssh = new SshIO(session);

            byte[] input = ssh.readV_S();
            Util.showHex(input);

            ssh.sendV_C();

            input = session.read();
            Util.showHex(input);

        } catch (Exception e) {
            log.error(e.toString());
        }

    }


}
