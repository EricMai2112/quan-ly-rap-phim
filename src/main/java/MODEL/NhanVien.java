package MODEL;

import java.util.Date;
import java.util.Objects;

public class NhanVien {
	private String maNHanVien;
	private String hoTen;
	private Date ngaySinh;
	private String soDienThoai;
	private String cccd;
	private VaiTro vaiTro;
	
	public NhanVien() {
		
	}
	
public NhanVien(String maNHanVien) {
	this.maNHanVien = maNHanVien;
	}
	
	public NhanVien(String maNHanVien, String hoTen, Date ngaySinh, String soDienThoai, String cccd, VaiTro vaiTro) {
		super();
		this.maNHanVien = maNHanVien;
		this.hoTen = hoTen;
		this.ngaySinh = ngaySinh;
		this.soDienThoai = soDienThoai;
		this.cccd = cccd;
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

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public VaiTro getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(maNHanVien);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(maNHanVien, other.maNHanVien);
	}

	@Override
	public String toString() {
		return "NhanVien [maNHanVien=" + maNHanVien + ", hoTen=" + hoTen + ", ngaySinh=" + ngaySinh + ", soDienThoai="
				+ soDienThoai + ", cccd=" + cccd + ", vaiTro=" + vaiTro + "]";
	}

	
}
