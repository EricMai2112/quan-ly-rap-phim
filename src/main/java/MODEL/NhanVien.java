package MODEL;

public class NhanVien {
	private String maNHanVien;
	private String hoTen;
	private String tenDangNhap;
	private String matKhau;
	private VaiTro vaiTro;
	public NhanVien(String maNHanVien, String hoTen, String tenDangNhap, String matKhau, VaiTro vaiTro) {
		super();
		this.maNHanVien = maNHanVien;
		this.hoTen = hoTen;
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.vaiTro = vaiTro;
	}
	public String getMaNHanVien() {
		return maNHanVien;
	}
	public void setMaNHanVien(String maNHanVien) {
		this.maNHanVien = maNHanVien;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getTenDangNhap() {
		return tenDangNhap;
	}
	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	public VaiTro getVaiTro() {
		return vaiTro;
	}
	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}
	@Override
	public String toString() {
		return "NhanVien [maNHanVien=" + maNHanVien + ", hoTen=" + hoTen + ", tenDangNhap=" + tenDangNhap + ", matKhau="
				+ matKhau + "]";
	}
	
	

}
