package org.caveman.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ByteHelperTest {

    @Test
    void getCRLFTerminatedString_ReturnsSubArray_WhenCRLFIsPresent() {
        byte[] input = "Hello, world!\r\nThis is a test.".getBytes();
        byte[] expected = "Hello, world!\r\n".getBytes();
        assertArrayEquals(expected, ByteHelper.getCRLFTerminatedString(input, 0));
    }

    @Test
    void getCRLFTerminatedString_ReturnsNull_WhenCRLFIsNotPresent() {
        byte[] input = "Hello, world! This is a test.".getBytes();
        assertNull(ByteHelper.getCRLFTerminatedString(input, 0));
    }

    @Test
    void getCRLFTerminatedString_ReturnsSubArray_WhenCRLFIsAtEnd() {
        byte[] input = "Hello, world!\r\n".getBytes();
        byte[] expected = input;
        assertArrayEquals(expected, ByteHelper.getCRLFTerminatedString(input, 0));
    }

    @Test
    void getCRLFTerminatedString_ReturnsSubArray_FromMiddleOfString() {
        byte[] input = "Hello, world!\r\nThis is a test.\r\n".getBytes();
        byte[] expected = "This is a test.\r\n".getBytes();
        assertArrayEquals(expected, ByteHelper.getCRLFTerminatedString(input, 15));
    }


    @Test
    void getCRLFTerminatedString_ThrowsException_WhenStartingIndexIsNegative() {
        byte[] input = "Hello, world!\r\nThis is a test.".getBytes();
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> ByteHelper.getCRLFTerminatedString(input, -1));
    }

    // Optional: testing edge cases
    @Test
    void getCRLFTerminatedString_ReturnsEmptyArray_WhenCRLFFollowsImmediatelyAfterFromIndex() {
        byte[] input = "\r\nHello, world!".getBytes();
        byte[] expected = "\r\n".getBytes();
        assertArrayEquals(expected, ByteHelper.getCRLFTerminatedString(input, 0));
    }

    @Test
    void getCRLFTerminatedString_ReturnsNull_WhenInputIsEmpty() {
        byte[] input = new byte[0];
        assertNull(ByteHelper.getCRLFTerminatedString(input, 0));
    }

    @Test
    void getCRLFTerminatedString_ThrowsException_WhenInputIsJustCRLF() {
        byte[] input = "\r\n".getBytes();
        byte[] expected = "\r\n".getBytes();
        assertArrayEquals(expected, ByteHelper.getCRLFTerminatedString(input, 0));
    }

}