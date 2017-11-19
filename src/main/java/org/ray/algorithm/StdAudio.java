package org.ray.algorithm;

import sun.plugin2.gluegen.runtime.BufferFactory;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public final class StdAudio {

    public static final int SAMPLE_RATE = 44100;

    private static final int    BYTES_PER_SAMPLE   = 2;
    private static final int    BITS_PER_SMAPLE    = 16;
    private static final double MAX_16_BIT         = Short.MAX_VALUE;
    private static final int    SAMPLE_BUFFER_SIZE = 4096;

    private static SourceDataLine line;
    private static byte[]         buffer;
    private static int bufferSize = 0;

    private StdAudio() {

    }

    static {
        init();
    }

    private static void init() {
        try {
            AudioFormat format = new AudioFormat((float) SAMPLE_RATE, BITS_PER_SMAPLE, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format, SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE);

            buffer = new byte[SAMPLE_BUFFER_SIZE * BYTES_PER_SAMPLE / 3];
        } catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }

        line.start();
    }

    public static void close() {
        line.drain();
        line.stop();
    }

    public static void play(double sample) {
        if (Double.isNaN(sample)) {
            throw new IllegalArgumentException("sample is NaN");
        }

        if (sample < -1.0) {
            sample = -1.0;
        }

        if (sample > +1.0) {
            sample = +1.0;
        }

        short s = (short) (MAX_16_BIT * sample);
        buffer[bufferSize++] = (byte) s;
        buffer[bufferSize++] = (byte) (s >> 8);

        if (bufferSize >= buffer.length) {
            line.write(buffer, 0, buffer.length);
            bufferSize = 0;
        }
    }

    public static void play(double[] samples) {
        if (samples == null) {
            throw new IllegalArgumentException("argument to play() is null");
        }
        for (int i = 0; i < samples.length; i++) {
            play(samples[i]);
        }
    }

    public static double[] read(String filename) {
        byte[] data = readByte(filename);
        int n = data.length;
        double[] d = new double[n / 2];
        for (int i = 0; i < n / 2; i++) {
            d[i] = ((short) (((data[2 * i + 1] & 0xFF) << 8) + (data[2 * i] & 0xFF))) / ((double) MAX_16_BIT);
        }
        return d;
    }

    private static byte[] readByte(String filename) {
        byte[] data = null;
        AudioInputStream audioInputStream = null;
        try {
            File file = new File(filename);
            if (file.exists()) {
                audioInputStream = AudioSystem.getAudioInputStream(file);
                int bytesToRead = audioInputStream.available();
                data = new byte[bytesToRead];
                int bytesRead = audioInputStream.read(data);
                if (bytesToRead != bytesRead) {
                    throw new IllegalStateException("read only " + bytesRead + " of " + bytesToRead + " bytes");
                }
            } else {
                URL url = StdAudio.class.getResource(filename);
                audioInputStream = AudioSystem.getAudioInputStream(url);
                int bytesToRead = audioInputStream.available();
                data = new byte[bytesToRead];
                int bytesRead = audioInputStream.read(data);
                if (bytesRead != bytesRead) {
                    throw new IllegalStateException("read only " + bytesRead + " of " + bytesToRead + " bytes");
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("could not read '" + filename + "'", e);
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("unsupported audio format: '" + filename + "'", e);
        }

        return data;
    }

    public static void save(String filename, double[] samples) {
        if (samples == null) {
            throw new IllegalArgumentException("samples[] is null");
        }

        AudioFormat format = new AudioFormat(SAMPLE_RATE, 16, 1, true, false);
        byte[] data = new byte[2 * samples.length];
        for (int i = 0; i < samples.length; i++) {
            int temp = (short) (samples[i] * MAX_16_BIT);
            data[2 * i + 0] = (byte) temp;
            data[2 * i + 1] = (byte) (temp >> 8);
        }

        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            AudioInputStream audioInputStream = new AudioInputStream(byteArrayInputStream, format, samples.length);
            if (filename.endsWith(".wav") || filename.endsWith(".WAV")) {
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(filename));
            } else if (filename.endsWith(".au") || filename.endsWith(".AU")) {
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.AU, new File(filename));
            } else {
                throw new IllegalArgumentException("unsupported audio format: '" + filename + "'");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("unable to save file '" + filename + "'", e);
        }
    }

    public static synchronized void play(final String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        InputStream inputStream = StdAudio.class.getResourceAsStream(filename);
        if (inputStream == null) {
            throw new IllegalArgumentException("could not read '" + filename + "'");
        }

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            new Thread(new Runnable() {

                public void run() {
                    stream(filename);
                }
            }).start();
        } catch (UnsupportedAudioFileException e) {
            return;
        } catch (IOException e) {
            throw new IllegalArgumentException("could not play '" + filename + "'", e);
        }

    }

    private static void playApplet(String filename) {
        URL url = null;
        try {
            File file = new File(filename);
            if (file.canRead()) {
                url = file.toURI().toURL();
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("could not play '" + filename + "'", e);
        }

        if (url == null) {
            throw new IllegalArgumentException("could not play '" + filename + "'");
        }

        AudioClip clip = Applet.newAudioClip(url);
        clip.play();
    }

    private static void stream(String filename) {
        SourceDataLine line = null;
        int BUFFER_SIZE = 4096;

        try {
            InputStream inputStream = StdAudio.class.getResourceAsStream(filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            AudioFormat audioFormat = audioInputStream.getFormat();

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();
            byte[] samples = new byte[BUFFER_SIZE];
            int count = 0;
            while ((count = audioInputStream.read(samples, 0, BUFFER_SIZE)) != -1) {
                line.write(samples, 0, count);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            if (line != null) {
                line.drain();
                line.close();
            }
        }
    }

    public static synchronized void loop(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        try {
            Clip clip = AudioSystem.getClip();
            InputStream inputStream = StdAudio.class.getResourceAsStream(filename);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException e) {
            throw new IllegalArgumentException("unsupported audio format: '" + filename + "'", e);
        } catch (LineUnavailableException e) {
            throw new IllegalArgumentException("could not play '" + filename + "'", e);
        } catch (IOException e) {
            throw new IllegalArgumentException("could not play '" + filename + "'", e);
        }

    }

    private static double[] note(double hz, double duration, double amplitude) {
        int n = (int) (StdAudio.SAMPLE_RATE * duration);
        double[] a = new double[n + 1];
        for (int i = 0; i <= n; i++) {
            a[i] = amplitude * Math.sin(2 * Math.PI * i * hz / StdAudio.SAMPLE_RATE);
        }

        return a;
    }
}
