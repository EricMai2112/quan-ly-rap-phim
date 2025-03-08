package MODEL;

public class ChiTietGiaoDich {
	private String maChiTiet;
	private GiaoDich giaoDich;
	private Ve ve;
	private SanPham sanPham;
	private int soLuong;
	private Double donGia;
	private Double thanhTien;
	public ChiTietGiaoDich(String maChiTiet, GiaoDich giaoDich, Ve ve, SanPham sanPham, int soLuong, Double donGia,
			Double thanhTien) {
		super();
		this.maChiTiet = maChiTiet;
		this.giaoDich = giaoDich;
		this.ve = ve;
		this.sanPham = sanPham;
		this.soLuong = soLuong;
		this.donGia = donGia;
		this.thanhTien = thanhTien;
	}
	public String getMaChiTiet() {
		return maChiTiet;
	}
	public void setMaChiTiet(String maChiTiet) {
		this.maChiTiet = maChiTiet;
	}
	public GiaoDich getGiaoDich() {
		return giaoDich;
	}
	public void setGiaoDich(GiaoDich giaoDich) {
		this.giaoDich = giaoDich;
	}
	public Ve getVe() {
		return ve;
	}
	public void setVe(Ve ve) {
		this.ve = ve;
	}
	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public Double getDonGia() {
		return donGia;
	}
	public void setDonGia(Double donGia) {
		this.donGia = donGia;
	}
	public Double getThanhTien() {
		return thanhTien;
	}
	public void setThanhTien(Double thanhTien) {
		this.thanhTien = thanhTien;
	}
	@Override
	public String toString() {
		return "ChiTietGiaoDich [maChiTiet=" + maChiTiet + ", ve=" + ve + ", soLuong=" + soLuong + ", donGia=" + donGia
				+ ", thanhTien=" + thanhTien + "]";
	}
	
	

}
