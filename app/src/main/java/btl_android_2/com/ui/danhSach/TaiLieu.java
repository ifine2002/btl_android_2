//package btl_android_2.com.ui.danhSach;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.io.Serializable;
//
//public class TaiLieu implements Parcelable {
//    private int id;
//    private String tieuDe;
//    private String moTa;
//    private String noiDung;
//    private int trangThai;
//
//    private String soDienThoai;
//    private boolean isFree;
//    private int gia;
//
//    private int idAccount;
//    private int idLoaiTaiLieu;
//
//    public TaiLieu(int id, String tieuDe, String moTa, String noiDung, int trangThai, boolean isFree, int gia, int idAccount,int idLoaiTaiLieu ) {
//        this.id = id;
//        this.tieuDe = tieuDe;
//        this.moTa = moTa;
//        this.noiDung = noiDung;
//        this.trangThai = trangThai;
////        this.soDienThoai=soDienThoai;
//        this.isFree = isFree;
//        this.gia = gia;
//        this.idAccount=idAccount;
//        this.idLoaiTaiLieu=idLoaiTaiLieu;
//
//
//    }
//
//    protected TaiLieu(Parcel in) {
//        id = in.readInt();
//        tieuDe = in.readString();
//        moTa = in.readString();
//        noiDung = in.readString();
//        trangThai = in.readInt();
////        soDienThoai=in.readString();
//        isFree = in.readByte() != 0;
//        gia = in.readInt();
//        idAccount=in.readInt();
//        idLoaiTaiLieu=in.readInt();
//    }
//
//    public static final Parcelable.Creator<TaiLieu> CREATOR = new Creator<TaiLieu>() {
//        @Override
//        public TaiLieu createFromParcel(Parcel in) {
//            return new TaiLieu(in);
//        }
//
//        @Override
//        public TaiLieu[] newArray(int size) {
//            return new TaiLieu[size];
//        }
//    };
//
//    public int getId() { return id; }
//    public String getTieuDe() { return tieuDe; }
//    public String getMoTa() { return moTa; }
//    public String getNoiDung() { return noiDung; }
//    public int getTrangThai() { return trangThai; }
//    public boolean isFree() { return isFree; }
//    public int getGia() { return gia; }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public void setTieuDe(String tieuDe) {
//        this.tieuDe = tieuDe;
//    }
//
//    public void setMoTa(String moTa) {
//        this.moTa = moTa;
//    }
//
//    public void setNoiDung(String noiDung) {
//        this.noiDung = noiDung;
//    }
//
//    public void setTrangThai(int trangThai) {
//        this.trangThai = trangThai;
//    }
//
//    public void setFree(boolean free) {
//        isFree = free;
//    }
//
//    public void setGia(int gia) {
//        this.gia = gia;
//    }
//
////    public String getSoDienThoai() {
////        return soDienThoai;
////    }
//
//    public void setSoDienThoai(String soDienThoai) {
//        this.soDienThoai = soDienThoai;
//    }
//
////    public String getTacGia() {
////        return tacGia;
////    }
//
//    public int getIdAccount() {
//        return idAccount;
//    }
//
//    public void setIdAccount(int idAccount) {
//        this.idAccount = idAccount;
//    }
//
//    public int getIdLoaiTaiLieu() {
//        return idLoaiTaiLieu;
//    }
//
//    public void setIdLoaiTaiLieu(int idLoaiTaiLieu) {
//        this.idLoaiTaiLieu = idLoaiTaiLieu;
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(id);
//        dest.writeString(tieuDe);
//        dest.writeString(moTa);
//        dest.writeString(noiDung);
//        dest.writeInt(trangThai);
//        dest.writeString(soDienThoai);
//        dest.writeByte((byte) (isFree ? 1 : 0));
//        dest.writeInt(gia);
//        dest.writeInt(idAccount);
//        dest.writeInt(idLoaiTaiLieu);
//    }
//}
package btl_android_2.com.ui.danhSach;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TaiLieu implements Parcelable {
    private int id;
    private String tieuDe;
    private String tacGia;
    private String moTa;
    private String noiDung;
    private int trangThai;

    private String soDienThoai;
    private boolean isFree;
    private int gia;

    private int idAccount;
    private int idLoaiTaiLieu;

    public TaiLieu(int id, String tieuDe, String moTa, String noiDung, int trangThai, boolean isFree, int gia, int idAccount,int idLoaiTaiLieu ) {
        this.id = id;
        this.tieuDe = tieuDe;
        this.moTa = moTa;
        this.noiDung = noiDung;
        this.trangThai = trangThai;
        this.isFree = isFree;
        this.gia = gia;
        this.idAccount = idAccount;
        this.idLoaiTaiLieu = idLoaiTaiLieu;
    }

    protected TaiLieu(Parcel in) {
        id = in.readInt();
        tieuDe = in.readString();
        moTa = in.readString();
        noiDung = in.readString();
        trangThai = in.readInt();
        isFree = in.readByte() != 0;
        gia = in.readInt();
        idAccount = in.readInt();
        idLoaiTaiLieu = in.readInt();
    }

    public static final Creator<TaiLieu> CREATOR = new Creator<TaiLieu>() {
        @Override
        public TaiLieu createFromParcel(Parcel in) {
            return new TaiLieu(in);
        }

        @Override
        public TaiLieu[] newArray(int size) {
            return new TaiLieu[size];
        }
    };

    public int getId() { return id; }
    public String getTieuDe() { return tieuDe; }
    public String getMoTa() { return moTa; }
    public String getNoiDung() { return noiDung; }
    public int getTrangThai() { return trangThai; }
    public boolean isFree() { return isFree; }
    public int getGia() { return gia; }

    public void setId(int id) {
        this.id = id;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getIdLoaiTaiLieu() {
        return idLoaiTaiLieu;
    }

    public void setIdLoaiTaiLieu(int idLoaiTaiLieu) {
        this.idLoaiTaiLieu = idLoaiTaiLieu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tieuDe);
        dest.writeString(moTa);
        dest.writeString(noiDung);
        dest.writeInt(trangThai);
        dest.writeByte((byte) (isFree ? 1 : 0));
        dest.writeInt(gia);
        dest.writeInt(idAccount);
        dest.writeInt(idLoaiTaiLieu);
    }
}
