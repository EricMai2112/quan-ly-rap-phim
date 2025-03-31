package MODEL;

import java.util.Objects;

public class Phim {
	private String maPhim;
	private String tenPhim;
	private int thoiLuong;
	private String theLoai;
	private TrangThaiPhim trangThaiPhim;
	
	public Phim() {
		
	}
	
	public Phim(String maPhim, String tenPhim, int thoiLuong, String theLoai, TrangThaiPhim trangThaiPhim) {
		super();
		this.maPhim = maPhim;
		this.tenPhim = tenPhim;
		this.thoiLuong = thoiLuong;
		this.theLoai = theLoai;
		this.trangThaiPhim = trangThaiPhim;
	}
	
	public String getMaPhim() {
		return maPhim;
	}
	public void setMaPhim(String maPhim) {
		this.maPhim = maPhim;
	}
	public String getTenPhim() {
		return tenPhim;
	}
	public void setTenPhim(String tenPhim) {
		this.tenPhim = tenPhim;
	}
	public int getThoiLuong() {
		return thoiLuong;
	}
	public void setThoiLuong(int thoiLuong) {
		this.thoiLuong = thoiLuong;
	}
	public String getTheLoai() {
		return theLoai;
	}
	public void setTheLoai(String theLoai) {
		this.theLoai = theLoai;
	}
	
	public TrangThaiPhim getTrangThaiPhim() {
		return trangThaiPhim;
	}

	public void setTrangThaiPhim(TrangThaiPhim trangThaiPhim) {
		this.trangThaiPhim = trangThaiPhim;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maPhim);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phim other = (Phim) obj;
		return Objects.equals(maPhim, other.maPhim);
	}

	@Override
	public String toString() {
		return "Phim [maPhim=" + maPhim + ", tenPhim=" + tenPhim + ", thoiLuong=" + thoiLuong + ", theLoai=" + theLoai
				+ ", trangThaiPhim=" + trangThaiPhim + "]";
	}
	
	
	
}

