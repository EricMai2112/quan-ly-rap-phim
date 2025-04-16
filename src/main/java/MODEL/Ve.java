package MODEL;

import java.time.LocalDate;
import java.util.Date;

public class Ve {
	private String maVe;
	private LichChieu lichChieu;
	private Ghe ghe;
	private Double giaVe;
	private String trangThaiVe;
	private java.util.Date thoiGianBan;
	private NhanVien nhanVien;
	private KhachHang khachHang;
	
	public Ve() {
		
	}
	
	public Ve(String maVe, LichChieu lichChieu, Ghe ghe, Double giaVe, String trangThaiVe, Date thoiGianBan,
			NhanVien nhanVien, KhachHang khachHang) {
		super();
		this.maVe = maVe;
		this.lichChieu = lichChieu;
		this.ghe = ghe;
		this.giaVe = giaVe;
		this.trangThaiVe = trangThaiVe;
		this.thoiGianBan = thoiGianBan;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
	}
	public String getMaVe() {
		return maVe;
	}
	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}
	public LichChieu getLichChieu() {
		return lichChieu;
	}
	public void setLichChieu(LichChieu lichChieu) {
		this.lichChieu = lichChieu;
	}
	public Ghe getGhe() {
		return ghe;
	}
	public void setGhe(Ghe ghe) {
		this.ghe = ghe;
	}
	public Double getGiaVe() {
		return giaVe;
	}
	public void setGiaVe(Double giaVe) {
		this.giaVe = giaVe;
	}
	public String getTrangThaiVe() {
		return trangThaiVe;
	}
	public void setTrangThaiVe(String trangThaiVe) {
		this.trangThaiVe = trangThaiVe;
	}
	public java.util.Date getThoiGianBan() {
		return thoiGianBan;
	}
	public void setThoiGianBan(java.util.Date thoiGianBan) {
		this.thoiGianBan = thoiGianBan;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	@Override
	public String toString() {
		return "Ve [ghe=" + ghe + ", giaVe=" + giaVe + ", trangThaiVe=" + trangThaiVe + ", thoiGianBan=" + thoiGianBan
				+ "]";
	}

	
}
