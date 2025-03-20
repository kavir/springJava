package com.authh.springJwt.Wallet.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeService {

    public BufferedImage generateQRCode(String userDetails) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.MARGIN, 1); // Optional, adjust margin

        // Create QR code image from the string (user details)
        BitMatrix bitMatrix = qrCodeWriter.encode(userDetails, BarcodeFormat.QR_CODE, 200, 200, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
}
