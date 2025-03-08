package MODEL;

import java.util.Date;

public class LichChieu {
	private String maLichChieu;
	private PhongChieu phongChieu;
	private Phim phim;
	private java.util.Date ngayChieu;
	private java.util.Date gioBatDau;
	private java.util.Date gioKetThuc;
	public LichChieu(String maLichChieu, PhongChieu phongChieu, Phim phim, Date ngayChieu, Date gioBatDau,
			Date gioKetThuc) {
		super();
		this.maLichChieu = maLichChieu;
		this.phongChieu = phongChieu;
		this.phim = phim;
		this.ngayChieu = ngayChieu;
		this.gioBatDau = gioBatDau;
		this.gioKetThuc = gioKetThuc;
	}
	public String getMaLichChieu() {
		return maLichChieu;
	}
	public void setMaLichChieu(String maLichChieu) {
		this.maLichChieu = maLichChieu;
	}
	public PhongChieu getPhongChieu() {
		return phongChieu;
	}
	public void setPhongChieu(PhongChieu phongChieu) {
		this.phongChieu = phongChieu;
	}
	public Phim getPhim() {
		return phim;
	}
	public void setPhim(Phim phim) {
		this.phim = phim;
	}
	public java.util.Date getNgayChieu() {
		return ngayChieu;
	}
	public void setNgayChieu(java.util.Date ngayChieu) {
		this.ngayChieu = ngayChieu;
	}
	public java.util.Date getGioBatDau() {
		return gioBatDau;
	}
	public void setGioBatDau(java.util.Date gioBatDau) {
		this.gioBatDau = gioBatDau;
	}
	public java.util.Date getGioKetThuc() {
		return gioKetThuc;
	}
	public void setGioKetThuc(java.util.Date gioKetThuc) {
		this.gioKetThuc = gioKetThuc;
	}
	@Override
	public String toString() {
		return "LichChieu [maLichChieu=" + maLichChieu + ", phongChieu=" + phongChieu + ", phim=" + phim
				+ ", ngayChieu=" + ngayChieu + ", gioBatDau=" + gioBatDau + ", gioKetThuc=" + gioKetThuc + "]";
	}
	
	
}
