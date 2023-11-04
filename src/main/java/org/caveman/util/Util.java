package org.caveman.util;

public class Util {

    /**
     * Converts a byte array into a formatted hex dump string.
     *
     * Each line of the hex dump contains two parts:
     * 1. A sequence of hexadecimal values, each representing a byte from the input array.
     * 2. A sequence of characters, where printable ASCII characters are displayed as-is,
     *    and non-printable characters are replaced with a dot ('.').
     *
     * @param array input array
     * @param width Bytes per line
     * @return A formatted hex dump string representation of the input byte array.
     */
    public static String bytesToHexDump(byte[] array, int width) {
        var result = new StringBuilder();

        for (int i = 0, eol = 0; eol != array.length; i += width) {

            var sb = new StringBuilder();
            eol = i + width;

            for (int j = i; j < eol; j++)
                sb.append( (j < array.length ? String.format("%02x ", array[j]) : "   "));

            sb.append("\t");
            eol = Math.min(eol, array.length);
            for (int j = i; j < eol; j++)
                sb.append(isPrintable(array[j]) ? (char)array[j] :'.');

            result.append(sb).append("\n");
        }

        return result.toString();
    }

    public static void showHex(byte[] array) {
        System.out.print(bytesToHexDump(array, 16));
        System.out.println("(" + array.length + " bytes)");
    }

    private static boolean isPrintable(byte b) {
        return (b >= ' ' && b <= '~');
    }


}
