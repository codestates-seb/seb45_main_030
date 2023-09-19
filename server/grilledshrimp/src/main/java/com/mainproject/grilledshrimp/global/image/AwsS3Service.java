package com.mainproject.grilledshrimp.global.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.mainproject.grilledshrimp.global.exception.BusinessLogicException;
import com.mainproject.grilledshrimp.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = "images/" + createFilename(multipartFile);

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentDisposition("inline");
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, multipartFile.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch(IOException e) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_UPLOAD_FAILED);
        }

        String fileUrl = amazonS3.getUrl(bucket, fileName).toString();

        return fileUrl;
    }
    public String createFilename(MultipartFile multipartFile){
        int pos = multipartFile.getOriginalFilename().lastIndexOf(".");
        return UUID.randomUUID() + multipartFile.getOriginalFilename().substring(pos);
    }

    public String uploadThumbnail(MultipartFile multipartFile){
        try{
            String fileName = "thumbnails/" + createFilename(multipartFile);
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            int originalWidth = image.getWidth();
            int originalHeight = image.getHeight();
            int newHeight = (int) (originalHeight * (400 / (double) originalWidth));

            Image thumbnail =
                    image.getScaledInstance(400,
                            newHeight,
                            Image.SCALE_FAST);
            BufferedImage newImage = new BufferedImage(400, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = newImage.getGraphics();
            graphics.drawImage(thumbnail, 0, 0, null);
            graphics.dispose();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(newImage, "jpg", os);
            byte[] imageBytes = os.toByteArray();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(imageBytes.length);
            objectMetadata.setContentType("image/jpeg");

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, new ByteArrayInputStream(imageBytes), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            String fileUrl = amazonS3.getUrl(bucket, fileName).toString();

            return fileUrl;
        } catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.IMAGE_UPLOAD_FAILED);
        }

    }

}