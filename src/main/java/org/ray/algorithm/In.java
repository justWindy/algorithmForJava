package org.ray.algorithm;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class In {

    private static final String  CHARSET_NAME       = "UTF-8";
    private static final Locale  LOCALE             = Locale.US;
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}");
    private static final Pattern EMPTY_PATTERN      = Pattern.compile("");
    private static final Pattern EVERYTHING_PATTERN = Pattern.compile("\\A");
    private Scanner scanner;

    public In() {
        scanner = new Scanner(new BufferedInputStream(System.in), CHARSET_NAME);
        scanner.useLocale(LOCALE);
    }

    public In(Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket argument is null");
        }

        try {
            InputStream inputStream = socket.getInputStream();
            scanner = new Scanner(new BufferedInputStream(inputStream), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + socket, ioe);
        }
    }

    public In(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url argument is null");
        }

        try {
            URLConnection site = url.openConnection();
            InputStream inputStream = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(inputStream), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + url, ioe);
        }
    }

    public In(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file argument is null");
        }

        try {
            FileInputStream inputStream = new FileInputStream(file);
            scanner = new Scanner(new BufferedInputStream(inputStream), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + file, ioe);
        }
    }

    public In(String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument is null");
        }

        try {
            File file = new File(name);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), CHARSET_NAME);
                scanner.useLocale(LOCALE);
                return;
            }

            URL url = getClass().getResource(name);

            if (url == null) {
                url = new URL(name);
            }

            URLConnection site = url.openConnection();
            InputStream is = site.getInputStream();
            scanner = new Scanner(new BufferedInputStream(is), CHARSET_NAME);
            scanner.useLocale(LOCALE);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + name, ioe);
        }
    }

    public In(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner argument is null");
        }

        this.scanner = scanner;
    }

    public boolean exists() {
        return scanner != null;
    }

    public boolean isEmpty() {
        return !scanner.hasNext();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public boolean hasNextChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        boolean result = scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public String readLine() {
        String line;
        try {
            line = scanner.nextLine();
        } catch (NoSuchElementException e) {
            line = null;
        }

        return line;
    }

    public char readChar() {
        scanner.useDelimiter(EMPTY_PATTERN);
        String ch = scanner.next();
        assert ch.length() == 1 : "Internal (Std)In.readChar() error! Please contact the author.";
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return ch.charAt(0);
    }

    public String readAll() {
        if (!scanner.hasNextLine()) {
            return "";
        }

        String result = scanner.useDelimiter(EVERYTHING_PATTERN).next();
        scanner.useDelimiter(WHITESPACE_PATTERN);

        return result;
    }

    public String readString() {
        return scanner.next();
    }

    public int readInt() {
        return scanner.nextInt();
    }

    public double readDouble() {
        return scanner.nextDouble();
    }

    public Float readFloat() {
        return scanner.nextFloat();
    }

    public long readLong() {
        return scanner.nextLong();
    }

    public short readShort() {
        return scanner.nextShort();
    }

    public byte readByte() {
        return scanner.nextByte();
    }

    public boolean readBoolean() {
        String s = readString();
        if ("true".equalsIgnoreCase(s)) {
            return true;
        }

        if ("false".equalsIgnoreCase(s)) {
            return false;
        }

        if ("1".equals(s)) {
            return true;
        }

        if ("0".equals(s)) {
            return false;
        }

        throw new InputMismatchException();
    }

    public String[] readAllStrings() {
        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length() > 0) {
            return tokens;
        }

        String[] decapitokens = new String[tokens.length - 1];
        for (int i = 0; i < tokens.length - 1; i++) {
            decapitokens[i] = tokens[i + 1];
        }

        return decapitokens;
    }

    public String[] readAllLines() {
        ArrayList<String> lines = new ArrayList<>();
        while (hasNextLine()) {
            lines.add(readLine());
        }

        return lines.toArray(new String[lines.size()]);
    }

    public int[] readAllInts() {
        String[] fields = readAllStrings();
        int[] values = new int[fields.length];
        for (int i = 0; i < fields.length; i++) {
            values[i] = Integer.parseInt(fields[i]);
        }

        return values;
    }

    public long[] readAllLongs() {
        String[] fields = readAllStrings();
        long[] values = new long[fields.length];

        for (int i = 0; i < fields.length; i++) {
            values[i] = Long.parseLong(fields[i]);
        }

        return values;
    }

    public double[] readAllDoubles() {
        String[] fields = readAllStrings();
        double[] values = new double[fields.length];

        for (int i = 0; i < fields.length; i++) {
            values[i] = Double.parseDouble(fields[i]);
        }

        return values;
    }

    public static int[] readInts(String filename) {
        return new In(filename).readAllInts();
    }

    public void close() {
        scanner.close();
    }
}
