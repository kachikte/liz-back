package com.LizWedding.LizzysWeddingcore.controller;

import net.glxn.qrgen.javase.QRCode;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@RestController
public class QRCodeResource {

    @Bean
    public HttpMessageConverter<BufferedImage> createImageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    public BufferedImage generateQRCode(String code) throws IOException {
        ByteArrayOutputStream stream = QRCode.from(code).withSize(250, 250).stream();

        ByteArrayInputStream bis = new ByteArrayInputStream(stream.toByteArray());

        return ImageIO.read(bis);
    }


    @GetMapping(value = "/generate-code", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> generateCode() throws IOException {
        String code = "http://localhost:4200/brochure";
        return new ResponseEntity<BufferedImage>(generateQRCode(code), HttpStatus.ACCEPTED);
    }
}
