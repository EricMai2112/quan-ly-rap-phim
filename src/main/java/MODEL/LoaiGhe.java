/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package MODEL;

public enum LoaiGhe {
    THUONG("Ghe thuong"), VIP("Ghe vip");
    
    private String loaiGhe;
    

    private LoaiGhe(String loaiGhe) {
		this.loaiGhe = loaiGhe;
	}


	@Override
    public String toString() {
        return loaiGhe;
    }
}
