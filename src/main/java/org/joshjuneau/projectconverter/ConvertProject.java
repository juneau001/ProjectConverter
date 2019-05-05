/*
 * This class accepts the directory path, then traverses each file looking for 
 * those ending in .java, and changes all occurrances of the text within those files.
 * Next, it searches for files ending in .xml, and changes all occurances of text
 * within those files.
 */
package org.joshjuneau.projectconverter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Juneau
 */
public class ConvertProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Charset charset = StandardCharsets.UTF_8;
        if (args[0] != null && args[1] != null && args[2] != null) {
            String changeFrom = args[1];
            String changeTo = args[2];
            try (Stream<Path> walk = Files.walk(Paths.get(args[0]))) {
                System.out.println("Arg1: " + args[0]);

                // Change all occurrences of javax in .java files
                List<String> javafiles = walk.map(x -> x.toString())
                        .filter(f -> f.endsWith(".java")).collect(Collectors.toList());

                
                if (javafiles != null) {
                    System.out.println("Java Files size:" + javafiles.size());

                    for (String file : javafiles) {
                        File currentFile = new File(file);
                        String content = new String(Files.readAllBytes(currentFile.toPath()), charset);
                        content = content.replaceAll(changeFrom, changeTo);
                        Files.write(currentFile.toPath(), content.getBytes(charset));
                    }
                }
            } catch (IOException ex) {
                System.out.println("Exception occurred while traversing files ending in .java: " + ex);
            }

            try (Stream<Path> walk = Files.walk(Paths.get(args[0]))) {
                List<String> xmlfiles = walk.map(x -> x.toString())
                        .filter(f -> f.endsWith(".xml")).collect(Collectors.toList());
                if (xmlfiles != null) {
                    System.out.println("XML Files size:" + xmlfiles.size());

                    for (String file : xmlfiles) {
                        File currentFile = new File(file);
                        String content = new String(Files.readAllBytes(currentFile.toPath()), charset);
                        content = content.replaceAll(changeFrom, changeTo);
                        Files.write(currentFile.toPath(), content.getBytes(charset));
                    }
                }

            } catch (IOException ex) {
                System.out.println("Exception occurred while traversing files ending in .xml: " + ex);
            }
        } else {
            System.out.println("Please provide path to directory containing source files");
        }

    }

}
