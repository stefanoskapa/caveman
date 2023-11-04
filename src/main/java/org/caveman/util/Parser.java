package org.caveman.util;

import org.caveman.exception.VersionStringException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


public class Parser {

    private static final byte[] PROTOVERSION = "SSH-2.0-".getBytes(StandardCharsets.US_ASCII);
    private static final byte[] PROTOCOL = "SSH-".getBytes(StandardCharsets.US_ASCII);


    /**
     * Extracts the protocol version string (V_S) from the given input array. The
     * protocol version string is expected to be a CRLF-terminated string starting
     * with the protocol identifier. This method checks for the correct format,
     * validates that only one V_S is present, verifies the version is supported,
     * and ensures the V_S is not longer than 255 characters. If any of these
     * conditions are not met, a {@code VersionStringException} is thrown.
     *
     * @param input The input byte array to parse for the protocol version string.
     * @return A byte array containing the protocol version string.
     * @throws VersionStringException if the input is not in the correct format, if multiple V_S are found,
     *                                if no V_S is found, if the V_S is unsupported, or if the V_S exceeds
     *                                255 characters in length.
     */
    public static byte[] parseV_S(byte[] input) {

        byte[] V_S = null;

        int i = 0;
        while (i < input.length) {
            byte[] message = ByteHelper.getCRLFTerminatedString(input, i);

            if (message == null || V_S != null)
                throw new VersionStringException("Invalid Format");

            if (startsWithProtocol(message)) {
                if (!isSupportedVersion(message))
                    throw new VersionStringException("Unsupported SSH version");

                V_S = new byte[message.length];

                System.arraycopy(message, 0, V_S, 0, message.length);
            }
            i += message.length;
        }

        if (V_S == null)
            throw new VersionStringException("No V_S found");
        if (V_S.length > 255)
            throw new VersionStringException("V_S is longer than 255 characters");

        return V_S;
    }

    private static boolean isSupportedVersion(byte[] input) {
        return Arrays.equals(input, 0, PROTOVERSION.length,
                PROTOVERSION, 0, PROTOVERSION.length);
    }

    private static boolean startsWithProtocol(byte[] input) {
        return Arrays.equals(input, 0, PROTOCOL.length,
                PROTOCOL, 0, PROTOCOL.length);
    }

}
