package Diem.Model;

import Diem.Entity.SubcribeEntity;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;

import java.io.Serializable;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class SubcribeModel implements Serializable {
    public String endpoint;
    public String key;
    public String auth;

    public SubcribeModel(String endpoint, String auth, String key) {
        this.endpoint = endpoint;
        this.key = key;
        this.auth = auth;
    }

    public SubcribeModel(SubcribeEntity subcribeEntity) {
        this.endpoint = subcribeEntity.getEndpoint();
        this.key = subcribeEntity.getKey();
        this.auth = subcribeEntity.getAuth();
    }

    public SubcribeEntity toEntity() {
        SubcribeEntity subcribeEntity = new SubcribeEntity();
        subcribeEntity.setAuth(auth);
        subcribeEntity.setEndpoint(endpoint);
        subcribeEntity.setKey(key);
        return subcribeEntity;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public byte[] getAuthAsBytes() {
        return Base64.getDecoder().decode(auth);
    }

    public String getKey() {
        return key;
    }

    /**
     * Returns the base64 encoded public key string as a byte[]
     */
    public byte[] getKeyAsBytes() {
        return Base64.getDecoder().decode(getKey());
    }

    /**
     * Returns the base64 encoded public key as a PublicKey object
     */
    public PublicKey getUserPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        KeyFactory kf = KeyFactory.getInstance("ECDH", BouncyCastleProvider.PROVIDER_NAME);
        ECNamedCurveParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256r1");
        ECPoint point = ecSpec.getCurve().decodePoint(getKeyAsBytes());
        ECPublicKeySpec pubSpec = new ECPublicKeySpec(point, ecSpec);
        return kf.generatePublic(pubSpec);
    }

}
