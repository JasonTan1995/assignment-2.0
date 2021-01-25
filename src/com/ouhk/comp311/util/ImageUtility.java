package com.ouhk.comp311.util;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.*;

/**
 * Example usage:
 * COMP S311 Assignment 02 (Autumn 2020)
 * 8
 * ImageUtility.writePNGCustomData("input.png", "output.png",
 * "comment", "Test 123!!".getBytes());
 * byte[] bytes = ImageUtility.readPNGCustomData("output.png",
 * "comment");
 * System.out.println(new String(bytes)); // "Test 123!!"
 * <p>
 * Ref: https://stackoverflow.com/questions/6495518/writing-imagemetadata-in-java-preferably-png
 */
public class ImageUtility {

    public static void writePNGCustomData(String inputFile,
                                          String outputFile, String key, byte[] data) throws Exception {
        BufferedImage bufferedImage = ImageIO.read(new File(inputFile));
        ImageWriter writer =
                ImageIO.getImageWritersByFormatName("png").next();
        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier
                .createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
        byte[] encoded = Base64.getEncoder().encode(data);
        IIOMetadata metadata =
                writer.getDefaultImageMetadata(typeSpecifier,
                        writeParam);
        IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
        textEntry.setAttribute("keyword", key);
        textEntry.setAttribute("value", new String(encoded));
        IIOMetadataNode text = new IIOMetadataNode("tEXt");
        text.appendChild(textEntry);
        IIOMetadataNode root = new
                IIOMetadataNode("javax_imageio_png_1.0");
        root.appendChild(text);
        metadata.mergeTree("javax_imageio_png_1.0", root);
        try (OutputStream out = new FileOutputStream(outputFile);
             ImageOutputStream stream =
                     ImageIO.createImageOutputStream(out)) {
            writer.setOutput(stream);
            writer.write(metadata,
                    new IIOImage(bufferedImage, null, metadata), writeParam);
        }
    }

    public static byte[] readPNGCustomData(String inputFile, String key)
            throws IOException {
        ImageReader imageReader =
                ImageIO.getImageReadersByFormatName("png")
                        .next();
        IIOMetadata metadata;

        try (InputStream in = new FileInputStream(inputFile)) {
            imageReader.setInput(ImageIO.createImageInputStream(in),
                    true);
            metadata = imageReader.getImageMetadata(0);
        }
        List<Node> tEXtNodes = findNodesWithName("tEXtEntry",

                metadata.getAsTree(metadata.getNativeMetadataFormatName()));
        for (Node node : tEXtNodes) {
            final NamedNodeMap attributes = node.getAttributes();
            String keyword = attributes.getNamedItem("keyword").getNodeValue();
            String value =
                    attributes.getNamedItem("value").getNodeValue();
            if (key.equals(keyword)) {
                byte[] decoded = Base64.getDecoder().decode(value);
                return decoded;
            }
        }
        return null;
    }

    private static List<Node> findNodesWithName(String name, Node root) {
        List<Node> found = new ArrayList<>();
        Node n = root.getFirstChild();
        while (n != null) {
            if (n.getNodeName().equals(name)) {
                found.add(n);
            }
            found.addAll(findNodesWithName(name, n));
            n = n.getNextSibling();
        }
        return found;
    }

    public static void main(String[] args) throws Exception {
        ImageUtility.writePNGCustomData("input.png", "output.png",
                "comment", "Test 123!!".getBytes());
        byte[] bytes = ImageUtility.readPNGCustomData("output.png",
                "comment");
        System.out.println(new String(bytes)); // "Test 123!!"
    }
}