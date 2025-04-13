package MODEL;

public class SanPham {
	private String maSanPham;
	private String tenSanPham;
	private String danhMuc;
	private Double giaTien;
	private int soLuong;
	
	public SanPham() {
		
	}
	public SanPham(String maSanPham, String tenSanPham, String danhMuc, Double giaTien, int soLuong) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.danhMuc = danhMuc;
		this.giaTien = giaTien;
		this.soLuong = soLuong;
	}
	public String getMaSanPham() {
		return maSanPham;
	}
	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	public String getDanhMuc() {
		return danhMuc;
	}
	public void setDanhMuc(String danhMuc) {
		this.danhMuc = danhMuc;
	}
	public Double getGiaTien() {
		return giaTien;
	}
	public void setGiaTien(Double giaTien) {
		this.giaTien = giaTien;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	@Override
	public String toString() {
		return "SanPham [maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + ", danhMuc=" + danhMuc + ", giaTien="
				+ giaTien + ", soLuong=" + soLuong + "]";
	}
	
	

}
