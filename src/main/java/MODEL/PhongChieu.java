package MODEL;

public class PhongChieu {
	private String maPhong;
	private String tenPhong;
	private int soLuongNguoi;
	

	public PhongChieu(String maPhong, String tenPhong) {
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
	}
	public PhongChieu(String maPhong, String tenPhong, int soLuongNguoi) {
		super();
		this.maPhong = maPhong;
		this.tenPhong = tenPhong;
		this.soLuongNguoi = soLuongNguoi;
	}
	public PhongChieu() {
		// TODO Auto-generated constructor stub
	}

	public String getMaPhong() {
		return maPhong;
	}
	public void setMaPhong(String maPhong) {
		this.maPhong = maPhong;
	}
	public String getTenPhong() {
		return tenPhong;
	}
	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}
	public int getSoLuongNguoi() {
		return soLuongNguoi;
	}
	public void setSoLuongNguoi(int soLuongNguoi) {
		this.soLuongNguoi = soLuongNguoi;
	}
	@Override
	public String toString() {
		return "PhongChieu [maPhong=" + maPhong + ", tenPhong=" + tenPhong + ", soLuongNguoi=" + soLuongNguoi + "]";
	}
}
