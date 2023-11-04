package org.caveman.util;

import java.util.Arrays;

public class ByteHelper {

    /**
     * Searches for a CRLF terminated substring within the provided byte array,
     * starting from the specified index. If a CRLF terminated substring is found,
     * it returns a new byte array containing the bytes from the starting index
     * up to and including the CRLF sequence. If no such sequence is found, the
     * method returns {@code null}.
     *
     * @param array The byte array to be searched for a CRLF terminated substring.
     * @param from  The starting index from which to search for a CRLF terminated substring within the array.
     *              This value must be non-negative and less than or equal to the length of the array.
     * @return A byte array containing the CRLF terminated substring starting from the specified index,
     *         or {@code null} if no CRLF sequence is found in the remainder of the array.
     */
    public static byte[] getCRLFTerminatedString(byte[] array, int from) {
        for (int i = from; i < array.length - 1; i++)
            if (array[i] == '\r' && array[i + 1] == '\n')
                return Arrays.copyOfRange(array, from, i + 2);

        return null;
    }
}
