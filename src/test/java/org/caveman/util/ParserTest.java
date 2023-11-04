package org.caveman.util;

import org.caveman.exception.VersionStringException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parseV_S_ThrowsVersionStringException_WhenNoCRLFFound() {
        byte[] input = "SSH-2.0-ExampleServerWithoutCRLF".getBytes();
        assertThrows(VersionStringException.class, () ->Parser.parseV_S(input));
    }

    @Test
    void parseV_S_ThrowsVersionStringException_WhenSingleVSFollowedByIllegalString() {
        byte[] input = "SSH-2.0-ExampleServer\r\nExtraData".getBytes();
        assertThrows(VersionStringException.class, () ->Parser.parseV_S(input));
    }

    @Test
    void parseV_S_ThrowsVersionStringException_WhenMultipleVSFound() {
        byte[] input = "SSH-2.0-ExampleServer\r\nSSH-AnotherServer\r\n".getBytes();
        assertThrows(VersionStringException.class, () -> Parser.parseV_S(input));
    }

    @Test
    void parseV_S_ThrowsVersionStringException_WhenNoVSFound() {
        byte[] input = "InvalidData\r\n".getBytes();
        assertThrows(VersionStringException.class, () -> Parser.parseV_S(input));
    }

    @Test
    void parseV_S_ReturnsVS_WhenMessageBeforeVS() {
        byte[] input = "Hello World\r\nSSH-2.0-AnotherServer\r\n".getBytes();
        byte[] actual = Parser.parseV_S(input);
        byte[] expected = "SSH-2.0-AnotherServer\r\n".getBytes();
        assertArrayEquals(expected,actual);
    }

    @Test
    void parseV_S_ThrowsVersionStringException_WhenMessageAfterVS() {
        byte[] input = "SSH-2.0-AnotherServer\r\nHello World\r\n".getBytes();
        assertThrows(VersionStringException.class, () -> Parser.parseV_S(input));
    }

    @Test
    void parseV_S_ThrowsException_WhenVSIsTooLong() {
        byte[] input = ("SSH-2.0-" + "A".repeat(248) + "\r\n").getBytes();

        assertThrows(VersionStringException.class, () -> Parser.parseV_S(input),
                "Should throw VersionStringException because V_S is longer than 255 characters");
    }

    @Test
    void parseV_S_ThrowsVersionStringException_WhenArrayEmpty() {
        byte[] input = new byte[0];
        assertThrows(VersionStringException.class, () ->Parser.parseV_S(input));
    }


}