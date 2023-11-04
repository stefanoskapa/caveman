package org.caveman.ssh;

import org.caveman.util.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Session implements AutoCloseable {


    private final Socket socket;
    private static final Logger log = Logger.getInstance();
    private static final int MAX_BYTES = 65536;
    public static final int BUFFER_SIZE = 4096;
    public static final int TIMEOUT_MULTIPLIER = 2;


    public Session(String hostname, int port) throws IOException {
        long startTime = System.currentTimeMillis();
        socket = new Socket(hostname, port);

        int latency = (int) (System.currentTimeMillis() - startTime);
        socket.setSoTimeout(latency * TIMEOUT_MULTIPLIER);

        log.debug("Latency: " + latency + " ms");
        log.info("Connected to " + socket.getInetAddress().getHostAddress() + ":" + port);
    }

    /**
     * Writes the provided byte array to the socket's output stream.
     * <p>
     * This method sends the specified byte array {@code array} to
     * the connected socket.
     * </p>
     *
     * @param array The byte array containing the data to be sent.
     * @throws IOException If an I/O error occurs
     */
    public void write(byte[] array) throws IOException {
        var out = socket.getOutputStream();
        out.write(array);
        out.flush();
        log.debug("Sent " + array.length + " bytes");
    }

    /**
     * Reads data from the socket's input stream up to a maximum allowed limit.
     * <p>
     * This method reads data in chunks using a buffer of size {@code BUFFER_SIZE}.
     * The total bytes read will not exceed {@code MAX_BYTES}. If the incoming data
     * exceeds this limit, an {@code IOException} is thrown. If a
     * {@code SocketTimeoutException} occurs while reading, it is ignored, and the
     * method proceeds to return the data read so far.
     * </p>
     *
     * @return A byte array containing the data read from the socket's input stream.
     * @throws IOException If an I/O error occurs or if data exceeds {@code MAX_BYTES}.
     */
    public byte[] read() throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        var in = socket.getInputStream();
        byte[] buffer = new byte[BUFFER_SIZE];

        int bytesRead;
        int totalBytesRead = 0;

        try {
            while ((bytesRead = in.read(buffer)) != -1) {
                totalBytesRead += bytesRead;
                if (totalBytesRead > MAX_BYTES)
                    throw new IOException("Data exceeds maximum allowed limit");

                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (SocketTimeoutException ignored) {}

        byte[] result = outputStream.toByteArray();
        log.debug("Received " + result.length + " bytes");
        return result;
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }


}
