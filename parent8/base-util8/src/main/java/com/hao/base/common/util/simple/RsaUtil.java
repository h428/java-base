package com.hao.base.common.util.simple;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtil {
    /**
     * 从文件中读取公钥
     *
     * @param filename 公钥保存路径，相对于classpath
     * @return 公钥对象
     */
    public static PublicKey getPublicKey(String filename) {
        byte[] bytes = readFile(filename);
        return getPublicKey(bytes);
    }

    /**
     * 从文件中读取密钥
     *
     * @param filename 私钥保存路径，相对于classpath
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String filename) {
        byte[] bytes = readFile(filename);
        return getPrivateKey(bytes);
    }

    /**
     * 获取公钥
     *
     * @param bytes 公钥的字节形式
     * @return 公钥
     */
    public static PublicKey getPublicKey(byte[] bytes) {
        try {
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取密钥
     *
     * @param bytes 私钥的字节形式
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(byte[] bytes) {
        try {
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据密文，生存 rsa 公钥和私钥,并写入指定文件
     *
     * @param publicKeyFilename  公钥文件路径
     * @param privateKeyFilename 私钥文件路径
     * @param secret             生成密钥的密文
     */
    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            SecureRandom secureRandom = new SecureRandom(secret.getBytes());
            keyPairGenerator.initialize(2048, secureRandom);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            // 获取公钥并写出
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            writeFile(publicKeyFilename, publicKeyBytes);
            // 获取私钥并写出
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            writeFile(privateKeyFilename, privateKeyBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] readFile(String fileName) {
        Path path = new File(fileName).toPath();
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeFile(String destPath, byte[] bytes) {
        try {
            Path path = new File(destPath).toPath();
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        String publicKeyPath = "D:\\tmp\\lab.pub";
        String privateKeyPath = "D:\\tmp\\lab";
        generateKey(publicKeyPath, privateKeyPath, "");
        PublicKey publicKey = RsaUtil.getPublicKey(publicKeyPath);
        PrivateKey privateKey = RsaUtil.getPrivateKey(privateKeyPath);
        assert publicKey != null;
        assert privateKey != null;
    }

}