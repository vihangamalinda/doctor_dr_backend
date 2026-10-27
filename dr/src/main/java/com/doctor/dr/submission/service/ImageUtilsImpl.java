package com.doctor.dr.submission.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
@Service
public class ImageUtilsImpl implements ImageUtils{

    public  String prepareImageForJson(MultipartFile file) {
        try {
            // 1. Convert MultipartFile → BufferedImage
            BufferedImage originalImage = ImageIO.read(file.getInputStream());

            if (originalImage == null) {
                throw new RuntimeException("Invalid image file");
            }

            int targetWidth = 384;
            int targetHeight = 384;

            // 2. Resize image to 384x384 with smooth scaling
            Image scaledImage = originalImage.getScaledInstance(
                    targetWidth,
                    targetHeight,
                    Image.SCALE_SMOOTH
            );

            // 3. Convert to RGB BufferedImage (3 channels)
            BufferedImage resizedImage = new BufferedImage(
                    targetWidth,
                    targetHeight,
                    BufferedImage.TYPE_3BYTE_BGR
            );

            Graphics2D g2d = resizedImage.createGraphics();

            // Improve rendering quality
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // 4. Write to byte array as JPEG
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);

            byte[] imageBytes = baos.toByteArray();

            // 5. Encode to Base64
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error processing image", e);
        }
    }

}
