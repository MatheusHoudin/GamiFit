package com.br.gamifit.model;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.br.gamifit.dao_factory.FirebaseFactory;
import com.br.gamifit.database.InviteFirebaseDAO;
import com.br.gamifit.database.UserFirebaseDAO;
import com.br.gamifit.database.dao_interface.IUserDAO;
import com.google.firebase.database.Exclude;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.QRCode;

import java.io.Serializable;
import java.util.Observable;

public class User extends Observable implements Serializable{
    private String name;
    private String email;
    private String password;
    private String code;

    public User(){}

    public User(String name,String email,String password){
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
    }

    public User(String name,String code,String email,String password){
        this.setName(name);
        this.setCode(code);
        this.setEmail(email);
        this.setPassword(password);
    }

    public Bitmap generateQRCode(){
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(this.code, BarcodeFormat.QR_CODE,600,600);
            int height = bitMatrix.getHeight();
            int width = bitMatrix.getWidth();
            Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);
            for(int x=0;x<width;x++){
                for(int y=0;y<height;y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK:Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GymInvite createInviteToJoin(Gym gym){
        GymInvite invite= new GymInvite();
        invite.setUser(this);
        invite.setGym(gym);
        return  invite;
    }

    public void createUserAccount(){
        IUserDAO userDAO = FirebaseFactory.getUserFirebaseDAO();
        userDAO.createUserAcount(this);
    }

    public void saveUser(){
        IUserDAO userDAO = FirebaseFactory.getUserFirebaseDAO();
        userDAO.saveUser(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode()==obj.hashCode();
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
