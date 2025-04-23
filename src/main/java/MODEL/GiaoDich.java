package MODEL;

//import java.sql.Timestamp;
import java.util.Date;

public class GiaoDich {
	private String maGiaoDich;
	private Double tongTien;
	private Date thoiGianGiaoDich;
	private NhanVien nhanVien;
	private KhachHang khachHang;
	public GiaoDich() {
		
	}
	public GiaoDich(String maGiaoDich, Double tongTien, Date thoiGianGiaoDich, NhanVien nhanVien, KhachHang khachHang) {
		super();
		this.maGiaoDich = maGiaoDich;
		this.tongTien = tongTien;
		this.thoiGianGiaoDich = thoiGianGiaoDich;
		this.nhanVien = nhanVien;
		this.khachHang = khachHang;
	}
	public String getMaGiaoDich() {
		return maGiaoDich;
	}
	public void setMaGiaoDich(String maGiaoDich) {
		this.maGiaoDich = maGiaoDich;
	}
	public Double getTongTien() {
		return tongTien;
	}
	public void setTongTien(Double tongTien) {
		this.tongTien = tongTien;
	}
	public Date getThoiGianGiaoDich() {
		return thoiGianGiaoDich;
	}
	public void setThoiGianGiaoDich(Date thoiGianGiaoDich) {
		this.thoiGianGiaoDich = thoiGianGiaoDich;
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
		return "GiaoDich [maGiaoDich=" + maGiaoDich + ", tongTien=" + tongTien + ", thoiGianGiaoDich="
				+ thoiGianGiaoDich + ", nhanVien=" + nhanVien + ", khachHang=" + khachHang + "]";
	}
	
	

}
