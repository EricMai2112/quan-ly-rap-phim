package MODEL;

public class Ghe {
	private String maGhe;
	private PhongChieu phongChieu;
	private String soGhe;
	private LoaiGhe loaiGhe;
	public Ghe(String maGhe, PhongChieu phongChieu, String soGhe, LoaiGhe loaiGhe) {
		super();
		this.maGhe = maGhe;
		this.phongChieu = phongChieu;
		this.soGhe = soGhe;
		this.loaiGhe = loaiGhe;
	}
	public String getMaGhe() {
		return maGhe;
	}
	public void setMaGhe(String maGhe) {
		this.maGhe = maGhe;
	}
	public PhongChieu getPhongChieu() {
		return phongChieu;
	}
	public void setPhongChieu(PhongChieu phongChieu) {
		this.phongChieu = phongChieu;
	}
	public String getSoGhe() {
		return soGhe;
	}
	public void setSoGhe(String soGhe) {
		this.soGhe = soGhe;
	}
	public LoaiGhe getLoaiGhe() {
		return loaiGhe;
	}
	public void setLoaiGhe(LoaiGhe loaiGhe) {
		this.loaiGhe = loaiGhe;
	}

	@Override
	public String toString() {
		return "Ghe [maGhe=" + maGhe + ", phongChieu=" + phongChieu + ", soGhe=" + soGhe + "]";
	}
	
	
	
}
