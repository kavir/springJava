package com.authh.springJwt.Wallet.Controller;

import com.authh.springJwt.Wallet.Service.QrCodeService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {
    @Autowired
    private QrCodeService qrCodeService;

    @GetMapping("/generate/{phoneNumber}")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable String phoneNumber) throws WriterException, IOException {
        String userDetails = phoneNumber; 
        System.out.println("the number chai ___"+phoneNumber);
        BufferedImage qrCodeImage = qrCodeService.generateQRCode(userDetails);
        System.out.println("the qr image is ___"+qrCodeImage);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImage, "PNG", baos);
        byte[] qrCodeBytes = baos.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/png");
        System.out.println("the qr byte is ___"+qrCodeBytes);

        return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
    }
}
