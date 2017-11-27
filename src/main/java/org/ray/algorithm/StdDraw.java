package org.ray.algorithm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.TreeSet;

public final class StdDraw implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    public static final Color BLACK = Color.BLACK;

    public static final Color BLUE = Color.BLUE;

    public static final Color CYAN = Color.CYAN;

    public static final Color DARK_GRAY = Color.DARK_GRAY;

    public static final Color GRAY = Color.GRAY;

    /**
     * The color light gray.
     */
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;

    /**
     * The color magenta.
     */
    public static final Color MAGENTA = Color.MAGENTA;

    /**
     * The color orange.
     */
    public static final Color ORANGE = Color.ORANGE;

    /**
     * The color pink.
     */
    public static final Color PINK = Color.PINK;

    /**
     * The color red.
     */
    public static final Color RED = Color.RED;

    /**
     * The color white.
     */
    public static final Color WHITE = Color.WHITE;

    /**
     * The color yellow.
     */
    public static final Color YELLOW = Color.YELLOW;

    /**
     * Shade of blue used in <em>Introduction to Programming in Java</em>.
     * It is Pantone 300U. The RGB values are approximately (9, 90, 166).
     */
    public static final Color BOOK_BLUE = new Color(9, 90, 166);

    /**
     * Shade of light blue used in <em>Introduction to Programming in Java</em>.
     * The RGB values are approximately (103, 198, 243).
     */
    public static final Color BOOK_LIGHT_BLUE = new Color(103, 198, 243);

    /**
     * Shade of red used in <em>Algorithms, 4th edition</em>.
     * It is Pantone 1805U. The RGB values are approximately (150, 35, 31).
     */
    public static final Color BOOK_RED = new Color(150, 35, 31);

    // default colors
    private static final Color  DEFAULT_PEN_COLOR   = BLACK;
    private static final Color  DEFAULT_CLEAR_COLOR = WHITE;
    // default canvas size is DEFAULT_SIZE-by-DEFAULT_SIZE
    private static final int    DEFAULT_SIZE        = 512;
    // default pen radius
    private static final double DEFAULT_PEN_RADIUS  = 0.002;
    // boundary of drawing canvas, 0% border
    // private static final double BORDER = 0.05;
    private static final double BORDER              = 0.00;
    private static final double DEFAULT_XMIN        = 0.0;
    private static final double DEFAULT_XMAX        = 1.0;
    private static final double DEFAULT_YMIN        = 0.0;
    private static final double DEFAULT_YMAX        = 1.0;
    // default font
    private static final Font   DEFAULT_FONT        = new Font("SansSerif", Font.PLAIN, 16);
    // current pen color
    private static Color penColor;
    private static int width  = DEFAULT_SIZE;
    private static int height = DEFAULT_SIZE;
    // current pen radius
    private static double penRadius;
    // show we draw immediately or wait until next show?
    private static boolean defer = false;
    private static double xmin, ymin, xmax, ymax;
    // for synchronization
    private static Object mouseLock = new Object();
    private static Object keyLock   = new Object();
    // current font
    private static Font font;

    // double buffered graphics
    private static BufferedImage offscreenImage, onscreenImage;
    private static Graphics2D offscreen, onscreen;

    // singleton for callbacks: avoids generation of extra .class files
    private static StdDraw std = new StdDraw();

    // the frame for drawing to the screen
    private static JFrame frame;

    // mouse state
    private static boolean mousePressed = false;
    private static double  mouseX       = 0;
    private static double  mouseY       = 0;

    // queue of typed key characters
    private static LinkedList<Character> keysTyped = new LinkedList<Character>();

    // set of key codes currently pressed down
    private static TreeSet<Integer> keysDown = new TreeSet<Integer>();

    // time in milliseconds (from currentTimeMillis()) when we can draw again
    // used to control the frame rate
    private static long nextDraw = -1;

    // static initializer
    static {
        init();
    }

    // singleton pattern: client can't instantiate
    private StdDraw() {
    }

    public static void setCanvasSize() {
        setCanvasSize(DEFAULT_SIZE, DEFAULT_SIZE);
    }

    public static void setCanvasSize(int canvasWidth, int canvasHeight) {
        if (canvasWidth <= 0 || canvasHeight <= 0) {
            throw new IllegalArgumentException("width and height must be positive");
        }
        width = canvasWidth;
        height = canvasHeight;
        init();
    }

    private static void init() {
        if (frame != null) {
            frame.setVisible(false);
        }

        frame = new JFrame();
        offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        onscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        offscreen = offscreenImage.createGraphics();
        onscreen = onscreenImage.createGraphics();
        setXscale();
        setYscale();
        offscreen.setColor(DEFAULT_CLEAR_COLOR);
        offscreen.fillRect(0, 0, width, height);
        setPenColor();
        setPenRadius();
        setFont();
        clear();

        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                                                  RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        offscreen.addRenderingHints(hints);

        ImageIcon imageIcon = new ImageIcon(onscreenImage);
        JLabel draw = new JLabel(imageIcon);

        draw.addMouseListener(std);
        draw.addMouseMotionListener(std);

        frame.setContentPane(draw);
        frame.addKeyListener(std);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setTitle("Standard Draw");
        frame.setJMenuBar(createMenuBar());
        frame.pack();
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    public static void clear() {
        clear(DEFAULT_CLEAR_COLOR);
    }

    public static void clear(Color color) {
        offscreen.setColor(color);
        offscreen.fillRect(0, 0, width, height);
        offscreen.setColor(penColor);
        draw();
    }

    public static void show() {
        onscreen.drawImage(offscreenImage, 0, 0, null);
        frame.repaint();
    }

    private static void draw() {
        if (!defer) {
            show();
        }
    }

    public static void setXscale() {
        setXscale(DEFAULT_XMIN, DEFAULT_XMAX);
    }

    public static void setYscale() {
        setYscale(DEFAULT_YMIN, DEFAULT_YMAX);
    }

    public static void setScale() {
        setXscale();
        setYscale();
    }

    public static void setScale(double min, double max) {
        double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        synchronized (mouseLock) {
            xmin = min - BORDER * size;
            xmax = max + BORDER * size;
            ymin = min - BORDER * size;
            ymax = max + BORDER * size;
        }
    }

    private static double scaleX(double x) {
        return width * (x - xmin) / (xmax - xmin);
    }

    private static double scaleY(double y) {
        return height * (ymax - y) / (ymax - ymin);
    }

    private static double factorX(double w) {
        return w * width / Math.abs(xmax - xmin);
    }

    private static double factorY(double h) {
        return h * height / Math.abs(ymax - ymin);
    }

    private static double userX(double x) {
        return xmin + x * (xmax - xmin) / width;
    }

    private static double userY(double y) {
        return ymax - y * (ymax - ymin) / height;
    }

    public static void setXscale(double min, double max) {
        double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        synchronized (mouseLock) {
            xmin = min - BORDER * size;
            xmax = max + BORDER * size;
        }
    }

    public static Color getPenColor() {
        return penColor;
    }

    public static void setPenColor(Color color) {
        if (color == null) {
            throw new IllegalArgumentException();
        }
        penColor = color;
        offscreen.setColor(penColor);
    }

    public static void setYscale(double min, double max) {
        double size = max - min;
        if (size == 0.0) {
            throw new IllegalArgumentException("the min and max are the same");
        }
        synchronized (mouseLock) {
            ymin = min - BORDER * size;
            ymax = max + BORDER * size;
        }
    }

    public static void setPenColor() {
        setPenColor(DEFAULT_PEN_COLOR);
    }

    public static double getPenRadius() {
        return penRadius;
    }

    public static void setPenRadius(double radius) {
        if (!(radius >= 0)) {
            throw new IllegalArgumentException("pen radius must be non-negative");
        }
        penRadius = radius;
        float scaledPenRadius = (float) (radius * DEFAULT_SIZE);
        BasicStroke stroke = new BasicStroke(scaledPenRadius, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        offscreen.setStroke(stroke);
    }

    public static void setPenRadius() {

    }

    public static Font getFont() {
        return font;
    }

    public static void setFont(Font font) {
        if (font == null) {
            throw new IllegalArgumentException();
        }
        StdDraw.font = font;
    }

    public static void setFont() {
        setFont(DEFAULT_FONT);
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("Save...");
        menuItem.addActionListener(std);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                                       Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        menu.add(menuItem);
        return menuBar;
    }

    public static void line(double x0, double y0, double x1, double y1) {
        offscreen.draw(new Line2D.Double(scaleX(x0), scaleY(y0), scaleX(x1), scaleY(y1)));
        draw();
    }

    private static void pixel(double x, double y) {
        offscreen.fillRect((int) Math.round(scaleX(x)), (int) Math.round(scaleY(y)), 1, 1);
    }

    public static void point(double x, double y) {
        double xs = scaleX(x);
        double ys = scaleY(y);
        double r = penRadius;

        float scalePenRadius = (float) (r * DEFAULT_SIZE);

        if (scalePenRadius <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Ellipse2D.Double(xs - scalePenRadius / 2, ys - scalePenRadius / 2, scalePenRadius,
                                                scalePenRadius));
        }
        draw();
    }

    public static void circle(double x, double y, double radius) {
        if (!(radius >= 0)) {
            throw new IllegalArgumentException("radius must be no-negative");
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * radius);
        double hs = factorY(2 * radius);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    public static void filledCircle(double x, double y, double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("radius must be non-negative");
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * radius);
        double hs = factorY(2 * radius);

        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();

    }

    public static void ellipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        if (semiMajorAxis < 0) {
            throw new IllegalArgumentException("ellipse semimajor axis must be non-negative");
        }

        if (semiMinorAxis < 0) {
            throw new IllegalArgumentException("ellipse semiminor axis must be non-negative");
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * semiMajorAxis);
        double hs = factorY(2 * semiMinorAxis);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();

    }

    public static void filledEllipse(double x, double y, double semiMajorAxis, double semiMinorAxis) {
        if (!(semiMajorAxis >= 0)) {
            throw new IllegalArgumentException("ellipse semimajor axis must be non-negative");
        }

        if (!(semiMinorAxis >= 0)) {
            throw new IllegalArgumentException("ellipse semiminor axis must be non-negative");
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * semiMajorAxis);
        double hs = factorY(2 * semiMinorAxis);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Ellipse2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();

    }

    public static void arc(double x, double y, double radius, double angle1, double angle2) {
        if (radius < 0) {
            throw new IllegalArgumentException("half length must be non-negative");
        }
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * radius);
        double hs = factorY(2 * radius);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.draw(new Arc2D.Double(xs - ws / 2, ys - hs / 2, ws, hs, angle1, angle2 - angle1, Arc2D.OPEN));
        }
        draw();

    }

    public static void square(double x, double y, double halfLength) {
        if (halfLength < 0) {
            throw new IllegalArgumentException("half length must be non-negative");
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfLength);
        double hs = factorY(2 * halfLength);

        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    public static void filledSquare(double x, double y, double halfLength) {
        if (halfLength < 0) {
            throw new IllegalArgumentException("half length must be non-negative");
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfLength);
        double hs = factorY(2 * halfLength);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    public static void rectangle(double x, double y, double halfWidth, double halfHeight) {
        if (halfWidth < 0) {
            throw new IllegalArgumentException("half width must be non-negative");
        }

        if (halfHeight < 0) {
            throw new IllegalArgumentException("half height must be non-negative");
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfWidth);
        double hs = factorY(2 * halfHeight);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.draw(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();

    }

    public static void filledRectangle(double x, double y, double halfWidth, double halfHeight) {
        if (halfWidth < 0) {
            throw new IllegalArgumentException("half width must be non-negative");
        }

        if (halfHeight < 0) {
            throw new IllegalArgumentException("half height must be non-negative");
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(2 * halfWidth);
        double hs = factorY(2 * halfHeight);
        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.fill(new Rectangle2D.Double(xs - ws / 2, ys - hs / 2, ws, hs));
        }
        draw();
    }

    public static void polygon(double[] x, double[] y) {
        if (x == null) {
            throw new IllegalArgumentException();
        }

        if (y == null) {
            throw new IllegalArgumentException();
        }

        int n1 = x.length;
        int n2 = y.length;

        if (n1 != n2) {
            throw new IllegalArgumentException("arrays must be of the same length");
        }
        int n = n1;
        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++) {
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        }
        path.closePath();
        offscreen.draw(path);
        draw();

    }

    public static void filledPolygon(double[] x, double[] y) {
        if (x == null) {
            throw new IllegalArgumentException();
        }

        if (y == null) {
            throw new IllegalArgumentException();
        }

        int n1 = x.length;
        int n2 = y.length;
        if (n1 != n2) {
            throw new IllegalArgumentException("arrays must be of the same length");
        }
        int n = n1;
        GeneralPath path = new GeneralPath();
        path.moveTo((float) scaleX(x[0]), (float) scaleY(y[0]));
        for (int i = 0; i < n; i++) {
            path.lineTo((float) scaleX(x[i]), (float) scaleY(y[i]));
        }
        path.closePath();
        offscreen.fill(path);
        draw();
    }

    private static Image getImage(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        ImageIcon imageIcon = new ImageIcon(filename);
        if ((imageIcon == null) || (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            try {
                URL url = new URL(filename);
                imageIcon = new ImageIcon(url);
            } catch (MalformedURLException e) {

            }
        }

        if ((imageIcon == null) || (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = StdDraw.class.getResource(filename);
            if (url != null) {
                imageIcon = new ImageIcon(url);
            }
        }

        if ((imageIcon == null) || (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
            URL url = StdDraw.class.getResource("/" + filename);
            if (url == null) {
                throw new IllegalArgumentException("image " + filename + " not found");
            }
            imageIcon = new ImageIcon(url);
        }

        return imageIcon.getImage();
    }

    public static void picture(double x, double y, String filename) {
        Image image = getImage(filename);

        double xs = scaleX(x);
        double ys = scaleY(y);

        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }
        offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math.round(ys - hs / 2.0), null);
        draw();
    }

    public static void picture(double x, double y, String filename, double degrees) {
        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);

        int ws = image.getWidth(null);
        int hs = image.getHeight(null);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }

        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0), (int) Math.round(ys - hs / 2), null);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);

        draw();
    }

    public static void picture(double x, double y, String filename, double scaleWidth, double scaleHeight) {
        Image image = getImage(filename);
        if (scaleWidth < 0) {
            throw new IllegalArgumentException("width is negative: " + scaleWidth);
        }

        if (scaleHeight < 0) {
            throw new IllegalArgumentException("height is negative: " + scaleHeight);
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaleWidth);
        double hs = factorY(scaleHeight);

        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }

        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        } else {
            offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0),
                                (int) Math.round(ys - hs / 2.0),
                                (int) Math.round(ws),
                                (int) Math.round(hs), null);
        }
        draw();
    }

    public static void picture(double x, double y, String filename, double scaledWidth, double scaledHeight,
                               double degrees) {
        if (scaledWidth < 0) {
            throw new IllegalArgumentException("width is negative: " + scaledWidth);
        }

        if (scaledHeight < 0) {
            throw new IllegalArgumentException("height is negative: " + scaledHeight);
        }

        Image image = getImage(filename);
        double xs = scaleX(x);
        double ys = scaleY(y);
        double ws = factorX(scaledWidth);
        double hs = factorY(scaledHeight);
        if (ws < 0 || hs < 0) {
            throw new IllegalArgumentException("image " + filename + " is corrupt");
        }

        if (ws <= 1 && hs <= 1) {
            pixel(x, y);
        }

        offscreen.rotate(Math.toRadians(-degrees), xs, ys);
        offscreen.drawImage(image, (int) Math.round(xs - ws / 2.0),
                            (int) Math.round(ys - hs / 2.0),
                            (int) Math.round(ws),
                            (int) Math.round(hs), null);
        offscreen.rotate(Math.toRadians(+degrees), xs, ys);
        draw();
    }

    public static void text(double x, double y, String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);
        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) (xs - ws / 2.0), (float) (ys + hs));
        draw();
    }

    public static void text(double x, double y, String text, double degress) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        double xs = scaleX(x);
        double ys = scaleY(y);
        offscreen.rotate(Math.toRadians(-degress), xs, ys);
        text(x, y, text);
        offscreen.rotate(Math.toRadians(+degress), xs, ys);
    }

    public static void textLeft(double x, double y, String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);

        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) xs, (float) (ys + hs));
        draw();
    }

    public static void textRight(double x, double y, String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        offscreen.setFont(font);
        FontMetrics metrics = offscreen.getFontMetrics();
        double xs = scaleX(x);
        double ys = scaleY(y);

        int ws = metrics.stringWidth(text);
        int hs = metrics.getDescent();
        offscreen.drawString(text, (float) (xs - ws), (float) (ys + hs));
        draw();
    }

    public static void pause(int t) {
        long millis = System.currentTimeMillis();
        if (millis < nextDraw) {
            try {
                Thread.sleep(nextDraw - millis);
            } catch (InterruptedException e) {
                System.out.println("Error sleeping");
            }
            millis = nextDraw;
        }

        nextDraw = millis + t;
    }

    public static void enableDoubleBuffering() {
        defer = true;
    }

    public static void disableDoubleBuffering() {
        defer = false;
    }

    public static void save(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }

        File file = new File(filename);
        String suffix = filename.substring(filename.lastIndexOf('.') + 1);

        if ("png".equalsIgnoreCase(suffix)) {
            try {
                ImageIO.write(onscreenImage, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("jpg".equalsIgnoreCase(suffix)) {
            WritableRaster raster = onscreenImage.getRaster();
            WritableRaster newRaster = raster.createWritableChild(0, 0, width, height, 0, 0, new int[] { 0, 1, 2 });
            DirectColorModel directColorModel = (DirectColorModel) onscreenImage.getColorModel();
            DirectColorModel newCM = new DirectColorModel(directColorModel.getPixelSize(),
                                                          directColorModel.getRedMask(),
                                                          directColorModel.getGreenMask(),
                                                          directColorModel.getBlueMask());
            BufferedImage rgbBuffer = new BufferedImage(newCM, newRaster, false, null);
            try {
                ImageIO.write(rgbBuffer, suffix, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid image file type: " + suffix);
        }
    }

    public void actionPerformed(ActionEvent e) {
        FileDialog chooser = new FileDialog(StdDraw.frame, "Use a .png or .jpg extension", FileDialog.SAVE);

    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
